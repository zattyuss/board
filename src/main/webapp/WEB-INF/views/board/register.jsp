<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<sec:authentication property="principal.memberVO" var="memberVO"/>
<sec:authentication property="principal.username" var="writer"/>
<div class="container">
	<div class="article_register my-4">
		<h3>게시글 쓰기</h3>
	</div>
	<form action="${pageContext.request.contextPath}/board/register" method="post" id="registerForm">
	<div class="hiddenArea">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</div>
		제목 : <input type="text" name="title"><br>
			 <textarea rows="30" cols="70" name="content"></textarea><br>
		작성자 : <input type="text" name="writer" class="form-control" readonly="readonly" value="${writer}"><br>
			
			<button class="btn btn-primary">등록</button>
</form>
<!--  
 <form:form action="register" id="registerForm" modelAttribute="board">
   제목 : <form:input type="text" path="title" />
      <form:errors path="title" class="error" element="div" />
      <br>
   내용 :<br>
      <form:textarea rows="30" cols="50" path="content" />
      <br>
   작성자 : <form:input type="text" path="writer" />
      <form:errors path="writer" />
      <br>
      <button>등록</button>
   </form:form>
-->
<div class="row"> 
	<div class="col-lg-12">
		<div class="card">
			<div class="card-header">
				<h4>파일 첨부</h4>
			</div>
			
			<div class="card-body">
				<div class="uploadDiv">
					<input type="file" name="uploadFile" multiple="multiple">
				</div>
				<div class="uploadResult">
					<ul class="list-group"></ul>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	let regex = new RegExp("(.*?)\.(exe|sh|js|alz)$")
	let maxSize = 524288000;
	
	function checkExtension(fileName, fileSize){
		
		if(fileSize >= maxSize){
			alert('파일 크기 초과');
			return false;
		}
		if(regex.test(fileName)){
			alert('허용되지 않는 확장자');
			return false;
		}
		return true;
	}

	let uploadResult = $('.uploadResult ul')
	function showUploadResult(uploadResultArr){
		if(!uploadResultArr || uploadResultArr.length == 0) return;
		let str = "";
		$(uploadResultArr).each(function(i, obj){
			if(!obj.fileType){ //이미지가 아닌 경우
				let fileCellPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
				let fileLink = fileCellPath.replace(new RegExp(/\\/g),"/");
				
				str+= "<li class='list-group-item' data-path='"+obj.uploadPath+"'";
				str+= "data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>";
				str+= "<img src='${contextPath}/resources/image/cat.jpg' style='width:100px;'>"
				str+= "<a href='${contextPath}/download?fileName="+fileCellPath+"'>"+obj.fileName+"</a>"
				str+= "<div class='d-flex justify-content-end'><span data-file='"+fileLink+"' data-type='file'>삭제</span></div>"
				str+= "</li>"
			}else{ //이미지인 경우
				let fileCellPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
				let originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
				originPath = originPath.replace(new RegExp(/\\/g), "/");
				
				str+= "<li class='list-group-item' data-path='"+obj.uploadPath+"'";
				str+= "data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>";
				str+= "<img src='${contextPath}/display?fileName="+fileCellPath+"'>";
				str+= "<a href='javascript:showImage(\""+originPath+"\")'>이미지원본보기</a>";
				str+= "<div class='d-flex justify-content-end'><span data-file='"+fileCellPath+"' data-type='image'>삭제</span></div>"
				str+= "</li>"				
			}
		
		})
		uploadResult.append(str);
	}
	
	
$(function(){
	let form = $('#registerForm')
	let submitBtn = $('#registerForm button');
	
	
	submitBtn.on('click', function(e){
		e.preventDefault();
		
		let str = "";
		$('.uploadResult li').each(function(i, obj){
			let jobj = $(obj);
			console.log(jobj.data('filename'));
			
			str+= "<input type='hidden' name='catList["+i+"].fileName' value='"+jobj.data('filename')+"'>"
			str+= "<input type='hidden' name='catList["+i+"].uuid' value='"+jobj.data('uuid')+"'>"
			str+= "<input type='hidden' name='catList["+i+"].uploadPath' value='"+jobj.data('path')+"'>"
			str+= "<input type='hidden' name='catList["+i+"].fileType' value='"+jobj.data('type')+"'>";
		});
		form.append(str).submit();
	})
	
	//파일 업로드
	$('input[type="file"]').change(function(){
		let formData = new FormData();
		let inputFiles = $('input[name="uploadFile"]');
		let files = inputFiles[0].files;
		
		for(let f of files){
			if(!checkExtension(f.name, f.size)) return false;
			formData.append('uploadFile', f);
		}
		
		$.ajax({
			url : contextPath + '/uploadAjaxAction',
			type : 'post',
			processData : false,
			contentType : false,
			data : formData,
			dataType : 'json',
			success : function(result){
				showUploadResult(result);
			}
		
		});
		
	})
	
	$('.uploadResult ul').on('click', 'span', function(){
		let targetFile = $(this).data('file');
		let type = $(this).data('type');
		let targetLi = $(this).closest('li');

		$.ajax({
			url : contextPath + '/deleteFile',
			type : 'post',
			data : {fileName : targetFile, type : type},
			dataType : 'text',
			success : function(result){
				alert(result);
				targetLi.remove();
			}
		})
		
	})
	
});
</script>

</div>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>