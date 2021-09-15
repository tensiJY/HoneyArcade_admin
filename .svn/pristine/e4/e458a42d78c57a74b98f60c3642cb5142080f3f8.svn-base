$(function(){
	//	데이트피커
	var f1 = flatpickr(document.getElementById('basicFlatpickr'), {
		minDate : "today"
	});
	var f2 = flatpickr(document.getElementById('basicFlatpickr1'), {
		minDate : "today"
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
	}
	
	//	미리보기 이벤트
	function _preViewEvt(target){
		var _img = null;
		var _prev = $("#bannerImgPrev");
		$(_prev).off("click");
		$(_prev).on("click", function(){
			_img = $("#topBanner").children().find(".file_image").attr("src");
			_doPop(_img);
		});
		
	}
	
	//	버튼 활성화
	var _prevDisabled = function(target, bool){
		$("#bannerImgPrev").attr("disabled", bool);
	}
	
	//	미리보기 이미지 컨테이너
	var _deleteImageBox = function(type){
		$("#topBanner").children().remove();
	}
	
	//	미리보기 : 팝업으로 이미지 보기 
	function _doPop(target){	
		var _iframe = "<iframe width='100%;' height='100%;' src='" + target +"'</iframe>";
		var _x = window.open();
		_x.document.open();
		_x.document.write(_iframe);
		_x.document.close();
	};
	
	function _doImageAjax(target, callFunction){
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
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
		_obj.ad_seq				= $("#ad_seq").val();
		_obj.ad_req_type		= $("#ad_req_type").val();
		_obj.ad_req_id			= $("#storeSelectExt").val();
		_obj.coupon_dtl_txt		= [];
		
		var _bannerImg = {};
		var _topBanner = $("#topBanner").find("div");
		_bannerImg.file_dtl_desc 	 = $(_topBanner).find("input[name=file_dtl_desc]").val();
		_bannerImg.file_dtl_ext 	 = $(_topBanner).find("input[name=file_dtl_ext]").val();
		_bannerImg.file_dtl_origin 	 = $(_topBanner).find("input[name=file_dtl_origin]").val();
		_bannerImg.file_dtl_path 	 = $(_topBanner).find("input[name=file_dtl_path]").val();
		_bannerImg.file_dtl_url_path = $(_topBanner).find("input[name=file_dtl_url_path]").val();
		_bannerImg.file_type 		 = $(_topBanner).find("input[name=file_type]").val();
		
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
		
		
		$.ajax({
			type: "POST",
			url: "/admin/ad/coupon/writeProc",
			data: JSON.stringify(_obj),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				if(res.result == false || res.result == 'false'){
					return alert("저장에 실패하였습니다. 관리자에게 문의 바랍니다.");
				}else{
					alert("저장에 성공하였습니다.");
					
					$("#listBtn").click();
				}
			},
			error: function (e) {
				alert("저장에 실패하였습니다. 관리자에게 문의 바랍니다.");
				console.log("error : " + e.toString())
			}
		});
		
		
	});
	
	////	목록
	$("#listBtn").on("click", function(){
		$("#listForm").submit();
	})

})