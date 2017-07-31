package me.wonwoo.support.json;


import net.minidev.json.parser.JSONParser;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;

import java.io.StringReader;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class JsonSmartParserTests {

  private AnnotationConfigApplicationContext context;

  @After
  public void tearDown() {
    if (this.context != null) {
      this.context.close();
    }
  }

  @Test
  public void writeObject() throws Exception {
    Foo foo = new Foo("wonwoo");
    load(TestConfig.class);
    JsonSmartParser jsonSmartParser = context.getBean(JsonSmartParser.class);
    assertThat(jsonSmartParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    final String s = jsonSmartParser.writeObject(foo, resolvableType);
    assertThat(s).isEqualTo("{\"username\":\"wonwoo\"}");
  }

  @Test
  public void writeObject1() throws Exception {
    Foo foo = new Foo("wonwoo");
    load(TestConfig.class);
    JsonSmartParser jsonSmartParser = context.getBean(JsonSmartParser.class);
    assertThat(jsonSmartParser).isNotNull();
    final String s = jsonSmartParser.writeObject(foo, Foo.class);
    assertThat(s).isEqualTo("{\"username\":\"wonwoo\"}");
  }

  @Test
  public void readObject() throws Exception {
    load(TestConfig.class);
    JsonSmartParser jsonSmartParser = context.getBean(JsonSmartParser.class);
    assertThat(jsonSmartParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    Foo foo = jsonSmartParser.readObject(new StringReader("{\"username\" :\"wonwoo\"}"), resolvableType);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject1() throws Exception {
    load(TestConfig.class);
    JsonSmartParser jsonSmartParser = context.getBean(JsonSmartParser.class);
    assertThat(jsonSmartParser).isNotNull();
    Foo foo = jsonSmartParser.readObject(new StringReader("{\"username\" :\"wonwoo\"}"), Foo.class);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject2() throws Exception {
    load(TestConfig.class);
    JsonSmartParser jsonSmartParser = context.getBean(JsonSmartParser.class);
    assertThat(jsonSmartParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    Foo foo = jsonSmartParser.readObject("{\"username\" :\"wonwoo\"}", resolvableType);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject3() throws Exception {
    load(TestConfig.class);
    JsonSmartParser jsonSmartParser = context.getBean(JsonSmartParser.class);
    assertThat(jsonSmartParser).isNotNull();
    Foo foo = jsonSmartParser.readObject("{\"username\" :\"wonwoo\"}", Foo.class);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  private void load(Class<?>... config) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    if (config != null) {
      context.register(config);
    }
    context.refresh();
    this.context = context;
  }

  @Configuration
  protected static class TestConfig {

    @Bean
    public JsonSmartParser jsonSmartParser() {
      return new JsonSmartParser(new JSONParser(DEFAULT_PERMISSIVE_MODE));
    }

  }

}