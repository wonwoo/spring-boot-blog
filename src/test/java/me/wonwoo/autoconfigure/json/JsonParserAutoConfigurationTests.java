package me.wonwoo.autoconfigure.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import me.wonwoo.support.json.GsonParser;
import me.wonwoo.support.json.JacksonParser;
import me.wonwoo.support.json.JsonSmartParser;
import net.minidev.json.parser.JSONParser;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class JsonParserAutoConfigurationTests {

  private AnnotationConfigApplicationContext context;

  @After
  public void tearDown() {
    if (this.context != null) {
      this.context.close();
    }
  }

  @Test
  public void jacksonParserTest() {
    load(ObjectMapper.class);
    assertThat(context.getBeansOfType(JacksonParser.class)).isNotEmpty();
  }

  @Test
  public void gsonParserTest() {
    load(Gson.class);
    assertThat(context.getBeansOfType(GsonParser.class)).isNotEmpty();
  }

  @Test
  public void jsonSmartParserTest() {
    load(JSONParser.class);
    assertThat(context.getBeansOfType(JsonSmartParser.class)).isNotEmpty();
  }

  private void load(Class<?>... clazz) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(clazz);
    context.register(JsonParserAutoConfiguration.class);
    context.refresh();
    this.context = context;
  }
}