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
    <title>地址列表管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let table = layui.table;
        let form = layui.form;
        let $ = layui.jquery;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let okMock = layui.okMock;
        laydate.render({elem: "#startTime", type: "datetime"});
        laydate.render({elem: "#endTime", type: "datetime"});
        let userTable = table.render({
            elem: '#tableId',
            url: "/Phihong/queryworktime",
            limit: 100,
            limits: [100, 200, 500, 1000],
            page: true,
            toolbar: true,
            toolbar: "#toolbarTpl",
            size: "sm",
            cols: [[
                // {type: "checkbox", fixed: "left"},
                {field: "ProductModel", title: "机种", width: 250},
                {field: "work_date", title: "工作开始时间", width: 200},
                {field: "Work_time", title: "工作总时长", width: 100},
                {field: "DT_worktime", title: "损失时长", width: 100},
                {field: "UPM", title: "每分钟产能", width: 150},
                {field: "Attendance_manpower", title: "人力", width: 100},
                {field: "Theoretical_Rate", title: "理论产出", width: 300},
                {field: "Insert_time", title: "上传时间", width: 300}
            ]],
            height: 700,
            done: function (res, curr, count) {
                //console.info(res, curr, count);
            }
        });

        table.on("toolbar(tableFilter)", function (obj) {
            if (obj.event === "add") {
                add();
            }
        });
        function add() {
            //  $('#addwork').attr('style', 'display:block')
            okLayer.open("添加工作开始时间", "/Phihong/addworktime", "40%", "80%", null, function () {
                userTable.reload();
            })
        }
    })
</script>
<!-- 头工具栏模板 -->
<script type="text/html" id="toolbarTpl">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加上班时间</button>
    </div>
</script>


</body>
</html>
