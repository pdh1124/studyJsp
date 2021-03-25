
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request/*요청*/, HttpServletResponse response/*응답*/)
			throws ServletException, IOException {
		//코드 수정함
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//요청에 대한 처리.
		String id = request.getParameter("id"); 
		String passwd = request.getParameter("passwd");
		
		//응답 화면 만들기
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("아이디=" + id + "<br>");
		out.println("비밀번호=" + passwd + "<br>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
