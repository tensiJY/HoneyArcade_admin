$(function(){
	$("#listCount").selectpicker();
	
	//	엑셀 파일 업로드
	$("#fileUpload").on("change", function(e){
		var _target =$(this);
		//var _isExcel = koreasoft.modules.regex.isExcel($(_target).val());
		//if(_isExcel){
		//	$(_target).val("");
		//	return alert("엑셀만 업로드 할 수 있습니다");
		//}
		_doUploadExcel(_target, _excelCallback);
	})
	
	//	엑셀 서버에 저장
	function _doUploadExcel(target,  callFunction){
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
		
		_formData.append("file", _file);
		_excelUpload("/admin/mgt/build/excelUpload", _formData, callFunction, target, type);
	}
	
	//	엑셀 업로드
	var _excelUpload = function( url, formData, callback, target, type){
		$.ajax({
			type: "POST",
			url: url == null || url == ""? "/common/file/excelUpload" : url,
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			data: formData,
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				$("#l-wrapper").addClass("on");
			},
			success: function(res) {
				callback(res, target, type);
			},
			err: function(err) {
				console.log("err:", err)
			},
			complete : function(e){
				$("#l-wrapper").removeClass("on");
			}					
		})
	};
	
	//	엑셀 컬백
	var _excelCallback = function(res, target){
		if(res.result == false || res.result == 'false'){
			alert(res.msg);
			$(target).val("");
			return;
		}else{
			$(target).val("");
			alert(res.msg);
		}
	}
	
	//	엑셀 다운로드 서식
	$(".downBtn").on("click", function(){
		var _buildSeq = $(this).attr("data-build-seq");
		var _query = "?";
		_query += "build_seq="+ _buildSeq;
		 location.href="/admin/mgt/build/excelDown" + _query
	});
			
	//	검색 버튼
	$("#searchBtn").on("click", function(){
		$("#type").val("search");
		$("#buildSearchForm").submit();
	});
	
	//	건물 등록 버튼
	$("#addBtn").on("click", function(){
		location.href = "/admin/mgt/build/write";
	});
	
	//	이동 이벤트
	var _dtlArr = $(".dtlWriteBtn");
	for(var i=0; i<_dtlArr.length; i++){
		_dtlArr[i].onclick = function(e){
			e.preventDefault();
			$(this).parents('td').find('form').submit();
		}
	}
	
	//	검색 텍스트 엔터이벤트
	$("#search_text").on('keypress', function(e){
		if(e.keyCode == 13){
			$("#searchBtn").click();	
		}
	})

	//	삭제 버튼
	$("#delBtn").on("click", function(){
		if(confirm("건물을 삭제하시겠습니까?")){
			
			var _type = 3;
			var _data = _getCheckData();
			
			if(_data.length == 0){
				alert("선택된 데이터가 없습니다 확인해주시기 바랍니다");
				return;
			}
			
			var _url = "/admin/mgt/build/delProc";
			_buildListAjax(_url, _data, _resultCallback, _type);
		}
	});
	
	//	활성화 버튼
	$("#enableBtn").on("click", function(){
		if(confirm("건물을 활성화 시키겠습니까?")){
			var _type = 1;
			var _data = _getCheckData();
			
			if(_data.length == 0){
				alert("선택된 데이터가 없습니다 확인해주시기 바랍니다");
				return;
			}
			
			var _url = "/admin/mgt/build/enableProc";
			
			_buildListAjax(_url, _data, _resultCallback, _type);
		}
	});
	
	//	비활성화 버튼
	$("#disableBtn").on("click", function(){
		if(confirm("건물을 비활성 시키겠습니까?")){
			var _type = 2;
			var _data = _getCheckData();
				
			if(_data.length == 0){
				alert("선택된 데이터가 없습니다 확인해주시기 바랍니다");
				return;
			}
			
			var _url = "/admin/mgt/build/disableProc";
			
			_buildListAjax(_url, _data, _resultCallback, _type)
			
		}
	});
		
	//	선택된 데이터 가져오기
	function _getCheckData(_type){
		
		var _target = $("input[name=chk_build_seq]");
		var _dataArr = [];
		
		for(var i=0; i< _target.length; i++){
			var _chk =  _target[i];
			
			var _v = $(_chk).prop("checked");
			
			if(_v == false){
				continue;
			}	
			
			_checkObj = {};
			_checkObj.build_seq = $(_chk).val();
			_dataArr.push(_checkObj);
		}
		
		return _dataArr;
	}
	
	function _resultCallback(res, type){
		if(res.result == false || res.result == 'false'){
			alert("오류가 생겼습니다. 관리자에게 문의 바랍니다");
			return;
		}else{
			
			if(type==1){		//	활성화
				alert("건물을 활성화 하였습니다");
			}else if(type == 2){ // 비활성화
				alert("건물을 비활성화 하였습니다");
				
			}else if(type == 3){
				alert("건물을 삭제하였습니다");
			}
		}
		location.href = "/admin/mgt/build/list";
	}
	
	function _buildListAjax(url, data, callBack, type){
				
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				callBack(res, type)
				
			},
			error: function (e) {
				//console.log(e)
			}
		});
	}
		
});