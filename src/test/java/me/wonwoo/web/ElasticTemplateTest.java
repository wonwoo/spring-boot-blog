//package me.wonwoo.web;
//
//import me.wonwoo.elasticsearch.ElasticBuckets;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
//import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ElasticTemplateTest {
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Test
//    public void test(){
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices("blog").withTypes("history")
//                .addAggregation(terms("group_by_state").field("navigation"))
//                .build();
//        // when
//        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);
//        System.out.println(aggregations);
//
//        StringTerms group_by_state = aggregations.get("group_by_state");
//        List<Terms.Bucket> buckets = group_by_state.getBuckets();
//        buckets.stream().map(bucket -> new ElasticBuckets(bucket.getKeyAsString(), bucket.getDocCount())).collect(toList());
//    }
//
//}
