package com.wugui.dataxweb.interceptor;

import com.wugui.dataxweb.controller.BaseController;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.util.JSON;
import com.wugui.dataxweb.util.JwtUtil;
import com.wugui.dataxweb.util.KlksRedisUtils;
import com.wugui.dataxweb.vo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class LoginInterceptor extends BaseController implements HandlerInterceptor {

	@Autowired
	private KlksRedisUtils redis;

	@Autowired
	private UserService userService;

	private static List<String> urlList = new ArrayList<>();

	static {
		urlList.add("/swagger-ui.html");
		urlList.add("/college/article/");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("{}登录状态拦截:{}", request.getHeaderNames().toString(), request.getRequestURL());
		log.info("handler instanceof HandlerMethod:{}", handler instanceof HandlerMethod);

		//获取到目标方法对象
		if (handler instanceof HandlerMethod) {

			HandlerMethod method = (HandlerMethod) handler;
			//取到方法上的注解
			ExcludeInterceptor methodAnnotation = method.getMethodAnnotation(ExcludeInterceptor.class);
			if (!Objects.isNull(methodAnnotation)) {
				log.info("特殊注解不拦截");
				return true;
			}
		}

		if (request.getRequestURI().indexOf(urlList.get(1)) != -1) {
			return true;
		}

		String token = request.getHeader("Authorization");
		if (StringUtils.isNotBlank(token)) {
			String[] split = token.split(" ");
			String userId = JwtUtil.getUsername(split[1]);
			if (StringUtils.isNotBlank(userId)) {
				String redisToken = redis.getToken(userId);
				if (StringUtils.isNotBlank(redisToken)) {
					return true;
				}
			}
		}
		ResponseData<T> responseData = new ResponseData<>();
		responseData.setCode(400);
		responseData.setMessage("您当前未登录，请先登录!");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(JSON.marshal(responseData));
		return false;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

	}


}

