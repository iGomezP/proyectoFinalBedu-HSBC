package com.bedu.proyectofinalhsbcbedu.security;

import com.bedu.proyectofinalhsbcbedu.exceptions.CustomProductException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
@Slf4j
public class JwtUtils {


    private static final String SECRET_KEY = "7133743677397A24432646294A404E635266556A586E5A723475377821412544";
    // Duración del JWT (30 días)
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    // Extract userName from token
    public String extractUsername(String token) throws CustomProductException {
        validateTokenSignature(token);
        return extractClaim(token, Claims::getSubject);
    }


    private void validateTokenSignature(String token) throws CustomProductException {
        // Preparar el token
        String[] partesJwt = token.split("\\.");
        String tokenWithoutSignature = partesJwt[0] + "." + partesJwt[1];
        String signatureFromJwt = partesJwt[2];
        Key secret = getSigningKey();

        // Check sign
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(HS256, secret);
        if (!validator.isValid(tokenWithoutSignature, signatureFromJwt))
            throw new CustomProductException("No se pudo verificar la integridad del token");
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
    public boolean isTokenValid(String token, UserDetails userDetails) throws CustomProductException {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && isRoleValid(token, userDetails);
    }

    // Validate Role
    // Suponiendo que en un futuro un usuario tenga más de un rol se mantiene las listas
    private boolean isRoleValid(String token, UserDetails userDetails){
        // Get claims
        Claims claims = extractAllClaims(token);
        // Get rol from jwt claims
        List<String> authorities = (List<String>) claims.get("authorities");

        // Get rol from user and add to new list
        Collection<? extends GrantedAuthority> userDetailAuthorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : userDetailAuthorities){
            String authorityName = authority.getAuthority();
            roles.add(authorityName);
        }

        // Compare rol
        if (!authorities.get(0).equals(roles.get(0))){
            log.error("Los roles no coinciden");
            return false;
        }
        return true;
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
