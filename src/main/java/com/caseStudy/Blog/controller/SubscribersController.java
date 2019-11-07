package com.caseStudy.Blog.controller;

import com.caseStudy.Blog.model.Subscribers;
import com.caseStudy.Blog.service.SubscribersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("subscriber")
public class SubscribersController {
    private SubscribersService subscribersService;

    @Autowired
    public SubscribersController(SubscribersService subscribersService) {
        this.subscribersService = subscribersService;
    }

    @GetMapping(value = "get-subscribers", produces = "application/json")
    public List<Subscribers> getSubscribers(Principal principal) {
        return subscribersService.getSubscribers(principal);
    }

    @PostMapping(value = "subscribe", produces = "application/json")
    public List<Subscribers> subscribe(Principal principal, @RequestParam Long id) {
        return subscribersService.subscribe(principal, id);
    }

    @PostMapping(value = "unsubscribe", produces = "application/json")
    public List<Subscribers> unsubscribe(Principal principal, @RequestParam Long id) {
        return subscribersService.unsubscribe(principal, id);
    }

    @GetMapping(value = "subscription", produces = "application/json")
    public List<Subscribers> subscription(Principal principal) {
        return subscribersService.subscriptions(principal);
    }
}
