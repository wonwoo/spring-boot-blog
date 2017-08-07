package me.wonwoo.config.interceptor;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import me.wonwoo.web.NewController;

public class CommonHandlerInterceptorTests {

	private HandlerInterceptorAdapter handlerInterceptorAdapter;


	@Before
	public void setup() {
		handlerInterceptorAdapter = new CommonHandlerInterceptor();
	}

	@Test
	public void preHandle() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/test");
		request.setMethod("GET");
		MockHttpServletResponse response = new MockHttpServletResponse();
		NewController newController = new NewController(null);
		HandlerMethod handlerMethod = new HandlerMethod(newController, newController.getClass().getMethod("home", Model.class));
		handlerInterceptorAdapter.preHandle(request,response, handlerMethod);
	}
}