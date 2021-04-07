package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtil {
//	Connection conn = null;
//	      Context init = new InitialContext();
//	      DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
//	      conn = ds.getConnection();
	
	
	//앞으로 모델2 게시판에서 디비 연결처리는 아래 메소드 사용.
	//static 으로 만들어서 여러 클래스에서 공유.
	public static Connection getConnection() throws Exception { //Connection : 데이터베이스와 연결하는 객체입니다.
		Connection conn = null;
		Context init = new InitialContext(); //InitialContext : 연결하려는 준비단계
		//Context : 문맥, 흐름, 제어권, 프로그램 흐르망 제어권을 가진 객체.
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB"); //lookup :
		//DataSource
		//오라클 자원에 연결할 수 있는 객체 생성.
		conn = ds.getConnection();
		//그 객체 ds를 통해서 오라클과 자바의 연결 다리를 생성. conn
		conn.setAutoCommit(false); //자동 커밋하지 않음
		
		return conn;
	}
	
	
	//데이터 베이스는 외부 자원으로 사용후, 자원 반납을 해야 함.
	//close 메소드는 사용한 연결을 닫아서 자원을 돌려주는 역할.
	public static void close(Connection con) {
		try {
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//import sql.Statement
	//Statement : 쿼리문을 담아서 실행해 주는 객체.
	//데이터를 불러올때, 읽어올떄, 삭제할때마다 close를 쓰는게 아니고 
	public static void close(Statement stmt) { //Statement에 대한 매개변수를 받는다.
		try {
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//실행된 쿼리문의 결과를 담는 ResultSet
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//쿼리문 실행 완료.
	public static void commit(Connection con) {
		try {
			con.commit(); //sql에서 봤던 commit: 최종승인
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	//commit의 반대, 전달된 쿼리문의 실행을 취소.
	public static void rollback(Connection con) {
		try {
			con.rollback(); //rollback:처음으로 돌아가자
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
