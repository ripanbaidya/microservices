package com.eazybytes.gatewayserver.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Optional;

public final class FilterUtil {

    private FilterUtil() {
    }

    public static final String CORRELATION_ID = "eazybank-correlation-id";

    public static String getCorrelationId(HttpHeaders requestHeaders) {
        return Optional.ofNullable(requestHeaders.get(CORRELATION_ID))
                .filter(headers -> !headers.isEmpty())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Set the correlationId
     *
     * @param webExchange current request context
     * @param correlationId correlationId
     * @return new modified exchange
     * @see ServerWebExchange
     */
    public static ServerWebExchange setCorrelationId(ServerWebExchange webExchange,
                                                     String correlationId) {
        return setRequestHeader(webExchange, CORRELATION_ID, correlationId);
    }

    // Helpers

    /**
     * Takes the current request context, add header to it and return a new modified
     * exchange
     *
     * @param serverWebExchange current request context
     * @param name              name of the header
     * @param value             value of the header
     * @return a new modified exchange
     */
    private static ServerWebExchange setRequestHeader(ServerWebExchange serverWebExchange,
                                                      String name, String value) {
        if (name == null || value == null) {
            return serverWebExchange;
        }

        return serverWebExchange.mutate()
                .request(serverWebExchange.getRequest().mutate()
                        .header(name, value)
                        .build())
                .build();
    }
}
