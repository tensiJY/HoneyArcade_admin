var _adminMemberOwnerList = function(){
	
	var init = function(){
		//	엔터 이벤트
		$("#searchText").on("keypress", function(e){
			if(e.keyCode == 13){
				$("#searchBtn").click();	
			}
		})
		//	검색 버튼
		$("#searchBtn").on("click", function(){
			$("#searchForm").submit();
		});
			
		$(".clickBtn").on("click", function(){
			$(this).parents("tr").find("form").submit();
		});
	}
	
	return{
		init : init
	}
}

$(function(){
	new _adminMemberOwnerList().init();
});