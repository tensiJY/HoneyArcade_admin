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
			var _buildName = $("#build_name").val();
			var _build_x = $("#build_x").val();
			var _build_y = $("#build_y").val();
			if(_buildName.trim() == ""){
				alert("건물이름을 등록해주세요");
				return;
			}
			
			if(_build_x == ""){
				alert("경도를 등록해주세요");
				return;
			}
			
			if(_build_y ==""){
				alert("위도를 등록해주세요");
				return;
			}
			
			$("#buildWriteProcForm").submit();
		}
		
	});
	
})