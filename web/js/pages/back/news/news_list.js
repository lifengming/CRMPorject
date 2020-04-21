var jsUrl = "" ;
$(document).ready(function() {
	$(".click").click(function() {
        var eleId = this.id;
		$(".tip").fadeIn(200,function(){
			if (eleId == "liButAdd") {	// 如果是由
				jsUrl = "pages/back/news/NewsServletBack/addPre" ;
                document.getElementById("pMsg").innerHTML = "是否添加新公告？";
            }

            if (eleId == "liButRm") {//删除处理
                var nid = "";//保存所有要删除的ID编号
                var nidEle = document.all("nid");
                if (nidEle.length!=undefined) {//nidEle是一个数组
                    for (var x = 0; x < nidEle.length; x++) {
                        if (nidEle[x].checked) {
                            nid += nidEle[x].value+"-";
                        }
                    }
                }else {//nidEle是单个元素
                    if (nidEle.checked) {
                        nid = nidEle.value+"-";
                    }
                }
                if (nid!= null) {
                    jsUrl = "pages/back/news/NewsServletBack/rm?ids="+nid;
                    document.getElementById("pMsg").innerHTML = "是否确认对信息的删除？";
                } else {
                    jsUrl = "";
                    document.getElementById("pMsg").innerHTML = "您还未选择任何选项，请重新选择 ";
                }
            }

            if (eleId == "liButEdit") {//现在执行的是修改处理操作
                var nidEle = document.all("nid");
                var count = 0;
                var nid = 0;
                if (nidEle.length!=undefined) {//nidEle是一个数组
                    for (var x = 0; x < nidEle.length; x++) {
                        if (nidEle[x].checked) {
                            nid = nidEle[x].value;
                            count++;
                        }
                    }
                }else {//nidEle是单个元素
                    if (nidEle.checked) {
                        nid = nidEle.value;
                        count++;
                    }
                }
                if (count == 1) {
                    jsUrl = "pages/back/news/NewsServletBack/editPre?news.nid="+nid;
                    document.getElementById("pMsg").innerHTML = "是否确认对信息的修改？";
                } else {
                    jsUrl = "";
                    document.getElementById("pMsg").innerHTML = " 修改选择有误，请重新选择 ";
                }
            }


		});
	});

	$(".tiptop a").click(function() {
		$(".tip").fadeOut(200);
	});

	$(".sure").click(function() {
		$(".tip").fadeOut(100,function() {
            if (jsUrl != null) {
                window.location = jsUrl ;
            }
		});
	});

	$(".cancel").click(function() {
		$(".tip").fadeOut(100);
	});
	$('.tablelist tbody tr:odd').addClass('odd');
});