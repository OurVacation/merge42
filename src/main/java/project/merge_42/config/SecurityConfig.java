package project.merge_42.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF(Cross-Site Request Forgery) 보호 비활성화
                //    API 서버로 사용할 것이므로, 일반적으로 비활성화함 (특히 Postman 테스트 시)
                .csrf(csrf -> csrf.disable())

                // 2. 요청별 인가(Authorization) 설정
                .authorizeHttpRequests(auth -> auth
                        // "/api/**" 패턴으로 시작하는 모든 요청은 인증 없이 허용
                        .requestMatchers("/api/**").permitAll()
                        // 그 외의 모든 요청은 인증을 요구함
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
