package com.caseStudy.Blog.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserGroups implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer size;

    @ManyToOne
    private Users owner;

    private static final Integer MAX_SIZE = 256;

    public UserGroups() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public static Integer getMaxSize() {
        return MAX_SIZE;
    }
}
