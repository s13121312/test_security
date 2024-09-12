package com.example.test_security.dto;

import com.example.test_security.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    //UserDetails를 상속받은 CustomUserDetails는 세션에 저장된다.


    UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });


        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 니 계정 만료되지는 않았니?

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 니 계정 잠겨있지 않니?

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 니 계정 비밀번호 갈아낄때 되지 않았니?

        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년동안 로그인을 안하면 휴면 계정으로 변환하기로 했다면?
        // 현재 시간 - 마지막 로그인 시간으로 계산 => 1년 초과하면 false 로 return.
        // 나머지 비어있는 함수들도 다 비슷하게 구현해주면 된다.

        return true;
    }
}
