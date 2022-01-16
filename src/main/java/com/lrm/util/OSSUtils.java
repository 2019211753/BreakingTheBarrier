package com.lrm.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.aliyun.oss.internal.OSSConstants.URL_ENCODING;

public class OSSUtils {
    private static Logger logger = LoggerFactory.getLogger(OSSUtils.class);

    public static void uploadFile(MultipartFile multipartFile,
                                  String endpoint, String accessKeyId,
                                  String accessKeySecret, String bucketName,
                                  String catalog, String fileName) {
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
            if (multipartFile.getSize() != 0 && !"".equals(multipartFile.getName())) {
                InputStream multiInputStream = multipartFile.getInputStream();
                ossClient.putObject(bucketName, catalog + fileName, multiInputStream);
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

    public static void deleteFile(String endpoint, String accessKeyId,
                                  String accessKeySecret, String bucketName,
                                  String catalog) {
        //创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //删除目录及目录下的所有文件。
        String nextMarker = null;
        ObjectListing objectListing;
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName)
                    .withPrefix(catalog)
                    .withMarker(nextMarker);

            objectListing = ossClient.listObjects(listObjectsRequest);
            if (objectListing.getObjectSummaries().size() > 0) {
                List<String> keys = new ArrayList<>();
                for (OSSObjectSummary s : objectListing.getObjectSummaries()) {
                    System.out.println("key name: " + s.getKey());
                    keys.add(s.getKey());
                }
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys).withEncodingType(URL_ENCODING);
                DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest);
                List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
                try {
                    for(String obj : deletedObjects) {
                        String deleteObj =  URLDecoder.decode(obj, "UTF-8");
                        System.out.println(deleteObj);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());

        //关闭OSSClient。
        ossClient.shutdown();
    }

}
