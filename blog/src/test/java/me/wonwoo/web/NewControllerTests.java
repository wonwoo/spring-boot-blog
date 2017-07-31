package me.wonwoo.web;


import me.wonwoo.support.github.Commit;
import me.wonwoo.support.github.GithubClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoo on 2017. 2. 15..
 */
@WebMvcTest({NewController.class})
public class NewControllerTests extends AbstractControllerTests {

  @MockBean
  private GithubClient githubClient;

  @Autowired
  private MockMvc mockMvc;


  @Test
  public void homeTest() throws Exception {
    given(githubClient.getRecentCommits(any(), any()))
      .willReturn(Collections.singletonList(
        new Commit("733865b2e23b2acff6e9b1603b6ff83d79ee1972", "test messge",
          new Commit.Committer("wonwoo", "wonwoo", "https://avatars.githubusercontent.com/u/747472?v=3"), Instant.now()))
      );
    final MvcResult mvcResult = mockMvc.perform(get("/news"))
      .andExpect(status().isOk())
      .andReturn();

    List<Commit> commits = (List<Commit>) mvcResult.getModelAndView().getModel().get("latestSpringBlogCommits");
    assertThat(commits.get(0).getMessage()).isEqualTo("test messge");
    assertThat(commits.get(0).getSha()).isEqualTo("733865b2e23b2acff6e9b1603b6ff83d79ee1972");
    assertThat(commits.get(0).getCommitter().getName()).isEqualTo("wonwoo");
    assertThat(commits.get(0).getCommitter().getId()).isEqualTo("wonwoo");
    assertThat(commits.get(0).getCommitter().getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/747472?v=3");
    verify(githubClient, atLeastOnce()).getRecentCommits("wonwoo", "spring-boot-blog");
  }
}