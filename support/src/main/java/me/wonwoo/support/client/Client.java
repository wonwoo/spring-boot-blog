package me.wonwoo.support.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@RequiredArgsConstructor
public class Client {

  private final RestTemplate restTemplate;

  protected  <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
    try {
      return this.restTemplate.exchange(request, type);
    } catch (HttpClientErrorException ex) {
      throw ex;
    }
  }

  protected RequestEntity<?> createRequestEntity(String url) {
    try {
      return RequestEntity.get(new URI(url))
        .accept(MediaType.APPLICATION_JSON).build();
    } catch (URISyntaxException ex) {
      throw new IllegalStateException("Invalid URL " + url, ex);
    }
  }

}
