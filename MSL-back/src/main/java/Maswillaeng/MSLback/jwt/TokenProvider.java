package Maswillaeng.MSLback.jwt;

import Maswillaeng.MSLback.domain.entity.RoleType;
import Maswillaeng.MSLback.domain.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider{

    @Value("${secret.access}")
    private String SECRET_KEY;// = "sec";
    @Value("${secret.refresh}")
    private String REFRESH_KEY;// = "ref";

    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 1000L;   // 1시간
//    private final long ACCESS_TOKEN_VALID_TIME = 1000L;   // 1시간
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 1000L;   // 하루
    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
    }


    public Claims getClaimsFormToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build().parseClaimsJws(token)
                .getBody();
    }

    public Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_KEY.getBytes())
                .build().parseClaimsJws(token)
                .getBody();

    }
    //권한정보를 이용해서 토큰을 생성하는 메소드
    public String createAccessToken(User user) {

        Claims claims = Jwts.claims(); // JWT payload 에 저장되는 정보단위
        claims.put("userId", user.getId());
        claims.put("role", user.getRole()); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    public String createRefreshToken(User user) {

        Claims claims = Jwts.claims(); // JWT payload 에 저장되는 정보단위
        claims.put("userId", user.getId()); // 정보는 key / value 쌍으로 저장된다. 저장할지말지
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(Keys.hmacShaKeyFor(REFRESH_KEY.getBytes()), SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과
                .compact();
    }

    public boolean isValidAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build().parseClaimsJws(token)
                    .getBody();

            log.info("expireTime: " + claims.getExpiration());
            log.info("userId: " + claims.get("userId"));
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            return false;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            return false;
        } catch (NullPointerException exception) {
            log.info("Token is null");
            return false;
        }
    }
    public boolean isValidRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(REFRESH_KEY.getBytes())
                    .build().parseClaimsJws(token)
                    .getBody();
            log.info("expireTime: " + claims.getExpiration());
            log.info("userId: " + claims.get("userId"));
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT refresh 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (NullPointerException exception) {
            log.info("Token is null");
        }
        return false;
    }
}