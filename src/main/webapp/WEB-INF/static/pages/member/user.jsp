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
                <input class="layui-input" placeholder="开始日期" autocomplete="off" id="startTime" name="startTime">
                <input class="layui-input" placeholder="截止日期" autocomplete="off" id="endTime" name="endTime">
                <input class="layui-input" placeholder="账号" autocomplete="off" name="username">
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
                url: okMock.api.listUser,
                limit: 20,
                page: true,
                toolbar: true,
                toolbar: "#toolbarTpl",
                size: "sm",
                cols: [[
                    {type: "checkbox", fixed: "left"},
                    {field: "ProductModel", title: "机种", width: 100},
                    {field: "exceptionobject", title: "异常原因", width: 100},
                    {field: "exceptiontime", title: "影响产能/Pcs", width: 100},
                    {field: "Date", title: "异常时间", width: 100},
                ]],
                height:200,
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
                okLayer.open("添加用户", "user-add.html", "90%", "90%", null, function () {
                    userTable.reload();
                })
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
        {{#  if(d.status == 0){ }}
            <span class="layui-btn layui-btn-normal layui-btn-xs">已启用</span>
        {{#  } else if(d.status == 1) { }}
            <span class="layui-btn layui-btn-warm layui-btn-xs">已停用</span>
        {{#  } }}
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
