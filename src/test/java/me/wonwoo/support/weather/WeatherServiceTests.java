package me.wonwoo.support.weather;

import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by wonwoo on 2017. 2. 13..
 */
@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
@TestPropertySource(properties = "app.weather.api.key=test-ABC")
public class WeatherServiceTests {

  private static final String URL = "http://api.openweathermap.org/data/2.5/";

  @Autowired
  private WeatherService weatherService;

  @Autowired
  private MockRestServiceServer server;

  @Test
  public void getWeatherTest() {
    this.server.expect(
      requestTo(URL + "weather?q=Seoul,kr&APPID=test-ABC"))
      .andRespond(withSuccess(
        new ClassPathResource("weather.json", getClass()),
        MediaType.APPLICATION_JSON));
    Weather forecast = this.weatherService.getWeather("kr", "Seoul");
    assertThat(forecast.getName()).isEqualTo("Seoul");
    assertThat(forecast.getTemperature()).isEqualTo(251.92, Offset.offset(0.1));
    assertThat(forecast.getWeatherId()).isEqualTo(800);
    assertThat(forecast.getWeatherIcon()).isEqualTo("01d");
    this.server.verify();
  }
}