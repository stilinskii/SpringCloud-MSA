package com.appsdeveloperblog.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyPostFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(MyPostFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //post는 리턴타입을 이런식으로 해줘야함..
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            logger.info("Global Post-filter executed...");
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
