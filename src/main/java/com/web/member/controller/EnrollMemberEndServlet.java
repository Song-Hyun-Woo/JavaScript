package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.ExpandVetoException;

import com.web.common.AESEncrypt;
import com.web.member.model.service.MemberService;
import com.web.member.model.vo.Member;

/**
 * Servlet implementation class EnrollMemberEndServlet
 */
@WebServlet(name="enrollMemberEnd",urlPatterns = "/member/enrollMemberEnd.do")
public class EnrollMemberEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollMemberEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 보낸 데이터를 가져와
		//member테이블에 저장하는 기능
		request.setCharacterEncoding("utf-8");
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		String userName=request.getParameter("userName");
		int age=Integer.parseInt(request.getParameter("age"));
		String gender=request.getParameter("gender");
		String email=request.getParameter("email");
		/* 전달되 email암호화처리하기 */
		try {
			email=AESEncrypt.encryptData(email);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String address=request.getParameter("address");
		String phone=request.getParameter("phone");
		String[] hobby=request.getParameterValues("hobby");
		Member m=Member.builder()
				.userId(userId).password(password)
				.userName(userName).age(age)
				.gender(gender.charAt(0)).email(email)
				.phone(phone).address(address)
				.hobby(hobby)
				.build();
//		System.out.println(m);
		int result=new MemberService().insertMember(m);
		
//		System.out.println(result);
		//회원가입성공하면 메세지 출력 후 main화면으로 
		//회원가입 실패하면 메세지 출력 후 회원가입화면으로
		String msg="",loc="";
		if(result>0) {
			msg="회원가입 성공! 축하드립니다!";
			loc="/";
		}else {
			msg="회원가입실패~ 다시 시도해주세용!";
			loc="/member/enrollMember.do";
		}
		request.setAttribute("msg", msg);
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
