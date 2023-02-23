package com.bedu.ProyectoFinalHsbcBedu.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class JwtUtils {

    private static final String SECRET_KEY = "7133743677397A24432646294A404E635266556A586E5A723475377821412544";
    // Duración del JWT (30 días)
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    // Extract userName from token
    public String extractUsername(String token) throws Exception {
        boolean signatureCheck = validatetokenSignature(token);
        if (!signatureCheck){
            return null;
        }

        return extractClaim(token, Claims::getSubject);
    }

    private boolean validatetokenSignature(String token) throws Exception {
        // Preparar el token
        String[] partesJwt = token.split("\\.");
        String tokenWithoutSignature = partesJwt[0] + "." + partesJwt[1];
        String signatureFromJwt = partesJwt[2];
        Key secret = getSigningKey();

        // Check sign
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(HS256, secret);
        if (!validator.isValid(tokenWithoutSignature, signatureFromJwt)){
            throw new Exception("No se pudo verificar la integridad del token");
        }
        return true;
    }

    // Generate Token without claims
    public static String generateToken(UserDetails userDetails){return generateToken(new HashMap<>(), userDetails);}

    // Generate Token with claims
    public static String generateToken (
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
                .signWith(getSigningKey(), HS256)
                .compact();
    }

    // Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) throws Exception {
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

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
