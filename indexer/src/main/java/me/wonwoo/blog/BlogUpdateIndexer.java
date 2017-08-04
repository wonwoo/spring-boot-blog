package me.wonwoo.blog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wonwoo.Indexer;
import me.wonwoo.domain.model.WpPost;
import me.wonwoo.domain.repository.WpPostsRepository;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogUpdateIndexer implements Indexer<WpPost> {

	private final WpPostsRepository wpPostsRepository;
	private final PostElasticSearchService postElasticSearchService;

	@Override
	public Iterable<WpPost> indexItems() {
		LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1));
		return wpPostsRepository.findByPostTypeAndPostStatusAndIndexingAndPostModifiedAfter("post", "publish", "Y", localDateTime);
	}

	@Override
	public void indexItem(WpPost index) {
		//nothing
	}

	@Override
	public void save(WpPost index) {
		WpPosts wpPosts = new WpPosts();
		wpPosts.setPostContent(index.getPostContent());
		wpPosts.setPostTitle(index.getPostTitle());
		wpPosts.setPostContentFiltered(index.getPostContentFiltered());
		postElasticSearchService.update(index.getId().toString(), wpPosts);
	}

	@Override
	public void error(WpPost index, Throwable e) {
		logger.error("BlogUpdateIndexer error ", e);
		logger.error("id : {}, title : {} ", index.getId(), index.getPostTitle());
	}
}
