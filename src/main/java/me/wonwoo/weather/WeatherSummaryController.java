package me.wonwoo.weather;

import lombok.RequiredArgsConstructor;
import me.wonwoo.web.Navigation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static me.wonwoo.web.Section.WEATHER;

/**
 * Created by wonwoo on 2016. 10. 7..
 */
@Controller
@RequestMapping("/weather")
@Navigation(WEATHER)
@RequiredArgsConstructor
public class WeatherSummaryController {

  private final WeatherService weatherService;

  private final WeatherAppProperties properties;


  @GetMapping
  public String conferenceWeather(Model model){
    model.addAttribute("summary", getSummary());
    return "summary/summary";
  }

  private List<WeatherSummary> getSummary() {
    List<WeatherSummary> summary = new ArrayList<>();
    for (String location : this.properties.getLocations()) {
      String country = location.split("/")[0];
      String city = location.split("/")[1];
      Weather weather = this.weatherService.getWeather(country, city);
      summary.add(createWeatherSummary(country, city, weather));
    }
    return summary;
  }



  private WeatherSummary createWeatherSummary(String country, String city,
                                              Weather weather) {
    // cough cough
    if ("Las Vegas".equals(city)) {
      weather.setWeatherId(666);
    }
    return new WeatherSummary(country, city, weather);
  }

}
