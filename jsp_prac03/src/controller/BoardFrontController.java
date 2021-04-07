package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardWriterProAction;
import vo.ActionForward;

@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BoardFrontController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		doProcess(request, response);
	}
	
	//doGet으로 호출하든 doPost로 호출하든 doProcess로 처리하겠다.
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //한글 깨짐 방지
		String RequestURI = req.getRequestURI(); // localhost:9090/jsp_prac03/index.jsp
		String contextPath = req.getContextPath(); // localhost:9090/jsp_prac03
		String command = RequestURI.substring(contextPath.length()); //index.jsp
		//url 에서 command만 추출하는 절차.
		
		ActionForward forward = null;
		//이동할 주소와 값의 전달 유무
		Action action = null;
		//요청에 대한 처리.
		
		
		//게시물 등록 이라는 요청
		if(command.equals("/boardWriteForm.bo")) {
			forward = new ActionForward();
			// isRedirect = false, path=null
			forward.setPath("/board/qna_board_write.jsp");
			// 게시물 등록 양식만 보여줌.
		}
		
		//boardWriterPro.bo
		else if(command.equals("/boardWriterPro.bo")) {
			//게시물 쓰기 처리(process)
			action = new BoardWriterProAction();
			try {
				forward = action.execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		//페이지 이동하는 if문
		if(forward != null) {
			if(forward.isRedirect()) {
				resp.sendRedirect(forward.getPath());
				// 전달값 없이 페이지 이동.
			} else {
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);
				// 요청과 응답(공유할 값) 가지고 페이지 이동.
			}
		}
	}

}
