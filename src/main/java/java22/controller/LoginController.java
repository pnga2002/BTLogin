package java22.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java22.config.MysqlConfig;
import java22.entity.NguoiDung;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//nhận tham số	
		String email = req.getParameter("email");
		String password  = req.getParameter("password");
		//chuanr bị câu query 
		String query = "select  * from NguoiDung nd WHERE  nd.email =? and nd.matkhau =?";
		Connection connection = MysqlConfig.getConnecttion();
		List<NguoiDung> listNguoiDung = new ArrayList<>();
		//truyền câu query vào connection 
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			
			//Thực thi câu query 
			/**
			 *  executeQuery : Khi câu truy vấn là câu lấy dự liệu select
			 *  executeQuery : Không phải là câu lấy dữ liệu, INSERT, Delete, UPDATE
			 */
			ResultSet resultSet = statement.executeQuery();
			//duyệt dữ liệu từ resultSet
			while(resultSet.next()) {
				NguoiDung nd = new NguoiDung();
//				resultSet.getInt("id");//lấy giá trị của cột id trong CSDL khi duyệt qua từng dòng
				nd.setId(resultSet.getInt("id"));
				nd.setFullname(resultSet.getString("fullname"));
				nd.setEmail(resultSet.getString("email"));
				nd.setFullname(resultSet.getString("fullname"));
				listNguoiDung.add(nd);
			}
			if(listNguoiDung.size()>0) {
				HttpSession session = req.getSession();
				session.setAttribute("email", email);
				session.setAttribute("password", password);
				req.setAttribute("message", "Đăng nhập thành công");
				System.out.println("Đăng nhập thành công");
				System.out.println(session.getAttribute("email"));
			}
			else {
				req.setAttribute("message", "Đăng nhập thất bại");
				
				System.out.println("Đăng nhập thất bại");
				HttpSession session = req.getSession();
				session.invalidate();
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi thực thi câu query " + e.getLocalizedMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Looic đóng kết nối " + e.getLocalizedMessage());
				}
			}
		}
		
		resp.sendRedirect("profile");
	
	}
}
