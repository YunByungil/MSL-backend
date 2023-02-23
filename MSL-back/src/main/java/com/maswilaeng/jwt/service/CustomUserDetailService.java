//package com.maswilaeng.jwt.service;
//
//import com.maswilaeng.domain.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터 베이스에서 찾을수 없습니다."));
//    }
//
//    // DB에 User 값이 존재하면 UserDetails 객체로 만들어서 리턴
//    private UserDetails createUserDetails(com.maswilaeng.domain.entity.User user) {
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
//
//        return new org.springframework.security.core.userdetails.User(
//                String.valueOf(user.getId()),
//                user.getPassword(),
//                Collections.singleton(grantedAuthority));
//    }
//
//}
