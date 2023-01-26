package Maswillaeng.MSLback.jwt;

import Maswillaeng.MSLback.domain.entity.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {
    @Value("${secret.access}")
    private String SECRET_KEY;// = "sec";
    @Value("${secret.refresh}")
    private String REFRESH_KEY;// = "ref";

    private final long ACCESS_TOKEN_VALID_TIME = 300*1000;
    private final long REFRESH_TOKEN_VALID_TIME = 500*1000;


    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
    }


    public String createAccessToken(User user) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        //claims.put("email", user.getEmail());
        claims.put("userId", user.getUser_id());
//       claims.put("nickName", user.getNickName());
        claims.put("roles", user.getRole());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME *1000)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getEmail()); //
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME*1000);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, REFRESH_KEY)
                .compact();
    }


    public Claims getAccessClaims(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims;
    }

    public Claims getRefreshClaims(String token){
        Claims claims = Jwts.parser().setSigningKey(REFRESH_KEY).parseClaimsJws(token).getBody();
        return claims;
    }
    public boolean isValidAccessToken(String token) {
        try {
            Claims accessClaims = getAccessClaims(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userNickName: " + accessClaims.get("nickName"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("ACCESS Token Expired userEmail : " + exception.getClaims().get("email"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    public boolean isValidRefreshToken(String token) {
        try {
            Claims accessClaims = getRefreshClaims(token);
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("REFRESH Token Expired userNickName : " + exception.getClaims().get("nickName"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }
}
