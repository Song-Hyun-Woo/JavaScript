package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.service.MemberService;
import com.web.member.model.vo.Member;

/**
 * Servlet implementation class UpdateMemberServlet
 */
@WebServlet("/member/updateMember.do")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 전달한 데이터로 해당 회원정보를 수정한다.
		Member m=Member.builder()
				.userId(request.getParameter("userId"))
				.userName(request.getParameter("userName"))
				.age(Integer.parseInt(request.getParameter("age")))
				.gender(request.getParameter("gender").charAt(0))
				.email(request.getParameter("email"))
				.phone(request.getParameter("phone"))
				.address(request.getParameter("address"))
				.hobby(request.getParameterValues("hobby"))
				.build();
	
		int result=new MemberService().updateMember(m);
		
		String msg="",loc="";
		if(result>0) {
			msg="회원정보수정완료";
			loc="/";
			//session에 저장된 데이터를 변경해줘야한다.
			request.getSession().setAttribute("loginMember",m);
		}else {
			msg="회원정보수정실패";
			loc="/member/memberView.do?id="+m.getUserId();
		}
		request.setAttribute("msg",msg);
		request.setAttribute("loc", loc);
		
		request.getRequestDispatcher("/views/common/msg.jsp")
		.forward(request, response);
	
	
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
