package com.caseStudy.Blog.repository;

import com.caseStudy.Blog.model.Subscribers;
import com.caseStudy.Blog.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribersRepository extends JpaRepository<Subscribers, Long> {
    List<Subscribers> findAllByAuthor(Users author);

    List<Subscribers> findAllBySubscriber(Users subscriber);

    Optional<Subscribers> findBySubscriberAndAuthor(Users subscriber, Users Author);
}
