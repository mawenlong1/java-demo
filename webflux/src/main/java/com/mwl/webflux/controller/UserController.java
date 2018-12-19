package com.mwl.webflux.controller;

import com.mwl.webflux.bean.User;
import com.mwl.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author mawenlong
 * @date 2018/12/19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public Mono<User> save(User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
    /**
     * 为了与/times类似，我们也加一个MediaType， 这里返回的是JSON，因此不能使用TEXT_EVENT_STREAM，
     * 而是使用APPLICATION_STREAM_JSON，即application/stream+json格式
     */
    @GetMapping(value = "",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        return userService.findAll().delayElements(Duration.ofSeconds(1));
    }
}
