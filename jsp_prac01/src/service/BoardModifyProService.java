package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardModifyProService {

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

	public boolean updateArticle(BoardBean article) {
		// TODO Auto-generated method stub
		return false;
	}

}
