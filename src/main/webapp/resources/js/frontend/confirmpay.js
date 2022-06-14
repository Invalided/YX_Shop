$(function() {
	var orderurl = '/o2o/shopadmin/adduserproductmap';
	let total = localStorage.getItem("total");
	$("#price").text(total);
	$(document).on('click', '.order', function() {
		$.showPreloader('支付中');
		var userinfo = JSON.parse(localStorage.getItem("userinfo"));
		if (userinfo == undefined) {
			$.toast('获取用户信息失败');
		}
		var shopCart = JSON.parse(localStorage.getItem('shopCart'));
		var userMap = JSON.parse(localStorage.getItem('countMap'));
		var uid = userinfo[0].uid;
		var userTemp = {
			uid: uid,
			shopCart: shopCart,
			userMap: [...userMap]
		};
		var orders = [];
		orders.push(userTemp);
		localStorage.setItem('order', JSON.stringify(orders));
		console.log(new Map(userMap));
		// 向后端写入数据时需要获得商品数量
		var countMap = new Map(userMap);
		var submit = [];
		// js 中map 对象遍历方法
		countMap.forEach(function(value, key) {
			console.log(key, value);
			submit.push({
				pid: key,
				nums: value
			});
		});
		console.log(submit);
		var formData = new FormData();
		formData.append('uid', uid);
		formData.append('products', JSON.stringify(submit));
		$.ajax({
			url: orderurl,
			type: 'POST',
			data: formData,
			contentType: false,
			processData: false,
			cache: false,
			success: function(data) {
				if (data.success) {
					 // 清除购物车信息
					 localStorage.removeItem('shopCart');
					 localStorage.removeItem('countMap');
					 // 消除uData缓存数据
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
					 }, 3000);
					 window.location.href = 'paysuccess.html';				
				} else {
					$.toast('支付失败！' + data.errMsg);
				}
				
			}
		});

		// 清除购物车信息
		// localStorage.removeItem('shopCart');
		// localStorage.removeItem('countMap');
		// //消除uData缓存数据
		// var uData = JSON.parse(localStorage.getItem('uData'));
		// if (uData != null || uData != undefined) {
		// for (var u in uData) {
		// if (uData[u].uid == uid) {
		// uData.splice(u, 1);
		// }
		// }
		// console.log("uData", uData);
		// localStorage.setItem('uData', JSON.stringify(uData));
		// }
		// console.log(orders);
		// setTimeout(function() {
		// $.hidePreloader();
		// }, 3000);
		// window.location.href = 'paysuccess.html';
	})
});
