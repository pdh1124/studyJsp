<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- jstl을 사용하기 위함 : 코드를 더 편하게 만들어 줌 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/common.css">
</head>
<body>
	<section id="lstForm">
		<h2>글 목록</h2>
		<table border="1" style="margin-left: auto; margin-right: auto;">
			<tr>
				<td colspan="5" align="right"><a href="boardWriteForm.bo">글쓰기</a></td>
			</tr>
			<tr id="tr_top" align="center">
				<td>번호</td>
				<td width="200px">제목</td>
				<td>작성자</td>
				<td>날짜</td>
				<td>조회수</td>
			</tr>
			<c:set var="i" value="0" />
			<c:forEach var="article" items="${articleList }">
				<tr align="center">
					<td>${article.BOARD_NUM }</td>
					<td>${article.BOARD_SUBJECT }</td>
					<td>${article.BOARD_NAME }</td>
					<td>${article.BOARD_DATE }</td>
					<td>${article.BOARD_READCOUNT }</td>
				</tr>
			</c:forEach>
		</table>
	</section>

page:${pageInfo.page}<br>
startPage:${pageInfo.startPage}<br>
endPage:${pageInfo.endPage}<br>
	<section id="pageList">

		<c:forEach var="a" begin="${pageInfo.startPage }"
			end="${pageInfo.endPage }" step="1">
			<c:choose>
				<c:when test="${a==pageInfo.page }">
					<b>${a }</b>
				</c:when>
				<c:otherwise>
					<a herf="boardList.bo?page=${a }">${a } </a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

	</section>

</body>
</html>