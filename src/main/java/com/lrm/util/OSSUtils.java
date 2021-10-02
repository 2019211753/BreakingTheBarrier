package com.lrm.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

public class OSSUtils {
    private static Logger logger = LoggerFactory.getLogger(OSSUtils.class);

    public static void uploadFile(MultipartFile multipartFile, Date date,
                                  String endpoint, String accessKeyId,
                                  String accessKeySecret, String bucketName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 判断Bucket是否存在。
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket
                ossClient.createBucket(bucketName);
            }

            // 查看Bucket信息
            BucketInfo info = ossClient.getBucketInfo(bucketName);
            System.out.println("Bucket " + bucketName + "的信息如下：");
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());

            // 上传文件
            if (multipartFile.getSize() != 0 && !multipartFile.getName().equals("")) {
                InputStream multiInputStream = multipartFile.getInputStream();
                ossClient.putObject(bucketName, multipartFile.getOriginalFilename() + date.toString(), multiInputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        logger.info("文件上传到OSS成功");
    }


    public static void downloadFile(OutputStream os, String objectName,
                             String endpoint, String accessKeyId,
                             String accessKeySecret, String bucketName) throws IOException {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //通过文件名获取ossObject
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);

        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }

        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }


}
