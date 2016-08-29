package me.wonwoo.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
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
public class CategoryControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void categoriesTest() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/categories", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
  }

  @Test
  public void newCategoryTest() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/categories/new", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
  }

  @Test
  public void editTest() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/categories/edit/1", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
  }

  @Test
  public void createCategoryTest() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/categories", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
  }

  @Test
  public void modifyCategoryTest() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/categories/1/edit", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
  }

  @Test
  public void deleteCategoryTest() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/categories/1/delete", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
  }
}