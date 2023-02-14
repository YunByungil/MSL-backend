package Maswillaeng.MSLback.utils;

import Maswillaeng.MSLback.domain.entity.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String createJwt(Long userId, RoleType roleType, String secretKey, Long accessTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("role", roleType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String createRefreshJwt(String secretKey, Long refreshTime) {
        Claims claims = Jwts.claims();

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    /**
     * 유효 기간을 체크하는 메서드
     * @param token Bearer를 제외한 토큰 정보
     * @param secretKey
     */
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    /**
     * 로그인 시, 회원 id를 알 수 있는 메서드
     * @param token 토큰 정보
     * @param secretKey
     */
    public static Long getUserId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId", Long.class);
    }
}
