package com.caseStudy.Blog.service;

import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.Users;
import com.caseStudy.Blog.repository.PostsRepository;
import com.caseStudy.Blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class PostsService {
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }


    public List<Posts> addPost(Posts posts, Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();
        posts.setAuthorId(user.getId());
        posts.setDate(new Date());
        posts.setVisited(0L);

        postsRepository.saveAndFlush(posts);

        return postsRepository.findAllByAuthorId(user.getId());
    }

    public List<Posts> getPosts(Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();

        return postsRepository.findAllByAuthorId(user.getId());
    }

    public List<Posts> deletePost(Principal principal, Long id) {
        Optional<Posts> posts = postsRepository.findById(id);
        Users user = usersRepository.findByEmail(principal.getName()).get();
        posts.ifPresent(value -> postsRepository.delete(value));
        return postsRepository.findAllByAuthorId(user.getId());
    }

    public List<Posts> getPostByCategory(String category) {
        return postsRepository.findAllByCategory(category);
    }

    public List<Posts> editPost(Posts newPost, Long id) {
        Posts oldPost = postsRepository.findById(id).get();
        newPost.setAuthorId(oldPost.getAuthorId());
        newPost.setVisited(oldPost.getVisited());
        newPost.setDate(oldPost.getDate());
        newPost.setId(oldPost.getId());
        postsRepository.saveAndFlush(newPost);

        return postsRepository.findAllByAuthorId(newPost.getAuthorId());

    }

    public Posts viewPost(Long id) {
        Posts post = postsRepository.findById(id).get();
        post.setVisited(post.getVisited() + 1);
        postsRepository.saveAndFlush(post);

        return post;
    }

    public List<Posts> getPostsByAuthor(Long id) {
        return postsRepository.findAllByAuthorId(id);
    }
}
