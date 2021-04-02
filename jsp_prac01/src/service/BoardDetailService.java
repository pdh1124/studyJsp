package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDetailService {

	public BoardBean getArticle(int board_num, String mode) throws Exception { //mode : 읽기모드 
		BoardBean article = null;
		Connection con = JdbcUtil.getConnection();
		//오라클과의 커넥션 만들기.
		BoardDAO bDAO = BoardDAO.getInstance();
		//싱글톤으로 dao 객체 얻어오기.
		bDAO.setConnection(con);
		// dao에서 쿼리문 처리를 할 수 있도록 커넥션 전달.
		
		//조회수 증가
		if(mode.equals("read")) {  //조회수를 읽으면 1 업카운트 해라.
			int cnt = bDAO.updateReadCount(board_num);
			//조회수에 대한 처리.
			if(cnt > 0) {
				JdbcUtil.commit(con);
			} else {
				JdbcUtil.rollback(con);
			}
		}
		
		
		article = bDAO.selectArticle(board_num);
		//게시물 내용 가져오기.
		JdbcUtil.close(con);
		
		return article;
	}

}
