package ru.tkachenko.buyerassistant.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.tkachenko.buyerassistant.security.service.UserDetailsImpl;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${tkachenko.buyerassistant.jwtSecret}")
    private String jwtSecret;

    @Value("${tkachenko.buyerassistant.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature");
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");
            e.printStackTrace();
        }

        return false;
    }
}
