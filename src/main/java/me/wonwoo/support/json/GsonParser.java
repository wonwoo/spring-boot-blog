package me.wonwoo.support.json;

import com.google.gson.Gson;
import org.springframework.core.ResolvableType;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class GsonParser implements JsonParser {

  private final Gson gson;

  public GsonParser(Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T> String writeObject(T value, ResolvableType type) {
    return gson.toJson(value, type.getType());
  }

  public <T> String writeObject(T value, Class<T> type) {
    return gson.toJson(value, type);
  }

  @Override
  public <T> T readObject(Reader reader, ResolvableType type) {
    return readObject0(reader, type.getType());
  }

  @Override
  public <T> T readObject(Reader reader, Class<T> clazz) {
    return readObject0(reader, clazz);
  }

  @Override
  public <T> T readObject(String json, ResolvableType type) {
    return readObject0(new StringReader(json), type.getType());
  }

  @Override
  public <T> T readObject(String json, Class<T> clazz) {
    return readObject0(new StringReader(json), clazz);
  }

  private <T> T readObject0(Reader reader, Type type) {
    return gson.fromJson(reader, type);
  }
}
