package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.service.BoardService;
import com.web.board.model.vo.Board;
import com.web.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView.do")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo=Integer.parseInt(request.getParameter("boardNo"));
		
		Cookie[] cookies=request.getCookies();
		String boardRead="";
		boolean readflag=false;
		if(cookies!=null) {
			for(Cookie c : cookies) {
				String name=c.getName();//key값
				String value=c.getValue();//value값
				if(name.equals("boardRead")) {
					boardRead=value;
					if(value.contains("|"+boardNo+"|")) {
						readflag=true;
					}
					break;
				}
			}
		}
		
		if(!readflag) {
			//쿠키에 현재 게시글번호 저장
			Cookie c=new Cookie("boardRead",(boardRead+"|"+boardNo+"|"));
			c.setMaxAge(60*60*24);
			response.addCookie(c);
		}
		
		
		Board b=new BoardService().selectBoard(boardNo,readflag);
		List<BoardComment> list=new BoardService().selectBordComment(boardNo);
		
		request.setAttribute("comments", list);
		request.setAttribute("board",b);
		
		RequestDispatcher rd=request.getRequestDispatcher("/views/board/boardView.jsp");
		rd.forward(request, response);
	
	
	
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
