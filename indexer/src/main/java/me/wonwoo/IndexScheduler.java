package me.wonwoo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.wonwoo.blog.BlogIndexer;

@Component
@RequiredArgsConstructor
public class IndexScheduler {
	private static final long ONE_HOUR = 1000 * 60 * 60;

	private final IndexerService indexerService;
	private final BlogIndexer blogIndexer;

	@Scheduled(fixedDelay = ONE_HOUR, initialDelayString = "${search.indexer.delay:0}")
	public void indexBlogPosts() {
		indexerService.index(blogIndexer);
	}
}
