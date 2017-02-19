package me.wonwoo.support.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ResolvableType;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class JacksonParser implements JsonParser {

  private final ObjectMapper objectMapper;

  public JacksonParser(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public <T> String writeObject(T value, ResolvableType type) {
    try {
      return this.objectMapper.writerFor(getType(type)).writeValueAsString(value);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  @Override
  public <T> String writeObject(T value, Class<T> clazz) {
    try {
      return this.objectMapper.writerFor(clazz).writeValueAsString(value);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  @Override
  public <T> T readObject(Reader reader, ResolvableType type) {
    return readObject0(reader, getType(type));
  }

  @Override
  public <T> T readObject(Reader reader, Class<T> clazz) {
    return readObject0(reader, getType(clazz));
  }

  @Override
  public <T> T readObject(String json, ResolvableType type) {
    return readObject0(new StringReader(json), getType(type));
  }

  @Override
  public <T> T readObject(String json, Class<T> clazz) {
    return readObject0(new StringReader(json), getType(clazz));
  }

  private JavaType getType(Class<?> type) {
    return this.objectMapper.constructType(type);
  }

  private JavaType getType(ResolvableType type) {
    return this.objectMapper.constructType(type.getType());
  }

  private <T> T readObject0(Reader reader, JavaType type) {
    try {
      return this.objectMapper.readerFor(type).readValue(reader);
    } catch (IOException e) {
      return null;
    }
  }
}