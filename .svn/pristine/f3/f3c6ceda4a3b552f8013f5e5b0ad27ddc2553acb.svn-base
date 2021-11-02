$(function(){
	$(".delBtn").on("click", function(){
		if(confirm("삭제하시겠습니까?")){
			var _obj = {};
			_obj.ntc_seq = $(this).attr("data-seq");
			_obj.ntc_type = $(this).attr("data-type"); 
	
			$.ajax({
				type: "POST",
				url: "/admin/set/ntc/delProc",
				data: JSON.stringify(_obj),
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
		};
	});
	
	$(".modBtn").on("click", function(){
		var _seq	= $(this).attr("data-seq");
		var _type	= $(this).attr("data-type");
		var _url	= "/admin/set/ntc/mod";
		
		var _that = $("#hiddenForm");
		$(_that).find("input[name=ntc_seq]").val(_seq);
		$(_that).find("input[name=ntc_type]").val(_type);
		
		$(_that).attr("action", _url).submit();		
	});
	
	$(".addBtn").on("click", function(e){
		var _type = $(this).attr("data-type");
		var _that = $("#hiddenForm");
		var _url  = "/admin/set/ntc/write";
		
		$(_that).find("input[name=ntc_type]").val(_type);
		$(_that).attr("action", _url).submit();
	});
	
});