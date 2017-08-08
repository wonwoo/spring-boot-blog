package me.wonwoo.support.elasticsearch;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticBucketsTests {

	@Test
	public void elasticBuckets() {
		ElasticBuckets elasticBuckets = new ElasticBuckets("/user", 100L);
		assertThat(elasticBuckets.getKey()).isEqualTo("/user");
		assertThat(elasticBuckets.getValue()).isEqualTo(100L);
	}

}