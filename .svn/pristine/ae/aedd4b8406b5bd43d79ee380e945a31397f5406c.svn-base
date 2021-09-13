$(function(){
	//	대분류 파일 이미지 업로드 버튼
	var _lcateFile = $(".lcate-file")
	
	for(var i=0; i<_lcateFile.length; i++){	
		$(_lcateFile[i]).on("change", function(e){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				$(_target).val("")
				
				_deleteImageBox(_target, 3);	
				_prevDisabled(_target, 3, true);	
				return alert('이미지만 업로드 할수 있습니다');
			}
			_doImageAjax(_target, 3, _makeData);
			
		});	
	}
	
	function _makeData(res, target, type){
		if(res.result == false || res.result == 'false'){
			alert("파일 업로드에 문제가 생겼습니다");
			return;
		}
		
		var _clone = $("#fileClone").children().clone();
		$(_clone).find("input[name=file_type]").val(type);		
		$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
		$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
		$(_clone).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
		$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
		$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
		$(_clone).find(".file_image").attr('src', res.files[0].file_dtl_url_path);
		
		if(type == 3){	//	대분류
			_deleteImageBox(target, type);	//	이미지 삭제
			$(target).parents("div.targetDiv").find("div.lcateImgDiv").append(_clone);
			//	확인 버튼 활성화
			_prevDisabled(target, type, false);
			//	이벤트 추가
			_preViewEvt(target, type);
					
		}else{			//	층
			_deleteImageBox(target, type);
			$(target).parents('.floorEvt').children().find('.imgViewBox').append(_clone);
			_prevDisabled(target, type, false);
			_preViewEvt(target, type);
		}
	}
	
	//	이미지 서버에 저장
	function _doImageAjax(target, type , callFunction){
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
		_formData.append("files", _file);
		koreasoft.modules.file.uploadPost(null, _formData, callFunction, target, type);
	}
	//	이미지 파일 삭제 시퀀스
	function _addDelFileSeq(target, type){
		var _t = null;
		if(type == 3){
			_t = $(target).parents(".targetDiv").find(".lcateImgDiv").find("input[name=file_seq]");
			
		}else{
			_t = $(target).parents('.floorEvt').find('.imgViewBox').find("input[name=file_seq]");
			
		}
		
		if(_t.length != 0){
			if($(_t).val() != ""){
				var _clone = $(_t).clone();
				$("#delFileSeq").append(_clone);		
			}
		}
		
	} 
	
	
	//	이미지 삭제
	function _deleteImageBox(target, type ){
		
		if(type == 3){
			_addDelFileSeq(target, type);
			
			$(target).parents('div.targetDiv').find("div.lcateImgDiv").children().remove();
		}else{
			_addDelFileSeq(target, type);
			
			$(target).parents('.floorEvt').find('.imgViewBox').children().remove();
		}
	} 
	
	//	확인 버튼 disabled on/off
	function _prevDisabled(target, type, bool){
		if(type==3){
			$(target).parents('div.targetDiv').find(".lcate-file-prev").attr("disabled", bool);
		}else{
			//debugger;
			$(target).parents('.floorEvt').find('.prevBtn').attr('disabled', bool);
		}
	}
	
	//	미리보기 이벤트
	function _preViewEvt(target, type){
		var _img = null;
		
		if(type == 3){
			var _prev = $(target).parents(".targetDiv").find(".lcate-file-prev")[0];
			$(_prev).off("click");
			
			$(_prev).on("click", function(){
				_img = $(this).parents(".targetDiv").find(".lcateImgDiv").find(".file_image").attr('src');
				_doPop(_img);
			});
		}else{
			var _prev = $(target).parents('.floorEvt').children().find('.prevBtn');
			$(_prev).off("click");
			
			$(_prev).on("click", function(e){
				var _img = $(this).parents(".floorEvt").children().find(".imgViewBox").find(".file_image").attr("src");
				_doPop(_img);
			});
		}
	}
	
	//	목록
	$("#listBtn").on("click", function(){
		$("#buildListForm").submit();
	});
	
	//	저장
	$("#saveBtn").on("click", function(){
		if(confirm("저장하시겠습니까?")){
			//debugger;
			var _obj = {}
			_obj.lcate_list   = [];
			_obj.floor_list   = [];
			_obj.del_file_seq = [];
			_obj.dtlType = $("#dtlType").val();
			_obj.build_seq = $("input[name=build_seq]").val();
			
			var _lcateCnt = 0;
			var _lcateName = $("input[name=lcate_name]");	//	대분류 카테고리
			var _lcateFile = $(".targetDiv").children().find(".lcateImgDiv");	//	대분류 이미지
			var _floorEvt = $("#floorTable").find(".floorEvt");
			
			for(var i=0; i<_lcateName.length; i++){
				if($(_lcateName[i]).val().trim() == ""){
					_lcateCnt ++;
				}
			}
			
			if(_lcateCnt == 3){
				alert("업종 대분류를 입력해주세요");
				$(_lcateName[0]).focus();
				return;
			}
			
			for(var i=0; i<_lcateName.length; i++){
				
				var _lcateObj = {};
				_lcateObj.file = {};			//	대분류 이미지 객체
				_lcateObj.mcate_list = [];		//	소분류 리스트
				_lcateObj.lcate_name = $(_lcateName[i]).val();
				_lcateObj.file = _getFile(_lcateFile[i]);
				
				var _mcateName =  i == 0? "mcate_a" : i == 1? "mcate_b" : "mcate_c";
				var _target = $("input[name="+_mcateName+"]");
				
				for(var j=0; j<_target.length;j++){
					
					if($(_target[j]).val().trim()==""){
						continue;
					}
					
					var _mcateObj = {};
					_mcateObj.mcate_name = $(_target[j]).val();
					
					_lcateObj.mcate_list.push(_mcateObj);
						
				}
				_obj.lcate_list.push(_lcateObj);
			}
			
			for(var i=0; i<_floorEvt.length; i++){
				var _target = $(_floorEvt[i]);
				var _floorObj = {};
				_floorObj.floor_file =  _getFile(_target);
				_floorObj.floor_type = $(_target).find("input[name=floor_type]").prop("checked") == true ? 1: 2;//	1지상, 2지하
				_floorObj.floor_seq = $(_target).find("input[name=floor_seq]").val();
				
				
				if(_floorObj.floor_seq == ""){
					continue;
				}
				
				_obj.floor_list.push(_floorObj);
			}
			
			if($("#delFileSeq").children().length != 0){
				
				var _delFile = $("#delFileSeq").children("input[name=file_seq]");
				for(var i=0; i<_delFile.length; i++){
					var _v = $(_delFile[i]).val();
					if(_v == ""){
						continue;
					}
					_obj.del_file_seq.push(_v);
				}
			}
			
			_doAjax("/admin/mgt/build/dtlWriteProc", _obj, _writeProc)
			
		}
	});
	
	function _writeProc(res){
		
		if(res.result == false || res.result == 'false'){
			return alert("오류가 생겼습니다. 관리자에게 문의 바랍니다.");
		}else{
			alert("저장에 성공하였습니다")
			window.location.reload();
		}
		
	}
	
	function _doAjax(url, data, callback){
		$.ajax({
			type: "POST",
			url: url,
			data : JSON.stringify(data),
           	contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				
				callback(res);
			},
			error: function (e) {
				console.log("error : " + e.toString())
			}
		});
	}
	
	function _getFile(target){
		var _res = {};
		_res.fileObj = {};
		_res.hasFile = false;
		
		var _filePath = $(target).find("input[name=file_dtl_url_path]").val();
		
		if(  ! (_filePath == undefined) ){
			var _fileObj = {};
			_fileObj.file_dtl_desc	= $(target).find("input[name=file_dtl_desc]").val();
			_fileObj.file_dtl_ext	= $(target).find("input[name=file_dtl_ext]").val();
			_fileObj.file_dtl_origin	= $(target).find("input[name=file_dtl_origin]").val();
			_fileObj.file_dtl_path	= $(target).find("input[name=file_dtl_path]").val();
			_fileObj.file_dtl_url_path	= _filePath;
			_fileObj.file_type 		= $(target).find("input[name=file_type]").val();
			_fileObj.file_seq		= $(target).find("input[name=file_seq]").val();
			
			_res.fileObj = _fileObj;
			_res.hasFile = true	;
		}
		
		return _res;
	}
	
	//	층 추가
	$("#floorAddBtn").on("click", function(e){
		e.preventDefault();
		var _floorClone = $("#floorClone").children().find("tr.floorEvt").clone();
		var _floorFile = $(_floorClone).find("input[name=floor_file]");
		var _floorType = $(_floorClone).find("input[name=floor_type]");
				
		$(_floorFile).on("change", function(e){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			
			if(!_isImg){
				$(_target).val("")
				_deleteImageBox(_target, 4);	
				_prevDisabled(_target, 4, true);
				return alert('이미지만 업로드 할수 있습니다');
			}
			
			_doImageAjax(_target, 4, _makeData)
								
		});
		
		//	체크박스 이벤트
		$(_floorType).on("change", function(e){
			var _target = $(this).parents(".floorEvt").find(".floor_text");
			var _v = $(this).prop("checked");
			if(_v){
				$(_target).text("지상")
			}else{
				$(_target).text("지하")
			}
		})
				
		$("#floorTable").append(_floorClone);
		
	});
		
	//	층 삭제
	$("#flooarDelBtn").on("click", function(){
		var _checkArr = $("#floorTable").children().find("input[name=floor_check]");
		for(var i=0; i<_checkArr.length; i++){
			var _target = _checkArr[i];
			if($(_target).prop("checked")== true){
				var _t = $(_target).parents('.floorEvt').find(".imgViewBox").children().find("input[name=file_seq]");
				
				if(_t.length != 0){
					if($(_t).val() !=""){
						$("#delFileSeq").append($(_t).clone());		
					}
				}
				$(_target).parents("tr.floorEvt").remove();
			}	
		}
	});
		
	//	미리보기 : 팝업으로 이미지 보기 
	function _doPop(target){	
		var _iframe = "<iframe width='100%;' height='100%;' src='" + target +"'</iframe>";
		var _x = window.open();
		_x.document.open();
		_x.document.write(_iframe);
		_x.document.close();
	};
		
	//	업종 세부분류 추가 버튼
	$(".mCateAddBtn").on("click", function(e){
		var _placeholder = "소분류lcate-mcate 입력";//	플레이스홀더
		var _mcateType = $(this).attr('data-cate');	//	a=1, b=2, c=3
		var _mcateLen = $(this).parents('.card-body').children('.mcateEvt').length;
		_placeholder = _placeholder.replace("lcate", _mcateType);
		_placeholder = _placeholder.replace("mcate", (_mcateLen+1));
		
		if(_mcateLen == 10){
			alert("소분류는 10개까지 추가 가능합니다");
			return;
		}
		
		var _name = _mcateType == 1? "mcate_a" : _mcateType == 2? "mcate_b" : "mcate_c";
		var _cloneTag = $("#mcateClone").children(".mcateEvt").clone();
		var _inputText = $(_cloneTag).children().find('input[type=text]');
		$(_inputText).attr('placeholder', _placeholder).attr('name', _name);	//플레이스홀더
		
		var _delBtn = $(_cloneTag).children().find('.mCateDelBtn');
		$(_delBtn).attr("data-cate", _mcateType);
		$(_delBtn).attr("data-name", _name);
		
		$(_delBtn).on("click", function(){
			$(this).parents(".mcateEvt").remove();
			var _mtype = $(this).attr("data-cate");
			var _mcate = $(this).attr("data-name");
			var _input = $("input[name="+_mcate+"]");
			
			for(var i=0; i<_input.length; i++){
				if(i==0){
					continue;
				}
				$(_input[i]).attr("placeholder", "소분류"+_mtype+"-"+(i+1)+" 입력" );		
			}
		});
		
		$(this).parents('.card-body').append(_cloneTag);
		$(_inputText).focus();
	});
	
	
	////
	var _dtlType = $("#dtlType").val();
	if(_dtlType == 2 || _dtlType == '2'){
		//	이미지 업로드의 확인 버튼 > 대분류 이벤트 생성
		var _len = $(".lcateImgDiv").find("input[name=file_seq]").length;
		var _prevBtn = $(".lcate-file-prev");
		
		for(var i=0; i<_len; i++){
			_prevDisabled(_prevBtn[i],3, false);
			_preViewEvt(_prevBtn[i],3);	
		}
		
		//	이미지 도면 층 이벤트
		var _floorLen = $("#floorTable").find(".imgViewBox").find("input[name=file_seq]");
		var _floorPreBtn = $("#floorTable").children().find('.prevBtn');
		for(var i=0; i<_floorLen.length; i++){
			_prevDisabled(_floorPreBtn[i], 4, false);
			_preViewEvt(_floorPreBtn[i],4);
		}
		
		//	 이미지 업로드 버튼
		var _floorImgUpload = $("#floorTable").children().find("input[name=floor_file]");
		$(_floorImgUpload).on("change", function(){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			
			if(!_isImg){
				$(_target).val("")
				_deleteImageBox(_target, 4);	
				_prevDisabled(_target, 4, true);
				return alert('이미지만 업로드 할수 있습니다');
			}
			
			_doImageAjax(_target, 4, _makeData)	
		});
		
		var _mcateDelBtn = $(".card-body").children('.mcateEvt').find(".mCateDelBtn");
		for(var i=0; i<_mcateDelBtn.length; i++){
			$(_mcateDelBtn[i]).on("click", function(){
				
				$(this).parents(".card-body").find('.mcateEvt');
				var _addBtn = $(this).parents(".card-body").find('.mcateEvt').find('.mCateAddBtn')[0];
				var _dataCate = $(_addBtn).attr('data-cate');
				var _name = _dataCate == 1? "mcate_a" : _dataCate == 2? "mcate_b" : "mcate_c";
				
				$(this).parents(".mcateEvt").remove();
				var _input = $("input[name="+_name+"]")
				
				for(var i=0; i<_input.length; i++){
					if(i==0){
						continue;
					}
					$(_input[i]).attr("placeholder", "소분류"+_dataCate+"-"+(i+1)+" 입력" );		
				}
			});
		}
		
		
		//	체크박스 이벤트
		var _floorCheck = $("#floorTable").children().find("input[name=floor_type]");
		$(_floorCheck).on("change", function(e){
			var _target = $(this).parents(".floorEvt").find(".floor_text");
			var _v = $(this).prop("checked");
			if(_v){
				$(_target).text("지상")
			}else{
				$(_target).text("지하")
			}	
		})
	
	}else{
		
		//	층입력칸 생성
		var _floorLen = $("#floorTable").children().find('tr.floorEvt').length;
		if(_floorLen == 0){
			for(var i=0; i<3; i++){
				$("#floorAddBtn").click();
			}
		}	
	}
	
	
	
	
});