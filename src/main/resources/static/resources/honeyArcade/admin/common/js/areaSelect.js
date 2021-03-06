/**
시도, 군구, 동 지역 관련
리스트 에서 사용 : 시도, 시군구, 동, 건물, 층 순서
*/
 
$(function(){
	var _sidoSelect = document.getElementById("sidoSelect");
	var _sigunguSelect = document.getElementById("sigunguSelect");
	var _dongSelect = document.getElementById("dongSelect");
	var _buildSelect = document.getElementById("buildSelect");
	var _floorSelect = document.getElementById("floorSelect");
	
	$(_sidoSelect).selectpicker();
	$(_sigunguSelect).selectpicker();
	$(_dongSelect).selectpicker();
	
	if(_buildSelect != undefined){
		$(_buildSelect).selectpicker();
		$("#dongSelect").on("change", function(){
			_selectRemove(2);
			var _url = "/common/api/getBuild";
			var _type = 4;
			var _obj = {};
			_obj.dong_seq = $(this).val();
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
	
	if(_floorSelect != undefined){
		$(_floorSelect).selectpicker();
		$("#buildSelect").on("change", function(){
			var _url = "/common/api/getFloor";
			var _type = 5
			var _obj = {};
			_obj.build_seq = $(this).val();
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
	
	function _selectRemove(type){
		if(type==1){
			if(_buildSelect != undefined){
				$(_buildSelect).children().remove();
				$(_buildSelect).selectpicker("refresh");
			}
			if(_floorSelect != undefined){
				$(_floorSelect).children().remove();
				$(_floorSelect).selectpicker("refresh");
			}	
		}else{
			if(_floorSelect != undefined){
				$(_floorSelect).children().remove();
				$(_floorSelect).selectpicker("refresh");
			}	
		}
	}
	
	$("#sidoSelect").on('change', function(){
		$("#dongSelect").children().remove();
		$("#dongSelect").selectpicker("refresh");
		_selectRemove(1);
		var _url = "/common/api/getSigungu" ;
		var _type = 2;	//	군구 조회
		var _obj = {};
		_obj.sido_seq = $(this).val();
		_doAjax(_url, _obj, _doSelectChange, _type);
	})
	
	$("#sigunguSelect").on('change', function(){
		_selectRemove(1);
		var _url = "/common/api/getDong";
		var _type = 3;
		var _obj = {};
		_obj.sigungu_seq = $(this).val();
		_doAjax(_url, _obj, _doSelectChange, _type);
	});
	
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
				callBack(res, type)
				
			},
			error: function (e) {
				//console.log(e)
			}
		});
	}
	
	function _doSelectChange(data, type){
		if(data.result == false || data.result == 'false'){
			return alert("데이터를 가져올 수가 없습니다 관리자에게 문의 바랍니다.");
		}
					
		if(type == 1){
			var _target = $("#sidoSelect");
			$(_target).children().remove()
			var _list = data.sidoList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].sido_seq;
				_e.innerText = _list[i].sido_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
			
		}else if(type == 2){
			//	시군구 리스트
			var _target = $("#sigunguSelect");
			$(_target).children().remove()
			var _list = data.sigunguList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].sigungu_seq;
				_e.innerText = _list[i].sigungu_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
				
		}else if(type == 3){
			//	동리스트	
			var _target = $("#dongSelect");
			$(_target).children().remove();
			var _list = data.dongList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].dong_seq;
				_e.innerText = _list[i].dong_name;
				$(_target).append(_e);
			}
			
			$(_target).selectpicker("refresh");
			
		}else if(type == 4){
			//	건물 선택
			var _target = $("#buildSelect");
			$(_target).children().remove()
			var _list = data.buildList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].build_seq;
				_e.innerText = _list[i].build_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
			
		}else if(type == 5){
			var _target = $("#floorSelect");
			$(_target).children().remove()
			var _list = data.floorList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].floor_seq;
				_e.innerText = _list[i].floor_type == 1? "지상 "+ _list[i].floor_seq + "층" : '지하 '+(Math.abs(_list[i].floor_seq)) + "층";
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
		}
	};//
});