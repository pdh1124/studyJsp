package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDeleteProService {

	public boolean isArticleWriter(int board_num, String pass) throws Exception {
		//게시물 번호와 패스워드를 받아서, 디비에 게시물번호가 일치하는 항목의 비밀번호를 가져와서
		//사용자가 입력한 비밀번호와 같은지 비교
		
		boolean isArticleWriter = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		
		isArticleWriter = bDAO.isArticleBoardWriter(board_num, pass);
		JdbcUtil.close(con);
		
		return isArticleWriter;
	}

	public boolean DeleteArticle(BoardBean article) throws Exception {
		//커넥션을 얻어서 초기화 해주고,
		boolean isModi = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		
		//게시물 정보를 전달해서 디비 처리 할 메소드
		int upCnt = bDAO.deleteArtcle(article); //디비 접근
		
		if(upCnt > 0) {
			JdbcUtil.commit(con);
			isModi = true;
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isModi;
	}

}
