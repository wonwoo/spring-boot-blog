package me.wonwoo.github;

import me.wonwoo.client.Client;
import me.wonwoo.github.asciidoc.GitHubRepo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Service
public class GithubClient extends Client {

  private final static String GIT_HUB_URL = "https://api.github.com";

  public GithubClient(RestTemplate restTemplate) {
    super(restTemplate);
  }

  @Cacheable("github.user")
  public GithubUser getUser(String githubId) {
    return invoke(createRequestEntity(
      String.format(GIT_HUB_URL + "/users/%s", githubId)), GithubUser.class).getBody();
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
      GIT_HUB_URL + "/repos/%s/%s/commits", organization, project);
    return invoke(createRequestEntity(url), Commit[].class);
  }

  public String sendRequestForJson(String path) {
    return sendRequest(path, String.class);
  }


  public GitHubRepo sendRequestForGithub(String path) {
    return sendRequest(path, GitHubRepo.class);
  }

  public GitHubRepo[] sendRequestForGithubs(String path) {
    return sendRequest(path, GitHubRepo[].class);
  }

  public byte[] sendRequestForDownload(String path) {
    return sendRequest(path, byte[].class);
  }

  public <T> T sendRequest(String path, Class<T> clazz) {
    String url = String.format(
      GIT_HUB_URL + "/%s", path);
    return invoke(createRequestEntity(url), clazz).getBody();
  }
}