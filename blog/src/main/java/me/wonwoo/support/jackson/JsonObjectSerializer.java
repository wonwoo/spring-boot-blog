package me.wonwoo.support.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public abstract class JsonObjectSerializer<T> extends org.springframework.boot.jackson.JsonObjectSerializer<T> {

  @Override
  protected void serializeObject(T value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
    serializeObject(value, jsonGenerator);
  }
  protected abstract void serializeObject(T value, JsonGenerator jsonGenerator) throws IOException;
}