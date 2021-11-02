$(function(){
	var _adminSetDirList = function(){
		var _init = function(){
			$.fn.selectpicker.Constructor.DEFAULTS.noneResultsText = "검색결과가 없습니다... {0}";
			
			$("#dongSelect").selectpicker();
			$("#buildSelect").selectpicker();
			
			$(".delBtn").on("click", function(){
				
				if(confirm("삭제하시겠습니까?")){
					var _obj = {};
					_obj.admin_id = $(this).parents('tr').children().find("input[name=admin_id]").val();
					var _url = "/admin/set/dir/delProc";
					_doAjax(_url, _obj, _delCallBack) 
				}
				
			});
			
			$(".modBtn").on("click", function(){
				$(".modal-title").text("관리자 수정");
				$("#modalForm").find("input[name=modal_type]").val(2);
				var _adminId = $(this).parents('tr').children().find("input[name=admin_id]").val();
				var _adminGrade = $(this).parents('tr').children().find("input[name=admin_grade]").val();
				var _buildSeq = $(this).parents('tr').children().find("input[name=build_seq]").val();
				var _dongSeq = $(this).parents('tr').children().find("input[name=dong_seq]").val();
				var _adminName = $(this).parents('tr').children().find("input[name=admin_name]").val();
				$("#modalForm").find("input[name=admin_id]").attr("readonly",true);
				
				$("#modalForm").find("input[name=admin_pwd]").hide();
				$("#modalForm").find("input[name=admin_pwd2]").hide();
				
				$("#modalForm").find("input[name=admin_id]").val(_adminId);
				$("#modalForm").find("input[name=admin_name]").val(_adminName);
				$("#adminGradeSelect").selectpicker("val", _adminGrade);
								
				if(_adminGrade == 3){
					$("#dongTarget").show();
					$("#buildTarget").show();
					$("#dongSelect").selectpicker("val", _dongSeq);
					
					var _obj = {};
					_obj.dong_seq = _dongSeq;
					var _url = "/admin/set/dir/getBuild";
					
					_doAjax(_url, _obj, _makeBuild,_buildSeq);
						
				}else{
					$("#myModal").modal("show");
				}				
			});
			
			$("#adminAddBtn").on("click", function(){
				$(".modal-title").text("관리자 추가");
				$("#modalForm").find("input[name=admin_pwd]").show();
				$("#modalForm").find("input[name=admin_pwd2]").show();
				$("#adminGradeSelect").selectpicker("val", null);
				$("#dongSelect").selectpicker("val", null);
				$("#buildSelect").selectpicker("val", null);
				$("#modalForm").find("input[name=admin_id]").val("");
				$("#modalForm").find("input[name=admin_name]").val("");
				$("#modalForm").find("input[name=admin_pwd]").val("");
				$("#modalForm").find("input[name=admin_pwd2]").val("");
				//	modal_type = 1 관리자추가
				$("#modalForm").find("input[name=modal_type]").val(1);
				$("#myModal").modal("show");
				
			});
			
			//	저장 이벤트
			$("#modalSaveBtn").on("click", function(){
				if(confirm("저장하시겠습니까?")){
					var _modalType = $("#modalForm").find("input[name=modal_type]").val();
					
					var _adminId = $("#modalForm").find("input[name=admin_id]").val();
					var _adminPwd = $("#modalForm").find("input[name=admin_pwd]").val();
					var _adminPwd2 = $("#modalForm").find("input[name=admin_pwd2]").val();
					var _adminGrade = $("#adminGradeSelect").val();
					var _buildSeq = $("#buildSelect").val();
					var _adminName = $("#modalForm").find("input[name=admin_name]").val();
					
					var _obj = {};
					_obj.admin_id = _adminId;
					_obj.admin_pwd = _adminPwd;
					_obj.admin_grade = _adminGrade;
					_obj.type = _modalType;
					_obj.build_seq = _buildSeq;
					_obj.admin_name = _adminName;
										
					if(_modalType == 1 || _modalType == "1"){
						//	저장
						if(!koreasoft.modules.regex.isId(_adminId)){
							alert("아이디는 영문자로 시작하며 숫자포함 6에서 20자리입니다");
							return;
						}
						
						if(!koreasoft.modules.regex.isPassword(_adminPwd)){
							alert("비밀번호는 영문 대소문자로 시작하며, 숫자포함 6에서 20자리입니다");
							return;
						}
						
						if(_adminPwd != _adminPwd2){
							alert("비밀번호가 일치 하지 않습니다. 비밀번호를 확인해주시기 바랍니다.");
							return;
						}
						
						if(_adminGrade == ""){
							alert("권한을 선택해주세요");
							return;
						}	
						
						if(_adminGrade == 3){
							if(_buildSeq == ""){
								alert("건물을 선택해주세요");
								return;
							}
						}
						
						if(_adminName.trim() == ""){
							alert("관리자 이름을 작성해주세요");
							return;
						}
												
						var _url = "/admin/set/dir/saveProc";
						_doAjax(_url, _obj, _saveCallBack);
												
					}else{
						//	수정
						if(_adminName.trim() == ""){
							alert("관리자 이름을 작성해주세요");
							return;
						}
						
						if(_adminGrade == ""){
							alert("권한을 선택해주세요");
							return;
						}	
						
						if(_adminGrade == 3){
							if(_buildSeq == ""){
								alert("건물을 선택해주세요");
								return;
							}
						}
						
						var _url = "/admin/set/dir/modProc";
						_doAjax(_url, _obj, _saveCallBack);
						
					}
					
				}
			});
			
			//	관리자 추가 컬백
			function _saveCallBack(res){
				if(res.result == false || res.result == 'false'){
					alert(res.msg);
					return;
				}else{
					alert(res.msg);
					window.location.reload();
				}
			}
			
			//	모달 취소 이벤트
			$("#modalCancleBtn").on("click", function(){
				$("#modalForm").find("input[name=modal_type]").val("");
				$("#modalForm").find("input[name=admin_pwd]").hide();
				$("#modalForm").find("input[name=admin_pwd2]").hide();
				$("#dongTarget").hide();				
				$("#buildTarget").hide();
				$("#adminGradeSelect").selectpicker("val", null);
				$("#dongSelect").selectpicker("val", null);
				$("#buildSelect").selectpicker("val", null);
				$("#modalForm").find("input[name=admin_id]").attr("readonly",false);
				$("#modalForm").find("input[name=admin_id]").val("");
				$("#modalForm").find("input[name=admin_pwd]").val("");
				$("#modalForm").find("input[name=admin_pwd2]").val("");
				
				$("#myModal").modal("hide");
			});
			
			//	권한 선택 이벤트
			$("#adminGradeSelect").on("change", function(){
				var _v = $(this).val();
				
				if(_v == '3' || _v == 3){
					$("#dongTarget").show();				
					$("#buildTarget").show();
				}else{
					$("#dongTarget").hide();				
					$("#buildTarget").hide();	
				}
			});
			
			//	동 선택 이벤트
			$("#dongSelect").on("change", function(){
				var _url = "/admin/set/dir/getBuild";
				var _obj = {};
				_obj.dong_seq = $("#dongSelect").val();
				
				_doAjax(_url, _obj, _makeBuild, null);
			});
			
		}
		
		function _makeBuild(res, param){
			if(res.result == false || res.result == 'false'){
				alert(res.msg);
				return;
			}else{
				var _target = $("#buildSelect");
				$(_target).children().remove()
				var _list = res.buildList;
				for (var i = 0; i < _list.length; i++) {
					var _e = document.createElement("option")
					_e.value = _list[i].build_seq;
					_e.innerText = _list[i].build_name;
					$(_target).append(_e);
				}
				$(_target).selectpicker("refresh");
				
				if(param != null){
					$("#buildSelect").selectpicker("val", param);
					$("#myModal").modal("show");
				}
			}
		}
		
		function _delCallBack(res){
			if(res.result == false || res.result == 'false'){
				alert(res.msg);
				return;
			}else{
				alert(res.msg);
				window.location.reload();
			}
		}
		
		function _doAjax(url, data, callback, param){
			
			$.ajax({
				type: "POST",
				url: url,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				beforeSend: function(xhr) {
					xhr.setRequestHeader(_csrfHeader, _csrfToken);
					$("#l-wrapper").addClass("on");
				},
				success: function(res) {
					
					callback(res, param);	
				},
				error: function(e) {
					alert("저장에 실패하였습니다 관리자에게 문의 바랍니다");
					console.log("error : " + e.toString())
				},
				complete : function(e){
					$("#l-wrapper").removeClass("on");
			}	
			});
		}
				
		return {
			init : _init
		}
	}
	
	new _adminSetDirList().init();
})