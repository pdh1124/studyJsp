package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
	// 클라이언트의 요청을 받아서 처리하고, 응답을 ActionForward
	// 객체로 리턴
	// ActionForward는 이동여부, 이동할 url을 가지고 있음.
}
