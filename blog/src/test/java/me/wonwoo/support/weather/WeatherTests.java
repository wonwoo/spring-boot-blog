package me.wonwoo.support.weather;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class WeatherTests {

  @Test
  public void weather() {
    Weather weather = new Weather();
    weather.setName("test");
    assertThat(weather.getName()).isEqualTo("test");
  }

}