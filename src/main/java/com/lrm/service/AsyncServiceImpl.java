package com.lrm.service;

import com.lrm.util.OSSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AysncService {
    private static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    @Override
    //service层服务异化
    @Async("file_task")
    public Future<String> asyncDownload(HttpServletResponse response, String objectName,
                                String endpoint, String accessKeyId,
                                String accessKeySecret, String bucketName) throws IOException {
        logger.info("执行下载任务的线程：" + Thread.currentThread());
        OSSUtils.downloadFile(response.getOutputStream(), objectName, endpoint, accessKeyId, accessKeySecret, "wordverybig");
        logger.info("下载任务：" + Thread.currentThread() + "执行完毕");
        return new AsyncResult<>(Thread.currentThread().getName() + "下载线程异步执行完毕");
    }

    @Override
    @Async("file_task")
    public Future<String> asyncUpload(MultipartFile file, String endpoint, String accessKeyId,
                                      String accessKeySecret, String bucketName, String catalog, String fileName) {
        logger.info("执行下载任务的线程：" + Thread.currentThread());
        OSSUtils.uploadFile(file, endpoint, accessKeyId, accessKeySecret, bucketName, catalog, fileName);
        logger.info("下载任务：" + Thread.currentThread() + "执行完毕");
        return new AsyncResult<>(Thread.currentThread().getName() + "上传线程异步执行完毕");
    }


}
