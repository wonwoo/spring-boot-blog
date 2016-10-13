package me.wonwoo.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.domain.WpPosts;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
@RequiredArgsConstructor
public class PostElasticSearchService {
  private final ElasticsearchTemplate elasticsearchTemplate;
  private final ObjectMapper objectMapper;

  public Page<WpPosts> wpPosts(Pageable pageable) {

    final QueryBuilder builder = boolQuery()
      .must(termQuery("post_type", "post"))
      .must(termQuery("post_status", "publish"));
    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
      .withPageable(pageable)
      .withQuery(builder).build();
    return elasticsearchTemplate.queryForPage(searchQuery, WpPosts.class);
  }

  public Page<WpPosts> searchWpPosts(String q, Pageable pageable) {

    BoolQueryBuilder queryStringQueryBuilder = QueryBuilders.boolQuery();
    if (StringUtils.hasText(q)) {
      final MatchQueryBuilder postTitle = matchQuery("post_title", q);
      queryStringQueryBuilder.should(postTitle);
      final MatchQueryBuilder postContent = matchQuery("post_content", q);
      queryStringQueryBuilder.should(postContent);
//      final MatchQueryBuilder postContent = matchQuery("post_content", q);
//      queryStringQueryBuilder.should(postContent);
    }

    final QueryBuilder builder = boolQuery()
      .must(termQuery("post_type", "post"))
      .must(termQuery("post_status", "publish"))
      .must(queryStringQueryBuilder);
    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
      .withPageable(pageable)
      .withSearchType(SearchType.DEFAULT)
      .withSort(SortBuilders.scoreSort())
      .withHighlightFields(new HighlightBuilder.Field("post_content").fragmentSize(Integer.MAX_VALUE).numOfFragments(1),
        new HighlightBuilder.Field("post_title").fragmentSize(Integer.MAX_VALUE).numOfFragments(1))
      .withQuery(builder).build();
    return elasticsearchTemplate.queryForPage(searchQuery, WpPosts.class, new SearchResultMapper() {
      public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        List<WpPosts> chunk = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
          if (response.getHits().getHits().length <= 0) {
            return new AggregatedPageImpl<>(Collections.emptyList());
          }
          final WpPosts wpPosts = objectMapper.convertValue(searchHit.getSource(), WpPosts.class);
          final HighlightField postContent = searchHit.getHighlightFields().get("post_content");
          if(postContent != null){
            wpPosts.setHighlightedContent(postContent.fragments()[0].toString());
          }else{
            wpPosts.setHighlightedContent(wpPosts.getPostContent());
          }
          HighlightField postTitle = searchHit.getHighlightFields().get("post_title");
          if(postTitle != null){
            wpPosts.setPostTitle(postTitle.fragments()[0].toString());
          }
          chunk.add(wpPosts);
        }
        if (chunk.size() > 0) {
          return new AggregatedPageImpl<>((List<T>) chunk, pageable, response.getHits().getTotalHits());
        }
        return new AggregatedPageImpl<>(Collections.emptyList());
      }
    });
  }

  public WpPosts findOne(Long id) {
    CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria("post_type").is("post").and("post_status")
      .is("publish").and("ID").is(id));
    return elasticsearchTemplate.queryForObject(criteriaQuery, WpPosts.class);

  }
}

