<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#dragBox {
		width: 400px;
		height: 300px;
		border: 1px dotted gray;
	}
	
	#dragBox img {
		width: 100px;
		height: 100px;
		margin: 2px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<form id="f1">
		<input type="text" name="writer" id="writer" placeholder="작성자 이름">
		<input type="submit" value="전송">
	</form>
	
	<div id="dragBox">
	</div>
	
	<script>
		var formData = new FormData();
	
		$("#dragBox").on("dragenter dragover", function(e) {
			e.preventDefault();
		})

		$("#dragBox").on("drop", function(e) {
			e.preventDefault();

			var files = e.originalEvent.dataTransfer.files;
			var file = files[0];

			console.log(file);

			// drag한 이미지를 #dropBox에 나타나게 한다.
			var reader = new FileReader();
			
			reader.addEventListener("load", function(e) {
				var $img = $("<img>");
				
				$img.attr("src", reader.result);
				$("#dragBox").append($img);
			}, false);

			if (file) {
				reader.readAsDataURL(file);
			}
			
			formData.append("files", file);
		})
		
		$("#f1").submit(function(e) {
			e.preventDefault();
			formData.append("writer", $("#writer").val());

			$.ajax({
				url : "drag",
				type : "post",
				data : formData,
				processData : false, // formData를 ajax로 보내야 될 때 사용
				contentType : false, // formData를 ajax로 보내야 될 때 사용
				success : function(json) {
					console.log(json);
					$("#dragBox").empty();
					
					$(json).each(function(i, obj){
						var $img = $("<img>");
						$img.attr("src", "displayFile?filename=" + obj);
						$("#dragBox").append($img);
					})
				}
			})
		})
	</script>
</body>
</html>