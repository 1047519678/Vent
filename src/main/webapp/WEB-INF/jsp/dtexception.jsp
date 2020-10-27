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
    <title>设置DT异常</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
    <style type="text/css">
        .layui-laydate-content > .layui-laydate-list {
            padding-bottom: 0px;
            overflow: hidden;
        }

        .layui-laydate-content > .layui-laydate-list > li {
            width: 50%
        }

        .merge-box .scrollbox .merge-list {
            padding-bottom: 5px;
        }

    </style>
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">

            <div class="layui-input-inline">
                <select name="productmodel">
                    <option value="">请选择一个机种</option>
                    <c:forEach items="${model}" var="model">
                        <option value="${model.ProductModel}">${model.ProductModel}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="layui-btn" lay-submit="" lay-filter="search">
                <i class="layui-icon">&#xe615;</i>
            </button>

        </form>


    </div>
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>

    <script type="text/html" id="form2">
        <form class="layui-form layui-col-md12 ok-search">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>设置DT异常维护</legend>
            </fieldset>
            <div class="layui-form-item">
                <label class="layui-form-label">机种</label>
                <input class="layui-input" placeholder="机种" autocomplete="off" readonly name="frm2productmodel"
                       id="frm2productmodel">
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">站别</label>
                <input class="layui-input" placeholder="站别" autocomplete="off" readonly name="frm2workstation"
                       id="frm2workstation">
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">异常时间</label>
                <input class="layui-input" placeholder="异常时间" autocomplete="off" id="happedTime" name="happedTime">

            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">影响时长</label>
                <input class="layui-input" placeholder="影响时长（分钟）"  name="exTime"
                       lay-verify="exTime">
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">异常原因</label>
                <div style="margin-left: 3px;margin-right: 37px;">
                    <textarea placeholder="请输入异常原因" class="layui-textarea" name="exlog"
                              lay-verify="exlog"></textarea>
                </div>
            </div>

            <button type="button" class="layui-btn" lay-submit="" lay-filter="btnsubmit">提交</button>
        </form>
    </script>

</div>

<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script src="<%=basePath%>js/bug.js"></script>
<!-- 行工具栏模板 -->
<script type="text/html" id="operationTpl">
    <a href="javascript:" title="编辑" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
</script>

<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let table = layui.table;
        let form = layui.form;
        let $ = layui.jquery;
        let laydate = layui.laydate;
        let okUtils = layui.okUtils;
        let okLayer = layui.okLayer;
        let userTable = table.render({
            elem: '#tableId',
            url: "/Phihong/api_getworkstation",
            limit: 100,
            limits: [100, 200, 500, 1000], //这里设置可选择每页显示条数
            page: true,
            size: "sm",
            cols: [[
                {field: "ProductModel", title: "机种", width: 260},
                {field: "workstation", title: "站别", width: 250},
                {title: "操作", width: 100, align: "center", fixed: "right", templet: "#operationTpl"}
            ]],
            height: 650,
        });

        form.on("submit(search)", function (data) {
            table.reload('tableId', {
                url: "/Phihong/api_getworkstation",
                type: "POST",
                where: data.field
            });
            return false;
        });

        form.on("submit(btnsubmit)", function (data) {
            okUtils.ajax("/api_updateExhappedTime", "post", data.field, true)
            return false;
        });

        table.on("tool(tableFilter)", function (obj) {
            let data = obj.data;
            if (obj.event === "edit") {
                okLayer.showformopen("设施DT异常维护", $("#form2").html(), "50%", "80%", null, function () {
                });
                laydate.render({
                    elem: '#happedTime'//指定元素
                    , type: "time"
                    , format: 'HH:mm'
                });
                form.render();
                $("#frm2productmodel").attr("value", data.ProductModel)
                $("#frm2workstation").attr("value", data.workstation);
            }
        });
    })
</script>

</body>
</html>
