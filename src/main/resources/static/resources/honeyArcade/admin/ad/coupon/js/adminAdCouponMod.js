$(function(){
	//	데이트피커
	flatpickr(document.getElementById('basicFlatpickr'), {
		 locale: "ko"
	});
	
	flatpickr(document.getElementById('basicFlatpickr1'), {
		 locale: "ko"
	});
	
	////	파일 설정
	//	쿠폰
	var _bannerImg = $("input[name=banner_img]");
	
	var _fileUpload = function(target){
		$(target).on("change", function(e){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				$(_target).val("")
				
				_deleteImageBox();	
				_prevDisabled(_target, true);	
				return alert('이미지만 업로드 할수 있습니다');
			}
			_doImageAjax(_target, _makeData);
		})
	}		
	
	var _makeData = function(res, target){
		if(res.result == false || res.result == 'false'){
			alert("파일 업로드에 문제가 생겼습니다");
			return;
		}
		
		_deleteImageBox();
						
		var _clone = $("#fileClone").children().clone();
		$(_clone).find("input[name=file_type]").val(7);		
		$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
		$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
		$(_clone).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
		$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
		$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
		$(_clone).find(".file_image").attr('src', res.files[0].file_dtl_url_path);
		
		
		$("#topBanner").append(_clone);
				
		//	버튼 활성화
		_prevDisabled(target, false);
		//	이벤트 추가
		_preViewEvt(target);
		
		$(target).val("");
	}
	
	//	미리보기 이벤트
	function _preViewEvt(target){
		var _img = null;
		var _prev = $("#bannerImgPrev");
		$(_prev).off("click");
		$(_prev).on("click", function(){
			_img = $("#topBanner").children().find(".file_image").attr("src");
			var _image = new Image();
			_image.src = _img;
			_imageShow(_image);
		});
		
	}
	
	//	버튼 활성화
	var _prevDisabled = function(target, bool){
		$("#bannerImgPrev").attr("disabled", bool);
	}
	
	//	미리보기 이미지 컨테이너
	var _deleteImageBox = function(type){
		var _target = $("#topBanner");
		
		var _file_seq = $(_target).find("input[name=file_seq]").val();
		
		if(_file_seq != ""){
			
			var _input = document.createElement("input");
			_input.setAttribute("type", "hidden");
			_input.setAttribute("value", _file_seq);
			_input.setAttribute("name", "file_seq");
			$("#delFile").children("div").append(_input)
		}
		
		$(_target).children().remove();
	}
		
	function _doImageAjax(target, callFunction){
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
		if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
			alert("1메가 이하의 파일을 올려주세요");
			return;
		}		
		_formData.append("files", _file);
		koreasoft.modules.file.uploadPost(null, _formData, callFunction, target, null);
	}
	
	_fileUpload(_bannerImg);
	
	
	////	건물 설정 모달
	$("#buildAddBtn").on("click", function(){
		var _sidoSeq	= $("#popSidoSelect").val();
		var _sigunguSeq	= $("#popSigunguSelect").val();
		var _dongSeq	= $("#popDongSelect").val();
		var _buildSeq	= $("#popBuildSelect").val();
		var _buildName	= $("#popBuildSelect option:checked").text();
		
		if(_sidoSeq == ""){
			alert("시,도를 선택해주세요");
			return; 
		}
		
		if(_sigunguSeq == ""){
			alert("군,구를 선택해주세요");
			return; 
		}
		
		if(_dongSeq == ""){
			alert("지역을 선택해주세요");
			return; 
		}
		
		if(_buildSeq == ""){
			alert("건물을 선택해주세요");
			return;
		}
		
		if($("#buildAddbox").children().length != 0){
			var _buildBox = $("#buildAddbox").children();
			var _isHas = false;
			
			for(var i=0; i<_buildBox.length; i++){
				var _v = $(_buildBox[i]).find("input[name=build_seq]").val();
				
				if(_v == _buildSeq){
					_isHas = true;
					break;
				}
			}
			
			if(_isHas){
				alert(_buildName + "은 이미 선택된 건물입니다");
				return;
			}
		}
		
		
		var _clone = $("#badgeClone").children().clone();
		$(_clone).find("input[name=sido_seq]").val(_sidoSeq);
		$(_clone).find("input[name=sigungu_seq]").val(_sigunguSeq);
		$(_clone).find("input[name=dong_seq]").val(_dongSeq);
		$(_clone).find("input[name=build_seq]").val(_buildSeq);
		$(_clone).find(".build-text").text(_buildName);
		var _evtTarget = $(_clone).find(".banner-del-btn");
		_badgeDel(_evtTarget);
		$("#buildAddbox").append(_clone);
		
	});
	
	function _badgeDel(target){
		$(target).on("click", function(){
			$(target).parents(".build-box").remove();
		})
	}
	
	$("#popSaveBtn").on("click", function(){
		if($("#buildAddbox").children().length == 0){
			alert("선택된 건물이 없습니다");
		}else{
			var _span = $("#buildAddbox").children();
			var _build = $("#buildAd").children();
			
			for(var i=0; i<_span.length; i++){
				;
				var _s = $(_span[i]).find("input[name=build_seq]").val();
				var _has = false;	
				
				if(_build.length != 0){
					for(var j=0; j<_build.length; j++){
						var _a = $(_build[j]).find("input[name=build_seq]").val();
						if(_s == _a){
							
							_has = true;
							break;
						}
					}
				}
				
				if(!_has){
					$("#buildAd").append(_span[i]);	
				}
			}
		}
		
		$("#buildAddbox").children().remove();
		$('.modal').modal('hide');
	})
	
	$("#popCancelBtn").on("click", function(){
		$("#buildAddbox").children().remove();
	});
	
		
	/////	저장
	$("#saveBtn").on("click", function(){
		
		var _sidoSeq = $("#sidoSelectExt").val();				//	시도
		var _sigunguSeq = $("#sigunguSelectExt").val();			//	시군구
		var _dongSeq = $("#dongSelectExt").val();				//	지역
		var _bannerSort = $("#t-text").val();					//	노출 레벨
		var _bannerStartDay = $("#basicFlatpickr").val();		//	시작일
		var _bannerEndDay = $("#basicFlatpickr1").val();		//	종료일
		var _couponDtlText = $("input[name=coupon_dtl_text]");	//	쿠폰 상세 정보
				
		if(_sidoSeq == ""){
			alert("시,도를 선택해주세요");
			return;
		}
		
		if(_sigunguSeq == ""){
			alert("군,구를 선택해주세요");
			return;
		}
		
		if(_dongSeq == ""){
			alert("지역을 선택해주세요");
			return;
		}
		
		if($("#topBanner").children().length == 0){
			alert("쿠폰 배너 사진을 업로드 해주세요");
			return;
		}
			
		var _textCheck = 0;
		for(var i=0; i<_couponDtlText.length; i++){
			if($(_couponDtlText[i]).val().trim() == ""){
				_textCheck++;
			}
		}	
		
		if(_textCheck == 5){
			alert("쿠폰 내용을 입력해주시기 바랍니다");
			return;
		}
		
		if(_bannerStartDay == ""){
			alert("광고 시작일을 입력해주시기 바랍니다");
			return;
		}
		
		if(_bannerEndDay == ""){
			alert("광고 종료일을 입력해주시기 바랍니다");
			return;
		}
						
		if(_bannerStartDay>_bannerEndDay){
			alert("광고 시작일은 종료일보다 이전이어야 합니다");
			return;
		}
		
		if(_bannerSort == ""){
			alert("레벨설정을 해주시기 바랍니다");
			return;
		}
		
		if($("#buildAd").children().length == 0){
			alert("건물을 설정해주세요");
			return;
		}
		
		if(isNaN(_bannerSort)){
			alert("숫자만 입력해주세요");
			return;
		}
		
		if(_bannerSort.toString().length > 4){
			alert("최대 4자리수까지 등록할수 있습니다");
			return;
		}
		
		if(_bannerSort < 1){
			alert("0보다 큰수를 입력해주세요");
			return;
		}
			
		var _obj = {};
		_obj.sido_seq			= _sidoSeq;
		_obj.sigungu_seq		= _sigunguSeq;
		_obj.dong_seq			= _dongSeq;
		_obj.coupon_sort		= _bannerSort;
		_obj.coupon_start_day	= _bannerStartDay;
		_obj.coupon_end_day		= _bannerEndDay;
		_obj.file				= [];
		_obj.build				= [];
		_obj.coupon_seq			= $("#coupon_seq").val();
		_obj.owner_id			= $("#owner_id").val();
		_obj.coupon_dtl_txt		= [];
		_obj.del_file			= [];
		
		var _bannerImg = {};
		var _topBanner = $("#topBanner").find("div");
		_bannerImg.file_dtl_desc 	 = $(_topBanner).find("input[name=file_dtl_desc]").val();
		_bannerImg.file_dtl_ext 	 = $(_topBanner).find("input[name=file_dtl_ext]").val();
		_bannerImg.file_dtl_origin 	 = $(_topBanner).find("input[name=file_dtl_origin]").val();
		_bannerImg.file_dtl_path 	 = $(_topBanner).find("input[name=file_dtl_path]").val();
		_bannerImg.file_dtl_url_path = $(_topBanner).find("input[name=file_dtl_url_path]").val();
		_bannerImg.file_type 		 = $(_topBanner).find("input[name=file_type]").val();
		_bannerImg.file_seq 		 = $(_topBanner).find("input[name=file_seq]").val();
		
		_obj.file.push(_bannerImg);
		
		var _build = $("#buildAd").children();
		for(var i=0; i<_build.length; i++){
			_obj.build.push($(_build[i]).find("input[name=build_seq]").val());
		}
		
		for(var i=0; i<_couponDtlText.length; i++){
			var _text = $(_couponDtlText[i]).val();
			if(_text.trim() != ""){
				_obj.coupon_dtl_txt.push(_text);
			}
		}
		
		var _delFile = $("#delFile").children();
		for(var i=0; i<_delFile.length; i++){
			_obj.del_file.push($(_delFile[0]).find("input[name=file_seq]").val());
		}
		
		
		$.ajax({
			type: "POST",
			url: "/admin/ad/coupon/modProc",
			data: JSON.stringify(_obj),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				$("#l-wrapper").addClass("on");
			},
			success: function (res) {
				if(res.result == false || res.result == 'false'){
					return alert("저장에 실패하였습니다. 관리자에게 문의 바랍니다.");
				}else{
					alert("저장에 성공하였습니다.");
					
					window.location.reload();
				}
			},
			error: function (e) {
				alert("저장에 실패하였습니다. 관리자에게 문의 바랍니다.");
				console.log("error : " + e.toString())
			},
			complete : function(e){
				$("#l-wrapper").removeClass("on");
			}	
		});
		
		
	});
	
	////	목록
	$("#listBtn").on("click", function(){
		$("#listForm").submit();
	})
	
	////	수정
	_prevDisabled(null, false);
	_preViewEvt(null);
	var _delBtn = $("#buildAd").children(".build-box").find(".banner-del-btn");
	for(var i=0; i<_delBtn.length; i++){
		_badgeDel(_delBtn[i]);
	}		
	
	var _textLen = 5 - $("input[name=coupon_dtl_text]").length == 0 ? 0 : 5-$("input[name=coupon_dtl_text]").length;
	
	for(var i=0; i<_textLen; i++){
		
		var _input = document.createElement("input");
		_input.setAttribute("type", "text");
		_input.setAttribute("name", "coupon_dtl_text");
		_input.setAttribute("class", "form-control");
		$("#targetText").append(_input);
	}
	
	var viewer = null;
	var _imageShow = function(image){
		if(viewer != null){
			viewer.destroy();
		}
		 viewer= new Viewer(image,
				{  
					hidden: function () { 
							viewer.destroy(); 
				}
				, toolbar: { 
							zoomIn: 4, 
							zoomOut: 4, 
							oneToOne: 4, 
							reset: 4, 
							prev: 0, 
						    play: { 
						    	show: 4, 
						    	size: 'large' 
						    }, 
						    next: 0, 
						    rotateLeft: 4, 
						    rotateRight: 4, 
						    flipHorizontal: 4, 
						    flipVertical: 4, 
						}, 
						navbar : 0 
				} 
			); 
				
			
		viewer.show();
	}

})