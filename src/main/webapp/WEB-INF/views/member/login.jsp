<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<div class="container">
	<h2>로그인 페이지</h2>
	<div class="error">
		<p>${error}</p>
	</div>
	<div class="loginForm my-5">
		<form action="${contextPath}/member/login" method="post">
			<div class="form-group">
				<input type="text" name="loginId" value="${loginId}" placeholder="아이디" class="form-control">
			</div>
			<div class="form-group">
				<input type="password" name="loginPw" placeholder="비밀번호" class="form-control">
			</div>
			<div class="form-group">
				<label>아이디 저장하기</label>
				<input type="checkbox" name="remember-me">
			</div>
			<div class="error_area">
				${errorMessage}
			</div>
			<div class="form-group">
				<button class="btn btn-primary form-control">로그인</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>

<style>
	.loginForm {width: 400px; margin: auto;}
	.error_area {color: blue; text-align: center;}
</style>
