package com.wondoo.apigateway.filter;

import com.wondoo.apigateway.utils.JwtUtils;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;


@RequiredArgsConstructor
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JwtUtils jwtUtils;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (validateHeader(request)) {
                return responseUnauthorized(exchange);
            }

            String token = getTokenByRequest(request);

            if (jwtUtils.validateJwt(token)) {
                return responseUnauthorized(exchange);
            }

            addSocialIdByRequestHeader(exchange.getRequest(), token);

            return chain.filter(exchange);
        });
    }

    private boolean validateHeader(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private Mono<Void> responseUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @NotNull
    private String getTokenByRequest(ServerHttpRequest request) {
        String authorizationHeader = Objects.requireNonNull(request.getHeaders()
                        .get(HttpHeaders.AUTHORIZATION))
                .get(0);
        return authorizationHeader.replace("Bearer", "").trim();
    }

    private void addSocialIdByRequestHeader(ServerHttpRequest request, String token) {
        request.getHeaders().set(WondooHeader.SOCIAL_ID.getKey(), jwtUtils.getSubjectByJwt(token));
    }

    @Getter
    public enum WondooHeader {
        SOCIAL_ID("social_id");

        private final String key;

        WondooHeader(String key) {
            this.key = key;
        }
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
