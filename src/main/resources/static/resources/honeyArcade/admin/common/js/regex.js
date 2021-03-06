var koreasoft = koreasoft || {}; 
koreasoft.namespace = function(ns_string) { 
	var parts = ns_string.split('.'), parent = koreasoft, i; 
	if (parts[0] === "koreasoft") { 
		parts = parts.slice(1); 
	} 
	for (i = 0; i < parts.length; i += 1) { 
		if (typeof parent[parts[i]] === "undefined") { 
			parent[parts[i]] = {}; 
		} 
		parent = parent[parts[i]]; 
	} 
	return parent; 
} 

koreasoft.namespace('koreasoft.modules.regex'); 

koreasoft.modules.regex = function() { 
	//	private 
	//	id : 영문자 또는 숫자 만 가능 
	var _expUserId =  /[a-z0-9]$/;  
	//	document 속성 id 가 존재여부를 확인하는 함수	: 존재하면 true 
	var _targetIdCheck = function(target){ 
		if(document.getElementById(target) == undefined){ 
			console.log("target id is undefined : " + target); 
			return true; 
		}else{ 
			return false; 
		} 
	} 
	
	//	document 속성 id 의 값을 가져오는 함수 
	var _targetIdValue = function(target){ 
		return document.getElementById(target).value; 
	} 
	
	///////////	public 
	//	데이터가 있는지 없는지 확인하는 함수		null : true 	not : false 
	var _isNull = function(str) { 
		if(str == undefined || str ==''){ 
			return true 
		}else{ 
			if(str.trim() == ''){ 
				return true; 
			} 
			return false; 
		} 
	};// 
	
	//	document 속성 id를 확인 한 후 값이 널인지 체크하는 함수 
	//	null 일경우 true	아니면 false 
	var _isNullById = function(target){ 
		if(!_targetIdCheck(target)){ 
			return _isNull(_targetIdValue(target)); 
		} 
	} 
	
	var _isPassword = function(str){
		//6~20 영문 대소문자 , 최소 1개의 숫자 혹은 특수 문자를 포함해야 함
		var _exp = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/; 
		return _exp.test(str);	
	}
	
	//	정규식 사용자 id체크하는 함수 
	//	정규식 규칙에 맞으면 true	아니면 false 
	var _isId = function(str){
		//id : 아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.
		var _expUserId	=   /^[a-z]+[a-z0-9]{5,19}$/g;
		return _expUserId.test(str); 
	}
	
	//	정규식 사용자 id체크하는 함수 
	//	정규식 규칙에 맞으면 true	아니면 false 
	var _expUserIdCheck = function(str){ 
		return _expUserId.test(str) 
	} 
	
	//	사용자 id의 정규식 체크하는 함수	 
	//	정규식 규칙에 맞으면 true	아니면 false 
	var _expUserIdCheckById = function(target){ 
		if(!_targetIdCheck(target)){ 
			var targetValue = _targetIdValue(target); 
			return _expUserIdCheck(targetValue); 
		} 
	}//	_expUserIdCheckById	 
	

	//	이미지 확장자 검색 
	var _isImgExt = function(str){ 
		//var _imgArr = ['JPG', 'JPEG', 'BMP', 'PNG', 'GIF', 'ICO'] 
		var _imgArr = ['JPG', 'JPEG', 'PNG']
		var _str = str.slice(str.lastIndexOf('.')+1); 
		var _result = false; 
		if(_imgArr.includes(_str.toLocaleUpperCase())){ 
			_result = true; 
		} 
		return _result; 
	} 
	
	//	파일 용량 체크 default 1mb
	//	imgSize는 mb>> 
	var _imgSizeCheck = function(fileSize, num){
		var _mb = 1024*1024;
		var _result = true;
		var _imgSize = num == null ? _mb : _mb*num; 	
		//console.log(_imgSize);		
		if(_imgSize<fileSize){
			_result = false;
		}
		
		return _result;
	}
	
	//	엑셀 체크
	var _isExcel = function(str){
		var _excelArr = ['XLS', 'XLSX'];
		var _str = str.slice(str.lastIndexOf('.')+1); 
		var _result = false; 
		if(_excelArr.includes(_str.toLocaleUpperCase())){ 
			_result = true; 
		} 
		return _result; 
	}
	
	
	
	/////////////////////////////////////////// 

	// 특권 메서드가 들어있는 객체를 반환 
	return { 
		isNull : _isNull, 
		isNullById:_isNullById,
		expUserIdCheck : _expUserIdCheck, 
		expUserIdCheckById : _expUserIdCheckById,
		isImgExt : _isImgExt,
		imgSizeCheck : _imgSizeCheck,
		isId : _isId,
		isPassword : _isPassword,
		isExcel : _isExcel
	}; 
}();