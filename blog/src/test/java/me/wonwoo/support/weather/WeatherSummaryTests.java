package me.wonwoo.support.weather;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class WeatherSummaryTests {

  @Test
  public void weatherSummary() {
    Weather weather = new Weather();
    weather.setTimestamp(1502112315);
    weather.setTemperature(26);
    List<Map<String, Object>> weatherEntries = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("id", 100);
    map.put("icon", "sunny");
    weatherEntries.add(map);
    weather.setWeather(weatherEntries);
    WeatherSummary weatherSummary =
        new WeatherSummary("KR", "Seoul", weather);

    assertThat(weatherSummary.getCountry()).isEqualTo("KR");
    assertThat(weatherSummary.getCity()).isEqualTo("Seoul");
    assertThat(weatherSummary.getCode()).isEqualTo(100);
    assertThat(weatherSummary.getIcon()).isEqualTo("sunny");
    assertThat(weatherSummary.getFahrenheitTemperature()).isEqualTo("-412.87");
    assertThat(weatherSummary.getCelsiusTemperature()).isEqualTo("-247.15");
  }
}