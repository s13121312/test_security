package com.example.test_security.service;

import com.example.test_security.dto.CustomUserDetails;
import com.example.test_security.entity.UserEntity;
import com.example.test_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userdata = userRepository.findByUsername(username);

        if (userdata != null){
            return new CustomUserDetails(userdata);
        }
        return null;
    }
}
