package com.lrm.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    private List<User> members;

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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

}
