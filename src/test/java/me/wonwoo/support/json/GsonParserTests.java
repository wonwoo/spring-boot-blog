package me.wonwoo.support.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ResolvableType;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class GsonParserTests {

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
    load(GsonParser.class, Gson.class);
    GsonParser gsonParser = context.getBean(GsonParser.class);
    assertThat(gsonParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    final String s = gsonParser.writeObject(foo, resolvableType);
    assertThat(s).isEqualTo("{\"username\":\"wonwoo\"}");
  }

  @Test
  public void writeObject1() throws Exception {
    Foo foo = new Foo("wonwoo");
    load(GsonParser.class, Gson.class);
    GsonParser gsonParser = context.getBean(GsonParser.class);
    assertThat(gsonParser).isNotNull();
    final String s = gsonParser.writeObject(foo, Foo.class);
    assertThat(s).isEqualTo("{\"username\":\"wonwoo\"}");
  }

  @Test
  public void readObject() throws Exception {
    load(GsonParser.class, Gson.class);
    GsonParser gsonParser = context.getBean(GsonParser.class);
    assertThat(gsonParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    Foo foo = gsonParser.readObject(new StringReader("{\"username\" :\"wonwoo\"}"), resolvableType);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject1() throws Exception {
    load(GsonParser.class, Gson.class);
    GsonParser gsonParser = context.getBean(GsonParser.class);
    assertThat(gsonParser).isNotNull();
    Foo foo = gsonParser.readObject(new StringReader("{\"username\" :\"wonwoo\"}"), Foo.class);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject2() throws Exception {
    load(GsonParser.class, Gson.class);
    GsonParser gsonParser = context.getBean(GsonParser.class);
    assertThat(gsonParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    Foo foo = gsonParser.readObject("{\"username\" :\"wonwoo\"}", resolvableType);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject3() throws Exception {
    load(GsonParser.class, Gson.class);
    GsonParser gsonParser = context.getBean(GsonParser.class);
    assertThat(gsonParser).isNotNull();
    Foo foo = gsonParser.readObject("{\"username\" :\"wonwoo\"}", Foo.class);
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

}