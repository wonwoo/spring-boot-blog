package me.wonwoo.web;

import me.wonwoo.wordpress.WordPress;
import me.wonwoo.wordpress.WordPressClient;
import me.wonwoo.wordpress.WordPressLogin;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoo on 2017. 2. 26..
 */
@WebMvcTest(value = WordPressApiController.class)
@EnableSpringDataWebSupport
public class WordPressApiControllerTests extends AbstractControllerTests {

  @MockBean
  private WordPressClient wordPressClient;

  @Autowired
  private MockMvc mockMvc;
  private final WordPress wordPress = new WordPress();

  @Before
  public void setup() {
    wordPress.setContent("test");
    wordPress.setTitle("title");
    wordPress.setId(1000L);
    wordPress.setDate(OffsetDateTime.now());
    final WordPressLogin author = new WordPressLogin();
    author.setLogin("wonwoo");
    author.setName("wonwoo");
    wordPress.setAuthor(author);
  }

  @Test
  public void findAllApi() throws Exception {
    Page<WordPress> wordPresses = new PageImpl<>(Collections.singletonList(wordPress));
    given(wordPressClient.findAll(any(), any())).willReturn(wordPresses);

    final MvcResult mvcResult = mockMvc.perform(get("/wordPress/api"))
      .andExpect(status().isOk())
      .andReturn();

    Page<WordPress> result = (Page<WordPress>) mvcResult.getModelAndView().getModel().get("wordPresses");
    assertThat(result.getContent().get(0).getId()).isEqualTo(1000L);
    assertThat(result.getContent().get(0).getContent()).isEqualTo("test");
    assertThat(result.getContent().get(0).getTitle()).isEqualTo("title");
    assertThat(result.getContent().get(0).getAuthor().getName()).isEqualTo("wonwoo");
    assertThat(result.getContent().get(0).getAuthor().getLogin()).isEqualTo("wonwoo");
    verify(wordPressClient, atLeastOnce()).findAll(any(), any());

  }

  @Test
  public void findOneApi() throws Exception {
    given(wordPressClient.findOne(any())).willReturn(wordPress);

    final MvcResult mvcResult = mockMvc.perform(get("/wordPress/api/{id}", 1))
      .andExpect(status().isOk())
      .andReturn();

    WordPress result = (WordPress) mvcResult.getModelAndView().getModel().get("wordPress");
    assertThat(result.getId()).isEqualTo(1000L);
    assertThat(result.getContent()).isEqualTo("test");
    assertThat(result.getTitle()).isEqualTo("title");
    assertThat(result.getAuthor().getName()).isEqualTo("wonwoo");
    assertThat(result.getAuthor().getLogin()).isEqualTo("wonwoo");
    verify(wordPressClient, atLeastOnce()).findOne(any());
  }

}