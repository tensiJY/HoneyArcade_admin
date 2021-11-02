/**
전체 조회
	>> 모달에서 사용 , 관리자 등급없이 조회
		>> 1) 쿠폰, 배너에서의 건물설정
		>> 2) 푸쉬 알림 
1) 시도, 시군구, 동, 건물 순서
2) 시도, 시군구, 동, 건물, 상호명(가입되어있는 점포)

*/
$(function(){
	var _sidoSelect = document.getElementById("popSidoSelect");
	var _sigunguSelect = document.getElementById("popSigunguSelect");
	var _dongSelect = document.getElementById("popDongSelect");
	var _buildSelect = document.getElementById("popBuildSelect");
	var _storeSelect = document.getElementById("popStoreSelect");
	
	$(_sidoSelect).selectpicker();
	$(_sigunguSelect).selectpicker();
	$(_dongSelect).selectpicker();
	
	if(_buildSelect != undefined){
		$(_buildSelect).selectpicker();
		$(_dongSelect).on("change", function(){
			_selectRemove(2);
			var _url = "/common/api/getAllBuild";
			var _type = 4;
			var _obj = {};
			_obj.dong_seq = $(this).val();
			_doAjax(_url, _obj, _doSelectChange, _type);
		});
	}
	
	if(_storeSelect != undefined){
		$(_storeSelect).selectpicker();
		$(_buildSelect).on("change", function(){
			var _url = "/common/api/getAllStore";
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
			if(_storeSelect != undefined){
				$(_storeSelect).children().remove();
				$(_storeSelect).selectpicker("refresh");
			}	
		}else{
			if(_storeSelect != undefined){
				$(_storeSelect).children().remove();
				$(_storeSelect).selectpicker("refresh");
			}	
		}
	}
	
	$(_sidoSelect).on('change', function(){
		$(_dongSelect).children().remove();
		$(_dongSelect).selectpicker("refresh");
		_selectRemove(1);
		var _url = "/common/api/getAllSigungu" ;
		var _type = 2;	//	군구 조회
		var _obj = {};
		_obj.sido_seq = $(this).val();
		_doAjax(_url, _obj, _doSelectChange, _type);
	})
	
	$(_sigunguSelect).on('change', function(){
		_selectRemove(1);
		var _url = "/common/api/getAllDong";
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
				$("#l-wrapper").addClass("on");
			},
			success: function (res) {
				callBack(res, type)
				
			},
			error: function (e) {
				//console.log(e)
			},
			complete : function(e){
				$("#l-wrapper").removeClass("on");
			}
		});
	}
	
	function _doSelectChange(data, type){
		if(data.result == false || data.result == 'false'){
			return alert("데이터를 가져올 수가 없습니다 관리자에게 문의 바랍니다.");
		}
					
		if(type == 1){
			var _target = $(_sidoSelect);
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
			var _target = $(_sigunguSelect);
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
			var _target = $(_dongSelect);
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
			var _target = $(_buildSelect);
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
			var _target = $(_storeSelect);
			$(_target).children().remove()
			var _list = data.storeList;
			for(var i=0; i<_list.length; i++){
				var _e = document.createElement("option")
				_e.value = _list[i].owner_id;
				_e.innerText = _list[i].store_name;
				$(_target).append(_e);
			}
			$(_target).selectpicker("refresh");
		}
	};//
});