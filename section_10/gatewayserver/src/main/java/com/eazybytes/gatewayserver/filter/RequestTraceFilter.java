package com.eazybytes.gatewayserver.filter;

import com.eazybytes.gatewayserver.util.FilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(requestHeaders)) {
            log.debug("eazyBank-correlation-id found in RequestTraceFilter : {}",
                FilterUtil.getCorrelationId(requestHeaders)
            );
        } else {
            String correlationID = generateCorrelationId();
            exchange = FilterUtil.setCorrelationId(exchange, correlationID);
            log.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}", correlationID);
        }

        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return FilterUtil.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

}