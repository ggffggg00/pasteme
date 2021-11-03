package ru.borisof.pasteme.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import ru.borisof.pasteme.security.token.TokenAccessDeniedHandler;
import ru.borisof.pasteme.security.token.TokenAuthenticationEntryPoint;
import ru.borisof.pasteme.security.token.TokenProvider;
import ru.borisof.pasteme.security.token.TokenSecurityConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final TokenAuthenticationEntryPoint authenticationErrorHandler;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    // Configure BCrypt password encoder =====================================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure security settings ===========================================================================

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // we don't need CSRF because our token is invulnerable
            .csrf().disable()

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

            .exceptionHandling()
            .authenticationEntryPoint(authenticationErrorHandler)
            .accessDeniedHandler(tokenAccessDeniedHandler)

            // create no session
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()

            .antMatchers("/api/person").hasAuthority("ROLE_USER")
            .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN")

            .anyRequest().authenticated()

            .and()
            .apply(securityConfigurerAdapter());
    }

    private TokenSecurityConfigurer securityConfigurerAdapter() {
        return new TokenSecurityConfigurer(tokenProvider);
    }

}
