package service;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardListService {
	
	public ArrayList<BoardBean> getArticleList() throws Exception {
		ArrayList<BoardBean> aList = null;
		Connection con = JdbcUtil.getConnection();
		BoardDAO bDAO = BoardDAO.getInstance();
		bDAO.setConnection(con);
		
		aList = bDAO.selectArticleList();
		JdbcUtil.close(con);
		
		return aList;
	}
}
