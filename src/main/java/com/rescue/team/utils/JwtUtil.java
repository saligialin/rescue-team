package com.rescue.team.utils;

import com.rescue.team.bean.User;
import com.rescue.team.exception.RedisTokenNullException;
import com.rescue.team.exception.TokenErrorException;
import com.rescue.team.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Long ACCESS_TOKEN_EXPIRATION = 14*3600L*1000;

    private static final Long REFRESH_TOKEN_EXPIRATION = 14*24*3600L*1000;

    @Value("${token.subject}")
    private String subject; //主题

    @Value("${token.issuer}")
    private String issuer;  //签发人

    @Value("${token.secret}")
    private String secret;  //盐

    public String getToken(String userId) {
        Map<String,Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        Map<String,Object> claims = new HashMap<>();
        claims.put("userID",userId);
        return Jwts.builder()
                .setClaims(claims)
                .setHeader(header)
                .setSubject(subject)
                .setIssuer(issuer)
                .setId(UuidUtil.getUUID())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String refreshToken(String userId) {
        Map<String,Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        Map<String,Object> claims = new HashMap<>();
        claims.put("userID",userId);
        return Jwts.builder()
                .setClaims(claims)
                .setHeader(header)
                .setSubject(subject)
                .setIssuer(issuer)
                .setId(UuidUtil.getUUID())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public User getUser(String token, HttpServletRequest request) throws Exception {
        Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String userId= (String) claims.get("userId");
        String redisToken = redisTemplate.opsForValue().get("user-" + userId);
        if(redisToken == null) throw new RedisTokenNullException();
        if(!redisToken.equals(token)) throw new TokenErrorException();
        User user = userService.getUserByUid(userId);
        request.setAttribute("user",user);
        return user;
    }

    public User getUser(String token) throws Exception {
        Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String userId= (String) claims.get("userId");
        User user = userService.getUserByUid(userId);
        return user;
    }

}
