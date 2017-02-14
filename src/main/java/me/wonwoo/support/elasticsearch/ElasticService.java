package me.wonwoo.support.elasticsearch;

import lombok.val;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Service
public class ElasticService {

  private final ElasticsearchTemplate elasticsearchTemplate;

  public ElasticService(ElasticsearchTemplate elasticsearchTemplate) {
    this.elasticsearchTemplate = elasticsearchTemplate;
  }

  private final String INDEX = "blog";
  private final String TYPE = "history";

  public List<ElasticBuckets> findByGroupByNavigation() {
    val searchQuery = new NativeSearchQueryBuilder()
      .withQuery(QueryBuilders.matchAllQuery())
      .withIndices(INDEX)
      .withTypes(TYPE)
      .addAggregation(terms("groupByState")
        .field("navigation"))
      .build();

    val aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);
    final StringTerms value = aggregations.get("groupByState");

    val buckets = value.getBuckets();
    return buckets.stream().map(bucket -> new ElasticBuckets(bucket.getKeyAsString(), bucket.getDocCount())).collect(toList());
  }
}
