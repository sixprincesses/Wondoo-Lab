package com.wondoo.apigateway.route;

import com.wondoo.apigateway.filter.AuthorizationHeaderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WondooRouteLocator {

    private final AuthorizationHeaderFilter authorizationHeaderFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder build) {

        return build.routes()
                .route("article-service", r -> r.path("/article-service/**")
                        .uri("lb://ARTICLE-SERVICE"))
                .route("member-service", r -> r.path("/member-service/**")
                        .uri("lb://MEMBER-SERVICE"))
                .route("article-service", r -> r.path("/article-service/auth/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://ARTICLE-SERVICE"))
                .route("member-service", r -> r.path("/member-service/auth/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://MEMBER-SERVICE"))
                .route("message-service", r -> r.path("/message-service/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://MESSAGE-SERVICE"))
                .route("notification-service", r -> r.path("/notification-service/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://NOTIFICATION-SERVICE"))
                .route("storage-service", r -> r.path("/storage-service/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://STORAGE-SERVICE"))
                .build();
    }
}
