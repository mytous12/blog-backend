package com.caseStudy.Blog.service;

import com.caseStudy.Blog.model.GroupMembers;
import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.UserGroups;
import com.caseStudy.Blog.model.Users;
import com.caseStudy.Blog.repository.GroupMembersRepository;
import com.caseStudy.Blog.repository.PostsRepository;
import com.caseStudy.Blog.repository.UserGroupsRepository;
import com.caseStudy.Blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsService {
    private UserGroupsRepository userGroupsRepository;
    private UsersRepository usersRepository;
    private GroupMembersRepository groupMembersRepository;
    private PostsRepository postsRepository;

    @Autowired
    public GroupsService(UserGroupsRepository userGroupsRepository, UsersRepository usersRepository,
                         GroupMembersRepository groupMembersRepository, PostsRepository postsRepository) {
        this.userGroupsRepository = userGroupsRepository;
        this.usersRepository = usersRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.postsRepository = postsRepository;
    }

    public UserGroups createGroup(Principal principal, String name) {
        Users owner = usersRepository.findByEmail(principal.getName()).get();
        UserGroups userGroups = new UserGroups();
        userGroups.setName(name);
        userGroups.setOwner(owner);
        userGroups.setSize(0);
        userGroupsRepository.saveAndFlush(userGroups);
        return userGroups;
    }

    public List<UserGroups> getGroups(Principal principal) {
        Users users = usersRepository.findByEmail(principal.getName()).get();
        return userGroupsRepository.findAllByOwner(users);
    }

    public List<UserGroups> deleteGroup(Principal principal, Long id) {
        Users users = usersRepository.findByEmail(principal.getName()).get();
        userGroupsRepository.deleteById(id);
        return userGroupsRepository.findAllByOwner(users);
    }

    public GroupMembers addMember(Long groupId, Long memberId) {
        Users users = usersRepository.findById(memberId).get();
        UserGroups userGroups = userGroupsRepository.findById(groupId).get();
        Optional<GroupMembers> optional = groupMembersRepository.findByUserGroupsAndUsers(userGroups, users);

        if (!optional.isPresent()) {
            GroupMembers groupMembers = new GroupMembers();
            groupMembers.setUsers(users);
            groupMembers.setUserGroups(userGroups);
            if (userGroups.getSize() < UserGroups.getMaxSize()) {
                userGroups.setSize(userGroups.getSize() + 1);
                userGroupsRepository.saveAndFlush(userGroups);
                groupMembersRepository.saveAndFlush(groupMembers);
            }
            return groupMembers;
        }
        return null;
    }

    public List<GroupMembers> removeMember(Long groupId, Long memberId) {
        Users users = usersRepository.findById(memberId).get();
        UserGroups userGroups = userGroupsRepository.findById(groupId).get();
        Optional<GroupMembers> optional = groupMembersRepository.findByUserGroupsAndUsers(userGroups, users);

        groupMembersRepository.delete(optional.get());
        userGroups.setSize(userGroups.getSize() - 1);
        userGroupsRepository.saveAndFlush(userGroups);

        return groupMembersRepository.findAllByUserGroups(userGroups);
    }

    public List<GroupMembers> getMembers(Long id) {
        UserGroups userGroups = userGroupsRepository.findById(id).get();
        return groupMembersRepository.findAllByUserGroups(userGroups);
    }

    public List<Posts> getPosts(Long groupId) {
        UserGroups userGroups = userGroupsRepository.findById(groupId).get();
        Users users = userGroups.getOwner();

        return postsRepository.findAllByAuthorAndIsPrivateOrderByDateDesc(users, 1);
    }
}
