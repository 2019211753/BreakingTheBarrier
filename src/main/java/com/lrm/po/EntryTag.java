package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_entryTag")
public class EntryTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "请输入标签名称")
    private String name;
    @JsonIgnore

    @ManyToMany(mappedBy = "entryTags")
    private List<InfoEntry> infoEntries = new ArrayList<>();

    public EntryTag(String tagName) {
        this.name = tagName;
    }

    public EntryTag() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
