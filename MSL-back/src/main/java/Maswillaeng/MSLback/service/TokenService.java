package Maswillaeng.MSLback.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private String SECRET_KEY = "maswilleang jwt scrt key its me";

    public void setTokenCookies(HttpServletResponse response, String name, String token, int maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    //리펙토링 필요함 유효시간만 변경됨으로..
    public String generateToken(Long userId, Date expiration){
        //토큰에 정보 입력
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);

        Date now = new Date();
        claims.put("exp", expiration.getTime());

         return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                    .compact();
    }

    //유효 서명 확인 후 id 반환
    public Long parseJwt(String token){
        Claims claims =  Jwts.parser()
                            .setSigningKey(SECRET_KEY.getBytes())
                            .parseClaimsJws(token)
                            .getBody();
        Long id = Long.valueOf(claims.get("user_id").toString());
        return id;
    }
}

