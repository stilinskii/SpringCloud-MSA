package com.appsdeveloperblog.photoapp.api.gateway;

import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    Environment env;

    //super class에게 어떤 config class 써야하는지 알려주기 위해.
    public AuthorizationHeaderFilter(){
        super(Config.class);
    }

    //how to custom filter
    public static class Config{
        // Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        //use Java lambda
        //S.C support reactive programming

        //return Mono
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED); //401
            }

            String authorizationHeader = request.getHeaders().get("Authorization").get(0);
            String jwt = authorizationHeader.replace("Bearer","");

            if(!isJwtValid(jwt)){
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED); //401
            }

            //invoke next filter in chain
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        //TODO err를 어디다가 넣으라는겨
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;

        try {
        subject = Jwts.parser()
                .setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject(); // userId
        }catch (Exception ex){
            returnValue = false;
        }
        if(subject == null || subject.isEmpty()){
            returnValue = false;
        }

        return returnValue;
    }

}
