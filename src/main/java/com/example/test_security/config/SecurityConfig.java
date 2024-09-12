package com.example.test_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }


    //스프링 시큐리티 3.1.X 버전 부터는 람다식으로 작성해줘야함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/loginProc","/join","/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
//스프링 시큐리티 Config 클래스 설정 후 특정 경로에 대한 접근 권한이 없는 경우
// 자동으로 로그인 페이지로 리다이렉팅 되지 않고 오류 페이지가 발생한다. 위의 admin 페이지를 따로 처리해주어야함
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());

        return http.build();

    }

}
