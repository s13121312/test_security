package com.example.test_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //csrf disable
// csrf 공격자체가 외부 링크에 의해 사용자가 원하지 않는 요청이 발송되어
//피해를 받게 되는데, 외부 링크를 클릭해도 세션 방식과 다르게 Access 토큰이 헤더에 탑재되지 않기 때문에 피해를 막을 수 있다.
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth)-> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login","/","/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()      //그외 요청은 로그인 한 사용자만.
                );

        //세션 설정 jwt방식에서는 session을 stateless방식으로 가짐. 포인트!!
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();

    }
}
