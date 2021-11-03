package ru.borisof.pasteme.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class TokenSecurityConfigurer extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenProvider tokenProvider;

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    SimpleTokenFilter tokenFilter = new SimpleTokenFilter(tokenProvider);
    builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
