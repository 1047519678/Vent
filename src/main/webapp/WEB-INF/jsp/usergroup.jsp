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
    <title>用户组管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">
            <input class="layui-input" placeholder="组名称" autocomplete="off" name="username">
            <button class="layui-btn" lay-submit="" lay-filter="search">
                <i class="layui-icon">&#xe615;</i>
            </button>
        </form>
    </div>
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
</div>
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
            url: "/Phihong/urlgroup",
            limit: 100,
            limits: [100, 200, 500, 1000], //这里设置可选择每页显示条数
            page: true,
            toolbar: true,
            toolbar: "#toolbarTpl",
            size: "sm",
            cols: [[
                {type: "checkbox", fixed: "left"},
                {field: "gparentid", title: "列表父ID", width: 100},
                {field: "gtitle", title: "列表名称", width: 150},
                {field: "ghref", title: "列表地址", width: 300},
                {field: "gfontfamily", title: "字体样式", width: 100},
                {field: "gicon", title: "图表样式", width: 100, sort: true, totalRow: true, templet: "#statusTpl2"},
                {field: "gspread", title: "展开状态", width: 100, sort: true, totalRow: true, templet: "#statusTpl"},
                {field: "gischeck", title: "默认check", width: 100},
            ]],
            height: 650,
            done: function (res, curr, count) {
                console.info(res, curr, count);
            }
        });

        form.on("submit(search)", function (data) {
            userTable.reload({
                where: data.field,
                page: {curr: 1}
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

        function batchEnabled() {
            okLayer.confirm("确定要批量启用吗？", function (index) {
                layer.close(index);
                let idsStr = okUtils.tableBatchCheck(table);
                if (idsStr) {
                    okUtils.ajax("/user/normalUser", "put", {idsStr: idsStr}, true).done(function (response) {
                        console.log(response);
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                }
            });
        }

        function batchDisabled() {
            okLayer.confirm("确定要批量停用吗？", function (index) {
                layer.close(index);
                let idsStr = okUtils.tableBatchCheck(table);
                if (idsStr) {
                    okUtils.ajax("/user/stopUser", "put", {idsStr: idsStr}, true).done(function (response) {
                        console.log(response);
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                }
            });
        }

        function batchDel() {
            okLayer.confirm("确定要批量删除吗？", function (index) {
                layer.close(index);
                let idsStr = okUtils.tableBatchCheck(table);
                if (idsStr) {
                    okUtils.ajax("/user/deleteUser", "delete", {idsStr: idsStr}, true).done(function (response) {
                        console.log(response);
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                }
            });
        }

        function add() {
            okLayer.showformopen("设施DT异常维护", $("#form2").html(), "50%", "80%", null, function () {
            });
            laydate.render({
                elem: '#happedTime'//指定元素
                , type: "time"
                , format: 'HH:mm'
            });
            form.render();
        }

        function edit(data) {
            okLayer.open("更新用户", "user-edit.html", "90%", "90%", function (layero) {
                let iframeWin = window[layero.find("iframe")[0]["name"]];
                iframeWin.initForm(data);
            }, function () {
                userTable.reload();
            })
        }

        function del(id) {
            okLayer.confirm("确定要删除吗？", function () {
                okUtils.ajax("/user/deleteUser", "delete", {idsStr: id}, true).done(function (response) {
                    console.log(response);
                    okUtils.tableSuccessMsg(response.msg);
                }).fail(function (error) {
                    console.log(error)
                });
            })
        }
    })
</script>
<!-- 头工具栏模板 -->
<script type="text/html" id="toolbarTpl">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="batchEnabled">批量启用</button>
        <button class="layui-btn layui-btn-sm layui-btn-warm" lay-event="batchDisabled">批量停用</button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDel">批量删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="add">添加用户</button>
    </div>
</script>
<!-- 行工具栏模板 -->
<script type="text/html" id="operationTpl">
    <a href="javascript:" title="编辑" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
    <a href="javascript:" title="删除" lay-event="del"><i class="layui-icon">&#xe640;</i></a>
</script>
<!-- 启用|停用模板 -->
<script type="text/html" id="statusTpl">
    {{#  if(d.gspread == "false"){ }}
    <span class="layui-btn layui-btn-normal layui-btn-xs">展开</span>
    {{#  } else if(d.gspread == "True") { }}
    <span class="layui-btn layui-btn-warm layui-btn-xs">不展开</span>
    {{#  } }}
</script>

<!-- 图表状态 -->
<script type="text/html" id="statusTpl2">
    <span class="{{d.gfontfamily}}">{{d.gicon}}</span>
</script>

<script type="text/html" id="roleTpl">
    {{#  if(d.role == 0){ }}
    <span class="layui-btn layui-btn-normal layui-btn-xs">超级会员</span>
    {{#  } else if(d.role == 1) { }}
    <span class="layui-btn layui-btn-warm layui-btn-xs">普通用户</span>
    {{#  } }}
</script>
</body>
</html>
