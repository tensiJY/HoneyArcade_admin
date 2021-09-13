$(function(){
	$("#listCount").selectpicker();
	
	$("#searchBtn").on("click", function(e){
		var _sido = $("#sidoSelect").val();
		var _sigungu = $("#sigunguSelect").val();
		var _dong = $("#dongSelect").val();
		var _build = $("#buildSelect").val();
		var _floor = $("#floorSelect").val();
		
		if(_sido == ""){
			alert("시도를 선택해주세요");
			return;
		}
		
		if(_sigungu == ""){
			alert("군구를 선택해주세요");
			return;
		}
		
		if(_dong == ""){
			 alert("동을 선택해주세요");
			 return;
		}
		
		if(_build == ""){
			alert("층을 선택해주세요");
			return;
		}
		
		if(_floor == ""){
			alert("층을 선택해주세요");
			return;
		}
		$("#type").val("search");
		$("#seearchForm").submit();
	});
	
	
	$("#search_text").on("keypress", function(e){
		if(e.keyCode == 13){
			
			$("#searchBtn").click();
		}
	});
	
	$("#viewBtn").on("click", function(e){
		if(confirm("선택된 상점들을 노출 시키겠습니까?")){
			var _chkArr = _getChecked();
			if(_chkArr.length == 0){
				alert("선택된 데이터가 없습니다");
				return;
			}
			var _obj = {};
			_obj.type = 2;
			_obj.data = _chkArr;
			var _url = "/admin/mgt/store/viewProc";
			_doAjax(_url, _obj, null, 2)
		}
		
	});
	
	$("#delBtn").on("click", function(e){
		if(confirm("선택된 상점들을 삭제하시겠습니까?")){
			var _chkArr = _getChecked();
			if(_chkArr.length == 0){
				alert("선택된 데이터가 없습니다");
				return;
			}
			var _obj = {};
			_obj.type = 1
			_obj.data = _chkArr;
			var _url = "/admin/mgt/store/viewProc";
			_doAjax(_url, _obj, null,1)
		}
		
	});
	
	function _getChecked(){
		var _chk = $("input[name=ckh_owner_id");
		var _arr = [];
		for(var i=0; i<_chk.length; i++){
    		var _v = $(_chk[i]).prop('checked');
    		if(_v){
        		var _ownerId = $(_chk[i]).attr('data-owner-id')
        		_arr.push(_ownerId)
    		}
		}
		return _arr;	
	}
	
	//	type : 2 노출, 1 숨김(삭제)	
	function _doAjax(url, data, callBack, type){
					
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				if(res.result == false || res.result == 'false'){
					alert("처리하는데 문제가 생겼습니다.")
					return;
				}else{
					
					alert("저장에 성공하였습니다");
					window.location.reload();
					
				}
			},
			error: function (e) {
				//console.log(e)
			}
		});
	}
	
	$("#execelDownBtn").on("click", function(){
		
		var _sido =$("#sidoSelect").val();
		var _sigungu = $("#sigunguSelect").val();
		var _dong = $("#dongSelect").val();
		var _build = $("#buildSelect").val();
		var _floor = $("#floorSelect").val();
		
		if($("#type").val()!='search'){
			alert("검색후 이용하실 수 있습니다");
			return;
		}
				
		var _list = $("#listCount").val();
		var _search = $("#search_text").val();
		
		var _query = "?sido_seq="+_sido+"&sigungu_seq="+_sigungu+"&dong_seq="+_dong+"&build_seq="+_build+"&floor_seq="+_floor+"&listCount="+_list+"&search_text="+_search		
		location.href="/admin/mgt/store/excelDown"+_query;
	});
});