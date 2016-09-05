package me.wonwoo.github;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Service
public class GithubClient {

  private final RestTemplate restTemplate;

  public GithubClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Cacheable("github.user")
  public GithubUser getUser(String githubId) {
    return invoke(createRequestEntity(
      String.format("https://api.github.com/users/%s", githubId)), GithubUser.class).getBody();
  }

  @Cacheable("github.commits")
  public List<Commit> getRecentCommits(String organization, String project) {
    ResponseEntity<Commit[]> response = doGetRecentCommit(organization, project);
    return Arrays.asList(response.getBody());
  }

  private <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
    try {
      return this.restTemplate.exchange(request, type);
    } catch (RestClientException ex) {
      throw ex;
    }
  }

  private ResponseEntity<Commit[]> doGetRecentCommit(String organization, String project) {
    String url = String.format(
            "https://api.github.com/repos/%s/%s/commits", organization, project);
    return invoke(createRequestEntity(url), Commit[].class);
  }

  private RequestEntity<?> createRequestEntity(String url) {
    try {
      return RequestEntity.get(new URI(url))
        .accept(MediaType.APPLICATION_JSON).build();
    } catch (URISyntaxException ex) {
      throw new IllegalStateException("Invalid URL " + url, ex);
    }
  }

  public String sendRequestForJson(String path) {
    String url = String.format(
            "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url),String.class).getBody();
  }

  public byte[] sendRequestForDownload(String path) {
    String url = String.format(
            "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url), byte[].class).getBody();
  }
}