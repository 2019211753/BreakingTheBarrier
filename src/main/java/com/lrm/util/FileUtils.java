package com.lrm.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * 文件控制类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class FileUtils {

    /**
     * @param file 被删除的文件
     */
    public static void deleteFile(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //判断是否是文件
            if (file.isFile()) {
                //删除文件
                file.delete();
                //否则如果它是一个目录
            } else if (file.isDirectory()) {
                //声明目录下所有的文件 files[];
                File[] files = file.listFiles();
                //遍历目录下所有的文件
                for (File value : files) {
                    //把每个文件用这个方法进行迭代
                    deleteFile(value);
                }
                //删除文件夹
                file.delete();
            }
        }
    }


    /**
     * 上传文件
     *
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 原文件名
     * @return 新文件名/null
     * @throws IOException 文件大小溢出异常
     */
    public static String upload(MultipartFile file, String path, String fileName) throws IOException {

        // 生成新的文件和目录名
        String newFileName = getFileName(fileName);

        String realPath = path + "/" + newFileName;
        File folder = new File(realPath);
        file.transferTo(folder);
        return newFileName;
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 源文件名
     * @return 得到如.jpg .png的后缀
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     *
     * @param fileOriginName 源文件名
     * @return 新文件名
     */
    public static String getFileName(String fileOriginName) {
        return UUID.randomUUID() + getSuffix(fileOriginName);
    }

    /**
     * @param path 需要重新建立的文件夹路径
     */
    public static void rebuildFolder(String path) {
        //需要重建建立的文件夹
        File folder = new File(path);

        //如果文件夹不存在，创建文件夹 否则删除文件夹，然后再创建文件夹
        if (folder.exists()) {
            FileUtils.deleteFile(folder);
        }

        folder.mkdirs();
    }


    /**
     * @param path 需要建立的文件夹
     */
    public static void buildFolder(String path) {
        //需要建立的文件夹
        File folder = new File(path);

        folder.mkdirs();
    }

    /**
     * @param path 由FileController中传进来的uploadFilePath + file.getOriginalFileName()
     * @return dest 目标文件夹
     */
    public static File buildDest(String path) {
        File dest = new File(path);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        return dest;
    }

    /**
     * 将multipartFile的信息储存在file中
     * @param uploadFile
     * @return 数据库中的file
     */
    public static com.lrm.po.File convertFile(MultipartFile uploadFile) {
        com.lrm.po.File newFile = new com.lrm.po.File(uploadFile.getOriginalFilename());
        //由于docu项目未实现登录功能，所以先忽略User这方面
        newFile.setName(uploadFile.getOriginalFilename());
        newFile.setSize(uploadFile.getSize() / 1024 + "KB");
        return newFile;
    }

    public static String convertAvatar(String path, String avatar) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path + File.separator + avatar);
            return Base64.getEncoder().encodeToString(FileUtils.inputStream2Byte(fileInputStream));
        } catch (IOException e) {
            //没找到文件就返回null
            return null;
        } finally {
            //如果fileInputStream不为null就关闭。
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     /**
     * 将inputstream转为byte[]
     *
     * @param is 服务器的文件流
     * @return byte[]文件而不是base64编码后的文件
     */
    public static byte[] inputStream2Byte(InputStream is) throws IOException {
        byte[] data = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new IOException("输入流关闭异常");
                }
            }
        }
        return data;
    }

    /**
     * base64转inputStream
     *
     * @param base64string base64编码的文件字符串
     * @return 返回指向文件的文件输入流
     */
    public static InputStream base2InputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes1 = decoder.decode(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
}
