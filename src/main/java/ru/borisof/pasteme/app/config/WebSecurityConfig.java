package ru.borisof.pasteme.app.config;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
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

    @Value("${security.password.seed}")
    private String passwordEncoderSeed;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")

            // allow anonymous resource requests
            .antMatchers(
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/h2-console/**"
            );
    }

    // Configure BCrypt password encoder =====================================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        SecureRandom random =
            new SecureRandom(passwordEncoderSeed.getBytes(StandardCharsets.UTF_8));
        return new BCryptPasswordEncoder(BCryptVersion.$2B, random);
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

            // enable h2-console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // create no session
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate", "/api/register").permitAll()

            .antMatchers("/api/*/**").hasAuthority("ROLE_USER")
            .antMatchers("/api/admin/*/**").hasAuthority("ROLE_ADMIN")

            .anyRequest().authenticated()

            .and()
            .apply(securityConfigurerAdapter());
    }

    private TokenSecurityConfigurer securityConfigurerAdapter() {
        return new TokenSecurityConfigurer(tokenProvider);
    }

}
