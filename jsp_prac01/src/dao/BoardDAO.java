package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public ArrayList<BoardBean> selectArticleList(int page, int limit) {

		PreparedStatement pstmt = null;
		ResultSet rs = null; //쿼리 수행 결과 얻어지는 여러개의 집합을 가르킴 (ResultSet : 결과 집합)
		String sql = "select * from (select p.*, row_number()"
		+" over (order by board_re_ref desc, "
		+"board_re_seq) as rnum from board p) "
		+"where rnum between ? and ?"; 
		//게시물의 추출이 조건이 없을때는 보여졌지만 
		//조건을 주므로 해서 일부만 표시 (rum이 ?에서부터 ?에서까지 보여주겠다.)
		
		//System.out.println("sql: "+ sql);
		ArrayList<BoardBean> aList = new ArrayList<>(); //sql에 게시물들을 ArrayList에 차곡차곡 가져 온다.
		BoardBean bRow = null;
		int startRow = (page - 1) * 10 + 1;// 읽기 시작할 row 번호.
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow); //예) 2페이지, 11~20, 11
			pstmt.setInt(2, page * limit); //예 2*10=20
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
	
	
	public int selectListCount() {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(board_num) from board"; 
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
				
		//System.out.println("dao_listCount:"+listCount);
		return listCount;
	}
	
	
	//해당 게시물로 들어가서 readcount의 해당하는 부분을 1 증가해라
	public int updateReadCount(int board_num) { 
		PreparedStatement pstmt = null;
		int cnt = 0;
		String sql = "update board set board_readcount=" 
		+ "board_readcount+1 where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			cnt = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(pstmt);
		}
		
		return cnt;
	}
	
	//하나의 게시물을 가져와서 bRow의 객체에 담고 리턴한다.
	public BoardBean selectArticle(int board_num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean bRow = null;
		String sql = "select * from board where board_num=?"; 
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
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
				//게시물 목록 보기에도 동일한 코드 존재.
				//객체 배열에 담는 부분만 빠졌음.
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
//		System.out.println(bRow.getBOARD_NAME());
//		System.out.println(bRow.getBOARD_SUBJECT());
//		System.out.println(bRow.getBOARD_CONTENT());
		return bRow;
	}

	public boolean isArticleBoardWriter(int board_num, String pass) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select board_pass from board where board_num=?"; //패스워드 조회
		boolean isWriter = false;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			//쿼리문을 구동하여 결과를 rs(커서) 객체에 전달.
			rs.next(); //실제값 가르키기
			
			//매개변수로 받은 pass와 매개변수로 받은 board_num이 일치하는 지 확인
			if(pass.equals(rs.getString("board_pass"))) { 
				isWriter = true;
				//결과적으로 비밀번호가 맞다면 true, 틀리면 false 리턴.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return isWriter;
	}

	public int modifyArtcle(BoardBean article) {
		int upCnt = 0;
		PreparedStatement pstmt = null;
		String sql = "update board set board_subject=?, board_content=? where board_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBOARD_SUBJECT());
			pstmt.setString(2, article.getBOARD_CONTENT());
			pstmt.setInt(3, article.getBOARD_NUM());
			upCnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		
		return upCnt;
	}
	
	//댓글 작성
	public int insertReplyArticle(BoardBean article) {
		//원글 답글,
		//답글 답글,
		//새로 쓰기도 하지만 끼워 넣기도 함.
		PreparedStatement pstmt = null;
		String sql ="";
		int insertCount = 0;
		int re_ref = article.getBOARD_RE_REF(); //참조
		int re_lev = article.getBOARD_RE_LEV(); //들여쓰기
		int re_seq = article.getBOARD_RE_SEQ(); //정렬 순서
		
		try {
			sql = "update board set BOARD_RE_SEQ=BOARD_RE_SEQ+1 where BOARD_RE_REF=? and BOARD_RE_SEQ>?";
			//이미 다른 답글이 있는 상태에서 참조가 같고,
			//현재 선택한 게시물보다 순서가 크다면,
			//순서값을 1씩 모두 증가
			//결론은 댓글이 달린다면 뒤에 있는 게시물들은 1씩 밀려나는 것을 표현 
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			int updateCount = pstmt.executeUpdate();
			
			//순서에 대한 정리
			if(updateCount > 0) { 
				JdbcUtil.commit(con); //디비에 변환값 반영.
			}
			JdbcUtil.close(pstmt);
			
			//
			re_seq = re_seq + 1; //선택한 글보다 순서 1증가.
			re_lev = re_lev + 1; //선택한 글보다 1칸더 들여쓰기.
			sql = "insert into board values ((select nvl(max(board_num),0)+1 from board),?,?,?,?,?,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, article.getBOARD_NAME());
			pstmt.setString(2, article.getBOARD_PASS());
			pstmt.setString(3, article.getBOARD_SUBJECT());
			pstmt.setString(4, article.getBOARD_CONTENT());
			pstmt.setString(5, ""); //답장은 파일 첨부 없음.
			pstmt.setInt(6, re_ref);
			pstmt.setInt(7, re_lev);
			pstmt.setInt(8, re_seq);
			pstmt.setInt(9, 0);
			insertCount = pstmt.executeUpdate();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return insertCount;
	}
}
