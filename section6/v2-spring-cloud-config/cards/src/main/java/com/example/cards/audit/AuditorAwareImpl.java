package com.example.cards.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor for audit tracking purposes.
     * <p>Currently returns a hardcoded value "ACCOUNT_MS" as the auditor identifier.
     * This implementation will be enhanced with Spring Security integration to
     * retrieve the actual authenticated user information in future iterations.</p>
     *
     * @return an Optional containing the current auditor's identifier
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("CARDS_MS");
    }
}
