package me.wonwoo;

import me.wonwoo.blog.BlogFullIndexer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.wonwoo.blog.BlogIndexer;
import me.wonwoo.blog.BlogUpdateIndexer;

@Component
@RequiredArgsConstructor
public class IndexScheduler {
  private static final long ONE_HOUR = 1000 * 60 * 60;

  private final IndexerService indexerService;
  private final BlogIndexer blogIndexer;
  private final BlogUpdateIndexer blogUpdateIndexer;
  private final BlogFullIndexer blogFullIndexer;

  @Scheduled(cron = "0 0 3 * * ?")
  public void indexFullPostPosts() {
    indexerService.index(blogFullIndexer);
  }

//  @Scheduled(fixedDelay = ONE_HOUR, initialDelayString = "${search.indexer.delay:0}")
//  public void indexBlogPosts() {
//    indexerService.index(blogIndexer);
//  }
//
//  @Scheduled(fixedDelay = ONE_HOUR, initialDelayString = "${search.indexer.delay:0}")
//  public void indexBlogUpdatePost() {
//    indexerService.index(blogUpdateIndexer);
//  }

}
