package com.lrm.service;

import com.lrm.po.File;
import org.springframework.data.domain.Page;

public interface FileService {
    File saveFile(File file, String tagName, String path, Long userId);

    Page<File> findFile(String query);

    void downloadFile(String fileName, Long userId);
}
