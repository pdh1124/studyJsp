<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%
   session.setMaxInactiveInterval(10);
   //세션 활성화 시간 10초.
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Session Test</title>
</head>
<body>
	<!-- 세션을 사용하는 간단한 예제 -->
	<h2>세션 테스트</h2>
	isNew():<%=session.isNew() %><br> 
	<!-- 새로 생성된 세션인지? -->
	생성시간:<%=session.getCreationTime() %><br>
	<!-- 1970.1.1 0시 기준 -->
	최종 접속 시간:<%=session.getLastAccessedTime() %><br> 세션ID:<%=session.getId() %><br>
</body>
</html>