package fr.m2i.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import fr.m2i.service.TokenService;

@SuppressWarnings("serial")
@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {
	
	private TokenService tokenService;
	       
    public AuthFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		this.tokenService = new TokenService();
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		
		String headerName="";
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				headerName=headerNames.nextElement();
				String headerValue = httpRequest.getHeader(headerName);
				if(headerName.equals("authorization")) {
					String tokenBaerer = headerValue;
					String token = tokenBaerer.substring(tokenBaerer.indexOf(" ") + 1);
					
					tokenService.isValid(token);
					break;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
