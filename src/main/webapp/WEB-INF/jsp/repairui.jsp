<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%
    pageContext.setAttribute("path", request.getContextPath());
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>24小时维修报表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">
            <div class="layui-form-item">
                <label class="layui-form-label">请选择线别</label>
                <div class="layui-input-inline">
                    <select name="selectline">
                        <option value="">请选择线别</option>
                        <c:forEach items="${line}" var="line">
                            <option value="${line.Line_Name}">${line.Line_Name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <select name="productmodel">
                        <option value="">请选择一个机种</option>
                        <c:forEach items="${model}" var="model">
                            <option value="${model.ProductModel}">${model.ProductModel}</option>
                        </c:forEach>
                    </select>
                </div>

                <input class="layui-input" placeholder="开始日期" autocomplete="off" id="startTime" name="startTime">
                <input class="layui-input" placeholder="截止日期" autocomplete="off" id="endTime" name="endTime">
                <button class="layui-btn" lay-submit="" lay-filter="search">
                    <i class="layui-icon layui-icon-search"></i>
                </button>
            </div>
        </form>
    </div>
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
    <div id="main" style="width: 100%;height:400px;margin-top: 50px;"></div>
    <script src="<%=basePath%>lib/echarts/echarts.min.js"></script>
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let laydate = layui.laydate;
        let form = layui.form;
        let okUtils = layui.okUtils;
        laydate.render({elem: "#startTime", type: "datetime"});
        laydate.render({elem: "#endTime", type: "datetime"});
        form.on("submit(search)", function (data) {
            if (data.field.startTime=="" ||data.field.startTime==null){
                layer.alert("请选择开始时间",{icon:2});
                return false ;
            }
            if (data.field.endTime=="" ||data.field.endTime==null){
                layer.alert("请选择结束时间",{icon:2});
                return false ;
            }
            okUtils.getcode("/api_repairuiparm", "post", data.field, true)
            return false;
        });

    })
</script>
</body>
</html>
