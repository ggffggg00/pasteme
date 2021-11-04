package ru.borisof.pasteme.security.token;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j
public class SimpleTokenFilter extends GenericFilterBean {

  public static final String DEFAULT_AUTHORIZATION_HEADER = "Authorization";
  public static final String DEFAULT_AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

  private final TokenProvider tokenProvider;

  @Setter
  private String authorizationHeader = DEFAULT_AUTHORIZATION_HEADER;

  @Setter
  private String authorizationTokenPrefix = DEFAULT_AUTHORIZATION_TOKEN_PREFIX;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String token = resolveToken(request);
    String requestUri = request.getRequestURI();

    if (StringUtils.hasText(token) && tokenProvider.validateToken(token)){
      Authentication authentication = tokenProvider.getAuthenticationByToken(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestUri);
    } else {
      log.debug("no valid JWT token found, uri: {}", requestUri);
    }

    filterChain.doFilter(servletRequest, servletResponse);

  }

  private String resolveToken(HttpServletRequest request) {
    String tokenHeader = request.getHeader(authorizationHeader);

    if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith(authorizationTokenPrefix)) {
      return tokenHeader.substring(authorizationTokenPrefix.length()).trim();
    }
    return null;
  }

}
