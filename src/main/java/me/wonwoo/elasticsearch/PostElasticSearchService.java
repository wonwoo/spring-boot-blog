package me.wonwoo.elasticsearch;

import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.domain.WpPosts;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@RequiredArgsConstructor
public class PostElasticSearchService {
    private final ElasticsearchTemplate elasticsearchTemplate;

    public Page<WpPosts> wpPosts(String q, Pageable pageable){

        BoolQueryBuilder queryStringQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.hasText(q)){
            queryStringQueryBuilder.must(QueryBuilders.queryStringQuery(q));
        }

        final QueryBuilder builder = boolQuery()
                .must(termQuery("post_type", "post"))
                .must(termQuery("post_status", "publish"))
                .must(queryStringQueryBuilder);
        final SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(builder).build();
        return elasticsearchTemplate.queryForPage(searchQuery, WpPosts.class);
    }

    public WpPosts findOne(Long id) {
        final QueryBuilder builder = boolQuery()
                .must(termQuery("post_type", "post"))
                .must(termQuery("post_status", "publish"))
                .must(termQuery("ID", id));
        final SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(builder).build();
//        StringQuery stringQuery = new StringQuery();

        return null;
//        return elasticsearchTemplate.queryForObject(searchQuery, WpPosts.class);

    }
}

