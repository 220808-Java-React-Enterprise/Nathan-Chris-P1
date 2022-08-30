package com.revature.services;

import com.revature.models.User;
import com.revature.utils.JwtConfig;
import com.revature.utils.custom_exceptions.AuthenticationException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenService {
    private static JwtConfig jwtConfig = new JwtConfig();

    public TokenService() {
        super();
    }

    public TokenService(JwtConfig jwtConfig) {
        TokenService.jwtConfig = jwtConfig;
    }

    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(username)
                .setIssuer("nathan-chris-p1")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .setSubject(username)
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return tokenBuilder.compact();
    }

    public static User extractRequesterDetails(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if((token == null) || (token.isEmpty())){
            throw new AuthenticationException("No Authorization token provide.");
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return UserService.getUserByUsername(claims.getSubject());
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}