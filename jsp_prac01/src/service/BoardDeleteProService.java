package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;

public class BoardDeleteProService {

	public boolean deleteArticle(int board_num) throws Exception {
		boolean isdel = false;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		//커넥션 얻어서 호출하기
		
		int delCnt = bDAO.deleteArtcle(board_num);
		
		if(delCnt > 0) {
			JdbcUtil.commit(con);
			isdel = true;
		} else {
			JdbcUtil.rollback(con);
		} 
		JdbcUtil.close(con);
		
		return isdel;
	}

}
