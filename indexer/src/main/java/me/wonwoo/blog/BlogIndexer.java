package me.wonwoo.blog;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wonwoo.Indexer;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogIndexer implements Indexer<Post> {

	private final PostRepository postRepository;
	private final PostElasticSearchService postElasticSearchService;
	private final static String Y_FIELD = "Y";

	@Override
	public Iterable<Post> indexItems() {
		logger.info("starter indexItems");
		return postRepository.findByYnAndIndexingIsNull("Y");
	}

	@Override
	public void indexItem(Post index) {
		logger.info("starter indexItem");
		WpPosts wpPosts = new WpPosts();
		wpPosts.setPostContent(index.getContent());
		wpPosts.setPostTitle(index.getTitle());
		wpPosts.setId(index.getId().intValue());
		wpPosts.setPostContentFiltered(index.getCode());
		wpPosts.setPostDate(index.getRegDate());
		postElasticSearchService.save(wpPosts);
	}

	@Override
	@Transactional
	public void save(Post index) {
		logger.info("starter save");
		index.setIndexing(Y_FIELD);
		postRepository.save(index);
	}

	@Override
	public void error(Post index, Throwable e) {
		logger.error("BlogIndexer error ", e);
		logger.error("id : {}, title : {} ", index.getId(), index.getTitle());
	}
}
