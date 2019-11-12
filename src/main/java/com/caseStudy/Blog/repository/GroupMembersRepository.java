package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.GroupMembers;
import com.caseStudy.Blog.model.UserGroups;
import com.caseStudy.Blog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {
    List<GroupMembers> findAllByUserGroups(UserGroups groups);

    Optional<GroupMembers> findByUserGroupsAndUsers(UserGroups groups, Users member);
}
