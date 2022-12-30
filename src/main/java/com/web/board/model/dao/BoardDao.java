package com.web.board.model.dao;

import static com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.board.model.vo.Board;
import com.web.board.model.vo.BoardComment;

public class BoardDao {
	private Properties sql=new Properties();
	
	public BoardDao() {
		String path=BoardDao.class
				.getResource("/sql/board/board_sql.properties")
				.getPath();
		try {
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Board> selectBoardList(Connection conn, int cPage, int numPerpage){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Board> list=new ArrayList();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectBoardList"));
			pstmt.setInt(1,(cPage-1)*numPerpage+1);
			pstmt.setInt(2,(cPage*numPerpage));
			rs=pstmt.executeQuery();
			while(rs.next()) {
				list.add(getBoard(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return list;
	}
	
	public int selectBoardCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectBoardCount"));
			rs=pstmt.executeQuery();
			if(rs.next()) result=rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	public Board selectBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Board b=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectBoard"));
			pstmt.setInt(1, boardNo);
			rs=pstmt.executeQuery();
			if(rs.next()) b=getBoard(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return b;
	}
	
	public int updateReadCount(Connection conn, int boardNo) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateReadCount"));
			pstmt.setInt(1, boardNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public int insertBoardComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertComment"));
			pstmt.setInt(1,bc.getBoardCommentLevel());
			pstmt.setString(2, bc.getBoardCommentWriter());
			pstmt.setString(3, bc.getBoardCommentContent());
			pstmt.setInt(4,bc.getBoardRef());
//			pstmt.setInt(5,bc.getBoardCommentRef()==0?null:bc.getBoardCommentRef());
			pstmt.setString(5,bc.getBoardCommentRef()==0?null
					:String.valueOf(bc.getBoardCommentRef()));
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public List<BoardComment> selectBordComment(Connection conn, int no){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardComment> list=new ArrayList();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectBordComment"));
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			while(rs.next()) list.add(getBoardComment(rs));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return list;
	}
	private BoardComment getBoardComment(ResultSet rs) throws SQLException{
		return BoardComment.builder()
				.boardCommentNo(rs.getInt("board_comment_no"))
				.boardCommentLevel(rs.getInt("board_comment_level"))
				.boardCommentWriter(rs.getString("board_comment_writer"))
				.boardCommentContent(rs.getString("board_comment_content"))
				.boardRef(rs.getInt("board_ref"))
				.boardCommentRef(rs.getInt("board_comment_ref"))
				.boardCommentDate(rs.getDate("board_comment_date"))
				.build();
	}
	private Board getBoard(ResultSet rs) throws SQLException{
		return Board.builder()
				.boardNo(rs.getInt("board_no"))
				.boardTitle(rs.getString("board_title"))
				.boardWriter(rs.getString("board_writer"))
				.boardContent(rs.getString("board_content"))
				.boardOriginalFilename(rs.getString("board_original_filename"))
				.boardRenamedFilename(rs.getString("board_renamed_filename"))
				.boardDate(rs.getDate("board_date"))
				.boardReadcount(rs.getInt("board_readcount"))
				.build();
	}
}