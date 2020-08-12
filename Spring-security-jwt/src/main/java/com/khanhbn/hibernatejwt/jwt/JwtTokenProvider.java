package com.khanhbn.hibernatejwt.jwt;

import com.khanhbn.hibernatejwt.user.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "mms";

    // Thời gian có hiệu lực của chuỗi JWT
    private final Long JWT_Expiration = 604800000L;

    // Tạo ra token từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("id", userDetails.getUser().getUser_id());
        map.put("username", userDetails.getUser().getUsername());
        map.put("password", userDetails.getUser().getPassword());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + JWT_Expiration);
        return Jwts.builder()
                .setClaims(map)
                .setSubject(Long.toString(userDetails.getUser().getUser_id()))
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes())
                .compact();
    }

    // Lấy thông tin User Id từ JWT
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // validate thông tin token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }


}
