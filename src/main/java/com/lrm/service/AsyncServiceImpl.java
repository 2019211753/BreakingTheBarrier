package com.lrm.service;

import com.lrm.util.OSSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class AsyncServiceImpl implements AysncService {
    private static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    @Override
    //service层服务异化
    @Async("file_task")
    public void executeAysnc(HttpServletResponse response, String objectName,
                             String endpoint, String accessKeyId,
                             String accessKeySecret, String bucketName) throws IOException {
        logger.info("Aysnc task start");
        OSSUtils.downloadFile(response.getOutputStream(), objectName, endpoint, accessKeyId, accessKeySecret, "wordverybig");
        logger.info("Aysnc task finished");
    }

}
