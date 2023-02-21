package Maswillaeng.MSLback.jwt;

import Maswillaeng.MSLback.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    @Value("${secret.access")
    private String secretKey;

    public final long ACCESS_TOKEN_VALID_TIME = 1000 * 60 * 30; // 30분
    public final long REFRESH_TOKEN_VALID_SECONDS = 1000 * 60 * 60 * 48; // 48시간

    @Override
    public void afterPropertiesSet() throws Exception {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(User user) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // 끝나는 날짜
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화
                .compact();
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getUserId()); //
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_SECONDS);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims getClaims(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims;
    }

    public Long getUserId(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.parseLong(String.valueOf(claims.get("userId")));
    }

    public boolean isValidToken(String token) {
        Claims accessClaims =getClaims(token);
        return true;
    }

}
