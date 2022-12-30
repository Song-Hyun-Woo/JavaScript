package com.web.board.model.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.commit;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.web.board.model.dao.BoardDao;
import com.web.board.model.vo.Board;
import com.web.board.model.vo.BoardComment;
import com.web.member.model.dao.MemberDao;
public class BoardService {

	private BoardDao dao=new BoardDao();
	
	public List<Board> selectBoardList(int cPage, int numPerpage){
		Connection conn=getConnection();
		List<Board> list=dao.selectBoardList(conn,cPage,numPerpage);
		close(conn);
		return list;
	}
	
	public int selectBoardCount() {
		Connection conn=getConnection();
		int result=dao.selectBoardCount(conn);
		close(conn);
		return result;
	}
	
	public Board selectBoard(int boardNo,boolean readflag) {
		Connection conn=getConnection();
		Board b=dao.selectBoard(conn,boardNo);
		if(b!=null&&!readflag) {
			//b.setComments(dao.selectBordComment(conn, boardNo));
			//b.setBoardWriter(new MemberDao().searchMemberId(conn, b.getBoardWriter()));
			//조회수를 증가해주기!
			int result= dao.updateReadCount(conn,boardNo);
			if(result>0) { 
				commit(conn);
				b.setBoardReadcount(b.getBoardReadcount()+1);
			}
			else rollback(conn);
		}
		close(conn);
		return b;
	}
	
	public int insertBoardComment(BoardComment bc) {
		Connection conn=getConnection();
		int result=dao.insertBoardComment(conn,bc);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	public List<BoardComment> selectBordComment(int boardNo){
		Connection conn=getConnection();
		List<BoardComment> list=dao.selectBordComment(conn,boardNo);
		close(conn);
		return list;
	}
	
	
}
