// var userMap;
// var shopCart;
// var userinfo;
//  $(function() {
// 	userinfo = JSON.parse(localStorage.getItem('userinfo'));
// 	if(!(userinfo!=null&&userinfo.length>0)){
// 	$.toast('去登录');
// 	}
// 	console.log(userinfo[0].uid);
// 	// var unloads = document.getElementById("tab").value;		
// 	// // if(unloads == null || unloads == ""){
// 	// // 	alert("您确定要退出页面吗？");
// 	// 	//复制代码
// 	// 	//绑定beforeunload事件
// 	// 	$(window).bind('beforeunload',function(){
// 	// 	return '您输入的内容尚未保存，确定离开此页面吗？';
// 	// 	});
// 	// }
// 	//获取shopCart中的数据 不存在则弹出提示
// 	shopCart = JSON.parse(localStorage.getItem('shopCart'));
// 	//遍历填充数据
// 	var productList = '';
// 	//存放用户商品数量映射
// 	userMap = localStorage.getItem('countMap');
// 	if(userMap!=null || userMap!=undefined){
// 	//console.log(userMap);
// 	userMap = new Map(JSON.parse(userMap));
// 	console.log('userMap',userMap);
// 	}else{
// 	userMap = new Map();
// 	}
// 	//var userMap = new Map();
// 	if(shopCart!=null){
// 	shopCart.map(function(item,index){
// 		console.log("index",index);
// 		//console.log(item.productName);
// 		productList += ''+
// 		'<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media"><img src="'
// 		+item.product.imgAddr+'"width="80" height="80"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'
// 			+item.product.productName+'</div></div><div class="item-subtitle">￥<span class="price">'
// 			+(item.product.promotionPrice!=undefined?item.product.promotionPrice:item.product.normalPrice)+'</span></div><div class="item-title-row culculator"><span class="pid" hidden="hidden">'
// 			+item.product.productId+'</span><input class="min" name="" type="button" value="-" />'+
// 			'<input class="text_box" name="" type="number" value="'
// 			+(userMap.get(item.product.productId)!=undefined?userMap.get(item.product.productId):1)+'" placeholder="0" /><input class="add" name="" type="button" value="+" /></div></div></li></ul></div></div></div>';	

// 			// productList += ''+
// 			// '<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media"><img src="'
// 			// +item.imgAddr+'"width="80" height="80"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'
// 			// +item.productName+'</div></div><div class="item-subtitle">￥<span class="price">'
// 			// +(item.promotionPrice!=undefined?item.promotionPrice:item.normalPrice)+'</span></div><div class="item-title-row culculator"><span class="pid" hidden="hidden">'
// 			// +item.productId+'</span><input class="min" name="" type="button" value="-" />'+
// 			// '<input class="text_box" name="" type="number" value="'
// 			// +(userMap.get(item.productId)!=undefined?userMap.get(item.productId):1)+'" placeholder="0" /><input class="add" name="" type="button" value="+" /></div></div></li></ul></div></div></div>';						
// 			//初始化映射数据
// 			//userMap.set(item.product.productId,1);
// 			console.log(userMap.get(item.product.productId));
// 		});
// 		$('#tab').html(productList);
// 		localStorage.setItem('wr',true);
// 	}

// 	// $(".card").on('click','.')
// 	$(".add").click(function(e) {
// 		var t = $(this).parent().find('input[class*=text_box]');
// 		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
// 		if (t.val() == "" || undefined || null) {
// 			t.val(1);
// 		}
// 		t.val(parseInt(t.val()) + 1);
// 		getConunt(countKey,t.val());
// 		console.log('t',t.val());
// 		console.log('map',userMap);
// 		setTotal();
// 	})
// 	$(".min").click(function() {
// 		var t = $(this).parent().find('input[class*=text_box]');
// 		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
// 		if (t.val() == "" || undefined || null) {
// 			$.toast("好像出问题了");
// 			t.val(1);

// 		}
// 		t.val(parseInt(t.val()) - 1)
// 		if (parseInt(t.val()) < 1) {
// 			$.toast("不能再少啦!");
// 			t.val(1);
// 		}
// 		getConunt(countKey,t.val());
// 		setTotal();
// 	})
// 	$(".text_box").keyup(function() {
// 		var t = $(this).parent().find('input[class*=text_box]');
// 		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
// 		if (parseInt(t.val()) == "" || undefined || null || isNaN(t.val())) {
// 			t.val(1);
// 		}
// 		getConunt(countKey,t.val());
// 		setTotal();
// 	})

// 	function setTotal() {
// 		var s = 0;
// 		$("#tab div[class='card']").each(function() {
// 			var t = $(this).find('input[class*=text_box]').val();
// 			var p = $(this).find('span[class*=price]').text();
// 			if (parseInt(t) == "" || undefined || null || isNaN(t) || isNaN(parseInt(t))) {
// 				t = 1;
// 			}
// 			s += parseInt(t) * parseFloat(p);
// 		});
// 		$("#total").html(s.toFixed(2));
// 	}
// 	setTotal();

// 	function getConunt(k,v){
// 		userMap.set(k,parseInt(v));
// 	}
// 	localStorage.setItem('countMap',JSON.stringify(userMap));

// });

//   //支付 向后台发起请求 清空购物车
//   $(document).on('click','.create-actions', function () {
// 	  // $.toast(111);
// 	  $.showPreloader('支付中');
// 	  //获取参数
// 	  var uinfo = [];
// 	  //uinfo.push({uid:'1',uname:'tset'});
// 	  //localStorage.setItem('userinfo',JSON.stringify(uinfo));
// 	  //{uid,shopCart,countMap}
// 	  console.log(userMap);
// 	  var userTemp = {uid:userinfo[0].uid,shopCart:shopCart,userMap:[...userMap]};
// 	  var uData = [];
// 	  uData.push(userTemp);
// 	  localStorage.setItem('uData',JSON.stringify(uData));
// 	  //清除购物车信息
// 	  localStorage.removeItem('shopCart');
// 	  localStorage.removeItem('countMap');
// 	  localStorage.removeItem('wr');
// 	  flag = true;
// 	  console.log(uData);
// 	      setTimeout(function () {
// 	          $.hidePreloader();
// 	      }, 2000);
//   });



//  //用户离开页面 记录数据
//  $(window).bind('beforeunload', function(e) {//页面卸载，就是用户关闭页面、点击链接跳转到其他页面或者刷新页面都会执行
//  	alert('quit');
//  	console.log('11111');
// 	console.log([...userMap]);
// 	localStorage.setItem("countMap",JSON.stringify([...userMap]));
//   }); 


var userMap,count;
$(function() {
	var unloads = document.getElementById("tab").value;
	// if(unloads == null || unloads == ""){
	// 	alert("您确定要退出页面吗？");
	//复制代码
	//绑定beforeunload事件
	$(window).bind('beforeunload', function() {
		return '您输入的内容尚未保存，确定离开此页面吗？';
	});
	// }
	//获取shopCart中的数据 不存在则弹出提示
	var shopCart = JSON.parse(localStorage.getItem('shopCart'));
	//遍历填充数据
	var productList = '';
	//存放用户商品数量映射
	userMap = localStorage.getItem('countMap');
	if (userMap != null || userMap != undefined) {
		//console.log(userMap);
		userMap = new Map(JSON.parse(userMap));
	} else {
		userMap = new Map();
	}
	//var userMap = new Map();
	if (shopCart != null) {
		shopCart.map(function(item, index) {
			console.log("index", index);
			console.log(item.productName);
			productList += '' +
				'<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media"><img src="' +
				item.product.imgAddr +
				'"width="80" height="80"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">' +
				item.product.productName + '</div></div><div class="item-subtitle">￥<span class="price">' +
				(item.product.promotionPrice != undefined ? item.product.promotionPrice : item.product.normalPrice) +
				'</span></div><div class="item-title-row culculator"><span class="pid" hidden="hidden">' +
				item.product.productId + '</span><input class="min" name="" type="button" value="-" />' +
				'<input class="text_box" name="" type="number" value="' +
				(userMap.get(item.product.productId) != undefined ? userMap.get(item.product.productId) : 1) +
				'" placeholder="0" /><input class="add" name="" type="button" value="+" /></div></div></li></ul></div></div></div>';
			//初始化映射数据
			//userMap.set(item.product.productId,1);
		});
		$('#tab').html(productList);
	}


	// $(".card").on('click','.')
	$(".add").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
		if (t.val() == "" || undefined || null) {
			t.val(1);
		}
		t.val(parseInt(t.val()) + 1);
		getConunt(countKey, t.val());
		console.log('t', t.val());
		console.log('map', userMap);
		setTotal();
	})
	$(".min").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
		if (t.val() == "" || undefined || null) {
			$.toast("好像出问题了");
			t.val(1);

		}
		t.val(parseInt(t.val()) - 1)
		if (parseInt(t.val()) < 1) {
			$.toast("不能再少啦!");
			t.val(1);
		}
		getConunt(countKey, t.val());
		setTotal();
	})
	$(".text_box").keyup(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
		if (parseInt(t.val()) == "" || parseInt(t.val()) < 1 || undefined || null || isNaN(t.val())) {
			t.val(1);
		}
		getConunt(countKey, t.val());
		setTotal();
	})

	function setTotal() {
		var s = 0;
		$("#tab div[class='card']").each(function() {
			var t = $(this).find('input[class*=text_box]').val();
			var p = $(this).find('span[class*=price]').text();
			if (parseInt(t) == "" || undefined || null || isNaN(t) || isNaN(parseInt(t))) {
				t = 1;
			}
			s += parseInt(t) * parseFloat(p);
			count = true;
		});
		$("#total").html(s.toFixed(2));
		if (s == 0.00 || s == undefined || s == NaN) {
			//$.toast('请先添加商品');
			//$("#payment").attr('disabled', true);
			count = false;
		}
	}
	setTotal();

	function getConunt(k, v) {
		userMap.set(k, parseInt(v));
	}

	//支付向后台发起请求 清空购物车
	$(document).on('click', '.create-actions', function() {
		if(!count){
			$.toast('请先添加商品');
			return;
		}
		// $.toast(111);
		//$.showPreloader('支付中');
		//获取参数
		//var uinfo = [];
		//uinfo.push({uid:'1',uname:'tset'});
		// var userinfo = JSON.parse(localStorage.getItem("userinfo"));
		// if (userinfo == undefined) {
		// 	$.toast('获取用户信息失败');
		// }
		// var uid = userinfo[0].uid;
		// var userTemp = {
		// 	uid: uid,
		// 	shopCart: shopCart,
		// 	userMap: [...userMap]
		// };
		// var orders = [];
		// orders.push(userTemp);
		// localStorage.setItem('order', JSON.stringify(orders));

		// //清除购物车信息
		// localStorage.removeItem('shopCart');
		// localStorage.removeItem('countMap');
		// //消除uData缓存数据
		// var uData = JSON.parse(localStorage.getItem('uData'));
		// if (uData != null || uData != undefined) {
		// 	for (var u in uData) {
		// 		if (uData[u].uid == uid) {
		// 			uData.splice(u, 1);
		// 		}
		// 	}
		// 	console.log("uData", uData);
		// 	localStorage.setItem('uData', JSON.stringify(uData));
		// }
		// console.log(orders);
		// setTimeout(function() {
		// 	$.hidePreloader();
		// }, 2000);
		let total = $('#total').text();
		localStorage.setItem("total",total);
		window.location.href = '/o2o前端/html/frontend/confirmpay.html';
	});





})
//用户离开页面 记录数据
$(window).bind('beforeunload', function(e) { //页面卸载，就是用户关闭页面、点击链接跳转到其他页面或者刷新页面都会执行
	alert('quit');
	console.log('11111');
	console.log([...userMap]);
	let tempMap = localStorage.getItem('countMap');
	if (tempMap != null && tempMap != undefined) {
		localStorage.setItem("countMap", JSON.stringify([...userMap]));
	}
});
