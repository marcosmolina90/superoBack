package security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
		return;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers",
				"origin, content-type, accept, x-requested-with, Access-Control-Allow-Credentials,Access-Control-Allow-Headers, Access-Control-Allow-Method, Cache-Control, Expires   ");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpReponse = (HttpServletResponse) response;

		if ("OPTIONS".equals(httpRequest.getMethod())) {
			chain.doFilter(httpRequest, httpReponse);
			return;
		}
		chain.doFilter(httpRequest, httpReponse);
		return;

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		return;
	}

}
