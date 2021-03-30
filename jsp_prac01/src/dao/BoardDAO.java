package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import db.JdbcUtil;
import vo.BoardBean;

public class BoardDAO { //오라클에 쿼리문을 전달하는 역할을 할 예정
	DataSource ds;
	Connection con;
	private static BoardDAO/*자료형*/ boardDAO/*객체 변수*/;
	
	//생성자
	private BoardDAO() {
		
	}
	
	//싱글톤 생성
	public static BoardDAO getInstance() {
		//singleton 1개의 객체 공유
		if (boardDAO == null) {
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}
	
	//Connection con 생성한것을 받아서 초기화
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	//실제로 쿼리문을 만들어서 db에 전달, 폼에서 작성한 게시물을 오라클에 저장하기.
	public int insertArtcle(BoardBean article) {
		PreparedStatement pstmt = null; 
		//PreparedStatement : 자바에서 생성된 쿼리문을 db로 전달
		String sql = "insert into board values(" 
		+ "(select nvl(max(board_num),0)+1 from "
		+ "board),"
		+ "?,?,?,?,?,(select nvl(max(board_num),0)+1 "
		+ "from "
		+ "board),?,?,?,sysdate)";
		//+ 는 길어서 줄바꿈 때문에 넣었다. 
		//insert into board values((select nvl(max(board_num),0)+1 from "board),?,?,?,?,?,(select nvl(max(board_num),0)+1 from board),?,?,?,sysdate);
		//nvl(max(board_num),0)+1 다음 게시물은 현재 마지막 게시물의 + 1
		//시퀀스를 대신하는 서브쿼리 이용.
		int insertCount = 0;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBOARD_NAME());
			pstmt.setString(2, article.getBOARD_PASS());
			pstmt.setString(3, article.getBOARD_SUBJECT());
			pstmt.setString(4, article.getBOARD_CONTENT());
			pstmt.setString(5, " "); //첨부파일인데 불완전한 첨부파일 처리로 사용이 바람직 하지 않습니다. ""주면, 널로 처리됨
			//해당 컬럼이 널을 허용하지 않는다면,	새로운 레코드는 등록되지 않음.
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			
			insertCount = pstmt.executeUpdate(); //insert
			// 등록된 레코드 갯수 리턴
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return insertCount;		
	}

	public ArrayList<BoardBean> selectArticleList() {

		PreparedStatement pstmt = null;
		ResultSet rs = null; //쿼리 수행 결과 얻어지는 여러개의 집합을 가르킴 (ResultSet : 결과 집합)
		String sql = "select * from (select p.*, row_number() over (order by board_re_ref desc, board_re_seq) as rnum from board p) ";
		
		//System.out.println("sql: "+ sql);
		ArrayList<BoardBean> aList = new ArrayList<>(); //sql에 게시물들을 ArrayList에 차곡차곡 가져 온다.
		BoardBean bRow = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); //select
			
			while(rs.next()) {
				bRow = new BoardBean(); 
				bRow.setBOARD_NUM(rs.getInt("board_num"));
				bRow.setBOARD_NAME(rs.getString("board_name"));
				bRow.setBOARD_SUBJECT(rs.getString("board_subject"));
				bRow.setBOARD_CONTENT(rs.getString("board_content"));
				bRow.setBOARD_FILE(rs.getString("board_file"));
				bRow.setBOARD_RE_REF(rs.getInt("board_re_ref"));
				bRow.setBOARD_RE_LEV(rs.getInt("board_re_lev"));
				bRow.setBOARD_RE_SEQ(rs.getInt("board_re_seq"));
				bRow.setBOARD_READCOUNT(rs.getInt("board_readcount"));
				bRow.setBOARD_DATE(rs.getDate("board_date"));
				
				aList.add(bRow);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return aList;
	}
}
