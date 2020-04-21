<%@ page pageEncoding="UTF-8"%>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%--<jsp:include page="split_page_bar_plugin_mvc.jsp"/>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path+"/";
%>
<%
	String url=basePath+(String) request.getAttribute("url");
	int currentPage=1;//当前所在页
 	int lineSize=5;//每页显示多少行

	String column=(String) request.getAttribute("column");
	String keyWord=(String) request.getAttribute("keyWord");
    String paramName = (String)request.getAttribute("paramName") ;
    String paramValue = (String)request.getAttribute("paramValue") ;
    String paramNameB = (String)request.getAttribute("paramNameB") ;
    String paramValueB = (String)request.getAttribute("paramValueB") ;
	int allRecorders=0;//定义总记录数变量
 	int pageSize=1;//定义总页数，假设只有一页
%>
 <%
 	//将用户传递的参数变为int型，赋值给currentPage变量！
     try{//整个操作之中唯一可能出现异常的情况就是没有cp参数，
         currentPage=(Integer)(request.getAttribute("currentPage"));
     }catch(Exception e){}
     try{//控制每页显示的数据行数，
         lineSize=(Integer)(request.getAttribute("lineSize"));
     }catch(Exception e){}
     try{//控制总计录数，
         allRecorders=(Integer)(request.getAttribute("allRecorders"));
     }catch(Exception e){}

 	//out.println(column+""+keyWord);
 %>

<%
	if(allRecorders>0){
 	 	pageSize=(allRecorders+lineSize-1)/lineSize;
 	 }	
%>
<div id="splitPageDiv" style="float:right">
 <ul class="pagination">
 
<%
	if(currentPage==1){//加入判断，如果到第一页，那么首页和上一页功能应该禁止！
%>
	<li class="disabled"><span>上一页</span></li>
	<li class="active"><span>1</span></li>
<%	
	}else{
%> 
    
    <li><a href="<%=url%>?cp=<%=currentPage-1%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>">上一页</a></li>
    <li ><a href="<%=url%>?cp=1&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>">1</a></li>
<%
}
%>

<%
    //记住，分页没有定势，依据总的页数变化而定，调整seed的大小！对于总页数为7的分页来说，seed=3.就有点大了，选择2刚好
    //一定要把分页的seed调整好，且也需要调整何时出现"..."，一定要依据实际情况而定，没有定势！！！
	int seed=3;//设定判断的种子数
	if(pageSize>seed*2){//无法全部列出所有页面，需要使用到省略号"..."
		if(currentPage<seed*2){//当前页小于4页，先显示前面的数据,到第七页就不能用这个情况了，
			for(int x=2;x<currentPage+seed;x++){
%>
		 <li class="<%=currentPage==x ? "active" : ""%>"><a href="<%=url%>?cp=<%=x%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>"><%=x%></a></li>
<%
			}
			if(currentPage+seed <=pageSize){
%>
			<li class="disabled"><span>...</span></li>
<%
			}
		}
		else{//当前页大于4页
%>
			<li class="disabled"><span>...</span></li>
<%	
		int startPage=currentPage-seed;
		int endPage=currentPage+seed;
				for(int x=startPage;x<=endPage;x++){
					if(x<pageSize){
%>
						<li class="<%=currentPage==x ? "active" : ""%>"><a href="<%=url%>?cp=<%=x%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>"><%=x%></a></li>
<%
					}
				}
			if(currentPage+seed*2<pageSize){
%>
			<li class="disabled"><span>...</span></li>
<%		
			}	
		}
	}else{//此种情况可以全部列出所有页面
//尾页和首页之间的控制,实现页数的循环输出
		for(int x=2;x<pageSize;x++){
%>
	    <li class="<%=currentPage==x ? "active" : ""%>"><a href="<%=url%>?cp=<%=x%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>"><%=x%></a></li>
<%
		}
	}
%>

<%
	if(currentPage == pageSize){//到了尾页
%>
	
	<li class="active"><span><%=pageSize%></span></li>
	<li class="disabled"><span>下一页</span></li>
	
<%
	}else{
%>
    
    <li ><a href="<%=url%>?cp=<%=pageSize%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>"><%=pageSize%></a></li>
    <li><a href="<%=url%>?cp=<%=currentPage+1%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>&<%=paramName%>=<%=paramValue%>&<%=paramNameB%>=<%=paramValueB%>">下一页</a></li>
<%
	}
%>
</ul>
 </div>