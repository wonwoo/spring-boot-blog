package me.wonwoo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import me.wonwoo.blog.BlogIndexer;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexSchedulerTests {

	@MockBean
	private BlogIndexer blogIndexer;

	@MockBean
	private IndexerService indexerService;

	@Test
	public void schedulerIndexers() throws Exception {
		verify(indexerService).index(blogIndexer);
	}
}