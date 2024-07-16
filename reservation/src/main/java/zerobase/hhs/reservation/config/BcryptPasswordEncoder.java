package zerobase.hhs.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BcryptPasswordEncoder {
    @Bean
    public BcryptPasswordEncoder passwordEncoder() {
        return new BcryptPasswordEncoder();
    }
}
