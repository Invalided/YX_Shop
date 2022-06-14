$(function() {
	/**
	 * 根据用户id获取用户的订单信息
	 */
	 //获取uid
	 var uid = JSON.parse(localStorage.getItem("userinfo"))[0].uid;
	 //获取用户名
	 var uname = JSON.parse(localStorage.getItem("userinfo"))[0].uname;
	 //设置pageIndex
	 var pageIndex = 1;
	 //设置pageSize(max)
	 var pageSize = 100;
	 //后台接口url
	 var ordersUrl = '/o2o/frontend/listuserproductmapsbycustomer?pageIndex='+pageIndex+'&pageSize='+pageSize+'&uid='+uid+'&uname='+uname; 
	 var orderList = '';
	 //获取后台数据
	 $.getJSON(ordersUrl,function(data){
	 	if(data.success){
	 		//获取返回的用户信息映射列表
	 		var orders = data.userProductMapList;
	 		orders.map(function(item,index){
	 			orderList += ''+'<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media">'
	 			+'<img src="'+item.product.imgAddr+'"width="55" height="55"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'
	 			+item.product.productName+'</div></div><div class="item-title-row price"><div class="item-subtitle">￥<span class="price">'
	 			+(item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice)+'</span></div></div><div class="item-title-row"><div class="item-subtitle count">共<span class="count">'
	 			+item.nums+'</span>件商品,实付款￥<span class="countprice">'
	 			+((item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice)*item.nums)+'</span>元</div></div></div></li></ul></div></div><div class="card-footer"><span>'
	 			+new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")+'</span><span>交易完成</span></div></div>';
	 		});
	 		$('#tab').html(orderList);
	 	}else{
	 		$.toast(data.errMsg);
	 		window.location.href = '/o2o/local/login';
	 	}
	 });
//	var uid = JSON.parse(localStorage.getItem("userinfo"))[0].uid;
//	var ordersUrl = '/o2o/frontend/listuserproductmapsbycustomer?pageIndex=0&pageSize=30&uid='+uid;
//
//	getorderlist();
//
//	function getorderlist() {
//		$.ajax({
//			url: ordersUrl,
//			type: "get",
//			dataType: "json",
//			success: function(data) {
//				if (data.success) {
//					handleList(data.userProductMapList);
//				} else {
//					$.toast(data.errMsg);
//				}
//			}
//		});
//	}
//
//	function handleList(data) {
//		// var html = '';
//		// data.map(function(item, index) {
//		// 	html += '<div class="row row-shop"><div class="col-40">' +
//		// 		item.shopName + '</div><div class="col-40">' +
//		// 		shopStatus(item.enableStatus) +
//		// 		'</div><div class="col-20">' +
//		// 		goShop(item.enableStatus, item.shopId) + '</div></div>';
//
//		// });
//		// $('.shop-wrap').html(html);
	 
//		//var source ='{"userProductMapList":[{"userProductId":7,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":2,"productName":"测试2","productDesc":null,"imgAddr":"test2","normalPrice":null,"promotionPrice":null,"priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":1,"shopName":"正式的店铺","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1},{"userProductId":5,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":1,"productName":"正式的商品2","productDesc":null,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\1\\\\2020041515044011287.png","normalPrice":"5","promotionPrice":"2.5","priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":1,"shopName":"正式的店铺","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1},{"userProductId":8,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":8,"productName":"Java从入门到精通","productDesc":null,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\23\\\\2020060311342034249.jpg","normalPrice":"55","promotionPrice":"49","priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":23,"shopName":"新的店铺1","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1},{"userProductId":6,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":1,"productName":"正式的商品2","productDesc":null,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\1\\\\2020041515044011287.png","normalPrice":"5","promotionPrice":"2.5","priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":1,"shopName":"正式的店铺","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1}],"success":true,"count":4}';
//		var source = '{"userProductMapList": [{"userProductId": 8,"createTime": 1613563478000,"point": null,"user": {"userId": 3,"name": "用户1","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"product": {"productId": 8,"productName": "Java从入门到精通","productDesc": null,"imgAddr": "\\upload\\item\\shop\\23\\2020060311342034249.jpg","normalPrice": "55","promotionPrice": "49","priority": null,"point": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"productImgList": null,"productCategory": null,"shop": null},"shop": {"shopId": 23,"shopName": "新的店铺1","shopDesc": null,"shopAddr": null,"phone": null,"shopImg": null,"priority": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"advice": null,"area": null,"owner": null,"shopCategory": null},"operator": {"userId": 1,"name": "半夏","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"nums": 1},{"userProductId": 6,"createTime": 1613563478000,"point": null,"user": {"userId": 3,"name": "用户1","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"product": {"productId": 1,"productName": "正式的商品2","productDesc": null,"imgAddr": "\\upload\\item\\shop\\1\\2020041515044011287.png","normalPrice": "5","promotionPrice": "2.5","priority": null,"point": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"productImgList": null,"productCategory": null,"shop": null},"shop": {"shopId": 1,"shopName": "二手交易店","shopDesc": null,"shopAddr": null,"phone": null,"shopImg": null,"priority": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"advice": null,"area": null,"owner": null,"shopCategory": null},"operator": {"userId": 1,"name": "半夏","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"nums": 1},{"userProductId": 5,"createTime": 1613563478000,"point": null,"user": {"userId": 3,"name": "用户1","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"product": {"productId": 1,"productName": "正式的商品2","productDesc": null,"imgAddr": "\\upload\\item\\shop\\1\\2020041515044011287.png","normalPrice": "5","promotionPrice": "2.5","priority": null,"point": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"productImgList": null,"productCategory": null,"shop": null},"shop": {"shopId": 1,"shopName": "二手交易店","shopDesc": null,"shopAddr": null,"phone": null,"shopImg": null,"priority": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"advice": null,"area": null,"owner": null,"shopCategory": null},"operator": {"userId": 1,"name": "半夏","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"nums": 1},{"userProductId": 7,"createTime": 1613563478000,"point": null,"user": {"userId": 3,"name": "用户1","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"product": {"productId": 2,"productName": "测试2","productDesc": null,"imgAddr": "test2","normalPrice": null,"promotionPrice": null,"priority": null,"point": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"productImgList": null,"productCategory": null,"shop": null},"shop": {"shopId": 1,"shopName": "二手交易店","shopDesc": null,"shopAddr": null,"phone": null,"shopImg": null,"priority": null,"createTime": null,"lastEditTime": null,"enableStatus": null,"advice": null,"area": null,"owner": null,"shopCategory": null},"operator": {"userId": 1,"name": "半夏","profileImg": null,"email": null,"gender": null,"enableStatus": null,"userType": null,"createTime": null,"lastEditTime": null},"nums": 1}],"success": true,"count": 4}';
//		var orders = eval('(' + source + ')').userProductMapList;
//		var orderList = '';
//		orders.map(function(item, index) {
//			console.log(item);
//			orderList += '' +
//				'<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media">' +
//				'<img src="' + item.product.imgAddr +
//				'"width="55" height="55"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">' +
//				item.product.productName +
//				'</div></div><div class="item-title-row price"><div class="item-subtitle">￥<span class="price">' +
//				(item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice) +
//				'</span></div></div><div class="item-title-row"><div class="item-subtitle count">共<span class="count">' +
//				item.nums + '</span>件商品,实付款￥<span class="countprice">' +
//				((item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice) * item.nums) +
//				'</span>元</div></div></div></li></ul></div></div><div class="card-footer"><span>' +
//				new Date(item.createTime).Format("yyyy-MM-dd") + '</span><span>交易完成</span></div></div>';
//		});
//		$('#tab').html(orderList);
//	}


	//使用本地数据测试
	// var source ='{"userProductMapList":[{"userProductId":7,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":2,"productName":"测试2","productDesc":null,"imgAddr":"test2","normalPrice":null,"promotionPrice":null,"priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":1,"shopName":"正式的店铺","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1},{"userProductId":5,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":1,"productName":"正式的商品2","productDesc":null,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\1\\\\2020041515044011287.png","normalPrice":"5","promotionPrice":"2.5","priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":1,"shopName":"正式的店铺","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1},{"userProductId":8,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":8,"productName":"Java从入门到精通","productDesc":null,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\23\\\\2020060311342034249.jpg","normalPrice":"55","promotionPrice":"49","priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":23,"shopName":"新的店铺1","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1},{"userProductId":6,"createTime":1613563478000,"point":null,"user":{"userId":3,"name":"用户1","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"product":{"productId":1,"productName":"正式的商品2","productDesc":null,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\1\\\\2020041515044011287.png","normalPrice":"5","promotionPrice":"2.5","priority":null,"point":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"productImgList":null,"productCategory":null,"shop":null},"shop":{"shopId":1,"shopName":"正式的店铺","shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null},"operator":{"userId":1,"name":"半夏","profileImg":null,"email":null,"gender":null,"enableStatus":null,"userType":null,"createTime":null,"lastEditTime":null},"nums":1}],"success":true,"count":4}';
	// var orders = eval('(' + source + ')').userProductMapList;
	// var orderList = '';
	// orders.map(function(item, index) {
	// 	console.log(item);
	// 	orderList += '' +
	// 		'<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media">' +
	// 		'<img src="' + item.product.imgAddr +
	// 		'"width="55" height="55"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">' +
	// 		item.product.productName +
	// 		'</div></div><div class="item-title-row price"><div class="item-subtitle">￥<span class="price">' +
	// 		(item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice) +
	// 		'</span></div></div><div class="item-title-row"><div class="item-subtitle count">共<span class="count">' +
	// 		item.nums + '</span>件商品,实付款￥<span class="countprice">' +
	// 		((item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice) * item.nums) +
	// 		'</span>元</div></div></div></li></ul></div></div><div class="card-footer"><span>' +
	// 		new Date(item.createTime).Format("yyyy-MM-dd") + '</span><span>交易完成</span></div></div>';
	// });
	// $('#tab').html(orderList);


	// 初始化页面
	$.init();
});
