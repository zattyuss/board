<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<div class="container">
	<h2>관리자 페이지</h2>
	<div>
		MemberVO : <sec:authentication property="principal.memberVO" var="member"/><br>
		아이디 : <sec:authentication property="principal.memberVO.userId"/><br>
		이름 : <sec:authentication property="principal.memberVO.userName"/><br>
		권한 : <sec:authentication property="principal.memberVO.authList"/><br>
		
	</div>
	
	
	<a href="${contextPath}/customLogout">로그아웃하러 가자</a>
	<div>
		${member}
	</div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

<style>
	.loginForm {width: 400px; margin: auto;}
</style>
