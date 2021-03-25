<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- 
	cookie : client(내 컴퓨터)에 다운로드 되는 접속 정보. (= 아이디, 패스워드 저장하기)
	session : 서버에서 관리하는 접속 정보. 쿠키와는 반대 서버상에서만 유지.
	
	보안상에 문제가 없는데 자주 쓰는것은 쿠키에 담는다.
	보안상으로 중요한 정보는 세션에 담는다.
	
	일정부분은 서버가 가지고 있고 일정부분은 클라이언트가 가지고 있게 하는것
-->
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
h1 {
	text-align: center;
}

table {
	margin: auto;
	whdth: 400px;
	border: 1px solid red;
}
</style>
<body>
	<h1>쿠키, URL/URI, 요청방식에 관련된 정보 예제</h1>
	<table border="1">
		<tr>
			<td>쿠키정보</td>
			<%
				Cookie[] cookie = request.getCookies(); //쿠키의 객체배열. cookie의 배열을 리턴받을 수 있다.
			//쿠키에 저장된 값을 배열 형태로 리턴.
				if(cookie == null) {
				%>
				<td>쿠키가 존재하지 않습니다.</td>
				<%	
				} else {
					for(int i=0; i < cookie.length;i++) {
				%>		
					 <td><%=cookie[i].getName()%>(<%=cookie[i].getValue()%>)&nbsp;&nbsp;</td>	
				<%	
					} //end for
				}// end else
			%>
		</tr>
		<tr>
			<td>서버 도메인명</td>
			<td><%=request.getServerName() %></td>
		</tr>
		<tr>
			<td>서버 포트번호</td>
			<td><%=request.getServerPort() %></td>
		</tr>
		<tr>
			<td>요청 URL</td>
			<td><%=request.getRequestURL() %></td>
		</tr>
		<tr>
			<td>요청 URI</td>
			<td><%=request.getRequestURI() %></td>
		</tr>
		<tr>
			<td>요청 쿼리</td>
			<td><%=request.getQueryString() %></td>
		</tr>
		<tr>
			<td>클라이언트 호스트명</td>
			<td><%=request.getRemoteHost() %></td>
		</tr>
		<tr>
			<td>클라이언트 IP 주소</td>
			<td><%=request.getRemoteAddr() %></td>
		</tr>
		<tr>
			<td>프로토콜</td>
			<td><%=request.getProtocol() %></td>
		</tr>
		<tr>
			<td>요청방식</td>
			<td><%=request.getMethod() %></td>
		</tr>
		<tr>
			<td>컨텍스트 경로</td>
			<td><%=request.getContextPath() %></td>
		</tr>
	</table>

</body>
</html>