$(function(){
	//	목록 버튼
	$("#listBtn").on("click", function(){
		location.href = "/admin/set/ntc/list";
	});
	
	//	저장 버튼
	$("#saveBtn").on("click", function(){
		if(confirm("저장하시겠습니까?")){
			var _title = $("#ntc_title").val();
			var _text = $("#ntc_text").val();
			
			if(_title.trim() == ""){
				alert("공지사항 제목을 작성해주시기 바랍니다");
				return;
			}
			
			if(_text.trim() == ""){
				alert("공지사항 내용을 작성해주시기 바랍니다");
				return;
			}
			
			var _obj = {};
			_obj.ntc_seq	= $("input[name=ntc_seq]").val();
			_obj.ntc_type	= $("input[name=ntc_type]").val();
			_obj.ntc_title	= _title;
			_obj.ntc_text	= _text;
			
			var _url = "/admin/set/ntc/modProc";
			
			_doAjax(_obj, _url, 1);
		}
		
	});
	
	//	삭제 버튼
	$("#delBtn").on("click", function(){
		if(confirm("삭제하시겠습니까?")){
			var _obj = {};
			_obj.ntc_seq  = $("input[name=ntc_seq]").val();
			_obj.ntc_type = $("input[name=ntc_type]").val();
			
			var _url = "/admin/set/ntc/delProc";
			
			_doAjax(_obj, _url, 2);
		}
	});
	
	
	function _doAjax(obj, url, type){
		
		var _errMsg = null;
		var _successMsg = null;
				
		if(type == 1){
			_errMsg = "저장에 실패하였습니다. 관리자에게 문의 바랍니다";
			_successMsg = "저장에 성공하였습니다";
		}else{
			_errMsg = "삭제에 실패하였습니다. 관리자에게 문의 바랍니다";
			_successMsg = "삭제에 성공하였습니다";					
		}			
		
		$.ajax({
			type: "POST",
			url: url,
			data: JSON.stringify(obj),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				
				if(res.result == false || res.result == 'false'){
					
					return alert(_errMsg);
				}else{
					alert(_successMsg);
					
					if(type == 1){	//	저장
						window.location.reload();
					}else{	//	삭제
						$("#listBtn").click();
					}
				}
			},
			error: function (e) {
				alert(_errMsg);
				console.log("error : " + e.toString())
			}
		});
	}
	
	
});