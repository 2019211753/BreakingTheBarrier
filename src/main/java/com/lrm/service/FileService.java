package com.lrm.service;

import com.lrm.po.File;
import org.springframework.data.domain.Page;

public interface FileService {
    File saveFile(File file, String[] tagNames, String path);

    Page<File> findFile(String query, Short pageIndex);

    void downloadFile(File file);
}
