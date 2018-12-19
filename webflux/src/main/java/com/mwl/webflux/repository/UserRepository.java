package com.mwl.webflux.repository;

import com.mwl.webflux.bean.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUsername(String username);     // 2

    Mono<Long> deleteByUsername(String username);
}
