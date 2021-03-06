package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import db.JdbcUtil;
import vo.BoardBean;

public class BoardDAO {
	
	private static BoardDAO boardDAO;
	Connection con;
	
	
	public static BoardDAO getInstance() {
		// singleton 1개의 객체 공유.
		if(boardDAO == null) { //객체가 없다면 새로 생성
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}

	public void setConnection(Connection con) {
		this.con = con;	
	}
	
	//실제로 쿼리문을 만들어서 db에 전달, 폼에서 작성한 게시물을 오라클에 저장하기.
		public int insertArtcle(BoardBean article) {
			PreparedStatement pstmt = null; 
			//PreparedStatement : 자바에서 생성된 쿼리문을 db로 전달
			String sql = "insert into board values((select nvl(max(board_num),0)+1 from board),?,?,?,?,?,"
			+ "(select nvl(max(board_num),0)+1 from board),?,?,?,sysdate)";

			
			//게시물을 여러개 생성하기 위해 임시로 사용
			/*
			String sql = "insert into board values(" 
	      	+ "seq_board.NEXTVAL,"
	      	+ "?,?,?,?,?,seq_board.NEXTVAL,?,?,?,sysdate)"; 
			*/
			
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

}
