<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/common.css">
</head>
<body>

<section id="writeForm">
	<h2>게시글 삭제</h2>
	<!-- submit을 하면 "boardModifyPro.bo"으로 post 방식으로 입력됨 -->
	<form action="boardDeleteProForm.bo" method="post" name="boardform">
	
		<!-- 특정 게시물의 정보를 가져오기(게시물 번호,페이지) -->
		<input type="hidden" name="BOARD_NUM" value="${article.BOARD_NUM }" />
		<input type="hidden" name="page" value="${page }">
		<!-- hidden : 폼에 전달하여 다음 페이지로 넘어가지만, 사용자에게 보이지 않는 값 -->
		
		<table>
			<tr>
				<td class="td_left">비밀번호</td>
				<td class="td_right"><input type="password" name="BOARD_PASS" required="required"></td>
			</tr>
		</table>
		<!-- 정적인 정보에 동적인 효과 표시. -->
		<section id="commandCell">
			<input type="submit" onclick="return confirm('정말로 삭제하시겠습니까?');" value="등록">&nbsp;&nbsp;
			<input type="reset" value="다시쓰기">
			<a href="javascript:history.go(-1)">뒤로</a><!-- 히스토리 객체를 이용하여 이전 페이지로 이동 -->
		</section>
	</form>
</section>

</body>
</html>