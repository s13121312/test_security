package com.example.test_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
                .httpBasic(Customizer.withDefaults());


//maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
//
//maxSessionPreventsLogin(불린) : 다중 로그인 개수를 초과하였을 경우 처리 방법
//true : 초과시 새로운 로그인 차단
//false : 초과시 기존 세션 하나 삭제
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));

        //세션 고정 공격을 보호하기 위한 로그인 성공시 세션 설정 방법은 sessionManagement() 메소드의 sessionFixation() 메소드를 통해서 설정할 수 있다.
        //- sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
        //- sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
        //- sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경← 주로씀
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());



        return http.build();

    }

}
