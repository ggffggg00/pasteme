package ru.borisof.pasteme.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.borisof.pasteme.dao.User;
import ru.borisof.pasteme.service.AccountService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.util.StringUtils.hasText;

@Log
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {


    public static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;

    private final AccountService accountService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        if (token == null || !jwtProvider.validateToken(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String userEmail = jwtProvider.getLoginFromToken(token);

        User customUserDetails = accountService.getUserByEmail(userEmail);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(customUserDetails,
                        null,
                        Collections.singletonList(
                                new SimpleGrantedAuthority("USER")
                        ));

        SecurityContextHolder.getContext().setAuthentication(auth);


    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
