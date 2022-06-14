var userinfo;
$(function() {
	userinfo = JSON.parse(localStorage.getItem("userinfo"));
	localStorage.getItem("countMap");
	$('#uname').text(userinfo[0].uname);
});
$('#logout').click(function() {
	var flag = true;
	if (userinfo == undefined) {
		$.toast('获取用户信息失败');
	}
	
	var uData = JSON.parse(localStorage.getItem('uData'));
	if(uData == undefined || uData == null){
		uData = [];
	}
	var uid = userinfo[0].uid;
	var shopCart = JSON.parse(localStorage.getItem('shopCart'));
	//购物车不为空
	if (shopCart != null) {
		var userMap = JSON.parse(localStorage.getItem('countMap'));
		for(var u in uData){
			if(uid == uData[u].uid){
				//修改对应的用户存储信息
				uData[u].shopCart = shopCart;
				uData[u].userMap = userMap;
				flag = false;
			}
		}
		if(flag){
			//记录用户信息
			var userTemp = {
				uid: userinfo[0].uid,
				shopCart: shopCart,
				userMap: [...userMap]
			};
			uData.push(userTemp);			
		}
		localStorage.setItem('uData', JSON.stringify(uData));
		//清除购物车信息
		localStorage.removeItem('shopCart');
		localStorage.removeItem('countMap');
	}
	//清除用户信息
	localStorage.removeItem('userinfo');
	//跳转到登录页
	window.location.href = '/o2o前端/html/local/login.html';
});
