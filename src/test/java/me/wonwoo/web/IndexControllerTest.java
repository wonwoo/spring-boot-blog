package me.wonwoo.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoo on 2016. 8. 29..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void homeTest() {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}