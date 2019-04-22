package me.wonwoo.blog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wonwoo.Indexer;
import me.wonwoo.domain.model.WpPost;
import me.wonwoo.domain.repository.WpPostsRepository;
import me.wonwoo.setting.IndexSettingsService;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import me.wonwoo.support.elasticsearch.WpPosts;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogFullIndexer implements Indexer<WpPost> {

    private final WpPostsRepository wpPostsRepository;
    private final PostElasticSearchService postElasticSearchService;
    private final IndexSettingsService indexSettingsService;

    @Override
    public Iterable<WpPost> indexItems() {
        String setting = indexSettingsService.getSetting();
        String mapping = indexSettingsService.getMapping();
        postElasticSearchService.reset(WpPosts.class, setting ,mapping);
        return wpPostsRepository.findByPostTypeAndPostStatus("post", "publish");
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
    public void save(WpPost index) {

    }

    @Override
    public void error(WpPost index, Throwable e) {
        logger.error("BlogIndexer error ", e);
        logger.error("id : {}, title : {} ", index.getId(), index.getPostTitle());
    }

}
