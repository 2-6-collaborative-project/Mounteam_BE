package com.example.mountain.oauth.jwt;

import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.global.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret-key}")
    private String jwtSecret;
    private final UserRepository userRepository;

    // JWT 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰에서 유저 정보 추출
    private UserDetails getUserDetails(String token) {
        Long userId = Long.valueOf(getUserPk(token));
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
        return new CustomUserDetails(user);
    }

    // JWT 토큰에서 유저 PK 추출
    private String getUserPk(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT 토큰 생성
    public String generate(String subject, Date expiryTimeMillis) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiryTimeMillis)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    public void validToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
