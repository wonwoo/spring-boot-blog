package me.wonwoo.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Test;

import lombok.Data;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticsearchConfigTests {

	@Test
	public void localDatetimeEntityMapperMapToStringTest() throws IOException {
		ElasticsearchConfig.LocalDatetimeEntityMapper localDatetimeEntityMapper = new ElasticsearchConfig.LocalDatetimeEntityMapper();
		LocalDateTime localDateTime = LocalDateTime.of(2017, 11,21,10,0,0);
		String mapToString = localDatetimeEntityMapper.mapToString(new User("wonwoo", localDateTime));
		assertThat(mapToString).contains("wonwoo", "2017-11-21T10:00:00");
	}

	@Test
	public void localDatetimeEntityMapperMapToObjectTest() throws IOException {
		String json = "{\"name\":\"wonwoo\",\"localDateTime\":\"2017-11-21T10:00:00\"}";
		ElasticsearchConfig.LocalDatetimeEntityMapper localDatetimeEntityMapper = new ElasticsearchConfig.LocalDatetimeEntityMapper();
		LocalDateTime localDateTime = LocalDateTime.of(2017, 11,21,10,0,0);
		User mapToObject = localDatetimeEntityMapper.mapToObject(json, User.class);
		assertThat(mapToObject.getLocalDateTime()).isEqualTo(localDateTime);
		assertThat(mapToObject.getName()).isEqualTo("wonwoo");
	}

	@Data
	private static class User {
		private String name;
		private LocalDateTime localDateTime;

		User() {

		}

		User(String name, LocalDateTime localDateTime) {
			this.name = name;
			this.localDateTime = localDateTime;
		}
	}
}