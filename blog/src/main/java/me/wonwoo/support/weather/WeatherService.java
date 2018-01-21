package me.wonwoo.support.weather;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

/**
 * Created by wonwoo on 2016. 10. 7..
 */
@Service
public class WeatherService {

  private static final String WEATHER_URL =
      "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";

  private final RestTemplate restTemplate;

  private final String apiKey;

  public WeatherService(RestTemplateBuilder restTemplateBuilder,
                        WeatherAppProperties properties) {
    this.restTemplate = restTemplateBuilder.build();
    this.apiKey = properties.getApi().getKey();
  }

  @Cacheable("weather")
  public Weather getWeather(String country, String city) {
    URI url = new UriTemplate(WEATHER_URL).expand(city, country, this.apiKey);
    return invoke(url, Weather.class);
  }


  private <T> T invoke(URI url, Class<T> responseType) {
    RequestEntity<?> request = RequestEntity.get(url)
        .accept(MediaType.APPLICATION_JSON).build();
    ResponseEntity<T> exchange = this.restTemplate
        .exchange(request, responseType);
    return exchange.getBody();
  }

}