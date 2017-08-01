package me.wonwoo.support.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public abstract class JsonObjectDeserializer<T> extends org.springframework.boot.jackson.JsonObjectDeserializer<T> {

  @Override
  protected T deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
    return deserializeObject(tree);
  }

  protected final <D> D nullSafeValue(JsonNode jsonNode, String key, Class<D> type) {
    return super.nullSafeValue(jsonNode.get(key), type);
  }

  protected abstract T deserializeObject(JsonNode tree);
}