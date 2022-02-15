package com.lrm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Future;

@Service
public interface AysncService {
    Future<String> asyncDownload(HttpServletResponse response, String objectName,
                                 String endpoint, String accessKeyId,
                                 String accessKeySecret, String bucketName) throws IOException;

    Future<String> asyncUpload(MultipartFile file, String endpoint, String accessKeyId,
                               String accessKeySecret, String bucketName, String catalog, String fileName);
}
