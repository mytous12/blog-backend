package com.caseStudy.Blog.service;

import com.caseStudy.Blog.model.Comments;
import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.Users;
import com.caseStudy.Blog.repository.CommentsRepository;
import com.caseStudy.Blog.repository.PostsRepository;
import com.caseStudy.Blog.repository.SubscribersRepository;
import com.caseStudy.Blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostsService {
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private SubscribersRepository subscribersRepository;
    private CommentsRepository commentsRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository, UsersRepository usersRepository,
                        SubscribersRepository subscribersRepository, CommentsRepository commentsRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
        this.subscribersRepository = subscribersRepository;
        this.commentsRepository = commentsRepository;
    }

    public List<Posts> getAllPosts() {
        return postsRepository.findAllByIsPrivateOrderByDateDesc(0);
    }

    public Posts getPostById(Long id) {
        return postsRepository.findById(id).get();
    }

    public List<Posts> addPost(Posts posts, Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();
        posts.setAuthor(user);
        posts.setDate(LocalDate.now());
        posts.setVisited(0L);
        posts.setLikes(0L);
        posts.setDislikes(0L);

        postsRepository.saveAndFlush(posts);

        return postsRepository.findAllByAuthorAndIsPrivateOrderByDateDesc(user, 0);
    }

    public List<Posts> getMyPosts(Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();

        return postsRepository.findAllByAuthorOrderByDateDesc(user);
    }

    public List<Posts> deletePost(Principal principal, Long id) {
        Optional<Posts> posts = postsRepository.findById(id);
        Users user = usersRepository.findByEmail(principal.getName()).get();
        if (posts.isPresent()) {
            for (Comments comments : commentsRepository.findAllByPost(posts.get())) {
                commentsRepository.delete(comments);
            }
            postsRepository.delete(posts.get());
        }
        return postsRepository.findAllByAuthorAndIsPrivateOrderByDateDesc(user, 0);
    }

    public List<Posts> getPostsByCategory(String category) {
        return postsRepository.findAllByCategoryAndIsPrivateOrderByDateDesc(category, 0);
    }

    public List<Posts> getPostsByTitle(String title) {
        return postsRepository.findAllByTitleContainingAndIsPrivateOrderByDateDesc(title, 0);
    }

    public List<Posts> getPostsByDescription(String description) {
        return postsRepository.findAllByDescriptionContainingAndIsPrivateOrderByDateDesc(description, 0);
    }

    public List<Posts> getPostsByDate(Integer year, Integer month, Integer day) {
        return postsRepository.findAllByDateAndIsPrivateOrderByDateDesc(LocalDate.of(year, month, day), 0);
    }


    public List<Posts> editPost(Posts newPost, Long id) {
        Posts oldPost = postsRepository.findById(id).get();
        newPost.setAuthor(oldPost.getAuthor());
        newPost.setVisited(oldPost.getVisited());
        newPost.setDate(oldPost.getDate());
        newPost.setId(oldPost.getId());
        postsRepository.saveAndFlush(newPost);

        return postsRepository.findAllByAuthorAndIsPrivateOrderByDateDesc(newPost.getAuthor(), 0);

    }

    public Posts viewPost(Long id) {
        Posts post = postsRepository.findById(id).get();
        post.setVisited(post.getVisited() + 1);
        postsRepository.saveAndFlush(post);

        return post;
    }

    public List<Posts> getPostsByAuthor(Long id) {
        Users users = usersRepository.findById(id).get();
        return postsRepository.findAllByAuthorAndIsPrivateOrderByDateDesc(users, 0);
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

    public List<Posts> getPopular() {
        return postsRepository.findTop5ByIsPrivateOrderByVisitedDesc(0);
    }

    public String getAuthorName(Long id) {
        Users users = usersRepository.findById(id).get();
        return "\"" + users.getName() + "\"";
    }

    public List<Posts> findAllByAuthorName(String name) {
        List<Users> usersList = usersRepository.findAllByNameContainingIgnoreCase(name);
        List<Posts> postsList = new ArrayList<>();
        for (Users users : usersList) {
            postsList.addAll(postsRepository.findAllByAuthorAndIsPrivateOrderByDateDesc(users, 0));
        }
        return postsList;
    }
}
