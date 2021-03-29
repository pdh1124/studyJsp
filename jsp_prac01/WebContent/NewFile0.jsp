<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- page 지시어. -->
    
<!-- 톰캣을 설치하면, 톰캣 내부에 servlet-api.jar 파일이 추가되어 있음 -->
<!-- 자바의 기본 제공 클래스에는 웹에 대한 처리를 위한 클래스들을 제공하지 않음.
	그러므로, 해당 클래스를 build path로 추가하여 컴파일 시 이용하도록 알려줌.-->
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<html>
<head>
<%
// scriptlet 스크립틀릿 : 웹페이지에 보여줄 정보 구현 (구현부)
Calendar c = Calendar.getInstance();
int hour = c.get(Calendar.HOUR_OF_DAY);
int minute = c.get(Calendar.MINUTE);
int second = c.get(Calendar.SECOND);
%>
<meta charset="UTF-8">
<title>현재 시각</title>
</head>
<body>
	현재 시간은
	<!-- 표현부 -->
	<%=hour %>시
	<%=minute %>분
	<%=second %>초 입니다.
</body>
</html>