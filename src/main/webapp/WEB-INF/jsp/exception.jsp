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
    <title>用户列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">
            <div class="layui-input-inline">
                <select name="model">
                    <option value="">请选择一个机种</option>
                    <c:forEach items="${model}" var="model">
                        <option value="${model.ProductModel}">${model.ProductModel}</option>
                    </c:forEach>
                </select>
            </div>
            <input class="layui-input" placeholder="开始日期" autocomplete="off" id="startTime" name="startTime">
            <input class="layui-input" placeholder="截止日期" autocomplete="off" id="endTime" name="endTime">
            <button class="layui-btn" lay-submit="" lay-filter="search">
                <i class="layui-icon">&#xe615;</i>
            </button>
        </form>
    </div>
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let table = layui.table;
        let form = layui.form;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let okMock = layui.okMock;
        laydate.render({elem: "#startTime", type: "datetime"});
        laydate.render({elem: "#endTime", type: "datetime"});
        let userTable = table.render({
            elem: '#tableId',
            url: "/Phihong/exception",
            limit: 100,
            limits: [100, 200, 500, 1000], //这里设置可选择每页显示条数
            page: true,
            toolbar: true,
            size: "sm",
            cols: [[
                // {type: "checkbox", fixed: "left"},
                {field: "ProductModel", title: "机种", width: 300},
                {field: "exceptionobject", title: "异常原因", width: 200},
                {field: "exceptiontime", title: "影响产能/Pcs", width: 200},
                {field: "Date", title: "异常时间", width: 300},
            ]],
            height: 650,
            done: function (res, curr, count) {
                console.info(res, curr, count);
            }
        });

        form.on("submit(search)", function (data) {
            table.reload('tableId', {
                url: "/Phihong/exception",
                type: "POST",
                where: data.field
            });
            return false;
        });

        table.on("toolbar(tableFilter)", function (obj) {
            switch (obj.event) {
                case "batchEnabled":
                    batchEnabled();
                    break;
                case "batchDisabled":
                    batchDisabled();
                    break;
                case "batchDel":
                    batchDel();
                    break;
                case "add":
                    add();
                    break;
            }
        });

        table.on("tool(tableFilter)", function (obj) {
            let data = obj.data;
            switch (obj.event) {
                case "edit":
                    edit(data);
                    break;
                case "del":
                    del(data.id);
                    break;
            }
        });

    })
</script>
</body>
</html>
