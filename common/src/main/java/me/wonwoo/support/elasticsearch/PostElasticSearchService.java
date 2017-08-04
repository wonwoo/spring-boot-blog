package me.wonwoo.support.elasticsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
@RequiredArgsConstructor
public class PostElasticSearchService {

  private final ElasticsearchTemplate elasticsearchTemplate;
  private final ObjectMapper objectMapper;

  private static final String BOOSTED_TITLE_FIELD = "post_title^3";
  private static final String TITLE_FIELD = "post_title";
  private static final String POST_CONTENT_FILTERED = "post_content_filtered";
  private static final String ID_FIELD = "ID";
  private static final String DATE_FIELD = "post_date";

  public Page<WpPosts> wpPosts(Pageable pageable) {
    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
      .withPageable(pageable).build();
    return elasticsearchTemplate.queryForPage(searchQuery, WpPosts.class);
  }
  private static MultiMatchQueryBuilder matchTitleContent(String queryTerm) {
  	  return QueryBuilders
			  .multiMatchQuery(queryTerm, BOOSTED_TITLE_FIELD, BOOSTED_TITLE_FIELD, POST_CONTENT_FILTERED)
			  .fuzziness(Fuzziness.ONE)
			  .minimumShouldMatch("30%");
  }
  public Page<WpPosts> searchWpPosts(String q, Pageable pageable) {

    BoolQueryBuilder query = QueryBuilders.boolQuery()
	  	    .must(matchTitleContent(q))
		    .boost(3f);

    final QueryBuilder builder = boolQuery()
      .must(query);
    final SearchQuery searchQuery = new NativeSearchQueryBuilder()
      .withPageable(pageable)
      .withSearchType(SearchType.DEFAULT)
      .withSort(SortBuilders.scoreSort())
      .withHighlightFields(new HighlightBuilder.Field(POST_CONTENT_FILTERED)
          .preTags("<highlight>")
		  .order("score")
		  .requireFieldMatch(false)
          .postTags("</highlight>")
          .fragmentSize(Integer.MAX_VALUE)
          .numOfFragments(0),
        new HighlightBuilder.Field(TITLE_FIELD)
          .preTags("<highlight>")
          .postTags("</highlight>")
		  .order("score")
		  .requireFieldMatch(false)
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
          final WpPosts wpPosts = objectMapper.convertValue(searchHit.getSource(), WpPosts.class);
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
      .withPageable(new PageRequest(0, 4))
      .withFields(ID_FIELD, TITLE_FIELD, DATE_FIELD)
      .withQuery(queryStringQueryBuilder)
      .build();
    return elasticsearchTemplate.queryForList(searchQuery, WpPosts.class);
  }

  public void update(String id, WpPosts index) {
    IndexRequest indexRequest = new IndexRequest();
    indexRequest.source("post_title", index.getPostTitle(),
            "post_content", index.getPostContent() ,
            "post_content_filtered", index.getPostContentFiltered());
    UpdateQuery updateQuery = new UpdateQueryBuilder().withId(id)
            .withClass(WpPosts.class).withIndexRequest(indexRequest).build();
    elasticsearchTemplate.update(updateQuery);
  }
}

