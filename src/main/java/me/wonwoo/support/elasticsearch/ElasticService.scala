package me.wonwoo.support.elasticsearch

import java.util

import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.index.query.QueryBuilders._
import org.elasticsearch.search.aggregations.AggregationBuilders._
import org.elasticsearch.search.aggregations.Aggregations
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.{ElasticsearchTemplate, ResultsExtractor}
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._

@Service
class ElasticService (val elasticsearchTemplate : ElasticsearchTemplate) {
  private val INDEX: String = "blog"
  private val TYPE: String = "history"

  def findByGroupByNavigation: util.List[ElasticBuckets] = {
    val searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery).withIndices(INDEX).withTypes(TYPE).addAggregation(terms("groupByState").field("navigation")).build
    val aggregations: Aggregations = elasticsearchTemplate query(searchQuery, new ResultsExtractor[Aggregations] {
      def extract(response: SearchResponse): Aggregations = response getAggregations
    })

    val value: StringTerms = aggregations get "groupByState"
    val buckets = value.getBuckets
    buckets.map(bucket => ElasticBuckets(bucket.getKeyAsString, bucket.getDocCount))
  }
}
