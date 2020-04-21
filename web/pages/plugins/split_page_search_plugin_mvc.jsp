<%@ page pageEncoding="UTF-8"%>
<%--<jsp:include page="split_page_search_plugin_mvc.jsp"/>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path+"/";
%>
<%
    String url=basePath+(String) request.getAttribute("url");
    int allRecorders=(Integer) request.getAttribute("allRecorders");
    String columnData=(String) request.getAttribute("columnData");
    String column=(String) request.getAttribute("column");
    String keyWord=(String) request.getAttribute("keyWord");
%>
<div id="searchDiv">
    <form action="<%=url%>" method="post">
        <%
            if(!(columnData==null||"".equals(columnData))){
                String result[]=columnData.split("\\|");
        %>
        <select id="col" name="col">
            <%
                for(int x=0;x<result.length;x++){
                    String temp[]=result[x].split(":");
            %>
            <option value="<%=temp[1]%>" <%=temp[1].equals(column) ? "selected" : ""%>> <%=temp[0]%></option>
            <%
                }
            %>
        </select>
        <%
            }
        %>
        <input type="text" id="kw" name="kw" placeholder="请输入检索关键字" value="<%=keyWord == null ? "" : keyWord%>">
        <input type="submit" value="检索">
        <span>本次查询一共返回有“<%=allRecorders%>”行记录</span>
    </form>
</div>