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

	<section id="passForm">
		<form name="deleteForm" action="boardDeletePro.bo?board_num=${board_num}" method="post">
			<input type="hidden" name="page" value="${page}" />
			<table>
				<tr>
					<td><label style="margin-left: 10px">글 비밀번호 :</label></td>
					<td><input name="BOARD_PASS" type="password"></td>
				</tr>
				<tr>
					<td colspan=2>
						<div align="center">
							<input type="submit" value="삭제" />
							<input type="button" value="돌아가기" onClick="javascript:history.go(-1)"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</section>
	
</body>
</html>