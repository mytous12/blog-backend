package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.Comments;
import com.caseStudy.Blog.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByPost(Posts posts);
}
