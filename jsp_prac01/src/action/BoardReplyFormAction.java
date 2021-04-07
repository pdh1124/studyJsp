package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		ActionForward fo = null;
		BoardBean article = new BoardBean();
		fo = new ActionForward();
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		
		BoardDetailService bSer = new BoardDetailService();
		article = bSer.getArticle(board_num, "reply");
		//게시물 번호를 전달하면 게시물 1개의 내용을 객체로 전달.
		req.setAttribute("article", article);
		fo.setPath("/board/qna_board_reply.jsp");
		//qna_board_view.jsp에는 제목, 내용, 파일등의 정보만 남아 있음.
		//답변을 처리하기 위해서는 ref(참조), lev(들여쓰기 깊이), seq(순서) 값이 필요.
		//다시 읽어서 해당 값을 hidden으로 처리.
		return fo;
	}

}
