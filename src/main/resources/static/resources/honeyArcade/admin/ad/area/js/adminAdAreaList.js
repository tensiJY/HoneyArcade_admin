$(function(){
	$("#listCount").selectpicker();
	
	$("#area_type").selectpicker();
	
	$("#searchText").on("keypress", function(e){
		if(e.keyCode==13){
			$("#searchBtn").click();	
		}
	});
	
	$("#searchBtn").on("click", function(e){
		$("#type").val("search");
		$("#searchForm").submit();
	});
	
	$("#delBtn").on("click", function(e){
		if(confirm("삭제하시겠습니까?")){
			var _chkBox = $("input[name=check_box]");
			var _chkArr = [];
			for(var i=0; i<_chkBox.length; i++){
				var _checked = $(_chkBox[i]).prop("checked");
				if(_checked){
					
					var _obj = {};
					_obj.owner_id = $(_chkBox[i]).attr("data-owner-id");
					_obj.banner_seq = $(_chkBox[i]).attr("data-banner-seq");
					_obj.banner_type = $(_chkBox[i]).attr("data-banner-type");
					_obj.ad_owner_type = $(_chkBox[i]).attr("data-ad-owner-type"); 
					_chkArr.push(_obj);
				}
			}
			
			if(_chkArr.length == 0){
				alert("선택된 데이터가 없습니다");
				return;
			}
			
			$.ajax({
				type: "POST",
				url: "/admin/ad/area/delProc",
				data: JSON.stringify(_chkArr),
				contentType : "application/json; charset=utf-8", 
				beforeSend: function (xhr) {
					xhr.setRequestHeader(_csrfHeader, _csrfToken);
				},
				success: function (res) {
					if(res.result == false || res.result == 'false'){
						return alert("삭제에 실패하였습니다. 관리자에게 문의 바랍니다.");
					}else{
						alert("삭제에 성공하였습니다.");
						
						window.location.reload();
					}
				},
				error: function (e) {
					alert("삭제에 실패하였습니다. 관리자에게 문의 바랍니다.");
					console.log("error : " + e.toString())
				}
			});
			
		}
	});
	
	$("#writeBtn").on("click", function(){
		$("#writeForm").submit();
	})
	
	$(".modBtn").on("click", function(){
		
		$(this).parents('tr').children().find("form[name=modForm]").submit()
	});
	
});