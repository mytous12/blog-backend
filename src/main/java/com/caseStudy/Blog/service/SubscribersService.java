package com.caseStudy.Blog.service;

import com.caseStudy.Blog.model.Subscribers;
import com.caseStudy.Blog.model.Users;
import com.caseStudy.Blog.repository.SubscribersRepository;
import com.caseStudy.Blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class SubscribersService {
    private SubscribersRepository subscribersRepository;
    private UsersRepository usersRepository;

    @Autowired
    public SubscribersService(SubscribersRepository subscribersRepository, UsersRepository usersRepository) {
        this.subscribersRepository = subscribersRepository;
        this.usersRepository = usersRepository;
    }

    public List<Subscribers> getSubscribers(Principal principal) {
        Users author = usersRepository.findByEmail(principal.getName()).get();
        return subscribersRepository.findAllByAuthor(author);
    }

    public List<Subscribers> subscribe(Principal principal, Long id) {
        Users subscriber = usersRepository.findByEmail(principal.getName()).get();
        Users author = usersRepository.findById(id).get();
        Optional<Subscribers> optSubscribe = subscribersRepository.findBySubscriberAndAuthor(subscriber, author);
        Subscribers subscribe;
        if (!optSubscribe.isPresent() && !subscriber.equals(author)) {
            subscribe = new Subscribers();
            subscribe.setAuthor(author);
            subscribe.setSubscriber(subscriber);
            author.setSubscribers(author.getSubscribers() + 1);
            subscribersRepository.saveAndFlush(subscribe);
        }
        return subscribersRepository.findAllBySubscriber(subscriber);
    }

    public List<Subscribers> unsubscribe(Principal principal, Long id) {
        Users subscriber = usersRepository.findByEmail(principal.getName()).get();
        Users author = usersRepository.findById(id).get();
        Optional<Subscribers> optSubscribe = subscribersRepository.findBySubscriberAndAuthor(subscriber, author);
        author.setSubscribers(author.getSubscribers() - 1);
        optSubscribe.ifPresent(subscribers -> subscribersRepository.delete(subscribers));
        return subscribersRepository.findAllBySubscriber(subscriber);
    }

    public List<Subscribers> subscriptions(Principal principal) {
        Users user = usersRepository.findByEmail(principal.getName()).get();
        return subscribersRepository.findAllBySubscriber(user);
    }

    public Boolean isSubsScribed(Principal principal, Long id) {
        Users subscriber = usersRepository.findByEmail(principal.getName()).get();
        Users author = usersRepository.findById(id).get();
        return subscribersRepository.existsBySubscriberAndAuthor(subscriber, author);
    }
}
