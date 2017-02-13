package me.wonwoo.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wonwoo on 2016. 10. 7..
 */
@ConfigurationProperties("app.weather")
@Validated
public class WeatherAppProperties {

  @Valid
  private final Api api = new Api();

  private List<String> locations = Arrays.asList("KR/Seoul", "USA/Los angeles");

  public Api getApi() {
    return this.api;
  }

  public List<String> getLocations() {
    return this.locations;
  }

  public void setLocations(List<String> locations) {
    this.locations = locations;
  }

  public static class Api {

    @NotNull
    private String key;

    public String getKey() {
      return this.key;
    }

    public void setKey(String key) {
      this.key = key;
    }

  }
}