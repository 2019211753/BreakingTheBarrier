package com.lrm.web;

import com.lrm.dao.FileRepository;
import com.lrm.dao.FileTagRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.FileTag;
import com.lrm.service.AysncService;
import com.lrm.service.FileServiceImpl;
import com.lrm.service.SearchService;
import com.lrm.util.*;
import com.lrm.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/files")
public class FileController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload-path}")
    private String uploadFilePath;

    @Value("${file.download-path}")
    private String downloadFilePath;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${hotfile_queries}")
    private String hotFileQueries;

    @Value("${hotfile_downloads}")
    private String hotFileDownloads;

    @Value("${hotfile_nums}")
    private int hotFileNums;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileTagRepository fileTagRepository;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Autowired
    private AysncService aysncService;

    @Autowired
    SearchService searchService;

    @GetMapping
    public String index() {
        return "index";
    }

    /**
     * 返回所有标签
     * @return 所有文件标签
     */
    @GetMapping("/getAllTags")
    @ResponseBody
    public Result getAllTags() {
//        Page<FileTag> fileTags = fileTagRepository.findAll(PageRequest.of(0, 10));
        List<FileTag> fileTags = fileTagRepository.findAll();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileTags", fileTags);
        return new Result(hashMap, "文件标签");
    }

    /**
     * 查询关键词相关文件
     * @param query 用户想要查询的文件关键词
     * @return 查询结果
     */
    @GetMapping(value = "/find")
    @ResponseBody
    public Result find(@RequestParam("query") String query,
                       @RequestParam("pageIndex") Short pageIndex) {
        Long rank = searchService.rank(hotFileQueries, query);
        if (rank == null) {
            searchService.zadd(hotFileQueries, query, 1d);
        } else {
            int score = (int) searchService.zscore(hotFileQueries, query);
            searchService.zadd(hotFileQueries, query, score + 1);
        }
        Page<com.lrm.po.File> filePage = fileServiceImpl.findFile(query, pageIndex);
        HashMap<String, Object> hashMap = new HashMap<>(2);
        logger.error(query);
        hashMap.put("page", filePage);
        return new Result(hashMap, "文件查询成功");
    }

    @ResponseBody
    @GetMapping("/hotSearch")
    public Result hotSearch() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("hotQueries", searchService.reverseRange(hotFileQueries, 0, hotFileNums));
        return new Result(hashMap, "热搜数据");
    }

    @ResponseBody
    @GetMapping("/hotDownload")
    public Result hotDownload() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("hotDownloads", searchService.reverseRange(hotFileDownloads, 0, hotFileNums));
        return new Result(hashMap, "热下载文件");
    }

    /**
     *
     * @param uploadFile 上传文件
     * @param fileTagName 前端传入对应标签名字
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam("uploadFile") MultipartFile uploadFile,
                         @RequestParam("fileTagName") String fileTagName,
                         HttpServletRequest request) throws IOException, ExecutionException, InterruptedException {
        //打印上传文件信息
        logFileInfo(uploadFile, uploadFilePath);

        //因为每次调用getFileName的时候返回的结果都不一样 所以在每个方法中只能使用第一次调用的结果 在最上方声明
        String fileName = FileUtils.getFileName(uploadFile.getOriginalFilename());
        //多媒体文件转为po file
        com.lrm.po.File newFile = FileUtils.convertFile(uploadFile, fileName);
        String realTagNames = fileTagName.substring(1, fileTagName.length() - 1);
        String[] tagNames = realTagNames.split(",");
        for (int i = 0; i < tagNames.length; i++) {
            tagNames[i] = tagNames[i].substring(1, tagNames[i].length() - 1);
        }


        Future<String> futureRes = aysncService.asyncUpload(
                uploadFile, endpoint, accessKeyId, accessKeySecret, "wordverybig", "", fileName);

        //上传到oss
        OSSUtils.uploadFile(uploadFile, endpoint, accessKeyId, accessKeySecret, "wordverybig", "", fileName);

        //调用service 服务，储存到数据库，进行上传相关逻辑的处理
        fileServiceImpl.saveFile(newFile, tagNames, null);

        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put("contentType", uploadFile.getContentType());
        hashMap.put("fileName", uploadFile.getOriginalFilename());
        //这么写有缺陷，会丢失精度，应该四舍五入使用Decimal类（小问题，待更改-------------
        hashMap.put("fileSize", String.valueOf(uploadFile.getSize() / 1024) + "KB");//单位是B,大写B代表byte，小写b代表bit
        logger.info(futureRes.get());
        Result res = new Result(hashMap, "上传成功");
        return res;
    }

    /**
     * @param fileId
     */
    @GetMapping("/download")
    @ResponseBody
    public Result fileDownload(@RequestParam("fileId") Long fileId,
                               HttpServletResponse response,
                               HttpServletRequest request) throws IOException, ExecutionException, InterruptedException {
        //通过id查询唯一的文件
        Optional<com.lrm.po.File> found = fileRepository.findById(fileId);

        if (!found.isPresent()) {
            throw new NotFoundException("无对应此id的文件");
        }
        String fileName = found.get().getName();
        System.out.println("the filename: " + fileName);
        String originName = found.get().getOriginName();
        //直接在这里判断文件存不存在,如果不存在会抛异常的
        fileServiceImpl.downloadFile(found.get());

        try {
            Long rank = searchService.rank(hotFileDownloads, originName);
            if (rank == null) {
                searchService.zadd(hotFileDownloads, originName, 1d);
            } else {
                searchService.zadd(hotFileDownloads, originName,
                        searchService.zscore(hotFileDownloads, originName) + 1d);
            }
        } catch (RedisConnectionFailureException ex) {
            logger.info("redis未连接");
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

//        OSSUtils.downloadFile(response.getOutputStream(), fileName, endpoint, accessKeyId, accessKeySecret, "wordverybig");
        Future<String> futureRes = aysncService.asyncDownload(
                response, fileName, endpoint, accessKeyId, accessKeySecret, "wordverybig");
        logger.info(futureRes.get());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", fileName);

        return new Result(hashMap, "下载成功");
    }

    private void logFileInfo(MultipartFile file, String uploadFilePath) {
        logger.info("文件名：" + file.getName());
        logger.info("原文件名：" + file.getOriginalFilename());
        logger.info("文件类型" + file.getContentType());
        logger.info("文件地址: " + (uploadFilePath + file.getOriginalFilename()));
    }

    public String getFilePath(String sourceFileName) {
        Date dateTime = new Date();
        return sourceFileName + dateTime.toString();
    }

}
