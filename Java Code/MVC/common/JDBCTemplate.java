package common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/*
	// DB 연결하는 곳
	public static Connection getConnection() {
		Connection conn = null;

		try {
			String url = "jdbc:mysql://localhost:3306/my_emp";
			String user = "mydb";
			String password = "admin1234";

			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);

			if (!conn.isClosed()) {
				System.out.println("연결 중임");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}
	*/
	
	// properties로 DB 연결하기
	public static Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		
		try (InputStream input = JDBCTemplate.class.getClassLoader().getResourceAsStream("db.properties")) {
			
			if (input == null) {
				throw new RuntimeException("db.properties 파일 없음");
			}
			
			prop.load(input);
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			
			Class.forName(driver);	// properties 사용하면 driver 명명해야함

			conn = DriverManager.getConnection(url + "?user=" + user + "&password=" + password);

			conn.setAutoCommit(false);

			if (!conn.isClosed()) {
				System.out.println("연결 중임");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}

	// DB close 하는 곳
	public static void Close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("DB 연결 닫기 오류:" + e.getSQLState() + "\t" + e.getMessage());
			}
		}
	}

	public static void Close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println("명령 오류: " + e.getSQLState() + "\t" + e.getMessage());
			}
		}
	}

	public static void Close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("쿼리 리턴 오류:" + e.getSQLState() + "\t" + e.getMessage());
			}
		}
	}

	// 트랙잭션 처리
	// COMMIT
	public static void Commit(Connection con) {
		if (con != null) {
			try {
				con.commit();
			} catch (SQLException e) {
				System.out.println("DB 연결 닫기 오류:" + e.getSQLState() + "\t" + e.getMessage());
			}
		}
	}
	
	// ROLLBACK
	public static void Rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				System.out.println("DB 연결 닫기 오류:" + e.getSQLState() + "\t" + e.getMessage());
			}
		}
	}
}
