package me.wonwoo.github;

import me.wonwoo.client.Client;
import me.wonwoo.github.asciidoc.GitHubRepo;
import me.wonwoo.github.page.GithubPage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Service
public class GithubClient extends Client {

  public GithubClient(RestTemplate restTemplate) {
    super(restTemplate);
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


  @Cacheable("github.page")
  public <T> T sendGithubPage(String path, Class<T> clazz) {
    return sendRequest(path, clazz);
  }

  private ResponseEntity<Commit[]> doGetRecentCommit(String organization, String project) {
    String url = String.format(
      "https://api.github.com/repos/%s/%s/commits", organization, project);
    return invoke(createRequestEntity(url), Commit[].class);
  }

  public String sendRequestForJson(String path) {
    String url = String.format(
      "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url), String.class).getBody();
  }


  public GitHubRepo sendRequestForGithub(String path) {
    String url = String.format(
      "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url), GitHubRepo.class).getBody();
  }

  public GitHubRepo[] sendRequestForGithubs(String path) {
    String url = String.format(
      "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url), GitHubRepo[].class).getBody();
  }

  public byte[] sendRequestForDownload(String path) {
    String url = String.format(
      "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url), byte[].class).getBody();
  }

  public <T> T sendRequest(String path, Class<T> clazz) {
    String url = String.format(
      "https://api.github.com/%s", path);
    return invoke(createRequestEntity(url), clazz).getBody();
  }
}