package com.kocak.apigateway.config;
import com.kocak.apigateway.model.AuthenticationTokenDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    public AuthFilter() {
        super(Config.class);
    }
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre Filter. Suppose we can extract JWT and perform Authentication
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            final List<String> openEndpoints = List.of("/api/v1/auth/public/login","/api/v1/auth/public/verify","/api/v1/auth/users/public/register","/api/v1/restaurants/getMenu");
            final Predicate<ServerHttpRequest> isApiSecured = r -> openEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri) || r.getURI().getPath().contains("/public/"));

            if (isApiSecured.test(request)) {
                boolean isAuthorizationHeader = request.getHeaders().containsKey("AUTHORIZATION");
                if (!isAuthorizationHeader) {
                    response.setStatusCode(HttpStatus.OK);
                    return response.setComplete();
                }
                try {
                    final String authHeader = request.getHeaders().get("AUTHORIZATION").get(0);
                    final String[] parts = authHeader.split(" ");
                    if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                        throw new RuntimeException("Incorrect auth structure");
                    }

                    if(!request.getURI().getPath().endsWith("/refresh-token")) {
                        final String authenticationToken = parts[1];
                        Mono<Boolean> result = webClientBuilder.build()
                                .get()
                                .uri("http://localhost:8086/api/v1/auth/public/verify?token=" + authenticationToken)
                                .retrieve()
                                .bodyToMono(Boolean.class);
                        return result.flatMap(resp -> {
                            if (resp) {
                                return chain.filter(exchange);
                            } else {
                                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                return response.setComplete();
                            }
                        });
                    }

                } catch (Exception e) {
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.setComplete();
                }

            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}
/*
@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    @Autowired



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    }

}*/
