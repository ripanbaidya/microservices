package com.eazybytes.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * This file contains the configuration for the api-gateway.
 * The gateway is the entry point for all requests to the application.
 */
@Configuration
public class GatewayConfig {

    private static final String X_RESPONSE_TIME = "X-Response-Time";

    /**
     * Defining our own custom route
     * This method configures the API Gateway routes for the microservices.
     * It sets up routing rules for accounts, loans, and cards services with path rewriting
     * and adds custom headers for request tracking.
     *
     * @param locatorBuilder RouteLocatorBuilder instance used to build routes
     * @return RouteLocator configured with custom routing rules for microservices
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder.routes()

                /*
                 * path - It decides whether a route should be applied to an incoming request.
                 * Think this as a condition/matcher
                 *
                 * filters - It's a filter chain that can modify the incoming request before sending
                 * to the downstream service or modify the response before returning to the client.
                 * eg: [client] --> [filters] --> microservice --> [filters] --> client
                 *
                 * rewritePath - It rewrites the path of the incoming request before forwarding it to
                 * the downstream service.
                 * eg: with 'accounts' microservice
                 *  - Regex captures everything after /accounts/
                 *  - (?<segment>.*) → named capturing group
                 *  - ${segment} → reuse captured value
                 * Now: /eazybank/accounts/getAll  --> /getAll
                 *
                 * addRequestHeader - It adds a custom header to the request before forwarding it to the
                 * downstream service.
                 * eg: Every request forwarded to ACCOUNTS service will include: X-Response-Time: 2026-04-03T12:34:56
                 *
                 * uri - It specifies the destination URI for the route. The 'lb://ACCOUNTS' prefix indicates that
                 * the URI should be resolved using a load balancer, allowing for dynamic routing.
                 * and then the name of the respected Microservice defined in eureka dashboard.
                 *
                 */

                /*
                 * accounts
                 */
                .route(p -> p
                        .path("/eazybank/accounts/**")
                        .filters(f -> f
                                .rewritePath("/eazybank/accounts/(?<segment>.*)", "/${segment}")
                                .addRequestHeader(X_RESPONSE_TIME, LocalDateTime.now().toString())
                        )
                        .uri("lb://ACCOUNTS")
                )

                /*
                 * loans
                 */
                .route(p -> p
                        .path("/eazybank/loans/**")
                        .filters(f -> f
                                .rewritePath("/eazybank/loans/(?<segment>.*)", "/${segment}")
                                .addRequestHeader(X_RESPONSE_TIME, LocalDateTime.now().toString())
                        )
                        .uri("lb://LOANS")
                )

                /*
                 * cards
                 */
                .route(p -> p
                        .path("/eazybank/cards/**")
                        .filters(f -> f
                                .rewritePath("/eazybank/cards/(?<segment>.*)", "/${segment}")
                                .addRequestHeader(X_RESPONSE_TIME, LocalDateTime.now().toString())
                        )
                        .uri("lb://CARDS")
                )
                .build();
    }
}
