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

        // "/login" 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행.
        // 결과적으로 컨트롤러에 따로 "/login"을 구현하지 않아도 괜찮다.
        // 이 로그인 과정에서 필요한 것이 있기 때문에 auth 패키지를 파서 CustomUserDetails 을 만들어줘야한다.
        http
                .formLogin((auth) -> {
                            auth.loginPage("/login")//커스텀 로그인 페이지 지정
                                    .loginProcessingUrl("/loginProc")//SecurityConfig에서 loginProcessingUrl() 메서드를 작성해주면 해당 Url로 요청이 될 시 SpringSecurity가 직접 알아서 로그인 과정을 진행해준다.
                                    //위의 /loginProc 에 대한 요청이 Spring MVC와 컨트롤러에 전달되지 않는다.
                                    .permitAll();

                });

        http
                .csrf((auth) -> auth.disable());

        return http.build();

    }

}
