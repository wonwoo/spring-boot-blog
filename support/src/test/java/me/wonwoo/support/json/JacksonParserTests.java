package me.wonwoo.support.json;

import java.io.StringReader;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ResolvableType;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class JacksonParserTests {

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
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    final String s = jacksonParser.writeObject(foo, resolvableType);
    assertThat(s).isEqualTo("{\"username\":\"wonwoo\"}");
  }

  @Test
  public void writeObject1() throws Exception {
    Foo foo = new Foo("wonwoo");
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    final String s = jacksonParser.writeObject(foo, Foo.class);
    assertThat(s).isEqualTo("{\"username\":\"wonwoo\"}");
  }

  @Test
  public void readObject() throws Exception {
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    Foo foo = jacksonParser.readObject(new StringReader("{\"username\" :\"wonwoo\"}"), resolvableType);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject1() throws Exception {
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    Foo foo = jacksonParser.readObject(new StringReader("{\"username\" :\"wonwoo\"}"), Foo.class);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject2() throws Exception {
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    ResolvableType resolvableType = ResolvableType.forType(Foo.class);
    Foo foo = jacksonParser.readObject("{\"username\" :\"wonwoo\"}", resolvableType);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject3() throws Exception {
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    Foo foo = jacksonParser.readObject("{\"username\" :\"wonwoo\"}", Foo.class);
    assertThat(foo.getUsername()).isEqualTo("wonwoo");
  }

  @Test
  public void readObject3_exception() throws Exception {
    load(JacksonParser.class, ObjectMapper.class);
    JacksonParser jacksonParser = context.getBean(JacksonParser.class);
    assertThat(jacksonParser).isNotNull();
    Foo foo = jacksonParser.readObject("{\"username\" :\"wonwoo\"", Foo.class);
    assertThat(foo).isNull();
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