package com.caseStudy.Blog.controller;

import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/post")
public class PostsController {
    private PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping(path = "/get", produces = "application/json")
    public List<Posts> getMyPosts(Principal principal) {
        return postsService.getMyPosts(principal);
    }

    @GetMapping(path = "/get-by-category", produces = "application/json")
    public List<Posts> getPostsByCategory(@RequestParam(value = "category") String category) {
        return postsService.getPostsByCategory(category);
    }

    @GetMapping(path = "/get-by-author", produces = "application/json")
    public List<Posts> getPostsByAuthor(@RequestParam(value = "id") Long id) {
        return postsService.getPostsByAuthor(id);
    }

    @GetMapping(path = "/get-by-title", produces = "application/json")
    public List<Posts> getPostsByTitle(@RequestParam(value = "title") String title) {
        return postsService.getPostsByTitle(title);
    }

    @GetMapping(path = "/get-by-date", produces = "application/json")
    public List<Posts> getPostsByDate(@RequestParam(value = "year") Integer year,
                                      @RequestParam(value = "month") Integer month,
                                      @RequestParam(value = "day") Integer day) {
        return postsService.getPostsByDate(year, month, day);
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public List<Posts> addPost(@RequestBody Posts post, Principal principal) {
        return postsService.addPost(post, principal);
    }

    @PostMapping(path = "/delete")
    public List<Posts> deletePost(@RequestParam(value = "id") Long id, Principal principal) {
        return postsService.deletePost(principal, id);
    }

    @PostMapping(path = "/edit", consumes = "application/json")
    public List<Posts> editPost(@RequestBody Posts post, @RequestParam(value = "id") Long id) {
        return postsService.editPost(post, id);
    }

    @GetMapping(path = "/view", produces = "application/json")
    public Posts viewPost(@RequestParam(value = "id") Long id) {
        return postsService.viewPost(id);
    }

    @PostMapping(path = "/like", produces = "application/json")
    public Posts likePost(@RequestParam(value = "id") Long id) {
        return postsService.likePost(id);
    }

    @PostMapping(path = "/unlike", produces = "application/json")
    public Posts unlikePost(@RequestParam(value = "id") Long id) {
        return postsService.unlikePost(id);
    }

    @PostMapping(path = "/dislike", produces = "application/json")
    public Posts dislikePost(@RequestParam(value = "id") Long id) {
        return postsService.dislikePost(id);
    }

    @PostMapping(path = "/un-dislike", produces = "application/json")
    public Posts unDislikePost(@RequestParam(value = "id") Long id) {
        return postsService.unDislikePost(id);
    }
}
