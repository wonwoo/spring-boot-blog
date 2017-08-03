package me.wonwoo.blog;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class BlogIndexer implements Indexer<WpPost> {

	private final WpPostsRepository wpPostsRepository;
	private final PostElasticSearchService postElasticSearchService;

	@Override
	public Iterable<WpPost> indexItems() {
		return wpPostsRepository.findByPostTypeAndPostStatusAndIndexingIsNull("post", "publish");
	}

	@Override
	public void indexItem(WpPost index) {
		WpPosts wpPosts = new WpPosts();
		wpPosts.setPostContent(index.getPostContent());
		wpPosts.setPostTitle(index.getPostTitle());
		wpPosts.setId(index.getId());
		wpPosts.setPostContentFiltered(index.getPostContentFiltered());
		wpPosts.setPostType(index.getPostType());
		wpPosts.setPostStatus(index.getPostStatus());
		wpPosts.setPostAuthor(index.getPostAuthor());
		wpPosts.setPostDate(index.getPostDate());
		postElasticSearchService.save(wpPosts);
	}

	@Override
	@Transactional
	public void save(WpPost index) {
		index.setIndexing("Y");
		wpPostsRepository.save(index);
	}

	@Override
	public void error(WpPost index, Throwable e) {
		logger.error("indexer error ", e);
		logger.error("id : {}, title : {} ", index.getId(), index.getPostTitle());
	}
}
