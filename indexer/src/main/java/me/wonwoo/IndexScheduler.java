package me.wonwoo;

import me.wonwoo.blog.FullIndexer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IndexScheduler {
  private static final long ONE_HOUR = 1000 * 60;// * 60;
  private static final long ONE_DAY = ONE_HOUR * 24;
  private final IndexerService indexerService;
  private final FullIndexer fullIndexer;
//  private final BlogUpdateIndexer blogUpdateIndexer;

//  @Scheduled(fixedDelay = ONE_DAY, initialDelayString = "${search.indexer.delay:0}")
//  public void fullIndexing() {
//    indexerService.index(fullIndexer);
//  }


//  @Scheduled(fixedDelay = ONE_HOUR, initialDelayString = "${search.indexer.delay:0}")
//  public void indexBlogPosts() {
//    indexerService.index(blogIndexer);
//  }

  //TODO update
//	@Scheduled(fixedDelay = ONE_HOUR, initialDelayString = "${search.indexer.delay:0}")
//	public void indexBlogUpdatePost() {
//		indexerService.index(blogUpdateIndexer);
//	}

}
