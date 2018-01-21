package me.wonwoo.domain.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.data.convert.Jsr310Converters;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

  @Override
  public Date convertToDatabaseColumn(LocalDateTime date) {
    return Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(date);
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Date date) {
    return Jsr310Converters.DateToLocalDateTimeConverter.INSTANCE.convert(date);
  }
}