
$(function(){
	//	데이트피커
	var f1 = flatpickr(document.getElementById('basicFlatpickr'), {
		minDate : "today"
		, locale: "ko"
	});
	var f2 = flatpickr(document.getElementById('basicFlatpickr1'), {
		minDate : "today"
		, locale: "ko"
	});
	
	////	파일 설정
	//	배너 사진 1, 홍보 메인 사진 2
	var _bannerImg = $("input[name=banner_img]");
	var _bannerMainImg = $("input[name=banner_main_img]");
	
	var _fileUpload = function(target, type){
		$(target).on("change", function(e){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				$(_target).val("")
				
				_deleteImageBox(type);	
				_prevDisabled(_target, type, true);	
				return alert('이미지만 업로드 할수 있습니다');
			}
			_doImageAjax(_target, type, _makeData);
		})
	}		
	
	var _makeData = function(res, target, type){
		if(res.result == false || res.result == 'false'){
			alert("파일 업로드에 문제가 생겼습니다");
			return;
		}
		
		_deleteImageBox(type)
				
		var _clone = $("#fileClone").children().clone();
		$(_clone).find("input[name=file_type]").val(type==1? 5 : 6);		
		$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
		$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
		$(_clone).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
		$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
		$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
		$(_clone).find(".file_image").attr('src', res.files[0].file_dtl_url_path);
		
		if(type == 1){
			$("#topBanner").append(_clone);
		}else{
			$("#popBanner").append(_clone);
		}
		
		//	버튼 활성화
		_prevDisabled(target, type, false);
		//	이벤트 추가
		_preViewEvt(target, type);
		$(target).val("");
	}
	
	//	미리보기 이벤트
	function _preViewEvt(target, type){
		var _img = null;
		
		if(type == 1){
			var _prev = $("#bannerImgPrev");
			
			$(_prev).off("click");
			
			$(_prev).on("click", function(){
				_img = $("#topBanner").children().find(".file_image").attr("src");
				var _image = new Image();
				_image.src = _img;
				_imageShow(_image);				
			});
		}else{
			var _prev = $("#bannerMainImgPrev");
			$(_prev).off("click");
			
			$(_prev).on("click", function(e){
				_img = $("#popBanner").children().find(".file_image").attr("src");
				var _image = new Image();
				_image.src = _img;
				_imageShow(_image);				
			});
		}
	}
	
	//	버튼 활성화
	var _prevDisabled = function(target, type, bool){
		if(type == 1){
			$("#bannerImgPrev").attr("disabled", bool);
		}else{
			$("#bannerMainImgPrev").attr("disabled", bool)
		}
	}
	
	//	미리보기 이미지 컨테이너
	var _deleteImageBox = function(type){
		if(type == 1){
			$("#topBanner").children().remove();
		}else{
			$("#popBanner").children().remove();
		}
	}
		
	function _doImageAjax(target, type , callFunction){
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
		if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
			alert("1메가 이하의 파일을 올려주세요");
			return;
		}
		_formData.append("files", _file);
		koreasoft.modules.file.uploadPost(null, _formData, callFunction, target, type);
	}
	
	_fileUpload(_bannerImg		, 1);
	_fileUpload(_bannerMainImg	, 2);
	
	////	지역 설정 모달
	$("#areaAddBtn").on("click", function(){
		var _sidoSeq = $("#popSidoSelect").val();
		var _sigunguSeq = $("#popSigunguSelect").val();
		var _dongSeq = $("#popDongSelect").val();
		var _dongName = $("#popDongSelect option:checked").text();
		
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
		
		if($("#areaAddbox").children().length != 0){
			var _areaBox = $("#areaAddbox").children();
			var _isHas = false;
			
			for(var i=0; i<_areaBox.length; i++){
				var _v = $(_areaBox[i]).find("input[name=dong_seq]").val();
				
				if(_v == _dongSeq){
					_isHas = true;
					break;
				}
			}
			
			if(_isHas){
				alert(_dongName + "은 이미 선택된 지역입니다");
				return;
			}
		}
		
		
		var _clone = $("#badgeClone").children().clone();
		$(_clone).find("input[name=sido_seq]").val(_sidoSeq);
		$(_clone).find("input[name=sigungu_seq]").val(_sigunguSeq);
		$(_clone).find("input[name=dong_seq]").val(_dongSeq);
		$(_clone).find(".area-text").text(_dongName);
		var _evtTarget = $(_clone).find(".banner-del-btn");
		_badgeDel(_evtTarget);
		$("#areaAddbox").append(_clone);
		
	});
	
	function _badgeDel(target){
		$(target).on("click", function(){
			$(target).parents(".area-box").remove();
		})
	}
	
	$("#popSaveBtn").on("click", function(){
		if($("#areaAddbox").children().length == 0){
			alert("선택된 지역이 없습니다");
		}else{
			var _span = $("#areaAddbox").children();
			var _area = $("#areaAd").children();
			
			for(var i=0; i<_span.length; i++){
				
				var _s = $(_span[i]).find("input[name=dong_seq]").val();
				var _has = false;	
				
				if(_area.length != 0){
					for(var j=0; j<_area.length; j++){
						var _a = $(_area[j]).find("input[name=dong_seq]").val();
						if(_s == _a){
				
							_has = true;
							break;
						}
					}
				}
				
				if(!_has){
					$("#areaAd").append(_span[i]);	
				}
			}
		}
		
		$("#areaAddbox").children().remove();
		$('.modal').modal('hide');
	})
	
	$("#popCancelBtn").on("click", function(){
		$("#areaAddbox").children().remove();
	});
	
	/////	저장
	//	ad_owner_type	1 제휴점포, 2외부점포
	$("#saveBtn").on("click", function(){
		
		var _sidoSeq = $("#sidoSelectExt").val();
		var _sigunguSeq = $("#sigunguSelectExt").val();
		var _dongSeq = $("#dongSelectExt").val();
		var _ownerName =$("#owner_name").val();
		var _bannerUrl = $("#banner_url").val();
		var _bannerSort = $("#t-text").val();
		var _bannerStartDay = $("#basicFlatpickr").val();
		var _bannerEndDay = $("#basicFlatpickr1").val();
		
		
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
		
		if(_ownerName==""){
			alert("상호명을 입력해주세요");
			return;
		}
		
		if(_bannerUrl==""){
			alert("URl을 입력해주세요");
			return;
		}
		
		
		if($("#topBanner").children().length == 0){
			alert("광고배너 사진을 업로드 해주세요");
			return;
		}
		
		if($("#popBanner").children().length == 0){
			alert("홍보메인 사진을 업로드 해주세요");
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
		
		if($("#areaAd").children().length == 0){
			alert("지역을 설정해주세요");
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
		_obj.owner_name			= _ownerName;
		_obj.banner_url			= _bannerUrl;
		_obj.banner_sort		= _bannerSort;
		_obj.banner_start_day	= _bannerStartDay;
		_obj.banner_end_day		= _bannerEndDay;  	 
		_obj.banner_type		= $("#banner_type").val();
		_obj.file				= [];
		_obj.area				= [];
		
		var _bannerImg = {};
		var _topBanner = $("#topBanner").find("div");
		_bannerImg.file_dtl_desc 	 = $(_topBanner).find("input[name=file_dtl_desc]").val();
		_bannerImg.file_dtl_ext 	 = $(_topBanner).find("input[name=file_dtl_ext]").val();
		_bannerImg.file_dtl_origin 	 = $(_topBanner).find("input[name=file_dtl_origin]").val();
		_bannerImg.file_dtl_path 	 = $(_topBanner).find("input[name=file_dtl_path]").val();
		_bannerImg.file_dtl_url_path = $(_topBanner).find("input[name=file_dtl_url_path]").val();
		_bannerImg.file_type 		 = $(_topBanner).find("input[name=file_type]").val();
		
		var _bannerMainImg = {};
		var _popBanner = $("#popBanner").find("div");
		_bannerMainImg.file_dtl_desc	 = $(_popBanner).find("input[name=file_dtl_desc]").val();
		_bannerMainImg.file_dtl_ext		 = $(_popBanner).find("input[name=file_dtl_ext]").val();
		_bannerMainImg.file_dtl_origin	 = $(_popBanner).find("input[name=file_dtl_origin]").val();
		_bannerMainImg.file_dtl_path 	 = $(_popBanner).find("input[name=file_dtl_path]").val();
		_bannerMainImg.file_dtl_url_path = $(_popBanner).find("input[name=file_dtl_url_path]").val();
		_bannerMainImg.file_type 		 = $(_popBanner).find("input[name=file_type]").val();
		
		_obj.file.push(_bannerImg);
		_obj.file.push(_bannerMainImg);
		
		var _area = $("#areaAd").children();
		for(var i=0; i<_area.length; i++){
			_obj.area.push($(_area[i]).find("input[name=dong_seq]").val());
		}
		
		$.ajax({
			type: "POST",
			url: "/admin/ad/area/writeProc",
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
					
					$("#listBtn").click();
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