package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardWriteProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ActionForward forward = null;
		BoardBean boardBean = null;
		//게시물의 작성 내용(제목, 내용, 작성자, 작성일 등등)을 담아서 이동할 객체.
		boardBean = new BoardBean();
		boardBean.setBOARD_NAME(req.getParameter("BOARD_NAME"));
		boardBean.setBOARD_PASS(req.getParameter("BOARD_PASS"));
		boardBean.setBOARD_SUBJECT(req.getParameter("BOARD_SUBJECT"));
		boardBean.setBOARD_CONTENT(req.getParameter("BOARD_CONTENT"));
		// 사용자가 입력한 내용으로 BoardBean 객체 초기화.
		
		BoardWriteProService bo = new BoardWriteProService();
		boolean isWriteSuccess = bo.registArticle(boardBean); //registArticle : 게시물에 대한 내용을 등록하겠다 라는 명령을 만든다.
		//boolean isWriteSuccess = false;
		//일부러 false 리턴해서 안되는 상황 연출
		
		if(!isWriteSuccess) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>");
			out.print("alert('쓰기실패')");
			out.print("history.back()");
			out.print("</script>");
			//쓰기 잘못 됬다면, 안내창을 보이고, 쓰기창으로 이동
		} else {
			//쓰기가 성공 했다면,
			//forward 객체를 생성해서, 리다이렉트 시키고, 이동할 주소는
			//게시판 목록 보기.
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./boardList.bo");
			
		}
		
		return forward;
	}

}
