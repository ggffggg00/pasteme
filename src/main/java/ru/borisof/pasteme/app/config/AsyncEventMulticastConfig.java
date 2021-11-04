package ru.borisof.pasteme.app.config;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class AsyncEventMulticastConfig {

  @Bean(name = "applicationEventMulticasterExecutor")
  public Executor eventTaskExecutor() {
    return new SimpleAsyncTaskExecutor();
  }


  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster applicationEventMulticaster(
      @Qualifier("applicationEventMulticasterExecutor") Executor taskExecutor) {

    SimpleApplicationEventMulticaster eventMulticaster =
        new SimpleApplicationEventMulticaster();

    eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    return eventMulticaster;
  }


}
