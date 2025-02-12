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
//                                        .setFallbackUri(URI.create("forward:/fallback"))
//                                )
                        )
                        .uri("lb://" + props.getUser())
                )
                .route(props.getNews() + "-route-users", r -> r
                        .path(apiPrefix + "/news/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(c -> c
                                        .setName(props.getNews() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getNews())
                )
                .route(props.getFeed() + "-route-feed", r -> r
                        .path(apiPrefix + "/feed/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(cb -> cb
                                        .setName(props.getFeed() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                 .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getFeed())
                )
                .route(props.getMessenger() + "-route-messenger", r -> r
                        .path(apiPrefix + "/chats/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(cb -> cb
                                        .setName(props.getMessenger() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getMessenger())
                )
                .route(props.getSubscription() + "-route-sub", r -> r
                        .path(apiPrefix + "/subscribe/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(cb -> cb
                                        .setName(props.getSubscription() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getSubscription())
                )
                .build();
    }
}