package id.my.hendisantika.oauth2login.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-oauth2-login
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 19/03/25
 * Time: 05.25
 * To change this template use File | Settings | File Templates.
 */
@Component
public class JwtUtil {
    private final String SECRET_KEY = "631c783daa4b112d22b451f9ff14aabc69d096f0a21095526c8abbf95720d2cfcf90dbf452e21239cc1b63c43644d3d19027b837cbe2ed2569ed523d67cf8010cac4c17a05c2d33f1024738e1dc6ca654370f8019b5c91fdb66ef796ebba7e7158d4e101392e0d5a99bd09f3a402e477053a044861a227f3f24a4708b4ec2b85e3447aebb0b4ed02b7861f78545c87e118b992cd63889e368d3c41f74ba1a6cc35fc726bbd94cf1a3f43d23e2ed9fbef0a46de9926d94e83ab795d6ab28bdbf022b0d3ba4eac49d9bdcec3ac636110965d2eccefd98df38b86396cc6d3f3c40ec04b6f673fa16c3fb8ff9a60459ed1b17c88bf2b429ffbe75695d3fb30d43238"; // Use environment variables in production

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
