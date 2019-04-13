package me.wonwoo.web;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import me.wonwoo.service.GuidesService;
import me.wonwoo.support.asciidoc.DefaultGuideMetadata;
import me.wonwoo.support.asciidoc.GettingStartedGuide;
import me.wonwoo.support.asciidoc.GuideMetadata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SpringTutController.class)
public class SpringTutControllerTests extends AbstractControllerTests {

	@MockBean
	private GuidesService guidesService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void tuts() throws Exception{
		given(guidesService.findTutAllMetadata()).willReturn(Arrays.asList(new DefaultGuideMetadata()));
		MvcResult mvcResult = mockMvc
			.perform(get("/tut"))
			.andExpect(status().isOk())
			.andReturn();

		String type = (String) mvcResult.getModelAndView().getModel().get("type");
		assertThat(type).isEqualTo("tut");
		verify(guidesService).findTutAllMetadata();
	}

	@Test
	public void tut() throws Exception {
		GuideMetadata guideMetadata = new DefaultGuideMetadata("wonwoo","100","spring-blog", "test");
		GettingStartedGuide gettingStartedGuide = new GettingStartedGuide();
		gettingStartedGuide.setContent("test content");
		gettingStartedGuide.setSidebar("test sidebar");
		gettingStartedGuide.setMetadata(guideMetadata);
		given(guidesService.findTut(any())).willReturn(gettingStartedGuide);
		MvcResult mvcResult = mockMvc
			.perform(get("/tut/{project}", "spring-blog"))
			.andExpect(status().isOk())
			.andReturn();

		assertThat(mvcResult.getModelAndView().getViewName()).isEqualTo("guides/guide");
		verify(guidesService).findTut("spring-blog");
	}

}