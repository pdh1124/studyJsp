package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//req(request) : 요청
		//response : 응답
		//web은 요청과 응답의 1사이클로 작업 종료.
		
		ArrayList<BoardBean> articleList = new ArrayList<>();
		//게시물 목록 한줄한줄을 담을 컬렉션 프레임 워크.
		ActionForward forward = new ActionForward();
		String str = null;
		int page = 1; //현재 페이지 초기값.
		int limit = 10; //페이지당 보여줄 게시물 수.
		
		if(req.getParameter("page") != null) {//페이지가 널이 아니라면 
			page = Integer.parseInt(req.getParameter("page")); //변수 page에 값 할당.
			//처음에 페이지가 1이고 사용자가 2를 눌렀을때 2를 가져와 2페이지를 보겠다고 의식
		}
		
		BoardListService bService = new BoardListService();
		articleList = bService.getArticleList(page,limit); //매개변수 page와 limit를 가져온다.
		
		int listCount = bService.getListCount(); 
		System.out.println("listCount :"+listCount);
		//총 게시물 확인( 총 게시물에 따라서 페이징의 수를 결정함 예를 들어 게시물이 12개가 있는데 페이징에 10페이지까지 있으면 안되기 때문)
		int maxPage = (int) ((double) listCount / limit + 0.9);
		//예를 들어서 161개의 총게시물이 있다면,
		//총 페이지는 게시물을 페이지당 게시물로 나눈것의 반올림.
		//이유는 소수점 페이지는 없고, 게시물의 소수점은 반올림 되야 함.
		// 16.1이 17로 바뀌게 됨
		
		//시작페이지를 구하는 값
		int startPage = (((int)((double) page / 5 + 0.9)) - 1) *5 + 1;
		// 1.0 >> 1.1 >> 1 >> 0 >> 1
		// 4.0 >> 0.8 >> 
		// 8.0 >> 1.6 >> 2.5 >> 2 >> 1 >> 5 >> 6
		//1~5 페이지는 1, 6~10 페이지는 6, 11~15페이지는 11 ... 한페이지의 페이징수 5 
		//시작 페이지도 계속 변화하니까 공식을 적용.
		//위의 5는 한 페이지에 보여줄 페이지수. 게시물수 아님, 
		//선택 페이지가 변경되도, 시작 페이지를 일정하게 보여주기 위함.
		
		
		//한 화면에 보이는 마지막 페이지 번호.
		int endPage = startPage + 5 - 1; //startPage : 1이거나 6이거나 11이거나 ....으로 온다.
		//maxPage와 endpage는 다르다.
		
		//마지막 페이지에서 모자란 페이지를 맞춰서 보여주기 위해
		if(endPage > maxPage) {
			endPage = maxPage;
			//endPage : 5이거나10 이거나 15이거나 ... 으로 온다.
		}
		
		
//		System.out.println("page :"+page);
//		System.out.println("maxPage :"+maxPage);
//		System.out.println("endPage :"+endPage);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setEndPage(endPage);
		pageInfo.setStartPage(startPage);
		
		req.setAttribute("pageInfo", pageInfo);	
		req.setAttribute("articleList", articleList);
		//ArrayList에 담긴 값을 articleList 라는 이름으로 웹페이지에 전달.
		forward.setPath("/board/qna_board_list.jsp");
		//목록 페이지 호출
		
		return forward;
	}

}
