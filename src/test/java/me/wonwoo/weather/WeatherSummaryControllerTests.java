package me.wonwoo.weather;

import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by wonwoo on 2017. 2. 13..
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = WeatherSummaryController.class, secure = false)
@TestPropertySource(properties = "app.weather.locations=KR/Seoul")
public class WeatherSummaryControllerTests {

  @MockBean
  private WeatherService weatherService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void weatherTest() throws Exception {
    Weather weather = new Weather();
    weather.setName("Seoul");
    setWeatherEntry(weather, 251.92, 800, "01d", Instant.ofEpochSecond(1234));
    given(this.weatherService.getWeather("KR", "Seoul")).willReturn(weather);
    final MvcResult mvcResult = this.mvc.perform(get("/weather"))
      .andExpect(status().isOk())
      .andReturn();

    final List<WeatherSummary> summary = (List<WeatherSummary>)mvcResult.getModelAndView().getModel().get("summary");
    final WeatherSummary weatherSummary = summary.get(0);
    assertThat(weatherSummary.getCountry()).isEqualTo("KR");
    assertThat(weatherSummary.getCity()).isEqualTo("Seoul");
    assertThat(weatherSummary.getCelsiusTemperature()).isEqualTo("-21.23");
    assertThat(weatherSummary.getCode()).isEqualTo(800);
    assertThat(weatherSummary.getIcon()).isEqualTo("01d");
    assertThat(weatherSummary.getFahrenheitTemperature()).isEqualTo("-6.21");


    verify(this.weatherService).getWeather("KR", "Seoul");
  }
  private static void setWeatherEntry(WeatherEntry entry, double temperature, int id, String icon,
                                      Instant timestamp) {
    entry.setTemperature(temperature);
    entry.setWeatherId(id);
    entry.setWeatherIcon(icon);
    entry.setTimestamp(timestamp.getEpochSecond());
  }

  @Configuration
  @ComponentScan(basePackageClasses = WeatherSummaryController.class)
  public static class TestConfig {

  }

}