package com.lrm.service;


import com.lrm.dao.FileTagRepository;
import com.lrm.exception.CommonException;
import com.lrm.po.FileTag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileTagServiceImpl implements FileTagService {
    @Autowired
    private FileTagRepository fileTagRepository;

    @Override
    public FileTag saveTag(FileTag fileTag) {
        FileTag found = fileTagRepository.findByName(fileTag.getName());
        //没有同名
        if (found == null) {
            return fileTagRepository.save(fileTag);
        } else {
            throw new CommonException("存在同名标签");
        }
    }

    @Override
    public FileTag getFileTag(Long id) {
        Optional<FileTag> optionalFileTag = fileTagRepository.findById(id);
        return optionalFileTag.orElse(null);
    }

    @Override
    public FileTag getFileTagByName(String name) {
        return fileTagRepository.findByName(name);
    }


    @Override
    public FileTag updateTag(FileTag fileTag) {
        Optional<FileTag> optionalFileTag = fileTagRepository.findById(fileTag.getId());
        //如果存在，返回optional中的值，否则抛出异常
        FileTag targetTag = optionalFileTag.get();
        BeanUtils.copyProperties(fileTag, targetTag);
        return fileTagRepository.save(targetTag);
    }

    @Override
    public void deleteTag(long id) {
        fileTagRepository.deleteById(id);
    }
}

