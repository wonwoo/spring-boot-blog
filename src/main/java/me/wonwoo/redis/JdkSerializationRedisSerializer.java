package me.wonwoo.redis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

/**
 * Created by wonwoo on 2016. 10. 16..
 */
public class JdkSerializationRedisSerializer implements RedisSerializer<Object> {

  private final Converter<Object, byte[]> serializer;
  private final Converter<byte[], Object> deserializer;

  public JdkSerializationRedisSerializer() {
    this(new SerializingConverter(), new DeserializingConverter());
  }


  public JdkSerializationRedisSerializer(ClassLoader classLoader) {
    this(new SerializingConverter(), new DeserializingConverter(classLoader));
  }

  public JdkSerializationRedisSerializer(Converter<Object, byte[]> serializer, Converter<byte[], Object> deserializer) {

    Assert.notNull("Serializer must not be null!");
    Assert.notNull("Deserializer must not be null!");

    this.serializer = serializer;
    this.deserializer = deserializer;
  }


  static boolean isEmpty(byte[] data) {
    return (data == null || data.length == 0);
  }

  public Object deserialize(byte[] bytes) {
    if (isEmpty(bytes)) {
      return null;
    }

    try {
      return deserializer.convert(bytes);
    } catch (Exception ex) {
      throw new SerializationException("Cannot deserialize", ex);
    }
  }

  public byte[] serialize(Object object) {
    if (object == null) {
      return new byte[0];
    }
    try {
      return serializer.convert(object);
    } catch (Exception ex) {
      throw new SerializationException("Cannot serialize", ex);
    }
  }
}

