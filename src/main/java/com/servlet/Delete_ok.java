package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Delete_ok")
public class Delete_ok extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Delete_ok() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		delete from member where id = ?
		
		1. 아이디는 세션에서 얻습니다.
		2. executeUpdate()문장으로 삭제를 진행합니다.
		3. 
		삭제 성공시에는 login.jsp로 리다이렉트,
		삭제 실패시에는 login_welcome.jsp로 리다이렉트
		*/
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		int result = dao.delete(id);
		
		if (result == 1) {
			session.invalidate();
			response.sendRedirect("login.jsp");
			
		} else {
			response.sendRedirect("login_welcome.jsp");
		}
		
		
		/*
		//데이터베이스 연결주소
	    String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	    String uid = "jsp";
	    String upw = "jsp";
	    
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    String sql = "delete from member where id = ?";
	    
	    try {
			//driver
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	
	    	//커넥션 생성
	    	conn = DriverManager.getConnection(url, uid, upw);
	    	
	    	//pstmt
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, id);
	    	
	    	//executeUpdate() 실행
	    	int result = pstmt.executeUpdate();
	    	
	    	if(result == 1) { //삭제성공
	    	    session.invalidate(); //세션을 비운다.
	    		response.sendRedirect("login.jsp"); //로그인 화면
	    	} else {
	    		response.sendRedirect("login_welcome.jsp");
	    	}
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
		*/
		
	}
    
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
