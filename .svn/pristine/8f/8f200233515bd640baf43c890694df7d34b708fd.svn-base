$(function(){

	
	//	건물 목록
	$("#listBtn").on('click', function(){
		location.href = "/admin/mgt/build/list";
	});
	
	//	코드 관리
	$("#codeMgtBtn").on("click", function(){
		location.href = "/admin/mgt/build/codeList";		
	});
	
	//	건물 저장
	$("#saveBtn").on('click', function(){
		
		
		if(confirm("저장하시겠습니까?")){
			var _topFloor = $("#top_floor").val();
			if(isNaN(_topFloor)){
				alert("숫자만 등록해주세요")
				return;
			}
			
			$("#buildWriteProcForm").submit();
		}
		
	});
	
})