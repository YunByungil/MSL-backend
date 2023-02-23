package Maswillaeng.MSLback.auth;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("\"loadUserByUsername \" = " + "loadUserByUsername 진입 완료");
        System.out.println("username = " + username);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다!"));
        System.out.println("user정보를 받아보자! loadUserByUsername 메서드임 = " + user);
        return new PrincipalDetails(user);
    }
}
