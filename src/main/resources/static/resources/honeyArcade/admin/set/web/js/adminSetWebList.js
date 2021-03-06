$(function(){
	//	점주용 홈페이지 회사소개 파일 업로드
	$("#webImgFile").on("change", function(e){
		_changeEvt($(this), 1);
	});
	
	//	 점주용 홈페이지 유료상품 혜택 소개 파일 업로드	
	$("#eventImgFile").on("change", function(e){
		_changeEvt($(this), 2);
	});
	
	//	파일업로드 이벤트
	function _changeEvt(target, type){
		
		var _isImg = koreasoft.modules.regex.isImgExt($(target).val());
		if(!_isImg){
			$(target).val("")		
			_deleteImageBox(target, type);	
			_prevDisabled(target, type, true);	
			return alert('이미지만 업로드 할수 있습니다');
		}
		
		_doImageAjax(target, type, _makeData);
	}
	
	//	미리보기 이벤트 이미지 삭제
	function _deleteImageBox(target, type){
		var _imgClone = $("#imgClone").find(".imgClone").clone()
		
		if(type == 1){
			$("#webImgFilePrevBox").children().remove();
			$("#imgArea1").children().remove();
			$("#imgArea1").append(_imgClone);
			
		}else{
			$("#eventImgPrevBox").children().remove();
			$("#imgArea2").children().remove();
			$("#imgArea2").append(_imgClone);
		}
	}
		
	//	버튼 활성화 이벤트
	function _prevDisabled(target, type, bool){
		if(type == 1){
			$("#webImgPreViewBtn").attr("disabled", bool);
			$("#webImgSaveBtn").attr("disabled", bool);
		}else{
			$("#eventImgPreViewBtn").attr("disabled", bool);
			$("#eventImgSaveBtn").attr("disabled", bool);			
		}
	}
	
	//	이미지 서버에 저장
	function _doImageAjax(target, type , callFunction){
		var _formData = new FormData();
		var _file = $(target)[0].files[0];
		if(!koreasoft.modules.regex.imgSizeCheck(_file.size, 3)){
			alert("3메가 이하의 파일을 올려주세요");
			return;
		}				
		_formData.append("files", _file);
		koreasoft.modules.file.uploadPost(null, _formData, callFunction, target, type);
	}
	
	//	데이터 생성
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
		
		if(type == 1){	
			_deleteImageBox(target, type);	//	이미지 삭제
			$("#webImgFilePrevBox").append(_clone);
			//	확인 버튼 활성화
			_prevDisabled(target, type, false);
			//	이벤트 추가
			_preViewEvt(target, type);
					
		}else{			
			_deleteImageBox(target, type);
			$("#eventImgPrevBox").append(_clone);
			_prevDisabled(target, type, false);
			_preViewEvt(target, type);
		}
		
		$(target).val("");
		
	}
	
	//	이벤트 생성
	function _preViewEvt(target, type){
				
		if(type == 1){
			$("#imgArea1").children().remove();
			var _t = $("#webImgFilePrevBox").find(".file_image").clone();
			$(_t).attr("style", "width:50%");
			$("#imgArea1").append(_t);
						
			$("#webImgPreViewBtn").on("click", function(){
				var _img = $("#webImgFilePrevBox").find(".file_image").clone();
				
				var _image = new Image();
				_image.src = $(_img).attr("src")
				_imageShow(_image);
			});
			
			$("#webImgSaveBtn").on("click", function(){
				var _that = $("#webImgFilePrevBox");
				_doSaveAjax(_that, type);
			});
						
		}else{
			
			$("#imgArea2").children().remove();
			var _t = $("#eventImgPrevBox").find(".file_image").clone();
			$(_t).attr("style", "width:50%");
			$("#imgArea2").append(_t);
			
			$("#eventImgPreViewBtn").on("click", function(){
				var _img = $("#eventImgPrevBox").find(".file_image").clone();
				
				var _image = new Image();
				_image.src = $(_img).attr("src")
				_imageShow(_image);				
			
			});
			
			$("#eventImgSaveBtn").on("click", function(){
				var _that = $("#eventImgPrevBox");
				_doSaveAjax(_that, type);
			});
		}
	}//
	
	function _doSaveAjax(_target, type){
		var _obj = {};
		_obj.type = type;		//	1 점주 웹 이미지, 2 혜택사항
		_obj.file_dtl_desc		= $(_target).find("input[name=file_dtl_desc]").val();
		_obj.file_dtl_ext	 	= $(_target).find("input[name=file_dtl_ext]").val();
		_obj.file_dtl_origin	= $(_target).find("input[name=file_dtl_origin]").val();
		_obj.file_dtl_path		= $(_target).find("input[name=file_dtl_path]").val();
		_obj.file_dtl_url_path	= $(_target).find("input[name=file_dtl_url_path]").val();
		_obj.file_type			= $(_target).find("input[name=file_type]").val();
		_obj.old_has_file		= false;
		_obj.old_file = null;
		
		var _that = type==1? $("#savedWebImg") : $("#savedEventImg"); 
		
		if($(_that).find("input[name=file_seq]").length !=0){
			_obj.old_has_file = true;
			
			var _file = {};
			_file.file_seq = $(_that).find("input[name=file_seq]").val();
			_file.img_seq  = $(_that).find("input[name=img_seq]").val();
			
			_obj.old_file = _file;
		}
		
		$.ajax({
			type: "POST",
			url: "/admin/set/web/writeProc",
			data : JSON.stringify(_obj),
           	contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function (res) {
				if(res.result == false || res.result == 'false'){
					alert("저장에 실패하였습니다 관리자에게 문의 바랍니다");
					return;
				}else{
					alert("저장에 성공하였습니다");
					window.location.reload();
				}
				
			},
			error: function (e) {
				alert("저장에 실패하였습니다 관리자에게 문의 바랍니다");
				console.log("error : " + e.toString())
			}
		});
	}
	
	//	회사소개
	var _savedWebImg = $("#savedWebImg");
	//	유료상품
	var _savedEventImg = $("#savedEventImg");
	
	if($(_savedWebImg).find("input[name=file_seq]").length !=0 ){
		$("#webImgBtn").attr("disabled", false);
		$("#webImgBtn").on("click", function(){
			var _img = $(_savedWebImg).find(".file_image").clone();
			var _image = new Image();
			_image.src = $(_img).attr("src")
			_imageShow(_image);
		})
	}
	
	if($(_savedEventImg).find("input[name=file_seq]").length !=0 ){
		$("#eventImgBtn").attr("disabled", false);
		$("#eventImgBtn").on("click", function(){
			var _img = $(_savedEventImg).find(".file_image").clone();
			var _image = new Image();
			_image.src = $(_img).attr("src")
			_imageShow(_image);
		});
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
