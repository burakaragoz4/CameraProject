package com.camera.API.core.utilities.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.camera.API.entities.concretes.User;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private Long expirationTime;

	@jakarta.annotation.Resource
	private RedisTemplate<String, String> redisTemplate;

	public String generateToken(User user) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationTime);

		SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));

		String token = Jwts.builder().setSubject(user.getName()).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(key).compact();

		String previousToken = redisTemplate.opsForValue().get("user:token:" + user.getName());
		if (previousToken != null) {
			invalidateToken(previousToken);
		}

		redisTemplate.opsForValue().set("user:token:" + user.getName(), token);
		return token;
	}

	public boolean isTokenValidForUser(String token, String userName) {
		String storedToken = redisTemplate.opsForValue().get("user:token:" + userName);
		return token.equals(storedToken);
	}

	public boolean validateToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public String getUsernameFromToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return claims.getSubject();
		} catch (Exception e) {
			return "Tokenın Süresi Geçmiş";
		}
	}

	public Long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public void invalidateToken(String token) {
		getUsernameFromToken(token);
		System.out.println(1);
		redisTemplate.delete("user:token:" + getUsernameFromToken(token));
	}
}
