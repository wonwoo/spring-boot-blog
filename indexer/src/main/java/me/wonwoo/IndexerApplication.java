package me.wonwoo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class IndexerApplication {

  public static void main(String[] args) {
    SpringApplication.run(IndexerApplication.class, args);
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(5);
  }

  @Bean
  public TaskScheduler scheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setPoolSize(5);
    return scheduler;
  }
}
