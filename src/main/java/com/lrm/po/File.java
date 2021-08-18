package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_file")
public class File {
    @Id
    @GeneratedValue
    private Long id;

    //文件名
    private String name;

    //文件存放 路径
    private String path;

    //文件大小
    private String size;

    //下载次数
    private Integer downloadCount = 0;

    //浏览或搜索次数，在service层中每被调用一次，增加一次
    private Integer viewCount = 0;

    // 对应的标签类型
    @ManyToMany
    private List<FileTag> fileTags = new ArrayList<>();

    @ManyToOne
    private User uploadUser;

    public File() {
    }

    public File(String filename) {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public List<FileTag> getFileTags() {
        return fileTags;
    }

    public void setFileTags(List<FileTag> fileTags) {
        this.fileTags = fileTags;
    }

    public User getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(User uploadUser) {
        this.uploadUser = uploadUser;
    }

}
