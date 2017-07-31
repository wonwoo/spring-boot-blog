package me.wonwoo.support.json;

import org.springframework.core.ResolvableType;

import java.io.Reader;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
public interface JsonParser {

  <T> String writeObject(T value, ResolvableType type);

  <T> String writeObject(T value, Class<T> clazz);

  <T> T readObject(Reader reader, ResolvableType type);

  <T> T readObject(Reader reader, Class<T> clazz);

  <T> T readObject(String json, ResolvableType type);

  <T> T readObject(String json, Class<T> clazz);
}
