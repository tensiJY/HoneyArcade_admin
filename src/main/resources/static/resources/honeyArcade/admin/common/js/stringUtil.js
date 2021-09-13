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

koreasoft.namespace('koreasoft.modules.stringUtil');


koreasoft.modules.stringUtil = function() { 
	///////////	private 

	
	///////////	public 
	function _getUUID() { // UUID v4 generator in JavaScript (RFC4122 compliant)
  		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    	var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 3 | 8);
    	return v.toString(16);
  		});
	}


	// 특권 메서드가 들어있는 객체를 반환 
	return {		
		getUUID : _getUUID
	}; 
}(); 


 