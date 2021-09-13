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

koreasoft.namespace('koreasoft.modules.file');

koreasoft.modules.file = function() {
	//	_csrfToken 필요
	
	//	파일 업로드 form post ajax
	var _uploadPost = function( url, formData, callback, target, type){
		$.ajax({
			type: "POST",
			url: url == null || url == ""? "/common/file/upload" : url,
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			data: formData,
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
			},
			success: function(res) {
				callback(res, target, type);
			},
			err: function(err) {
				console.log("err:", err)
			}				
		})
	};	


	// 특권 메서드가 들어있는 객체를 반환 
	return { 
		
		uploadPost : _uploadPost
	}; 
}(); 


 