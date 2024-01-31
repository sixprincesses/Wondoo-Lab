package com.wondoo.apigateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WondooRouteLocator {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder build) {
        return build.routes()
                .route("article-service", r -> r.path("/article-service")
                        .uri("lb://ARTICLE-SERVICE"))
                .route("member-service", r -> r.path("/member-service")
                        .uri("lb://MEMBER-SERVICE"))
                .route("message-service", r -> r.path("/message-service")
                        .uri("lb://MESSAGE-SERVICE"))
                .route("notification-service", r -> r.path("/notification-service")
                        .uri("lb://NOTIFICATION-SERVICE"))
                .route("storage-service", r -> r.path("/storage-service")
                        .uri("lb://STORAGE-SERVICE"))
                .build();
    }
}
