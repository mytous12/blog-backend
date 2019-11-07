package com.caseStudy.Blog.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Subscribers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users subscriber;

    @ManyToOne
    private Users author;

    public Subscribers() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Users subscriber) {
        this.subscriber = subscriber;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }
}
