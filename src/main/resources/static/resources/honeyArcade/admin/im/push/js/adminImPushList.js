$(function(){
	var _adminImPushList = function(){
		var _init = function(){
			
			flatpickr("#date-pick", {
				locale: "ko"
				, minDate : "today" 
			});
			
			flatpickr("#time-pick", {
				enableTime: true,
				noCalendar: true,
				dateFormat: "H:00",
				time_24hr: true
			});
			
			$("#notiType").on("change", function(){
				if($(this).val() == 1){
					$("#dateTarget").hide();
				}else{
					$("#dateTarget").show();
				}
			});
						
			$(".cancleBtn").on("click", function(){
				if(confirm("푸쉬알림을 취소하시겠습니까?")){
					var _obj = {};
					_obj.push_seq = $(this).attr("data-push-seq");
					_doAjax(_obj, "/admin/im/push/cancelProc", _cancelCallBack);
				}
			});
			
			$(".viewBtn").on("click", function(){
				var _obj = {};
				_obj.push_seq = $(this).attr("data-push-seq");
				_doAjax(_obj, "/admin/im/push/getData", _viewCallBack);
			});
			
			var _btnArr = ["popSidoSelect", "popSigunguSelect","popDongSelect", "popBuildSelect", "popStoreSelect"];
			var _btnEvt = [];
			var _tagBtn = $("button");
			
			for(var i=0; i< _tagBtn.length ; i++){
				var _dataId = $(_tagBtn[i]).attr("data-id");
				for(var j=0; j<_btnArr.length; j++){
					if(_btnArr[j] == _dataId){
						_btnEvt.push(_tagBtn[i])
					}
				}
			};
			
			_btnHide(_btnEvt);
						
			$("#pushTypeSelect").on("change", function(){
				var _v = $(this).val();
				_btnHide(_btnEvt);
				if( 5 >_v && _v >0 ){
					for(var i=0; i<_btnEvt.length-1; i++){
						$(_btnEvt[i]).show();
					}
				}else if(_v == 5){
					for(var i=0; i<_btnEvt.length; i++){
						$(_btnEvt[i]).show();
					}
				}
			});
			
			$("#saveBtn").on("click", function(){
				if(confirm("저장하시겠습니까?")){
					var _pushTypeSelect = $("#pushTypeSelect").val();
					var _popBuildSelect = $("#popBuildSelect").val();
					var _popStoreSelect	= $("#popStoreSelect").val();
					var _pushText = $("#pushText").val();
					var _date = $("#date-pick").val();
					var _time = $("#time-pick").val();
					var _notiType = $("#notiType").val();
					var _pushTitle = $("#pushTitle").val();
					
					if(_pushTitle == ""){
						alert("알림 제목을 작성해주세요");
						return;
					}
					
					if(_pushTypeSelect==""){
						alert("유형을 선택해주세요");
						return;
					}
					
					if(_pushTypeSelect<5 && _pushTypeSelect>0){
						if(_popBuildSelect==""){
							alert("건물을 선택해주세요");
							return;	
						}
					}
					
					if(_pushTypeSelect == 5){
						if(_popStoreSelect==""){
							alert("상점을 선택해주세요");
							return;
						}
					}
					
					if(_pushText == ""){
						alert("알림 내용을 작성해주세요");
						return;
					}
					
					if(_notiType == 2 || _notiType == "2"){
						if(_date == ""){
							alert("날짜를 선택해주세요");
							return;
						}
						if(_time==""){
							alert("시간을 선택해주세요");
							return;
						}
					}
					
					var _obj = {};
					_obj.push_type = _pushTypeSelect
					_obj.push_text = _pushText;
					_obj.build_seq = _popBuildSelect;
					_obj.store_seq = _popStoreSelect;
					_obj.psuh_rez_dt = _date;
					_obj.push_rez_time = _time;
					_obj.build_name = $("#popBuildSelect option:selected").text();
					_obj.store_name = $("#popStoreSelect option:selected").text();
					_obj.noti_type = _notiType;
					_obj.push_title = _pushTitle;
					
					_doAjax( _obj, "/admin/im/push/saveProc", _saveCallBack)
				}
			});
			
			$("#modalCloseBtn").on("click", function(){
				_modalClear();
				$("#myModal").modal("hide");
			});
		}
		
		function _saveCallBack(res){
			if(res.result == "false" || res.result == false){
				alert(res.msg);
				return;
			}else{
				alert(res.msg);
				window.location.reload();				
			}
		}
		
		function _btnHide(_btnEvt){
			for(var i=0; i<_btnEvt.length; i++){
				$(_btnEvt[i]).hide();
			}
		}
		
		function _cancelCallBack(res){
			if(res.result == "false" || res.result == false){
				alert(res.msg);
				return;
			}else{
				alert(res.msg);
				window.location.reload();
			}
		}
		
		function _modalClear(){
			$("#modalPushText").val("");	
			$("#modalAndroidLink").val("");
			$("#modalIosLink").val("");
			$("#modalAndCnt").val("");
			$("#modalIosCnt").val("");
			$("#modalPushTitle").val("");
		}
		
		function _viewCallBack(res){
			if(res.result == "false" || res.result == false){
				alert(res.msg);
				return;
			}else{
				_modalClear();
				$("#modalPushText").val(res.pushVO.push_text);
				$("#modalAndroidLink").val(res.pushVO.push_android_link);
				$("#modalIosLink").val(res.pushVO.push_ios_link);
				$("#modalAndCnt").val(res.pushVO.and_cnt);
				$("#modalIosCnt").val(res.pushVO.ios_cnt);
				$("#myModal").modal("show");
				$("#modalPushTitle").val(res.pushVO.push_title);
			}
		}
		
		function _doAjax(data, url, callback){
			$.ajax({
				type: "POST",
				url: url,
				data: data == null? null : JSON.stringify(data),
				contentType : "application/json; charset=utf-8", 
				beforeSend: function (xhr) {
					xhr.setRequestHeader(_csrfHeader, _csrfToken);
					$("#l-wrapper").addClass("on");
				},
				success: function (res) {
					callback(res)
				},
				error: function (e) {
					alert("처리하는데 문제가 생겼습니다 관리자에게 문의 바랍니다");
				},
				complete : function(e){
					$("#l-wrapper").removeClass("on");
				}
			});
		}
		
		return {
			init : _init
		}
	}
	
	new _adminImPushList().init();
});