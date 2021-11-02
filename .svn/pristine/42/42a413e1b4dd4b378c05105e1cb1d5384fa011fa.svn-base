$(function(){
	$(".myModal").on("click", function(e){
		var _email = $(this).attr("data-email");
		var _name = $(this).attr("data-name");
		var _id = $(this).attr("data-id");
		
		$("#userInfo").find("input[name=pop_user_id]").val("");
		$("#userInfo").find("input[name=pop_user_name]").val("");
		$("#userInfo").find("input[name=pop_user_email]").val("");
		
		$("#userInfo").find("input[name=pop_user_id]").val(_id);
		$("#userInfo").find("input[name=pop_user_name]").val(_name);
		$("#userInfo").find("input[name=pop_user_email]").val(_email);
		
		$("#userInfo").modal();
	});
	
	
});