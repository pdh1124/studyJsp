package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardReplyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ActionForward fo = null;
		BoardBean article = new BoardBean();
		
		article.setBOARD_NAME(req.getParameter("BOARD_NAME"));
		article.setBOARD_PASS(req.getParameter("BOARD_PASS"));
		article.setBOARD_SUBJECT(req.getParameter("BOARD_SUBJECT"));
		article.setBOARD_CONTENT(req.getParameter("BOARD_CONTENT"));
		article.setBOARD_RE_REF(Integer.parseInt(req.getParameter("BOARD_RE_REF")));
		article.setBOARD_RE_LEV(Integer.parseInt(req.getParameter("BOARD_RE_LEV")));
		article.setBOARD_RE_SEQ(Integer.parseInt(req.getParameter("BOARD_RE_SEQ")));
		// 답변 쓴 내용을 BoardBean 객체에 저장.
		
		BoardReplyProService bSer = new BoardReplyProService();
		boolean isRe = bSer.replyArticle(article);
		// 서비스의 replyArticle메소드에 boardBean 객체를 전달.
		// 답글 쓰기 처리 호출
		
		if(isRe) {
			fo = new ActionForward();
			fo.setRedirect(true);
			fo.setPath("boardList.bo");
		} else {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>");
			out.println("alert<'답글 실패'>");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return fo;
	}

}
