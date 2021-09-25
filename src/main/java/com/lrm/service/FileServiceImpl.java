package com.lrm.service;


import com.lrm.dao.FileRepository;
import com.lrm.dao.FileTagRepository;
import com.lrm.dao.UserRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.File;
import com.lrm.po.FileTag;
import com.lrm.po.User;
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
    public File saveFile(File file, String[] tagNames, String path, Long userId) {
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
        User uploadUser = userServiceImpl.getUser(userId);
        if (uploadUser == null) {
            throw new NotFoundException("当前用户不存在");
        }
        uploadUser.setAvailableNum(uploadUser.getAvailableNum() + 5);
        file.setUploadUser(uploadUser);
        return fileRepository.save(file);
    }


    @Override
    @Transactional
    public void downloadFile(String fileName, Long userId) {
        File file = fileRepository.findByName(fileName);
        if (file == null) {
            throw new NotFoundException("下载的文件资源不存在");
        }
        User user = userServiceImpl.getUser(userId);
        if (user == null) {
            throw new NotFoundException("当前用户不存在");
        }
        //可用下载次数
        user.setAvailableNum(user.getAvailableNum() - 1);
        file.setDownloadCount(file.getDownloadCount() + 1);
    }


    @Override
    public Page<File> findFile(String query, Short pageIndex) {
        if (query == null)
            return fileRepository.findAll(PageRequest.of(pageIndex, 9));
        return fileRepository.findByQuery(query, PageRequest.of(pageIndex, 9));
    }
}
