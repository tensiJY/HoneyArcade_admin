$(function(){
	var _adminMemberOwnerView = function(){
	var _openArr 	= ["주중", "주말", "월~토", "월", "화", "수", "목", "금", "토", "일", "해당사항없음"]
	var _dayArr 	= ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	var _weekArr 	= ["매주", "첫째주", "둘째주", "셋째주", "넷째주", "마지막주"];
	
	var init = function(){
		$.fn.selectpicker.Constructor.DEFAULTS.noneResultsText = "검색결과가 없습니다... {0}";
		$("#openDaySelect").selectpicker();
		$("#breakDaySelect").selectpicker();
		
		flatpickr(document.getElementById("openDayStart"), {
			enableTime : true,
			noCalendar : true,
			dateFormat : "H:i",
			time_24hr: true
		});
		
		flatpickr(document.getElementById("openDayEnd"), {
			enableTime : true,
			noCalendar : true,
			dateFormat : "H:i",
			time_24hr: true
		});
		
		flatpickr(document.getElementById("breakDayStart"), {
			enableTime : true,
			noCalendar : true,
			dateFormat : "H:i",
			time_24hr: true
		})

		flatpickr(document.getElementById("breakDayEnd"), {
			enableTime : true,
			noCalendar : true,
			dateFormat : "H:i",
			time_24hr: true
		});
		
		//	상태 업데이트
		$("#statusBtn").on("click", function(){
			var _status = $(this).attr("data-store-status");
			var _msg = "점포 상태를 변경하시겠습니까?";
			if(confirm(_msg)){
				var _obj = {};
				_obj.del_flag	= _status == 'N'? 'Y' : 'N';
				_obj.owner_id 	= $("#owner_id").val();
				_doAjax(_obj, "/admin/member/owner/statusProc", _statusCall);	
			}
		});
					
		//	휴무일 이벤트
		$("#dayOffAddBtn").on("click", function(){
			var _t = $("input[name=off_day]");
			var _a = $("input[name=off_week]");
			var _dayOffDay	= null;
			var _dayOffWeek	= null;
			for(var i=0; i<_t.length; i++){ 
				if($(_t[i]).prop("checked")){
					_dayOffDay = $(_t[i]).val();
				}
			 }
			for(var i=0; i<_a.length; i++){
				if($(_a[i]).prop("checked")){
					_dayOffWeek = $(_a[i]).val();
				}
			}
			
			var _check = true;
			var _len = $("#offDay").find(".offDay");
			for (var i = 0; i < _len.length; i++) {
				var _v = $(_len[i]).find("input[name=day_off_week]").val();
				if (_v == _dayOffWeek) {
					_check = false;
					break;
				}
			}
			if (!_check) {
				alert("휴무일의 주차가 중복 됩니다");
				return;
			}
			 
			var _dayOffDayText	= _dayArr[_dayOffDay];
			var _dayOffWeekText = _weekArr[ _dayOffWeek == 'A'? 0 : _dayOffWeek];
			
			var _clone = $("#cloneDayOff").children().clone(); 
			$(_clone).find("input[name=day_off_week]").val(_dayOffWeek);
			$(_clone).find("input[name=day_off_day]").val(_dayOffDay);
			$(_clone).find("input[type=text]").val(_dayOffWeekText + " " + _dayOffDayText);
			var _target = (_clone).find(".offDayDelBtn");
			$(_target).on("click", function(){
				_delOffDay(this);
			});
			$("#offDay").append(_clone);
		});
		
		//	휴무일 삭제 이벤트
		$(".offDayDelBtn").on("click", function(){
			_delOffDay(this);
		});
		
		//	휴게시간 추가 이벤트
		$("#breakAddBtn").on("click", function(){
			var _breakDay = $("#breakDaySelect").val();
			if(_breakDay == ""){
				alert("휴게 시간 요일을 선택 해주시기 바랍니다");
				return;
			}
			
			var _openTime = "";
			var _closeTime = "";
			
			if(_breakDay != 11){
				_openTime  = $("#breakDayStart").val();
				if(_openTime ==""){
					alert("오픈 시간을 선택해주시기 바랍니다");
					return;
				}
				_closeTime = $("#breakDayEnd").val();
				if(_closeTime == ""){
					alert("마감 시간을 선택해주시기 바랍니다");
					return;
				}
				if(_openTime>_closeTime){
					alert("오픈 시간은 마감 시간보다 이전 시간이어야 합니다");
					return;
				}
			}
			
			var _check = true;
			var _len = $("#breakDay").find(".breakDay");
			for (var i = 0; i < _len.length; i++) {
				var _v = $(_len[i]).find("input[name=break_day]").val();
				if (_v == _breakDay) {
					_check = false;
					break;
				}
			}
			if (!_check) {
				alert("휴게시간의 요일이 중복 됩니다");
				return;
			}
			
			var _clone = $("#cloneBreakDay").children().clone(); 
			$(_clone).find("input[name=break_day]").val(_breakDay);
			$(_clone).find("input[name=open_time]").val(_openTime);
			$(_clone).find("input[name=close_time]").val(_closeTime);
			
			var _text = _openArr[_breakDay-1];
			if(_breakDay != 11){
				_text += " " + _openTime + " ~ " + _closeTime;
			}
			
			$(_clone).find("input[type=text]").val(_text);
			var _target = (_clone).find(".breakDayDelBtn");
			$(_target).on("click", function(){
				_delBreakDay(this);
			})
			
			$("#breakDay").append(_clone);			
		});
		
		//	휴게시간 삭제 이벤트
		$(".breakDayDelBtn").on("click", function(){
			_delBreakDay(this);
		});
				
		//	영업시간 추가 이벤트
		$("#openAddBtn").on("click", function(){
			var _openDay = $("#openDaySelect").val();
			if(_openDay == ""){
				alert("영업 시간 요일을 선택 해주시기 바랍니다");
				return;
			}
			
			var _openTime = "";
			var _closeTime = "";
			
			if(_openDay != 11){
				_openTime  = $("#openDayStart").val();
				if(_openTime ==""){
					alert("오픈 시간을 선택해주시기 바랍니다");
					return;
				}
				
				_closeTime = $("#openDayEnd").val();
				if(_closeTime == ""){
					alert("마감 시간을 선택해주시기 바랍니다");
					return;
				}
				
				if(_openTime>_closeTime){
					alert("오픈 시간은 마감 시간보다 이전 시간이어야 합니다");
					return;
				}
			}
			var _check = true;
			var _list = $("#openDay").find(".openDay");
			for(var i=0; i<_list.length; i++){
				var _v = $(_list[i]).find("input[name=open_day]").val();
				if(_v == _openDay){
					_check = false;	
					break;
				}
			}
			if(!_check){
				alert("영업의 요일이 중복 됩니다");
				return;
			}
			
			var _clone = $("#cloneOpenDay").children().clone(); 
			$(_clone).find("input[name=open_day]").val(_openDay);
			$(_clone).find("input[name=open_time]").val(_openTime);
			$(_clone).find("input[name=close_time]").val(_closeTime);
			
			var _text = _openArr[_openDay-1];
			if(_openDay != 11){
				_text += " " + _openTime + " ~ " + _closeTime;
			}
			
			$(_clone).find("input[type=text]").val(_text);
			var _target = (_clone).find(".openDayDelBtn");
			$(_target).on("click", function(){
				_delOpenDay(this);
			})
			
			$("#openDay").append(_clone);
		});
		
		//	영업시간 오픈 삭제 이벤트
		$(".openDayDelBtn").on("click", function(){
			_delOpenDay(this);
		});
		
		//	업종 이벤트
		$("input[name=lcate_seq]").on("click", function(){
			$("input[name=lcate_seq]").removeClass("lcateSelect");
			$(this).addClass("lcateSelect");
			
			var _obj = {};
			_obj.build_seq = $(this).attr("data-build-seq");
			_obj.lcate_seq = $(this).attr("data-lcate-seq");
			
			var _url = "/common/api/getMcateOfBuild";
		
			_doAjax(_obj,_url, _setMcate)	
			
		});
		
		//	메뉴 정보 이벤트
		var _mt4 = $("#productForm").find(".productList");
		for(var i=0; i<_mt4.length; i++){
			var _downBtn = $(_mt4[i]).children(".productDownBtn");
			var _uploadBtn = $(_mt4[i]).find("input[name=file]");
			var _delBtn = $(_mt4[i]).children(".productDelBtn");
			var _viewBtn = $(_mt4[i]).children(".productViewBtn");
			
			//	다운로드 이벤트
			$(_downBtn).on("click", function(){
				_downEvt(this, 4);
			})
			
			//	삭제 이벤트
			$(_delBtn).on("click", function(){
				_delEvt(this, 4);
			});
			
			//	확인 이벤트
			$(_viewBtn).on("click", function(){
				_viewEvt(this, 4);
			});
			
			//	업로드 이벤트
			$(_uploadBtn).on("change", function(){
				_upEvt(this, 4);
			});
		}
		
		//	추가 이벤트 : 상품추가
		$("#productAddBtn").on("click", function(){
			var _productName = $("#productName").val();
			var _productPrice = $("#productPrice").val();
			if(_productName == ""){
				alert("상품명을 입력해주세요");
				$("#productName").focus();
				return 
			}
			if(_productPrice == ""){
				alert("가격을 입력해주세요");
				return;
			}
			var _t = $(this).parents("form").find("div#productAddArea");
			var _fileType 		= $(_t).find("input[name=file_type]").val();		
			var _fileDtlDesc 	= $(_t).find("input[name=file_dtl_desc]").val();
			var _fileDtlExt 	= $(_t).find("input[name=file_dtl_ext]").val();
			var _fileDtlOrigin 	= $(_t).find("input[name=file_dtl_origin]").val();
			var _fileDtlPath 	= $(_t).find("input[name=file_dtl_path]").val();
			var _fileDtlUrlPath = $(_t).find("input[name=file_dtl_url_path]").val();
			if(_fileType == ""){
				return alert("상품 이미지를 추가해주시기 바랍니다");
			}
			var _clone = $("#cloneProductFile").children().clone();
			$(_clone).find("input[name=file_type]").val(_fileType);
			$(_clone).find("input[name=file_dtl_desc]").val(_fileDtlDesc);
			$(_clone).find("input[name=file_dtl_ext]").val(_fileDtlExt);
			$(_clone).find("input[name=file_dtl_origin]").val(_fileDtlOrigin);
			$(_clone).find("input[name=file_dtl_path]").val(_fileDtlPath);
			$(_clone).find("input[name=file_dtl_url_path]").val(_fileDtlUrlPath);
			$(_clone).find("input[name=product_name]").val(_productName);
			$(_clone).find("input[name=product_price]").val(_productPrice);
			var _viewBtn	= $(_clone).find(".productViewBtn");
			var _delBtn 	= $(_clone).find(".productDelBtn");
			var _downBtn 	= $(_clone).find(".productDownBtn");
			var _uploadBtn 	= $(_clone).find("input[name=file]");
			$("#productTarget").append(_clone);
			$(_delBtn).on("click", function(e){
				_delEvt(this, 4);	
			});
			$(_viewBtn).on("click",function(e){
				_viewEvt(this, 4);
			});
			$(_downBtn).on("click", function(e){
				_downEvt(this, 4);	
			});
			$(_uploadBtn).on("change", function(){
				_upEvt(this, 4);
			});
			$(_t).find("input[name=file_type]").val("");		
			$(_t).find("input[name=file_dtl_desc]").val("");
			$(_t).find("input[name=file_dtl_ext]").val("");
			$(_t).find("input[name=file_dtl_origin]").val("");
			$(_t).find("input[name=file_dtl_path]").val("");
			$(_t).find("input[name=file_dtl_url_path]").val("");
			$("#productName").val("");
			$("#productPrice").val("");
		});
		
		//	사진첨부 이벤트 : 상품 추가
		$("input[name=productUpBtn]").on("change", function(){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				return alert('이미지만 업로드 할수 있습니다');
			}
			var _formData = new FormData();
			var _file = $(_target)[0].files[0];
			if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
				alert("1메가 이하의 파일을 올려주세요");
				return;
			} 
			_formData.append("files", _file);
			koreasoft.modules.file.uploadPost(null, _formData, _setFile, _target, 5);
		});
		
		//	매장 프로필 사진 다운로드
		$("#proDownBtn").on("click", function(){
			_downEvt(this, 3);
		});
		//	매장 프로필 사진 확인
		$("#proViewBtn").on("click", function(){
			_viewEvt(this, 3);
		});		
		
		//	매장 프로필 사진 업로드
		$("input[name=proUpBtn]").on("change", function(){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				return alert('이미지만 업로드 할수 있습니다');
			}
			var _formData = new FormData();
			var _file = $(_target)[0].files[0];
			if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
				alert("1메가 이하의 파일을 올려주세요");
				return;
			} 
			_formData.append("files", _file);
			koreasoft.modules.file.uploadPost(null, _formData, _setFile, _target, 3);
		});
		
		//	메인 사진 다운로드
		$("#mainImgTarget .mainFileImg").find(".fileDownBtn").on("click", function(){
			_downEvt(this, 1);
		});
				
		//	메인 사진 확인
		$("#mainImgTarget .mainFileImg").find(".imgViewBtn").on("click", function(){
			_viewEvt(this, 1);
		});
		
		//	메인 사진 삭제
		$("#mainImgTarget .mainFileImg").find(".delBtn").on("click", function(){
			 _delEvt(this, 1);
		});
		
		//	메인 사진 업로드
		$("input[name=mainUpBtn]").on("change", function(){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				return alert('이미지만 업로드 할수 있습니다');
			}
			if($("#mainImgTarget .mainFileImg").length == 5){
				return alert("이미지는 최대 5개까지 업로드 할 수 있습니다");
			}
			
			var _formData = new FormData();
			var _file = $(_target)[0].files[0];
			if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
				alert("1메가 이하의 파일을 올려주세요");
				return;
			} 
			_formData.append("files", _file);
			koreasoft.modules.file.uploadPost(null, _formData, _setFile, _target, 1);
		});
		
		//	사업자 등록증 다운로드
		$("#busiDownBtn").on("click", function(){
			_downEvt(this, 2);
		})
		//	사업자 등록증 업로드
		$("input[name=busiUpBtn]").on("change", function(){
			var _target = $(this);
			var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
			if(!_isImg){
				return alert('이미지만 업로드 할수 있습니다');
			}
			var _formData = new FormData();
			var _file = $(_target)[0].files[0];
			if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
				alert("1메가 이하의 파일을 올려주세요");
				return;
			} 
			_formData.append("files", _file);
			koreasoft.modules.file.uploadPost(null, _formData, _setFile, _target, 2);
		
		});		
		
		//	사업자 등록증 확인
		$("#busiViewBtn").on("click", function(){
			_viewEvt(this, 2);
		});
		
		//	목록 이벤트
		$("#listBtn").on("click", function(){
			$("#listForm").submit();
		})
		
		//	승인 이벤트
		$("#acceptBtn").on("click", function(){
			if(confirm("승인하시겠습니까?")){
				var _obj = {};
				_obj.owner_id = $("#owner_id").val();
				var _url = "/admin/member/owner/acceptProc";
				_doAjax(_obj, _url, _acceptCallback)
			}
		});
		
		//	거절 이벤트
		$("#rejectBtn").on("click", function(){
			$("#rejectModal").modal();
		});
		
		$("#rejectModalBtn").on("click", function(){
			if(confirm("반려하시겠습니까?")){
				if($("#rejectText").val().trim()==""){
					alert("반려 사유를 작성해주세요");
					return;
				}
				var _obj = {};
				_obj.owner_id = $("#owner_id").val();
				_obj.member_id = $("#member_id").val();
				_obj.reject_text = $("#rejectText").val();
				_obj.owner_email = $("#owner_email").val();
				_doAjax(_obj, "/admin/member/owner/rejectProc", _rejectCallback);	
			}
			$("#rejectModal").modal("hide");
		});
		
		//	저장 이벤트
		$("#saveBtn").on("click", function(){
			if(confirm("저장하시겠습니까?")){
				var _obj = {};
				_obj.owner_id		= $("#owner_id").val();
				_obj.owner_email	= $("#owner_email").val();
				_obj.owner_phone 	= $("#owner_phone").val();
				_obj.sido_seq		= $("#sidoSelect").val();
				_obj.sigungu_seq	= $("#sigunguSelect").val();
				_obj.dong_seq		= $("#dongSelect").val();
				_obj.build_seq		= $("#buildSelect").val();
				_obj.store_name		= $("#store_name").val();
				_obj.store_link		= $("#store_link").val();
				_obj.open			= [];
				_obj.break			= [];
				_obj.day			= [];
				_obj.product		= [];
				_obj.del_file		= [];
				_obj.main_file_seq	= $("#mainImg").children("input[name=file_seq]").val();
				_obj.main_img		= [];
				_obj.proImg			= null;
				_obj.busiImg		= null;
				_obj.lcate_seq		= null;
				_obj.mcate_seq		= $("#mcate_seq").val();
				_obj.store_text		= $("#store_text").val();
				_obj.store_keyword	= $("#store_keyword").val();
				_obj.store_sort		= $("#store_sort").val();
				
				var _mainImgTarget = $("#mainImgTarget").find(".mainFileImg");
				if(_mainImgTarget.length == 0){
					alert("메인 사진을 최소 1장, 최대 5장을 등록해주세요");
					return;
				}
				for(var i=0; i<_mainImgTarget.length; i++){
					_obj.main_img.push(_getImgObj($(_mainImgTarget[i])));
				}
				if($("#delFile").children().length != 0){
					var _delFile = $("#delFile").find("input[name=file_seq]");
					for(var i=0; i<_delFile.length; i++){
						var _v = $(_delFile[i]).val().trim();
						if(_v == ""){
							continue;
						}
						_obj.del_file.push(_v);
					}
				}
								
				var _lcate = $(".lcate");
				for(var i=0; i<_lcate.length; i++) {
				    if($(_lcate[i]).hasClass('lcateSelect')){
				        _obj.lcate_seq = $(_lcate[i]).attr("data-lcate-seq");
				    }
				}

				var _openDay = $("#openDay").find('.openDay');
				if(_openDay.length == 0){
					alert("영업 시간을 입력해주세요");
					return;
				}
				
				for(var i=0; i<_openDay.length; i++){
					var _target = _openDay[i];
					var _openObj = {};
					_openObj.open_day	= $(_target).find("input[name=open_day]").val();
					_openObj.open_time	= $(_target).find("input[name=open_time]").val();
					_openObj.close_time	= $(_target).find("input[name=close_time]").val();
					_obj.open.push(_openObj);
				}
				
				var _breakDay = $("#breakDay").find('.breakDay');
				if(_breakDay.length == 0){
					alert("휴게 시간을 입력해주세요");
					return;
				}
				
				for(var i=0; i<_breakDay.length; i++){
					var _target = _breakDay[i];
					var _breakObj = {};
					_breakObj.break_day		= $(_target).find("input[name=break_day]").val();
					_breakObj.open_time		= $(_target).find("input[name=open_time]").val();
					_breakObj.close_time	= $(_target).find("input[name=close_time]").val();
					
					_obj.break.push(_breakObj);
				}				
				
				var _offDay = $("#offDay").find('.offDay');
				if(_offDay.length == 0){
					alert("휴무일을 입력해주세요");
					return;
				}
				
				for(var i=0; i<_offDay.length; i++){
					var _target = _offDay[i];
					var _offObj = {};
					_offObj.day_off_week	= $(_target).find("input[name=day_off_week]").val();
					_offObj.day_off_day		= $(_target).find("input[name=day_off_day]").val();
					
					_obj.day.push(_offObj);
				}
								
				_obj.proImg		= _getImgObj($("#proImg"));
				_obj.busiImg	= _getImgObj($("#busiImg"));
				
				if(_obj.store_sort == ""){
					return alert("노출 순위를 입력해주세요");
				}
				
				if(_obj.mcate_seq == ""){
					alert("세부 업종을 선택해주세요");
					return;
				}
				
				if(_obj.store_text.trim() == ""){
					return alert("한줄 소개를 입력해주세요");
				}
				if(_obj.store_keyword.trim() == ""){
					return alert("검색 키워드를 입력해주세요");
				}				
				var _productList = $("#productForm").find(".productList");//$("#productForm").find(".product-file-info");
				if(_productList.length == 0){
					alert("상품을 등록해주시기 바랍니다");
					return;
				}								
				var _productCheck = true;
				for(var i=0; i<_productList.length; i++){
					var _proTarget = $(_productList[i]);
					var _productObj = _getImgObj($(_proTarget).find('.product-file-info'));
					_productObj.product_name  = $(_proTarget).find('.productName').val();
					_productObj.product_price = $(_proTarget).find('.productPrice').val();
					if(_productObj.product_name == "" || _productObj.product_price == ""){
						_productCheck = false;
						break;
					}
					_obj.product.push(_productObj);
				}
				if(!_productCheck){
					alert("메뉴 및 가격을 입력해주세요");
					return;
				}
				_doAjax(_obj, "/admin/member/owner/saveProc", _saveCallback)
			}
		});
	}
	
	function _rejectCallback(res){
		if(res.result == false || res.result == 'false'){
			return alert("문제가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		
		alert("반려하였습니다");
		window.location.reload();
	}
	
	function _statusCall(res){
		if(res.result == false || res.result == 'false'){
			return alert("문제가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		
		alert("점포 상태를 변경하였습니다");
		window.location.reload();		
	}
	
	function _saveCallback(res){
		if(res.result == false || res.result == 'false'){
			return alert("문제가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		alert("저장하였습니다");
		window.location.reload();
	}
	
	//	이미지 정보 가져오기
	function _getImgObj(target){
		var _obj = {}
		_obj.file_seq			= $(target).find("input[name=file_seq]").val();
		_obj.file_type 			= $(target).find("input[name=file_type]").val();
		_obj.file_dtl_path 		= $(target).find("input[name=file_dtl_path]").val();
		_obj.file_dtl_desc 		= $(target).find("input[name=file_dtl_desc]").val();
		_obj.file_dtl_origin 	= $(target).find("input[name=file_dtl_origin]").val();
		_obj.file_dtl_ext 		= $(target).find("input[name=file_dtl_ext]").val();
		_obj.file_dtl_url_path	= $(target).find("input[name=file_dtl_url_path]").val();
		return _obj;
	}
	
	//	휴무일 삭제
	function _delOffDay(target){
		$(target).parents("div.offDay").remove();
	}
	
	//	휴게 시간 삭제
	function _delBreakDay(target){
		$(target).parents("div.breakDay").remove();
	}
	
	//	영업 시간 삭제
	function _delOpenDay(target){
		$(target).parents("div.openDay").remove();
	}
	
	//	승인 - 컬백
	function _acceptCallback(res){
		if(res.result == false || res.result == 'false'){
			return alert("승인 문제가 생겼습니다. 관리자에게 문의 바랍니다.");
		}
		alert("승인 처리하였습니다");
		window.location.reload();
	}
	
	//	파일 시퀀스 삭제
	function _appendDelFileSeq(fileSeq){
		var _input = document.createElement("input");
		$(_input).attr("name", "file_seq");
		$(_input).attr("value", fileSeq);
			
		$("#delFile").append(_input);
	}
	
	//	callback file 설정
	function _setFile(res, target, type){
		if(res.result == false || res.result == 'false'){
			alert("파일 업로드에 문제가 생겼습니다");
			return;
		}
				
		var _fileType = null;
		
		if(type==1){			//	메인사진
			_fileType = 8;
			var _clone = $("#mainFileImgClone").children().clone();
			$(_clone).find("input[name=file_type]").val(_fileType);
			$(_clone).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
			$(_clone).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
			$(_clone).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
			$(_clone).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
			$(_clone).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
			$(_clone).find(".fileName").val(res.files[0].file_dtl_origin);
			$(_clone).find(".fileDownBtn").on("click", function(){
				_downEvt(this, 1);
			});
			$(_clone).find(".imgViewBtn").on("click", function(){
				_viewEvt(this, 1);
			});
			$(_clone).find(".delBtn").on("click", function(){
				_delEvt(this, 1);
			});			
			$("#mainImgTarget").append(_clone);	
		}else if(type==2){		//	사업자 등록증
			_fileType = 9;
			var _fileSeqTag		= $("#cloneProductFile").children().find("input[name=file_seq]").clone();
			var _fileTypeTag	= $("#cloneProductFile").children().find("input[name=file_type]").clone();
			var _fileDtlPathTag	= $("#cloneProductFile").children().find("input[name=file_dtl_path]").clone();
			var _fileDtlDescTag	= $("#cloneProductFile").children().find("input[name=file_dtl_desc]").clone();
			var _fileDtlOriginTag = $("#cloneProductFile").children().find("input[name=file_dtl_origin]").clone();
			var _fileDtlExtTag 	= $("#cloneProductFile").children().find("input[name=file_dtl_ext]").clone();
			var _fileDtlUrlPathTag = $("#cloneProductFile").children().find("input[name=file_dtl_url_path]").clone();
			var _fileSeq = $("#busiImg").find("input[name=file_seq]").val();
			if(_fileSeq != ""){
				_appendDelFileSeq(_fileSeq);
			}
			$(_fileTypeTag).val(_fileType);
			$(_fileDtlPathTag).val(res.files[0].file_dtl_path);
			$(_fileDtlDescTag).val(res.files[0].file_dtl_desc);
			$(_fileDtlExtTag).val(res.files[0].file_dtl_ext);
			$(_fileDtlOriginTag).val(res.files[0].file_dtl_origin);
			$(_fileDtlUrlPathTag).val(res.files[0].file_dtl_url_path);
			$("#busiImg").children().remove();
			$("#busiImg").append(_fileSeqTag);
			$("#busiImg").append(_fileTypeTag);
			$("#busiImg").append(_fileDtlPathTag);
			$("#busiImg").append(_fileDtlDescTag);
			$("#busiImg").append(_fileDtlOriginTag);
			$("#busiImg").append(_fileDtlExtTag);
			$("#busiImg").append(_fileDtlUrlPathTag);			
		}else if(type==3){
			_fileType = 11;
			var _fileSeqTag		= $("#cloneProductFile").children().find("input[name=file_seq]").clone();
			var _fileTypeTag	= $("#cloneProductFile").children().find("input[name=file_type]").clone();
			var _fileDtlPathTag	= $("#cloneProductFile").children().find("input[name=file_dtl_path]").clone();
			var _fileDtlDescTag	= $("#cloneProductFile").children().find("input[name=file_dtl_desc]").clone();
			var _fileDtlOriginTag = $("#cloneProductFile").children().find("input[name=file_dtl_origin]").clone();
			var _fileDtlExtTag 	= $("#cloneProductFile").children().find("input[name=file_dtl_ext]").clone();
			var _fileDtlUrlPathTag = $("#cloneProductFile").children().find("input[name=file_dtl_url_path]").clone();
			
			var _fileSeq = $("#proImg").find("input[name=file_seq]").val();
			if(_fileSeq != ""){
				_appendDelFileSeq(_fileSeq);
			}
			
			$(_fileTypeTag).val(_fileType);
			$(_fileDtlPathTag).val(res.files[0].file_dtl_path);
			$(_fileDtlDescTag).val(res.files[0].file_dtl_desc);
			$(_fileDtlExtTag).val(res.files[0].file_dtl_ext);
			$(_fileDtlOriginTag).val(res.files[0].file_dtl_origin);
			$(_fileDtlUrlPathTag).val(res.files[0].file_dtl_url_path);
			
			$("#proImg").children().remove();
			$("#proImg").append(_fileSeqTag);
			$("#proImg").append(_fileTypeTag);
			$("#proImg").append(_fileDtlPathTag);
			$("#proImg").append(_fileDtlDescTag);
			$("#proImg").append(_fileDtlOriginTag);
			$("#proImg").append(_fileDtlExtTag);
			$("#proImg").append(_fileDtlUrlPathTag);		
					
		}else if(type==4){
			_fileType = 10;
			$(target).parents(".productList").find("input[name=file_type]").val(_fileType);		
			$(target).parents(".productList").find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
			$(target).parents(".productList").find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
			$(target).parents(".productList").find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
			$(target).parents(".productList").find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
			$(target).parents(".productList").find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
			var _fileSeq = $(target).parents(".productList").find("input[name=file_seq]").val();
			if(_fileSeq != ""){
				_appendDelFileSeq(_fileSeq);
			}	
			$(target).parents(".productList").find("input[name=file_seq]").val("");
		}else if(type == 5){
			_fileType = 10;
			var _t = $(target).parents("form").find("div#productAddArea");
			$(_t).find("input[name=file_type]").val(_fileType);		
			$(_t).find("input[name=file_dtl_desc]").val(res.files[0].file_dtl_desc);
			$(_t).find("input[name=file_dtl_ext]").val(res.files[0].file_dtl_ext);
			$(_t).find("input[name=file_dtl_origin]").val(res.files[0].file_dtl_origin);
			$(_t).find("input[name=file_dtl_path]").val(res.files[0].file_dtl_path);
			$(_t).find("input[name=file_dtl_url_path]").val(res.files[0].file_dtl_url_path);
		}
		$(target).val("");
	}
	
	//	업로드 이벤트
	function _upEvt(target, type){
		var _target = $(target);
		var _isImg = koreasoft.modules.regex.isImgExt($(_target).val());
		if(!_isImg){
			return alert('이미지만 업로드 할수 있습니다');
		}
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
		if(!koreasoft.modules.regex.imgSizeCheck(_file.size)){
			alert("1메가 이하의 파일을 올려주세요");
			return;
		} 
		_formData.append("files", _file);
		koreasoft.modules.file.uploadPost(null, _formData, _setFile, target, type);
	}
	
	//	확인 이벤트
	function _viewEvt(target, type){
		var _src = null;
		if(type==1){
			_src = $(target).parents(".mainFileImg").find("input[name=file_dtl_url_path]").val();
		}else if(type==2){
			_src = $("#busiImg").find("input[name=file_dtl_url_path]").val();
		}else if(type==3){
			_src = $("#proImg").find("input[name=file_dtl_url_path]").val();
		}else if(type==4){
			_src = $(target).parents(".productList").find("input[name=file_dtl_url_path]").val();
		}
		var _image = new Image();
		_image.src = _src
		_imageShow(_image);
	}
	
	//	다운로드 이벤트
	function _downEvt(target, type){
		if(type==1){	//	메인
			var _fileSeq 	= $(target).parent(".mainFileImg").find("input[name=file_seq]").val();
			var _fileDtlSeq	= $(target).parent(".mainFileImg").find("input[name=file_dtl_seq]").val();
			if(_fileSeq == ""){
				var _obj = {};
				_obj.file_dtl_origin 	= $(target).parent(".mainFileImg").find("input[name=file_dtl_origin]").val();
				_obj.file_dtl_url_path 	= $(target).parent(".mainFileImg").find("input[name=file_dtl_url_path]").val();
				koreasoft.modules.file.downloadOfPath(_obj);
			}else{
				koreasoft.modules.file.download(_fileSeq, _fileDtlSeq);
			}
		}else if(type==2){
			var _fileSeq = $("#busiImg").find("input[name=file_seq]").val();
			if(_fileSeq == ""){
				var _obj = {};
				_obj.file_dtl_origin 	= $("#busiImg").find("input[name=file_dtl_origin]").val();
				_obj.file_dtl_url_path 	= $("#busiImg").find("input[name=file_dtl_url_path]").val();
				koreasoft.modules.file.downloadOfPath(_obj);
			}else{
				koreasoft.modules.file.download(_fileSeq);
			}
		}else if(type==3){
			var _fileSeq = $("#proImg").find("input[name=file_seq]").val();
			if(_fileSeq == ""){
				var _obj = {};
				_obj.file_dtl_origin 	= $("#proImg").find("input[name=file_dtl_origin]").val();
				_obj.file_dtl_url_path 	= $("#proImg").find("input[name=file_dtl_url_path]").val();
				koreasoft.modules.file.downloadOfPath(_obj);
			}else{
				koreasoft.modules.file.download(_fileSeq);
			}
		}else if(type == 4){ // 상품
			var _fileSeq = $(target).parents(".productList").find("input[name=file_seq]").val();
			if(_fileSeq == ""){
				var _obj = {};				
				_obj.file_dtl_origin = $(target).parents(".productList").find("input[name=file_dtl_origin]").val();
				_obj.file_dtl_url_path = $(target).parents(".productList").find("input[name=file_dtl_url_path]").val();
				koreasoft.modules.file.downloadOfPath(_obj);
			}else{
				koreasoft.modules.file.download(_fileSeq);
			}
		}
	}
	
	//	삭제 이벤트
	function _delEvt(target, type){
		var _fileSeq = null;
		if(type==1){
			$(target).parents(".mainFileImg").remove();
		}else if(type==4){
			_fileSeq = $(target).parents(".productList").find("input[name=file_seq]").val();	
			$(target).parents(".productList").remove();			
		}
		if(_fileSeq != ""){
			var _input = document.createElement("input");
			$(_input).attr("name", "file_seq");
			$(_input).attr("value", _fileSeq);
			$("#delFile").append(_input);
		}
	}
	
	//	ajax
	function _doAjax(data, url, callback){
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
				if(res.result == false || res.result == 'false'){
					alert("처리하는데 문제가 생겼습니다.");
					return;
				}else{
					callback(res);					
				}
			},
			error: function (e) {
				alert("처리하는데 문제가 생겼습니다 관리자에게 문의 바랍니다");
			},
			complete : function(e){
				$("#l-wrapper").removeClass("on");
			}
		});
	}
	
	//	callback function 소분류
	function _setMcate(result){
		$("#mcate_seq").children().remove();
		for(var i=0; i<result.mcateList.length; i++){
			var _data = result.mcateList[i];
			var _opt = document.createElement("option");
			$(_opt).attr("value", _data.mcate_seq);
			$(_opt).text(_data.mcate_name);
			$("#mcate_seq").append(_opt);
		}	
		$("#mcate_seq").selectpicker("refresh");
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
	
	return{
			init : init
		}
	}

	new _adminMemberOwnerView().init();
});