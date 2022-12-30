package com.web.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.member.model.dao.MemberDao;
import com.web.member.model.vo.Member;
import static com.web.common.JDBCTemplate.close;

public class AdminDao {
	
	private Properties sql=new Properties();
	
	public AdminDao() {
		String path=AdminDao.class.getResource("/sql/admin/admin_sql.properties").getPath();
		try {
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Member> searchMemberList(Connection conn,int cPage,int numPerpage){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Member> result=new ArrayList();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("searchMemberList"));
			//시작값
			pstmt.setInt(1, (cPage-1)*numPerpage+1);
			//끝값
			pstmt.setInt(2, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				result.add(MemberDao.getMember(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	public int selectMemberCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberCount"));
			rs=pstmt.executeQuery();
			if(rs.next()) count=rs.getInt(1);//rs.getInt("cnt");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return count;
	}
	
	public List<Member> searchMemberList(Connection conn,
			String type, String keyword,int cPage,int numPerpage){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Member> result=new ArrayList();
		String query=sql.getProperty("searchMemberListKeyword");
		//SELECT * FROM MEMBER WHERE $COL LIKE ?
		query=query.replace("$COL", type);
//		System.out.println(query);
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, type.equals("gender")?
					keyword:"%"+keyword+"%");
			pstmt.setInt(2, (cPage-1)*numPerpage+1);
			pstmt.setInt(3, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				result.add(MemberDao.getMember(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
		
	}
	
	
	public int selectMemberCount(Connection conn, String type, 
			String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		String query=sql.getProperty("selectMemberCountKeyword");
		query=query.replace("$COL", type);
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1,"%"+keyword+"%");
			rs=pstmt.executeQuery();
			if(rs.next()) result=rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	
	
}
