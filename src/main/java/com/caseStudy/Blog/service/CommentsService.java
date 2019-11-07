package com.caseStudy.Blog.service;

import com.caseStudy.Blog.model.Comments;
import com.caseStudy.Blog.model.Posts;
import com.caseStudy.Blog.model.Users;
import com.caseStudy.Blog.repository.CommentsRepository;
import com.caseStudy.Blog.repository.PostsRepository;
import com.caseStudy.Blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CommentsService {
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(PostsRepository postsRepository, UsersRepository usersRepository,
                           CommentsRepository commentsRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
        this.commentsRepository = commentsRepository;
    }

    public List<Comments> viewComments(Long id) {
        Posts post = postsRepository.findById(id).get();

        return commentsRepository.findAllByPost(post);
    }

    public List<Comments> createComment(Comments comment, Principal principal, Long id) {
        Posts post = postsRepository.findById(id).get();
        Users user = usersRepository.findByEmail(principal.getName()).get();

        comment.setDate(LocalDate.now());
        comment.setPost(post);
        comment.setUser(user);
        comment.setLikes(0L);

        commentsRepository.saveAndFlush(comment);
        return commentsRepository.findAllByPost(post);
    }

    public List<Comments> deleteComment(Principal principal, Long postId, Long commentId) {
        Posts post = postsRepository.findById(postId).get();
        Users user = usersRepository.findByEmail(principal.getName()).get();
        Comments comment = commentsRepository.findById(commentId).get();

        commentsRepository.delete(comment);
        return commentsRepository.findAllByPost(post);
    }

    public List<Comments> like(Long commentId) {
        Comments comment = commentsRepository.findById(commentId).get();
        Posts post = comment.getPost();

        comment.setLikes(comment.getLikes() + 1);
        commentsRepository.saveAndFlush(comment);

        return commentsRepository.findAllByPost(post);
    }

    public List<Comments> unLike(Long commentId) {
        Comments comment = commentsRepository.findById(commentId).get();
        Posts post = comment.getPost();

        comment.setLikes(comment.getLikes() - 1);
        commentsRepository.saveAndFlush(comment);

        return commentsRepository.findAllByPost(post);
    }

}
