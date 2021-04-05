package vo; //vo : value object

public class ActionForward { //주소를 이동할때 공유할 값이 있는지 여부와 url 주소를 가져오기 위함
	private boolean isRedirect = false;  //주소 이동 여부
	//공유할 값이 있다면 dispatcher, 목록, 조회      
	//dispatcher : 무슨 값을 물고 가는 것
	//게시판에서 게시물을 클릭해 그 게시물을 볼 때
	
	//공유할 값이 없다면 redirect, 수정             
	//redirect : 단순 주소 이동(전달하는 값이 없다.)
	//예를 들어 회원가입 후 메인 페이지로 갈때
	
	private String path = null;
	//어느 주소로 갈지 주소경로 , url
	
	//getter와 setter 생성
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
