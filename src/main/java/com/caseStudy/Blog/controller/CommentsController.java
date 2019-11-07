package com.caseStudy.Blog.controller;

import com.caseStudy.Blog.model.Comments;
import com.caseStudy.Blog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/comment")
public class CommentsController {
    private CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping(path = "/view-comments", produces = "application/json")
    public List<Comments> viewComments(@RequestParam(value = "id") Long id) {
        return commentsService.viewComments(id);
    }

    @PostMapping(path = "/create-comment", consumes = "application/json", produces = "application/json")
    public List<Comments> createComment(@RequestParam(value = "id") Long id, @RequestBody Comments comment,
                                        Principal principal) {
        return commentsService.createComment(comment, principal, id);
    }

    @PostMapping(path = "/delete-comment", consumes = "application/json", produces = "application/json")
    public List<Comments> deleteComment(@RequestParam(value = "postId") Long postId,
                                        @RequestParam(value = "commentId") Long commentId,
                                        Principal principal) {
        return commentsService.deleteComment(principal, postId, commentId);
    }

    @PostMapping(path = "/like", consumes = "application/json", produces = "application/json")
    public List<Comments> likeComment(@RequestParam(value = "id") Long id) {
        return commentsService.like(id);
    }

    @PostMapping(path = "/unlike", consumes = "application/json", produces = "application/json")
    public List<Comments> unlikeComment(@RequestParam(value = "id") Long id) {
        return commentsService.unLike(id);
    }
}
