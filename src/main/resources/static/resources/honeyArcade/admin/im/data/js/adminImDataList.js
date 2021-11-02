$(function(){
	var _adminImDataList = function(){
		var _init = function(){
			//	점포 연별 이벤트
			_changeDate("store" ,$("#storeDateType").val());
			$("#storeDateType").on("change", function(){
				_changeDate("store", $(this).val());
			});
			//	점포 데이터 연도 선택
			$("#storeYearPicker").datePicker({
				 format: 'YYYY',
				 language: 'ko',
				 hide : function(e){
					var _v = $("#storeYear").val();
					$("#storeYearTarget").val(_v);
				}
			});
			//	점포 데이터 월 선택
			$("#storeMonthPicker").datePicker({
				format:'YYYY-MM',
				language: 'ko',
				hide: function (e) { 
					var _v = $("#storeMonth").val();
					var _year  = _v.substring(0, _v.indexOf("-")); 
				    var _month = _v.substring(_v.indexOf("-")+1);
					$("#storeMonthTarget").val(_month);
					$("#storeYearTarget").val(_year);
				 },
			});
			//	점포 데이터 조회 버튼
			$("#storeSearchBtn").on("click", function(){
				$("#storeSearchForm").submit();
			});
			//	쿠폰 연별 이벤트
			_changeDate("coupon" ,$("#couponDateType").val());
			$("#couponDateType").on("change", function(){
				_changeDate("coupon", $(this).val());
			});
			//	쿠폰 데이터 연도 선택
			$("#couponYearPicker").datePicker({
				 format: 'YYYY',
				 language: 'ko',
				 hide : function(e){
					var _v = $("#couponYear").val();
					$("#couponYearTarget").val(_v);
				}
			});
			//	쿠폰 데이터 월 선택
			$("#couopnMonthPicker").datePicker({
				format:'YYYY-MM',
				language: 'ko',
				hide: function (e) { 
					var _v = $("#couponMonth").val();
					var _year  = _v.substring(0, _v.indexOf("-")); 
				    var _month = _v.substring(_v.indexOf("-")+1);
					$("#couponMonthTarget").val(_month);
					$("#couponYearTarget").val(_year);
				 },
			});
			$("#couponSearchBtn").on("click", function(){
				$("#couponSearchForm").submit();
			})
		}
		
		/**
		 * 월별 년별 체인지 이벤트
		 */
		function _changeDate(evtType, dateType){
			var _year = null;
			var _month = null;
			if(evtType == "store"){
				_year = $("#storeYearPicker");
				_month = $("#storeMonthPicker");
			}else{
				_year =  $("#couponYearPicker");
				_month = $("#couopnMonthPicker")
			}
			
			if(dateType == 1){
				$(_year).show();
				$(_month).hide();	
			}else{
				$(_year).hide();
				$(_month).show();
			}
		}
		
		return{
			init : _init
		}
	}
	
	new _adminImDataList().init();
})