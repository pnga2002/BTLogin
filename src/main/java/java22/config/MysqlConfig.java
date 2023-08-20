package java22.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConfig {
	//database 
		private final static String url= "jdbc:mysql://localhost:3308/crm";
		private final static String username = "root";
		private final static String password = "admin123";
		
		//tạo kêt nối tới csdl
		public static Connection getConnecttion() {
			Connection connection = null;
			//khai báo driver sử dụng giành cho CSDL tương ứng
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Lỗi kêt nối CSDL " + e.getLocalizedMessage());
			}
			return connection;
		}
}
