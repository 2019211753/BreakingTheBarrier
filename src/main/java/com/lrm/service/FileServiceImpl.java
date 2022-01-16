package com.lrm.service;


import com.lrm.dao.FileRepository;
import com.lrm.dao.FileTagRepository;
import com.lrm.dao.UserRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.File;
import com.lrm.po.FileTag;
import com.lrm.po.User;
import com.lrm.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileTagRepository fileTagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public File saveFile(File file, String[] tagNames, String path) {
        //标签是否存在？
        for (int i = 0; i < tagNames.length; i++) {
            FileTag found = fileTagRepository.findByName(tagNames[i]);

            //如果用户输入的tagName不存在，那么创建一个新的tag
            if (found == null) {
                found = new FileTag(tagNames[i]);
                fileTagRepository.save(found);
            }
            file.getFileTags().add(found);
        }


        //录入file的path
        file.setPath(path);

        //奖励5次下载次数
        //利用threadLocal传入userservice层
        User uploader = UserHolder.getLocalUser();
        if (uploader == null) {
            throw new NotFoundException("当前用户不存在");
        }
        uploader.setAvailableNum(uploader.getAvailableNum() + 5);
        file.setUploadUser(uploader);
        return fileRepository.save(file);
    }


    @Override
    @Transactional
    public void downloadFile(File file) {
        User downloader = UserHolder.getLocalUser();
        if (downloader == null) {
            throw new NotFoundException("当前用户不存在");
        }
        //可用下载次数
        downloader.setAvailableNum(downloader.getAvailableNum() - 1);
        file.setDownloadCount(file.getDownloadCount() + 1);
    }


    @Override
    public Page<File> findFile(String query, Short pageIndex) {
        if (query == null)
            return fileRepository.findAll(PageRequest.of(pageIndex, 9));
        return fileRepository.findByQuery(query, PageRequest.of(pageIndex, 9));
    }
}
