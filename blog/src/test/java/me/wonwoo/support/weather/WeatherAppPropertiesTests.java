package me.wonwoo.support.weather;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class WeatherAppPropertiesTests {

  @Test
  public void weatherAppPropertiesEmpty() {
    WeatherAppProperties weatherAppProperties = new WeatherAppProperties();
    assertThat(weatherAppProperties.getLocations())
    .isEqualTo(Arrays.asList("KR/Seoul", "USA/Los angeles"));
  }

  @Test
  public void weatherAppProperties() {
    WeatherAppProperties weatherAppProperties = new WeatherAppProperties();
    weatherAppProperties.getApi().setKey("testkey");
    assertThat(weatherAppProperties.getLocations())
        .isEqualTo(Arrays.asList("KR/Seoul", "USA/Los angeles"));
    assertThat(weatherAppProperties.getApi().getKey())
        .isEqualTo("testkey");
  }

}