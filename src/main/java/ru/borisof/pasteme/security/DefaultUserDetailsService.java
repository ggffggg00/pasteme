package ru.borisof.pasteme.security;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.borisof.pasteme.account.repo.UserRepository;

@Component("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class DefaultUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (EmailValidator.getInstance().isValid(username)) {
      return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(username)
          .map(user -> createSpringSecurityUser(username, user))
          .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " was not found in the database"));
    }

    String lowercaseLogin = username.toLowerCase(Locale.ENGLISH);
    return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
        .map(user -> createSpringSecurityUser(lowercaseLogin, user))
        .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
  }

  private User createSpringSecurityUser(String lowercaseLogin, ru.borisof.pasteme.account.model.entity.User user) {

    List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
        .collect(Collectors.toList());

    return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
  }


}
