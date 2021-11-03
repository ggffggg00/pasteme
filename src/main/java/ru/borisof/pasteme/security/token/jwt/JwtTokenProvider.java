package ru.borisof.pasteme.security.token.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import ru.borisof.pasteme.security.token.TokenProvider;

@Component
@Slf4j
public class JwtTokenProvider implements TokenProvider, InitializingBean {

  private static final String AUTHORITIES_KEY = "auth";

  /**
   * The secret used to generate the access token
   */
  @Value("${jwt.secret}")
  private String base64Secret;

  /**
   * Access token lifetime in milliseconds
   */
  @Value("${jwt.lifetime}")
  private long tokenValidityTime;

  /**
   * Secret key, generated from base64Secret
   */
  private Key key;


  @Override
  public void afterPropertiesSet() throws Exception {
    //When properties set, creates secret key with base64Secret from application properties
    byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }


  @Override
  public String generateToken(Authentication authentication) {

    String joinedAuthorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long tokenExpirationTimeInMillis = Instant.now().toEpochMilli() + tokenValidityTime;

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, joinedAuthorities)
        .signWith(key, SignatureAlgorithm.HS256)
        .setExpiration(new Date(tokenExpirationTimeInMillis))
        .compact();
  }

  @Override
  public Authentication getAuthenticationByToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token)
        .getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  @Override
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace: {}", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.trace("Expired JWT token trace: {}", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.trace("Unsupported JWT token trace: {}", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace: {}", e);
    }
    return false;
  }

}
