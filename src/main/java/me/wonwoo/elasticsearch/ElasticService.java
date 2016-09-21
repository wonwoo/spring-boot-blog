//package me.wonwoo.elasticsearch;
//
//import lombok.RequiredArgsConstructor;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.ResultsExtractor;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
//import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
//
//@Service
//@RequiredArgsConstructor
//public class ElasticService {
//
//    private final ElasticsearchTemplate elasticsearchTemplate;
//
//    private final static String INDEX = "blog";
//    private final static String TYPE = "history";
//
//    public List<ElasticBuckets> findByGroupByNavigation() {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices(INDEX).withTypes(TYPE)
//                .addAggregation(terms("groupByState").field("navigation"))
//                .build();
//        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//            @Override
//            public Aggregations extract(SearchResponse response) {
//                return response.getAggregations();
//            }
//        });
//        StringTerms groupByState = aggregations.get("groupByState");
//        List<Terms.Bucket> buckets = groupByState.getBuckets();
//        return buckets.stream().map(bucket -> new ElasticBuckets(bucket.getKeyAsString(), bucket.getDocCount())).collect(toList());
//    }
//}
