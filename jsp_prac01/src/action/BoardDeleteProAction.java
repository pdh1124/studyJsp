package action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDeleteProService;
import service.BoardModifyProService;
import vo.ActionForward;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		// 게시물 번호, 사용자 입력 비밀번호, 이동한 페이지 번호의 3개 값이 필요함
		ActionForward fo =null; // 이동할 페이지 주소와 값의 공유 우무
		boolean isDel = false; // 삭제가 됐는지?
		int board_num = Integer.parseInt(req.getParameter("board_num")); //게시물 번호
		String page = req.getParameter("page"); //페이지번호
		
		String pass = req.getParameter("BOARD_PASS"); //비밀번호
		
		//입력한 비밀번호가 일치하는지 확인
		BoardModifyProService bmps = new BoardModifyProService();
		boolean isRightUser = bmps.isArticleWriter(board_num, pass);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		if (!isRightUser) { //비밀번호가 일치하지 않는다면
			out.println("<script>");
			out.println("alert('삭제할 권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
		} else {
			// 비밀번호가 일치한다면, 게시물 삭제.
			BoardDeleteProService bdps = new BoardDeleteProService();
			isDel = bdps.deleteArticle(board_num);
			
			if(!isDel) {
				out.println("<script>");
				out.println("alert('삭제 실패');");
				out.println("history.back();");
				out.println("</script>");	
			} else {			
				fo = new ActionForward();
				fo.setRedirect(true);
				fo.setPath("boardList.bo?page=" + page);
			}
		}

		return fo;
	}

}
