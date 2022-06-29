<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>

</head>
<body>
<h2>파일업로드 : AJAX 아약스</h2>
<div class="uploadDiv">
	<input type="file" name="uploadFile" multiple="multiple">
</div>
<button id="uploadBtn">업로드</button>

<div class="uploadResult">
	<ul>

	</ul>
</div>



</body>

<script>
$(function(){
	let contextPath = '${pageContext.request.contextPath}';
	let regex = new RegExp("(.*?)\.(exe|sh|js|alz)$")
	let maxSize = 5242880;
	
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
	
	
	let cloneObj = $('.uploadDiv').clone();
	$('#uploadBtn').on('click', function(){
		let formData = new FormData();
		
		let inputFile = $('input[name="uploadFile"]')
		let files = inputFile[0].files
		console.log(files)
		
		for (let i = 0; i < files.length; i++) {
			if(!checkExtension(files[i].name, files[i].size)) return;
			formData.append("uploadFile", files[i])
		}
		
		$.ajax({
			url : contextPath + '/uploadAjaxAction',
			processData : false,
			contentType: false,
			data : formData,
			type : 'post',
			success : function(result){
				alert("Uploaded");
				$('uploadDIV').html(cloneObj.html());
				showUploadFile(result);
			}
		})
	})
	
	let uploadResult = $('.uploadResult ul')
	function showUploadFile(uploadResultArr){
		let str = "";
		$(uploadResultArr).each(function(i, obj){
			str += "<li>"+obj.fileName+"</li>"
		})
		uploadResult.append(str);
	}
})
</script>

</html>