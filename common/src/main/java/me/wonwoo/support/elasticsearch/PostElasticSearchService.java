package me.wonwoo.support.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class PostElasticSearchService {

  private final ElasticsearchTemplate elasticsearchTemplate;
  private final ObjectMapper objectMapper;
  private final RestHighLevelClient restHighLevelClient;

  private static final String BOOSTED_TITLE_FIELD = "post_title^3";
  private static final String TITLE_FIELD = "post_title";
  private static final String POST_CONTENT_FILTERED = "post_content_filtered";
  private static final String ID_FIELD = "ID";
  private static final String DATE_FIELD = "post_date";

  public Page<WpPosts> wpPosts(Pageable pageable) {
    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withPageable(pageable).build();
    AggregatedPage<WpPosts> wpPosts = elasticsearchTemplate.queryForPage(searchQuery, WpPosts.class);
    //TODO
    Aggregations aggregations = wpPosts.getAggregations();
    if (aggregations == null) {
      Field field = ReflectionUtils.findField(AggregatedPageImpl.class, "aggregations");
      ReflectionUtils.makeAccessible(field);
      ReflectionUtils.setField(field, wpPosts, InternalAggregations.EMPTY);
      return wpPosts;
    }
    return wpPosts;
  }

  private static MultiMatchQueryBuilder matchTitleContent(String queryTerm) {
    return QueryBuilders
        .multiMatchQuery(queryTerm, TITLE_FIELD, POST_CONTENT_FILTERED);
  }

  //TODO
  private List<String> analyze(String index, String text) {
    AnalyzeRequest request = new AnalyzeRequest();
    request.index(index);
    request.text(text);
    request.analyzer("post_index_analyzer");
    AnalyzeResponse analyze;
    try {
      analyze = restHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      return Collections.singletonList(text);
    }
    return analyze.getTokens()
        .stream()
        .filter(isToken())
        .map(AnalyzeToken::getTerm)
        .collect(Collectors.toList());
  }

  private Predicate<AnalyzeToken> isToken() {
      return analyzeToken -> analyzeToken.getType().equals("Noun") || analyzeToken.getType().equals("Alpha")
          || analyzeToken.getType().equals("SYNONYM");
  }

  public Page<WpPosts> searchWpPosts(String q, Pageable pageable) {
      List<String> analyzes = analyze("wordpress", q);
      BoolQueryBuilder query = QueryBuilders.boolQuery();
      for (String analyze : analyzes) {
          query.must(matchTitleContent(analyze));
      }
      final QueryBuilder builder = boolQuery()
          .must(query);
    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withPageable(pageable)
        .withSearchType(SearchType.DEFAULT)
        .withSort(SortBuilders.scoreSort())
        .withHighlightFields(new HighlightBuilder.Field(POST_CONTENT_FILTERED)
                .preTags("<highlight>")
                .order("score")
                .requireFieldMatch(true)
                .postTags("</highlight>")
                .fragmentSize(10)
                .numOfFragments(0),
            new HighlightBuilder.Field(TITLE_FIELD)
                .preTags("<highlight>")
                .postTags("</highlight>")
                .order("score")
                .requireFieldMatch(true)
                .fragmentSize(Integer.MAX_VALUE)
                .numOfFragments(0))
        .withQuery(builder).build();
    return elasticsearchTemplate.queryForPage(searchQuery, WpPosts.class, new SearchResultMapper() {
      public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        List<WpPosts> chunk = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
          if (response.getHits().getHits().length <= 0) {
            return new AggregatedPageImpl<>(Collections.emptyList());
          }
          final WpPosts wpPosts = objectMapper.convertValue(searchHit.getSourceAsMap(), WpPosts.class);
          final HighlightField postContentFiltered = searchHit.getHighlightFields().get(POST_CONTENT_FILTERED);
          if (postContentFiltered != null) {
            wpPosts.setHighlightedContent(postContentFiltered.fragments()[0].toString());
          } else {
            wpPosts.setHighlightedContent(wpPosts.getPostContentFiltered());
          }
          HighlightField postTitle = searchHit.getHighlightFields().get(TITLE_FIELD);
          if (postTitle != null) {
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

  public void save(WpPosts WpPosts) {
    IndexQuery indexQuery = new IndexQuery();
    indexQuery.setObject(WpPosts);
    elasticsearchTemplate.index(indexQuery);
  }

  public WpPosts findOne(Long id) {
    CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria(ID_FIELD).is(id));
    return elasticsearchTemplate.queryForObject(criteriaQuery, WpPosts.class);
  }

  public List<WpPosts> findRelationPosts(String q) {
    BoolQueryBuilder queryStringQueryBuilder = QueryBuilders.boolQuery()
        .should(matchQuery(TITLE_FIELD, q).boost(3))
        .should(matchQuery(POST_CONTENT_FILTERED, q));

    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withPageable(PageRequest.of(0, 4))
        .withFields(ID_FIELD, TITLE_FIELD, DATE_FIELD)
        .withQuery(queryStringQueryBuilder)
        .build();
    return elasticsearchTemplate.queryForList(searchQuery, WpPosts.class);
  }

  public void update(String id, WpPosts index) {
    IndexRequest indexRequest = new IndexRequest();
    indexRequest.source("post_title", index.getPostTitle(),
        "post_content", index.getPostContent(),
        "post_content_filtered", index.getPostContentFiltered());
    UpdateQuery updateQuery = new UpdateQueryBuilder().withId(id)
        .withClass(WpPosts.class).withIndexRequest(indexRequest).build();
    elasticsearchTemplate.update(updateQuery);
  }

  public <T> void reset(Class<T> clazz, Object setting, Object mapping) {
    this.deleteIndex(clazz);
    this.createIndex(clazz, setting);
    this.putMapping(clazz, mapping);
  }

  public <T> boolean deleteIndex(Class<T> clazz) {
    return elasticsearchTemplate.deleteIndex(clazz);
  }

  public <T> boolean createIndex(Class<T> clazz, Object setting) {
    return elasticsearchTemplate.createIndex(clazz, setting);
  }

  public <T> boolean putMapping(Class<T> clazz, Object mapping) {
    return elasticsearchTemplate.putMapping(clazz, mapping);
  }

}

