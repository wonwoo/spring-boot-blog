package me.wonwoo.service;

import me.wonwoo.domain.model.BlogHistory;
import me.wonwoo.domain.repository.BlogHistoryRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by wonwoolee on 2017. 8. 9..
 */
@RunWith(MockitoJUnitRunner.class)
public class BlogHistoryServiceTests {

  @Mock
  private BlogHistoryRepository blogHistoryRepository;

  private BlogHistoryService blogHistoryService;

  @Before
  public void setup() {
    blogHistoryService = new BlogHistoryService(blogHistoryRepository);
  }

  @Test
  public void save() {
    BlogHistory blogHistory = new BlogHistory("/accounts", "http://localhost:8080/accounts", "127.0.0.1", "USER", null, LocalDateTime.now());
    given(blogHistoryRepository.save(any(BlogHistory.class)))
        .willReturn(blogHistory);

    BlogHistory result = blogHistoryService.save(blogHistory);
    assertThat(result.getUrl()).isEqualTo("/accounts");
    assertThat(result.getFullUrl()).isEqualTo("http://localhost:8080/accounts");
    assertThat(result.getIp()).isEqualTo("127.0.0.1");
    assertThat(result.getNavigation()).isEqualTo("USER");
    assertThat(result.getDate()).isNotNull();
  }

  @Test
  public void findByDateBetween() {

    Page<BlogHistory> page = new PageImpl<>(Collections.singletonList(
        new BlogHistory("/accounts", "http://localhost:8080/accounts", "127.0.0.1", "USER", null, LocalDateTime.now())));
    given(blogHistoryRepository.findAll(any(Pageable.class))).willReturn(page);

    PageRequest request = new PageRequest(0, 10);
    Page<BlogHistory>  result = blogHistoryService.findByDateBetween(request);

    assertThat(result.getContent().get(0).getUrl()).isEqualTo("/accounts");
    assertThat(result.getContent().get(0).getFullUrl()).isEqualTo("http://localhost:8080/accounts");
    assertThat(result.getContent().get(0).getIp()).isEqualTo("127.0.0.1");
    assertThat(result.getContent().get(0).getNavigation()).isEqualTo("USER");
    assertThat(result.getContent().get(0).getDate()).isNotNull();
  }

}