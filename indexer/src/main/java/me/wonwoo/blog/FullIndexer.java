package me.wonwoo.blog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wonwoo.Indexer;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.support.elasticsearch.BlogPost;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FullIndexer implements Indexer<Post> {

    private final PostRepository postRepository;
    private final PostElasticSearchService postElasticSearchService;
    private final PostElasticMapper postElasticMapper = new PostElasticMapper();

    @Override
    public void preHandle() {
        String setting = ElasticsearchTemplate.readFileFromClasspath("settings.json");
        String mapping = ElasticsearchTemplate.readFileFromClasspath("post.json");
        postElasticSearchService.reset(BlogPost.class, setting, mapping);
    }

    @Override
    public Iterable<Post> indexItems() {
        return postRepository.findByYnOrderByRegDateDesc("Y");
    }

    @Override
    public void indexItem(Post index) {
        postElasticSearchService.save(postElasticMapper.map(index));
    }

    @Override
    public void error(Post index, Throwable e) {
        logger.error("Full Indexing error ", e);
        logger.error("id : {}, title : {} ", index.getId(), index.getTitle());
    }

    @Override
    public void postHandle() {

    }
}
