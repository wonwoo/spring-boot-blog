package me.wonwoo.support.jackson;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class JsonObjectDeserializerTests {

  private TestJsonObjectDeserializer<Object> testDeserializer = new TestJsonObjectDeserializer<>();

  @Test
  public void deserializeObjectJson() throws Exception {
    JsonDeserializer<Foo> deserializer = new FooDeserializer();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Foo.class, deserializer);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);
    Foo foo = mapper.readValue("{\"name\":\"hello?\",\"email\":\"hello@test.com\"}",
        Foo.class);
    assertThat(foo.getName()).isEqualTo("hellowd");
    assertThat(foo.getEmail()).isEqualTo("hellowd@hellowd.com");
  }

  @Test
  public void nullSafeValueTest() throws Exception {
    JsonNode node = mock(JsonNode.class);
    given(node.get("name")).willReturn(node);
    given(node.textValue()).willReturn("hello?");
    String value = this.testDeserializer.testNullSafeValue(node, "name", String.class);
    assertThat(value).isEqualTo("hello?");
  }

  static class TestJsonObjectDeserializer<T> extends JsonObjectDeserializer<T> {

    @Override
    protected T deserializeObject(JsonNode tree) {
      return null;
    }

    protected <D> D testNullSafeValue(JsonNode jsonNode, String key, Class<D> type) {
      return nullSafeValue(jsonNode, key, type);
    }

  }


  static class FooDeserializer extends JsonObjectDeserializer<Foo> {

    @Override
    protected Foo deserializeObject(JsonNode tree) {
      return new Foo("hellowd", "hellowd@hellowd.com");
    }
  }

  static class Foo {
    private String name;
    private String email;

    public Foo(String name, String email) {
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