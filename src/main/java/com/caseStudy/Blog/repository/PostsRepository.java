package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByAuthorId(Long authorId);

    List<Posts> findAllByCategory(String category);

    List<Posts> findAllByTitleContainingOrDescriptionContainingIgnoreCase(String keyword, String keyword2);

    List<Posts> findAllByDate(LocalDate date);
}
