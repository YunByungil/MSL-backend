package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

//    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = this.userRepository.findByEmail(email)
//                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을수 없습니다."));
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if ("ADMIN".equals(user.getRole())) {
//            authorities.add(new SimpleGrantedAuthority(RoleType.ADMIN.toString()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(RoleType.USER.toString()));
//        }
//    }
}