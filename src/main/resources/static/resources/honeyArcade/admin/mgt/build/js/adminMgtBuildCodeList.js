$(function(){
	
	var codeAppInit = function(){
		
		var _that = $("#sidoTarget");//document.querySelector(target);
		_targetClear(1);
		_btnHide(1);
		
		if($(_that).children().find('form').length == 0){
			console.log('no data...')
			
			$("#sigunguTarget").append(_sidoAddClone());
			$("#dongTarget").append(_sidoAddClone());
			$("#sidoTarget").append(_notdataClone());
			
			return; 
		}
		
		var _sidoModBtn = $(_that).children().find(".sidoModBtn");
		var _sidoCancelBtn = $(_that).children().find(".sidoCancelBtn");
		var _sidoSaveBtn = $(_that).children().find(".sidoSaveBtn");
		var _sidoDelBtn = $(_that).children().find(".sidoDelBtn");
		var _sidoText = $(_that).children().find(".sidoText");
		var _sidoName = $(_that).children().find("input[name=sido_name]");
			
		for (var i = 0; i < _sidoModBtn.length; i++) {
			_addSidoEvt(_sidoModBtn[i], 1);
			_addSidoEvt(_sidoCancelBtn[i], 2);
			_addSidoEvt(_sidoSaveBtn[i], 3);
			_addSidoEvt(_sidoDelBtn[i], 4);
			_addSidoEvt(_sidoText[i],5);
			_addSidoEvt(_sidoName[i],6);
		}
		
		_sidoText[0].click();
		_btnShow(1);
	}
		
	function _doAjax(url, data, callBack, type, target){
					
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				callBack(res, type, target);
			},
			error: function (e) {
				console.log("error : " + e.toString())
			}
		});
	}
	
	function _addSidoEvt(target, type){
		//	1 수정, 2 취소, 3저장, 4삭제, 5text 이벤트, 6 수정 값
		
		if(type == 1){
			target.onclick = function(e) {
				$(this).parents('form').find('div.evtBtnBox').find('.sidoModBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.sidoDelBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.sidoSaveBtn').show();
				$(this).parents('form').find('div.evtBtnBox').find('.sidoCancelBtn').show();
				$(this).parents('form').find('div.evtTextBox').find(".sidoText").hide();
				$(this).parents('form').find('div.evtTextBox').find("input[name=sido_name]").show();
				$(this).parents('form').find('div.evtTextBox').find("input[name=sido_name]").focus();
				var _v = $(this).parents('form').find('div.evtTextBox').find("input[name=sido_seq]").val();
				$("#target_sido_seq").val(_v);
				_getSigungu($(this));
			}		
			
		}else if(type == 2){
			//	시도 취소
			target.onclick = function(e){
				$(this).parents('form').find('div.evtBtnBox').find('.sidoModBtn').show();
				$(this).parents('form').find('div.evtBtnBox').find('.sidoDelBtn').show();
				$(this).parents('form').find('div.evtTextBox').find(".sidoText").show();
				$(this).parents('form').find('div.evtBtnBox').find('.sidoSaveBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.sidoCancelBtn').hide();
				$(this).parents('form').find('div.evtTextBox').find("input[name=sido_name]").hide();

				var _ori = $(this).parents('form').find('div.evtTextBox').find(".sidoText").val();
				var _new = $(this).parents('form').find('div.evtTextBox').find("input[name=sido_name]").val();
				if(_ori != _new) {
					$(this).parents('form').find('div.evtTextBox').find("input[name=sido_name]").val(_ori);	
				}
				_getSigungu($(this));
				
				$(this).parents('form').find('div.evtTextBox').find(".sidoText").addClass("area-active");
			
			}
			
		}else if(type == 3){
			target.onclick = function(e){
				if(confirm("저장하시겠습니까?")){
					var _arr = [];
					var _obj = {};
					_obj.sido_seq = $(this).parents('form').find('div.evtTextBox').find("input[name=sido_seq]").val();
					_obj.sido_name = $(this).parents('form').find('div.evtTextBox').find("input[name=sido_name]").val();
					
					if(_obj.sido_name == ""){
						alert("시도명을 작성해주시기 바랍니다");
						return;
					}
					
					_arr.push(_obj);
					var _url = "/admin/mgt/build/sidoModProc";
					var _target = $(this);
					_doAjax(_url, _arr, _sidoCallEvt, type, _target)																				
				}	
			}
		}else if(type == 4){
			target.onclick = function(e){
				if(confirm("상위코드 삭제시 하위코드도 같이 삭제 됩니다. 삭제하시겠습니까?")){
					
					var _arr = [];
					var _obj = {};
					_obj.sido_seq = $(this).parents('form').find('div.evtTextBox').find("input[name=sido_seq]").val();
					
					_arr.push(_obj); 
					
					var _url = "/admin/mgt/build/sidoDelProc";
					var _target = $(this).parents('form').parent('div');
					_doAjax(_url, _arr, _sidoCallEvt, type, _target);
					
				}	
			}
		}else if(type == 5 || type == 6){	// text 이벤트
			target.onclick = function(e){
				_getSigungu($(this));
			}
		}
	}
	
	function _getSigungu(target){
		_btnHide(1);
		
		var _obj = {}
		 _obj.sido_seq = $(target).parents('form').find('div.evtTextBox').find("input[name=sido_seq]").val(); 
						
		var _url = "/common/api/getSigungu";
		var _type = 2;
		
		_doAjax(_url, _obj, _dataSetCall, _type);
		_removeSidoActive();
		$(target).addClass('area-active');
		
		_btnShow(1);
	}
	
	//	시도 컬백 이벤트
	function _sidoCallEvt(res, type, target){
		if(res.result == false || res.result == 'false'){
			return alert("오류가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		
		if(type==3){
			alert("저장에 성공하였습니다");	//	시도명 변경
			
			var _v = $(target).parents('form').find('div.evtTextBox').find("input[name=sido_name]").val();
			
			$(target).hide();			 // 저장 버튼
			
			$(target).parents('form').find('div.evtBtnBox').find('.sidoCancelBtn').hide();
			$(target).parents('form').find('div.evtTextBox').find("input[name=sido_name]").hide();
			$(target).parents('form').find('div.evtBtnBox').find('.sidoModBtn').show();
			$(target).parents('form').find('div.evtBtnBox').find('.sidoDelBtn').show();
			$(target).parents('form').find('div.evtTextBox').find(".sidoText").show();
			$(target).parents('form').find('div.evtTextBox').find(".sidoText").val(_v);
		}else if(type==4){
			alert("삭제 되었습니다");
			$(target).remove();
			
			var _len = $("#sidoTarget").children().length;
			_targetClear(1);
			
			if(_len == 0){	
				_btnHide(1);
				$("#sigunguTarget").children().remove();
				$("#dongTarget").children().remove();
				
				$("#sidoTarget").append(_notdataClone());
				$("#sigunguTarget").append(_sidoAddClone());
				$("#dongTarget").append(_sidoAddClone());
			}else{
				var _nameSeqArr = $("#sidoTarget").find("input[name=sido_seq]");
				var _check = 0;
				var _idx = 0;
				for(var i = 0; i<_nameSeqArr.length; i++){
					var _v = _nameSeqArr[i].value;
					if(_v == ""){
						_check ++;
					}else{
						_idx = i;
						break;
					}
				};
				
				if(_check == _nameSeqArr.length){
					_btnHide(1);
					_clear(1);
					$("#sigunguTarget").append(_sidoAddClone());
					$("#dongTarget").append(_sidoAddClone());
				}else{
					var _c = _nameSeqArr[_idx];
				
					
					$(_c).parents('form').find('.sidoText')[0].click();
				}	
			}
			
		}
	}
	
	//	시군구 이벤트
	function _addSigunguEvt(target, type){
		//	1 수정, 2 취소, 3저장, 4삭제, 5text 이벤트
		
		if(type == 1){
			target.onclick = function(e) {
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguModBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguDelBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguSaveBtn').show();
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguCancelBtn').show();
				$(this).parents('form').find('div.evtTextBox').find(".sigunguText").hide();
				$(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").show();
				$(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").focus();
				
				var _v = $(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_seq]").val();
				$("#target_sigungu_seq").val(_v);
				_getDong($(this));
			}		
		}else if(type == 2){
			//	시군구 취소
			target.onclick = function(e){
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguModBtn').show();
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguDelBtn').show();
				$(this).parents('form').find('div.evtTextBox').find(".sigunguText").show();
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguSaveBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.sigunguCancelBtn').hide();
				$(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").hide();
				
				var _ori = $(this).parents('form').find('div.evtTextBox').find(".sigunguText").val();
				var _new = $(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").val();
				
				if(_ori != _new) { 
					$(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").val(_ori);	
				}
				_getDong($(this));
			}
			
		}else if(type == 3){
			target.onclick = function(e){
				if(confirm("저장하시겠습니까?")){
					var _arr = [];
					var _obj = {};
					_obj.sigungu_seq = $(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_seq]").val();
					_obj.sigungu_name = $(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").val();
					
					if(_obj.sigungu_name == ""){
						alert("군,구명을 작성해주시기 바랍니다");
						return;
					}
					
					_arr.push(_obj);
					var _url = "/admin/mgt/build/sigunguModProc";
					var _target = $(this);
					_doAjax(_url, _arr, _sigunguCallEvt, type, _target)																				
				}	
			}
		}else if(type == 4){
			target.onclick = function(e){
				if(confirm("군,구 삭제시 지역도 같이 삭제 됩니다. 삭제하시겠습니까?")){
					
					var _arr = [];
					var _obj = {};
					_obj.sigungu_seq = $(this).parents('form').find('div.evtTextBox').find("input[name=sigungu_seq]").val();
					_arr.push(_obj); 
					
					var _url = "/admin/mgt/build/sigunguDelProc";
					var _target = $(this).parents('form').parent('div');
					_doAjax(_url, _arr, _sigunguCallEvt, type, _target)
					
				}	
			}
		}else if(type == 5 || type == 6){	
			target.onclick = function(e){
				_getDong($(this));
			}
		}
	}
	
	function _getDong(target){
		_btnHide(2);
		
		var _obj = {}
		_obj.sigungu_seq = $(target).parents('form').find('div.evtTextBox').find("input[name=sigungu_seq]").val(); 
		var _url = "/common/api/getDong";
		var _type = 3;
		_removeSigunguActive();
		
		_doAjax(_url, _obj, _dataSetCall, _type);
		$(target).addClass('area-active');
		_btnShow(1);		
	}
	
	//	시군구 컬백 이벤트
	function _sigunguCallEvt(res, type, target){
		if(res.result == false || res.result == 'false'){
			return alert("오류가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		
		if(type==3){
			alert("저장에 성공하였습니다");	//	시군구명 변경
			var _v = $(target).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").val();
			$(target).hide(); // 저장 버튼
			$(target).parents('form').find('div.evtBtnBox').find('.sigunguCancelBtn').hide();
			$(target).parents('form').find('div.evtTextBox').find("input[name=sigungu_name]").hide();
			$(target).parents('form').find('div.evtBtnBox').find('.sigunguModBtn').show();
			$(target).parents('form').find('div.evtBtnBox').find('.sigunguDelBtn').show();
			$(target).parents('form').find('div.evtTextBox').find(".sigunguText").show();
			$(target).parents('form').find('div.evtTextBox').find(".sigunguText").val(_v);
			
		}else if(type==4){
			alert("삭제 되었습니다");
			$(target).remove();	
			var _len = $("#sigunguTarget").children().length;
			_targetClear(3);
			
			if(_len == 0){	//	데이터 등 없을 경우
				_btnHide(2);
				$("#sigunguTarget").append(_notdataClone());
			}else{
				var _nameSeqArr = $("#sigunguTarget").find("input[name=sigungu_seq]");
				var _check = 0;
				var _idx = 0;
				
				for(var i = 0; i<_nameSeqArr.length; i++){
					var _v = _nameSeqArr[i].value;
					if(_v == ""){
						_check ++;
					}else{
						_idx = i;
						break;
					}
				};
				
				if(_check == _nameSeqArr.length){
					_btnHide(2);
					_clear(2);
					$("#dongTarget").append(_sidoAddClone());
				}else{
					var _c = _nameSeqArr[_idx];
					$(_c).parents('form').find('.sigunguText')[0].click();
				}	
			}
		}
	}
	
	//	동 이벤트 추가
	function _addDongEvt(target, type){
		//	1 수정, 2 취소, 3저장, 4삭제, 5span 이벤트
		if(type == 1){
			target.onclick = function(e) {
				$(this).parents('form').find('div.evtBtnBox').find('.dongModBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.dongDelBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.dongSaveBtn').show();
				$(this).parents('form').find('div.evtBtnBox').find('.dongCancelBtn').show();
				$(this).parents('form').find('div.evtTextBox').find(".dongText").hide();
				$(this).parents('form').find('div.evtTextBox').find("input[name=dong_name]").show();
				$(this).parents('form').find('div.evtTextBox').find("input[name=dong_name]").focus();
				_removeDongActive();
				$(this).addClass("area-active");
			}		
		}else if(type == 2){
			//	동 취소
			target.onclick = function(e){
				var _dongText = $(this).parents('form').find('div.evtTextBox').find(".dongText");
				$(this).parents('form').find('div.evtBtnBox').find('.dongModBtn').show();
				$(this).parents('form').find('div.evtBtnBox').find('.dongDelBtn').show();
				$(_dongText).show();

				$(this).parents('form').find('div.evtBtnBox').find('.dongSaveBtn').hide();
				$(this).parents('form').find('div.evtBtnBox').find('.dongCancelBtn').hide();
				$(this).parents('form').find('div.evtTextBox').find("input[name=dong_name]").hide();

				var _ori = $(_dongText).val();
				var _new = $(this).parents('form').find('div.evtTextBox').find("input[name=dong_name]").val()
				if(_ori != _new) { 
					$(this).parents('form').find('div.evtTextBox').find("input[name=dong_name]").val(_ori);	
				}
				
				_removeDongActive();
				$(_dongText).addClass("area-active");
			}
			
		}else if(type == 3){
			target.onclick = function(e){
				if(confirm("저장하시겠습니까?")){
					var _arr = [];
					var _obj = {};
					_obj.dong_seq = $(this).parents('form').find('div.evtTextBox').find("input[name=dong_seq]").val();
					_obj.dong_name = $(this).parents('form').find('div.evtTextBox').find("input[name=dong_name]").val();
					
					if(_obj.dong_name == ""){
						alert("지역명을 작성해주시기 바랍니다");
						return;
					}
					
					_arr.push(_obj);
					var _url = "/admin/mgt/build/dongModProc";
					var _target = $(this);
					_doAjax(_url, _arr, _dongCallEvt, type, _target)																				
				}	
			}
		}else if(type == 4){
			target.onclick = function(e){
				if(confirm("선택된 지역을 삭제하시겠습니까?")){
					
					var _arr = [];
					var _obj = {};
					_obj.dong_seq = $(this).parents('form').find('div.evtTextBox').find("input[name=dong_seq]").val();
					
					_arr.push(_obj); 
					
					var _url = "/admin/mgt/build/dongDelProc";
					var _target = $(this).parents('form').parent('div');
					_doAjax(_url, _arr, _dongCallEvt, type, _target)
					
				}	
			}
		}else if(type == 5 || type == 6){
			target.onclick = function(e){
				_removeDongActive();
				$(target).addClass('area-active');	
			}
		}
	}	
	
	//	동 컬백 이벤트
	function _dongCallEvt(res, type, target){
		if(res.result == false || res.result == 'false'){
			return alert("오류가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		
		if(type==3){
			alert("저장에 성공하였습니다");	
			var _v = $(target).parents('form').find('div.evtTextBox').find("input[name=dong_name]").val();
			$(target).hide();
			$(target).parents('form').find('div.evtBtnBox').find('.dongCancelBtn').hide();
			$(target).parents('form').find('div.evtTextBox').find("input[name=dong_name]").hide();
			$(target).parents('form').find('div.evtBtnBox').find('.dongModBtn').show();
			$(target).parents('form').find('div.evtBtnBox').find('.dongDelBtn').show();
			$(target).parents('form').find('div.evtTextBox').find(".dongText").show();
			$(target).parents('form').find('div.evtTextBox').find(".dongText").val(_v);
		}else if(type==4){
			alert("삭제 되었습니다");
			$(target).remove();	
			if($("#dongTarget").children().length == 0){
				$("#dongTarget").append(_notdataClone())
			}
		}
	}	
	
	function _createForm(target, obj, type){
		//	2 : 시군구, 3: 동(지역)
		if(type == 2){
			var _cloneTag = $("#sigunguCloneDiv").children('div').clone()
			_cloneTag.children().find('.sigunguText').val(obj.sigungu_name);
			_cloneTag.children().find("input[name=sigungu_name]").val(obj.sigungu_name);
			_cloneTag.children().find("input[name=sigungu_seq]").val(obj.sigungu_seq);
			_cloneTag.children().find("input[name=new_sigungu_name]").hide();
			$(target).append(_cloneTag);
					
			var _sigunguModBtn = $(_cloneTag).children().find(".sigunguModBtn");	
			var _sigunguCancelBtn = $(_cloneTag).children().find(".sigunguCancelBtn");
			var _sigunguSaveBtn = $(_cloneTag).children().find(".sigunguSaveBtn");
			var _sigunguDelBtn = $(_cloneTag).children().find(".sigunguDelBtn");
			var _sigunguText = $(_cloneTag).children().find(".sigunguText");
			var _sigunguName = $(_cloneTag).children().find("input[name=sigungu_name]");

			//	1 수정, 2 취소, 3저장, 4삭제, 5보여지는 값, 6 수정하려는 값
			_addSigunguEvt(_sigunguModBtn[0], 1);
			_addSigunguEvt(_sigunguCancelBtn[0], 2);
			_addSigunguEvt(_sigunguSaveBtn[0], 3);
			_addSigunguEvt(_sigunguDelBtn[0], 4);
			_addSigunguEvt(_sigunguText[0], 5);
			_addSigunguEvt(_sigunguName[0],6);
			
		}else if(type == 3){
			var _cloneTag = $("#dongCloneDiv").children('div').clone()
			_cloneTag.children().find('.dongText').val(obj.dong_name);
			_cloneTag.children().find("input[name=dong_name]").val(obj.dong_name);
			_cloneTag.children().find("input[name=dong_seq]").val(obj.dong_seq);
			_cloneTag.children().find("input[name=new_dong_name]").hide();
			$(target).append(_cloneTag);
			
			var _dongModBtn = $(_cloneTag).children().find(".dongModBtn");	
			var _dongCancelBtn = $(_cloneTag).children().find(".dongCancelBtn");
			var _dongSaveBtn = $(_cloneTag).children().find(".dongSaveBtn");
			var _dongDelBtn = $(_cloneTag).children().find(".dongDelBtn");
			var _dongText = $(_cloneTag).children().find(".dongText");
			var _dongName = $(_cloneTag).children().find("input[name=dong_name]");
			
			//	1 수정, 2 취소, 3저장, 4삭제, 5보여지는 값, 6 수정하려는 값
			_addDongEvt(_dongModBtn[0], 1);
			_addDongEvt(_dongCancelBtn[0], 2);
			_addDongEvt(_dongSaveBtn[0], 3);
			_addDongEvt(_dongDelBtn[0], 4);
			_addDongEvt(_dongText[0], 5);
			_addDongEvt(_dongName[0], 6);
				
		}
	}
	
	//	전체 시군구동 데이터 셋팅
	function _dataSetCall(res, type){
		if(res.result == false || res.result == 'false'){
			$("#sigunguTarget").children().remove();
			$("#dongTarget").children().remove();;
			_targetClear(1);
			return alert("데이터를 가져올 수가 없습니다 관리자에게 문의 바랍니다.");
		}
		
		if(type==2){
			var _target = $("#sigunguTarget"); 
			_target.children().remove();
			var _list = res.sigunguList; 
			$("#target_sido_seq").val(res.sido_seq);
			_targetClear(3);	
			
			if(_list.length == 0){
				_target.append(_notdataClone());
				//	군, 구 지역이 없을 경우 지역(동) 데이터 없음을 알림
				$("#dongTarget").children().remove();
				$("#dongTarget").append(_notdataClone());
				
			}else{
				for(var i=0; i<_list.length; i++){
					var _obj = {};
					_obj.sigungu_name = _list[i].sigungu_name;
					_obj.sigungu_seq = _list[i].sigungu_seq;
					_createForm(_target, _obj, 2);	
				}
				//	시도 셋팅 후 -> 군구 셋팅 후 -> 동 셋팅
				$(".sigunguText")[0].click();		
				_btnShow(2);	
			}
				
		}else if(type == 3){
			var _target = $("#dongTarget");
			_target.children().remove();

			$("#target_sigungu_seq").val(res.sigungu_seq);
			var _list = res.dongList; 

			if(_list.length == 0){
				_target.append(_notdataClone());
				
			}else{
				for(var i=0; i<_list.length; i++){
					var _obj = {};
					_obj.dong_name = _list[i].dong_name;
					_obj.dong_seq = _list[i].dong_seq;
					_createForm(_target, _obj, 3);	
				}
			}
			_btnShow(2);
		}
	}
	
	function _notdataClone(){
		return $("#nodataCloneDiv").children().clone();
	}
	
	function _targetClear(type){
		if(type == 1){
			$("#target_sido_seq").val("");
			$("#target_sigungu_seq").val("");
		}else if(type == 2){
			$("#target_sido_seq").val("");
		}else if(type == 3){
			$("#target_sigungu_seq").val("");
		}
	}
	
	function _sidoAddClone(){
		return $("#sidoAddClone").children().clone();
	}
	
	
	function _btnShow(type){
		if(type==1){	//	시도 데이터 있는경우
			$("#sigunguAddBtn").show();
		}else if(type == 2){// 시군구 데이터 있는 경우
			$("#dongAddBtn").show();
		}
	}
	
	function _btnHide(type){
		if(type == 1){
			$("#sigunguAddBtn").hide();
			$("#dongAddBtn").hide();
		}else if(type == 2){
			$("#dongAddBtn").hide();
		}
		
	}
	
	function _clear(type){
		if(type == 1){
			$("#sigunguTarget").children().remove();
			$("#dongTarget").children().remove();
		}else if(type == 2){
			$("#dongTarget").children().remove();
		}
	}
	
	function _removeSidoActive(){
		$("input[name=sido_name]").removeClass("area-active");
		$(".sidoText").removeClass("area-active");
		$('.addSidoText').removeClass('area-active');
	}
	
	function _removeSigunguActive(){
		$("input[name=sigungu_name]").removeClass("area-active");
		$(".sigunguText").removeClass("area-active");
		$('.addSigunguText').removeClass('area-active');		
	}
	
	function _removeDongActive(){
		$("input[name=dong_name]").removeClass("area-active");
		$(".dongText").removeClass("area-active");
		$('.addDongText').removeClass('area-active');
	}
	
	function _addAreaCall(res, type, target){
		if(res.result == false || res.result == 'false'){
			return alert("저장하는데 에러가 발생하였습니다. 관리자에게 문의 바랍니다.");
		}
		
		if(type==1){	//	시도
			$(target).parents('form').find("div.evtBtnBox").find('.sidoAddBtn').hide();
			$(target).parents('form').find("div.evtBtnBox").find('.sidoAddCancelBtn').hide();

			var _sidoModBtn = $(target).parents('form').find("div.evtBtnBox").find('.sidoModBtn');			//	수정 버튼
			var _sidoCancelBtn = $(target).parents('form').find("div.evtBtnBox").find('.sidoCancelBtn');	//	취소 버튼
			var _sidoDelBtn = $(target).parents('form').find("div.evtBtnBox").find('.sidoDelBtn');			//	삭제 버튼
			var _sidoSaveBtn = $(target).parents('form').find("div.evtBtnBox").find('.sidoSaveBtn');		//	저장 버튼
			var _sidoText = $(target).parents('form').find("div.evtTextBox").find('.sidoText');				//	시도 텍스트
			var _addSidoText = $(target).parents('form').find("div.evtTextBox").find('input[name=new_sido_name]');	//	추가저장할때 쓴 시도 명
					
			_addSidoText.hide();
			_sidoText.val(_addSidoText.val());
			_sidoText.show();
			
			var _sidoName = $(target).parents('form').find("div.evtTextBox").find('input[name=sido_name]'); // 수정할 때 쓰는 시도
			_sidoName.val(_addSidoText.val());	
			
			$(target).parents('form').find("div.evtTextBox").find('input[name=sido_seq]').val(res.sido_seq);
			
			_sidoModBtn.show();
			_sidoDelBtn.show();
			
			_addSidoEvt(_sidoModBtn[0], 1);
			_addSidoEvt(_sidoCancelBtn[0], 2);
			_addSidoEvt(_sidoSaveBtn[0], 3);
			_addSidoEvt(_sidoDelBtn[0], 4);
			_addSidoEvt(_sidoText[0],5);
			_addSidoEvt(_sidoName[0],6);
			
			_sidoText[0].click();
		}else if(type == 2){	//	군구
			$(target).parents('form').find("div.evtBtnBox").find('.sigunguAddBtn').hide();
			$(target).parents('form').find("div.evtBtnBox").find('.sigunguAddCancelBtn').hide();
			
			var _sigunguModBtn = $(target).parents('form').find("div.evtBtnBox").find('.sigunguModBtn');			//	수정 버튼
			var _sigunguCancelBtn = $(target).parents('form').find("div.evtBtnBox").find('.sigunguCancelBtn');		//	취소 버튼
			var _sigunguDelBtn = $(target).parents('form').find("div.evtBtnBox").find('.sigunguDelBtn');			//	삭제 버튼
			var _sigunguSaveBtn = $(target).parents('form').find("div.evtBtnBox").find('.sigunguSaveBtn');			//	저장 버튼
			var _sigunguText = $(target).parents('form').find("div.evtTextBox").find('.sigunguText');				//	시도 텍스트
			var _sigunguName = $(target).parents('form').find("div.evtTextBox").find('input[name=sigungu_name]');
			var _addSigunguText = $(target).parents('form').find("div.evtTextBox").find('input[name=new_sigungu_name]');//	추가저장할때 쓴 시도 명
					
			_addSigunguText.hide();
			_sigunguText.val(_addSigunguText.val());
			_sigunguText.show();
			_sigunguName.val(_addSigunguText.val());	// 수정할 때 쓰는 시도
			
			$(target).parents('form').find("div.evtTextBox").find('input[name=sigungu_seq]').val(res.sigungu_seq);
			
			_sigunguModBtn.show();
			_sigunguDelBtn.show();
			
			_addSigunguEvt(_sigunguModBtn[0], 1);
			_addSigunguEvt(_sigunguCancelBtn[0], 2);
			_addSigunguEvt(_sigunguSaveBtn[0], 3);
			_addSigunguEvt(_sigunguDelBtn[0], 4);
			_addSigunguEvt(_sigunguText[0],5);
			_addSigunguEvt(_sigunguName[0],6);
			
			_sigunguText[0].click();
		}else if(type == 3){	//	지역
			$(target).parents('form').find("div.evtBtnBox").find('.dongAddBtn').hide();
			$(target).parents('form').find("div.evtBtnBox").find('.dongAddCancelBtn').hide();
			
			var _dongModBtn = $(target).parents('form').find("div.evtBtnBox").find('.dongModBtn');			//	수정 버튼
			var _dongCancelBtn = $(target).parents('form').find("div.evtBtnBox").find('.dongCancelBtn');	//	취소 버튼
			var _dongDelBtn = $(target).parents('form').find("div.evtBtnBox").find('.dongDelBtn');			//	삭제 버튼
			var _dongSaveBtn = $(target).parents('form').find("div.evtBtnBox").find('.dongSaveBtn');		//	저장 버튼
			var _dongText = $(target).parents('form').find("div.evtTextBox").find('.dongText');				//	시도 텍스트
			var _dongName = $(target).parents('form').find("div.evtTextBox").find('input[name=dong_name]');
			var _addDongText = $(target).parents('form').find("div.evtTextBox").find('input[name=new_dong_name]');	//	추가저장할때 쓴 시도 명
					
			_addDongText.hide();
			_dongText.val(_addDongText.val());
			_dongText.show();
			_dongName.val(_addDongText.val());	// 수정할 때 쓰는 시도
			
			$(target).parents('form').find("div.evtTextBox").find('input[name=dong_seq]').val(res.dong_seq);
			_dongModBtn.show();
			_dongDelBtn.show();
			
			_addDongEvt(_dongModBtn[0], 1);
			_addDongEvt(_dongCancelBtn[0], 2);
			_addDongEvt(_dongSaveBtn[0], 3);
			_addDongEvt(_dongDelBtn[0], 4);
			_addDongEvt(_dongText[0],5);
			_addDongEvt(_dongName[0],6);
		}
	}
	
	//	시도 추가 버튼
	$("#sidoAddBtn").on("click", function(){
		_btnHide(1);
		_clear(1);
		_targetClear(1);
		_removeSidoActive();
		
		$("#sigunguTarget").append(_sidoAddClone());
		$("#dongTarget").append(_sidoAddClone());
		
		if($("#sidoTarget").children().find("input[name=sido_seq]").length == 0){
			
			$("#sidoTarget").children().remove();
		}
		
		var _cloneDiv = $("#sidoCloneDiv").children().clone();
		var _sidoAddBtn = _cloneDiv.find('.sidoAddBtn');
		$(_sidoAddBtn).show();
		$(_sidoAddBtn).on("click", function(){
			if(confirm("저장 하시겠습니까?")){
				var _target = $(this);
				
				var _sido_name = $(_target).parents('form').find('input[name=new_sido_name]').val();
				if(_sido_name.trim() == ""){
					alert("시,도 명을 작성해주시기 바랍니다");
					return;
				}
				
				var _url = "/admin/mgt/build/sidoWriteProc";
				var _obj = {};
				_obj.sido_name = _sido_name;
				var _type = 1;				
				_doAjax(_url, _obj, _addAreaCall, _type, _target);
				
			}
		});
		
		var _sidoAddCancelBtn = _cloneDiv.find('.sidoAddCancelBtn');
		$(_sidoAddCancelBtn).show();
		$(_sidoAddCancelBtn).on("click", function(){
			if(confirm("작성한 데이터는 저장 되지 않습니다 삭제하시겠습니까?")){
				$(this).parents('div.padding-bottom-5px').remove();
			}
			
			if($("#sidoTarget").children().length == 0){
				$("#sidoTarget").append(_notdataClone());
			}
		});
		
		_cloneDiv.find('.sidoModBtn').hide();
		_cloneDiv.find('.sidoDelBtn').hide();
	 	_cloneDiv.find('.sidoText').hide();
		
		var _addSidoText = _cloneDiv.find(".addSidoText");
		
		$(_addSidoText).on("click", function(){
		
			_btnHide(1);
			_clear(1);
			_targetClear(1);
			
			_removeSidoActive();
			$("#sigunguTarget").append(_sidoAddClone());
			$("#dongTarget").append(_sidoAddClone());
			
			$(this).addClass('area-active');
			
		});
		
		$("#sidoTarget").append(_cloneDiv)
		$(_addSidoText).focus();
		
	});
	
	//	시군구 추가 버튼
	$("#sigunguAddBtn").on("click", function(){
		
		_btnHide(2);
		_clear(2);
		_targetClear(3);
		
		if($("#sigunguTarget").children('.not-data-class').length > 0){
			$("#sigunguTarget").children().remove();
		}

		_removeSigunguActive();
		
		$("#dongTarget").append(_sidoAddClone());
		
		var _cloneDiv = $("#sigunguCloneDiv").children().clone();
		var _sigunguAddBtn = _cloneDiv.find('.sigunguAddBtn');
		$(_sigunguAddBtn).show();
		$(_sigunguAddBtn).on("click", function(){
			if(confirm("저장 하시겠습니까?")){
				
				var _target = $(this);
				var _sigungu_name = $(_target).parents('form').find('input[name=new_sigungu_name]').val();
				
				if(_sigungu_name.trim() == ""){
					return alert("군,구 명을 작성해주시기 바랍니다");
				}
				
				
				var _url = "/admin/mgt/build/sigunguWriteProc";
				var _obj = {};
				_obj.sigungu_name = _sigungu_name;
				_obj.sido_seq = $("#target_sido_seq").val();
				var _type = 2;				
				
				
				_doAjax(_url, _obj, _addAreaCall, _type, _target);
				
			}
		});
		
		var _sigunguAddCancelBtn = _cloneDiv.find('.sigunguAddCancelBtn');
		$(_sigunguAddCancelBtn).show();
		
		$(_sigunguAddCancelBtn).on("click", function(){
			if(confirm("작성한 데이터는 저장 되지 않습니다 삭제하시겠습니까?")){
				$(this).parents('div.padding-bottom-5px').remove();
			}
			if($("#sigunguTarget").children().length == 0){
				$("#sigunguTarget").append(_notdataClone());
			}
		});
		
		_cloneDiv.find('.sigunguModBtn').hide();
		_cloneDiv.find('.sigunguDelBtn').hide();
	 	_cloneDiv.find('.sigunguText').hide();
		
		var _addSigunguText = _cloneDiv.find(".addSigunguText");
		
		$(_addSigunguText).on("click", function(){
			_btnHide(2);
			_clear(2);
			_targetClear(3);
			_removeSigunguActive();
			
			$("#dongTarget").append(_sidoAddClone());
			
			$(this).addClass('area-active');
		});
		
		$("#sigunguTarget").append(_cloneDiv)
		$(_addSigunguText).focus();
	});
	
	//	동 추가 버튼
	$("#dongAddBtn").on("click", function(){
				
		if($("#dongTarget").children('.not-data-class').length > 0){
			$("#dongTarget").children().remove();
		}
		_removeDongActive();
		
		var _cloneDiv = $("#dongCloneDiv").children().clone();
		var _dongAddBtn = _cloneDiv.find('.dongAddBtn');
		
		$(_dongAddBtn).show();
		$(_dongAddBtn).on("click", function(){
			if(confirm("저장 하시겠습니까?")){
				
				var _target = $(this);
				var _dong_name = $(_target).parents('form').find('input[name=new_dong_name]').val();
				
				if(_dong_name.trim() == ""){
					return alert("지역 명을 작성해주시기 바랍니다");
				}
				
				var _url = "/admin/mgt/build/dongWriteProc";
				var _obj = {};
				_obj.dong_name = _dong_name;
				_obj.sigungu_seq = $("#target_sigungu_seq").val();
				var _type = 3;				
				
				_doAjax(_url, _obj, _addAreaCall, _type, _target);
				
			}
		});
		var _dongAddCancelBtn = _cloneDiv.find('.dongAddCancelBtn');

		$(_dongAddCancelBtn).show();

		$(_dongAddCancelBtn).on("click", function(){
			if(confirm("작성한 데이터는 저장 되지 않습니다 삭제하시겠습니까?")){
				$(this).parents('div.padding-bottom-5px').remove();
			}
			if($("#dongTarget").children().length == 0){
				$("#dongTarget").append(_notdataClone());
			}
		});
		
		_cloneDiv.find('.dongModBtn').hide();
		_cloneDiv.find('.dongDelBtn').hide();
	 	_cloneDiv.find('.dongText').hide();
		
		var _addDongText = _cloneDiv.find(".addDongText");
		
		$(_addDongText).on("click", function(){
			_removeDongActive();
			$(this).addClass('area-active');
		});
		
		$("#dongTarget").append(_cloneDiv)
		$(_addDongText).focus();
	});
		
	new codeAppInit();
});