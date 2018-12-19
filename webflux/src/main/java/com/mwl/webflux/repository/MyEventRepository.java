package com.mwl.webflux.repository;

import com.mwl.webflux.bean.MyEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long> {
    //无限流
    @Tailable
    Flux<MyEvent> findBy();
}
