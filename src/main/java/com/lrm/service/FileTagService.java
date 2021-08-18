package com.lrm.service;

import com.lrm.po.FileTag;

public interface FileTagService {
    FileTag saveTag(FileTag fileTag);

    FileTag getFileTag(Long id);

    FileTag getFileTagByName(String name);

    FileTag updateTag(FileTag fileTag);

    void deleteTag(long id);

}
