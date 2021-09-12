package cn.ccsit.eeoi.portal.security.shiro.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.ccsit.common.utils.DateUtils;
import cn.ccsit.eeoi.common.utils.JwtUtils;
import org.springframework.web.bind.annotation.RequestMethod;

public class ShiroJwtFilter extends AuthenticatingFilter {

	private static Logger logger = LogManager.getLogger(ShiroJwtFilter.class);

	
	private JwtUtils jwtUtils;

	/**
	 * token 刷新间隔
	 */
	private int tokenRefreshInterval = 300;

	public ShiroJwtFilter() {
		logger.info("Initializing ShiroJwtFilter");
	}

	public ShiroJwtFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
		logger.info("Initializing ShiroJwtFilter");
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		return super.preHandle(request, response);
	}

	@Override
	protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
		super.postHandle(request, response);
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		String token = "";
		Cookie cookie = org.springframework.web.util.WebUtils.getCookie((HttpServletRequest) request, "token");
		if (cookie != null) {
			token = cookie.getValue();
		} else {
			System.out.println("=======================");
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			String header = httpRequest.getHeader("Authorization");
			if (StringUtils.isNotBlank(header)) {
				// token=StringUtils.removeStart(header, "Bearer ");
				token = header;
			} else {
				return null;
			}
		}
		return new ShiroJwtToken(token);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

//		if(true){
//			return true;
//		}
		if (this.isLoginRequest(request, response)) {
			return true;
		}
		boolean allowed = false;
		try {
			allowed = executeLogin(request, response);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			allowed = false;
		}
		if (allowed == false) {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			HttpServletResponse httpResponse = WebUtils.toHttp(response);
			Object ajax = httpRequest.getHeader("x-requested-with");
			if (ajax == null) {
				try {
					String loginUrl = this.getLoginUrl();
					httpResponse.sendRedirect(this.getLoginUrl());
					return false;
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
			}
		}
		return allowed || super.isPermissive(mappedValue);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setStatus(403);
		httpServletResponse.setContentType("application/json;charset=utf-8");
//		httpServletResponse.sendRedirect(";/logout");
		Cookie clearCookie=new Cookie("token",null);
		clearCookie.setMaxAge(0);
		httpServletResponse.addCookie(clearCookie);
		PrintWriter out = httpServletResponse.getWriter();
		JSONObject json = new JSONObject();
		json.put("state","403");
		json.put("msg","登录已失效，请重新登录！");
		out.println(json);
		out.flush();
		out.close();
		return false;
	}

	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		if (token instanceof UsernamePasswordToken) {
			return true;
		}
		if (token instanceof ShiroJwtToken) {
			ShiroJwtToken shiroJwtToken = (ShiroJwtToken) token;
			shiroJwtToken.getPrincipal();
			Date currTime = DateUtils.getCurrentDateTime();
			Date issuedTime = JwtUtils.getIssuedAt(shiroJwtToken.getToken());
			if (issuedTime == null) {
				return true;
			}
			long seconds = DateUtils.getDateDiffWithSecond(currTime, issuedTime);
			if (seconds > tokenRefreshInterval) { // 刷新token
				String refreshToken = jwtUtils.reSign(shiroJwtToken.getToken());
				// httpResponse.setHeader("Authorization","Bearer "+refreshToken);
				httpResponse.setHeader("Authorization", refreshToken);
			}
		}
		return true;
	}
	/**
	 * 请求异常则需要重新登录
	 */
	private void responseError(ServletResponse response, String message) {
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> result = new HashMap<>();
		result.put("status", 401);
		result.put("msg", "请先登录");
		// public static int NO_AUTHENTICATION = 401;//没有认证
//		ret.setCode(Constant.NO_AUTHENTICATION);
//		ret.setMsg(message);
//		ret.setSucceed(false);
//		String jsonString = JSONObject.toJSONString(ret);


		try {
			PrintWriter out = httpResponse.getWriter();
			JSONObject json = new JSONObject();
			json.put("state","403");
			json.put("msg","登录已失效，请重新登录！");
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {



		}
	}

//// 定义jackson对象
//private static final ObjectMapper MAPPER = new ObjectMapper();
//
//	/**
//	 * 生成自定义token
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
////	@Override
////	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
////		//获取请求token
////		jwtUtils.
////
////		return new AuthToken(token);
////	}
//
//	/**
//	 * 步骤1.所有请求全部拒绝访问
//	 *
//	 * @param request
//	 * @param response
//	 * @param mappedValue
//	 * @return
//	 */
//	@Override
//	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//		if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 步骤2，拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取 token，再调用executeLogin方法
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//		//获取请求token，如果token不存在，直接返回
//		Cookie cookie = org.springframework.web.util.WebUtils.getCookie((HttpServletRequest) request, "token");
//		String token = "";
//		if(cookie != null){
//			token = cookie.getValue();
//		}
//		if (StringUtils.isBlank(token)) {
//			HttpServletResponse httpResponse = (HttpServletResponse) response;
//			httpResponse.setContentType("application/json;charset=utf-8");
//			httpResponse.setCharacterEncoding("UTF-8");
//			Map<String, Object> result = new HashMap<>();
//			result.put("status", 401);
//			result.put("msg", "请先登录");
//			String json = MAPPER.writeValueAsString(result);
//			httpResponse.getWriter().print(json);
//			return false;
//		}
//		return executeLogin(request, response);
//	}
//
//	/**
//	 * token失效时候调用
//	 */
//	@Override
//	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		httpResponse.setContentType("application/json;charset=utf-8");
////		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
////		httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
//		httpResponse.setCharacterEncoding("UTF-8");
//		try {
//			//处理登录失败的异常
//			Throwable throwable = e.getCause() == null ? e : e.getCause();
//			Map<String, Object> result = new HashMap<>();
//			result.put("status", 401);
//			result.put("msg", "登录凭证已失效，请重新登录");
//			String json = MAPPER.writeValueAsString(result);
//			httpResponse.getWriter().print(json);
//		} catch (IOException e1) {
//		}
//		return false;
//	}

}
