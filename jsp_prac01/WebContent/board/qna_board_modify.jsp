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
	<h2>게시글 수정</h2>
	<!-- submit을 하면 "boardModifyPro.bo"으로 post 방식으로 입력됨 -->
	<form action="boardModifyPro.bo" method="post" name="boardform">
	
		<!-- 특정 게시물의 정보를 가져오기(게시물 번호,페이지) -->
		<input type="hidden" name="BOARD_NUM" value="${article.BOARD_NUM }" />
		<input type="hidden" name="page" value="${page }">
		<!-- hidden : 폼에 전달하여 다음 페이지로 넘어가지만, 사용자에게 보이지 않는 값 -->
		
		<table>
			<tr>
				<!-- 처리부분을 name 부분을 받아서 처리함 -->
				<td class="td_left">글쓴이</td>
				<td class="td_right"><input type="text" name="BOARD_NAME" value="${article.BOARD_NAME }" required="required"></td>
			</tr>
			<tr>
				<td class="td_left">비밀번호</td>
				<td class="td_right"><input type="password" name="BOARD_PASS" required="required"></td>
			</tr>
			<tr>
				<td class="td_left">제목</td>
				<td class="td_right"><input type="text" name="BOARD_SUBJECT" value="${article.BOARD_SUBJECT }" required="required"></td>
			</tr>
			<tr>
				<td class="td_left">내용</td>
				<td class="td_right"><textarea name="BOARD_CONTENT" cols="40" rows="15" required="required">${article.BOARD_CONTENT }</textarea></td>
			</tr>
		</table>
		<!-- 정적인 정보에 동적인 효과 표시. -->
		<section id="commandCell">
			<a href="javascript:modifyboard()">수정</a><!-- 해당 메소드 호출 -->
			&nbsp;&nbsp; 
			<a href="javascript:history.go(-1)">뒤로</a><!-- 히스토리 객체를 이용하여 이전 페이지로 이동 -->
		</section>
	</form>
</section>

</body>
<script type="text/javascript">
	function modifyboard() {
		boardform.submit();
	}
</script>

</html>