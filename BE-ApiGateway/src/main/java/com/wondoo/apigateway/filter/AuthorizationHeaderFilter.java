package com.wondoo.apigateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Objects;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final SecretKey key;

    public AuthorizationHeaderFilter(@Value("${token.secret}") String secretKey) {
        super(Config.class);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (validateHeader(request)) {
                return responseUnauthorized(exchange);
            }

            String token = getTokenByRequest(request);

            if (validateJwt(token)) {
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
        return authorizationHeader.replace("Bearer", "");
    }

    private boolean validateJwt(String token) {
        String subject = getSubjectByJwt(token);
        return !(subject != null && !subject.isEmpty());
    }

    private void addSocialIdByRequestHeader(ServerHttpRequest request, String token) {
        request.getHeaders().set(WondooHeader.SOCIAL_ID.getKey(), getSubjectByJwt(token));
    }

    private String getSubjectByJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
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
