package ru.borisof.pasteme.security.token.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@RequiredArgsConstructor
public class JwtConfigurer extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


  @Override
  public void configure(HttpSecurity builder) throws Exception {
    super.configure(builder);
  }
}
