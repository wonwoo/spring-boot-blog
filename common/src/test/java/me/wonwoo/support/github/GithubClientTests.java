package me.wonwoo.support.github;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


/**
 * Created by wonwoo on 2017. 2. 13..
 */

public class GithubClientTests {

  private GithubClient githubClient;

  private MockRestServiceServer server;

  @Before
  public void setup() {
    RestTemplate restTemplate = new RestTemplate();
    this.githubClient = new GithubClient(restTemplate);
    this.server = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  public void getUserTest() {
    expectJson("https://api.github.com/users/wonwoo",
      "githubUser.json");
    final GithubUser githubUser = githubClient.getUser("wonwoo");
    assertThat(githubUser).isNotNull();
    assertThat(githubUser.getAvatar()).isEqualTo("https://avatars.githubusercontent.com/u/747472?v=3");
    assertThat(githubUser.getBlog()).isEqualTo("http://wonwoo.ml");
    assertThat(githubUser.getCompany()).isNull();
    assertThat(githubUser.getName()).isEqualTo("wonwoo");
    assertThat(githubUser.getEmail()).isEqualTo("aoruqjfu@gmail.com");
  }
//
  @Test
  public void getRecentCommitsTest() {
    expectJson("https://api.github.com/repos/wonwoo/spring-boot-blog/commits",
      "spring-boot-blog-commits.json");
    List<Commit> recentCommits = this.githubClient.getRecentCommits(
      "wonwoo", "spring-boot-blog");
    this.server.verify();
    assertThat(recentCommits).hasSize(5);
    assertCommit(recentCommits.get(0), "c64c7088e298eb074f2b365be3adadf2a4d14e6c",
      "weather test",
      "2017-02-13T12:33:06Z",
      "wonwoo", "wonwoo", "https://avatars.githubusercontent.com/u/747472?v=3");
    assertCommit(recentCommits.get(4), "d62a74d7e06905b9d064df73e79b7c8819e4dc11",
      "flume 삭제",
      "2017-02-05T11:46:08Z",
      "wonwoo", "wonwoo", "https://avatars.githubusercontent.com/u/747472?v=3");
  }

  private void expectJson(String url, String bodyPath) {
    expectJson(url, bodyPath, null);
  }

  private void expectJson(String url, String bodyPath, String nextPage) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    if (nextPage != null) {
      httpHeaders.set(HttpHeaders.LINK,
        String.format("<%s>; rel=\"next\"", nextPage));
    }
    this.server.expect(requestTo(url))
      .andExpect(method(HttpMethod.GET))
      .andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
      .andRespond(withStatus(HttpStatus.OK)
        .body(new ClassPathResource(bodyPath, getClass()))
        .headers(httpHeaders));
  }


  private void assertCommit(Commit commit, String sha, String message, String date,
                            String committerId, String committerName, String committerAvatar) {
    assertThat(commit).isNotNull();
    assertThat(commit.getSha()).isEqualTo(sha);
    assertThat(commit.getMessage()).isEqualTo(message);
    assertThat(commit.getDate().toString()).isEqualTo(date);
    Commit.Committer committer = commit.getCommitter();
    assertThat(committer).isNotNull();
    assertThat(committer.getId()).isEqualTo(committerId);
    assertThat(committer.getName()).isEqualTo(committerName);
    assertThat(committer.getAvatarUrl()).isEqualTo(committerAvatar);
  }
}