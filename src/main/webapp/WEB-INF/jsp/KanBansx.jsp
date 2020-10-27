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
    <title>节拍上下限设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
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
            <%--            <input class="layui-input" placeholder="开始日期" autocomplete="off" id="startTime" name="startTime">--%>
            <button class="layui-btn" lay-submit="" lay-filter="search">
                <i class="layui-icon">&#xe615;</i>
            </button>
        </form>
    </div>
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
</div>
<div id="form2" style="display: none" class="layui-form">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>机种站点上下限维护</legend>
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
                <label class="layui-form-label">上限</label>
                <input class="layui-input" placeholder="上限" autocomplete="off" name="ul_sx" id="ul_sx"
                       lay-verify="ul_sx">
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">下限</label>
                <input class="layui-input" placeholder="下限" autocomplete="off" name="ll_sx" id="ll_sx"
                       lay-verify="ll_sx">
            </div>
            <div class="layui-form-item" style="text-align: center">
                <button type="button" class="layui-btn" lay-submit="" style="width: 86%;background: cornflowerblue"
                        lay-filter="btnsubmit">提交
                </button>
            </div>
        </form>
    </div>
</div>

<!-- 行工具栏模板 -->
<script type="text/html" id="operationTpl">
    <a href="javascript:" title="编辑" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
</script>


<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils"], function () {
        let table = layui.table;
        let form = layui.form;
        let $ = layui.jquery;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        laydate.render({elem: "#startTime", type: "datetime"});
        laydate.render({elem: "#endTime", type: "datetime"});
        form.verify({
            ul_sx: [/^$|^[0-9]{0,5}$/, '只能是数字且范围0~99999！']//这个就是上面的排序lay-verify="sort"
            , remark: function (value) {
                if (value.length > 200) {
                    return '长度大于200！请重新输入';
                }
            },

            ll_sx: [/^$|^[0-9]{0,5}$/, '只能是数字且范围0~99999！']//这个就是上面的排序lay-verify="sort"
            , remark: function (value) {
                if (value.length > 200) {
                    return '长度大于200！请重新输入';
                }
            }
        });
        let userTable = table.render({
            elem: '#tableId',
            url: "/Phihong/api_querymodelsx",
            limit: 100,
            limits: [100, 200, 500, 1000], //这里设置可选择每页显示条数
            page: true,
            toolbar: true,
            size: "sm",
            cols: [[
                // {type: "checkbox", fixed: "left"},

                {field: "ProductModel", title: "机种名", width: 250},
                {field: "WorkStation", title: "工作站", width: 250},
                {field: "OutputTarget_ll", title: "下限", width: 200},
                {field: "OutputTarget_UL", title: "上限", width: 100},
                {title: "操作", width: 100, align: "center", fixed: "right", templet: "#operationTpl"}
            ]],
            height: 650,
            done: function (res, curr, count) {
                // console.info(res, curr, count);
            }
        });

        form.on("submit(search)", function (data) {
            table.reload('tableId', {
                url: "/Phihong/api_querymodelsx",
                type: "POST",
                where: data.field
            });
            return false;
        });

        form.on("submit(btnsubmit)", function (data) {

            okUtils.ajax("/apiupdate_ul_ll", "post", data.field, false).done(function (response) {
                okLayer.greenTickMsg(response.msg, function () {
                })
            }).fail(function (error) {
                // console.log(error)
            });
            return false;
        });


        table.on("tool(tableFilter)", function (obj) {
            let data = obj.data;
            if (obj.event === "edit") {
                $("#frm2productmodel").attr("value", data.ProductModel)
                $("#frm2workstation").attr("value", data.WorkStation);
                $("#ll_sx").attr("value", data.OutputTarget_ll);
                $("#ul_sx").attr("value", data.OutputTarget_UL);
                okLayer.showformopen("修改机种上下限", $("#form2").html(), "23%", "50%", null, function () {
                    table.reload('tableId', {
                        url: "/Phihong/api_querymodelsx",
                        type: "POST",
                        where: data.field
                    });
                    return false;
                })
            }
        });

    })
</script>
</body>
</html>
