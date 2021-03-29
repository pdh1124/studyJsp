package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

@WebServlet("*.bo") // /BoardFrontController를 *.bo로 수정
//*은 이름에 상관없이 .bo가 붙으면 처리해 주겠다.
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BoardFrontController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doProcess(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//doGet(request, response);
		doProcess(request,response);
	}
	
	
	//get방식이든 post방식이든 doProcess로 처리하겠다.
	protected void doProcess(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// url에서 사용할 주소만 걸러내는 작업
		//'localhost:9090/jsp_prac01/요청한 주소.bo' 에서 'localhost:9090/jsp_prac01/' 를 빼서
		//'요청한 주소.bo'를 얻어냄
		
		req.setCharacterEncoding("UTF-8"); //요청에 대해서 utf8처리(한글깨짐 처리)
		String RequestURI = req.getRequestURI(); //전송된 uri를 가져온다. //http 규약을 뺀 나머지를 가져온다.
		// localhost:9090/jsp_prac01/요청한 주소.bo
		String contextPath = req.getContextPath(); //마지막 주소(요청한 주소.bo)를 나머지를 가져온다.
		// localhost:9090/jsp_prac01/
		// 마지막의 요청만 알고 싶기 때문에
		String command = RequestURI.substring(contextPath.length());
		// 요청한 주소.bo
		

		ActionForward forward = null;
		Action action = null;
		
		
		//게시물 등록 이라는 요청.
		if (command.equals("/boardWriteForm.bo")) { // 받는 값이 /boardWriteForm.bo 맞다면
			forward = new ActionForward(); //forward 객체 생성 
			//isRedirect= false, path=null
			forward.setPath("/board/qna_board_write.jsp"); //이동 url 지정
			//게시물 등록 양식만 보여줌.
		}
		
		if(forward != null) { //위에서 forward 객체를 생성하면
			if(forward.isRedirect()) { //주소만 변경 하는 것, 전달값 없이 페이지 이동
				response.sendRedirect(forward.getPath());
			} else { //무언가를 물고서 변경하는 것, 요청과 응답(공유할 값) 가지고 페이지 이동.
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				//RequestDispatcher : 페이지 이동
				dispatcher.forward(req, response);
			}
		}
	}
}