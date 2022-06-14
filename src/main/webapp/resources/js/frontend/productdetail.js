$(function() {
	 // 从地址栏的URL里获取productId
	 var productId = getQueryString('productId');
	 // 获取商品信息的URL
	 var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
	 		+ productId;
	 // 访问后台获取该商品的信息并渲染
	 var product;
	 $.getJSON(productUrl, function(data) {
	 	if (data.success) {
	 		// 获取商品信息
	 		product = data.product;
	 		// 给商品信息相关HTML控件赋值

	 		// 商品缩略图
	 		$('#product-img').attr('src', product.imgAddr);
	 		// 商品更新时间
	 		$('#product-time').text(
	 				new Date(product.lastEditTime).Format("yyyy-MM-dd"));
	 		if (product.point != undefined) {
	 			$('#product-point').text('购买可得' + product.point + '积分');
	 		}
	 		// 商品名称
	 		$('#product-name').text(product.productName);
	 		// 商品简介
	 		$('#product-desc').text(product.productDesc);
	 		// 商品价格展示逻辑，主要判断原价现价是否为空 ，所有都为空则不显示价格栏目
	 		if (product.normalPrice != undefined
	 				&& product.promotionPrice != undefined) {
	 			// 如果现价和原价都不为空则都展示，并且给原价加个删除符号
	 			$('#price').show();
	 			$('#normalPrice').html(
	 					'<del>' + '￥' + product.normalPrice + '</del>');
	 			$('#promotionPrice').text('￥' + product.promotionPrice);
	 		} else if (product.normalPrice != undefined
	 				&& product.promotionPrice == undefined) {
	 			// 如果原价不为空而现价为空则只展示原价
	 			$('#price').show();
	 			$('#promotionPrice').text('￥' + product.normalPrice);
	 		} else if (product.normalPrice == undefined
	 				&& product.promotionPrice != undefined) {
	 			// 如果现价不为空而原价为空则只展示现价
	 			$('#promotionPrice').text('￥' + product.promotionPrice);
	 		}
	 		var imgListHtml = '';
	 		// 遍历商品详情图列表，并生成批量img标签
	 		product.productImgList.map(function(item, index) {
	 			imgListHtml += '<div> <img src="' + item.imgAddr
	 					+ '" width="100%" /></div>';
	 		});
	 		$('#imgList').html(imgListHtml);
	 	}
	 });
	// 点击后添加购物车 获取所得数据放入localStorage
	 // { sid: tempid, spath: shimg, sname: sname, price: shprice, amount: 1,
		// subtotal: shprice}
	 // 页面的初始数据
	 // {
		// pid:'商品id',imgpath:'缩略图地址',pname:'商品名称',price:'商品价格',mount:'数量',sub:'价格'}
	 // product-name
	 // product-img
	 // normalPrice
	 // promotionPrice
	
	/**
	 * var map = new Map(); map.set(1, 1); map.set(2, 2); map.set(3, 3); // map ->
	 * array var arr = Array.from(map); // 二维数组 console.log(arr.flat()); //
	 * 压平(成为一维数组) // (6) [1, "111", 2, "222", 3, "333"] // 或：使用...rest运算符 var
	 * temp = [...map]; console.log(temp);
	 */
	 
	 
	// var products =
	// '{"product":{"productId":5,"productName":"雀巢咖啡","productDesc":"测试Desc1","imgAddr":"\\\\upload\\\\item\\\\shop\\\\1\\\\2020041523582281080.jpg","normalPrice":"15","promotionPrice":"10","priority":1,"createTime":1586879047000,"lastEditTime":1590820148000,"enableStatus":1,"productImgList":[{"productImgId":5,"imgAddr":"\\\\upload\\\\item\\\\shop\\\\1\\\\2020041411594145114.jpg","imgDesc":null,"priority":1,"createTime":1586879047000,"productId":5}],"productCategory":{"productCategoryId":24,"shopId":null,"productCategoryName":null,"priority":null,"createTime":null},"shop":{"shopId":19,"shopName":null,"shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null}},"success":true}';
	// var testSet = new Set();
	// var test =
	// [{"product":{"productId":8,"productName":"Java从入门到精通","productDesc":"非常好的学习资料","imgAddr":"\\upload\\item\\shop\\23\\2020060311342034249.jpg","normalPrice":"55","promotionPrice":"49","priority":10,"createTime":1591155161000,"lastEditTime":1591155260000,"enableStatus":1,"productImgList":[{"productImgId":23,"imgAddr":"\\upload\\item\\shop\\23\\2020060311342089364.png","imgDesc":null,"priority":10,"createTime":1591155161000,"productId":8}],"productCategory":{"productCategoryId":19,"shopId":null,"productCategoryName":null,"priority":null,"createTime":null},"shop":{"shopId":23,"shopName":null,"shopDesc":null,"shopAddr":null,"phone":null,"shopImg":null,"priority":null,"createTime":null,"lastEditTime":null,"enableStatus":null,"advice":null,"area":null,"owner":null,"shopCategory":null}},"success":true}];
	// 使用eval方法容错率高 eval('(' + str + ')')
	// products = '{"abc":"123"}';
	// 判断localStorage是否有值 有则取出 追加 无则新建 shopCart
	// localStorage.setItem("data", JSON.stringify(test));
	// localStorage.removeItem("data");
	// let AData = JSON.parse(localStorage.getItem("data") || '[]');
	// console.log(AData);
	// var product = eval('(' + products + ')');
	var uproduct = [];
	$(document).on('click','.add_button', function () {
		console.log("product",product);
		var userinfo = JSON.parse(localStorage.getItem("userinfo"));
		if(userinfo == null || userinfo == undefined){
			window.location.href = '/o2o/local/login';
		}
		var shopCart = localStorage.getItem('shopCart');
		var countMap = localStorage.getItem('countMap');
		console.log(shopCart);
		
		// 获取商品映射信息
		if(countMap!=null || countMap!=undefined){
			// console.log(countMap);
			countMap = new Map(JSON.parse(countMap));
		}else{
			countMap = new Map();
		}
			// 判断商品是否存在
			var flag = true;
			// 获取购物车信息
			// 判断shopCart 是否为空
			if(shopCart != null){
				uproduct = JSON.parse(shopCart);
			}
			for(var i in uproduct){
				if(uproduct[i].productId == product.productId){
					let tempid = product.productId;
					countMap.set(tempid,countMap.get(tempid)+1);
					// uproducts.push(product);
					$.toast('已添加');
					// return;
					flag = false;
				}
				console.log(uproduct[i].productId);
			}
		if(flag){
			console.log(product);
			uproduct.push(product);
			countMap.set(product.productId,1);
			$.toast('添加成功');
			console.log([...countMap]);
		}
		localStorage.setItem("shopCart",JSON.stringify(uproduct));
		localStorage.setItem('countMap',JSON.stringify([...countMap]));
		
		
		// if(testSet.has(product)){
		// $.toast("已添加");
		// }else{
		// testSet.add(product);
		// console.log(testSet);
		// uproduct.push(product);
		// uproduct = Array.from(testSet);
		// $.toast("添加成功");
		// }
	});
	// 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
