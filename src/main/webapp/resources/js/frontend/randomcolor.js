function getColor(){
	var color = "rgba("+Math.floor(Math.random()*256)+","+Math.floor(Math.random()*256)+","+Math.floor(Math.random()*256)+",0.3)";
	return color;
}
$(function(){
	// var collection = $(".col-50");
	// console.log(collection[0]);
	// collection[0].css("background",getColor());
	// console.log(collection[1]);
	// for(var temp in collection){
	// 	collection[in].css("background",getColor());
	// }
	$(".col-50").each(function(){
		$(this).css("background",getColor());
	});
	
});