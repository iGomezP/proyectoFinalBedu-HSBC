package com.bedu.ProyectoFinalHsbcBedu.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    private static final String SECRET_KEY = "7133743677397A24432646294A404E635266556A586E5A723475377821412544";
    // Duración del JWT (30 días)
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    // Extract userName from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generate Token without claims
    public String generateToken(UserDetails userDetails){return generateToken(new HashMap<>(), userDetails);}

    // Generate Token with claims
    public String generateToken (
            Map<String, Object> extraClaims,
            UserDetails userDetails){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Collection<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        extraClaims.put("authorities", authorities);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract single claim that we pass
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        // extract all keys
        final Claims claims = extractAllClaims(token);
        // extract the claim we want
        return claimsResolver.apply(claims);
    }

    // Extract claims and check if not modified
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
