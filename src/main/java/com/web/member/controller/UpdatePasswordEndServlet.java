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
 * Servlet implementation class UpdatePasswordEndServlet
 */
@WebServlet(name="updatePassword",urlPatterns = "/member/updatePasswordEnd")
public class UpdatePasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePasswordEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세개값 받아오기 id, 이전패스워드, 바꿀패스워드
		String userId=request.getParameter("userId");
		String oriPass=request.getParameter("password");
		String newPass=request.getParameter("password_new");

		//현재비밀번호가 맞는지 확인
		Member m=new MemberService().searchMember(userId,oriPass);
		//m이 null이면 비밀번호가 틀림, m이 null이 아니면 맞다.
		String msg="",loc="";
		if(m!=null) {
			//비밀번호 변경 로직진행
			int result=new MemberService().updatePassword(userId,newPass);
			if(result>0) {
				msg="비밀번호 변경완료";
				loc="/logout.do";
				String script="opener.location.replace('"+request.getContextPath()+"/logout.do');close();";
				request.setAttribute("script",script);
			}else {
				msg="비밀번호 변경실패";
				loc="/member/updatePassword.do?userId="+userId;
			}
		}else {
			//현재비밀번호가 다릅니다. 다시 시도하세요!
			msg="현재 비밀번호가 일치하지않습니다! 다시 시도하세요!";
			loc="/member/updatePassword.do?userId="+userId;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc",loc);
		
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
