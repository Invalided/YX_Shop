var userMap,count;
 $(function() {
	var unloads = document.getElementById("tab").value;				
	// if(unloads == null || unloads == ""){
	// alert("您确定要退出页面吗？");
		// 复制代码
		// 绑定beforeunload事件
		$(window).bind('beforeunload',function(){
		return '您输入的内容尚未保存，确定离开此页面吗？';
		});
	// }
	// 获取shopCart中的数据 不存在则弹出提示
	var shopCart = JSON.parse(localStorage.getItem('shopCart'));
	// 遍历填充数据
	var productList = '';
	// 存放用户商品数量映射
	userMap = localStorage.getItem('countMap');
	if(userMap!=null || userMap!=undefined){
		// console.log(userMap);
		userMap = new Map(JSON.parse(userMap));
	}else{
		userMap = new Map();
	}
	// var userMap = new Map();
	if(shopCart!=null){
		shopCart.map(function(item,index){
			console.log("index",index);
			console.log(item.productName);
			productList += ''+
			'<div class="card"><div class="card-content"><div class="list-block media-list"><ul><li class="item-content"><div class="item-media"><img src="'
			+item.imgAddr+'"width="80" height="80"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'
			+item.productName+'</div></div><div class="item-subtitle">￥<span class="price">'
			+(item.promotionPrice!=undefined?item.promotionPrice:item.normalPrice)+'</span></div><div class="item-title-row culculator"><span class="pid" hidden="hidden">'
			+item.productId+'</span><input class="min" name="" type="button" value="-" /><input class="text_box" name="" type="number" value="'
			+(userMap.get(item.productId)!=undefined?userMap.get(item.productId):1)+'" placeholder="0" /><input class="add" name="" type="button" value="+" /></div></div></li></ul></div></div></div>';	
			// 初始化映射数据
			// userMap.set(item.product.productId,1);
		});
		$('#tab').html(productList);
	}
	
	// $(".card").on('click','.')
	$(".add").click(function(e) {
		var t = $(this).parent().find('input[class*=text_box]');
		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
		if (t.val() == "" || undefined || null) {
			t.val(1);
		}
		t.val(parseInt(t.val()) + 1);
		getConunt(countKey,t.val());
		console.log('t',t.val());
		console.log('map',userMap);
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
		getConunt(countKey,t.val());
		setTotal();
	})
	$(".text_box").keyup(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		var countKey = parseInt($(this).parent().find('span[class=pid]').text());
		if (parseInt(t.val()) == "" || undefined || null || isNaN(t.val())) {
			t.val(1);
		}
		getConunt(countKey,t.val());
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
	
	function getConunt(k,v){
		userMap.set(k,parseInt(v));
	}
})

  $(document).on('click','.create-actions', function () {
	  if(!count){
			$.toast('请先添加商品');
			return;
	  }
	  let total = $('#total').text();
	  localStorage.setItem("total",total);
	  window.location.href = '/o2o/frontend/confirmpay';
  });
 // 用户离开页面 记录数据
 $(window).bind('beforeunload', function(e) {// 页面卸载，就是用户关闭页面、点击链接跳转到其他页面或者刷新页面都会执行
 	alert('quit');
 	console.log('11111');
	console.log([...userMap]);
	localStorage.setItem("countMap",JSON.stringify([...userMap]));
 });