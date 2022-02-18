package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.UserAccount;
import vo.UserMemo;

public class MemoDaoManager implements MemoInterface {
	
	Connection conn; 
	PreparedStatement pstmt; 
	ResultSet rs; 
	
	@Override
	public boolean insertAccount(UserAccount user) { //회원가입
		
		String query ="insert into useraccount values(?, ?, ?)";
		
		try{
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getId());
			pstmt.setString(3, user.getPassword());
	
			pstmt.executeUpdate();
			
			return true;

		} catch (SQLException e) { //이미 가입된 아이디 존재하는 경우, 
			System.out.println("회원가입 SQL문 오류"); 
			return false; 
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	
	@Override
	public UserAccount loginAccount(UserAccount user) { //로그인
		String sql = "select * from useraccount where id=? and password =?";
		
		try {
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPassword());
			
			rs = pstmt.executeQuery();
			if(rs.next()){ 
				return user; 
			} else { 
				return null; 
			}
			
		} catch (SQLException e) {
			System.out.println("login sql문 오류"); 
			return null;
			
		}finally {
			try {
				if(rs!=null) { rs.close(); }
				if(pstmt!=null) { pstmt.close(); }
				if(conn!=null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	
	
	@Override
	public boolean deleteAccount(UserAccount user) { //회원탈퇴
		
		String query ="delete from useraccount where id=? and password=?";
		
		try{
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPassword());
	
			pstmt.executeUpdate();
			
			return true;

		} catch (SQLException e) { 
			System.out.println("회원탈퇴 SQL문 오류"); 
			return false; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
			
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	
	
	@Override
	public boolean insertMemo(UserMemo memo) { //메모등록(글쓰기)
		
		String query ="insert into usermemo values(memoseq.nextval, ?, ?, sysdate, ?)";
		
		try{
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, memo.getTitle());
			pstmt.setString(2, memo.getContent());
			pstmt.setString(3, memo.getId() );
	
			pstmt.executeUpdate();
			return true;
			
		} catch (SQLException e) { 
			System.out.println("메모등록 SQL문 오류"); 
			return false; 
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}		
	}
	
	
	
	
	
	@Override
	public ArrayList<UserMemo> searchMemo(String searchword, String id) {
		
		ArrayList<UserMemo> memoList = new ArrayList<UserMemo>();
		
		try {
			conn = ConnectionManager.getConnection(); 
			String query ="select * from usermemo where id=? and (title LIKE ? or content like ?)";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setString(1, id);
			pstmt.setString(2,"%"+ searchword + "%"); 
			pstmt.setString(3,"%"+ searchword + "%");
			
			rs = pstmt.executeQuery(); 
		
			while(rs.next()) { //if를 쓰면 한행만 출력되는 문법
                		UserMemo userMemo = new UserMemo(); 
                	
                		userMemo.setMemoseq(rs.getString("memoseq"));
                		userMemo.setTitle(rs.getString("title"));
                		userMemo.setContent(rs.getString("content"));
                		userMemo.setIndate(rs.getString("indate")); 
                		userMemo.setId(rs.getString("id")); 
                		
               			memoList.add(userMemo); 
    		}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) { rs.close(); }
				if(pstmt!=null) { pstmt.close(); }
				if(conn!=null) { conn.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memoList; //메모 검색
	}
	
	

	@Override
	public ArrayList<UserMemo> getMemoList(String id) { //3.메모전체보기
		ArrayList<UserMemo> memoList = new ArrayList<UserMemo>();
	
		try {
			conn = ConnectionManager.getConnection(); 
			String query ="select * from userMemo where id=?";
			
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery(); 
		
			
			while(rs.next()) { //while을 쓰면 여러행이 출력. 
                		UserMemo userMemo = new UserMemo(); 
                	
                		userMemo.setMemoseq(rs.getString("memoseq"));
                		userMemo.setTitle(rs.getString("title"));
                		userMemo.setContent(rs.getString("content"));
                		userMemo.setIndate(rs.getString("indate")); 
                		userMemo.setId(rs.getString("id")); 
                		
                		memoList.add(userMemo); 
    			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
				if(conn!=null) {conn.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memoList;	
	}


	@Override
	public UserMemo searchOneMemo(String seq, String id) {
		
		UserMemo userMemo = new UserMemo(); 
		
		try {
			conn = ConnectionManager.getConnection(); 
			String query ="select * from userMemo where id=? and memoseq=?";
			
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, id);
			pstmt.setString(2, seq);
			
			rs = pstmt.executeQuery(); 
			
			if(rs.next()) { //if를 쓰면 한행만 출력되는 문법
				
				userMemo.setMemoseq(rs.getString("memoseq"));
				userMemo.setTitle(rs.getString("title"));
				userMemo.setContent(rs.getString("content"));
				userMemo.setIndate(rs.getString("indate")); 
				userMemo.setId(rs.getString("id")); 
				
				return userMemo; 
			} else{ 
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
			
		} finally {
			try {
				if(rs!=null) { rs.close();}
				if(pstmt!=null) { pstmt.close();}
				if(conn!=null) { conn.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean updateMemo(String seq, UserMemo memo) { //메모수정
		
		String query ="update usermemo set title=?, content=? where memoseq=?";
		
		try{
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, memo.getTitle());
			pstmt.setString(2, memo.getContent());
			pstmt.setString(3, seq);
	
			pstmt.executeUpdate();
			return true;
			
		} catch (SQLException e) { 
			e.printStackTrace();
			System.out.println("메모수정 SQL문 오류"); 
			return false; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}		
	}

	
	@Override
	public boolean deleteMemo(String seq) { //메모 삭제
		
		String query = "delete from usermemo where memoseq=?"; 
		
		try{
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, seq);
	
			pstmt.executeUpdate();
			
			return true;

		} catch (SQLException e) { 
			System.out.println("메모삭제 SQL문 오류"); 
			return false; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
			
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	
	@Override
	public boolean deleteAllMemo(String id) { //모든 메모 삭제
		
		String query = "delete from usermemo where id=?"; 
		
		try{
			conn = ConnectionManager.getConnection(); 
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, id);
	
			pstmt.executeUpdate();
			
			return true;

		} catch (SQLException e) { 
			System.out.println("모든메모삭제 SQL문 오류"); 
			return false; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
			
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	

	
	
}
