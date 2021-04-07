package vo; //value object

public class ActionForward {
	private boolean isRedirect = false;
	//주소 이동 여부,
	//공유할 값이 있다면 dispatcher, 목록, 조회
	//공유할 값이 없다면 redirect, 수정
	private String path = null;
	//어느 주소로 갈지 주소경로, url
	
	
	//getter와 setter
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
