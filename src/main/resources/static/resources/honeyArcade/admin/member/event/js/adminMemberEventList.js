var _adminMemberEventList = function(){
	
	var init = function(){
		
		//	검색 텍스트 엔터 이벤트
		$("#search_text").on("keypress", function(e){
			if(e.keyCode == 13){
				$("#searchBtn").click();
			}
		})
		
		//	검색 버튼 이벤트
		$("#searchBtn").on("click", function(e){
			$("#searchForm").submit();
		});
		
		//	이용관리 탭 이벤트
		$("#home-tab").on("click", function(e){
			var _mode = "new";
			$("#pageMode").val(_mode) 
			_btnEvent(_mode);
			_tabClear();
		})
		
		//	서비스 추가 탭 이벤트
		$("#contact-tab").on("click", function(){
			var _mode = "new";
			$("#pageMode").val(_mode) 
			_tabClear();
		});
		
		//	취소 버튼 이벤트
		$("#cancelBtn").on("click", function(){
			_tabClear();
		});
		
		//	저장 버튼 이벤트
		$("#saveBtn").on("click", function(){
			if(confirm("저장하시겠습니까?")){
				var _type = "save";
				if(_checkData()){
					var _obj = _getEventData(_type);
					var _url = "/admin/member/event/saveProc";
					
					_doAjax(_obj, _url, _type);	
				}
			}
		});
		
		//	수정 저장 이벤트
		$("#modBtn").on("click", function(){
			if(confirm("저장하시겠습니까?")){
				var _type = "modify";
				if(_checkData()){
					var _obj = _getEventData(_type);
					var _url = "/admin/member/event/modProc";
					
					_doAjax(_obj, _url, _type);						
				}
			}
		});
		
		//	삭제 버튼 이벤트
		$("#delBtn").on("click", function(){
			if(confirm("삭제하시겠습니까?")){
				var _obj = {};
				_obj.event_seq = $("#event_seq").val();
				var _url = "/admin/member/event/delete";

				_doAjax(_obj, _url, "delete");
			}
		});
		
		//	수정 버튼 이벤트 : 데이터 가져옮> 탭 전환
		var _modBtn = $(".modBtn");
		if(_modBtn.length != 0){
			$(_modBtn).on("click", function(){
				var _mode = "modify"
				$("#pageMode").val(_mode);
				_btnEvent(_mode);
				_tabClear(); 
				
				$("#home-tab").removeClass("active").attr("aria-selected", false);
				$("#home").removeClass("show").removeClass("active");
				$("#contact-tab").addClass("active").attr("aria-selected", true);
				$("#contact").addClass("show").addClass("active");
				
				var _obj = {};
				_obj.event_seq = $(this).attr("data-seq");
				var _url = "/admin/member/event/getData";
				
				_doAjax(_obj, _url, "get");
			});
		}
	}
	
	function _checkData(){
		var _eventTitle	= $("#eventTitle").val();
		var _eventText	= $("#eventText").val();
		var _eventPrice	= $("#eventPrice").val();
		var _eventSort	= $("#eventSort").val();
		
		if(_eventTitle.trim()==""){
			alert("유료 상품명을 작성해주세요");
			$("#eventTitle").focus();
			return false;
		}	
				
		if(_eventText.trim()==""){
			alert("상품 설명을 작성해주세요");
			$("#eventText").focus();
			return false;
		}
				
		if(_eventPrice.trim()==""){
			alert("가격을 작성해주세요");
			$("#eventPrice").focus();
			return false;
		}
		
		if(_eventPrice < 0){
			alert("가격은 0보다 큰 숫자를 입력해주세요");
			$("#eventPrice").focus();
			return false;
		}
		
		if(_eventSort.trim()==""){
			alert("노출순위를 작성해주세요");
			$("#eventSort").focus();
			return false;
		}
		
		if(_eventSort < 0){
			alert("노출순위는 0보다 큰 숫자를 입력해주세요");
			$("#eventSort").focus();
			return false;
		}
		
		var _cnt = 0;
		var _chk = $("input[type=checkbox]");
		
		for(var i=0; i<_chk.length; i++){
			if($(_chk[i]).prop("checked") == false){
				_cnt++;
			}
		}
		
		if(_cnt == 3){
			alert("상품 유형을 선택해주세요");
			return false;
		}
		
		var _buildCheck		= $("#buildCheck").prop("checked");
		var _areaCheck		= $("#areaCheck").prop("checked");
		var _couponCheck	= $("#couponCheck").prop("checked");
		
		if(_buildCheck){
			var _buildDay = $("#buildDay").val();
			
			if(_buildDay.trim()==""){
				alert("건물 광고 배너 일을 작성해주세요");
				$("#buildDay").focus();
				return false;
			}
			
			if(_buildDay < 0){
				alert("건물 광고 배너 일은 0보다 큰 숫자를 입력해주세요");
				$("#buildDay").focus();
				return false;
			}
			
		}
		
		if(_areaCheck){
			var _areaDay = $("#areaDay").val();
			if(_areaDay.trim()==""){
				alert("지역 광고 배너 일을 작성해주세요");
				$("#areaDay").focus();
				return false;
			}
			
			if(_areaDay < 0){
				alert("지역 광고 배너 일은 0보다 큰 숫자를 입력해주세요");
				$("#areaDay").focus();
				return false;
			}			
		}
		
		if(_couponCheck){
			var _couponDay = $("#couponDay").val();
			if(_couponDay.trim()==""){
				alert("쿠폰 일을 작성해주세요");
				$("#couponDay").focus();
				return false;
			}
			
			if(_couponDay < 0){
				alert("쿠폰 일은 0보다 큰 숫자를 입력해주세요");
				$("#couponDay").focus();
				return false;
			}				
		}
		
		return true;
	}
	
	function _getEventData(_type){
		var _eventTitle	= $("#eventTitle").val();
		var _eventText	= $("#eventText").val();
		var _eventPrice	= $("#eventPrice").val();
		var _eventSort	= $("#eventSort").val();
		
		var _obj = {};
		_obj.event_title	= _eventTitle;
		_obj.event_text		= _eventText;
		_obj.event_price	= _eventPrice;
		_obj.event_sort		= _eventSort 
		_obj.event_dtl		= [];
		
		var _buildCheck		= $("#buildCheck").prop("checked");
		var _areaCheck		= $("#areaCheck").prop("checked");
		var _couponCheck	= $("#couponCheck").prop("checked");
		
		if(_buildCheck){
			var _buildDay = $("#buildDay").val();
			var _event = {};
			_event.event_dtl_type = 1;
			_event.event_dtl_day = _buildDay;
			_obj.event_dtl.push(_event);
		}
		
		if(_areaCheck){
			var _areaDay = $("#areaDay").val();
			var _event = {};
			_event.event_dtl_type = 2;
			_event.event_dtl_day = _areaDay;
			_obj.event_dtl.push(_event);			
		}
		
		if(_couponCheck){
			var _couponDay = $("#couponDay").val();
			var _event = {};
			_event.event_dtl_type = 3;
			_event.event_dtl_day = _couponDay;
			_obj.event_dtl.push(_event);					
		}
		
		if(_type == "modify"){
			_obj.event_seq = $("#event_seq").val();
		}
		
		return _obj;
	}
	
	function _tabClear(){
		$("#eventTitle").val("");
		$("#eventText").val("");
		$("#eventPrice").val("");
		$("#eventSort").val("");
		$("input[type=checkbox]").prop("checked", false);
		$("#buildDay").val("");
		$("#areaDay").val("");
		$("#couponDay").val("");
		
		var _pageMode = $("#pageMode").val();
		if(_pageMode == "new"){
			$("#event_seq").val("");
		}
	}
	
	function _btnEvent(mode){
		if(mode == "new"){
			$("#saveBtn").show();
			$("#modBtn").hide();
			$("#delBtn").hide();
		}else{
			$("#saveBtn").hide();
			$("#modBtn").show();
			$("#delBtn").show();
		}
	}
	
	function _doAjax(data, url, type){
		var _msg = type=="get"? "데이터를 불러오는데 오류가 생겼습니다. 관리자에게 문의 바랍니다." : "데이터를 처리하는데 문제가 생겼습니다. 관리자에게 문의바랍니다.";
		
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				if(res.result == false || res.result == "false"){
					alert(_msg);
				}else{
					//	get : 수정버튼으로 넘어온 경우
					if(type == "get"){
						
						var _areaDay 	= res.data.area_day;
						var _buildDay	= res.data.build_day;
						var _couponDay	= res.data.coupon_day;
						
						if(_areaDay != null){
							$("#areaCheck").prop("checked", true);
							$("#areaDay").val(_areaDay);
						}
						
						if(_buildDay != null){
							$("#buildCheck").prop("checked", true);
							$("#buildDay").val(_buildDay);
						}
						
						if(_couponDay != null){
							$("#couponCheck").prop("checked", true);
							$("#couponDay").val(_couponDay);
						}
						
						$("#eventText").val(res.data.event_text);
						$("#eventSort").val(res.data.event_sort);
						$("#eventPrice").val(res.data.event_price);
						$("#eventTitle").val(res.data.event_title);
						$("#event_seq").val(res.data.event_seq);
						
					}else if(type=="delete"){
						alert("삭제에 성공하였습니다");
						window.location.reload();
						
					}else{
						alert("저장에 성공하였습니다");
						window.location.reload();
					}
					
				}
			},
			error: function (e) {
				alert(_msg);
				console.log("error : " + e.toString())
			}
		});
	}
	
	return {
		init : init
	}
	
};


$(function(){
	new _adminMemberEventList().init();
})

