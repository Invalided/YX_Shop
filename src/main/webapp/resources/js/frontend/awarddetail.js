$(function() {
	// 从地址栏的URL里获取productId
	var awardId = getQueryString('awardId');
	// 获取奖品信息的URL
	var awardUrl = '/o2o/frontend/listawarddetailpageinfo?awardId='
			+ awardId;
	// 访问后台获取该奖品的信息并渲染
	$.getJSON(awardUrl, function(data) {
		if (data.success) {
			// 获取奖品信息
			var award = data.award;
			// 给奖品信息相关HTML控件赋值

			// 奖品缩略图
			$('#award-img').attr('src', award.awardImg);
			// 奖品更新时间
			$('#award-time').text(new Date(award.lastEditTime).Format("yyyy-MM-dd"));
			// 奖品名称
			$('#award-name').text(award.awardName);
			// 奖品简介
			$('#award-desc').text(award.awardDesc);
			// 奖品积分
			$('#award-point').text("积分 "+award.point);
		}
	});
	// 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
