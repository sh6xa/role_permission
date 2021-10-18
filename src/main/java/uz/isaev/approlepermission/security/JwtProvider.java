package uz.isaev.approlepermission.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.isaev.approlepermission.entity.Role;

import java.util.Date;

@Component
public class JwtProvider {
    static long expireTime = 36_000_000;
    static String secKey = "BuTokenniMaxfiySuziHechKimBilmasin1234567890";

    public String generateToken(String username, Role role) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secKey)
                .claim("role", role.getName())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
