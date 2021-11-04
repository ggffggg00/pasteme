package ru.borisof.pasteme.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.borisof.pasteme.app.utils.Hashids;

@Configuration
public class HashIdsConfiguration {

  @Value("${hashids.salt}")
  private String salt;

  @Value("${hashids.min_length}")
  private int minHashLength;

  @Value("${hashids.alphabet}")
  private String alphabet;

  @Bean
  public Hashids idConverter(){
    return new Hashids(salt, minHashLength, alphabet);
  }

}
