package me.wonwoo.web;

import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.service.BlogHistoryService;
import me.wonwoo.support.elasticsearch.ElasticBuckets;
import me.wonwoo.support.elasticsearch.ElasticService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoolee on 2017. 8. 9..
 */
@WebMvcTest(BlogHistoryController.class)
@EnableSpringDataWebSupport
public class BlogHistoryControllerTests extends AbstractControllerTests {

  @MockBean
  private BlogHistoryService blogHistoryService;

  @MockBean
  private ElasticService elasticService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findByDateBetween() throws Exception {

    Page<BlogHistory> page = new PageImpl<>(Collections.singletonList(
        new BlogHistory("/accounts", "http://localhost:8080/accounts", "127.0.0.1", "USER", null, LocalDateTime.now())));
    given(blogHistoryService.findByDateBetween(any())).willReturn(page);
    given(elasticService.findByGroupByNavigation()).willReturn(Collections.singletonList(new ElasticBuckets("/accounts", 100L)));
    final MvcResult mvcResult = mockMvc.perform(get("/histories"))
        .andExpect(status().isOk())
        .andReturn();
    Page<BlogHistory> histories = (Page<BlogHistory>) mvcResult.getModelAndView().getModel().get("histories");
    List<ElasticBuckets> elasticBuckets = (List<ElasticBuckets>) mvcResult.getModelAndView().getModel().get("grouping");

    assertThat(histories.getContent().get(0).getUrl()).isEqualTo("/accounts");
    assertThat(histories.getContent().get(0).getFullUrl()).isEqualTo("http://localhost:8080/accounts");
    assertThat(histories.getContent().get(0).getIp()).isEqualTo("127.0.0.1");
    assertThat(histories.getContent().get(0).getNavigation()).isEqualTo("USER");

    assertThat(elasticBuckets.get(0).getKey()).isEqualTo("/accounts");
    assertThat(elasticBuckets.get(0).getValue()).isEqualTo(100L);
  }
}