package me.wonwoo.support.condition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class ConditionOnProfilesTests {

  private final ApplicationContextRunner runner = new ApplicationContextRunner();


  @Test
  public void doesNotMatchIfFoo1IsRequired() {
    runner.withUserConfiguration(Foo1Required.class)
        .run(context -> assertPresent(context, false));
  }

  @Test
  public void matchIfFoo1IsRequired() {
    runner
        .withPropertyValues("spring.profiles.active=foo1")
        .withUserConfiguration(Foo1Required.class)
        .run(context -> assertPresent(context, true));
  }


  @Test
  public void doesNotMatchIfFoo2IsRequired() {
    runner.withUserConfiguration(Foo2Required.class)
        .run(context -> assertPresent(context, false));
  }

  @Test
  public void matchIfFoo2IsRequired() {
    runner
        .withPropertyValues("spring.profiles.active=foo2")
        .withUserConfiguration(Foo2Required.class)
        .run(context -> assertPresent(context, true));
  }


  @Test
  public void matchIfFoo3Required() {
    runner.withUserConfiguration(Foo3Required.class)
        .run(context -> assertPresent(context, false));
  }


  @Test
  public void doesNotMatchIfFoo3IsRequired() {
    runner
        .withPropertyValues("spring.profiles.active=foo3")
        .withUserConfiguration(Foo3Required.class)
        .run(context -> assertPresent(context, true));
  }


  private void assertPresent(ApplicationContext context, boolean expected) {
    assertThat(context.getBeansOfType(String.class)).hasSize(expected ? 1 : 0);
  }

  @Configuration
  @ConditionalOnProfiles("foo1")
  static class Foo1Required {

    @Bean
    String foo1() {
      return "foo1";
    }
  }

  @Configuration
  @ConditionalOnProfiles("foo2")
  static class Foo2Required {

    @Bean
    String foo2() {
      return "foo2";
    }

  }

  @Configuration
  @ConditionalOnProfiles("foo3")
  static class Foo3Required {

    @Bean
    String foo3() {
      return "foo3";
    }
  }
}