package me.wonwoo.support.jackson;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class JsonObjectSerializerTests {

  @Test
  public void serializeObjectJson() throws Exception {
    JsonSerializer<Bar> deserializer = new FooSerializer();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Bar.class, deserializer);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);
    String json = mapper.writeValueAsString(new Bar("hellowd", "hello@test.com"));
    assertThat(json).isEqualToIgnoringWhitespace("{\"name\":\"hello???\"}");
  }

  static class FooSerializer extends JsonObjectSerializer<Bar> {

    @Override
    protected void serializeObject(Bar value, JsonGenerator jsonGenerator) throws IOException {
      jsonGenerator.writeStringField("name", "hello???");
    }
  }

  static class Bar {
    private String name;
    private String email;

    public Bar(String name, String email) {
      this.name = name;
      this.email = email;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
}