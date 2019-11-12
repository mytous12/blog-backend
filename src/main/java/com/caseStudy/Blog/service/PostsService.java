package com.caseStudy.Blog.service;

import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.Users;
import com.caseStudy.Blog.repository.PostsRepository;
import com.caseStudy.Blog.repository.SubscribersRepository;
import com.caseStudy.Blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class PostsService {
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private SubscribersRepository subscribersRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository, UsersRepository usersRepository, SubscribersRepository subscribersRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
        this.subscribersRepository = subscribersRepository;
    }


    public List<Posts> addPost(Posts posts, Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();
        posts.setAuthorId(user.getId());
        posts.setDate(LocalDate.now());
        posts.setVisited(0L);
        posts.setLikes(0L);
        posts.setDislikes(0L);

        postsRepository.saveAndFlush(posts);

        return postsRepository.findAllByAuthorId(user.getId());
    }

    public List<Posts> getMyPosts(Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();

        return postsRepository.findAllByAuthorId(user.getId());
    }

    public List<Posts> deletePost(Principal principal, Long id) {
        Optional<Posts> posts = postsRepository.findById(id);
        Users user = usersRepository.findByEmail(principal.getName()).get();
        posts.ifPresent(value -> postsRepository.delete(value));
        return postsRepository.findAllByAuthorId(user.getId());
    }

    public List<Posts> getPostsByCategory(String category) {
        return postsRepository.findAllByCategoryAndIsPrivate(category, 0);
    }

    public List<Posts> getPostsByTitle(String title) {
        return postsRepository.findAllByTitleContainingOrDescriptionContainingIgnoreCaseAndIsPrivate(title, title,0);
    }

    public List<Posts> getPostsByDate(Integer year, Integer month, Integer day) {
        return postsRepository.findAllByDateAndIsPrivate(LocalDate.of(year, month, day), 0);
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
        return postsRepository.findAllByAuthorIdAndIsPrivate(id, 0);
    }

    public Posts likePost(Long id) {
        Posts post = postsRepository.findById(id).get();
        post.setLikes(post.getLikes() + 1);
        postsRepository.saveAndFlush(post);
        return postsRepository.findById(id).get();
    }

    public Posts unlikePost(Long id) {
        Posts post = postsRepository.findById(id).get();
        post.setLikes(post.getLikes() - 1);
        postsRepository.saveAndFlush(post);
        return postsRepository.findById(id).get();
    }

    public Posts dislikePost(Long id) {
        Posts post = postsRepository.findById(id).get();
        post.setDislikes(post.getDislikes() + 1);
        postsRepository.saveAndFlush(post);
        return postsRepository.findById(id).get();
    }

    public Posts unDislikePost(Long id) {
        Posts post = postsRepository.findById(id).get();
        post.setDislikes(post.getDislikes() - 1);
        postsRepository.saveAndFlush(post);
        return postsRepository.findById(id).get();
    }

//    public List<Posts> getRecentFromSubscriptions(Principal principal) {
//
//    }

}
