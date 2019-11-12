package com.caseStudy.Blog.controller;

import com.caseStudy.Blog.model.GroupMembers;
import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.UserGroups;
import com.caseStudy.Blog.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/groups")
public class GroupsController {
    private GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @PostMapping("/create")
    public UserGroups createGroup(@RequestBody String name, Principal principal) {
        return groupsService.createGroup(principal, name);
    }

    @GetMapping("/get")
    public List<UserGroups> getGroups(Principal principal) {
        return groupsService.getGroups(principal);
    }

    @PostMapping("/delete")
    public List<UserGroups> deleteGroup(@RequestParam Long id, Principal principal) {
        return groupsService.deleteGroup(principal, id);
    }

    @PostMapping("/add-member")
    public GroupMembers addMember(@RequestParam Long groupId,@RequestParam Long memberId) {
        return groupsService.addMember(groupId, memberId);
    }

    @PostMapping("/remove-member")
    public List<GroupMembers> removeMember(@RequestParam Long groupId, @RequestParam Long memberId) {
        return groupsService.removeMember(groupId, memberId);
    }

    @GetMapping("/get-members")
    public List<GroupMembers> getMembers(@RequestParam Long groupId) {
        return groupsService.getMembers(groupId);
    }

    @GetMapping("get-posts")
    public List<Posts> getPosts(@RequestParam Long groupId) {
        return groupsService.getPosts(groupId);
    }
}
