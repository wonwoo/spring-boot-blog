package me.wonwoo.support.json;

import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import net.minidev.json.writer.JsonReader;
import org.springframework.core.ResolvableType;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public class JsonSmartParser implements JsonParser {

  private final JsonReader jsonReader;
  private final JSONParser jsonParser;

  public JsonSmartParser(JSONParser jsonParser) {
    this.jsonParser = jsonParser;
    this.jsonReader = new JsonReader();
  }

  public JsonSmartParser(JSONParser jsonParser, JsonReader jsonReader) {
    this.jsonParser = jsonParser;
    this.jsonReader = jsonReader;
  }

  @Override
  public <T> String writeObject(T value, ResolvableType type) {
    return JSONValue.toJSONString(value);
  }

  @Override
  public <T> String writeObject(T value, Class<T> clazz) {
    return JSONValue.toJSONString(value);
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
    try {
      return jsonParser.parse(reader, jsonReader.getMapper(type));
    } catch (ParseException e) {
      return null;
    }
  }
}
