$(function(){
	var _adminImSalesList = function(){
		
						
		var _init = function(){
			
			_changeDate($("#dateType").val());
			
			$("#dateType").on("change", function(){
				_changeDate($(this).val());
			});
					
			$(".yearPicker").datePicker({
				 format: 'YYYY',
				 language: 'ko',
				 hide : function(e){
					var _v = $("#yearPicker").val();
					$("#yearTarget").val(_v);
				}
			});
			
			$(".monthPicker").datePicker({
				format:'YYYY-MM',
				language: 'ko',
				hide: function (e) { 
					var _v = $("#monthPicker").val();
					var _year  = _v.substring(0, _v.indexOf("-")); 
				    var _month = _v.substring(_v.indexOf("-")+1);
					$("#monthTarget").val(_month);
					$("#yearTarget").val(_year);
					
				 },
			})
			
			$("#searchBtn").on("click", function(){
				var _v = $("#dateType").val();
				if(_v == 1 || _v == "1"){
					$("#monthTarget").val("");
					if($("#yearTarget").val()==""){
						alert("연도를 선택해주세요");
						return;
					}
					
				}else{
					if($("#monthTarget").val()==""){
						alert("월을 선택해주세요");
						return;
					}
					
				}
				$("#searchForm").submit();
			})
		}
		
		function _changeDate(v){
			
			if(v == 1 || v == "1"){
				$(".yearPicker").show();
				$(".monthPicker").hide();
			}else{
				$(".yearPicker").hide();
				$(".monthPicker").show();					
			}	
		}
		
		return {
			init : _init
		}
	}
	
	
	new _adminImSalesList().init();
})