package com.lrm.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "t_team")
public class Team {
    /**
     * 每个类都要有一个id主键
     */
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "请输入小组名称")
    private String nickname;

    /**
     * 小组组长
     */
    @ManyToOne
    private User leader;

    /**
     * 小组成员
     */
    @ManyToMany
    private Set<User> members = new HashSet<>();

    /**
     * 已经加入的人数
     */
    private Integer size;

    /**
     * 希望创建的团队人数
     */
    private Integer capacity;

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    /**
     * 是否满员
     */
    private boolean isFull;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
