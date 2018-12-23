package me.wonwoo;


import me.wonwoo.blog.FullIndexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexSchedulerTests {

	@MockBean
	private FullIndexer fullIndexer;

	@MockBean
	private IndexerService indexerService;

	@Test
	public void schedulerIndexers() throws Exception {
		verify(indexerService).index(fullIndexer);
	}
}