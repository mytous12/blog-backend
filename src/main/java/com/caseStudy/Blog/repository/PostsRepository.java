package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByAuthorIdAndIsPrivateOrderByDateDesc(Long authorId, Integer isPrivate);

    List<Posts> findAllByAuthorIdOrderByDateDesc(Long authorId);

    List<Posts> findAllByAuthorAndIsPrivateOrderByDateDesc(Users author,Integer isPrivate);

    List<Posts> findAllByIsPrivateOrderByDateDesc(Integer isPrivate);


    List<Posts> findAllByCategoryAndIsPrivateOrderByDateDesc(String category, Integer isPrivate);

    List<Posts> findAllByTitleContainingAndIsPrivateOrderByDateDesc(String title,
                                                                    Integer isPrivate);

    List<Posts> findAllByDescriptionContainingAndIsPrivateOrderByDateDesc(String description, Integer isPrivate);

    List<Posts> findTop5ByOrderByVisitedDesc();

    List<Posts> findAllByDateAndIsPrivateOrderByDateDesc(LocalDate date, Integer isPrivate);
}
