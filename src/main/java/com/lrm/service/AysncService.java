package com.lrm.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface AysncService {
    void executeAysnc(HttpServletResponse response, String objectName,
                      String endpoint, String accessKeyId,
                      String accessKeySecret, String bucketName) throws IOException;
}
