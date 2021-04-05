package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {
	
		// 게시물번호를 가져와서 해당 게시물의 비밀번호가 일치 한다면,
		// 해당 게시물을 수정 처리
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//폼페이지에 넘겼던 BOARD_NUM과 page를 가지고 옴
		int board_num = Integer.parseInt(req.getParameter("BOARD_NUM"));
		String page = req.getParameter("page");
		
		BoardModifyProService bmps = new BoardModifyProService(); //서비스 객체 호출
		boolean isRightUser = bmps.isArticleWriter(board_num, req.getParameter("BOARD_PASS"));
		//비밀번호 일치 여부 boolean으로 리턴, true = 일치, false=불일치.
		
		if(!isRightUser) { //비밀번호가 틀리다면,
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			
			out.println("<script>");
			out.println("alert('수정할 권한이 없습니다');");
			out.println("history.back();");
			out.println("</script>");	
		} else { //비밀번호가 맞는다면.
			BoardBean article = new BoardBean();
			article.setBOARD_NUM(board_num);
			article.setBOARD_SUBJECT(req.getParameter("BOARD_SUBJECT"));
			article.setBOARD_CONTENT(req.getParameter("BOARD_CONTENT"));
			//boardBean 객체에 폼에서 작성한 값을 저장하고,
			//디비에 반영하기 위해서 updateArticle 호출.
			boolean isModi = bmps.updateArticle(article);
		}
		
		return null;
	}

}
