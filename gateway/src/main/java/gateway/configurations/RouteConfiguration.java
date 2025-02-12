package gateway.configurations;

import gateway.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator routeLocator(
            RouteLocatorBuilder route,
            ServiceUrlsProperties props,
            AuthenticationFilter authFilter,
            @Value("${server.api.prefix}") String apiPrefix
    ) {
        return route.routes()
                .route(props.getUser() + "-route-auth", r -> r
                                .path(apiPrefix + "/accounts/users/**")
                                .filters(f -> f
                                                .stripPrefix(1)
//                                .circuitBreaker(c -> c
//                                        .setName(props.getUser() + "-circuit-breaker")
//                                        .setFallbackUri(URI.create("forward:/fallback/users"))
//                                )
                                )
                                .uri("lb://" + props.getUser())
                )
                .route(props.getFeed() + "-route-feed", r -> r
                                .path(apiPrefix + "/feed/**")
                                .filters(f -> f
                                                .stripPrefix(1)
//                                .circuitBreaker(cb -> cb
//                                        .setName(props.getFeed() + "-circuit-breaker")
//                                        .setFallbackUri(URI.create("forward:/fallback/feed"))
//                                )
//                                 .filter(authFilter.apply(new AuthenticationFilter.Config()))
                                )
                                .uri("lb://" + props.getFeed())
                )
                .route(props.getMessenger() + "-route-messenger", r -> r
                        .path(apiPrefix + "/chats/**")
                        .filters(f -> f
                                .stripPrefix(1)
//                                .circuitBreaker(cb -> cb
//                                        .setName(props.getMessenger() + "-circuit-breaker")
//                                        .setFallbackUri(URI.create("forward:/fallback"))
//                                )
//                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getMessenger())
                )
                .route(props.getSubs() + "-route-sub", r -> r
                        .path(apiPrefix + "/subscribe/**")
                        .filters(f -> f
                                .stripPrefix(1)
//                                .circuitBreaker(cb -> cb
//                                        .setName(props.getSubs() + "-circuit-breaker")
//                                        .setFallbackUri(URI.create("forward:/fallback/subs"))
//                                )
//                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getSubs())
                )
                .build();
    }
}