$(function(){
	var userinfo = JSON.parse(localStorage.getItem("userinfo"));
	if(userinfo == null || userinfo == undefined){
		$.toast('未登录');
		window.location.href = '/o2o/local/login';
	}
});