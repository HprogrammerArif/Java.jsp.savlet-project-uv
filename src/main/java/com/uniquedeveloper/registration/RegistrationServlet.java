package com.uniquedeveloper.registration;
import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// Database connection details
		String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbUser = "system";  // Replace with your actual schema username
		String dbPass = "arif";    // Replace with your actual password

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish connection
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			//prepare sql insert statement
			//String sql = "INSERT INTO users(uname,upwt,uemail,umobile) VALUES(?,?,?,?)";
			//pstmt = conn.prepareStatement(sql);
			
			pstmt = conn.prepareStatement("INSERT INTO users(uname,upwd,uemail,umobile) VALUES(?,?,?,?)");
			
			pstmt.setString(1, uname);   // uname
			pstmt.setString(2, upwd);    // upwt
			pstmt.setString(3, uemail);  // uemail
			pstmt.setString(4, umobile); // umobile


			
			
			int rowCount = pstmt.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			
			if(rowCount>0) {
				request.setAttribute("status", "success");
				
				//out.println("<h2>Data successfully inserted!!</h2>");
				//response.setHeader("Refresh","2; URL=Form.jsp");
			} else {
				request.setAttribute("status", "failed");
				//out.println("<h2>Failed to insert data!!</h2>");
			}
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
