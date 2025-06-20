package com.ambrosezen.weblearnenglish.services.impl;

import com.ambrosezen.weblearnenglish.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {
  public String generateToken(UserDetails userDetails) {
    //List role
  /*Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
  List<String> roles = new ArrayList<>();
  for (GrantedAuthority authority : authorities) {
    roles.add(authority.getAuthority());
  }*/

    // Role duy nhất
    String role = userDetails.getAuthorities().iterator().next().getAuthority();

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("roles", role)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    String role = userDetails.getAuthorities().iterator().next().getAuthority();
    return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
        .claim("roles", role)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 604800000))
        .signWith(getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUserName(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  public String extractRoles(String token) {
    return extractClaims(token, claims -> claims.get("roles", String.class));
  }

  private <T> T extractClaims(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply((claims));
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
  }

  private Key getSigninKey() {
    byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
    return Keys.hmacShaKeyFor(key);
  }


  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractClaims(token, Claims::getExpiration).before(new Date());
  }
}
