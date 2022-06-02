<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<div class="container">
<table class="table">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>등록일</th>
		<th>수정일</th>
	</tr>
	<c:if test="${not empty list }">
		<c:forEach items="${list}" var="b">
		<tr>
			<td>${b.bno }</td>	
			<td>
				<a href="get?bno=${b.bno }">${b.title }</a>
			</td>
			<td>${b.writer }</td>	
			<td>
				<fmt:parseDate var="regDate" value="${b.regDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>
				<fmt:formatDate value="${regDate}" pattern="yyyy-MM-dd HH:mm"/>
			</td>
			<td>
				<fmt:parseDate var="updateDate" value="${b.updateDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>
				<fmt:formatDate value="${updateDate}" pattern="yyyy-MM-dd HH:mm"/>
			</td>
		</tr>
		</c:forEach> 
	</c:if>
	<c:if test="${empty list }">
		<tr>
			<td colspan="5">
				게시글이 존재하지 않습니다.
			</td>
		</tr>
	</c:if>
</table>
<ul class="pagination">
	<c:if test="${pageMaker.prev}">
		<li class="page-item"><a class="page-link" href="?page=${pageMaker.startPage-1}">이전페이지</a></li>
	</c:if>
	<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
		<li class="page-item ${param.page == pageNum ? 'active':''}">
			<a href="?page=${pageNum}" class="page-link">${pageNum}</a>
		</li>
	</c:forEach>
	<c:if test="${pageMaker.next}">
		<li class="page-item"><a class="page-link" href="?page=${pageMaker.endPage+1}">다음페이지</a></li>
	</c:if>
</ul>
<br>


<a href="${pageContext.request.contextPath}/board/register" class="btn btn-primary">글등록</a>
<c:if test="${not empty message}">
${message}
</c:if>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>