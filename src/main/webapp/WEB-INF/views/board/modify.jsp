<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jspf"%>
<h3>수정페이지</h3>
<br>
<div class="container">
   <form action="modify" method="post" id="modifyForm">
      <input type="hidden" name="bno" value="${board.bno}"> 게시물 번호
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
      <input type="hidden" name="writer" value="${board.writer}">
      : ${board.bno } <br> 제목 : <input type="text" name="title"
         value="${board.title}"><br> 내용 :
      <textarea rows="" cols="" name="content">${board.content}</textarea>
      <br> 작성자 : ${board.writer }<br>
      <button class="btn btn-warning">수정하기</button>
   </form>
   <div class="row"> 
		<div class="col-lg-12">
			<div class="card">
				<div class="card-header">
					<h4>첨부된 파일</h4>
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
</div>
<%@ include file="/WEB-INF/views/layout/footer.jspf"%>



<script>
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
			str+= "<span>"+obj.fileName+"</span>"
			str+= "<div class='d-flex justify-content-end'><span data-file='"+fileLink+"' data-type='file'>삭제</span></div>"
			str+= "</li>"
		}else{ //이미지인 경우
			let fileCellPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
			let originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
			originPath = originPath.replace(new RegExp(/\\/g), "/");
			
			str+= "<li class='list-group-item' data-path='"+obj.uploadPath+"'";
			str+= "data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'>";
			str+= "<img src='${contextPath}/display?fileName="+fileCellPath+"'>";
			str+= "<span>"+obj.fileName+"</span>"
			str+= "<div class='d-flex justify-content-end'><span data-file='"+fileCellPath+"' data-type='image'>삭제</span></div>"
			str+= "</li>"				
		}
	
	})
	$('.uploadResult ul').append(str);
}



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

$(function(){
	let bnoValue = "${board.bno}";
	$.getJSON(contextPath + "/board/getCatList", {bno : bnoValue}, function(catList){	
		showUploadResult(catList);
	})
	
	$('.uploadResult ul').on('click', 'span', function(){
		let targetLi = $(this).closest('li');
		targetLi.remove();
		
	})
	
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
	
	let modifyForm = $('#modifyForm');
	let modifyBtn = $('#modifyForm button');
	modifyBtn.on('click', function(e){
		e.preventDefault();	
		let str = "";
		$('.uploadResult ul li').each(function(i, obj){
			let jobj = $(obj);
			str+= "<input type='hidden' name='catList["+i+"].fileName' value='"+jobj.data('filename')+"'>"
			str+= "<input type='hidden' name='catList["+i+"].uuid' value='"+jobj.data('uuid')+"'>"
			str+= "<input type='hidden' name='catList["+i+"].uploadPath' value='"+jobj.data('path')+"'>"
			str+= "<input type='hidden' name='catList["+i+"].fileType' value='"+jobj.data('type')+"'>";
		});
		modifyForm.append(str).submit();
	})
})
</script>