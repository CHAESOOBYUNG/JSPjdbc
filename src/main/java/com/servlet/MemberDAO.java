package com.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.jsp.tagext.TryCatchFinally;

public class MemberDAO {

	// 1. 싱글톤패턴을 이용한 클래스
	//스스로 객체를 생성하고 1개로 제한
	private static MemberDAO dao = new MemberDAO();	

	//2. 생성자에 private을 붙입니다
	private MemberDAO() {


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	//3. 외부에서 객체생성을 요구할 때 멤버변수 dao를 반환합니다.
	public static MemberDAO getInstance() {
		return dao;
	}

	/////////////////////////////////////////////////////////////
	//데이터베이스 연결주소
	String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	String uid = "jsp";
	String upw = "jsp";


	//example - id를 받아서 회원정보 반환

	public MemberVO getUserInfo(String id) {
		MemberVO vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;

		String sql = "select * from member where id = ?";

		try {
			conn = DriverManager.getConnection(url, uid, upw);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if(rs.next()) {

				String ids = rs.getString("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String region = rs.getString("region");

				vo = new MemberVO(ids, null, name, region, gender);	

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e2) {

			}
		}


		return vo;
	}

	//insert
	public int userInsert(MemberVO vo) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into member values(?, ?, ?, ?, ?)";

		try {
			conn = DriverManager.getConnection(url, uid, upw);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId() );
			pstmt.setString(2, vo.getPw() );
			pstmt.setString(3, vo.getName() );
			pstmt.setString(4, vo.getRegion() );
			pstmt.setString(5, vo.getGender() );

			result = pstmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e2) {

			}
		}

		return result;
	}

	//login
	public MemberVO login(String id, String pw) {
		MemberVO vo = null;

		//sql
		String sql = "SELECT * FROM MEMBER WHERE ID = ? AND PW = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		//실행
		try {

			//conn
			conn = DriverManager.getConnection(url, uid, upw);

			//pstmt
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			//rs.next()

			if(rs.next()) { //다음행이 없다면 실행하지 않음

				String id2 = rs.getString("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String region = rs.getString("region");


				vo = new MemberVO(id2, null, name, region, gender);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e2) {

			}
		}




		return vo;
	}

	//update메서드
	public int update(String id, String pw, String name, String region, String gender) {

		int result = 0; 

		String sql = "UPDATE MEMBER SET PW = ?, NAME = ?, REGION = ?, GENDER = ? WHERE ID = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		//rs는 select가 아니기 때문에 필요x

		try {

			conn = DriverManager.getConnection(url, uid, upw);

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pw);
			pstmt.setString(2, name);
			pstmt.setString(3, region);
			pstmt.setString(4, gender);
			pstmt.setString(5, id);

			result = pstmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();

			} catch(Exception e2) {

			}


		}
		
		return result; 

	}
	
	//delete
	public int delete(String id) {
		int result = 0;
		
		String sql = "delete from member where id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn= DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} 
		
		return result;
		
	}
	
	

}
