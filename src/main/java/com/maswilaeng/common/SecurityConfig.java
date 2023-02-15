//package com.maswilaeng.common;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.servlet.Filter;
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig implements SecurityFilterChain {
//
//    private final OAuth2UserService oAuth2UserService;
//
//    @Override
//    public boolean matches(HttpServletRequest request) {
//        return false;
//    }
//
//    @Override
//    public List<Filter> getFilters() {
//        return null;
//    }
//}
