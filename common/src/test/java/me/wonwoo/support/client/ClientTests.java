package me.wonwoo.support.client;


import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import me.wonwoo.junit.MockitoJsonJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by wonwoo on 2017. 2. 12..
 */
@RunWith(MockitoJsonJUnitRunner.class)
public class ClientTests {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  private JacksonTester<Foo> json;

  @Mock
  private RestTemplate restTemplate;

  private Client client;

  @Before
  public void setup() {
    client = new Client(restTemplate);
  }

  @Test
  public  void invokeTest() throws IOException {
    Foo foo = new Foo();
    foo.setId(1L);
    foo.setName("wonwoo");
    given(restTemplate.exchange(any(), Matchers.<Class<Foo>>any()))
      .willReturn(ResponseEntity.ok(foo));
    final ResponseEntity<Foo> result = client.invoke(client.createRequestEntity("http://localhost:8080/test"), Foo.class);
    assertThat(this.json.write(result.getBody()))
      .isEqualToJson("client.json");
  }

  @Test
  public void invokeTest404() {
    exception.expect(HttpClientErrorException.class);
    given(restTemplate.exchange(any(), Matchers.<Class<String>>any()))
      .willThrow(HttpClientErrorException.class);
    client.invoke(client.createRequestEntity("http://localhost:8080/test"), Foo.class);
  }

  @Test
  public void createRequestEntityTest() {
    final RequestEntity<?> requestEntity = client.createRequestEntity("http://localhost:8080/test");
    assertThat(requestEntity.getMethod()).isEqualTo(HttpMethod.GET);
    assertThat(requestEntity.getUrl()).isEqualTo(URI.create("http://localhost:8080/test"));
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("accept", "application/json");
    assertThat(requestEntity.getHeaders()).isEqualTo(httpHeaders);
  }

  @Test
  public void createRequestEntityUriExceptionTest() {
    exception.expect(IllegalStateException.class);
    RequestEntity<?> requestEntity = client.createRequestEntity("Asdfas###");
    System.out.println(requestEntity);
  }
}