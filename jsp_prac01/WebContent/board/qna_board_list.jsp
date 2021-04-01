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
		<table border="1" style="margin-left:auto; margin-right:auto;">
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
			<c:set var="i" value="0" /> <!-- i라는 변수에 0을 넣는다.  (var i = 0), 게시물 번호 관련 변수. -->
			<c:forEach var="article" items="${articleList }"><!-- 자바의 향상된 for문 역할 -->
					<tr align="center">
						<td>${article.BOARD_NUM }</td><!-- el 을 사용하면, 해당 필드의 getter 자동 호출 -->
						<td>${article.BOARD_SUBJECT }</td>
						<td>${article.BOARD_NAME }</td>
						<td>${article.BOARD_DATE }</td>
						<td>${article.BOARD_READCOUNT }</td>					
					</tr>
			</c:forEach>
		</table>
	</section>
	
	<!-- ◀ 버튼 기능 -->
	<section id="pageList">
		<c:if test="${pageInfo.page > 1 && pageInfo.startPage > 1 }">
			<a href="boardList.bo?page=${pageInfo.page-((pageInfo.page-1)%5+1) }">◀</a>
		</c:if>
	</section>
	<!-- 1~5를 나타내는 페이지가 아니라면 ◀ 표시. -->
	
	
	<!-- 페이징 숫자 기능 -->
	<c:forEach var="a" begin="${pageInfo.startPage }" end="${pageInfo.endPage }" step="1"> <!-- startPage에서 endPage까지 1씩늘어나며 반복 -->
		<c:choose><!-- switch문 -->
			<c:when test="${a==pageInfo.page }"><!-- case -->
				<b>${a }</b><!-- 보여지는 페이지가 현재 페이지라면 링크를 걸지 않음. -->
			</c:when>
			<c:otherwise> <!-- defult -->
				<a herf="boardList.bo?page=${a }">${a } </a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<!-- ▶ 버튼 기능 -->
	<c:if test="${(pageInfo.page < pageInfo.maxPage) && (pageInfo.endPage < pageInfo.maxPage) || (pageInfo.endPage != pageInfo.maxPage) } ">
		<a href="boardList.bo?page=${pageInfo.page+(6-(pageInfo.page-1)%5+1) }">▶</a>
	</c:if>
	
</body>
</html>