$(document).ready(function(e) {
	$(".select3").uedSelect({
		width : 152
	});
});
window.onload = function () {
    bind(ele("selAll"),"click",function(){
        handleSelectAll("tid", this.checked);
    });
    bind(ele("delTask"),"click",function () {
        handleDelete("tid","pages/back/task/TaskServletBack/rm?");
    });
};