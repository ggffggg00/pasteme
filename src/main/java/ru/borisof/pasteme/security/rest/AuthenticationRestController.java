package ru.borisof.pasteme.security.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.pasteme.security.rest.dto.LoginDTO;
import ru.borisof.pasteme.security.token.TokenProvider;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationRestController {

  private final TokenProvider tokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @PostMapping("/authenticate")
  public ResponseEntity<Token> authorize(@Valid @RequestBody LoginDTO loginDto) {

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new Token(token));
  }

  @Data
  @RequiredArgsConstructor
  static class Token {

    @JsonProperty("access_token")
    private final String accessToken;

  }

}
