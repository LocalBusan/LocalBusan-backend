package com.LocalBusan.LocalBusan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 스프링 시큐리티 설정 가능
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()); // csrf 보안 해제 (csrf : 외부에서 폼 요청 방지 하는기능)
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() // 모든 url 로그인 검사 해제
        );
        return http.build();
    }
}