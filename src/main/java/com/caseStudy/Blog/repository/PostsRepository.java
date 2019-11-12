package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByAuthorIdAndIsPrivate(Long authorId, Integer isPrivate);

    List<Posts> findAllByAuthorId(Long authorId);


    List<Posts> findAllByCategoryAndIsPrivate(String category, Integer isPrivate);

    List<Posts> findAllByTitleContainingOrDescriptionContainingIgnoreCaseAndIsPrivate(String keyword, String keyword2,
                                                                                      Integer isPrivate);

    List<Posts> findAllByDateAndIsPrivate(LocalDate date, Integer isPrivate);
}
