package me.wonwoo.blog;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.wonwoo.Indexer;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;

@Component
@RequiredArgsConstructor
public class BlogIndexer implements Indexer<WpPost> {

	private final WpPostsRepository wpPostsRepository;
	private final PostElasticSearchService postElasticSearchService;

	@Override
	public Iterable<WpPost> indexItems() {
		return wpPostsRepository.findByPostTypeAndPostStatusAndIndexingIsNull("post", "publish");
	}

	//TODO indexing update
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
}
