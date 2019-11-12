package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.UserGroups;
import com.caseStudy.Blog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupsRepository extends JpaRepository<UserGroups, Long> {
    List<UserGroups> findAllByOwner(Users owner);

}
