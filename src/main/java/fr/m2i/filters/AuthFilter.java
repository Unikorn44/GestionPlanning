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
import javax.servlet.http.HttpServletRequest;

import fr.m2i.service.TokenService;

@WebFilter({"/api/user/*", "/api/event/*"})
public class AuthFilter implements Filter {
	
	private TokenService tokenService;
	       
    public AuthFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("-------------------------- filtre");
		this.tokenService = new TokenService();
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		
		System.out.println("FiltreIci");
		
		String headerName="";
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				headerName=headerNames.nextElement();
				String headerValue = httpRequest.getHeader(headerName);
				System.out.println("headerNames " + headerValue);
				if(headerName.equals("authorization")) {
					System.out.println("ifToken");
					String tokenBaerer = headerValue;
					String token = tokenBaerer.substring(tokenBaerer.indexOf(" ") + 1);
					System.out.println("tokenBearer");
					if(tokenService.isValid(token)) {
						System.out.println("token valid");
						chain.doFilter(request, response);						
					} else {
						System.out.println("erreur");
					}
					break;
				}
			}
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
