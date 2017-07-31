package me.wonwoo.support.condition;

import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class ConditionOnMissingProfilesTests {

  private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

  @Test
  public void doesNotMatchIfFoo1IsRequired() {
    load(Foo1Required.class);
    assertPresent(true);
  }

  @Test
  public void matchIfFoo1IsRequired() {
    load(Foo1Required.class, "spring.profiles.active=foo1");
    assertPresent(false);
  }

  @Test
  public void matchIfFoo2IsRequired() {
    load(Foo2Required.class, "spring.profiles.active=foo1");
    assertPresent(true);
  }


  @Test
  public void doesNotMatchIfFoo2IsRequired() {
    load(Foo2Required.class, "spring.profiles.active=foo2");
    assertPresent(false);
  }


  @Test
  public void matchIfFoo3Required() {
    load(Foo3Required.class, "spring.profiles.active=foo2");
    assertPresent(true);
  }


  @Test
  public void doesNotMatchIfFoo3Required() {
    load(Foo3Required.class, "spring.profiles.active=foo3");
    assertPresent(false);
  }


  private void load(Class<?> config, String... environment) {
    EnvironmentTestUtils.addEnvironment(this.context, environment);
    this.context.register(config);
    this.context.refresh();
  }


  private void assertPresent(boolean expected) {
    assertThat(this.context.getBeansOfType(String.class)).hasSize(expected ? 1 : 0);
  }


  @Configuration
  @ConditionalOnMissingProfiles("foo1")
  static class Foo1Required {

    @Bean
    String foo1() {
      return "foo1";
    }
  }

  @Configuration
  @ConditionalOnMissingProfiles("foo2")
  static class Foo2Required {

    @Bean
    String foo2() {
      return "foo2";
    }
  }

  @Configuration
  @ConditionalOnMissingProfiles("foo3")
  static class Foo3Required {

    @Bean
    String foo3() {
      return "foo3";
    }
  }
}