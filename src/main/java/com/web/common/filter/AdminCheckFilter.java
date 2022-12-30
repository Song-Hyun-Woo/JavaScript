package com.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.common.exception.LoginCheckException;
import com.web.member.model.vo.Member;

/**
 * Servlet Filter implementation class AdminCheckFilter
 */
@WebFilter("/admin/*")
public class AdminCheckFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AdminCheckFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpSession session=((HttpServletRequest)request).getSession(false);
		Member loginMember=(Member)session.getAttribute("loginMember");
		if(loginMember!=null&&loginMember.getUserId().equals("admin")) {
			chain.doFilter(request, response);
		}else {
//			request.setAttribute("msg", "접근할 권한이 없습니다! X|");
//			request.setAttribute("loc", "/");
//			RequestDispatcher rs=request.getRequestDispatcher("/views/common/msg.jsp");
//			rs.forward(request, response);
//			throw new IllegalAccessError("권한 없는 접근입니다.");
			throw new LoginCheckException("권한 없는 접근입니다.");
		}
		
		
		
		
		// pass the request along the filter chain
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
