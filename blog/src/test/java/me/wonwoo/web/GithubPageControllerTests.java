package me.wonwoo.web;

import me.wonwoo.support.github.GithubClient;
import me.wonwoo.support.github.page.GithubPage;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoo on 2017. 2. 15..
 */
@WebMvcTest(GithubPageController.class)
public class GithubPageControllerTests extends AbstractControllerTests {

  @MockBean
  private GithubClient githubClient;

  @SpyBean
  private PegDownProcessor pegDownProcessor;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void githubPagesTest() throws Exception {
    final GithubPage githubPage = new GithubPage();
    githubPage.setContent("test content");
    githubPage.setName("wonwoo.md");
    githubPage.setSize(10000L);
    githubPage.setPath("/home/wonwoo/test");
    given(githubClient.sendRequest(anyString(), any()))
      .willReturn(new GithubPage[]{githubPage});

    final MvcResult mvcResult = mockMvc.perform(get("/github/page"))
      .andExpect(status().isOk())
      .andReturn();
    List<GithubPage> githubPages = (List<GithubPage>) mvcResult.getModelAndView().getModel().get("githubPages");
    assertThat(githubPages.get(0).getContent()).isEqualTo("test content");
    assertThat(githubPages.get(0).getName()).isEqualTo("wonwoo");
    assertThat(githubPages.get(0).getSize()).isEqualTo(10000L);
    assertThat(githubPages.get(0).getPath()).isEqualTo("/home/wonwoo/test");
    verify(githubClient, atLeastOnce()).sendRequest("repos/wonwoo/github/contents/_post", GithubPage[].class);
  }

  @Test
  public void githubPageTest() throws Exception {
    final GithubPage githubPage = new GithubPage();
    githubPage.setContent(Base64.encodeBase64String("test content".getBytes()));
    githubPage.setName("wonwoo.md");
    githubPage.setSize(10000L);
    githubPage.setPath("/home/wonwoo/test");
    given(githubClient.sendRequest(anyString(), any()))
      .willReturn(githubPage);

    final MvcResult mvcResult = mockMvc.perform(get("/github/page/{name}", "wonwoo"))
      .andExpect(status().isOk())
      .andReturn();
    GithubPage result = (GithubPage) mvcResult.getModelAndView().getModel().get("githubPage");
    assertThat(result.getContent()).isEqualTo("<p>test content</p>");
    assertThat(result.getName()).isEqualTo("wonwoo.md");
    assertThat(result.getSize()).isEqualTo(10000L);
    assertThat(result.getPath()).isEqualTo("/home/wonwoo/test");


    verify(githubClient, atLeastOnce()).sendRequest("repos/wonwoo/github/contents/_post/wonwoo.md", GithubPage.class);

  }
}