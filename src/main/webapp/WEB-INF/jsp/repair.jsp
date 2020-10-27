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
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let table = layui.table;
        let form = layui.form;
        let util = layui.util;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let okMock = layui.okMock;
        util.fixbar({});
        laydate.render({elem: "#startTime", type: "datetime"});
        laydate.render({elem: "#endTime", type: "datetime"});

        let articleTable = table.render({
            elem: "#tableId",
            url: "/Phihong/api_repair",
            limit: 100,
            limits: [100, 200, 500, 1000], //这里设置可选择每页显示条数
            page: true,
            even: true,
            size: "sm",
            cols: [[
                // {type: "checkbox", fixed: "left"},
                {field: "Line_Name", title: "线别", width: 120, sort: true},
                {field: "sn", title: "SN", width: 150, sort: true},
                {field: "ProductModel", title: "机种", width: 150},
                {field: "MO_NUMBER", title: "工单"},
                {field: "STATION_NUMBER", title: "站别", width: 150},
                {field: "STATION_NAME", title: "测试机台", width: 150},

                {
                    field: "FORK_FLAG",
                    title: "维修状态",
                    align: "center",
                    width: 100,
                    sort: true,
                    totalRow: true,
                    templet: "#statusTpl"
                },
                {field: "starttime", title: "异常发生时间"},
                {field: "endtime", title: "维修完成时间"},
                {
                    field: "DIFFDATE",
                    title: "间隔[H]",
                    align: "center",
                    width: 80,
                    sort: true,
                    totalRow: true,
                    templet: "#statusTpl2"
                },
                {field: "CARTON_NO", title: "箱号", width: 100},
                {field: "LNK_DATA", title: "成品序号", width: 150}
            ]],
            height: 650,
            done: function (res, curr, count) {

                // console.log(res, curr, count)
            }
        });

        form.on("submit(search)", function (data) {
            table.reload('tableId', {
                url: "/Phihong/api_repair",
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
                    edit(data.id);
                    break;
                case "del":
                    del(data.id);
                    break;
            }
        });
    })
</script>


<!-- 维修状态
    <input type="checkbox" name="top" value="{{d.status}}" lay-skin="switch" lay-text="已发布|未发布" {{ d.status== true ? 'checked' : ''}}>
-->
<script type="text/html" id="statusTpl">
    {{#  if(d.FORK_FLAG == 0){ }}
    <span class="layui-badge layui-bg-green">维修完成</span>
    <%--   // row.setAttribute('style', 'color: black;background-color:#32CD32 ;')--%>
    {{#  } else if(d.FORK_FLAG == 1) { }}
    <span class="layui-badge layui-bg-orange">未维修</span>
    {{#  } }}
</script>


<%--维修时间--%>
<script type="text/html" id="statusTpl2">
    {{#  if(d.DIFFDATE >=24){ }}
    <span class="layui-btn layui-btn-danger layui-btn-xs">{{d.DIFFDATE}}</span>
    {{#  } else if(d.DIFFDATE < 24) { }}
    <span class="layui-badge layui-bg-green">{{d.DIFFDATE}}</span>
    {{#  } }}
</script>

<!-- 行工具栏模板 -->
<script type="text/html" id="operationTpl">
    <a href="javascript:" title="编辑" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
    <a href="javascript:" title="删除" lay-event="del"><i class="layui-icon">&#xe640;</i></a>
</script>
</body>
</html>
