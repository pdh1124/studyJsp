package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardListService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		ArrayList<BoardBean> articleList = new ArrayList<>();
		//게시물 목록 한줄한줄을 담을 컬렉션 프레임 워크.
		ActionForward forward = new ActionForward();
		String str = null;
		
		BoardListService bService = new BoardListService();
		articleList = bService.getArticleList();
		
		req.setAttribute("articleList", articleList);
		//ArrayList에 담긴 값을 articleList 라는 이름으로 웹페이지에 전달.
		forward.setPath("/board/qna_board_list.jsp");
		//목록 페이지 호출
		
		return forward;
	}

}
