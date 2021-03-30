package service;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardWriteProService {

	public boolean registArticle(BoardBean bo) throws Exception {
		// 액션에서 전달해준 게시물의 내용(boardBean bo)를 이용하여
		// db에 등록하기.
		// 처리결과 성공 혹은 실패 리턴.
		boolean isWriteSuccess = false;
		Connection con = JdbcUtil.getConnection(); //싱글톤으로 받으려 함 (싱글톤 : 객체 1개 생성후 공유)
		//오라클 연결
		BoardDAO bDAO = BoardDAO.getInstance(); //싱글톤으로 받으려 함 (싱글톤 : 객체 1개 생성후 공유) 
		// bDAO 오라클과 연결관련
		// dao : database access object
		// 디비에 쿼리문 전달. insert ...
		
		bDAO.setConnection(con); //서비스에서 생성된 커넥션을 boardDAO객체에 전달.
		int insertCount = bDAO.insertArtcle(bo);
		// executeUpdate 결과, 처리된 갯수를 리턴.
		// 즉 1리턴.
		
		
		if (insertCount > 0) {
			JdbcUtil.commit(con);
			isWriteSuccess =true;
		} else {
			JdbcUtil.rollback(con); //쿼리문을 돌리기 이전으로 돌리기.
		}
		
		JdbcUtil.close(con);
		
		return isWriteSuccess;
	}

}
