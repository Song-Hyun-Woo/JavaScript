package com.web.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteServlet
 */
@WebServlet("/notice/noticeDelete.do")
public class NoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName=request.getParameter("fileName");
		int no=Integer.parseInt(request.getParameter("no"));
		
		//DB에 있는 데이터 삭제
//		int result=new NoticeService().deleteNotice(no);
		int result=1;
		String msg="",loc="";
		if(result>0) {
			msg="공지사항을 삭제했습니다.";
			loc="/notice/noticeList.do";
			String path=getServletContext()
					.getRealPath("/upload/notice/");
			File delFile=new File(path+fileName);
			if(delFile.exists()) delFile.delete();
			
		}else {
			msg="공지사항을 삭제하지 못했습니다 다시 시도하세요.";
			loc="/notice/noticeView.do?noticeNo="+no;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
