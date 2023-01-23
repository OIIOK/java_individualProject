//오라클과 mysql을 처리하는 DB매니저를 정의

package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	// 싱글톤으로 선언
	private static DBManager instance;
	private Connection connection;

	// 오라클용
	String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
	String oracle_user = "javase";
	String oracle_pass = "1234";

	// mysql용
	String mysql_driver = "com.mysql.jdbc.Driver";
	String mysql_url = "jdbc:mysql://localhost:3306/java";
	String mysql_user = "root";
	String mysql_pass = "1234";

	// --------------------------------------------------------------------------------

	// 생성자 선언(private -> new 못하고 getter메소드 써야 함)
	private DBManager() {
		connectOracle();
	}

	// --------------------------------------------------------------------------------

	// 오라클 접속용
	public void connectOracle() {
		try {
			// 오라클 드라이버 로드
			Class.forName(oracle_driver);
			// 오라클 접속
			connection = DriverManager.getConnection(oracle_url, oracle_user, oracle_pass);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// mysql 접속용
	public void connectMysql() {
		try {
			// mysql 드라이버 로드
			Class.forName(mysql_driver);
			// mysql 접속
			connection = DriverManager.getConnection(mysql_url, mysql_user, mysql_pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// getter
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// getter
	public Connection getConnection() {
		return connection;
	}

	// DB 관련 자원 닫기(종료 시 사용)
	public void release(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// DB 관련 자원 닫기(select)
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}