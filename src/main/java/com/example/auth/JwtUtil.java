package com.example.auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET  = "MySuperSecretKeyForJWTThatIsAtLeast256Bits!";
    private static long EXPIRATION = 1000*60*60*2;
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
    //生成令牌，附带用户名和角色
    public String generateToken(String username,String role){
        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    //从令牌中提取用户名
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    //提取角色
    public String extractRole(String token){
        return (String) getClaims(token).get("role");
    }

    //校验令牌是否有效（未过期、签名正确）
    public boolean isTokenValid(String token){
        try {
            getClaims(token);
            return true;
        }
        catch (JwtException|IllegalArgumentException e){
            return false;
        }
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())  // 设置签名密钥
                .build()
                .parseSignedClaims(token) // 解析
                .getPayload();

    }






























}
