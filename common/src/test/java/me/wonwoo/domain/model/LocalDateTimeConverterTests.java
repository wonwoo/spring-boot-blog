package me.wonwoo.domain.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MILLISECOND;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class LocalDateTimeConverterTests {

  private final LocalDateTimeConverter converter = new LocalDateTimeConverter();
  private static LocalDateTime TIME;

  private static Date DATE;

  static {
    Calendar c = Calendar.getInstance();
    c.set(2017, 7, 8, 12, 11, 45);
    c.set(MILLISECOND, 0);
    DATE = c.getTime();
    TIME = LocalDateTime.of(2017, 8, 8, 12, 11, 45, 0);
  }

  @Test
  public void convertToDatabaseColumn() {
    Date date = converter.convertToDatabaseColumn(TIME);
    assertThat(date).isEqualTo(DATE);
  }

  @Test
  public void convertToEntityAttribute() {
    LocalDateTime localDateTime = converter.convertToEntityAttribute(DATE);
    assertThat(localDateTime).isEqualTo(TIME);
  }

}