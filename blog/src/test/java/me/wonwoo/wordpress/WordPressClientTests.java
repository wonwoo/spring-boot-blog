package me.wonwoo.wordpress;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import me.wonwoo.support.sidebar.SidebarContents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class WordPressClientTests {

	private WordPressClient wordPressClient;

	private MockRestServiceServer server;

	@Before
	public void setup() {
		RestTemplate restTemplate = new RestTemplate();
		this.wordPressClient = new WordPressClient(restTemplate, new SidebarContents());
		this.server = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void findAll() {
		expectJson("https://public-api.wordpress.com/rest/v1.1/sites/aoruqjfu.fun25.co.kr/posts"
						+ "?number=1&page=1&search=tester&fields=ID,content,title,date,author,tags",
				"posts.json");

		PageRequest pageRequest = new PageRequest(0, 1);
		Page<WordPress> tester = wordPressClient.findAll(pageRequest, "tester");
		assertThat(tester.getContent()).isNotNull();
		assertThat(tester.getContent().get(0).getTitle()).isEqualTo("test title");
		assertThat(tester.getContent().get(0).getContent()).isEqualTo("<p>test content</p>\n");
		assertThat(tester.getContent().get(0).getId()).isEqualTo(1401);
	}

	@Test
	public void findOne() {
		expectJson("https://public-api.wordpress.com/rest/v1.1/sites/aoruqjfu.fun25.co.kr/posts"
						+ "/1?fields=ID,content,title,date,author,tags",
				"post.json");

		PageRequest pageRequest = new PageRequest(0, 1);
		WordPress wordPress = wordPressClient.findOne(1L);
		assertThat(wordPress).isNotNull();
		assertThat(wordPress.getTitle()).isEqualTo("test title");
		assertThat(wordPress.getContent()).isEqualTo("<body>\n" + " <p>test content</p> \n" + "</body>");
		assertThat(wordPress.getId()).isEqualTo(1834L);
	}

	private void expectJson(String url, String bodyPath) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.server.expect(requestTo(url))
				.andExpect(method(HttpMethod.GET))
				.andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
				.andRespond(withStatus(HttpStatus.OK)
						.body(new ClassPathResource(bodyPath, getClass()))
						.headers(httpHeaders));
	}

}