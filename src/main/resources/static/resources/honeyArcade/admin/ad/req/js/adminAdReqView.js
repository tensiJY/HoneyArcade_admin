$(function(){
	$("#listBtn").on("click", function(){
		$("#listForm").submit();
	});
	
	//	승인
	$("#acceptBtn").on("click", function(){
		if(confirm("승인 하시겠습니까?")){
			_doAjax(1);
		}
	});
	
	//	반려
	$("#rejectBtn").on("click", function(){
		$(this).attr("data-toggle", "modal");
	});
	
	//	모달 취소
	$("#popCancelBtn").on("click", function(){
		$(this).attr("data-toggle", "");
	});
	
	//	반려 저장
	$("#popSaveBtn").on("click", function(){
		if(confirm("반려하시겠습니까?")){
			var _v = $("#rejectReason").val();
			if(_v==""){
				alert("반려 내용을 작성해주시기 바랍니다");
				return;
			}
			
			$("input[name=ad_req_reject_reason]").val(_v);
			$('.modal').modal('hide');
			_doAjax(2);
			
		}else{
			$('.modal').modal('hide');
		}
	});
	
	//	파일 다운로드
	$(".downBtn").on("click", function(){
		var _fileSeq = $(this).attr("data-file-seq");
		var _fileDtlSeq= $(this).attr("data-file-dtl-seq")
		koreasoft.modules.file.download(_fileSeq, _fileDtlSeq);
	});
	
	function _doAjax(type){
		var _adReqDt = $("input[name=ad_req_dt]").val();
		
		var _obj 		 			= {};
		_obj.ad_seq		 			= $("input[name=ad_seq]").val();
		_obj.ad_req_type			= $("input[name=ad_req_type]").val();
		_obj.ad_req_reject_reason 	= $("input[name=ad_req_reject_reason]").val(); 
		_obj.member_id				= $("input[name=member_id]").val();		
		_obj.owner_id				= $("input[name=owner_id]").val();
		_obj.owner_email			= $("input[name=owner_email]").val();
		_obj.ad_req_type_text		= $("input[name=ad_req_type_text]").val();
		_obj.ad_req_dt				= _adReqDt.substring(0, 4) + "년 " + _adReqDt.substring(5,7) +"월 " + _adReqDt.substring(8) + "일";
		
		$.ajax({
			type: "POST",
			url: type ==1 ? "/admin/ad/req/accept" : "/admin/ad/req/reject",
			data: JSON.stringify(_obj),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				$("#l-wrapper").addClass("on");
			},
			success: function (res) {
				if(res.result == false || res.result == 'false'){
					alert("서버 처리에 문제가 생겼습니다. 관리자에게 문의 바랍니다");
				}else{
					
					if(type == 1){
						alert("승인하였습니다");
					}else{
						alert("반려하였습니다");
					}
					
					window.location.reload();
				}
				
			},
			error: function (e) {
				//console.log(e)
			},
			complete : function(e){
				$("#l-wrapper").removeClass("on");
			}		
		});
	}
});