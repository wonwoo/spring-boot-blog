package me.wonwoo.support.weather;


import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class WeatherEntryTests {

  @Test
  public void weatherEntry() {
    WeatherEntry weatherEntry = new WeatherEntry();
    weatherEntry.setTimestamp(1502112315);
    weatherEntry.setTemperature(26);
    List<Map<String,Object>> weatherEntries = new ArrayList<>();
    Map<String,Object> map = new HashMap<>();
    map.put("id", 100);
    map.put("icon", "sunny");
    weatherEntries.add(map);
    weatherEntry.setWeather(weatherEntries);
    assertThat(weatherEntry.getTemperature()).isEqualTo(26);
    assertThat(weatherEntry.getTimestamp()).isEqualTo(Instant.ofEpochMilli(1502112315000L));
    assertThat(weatherEntry.getWeatherId()).isEqualTo(100);
    assertThat(weatherEntry.getWeatherIcon()).isEqualTo("sunny");
  }
}