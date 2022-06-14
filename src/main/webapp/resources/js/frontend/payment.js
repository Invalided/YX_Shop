$(function(){
	$(document).on('click', '.order', function() {
		$.showPreloader('支付中');
		var userinfo = JSON.parse(localStorage.getItem("userinfo"));
		if (userinfo == undefined) {
			$.toast('获取用户信息失败');
		}
		var uid = userinfo[0].uid;
		var userTemp = {
			uid: uid,
			shopCart: shopCart,
			userMap: [...userMap]
		};
		var orders = [];
		orders.push(userTemp);
		localStorage.setItem('order', JSON.stringify(orders));
		
		//清除购物车信息
		localStorage.removeItem('shopCart');
		localStorage.removeItem('countMap');
		//消除uData缓存数据
		var uData = JSON.parse(localStorage.getItem('uData'));
		if (uData != null || uData != undefined) {
			for (var u in uData) {
				if (uData[u].uid == uid) {
					uData.splice(u, 1);
				}
			}
			console.log("uData", uData);
			localStorage.setItem('uData', JSON.stringify(uData));
		}
		console.log(orders);
		setTimeout(function() {
			$.hidePreloader();
		}, 2000);
		window.location.href = 'paysuccess.html';
	})
});