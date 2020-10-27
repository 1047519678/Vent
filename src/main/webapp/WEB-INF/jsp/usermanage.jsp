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
    <title>用户管理</title>
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
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock", 'layer', 'transfer', 'util'], function () {
        let table = layui.table;
        let form = layui.form;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let okMock = layui.okMock;
        var $ = layui.jquery;
        var layer = layui.layer;
        var transfer = layui.transfer;
        var util = layui.util;
        laydate.render({elem: "#startTime", type: "datetime"});
        laydate.render({elem: "#endTime", type: "datetime"});
        let userTable = table.render({
            elem: '#tableId',
            url: "/Phihong/apiusermanage",
            limit: 100,
            limits: [100, 200, 500, 1000], //这里设置可选择每页显示条数
            page: true,
            toolbar: true,
            toolbar: "#toolbarTpl",
            size: "sm",
            cols: [[
                {type: "checkbox", fixed: "left"},
                {field: "EmpyNo", title: "账号", width: 100},
                {field: "EmpyName", title: "姓名", width: 150},
                {field: "groupname", title: "角色名称", width: 100},
                {field: "Pwd", title: "密码", width: 300},
                {field: "Workstate", title: "账号状态", width: 100, sort: true, totalRow: true, templet: "#statusTpl"},
                {title: "操作", width: 100, align: "center", fixed: "right", templet: "#operationTpl"},


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
                    layer.open({
                        title: '权限'
                        ,content: $("#alterAuto").html()
                        ,area: ['610px', '470px']
                        ,type: 1
                        ,cancel: function(){
                            //右上角关闭回调
                            //return false 开启该代码可禁止点击该按钮关闭
                        }
                    });
                    $.ajax({
                        type: "post",
                        url: "<%=basePath%>showListAuth",
                        data: {"userId": data.EmpyNo},
                        dataType: "json",
                        success: function (data) {
                            console.log(data);
                            transfer.render({
                                elem: '#test1'
                                , id: 'test'
                                , title: ['<b>未添加权限</b>', '<b>已添加权限</b>']
                                //, showSearch: true   //开启搜索框
                                , data: data.result
                                , value: data.values
                                , parseData: function (res) {
                                    return {
                                        "value": res.urlid //数据值
                                        , "title": res.title //数据标题
                                        , "disabled": res.disabled  //是否禁用
                                        , "checked": res.checked //是否选中
                                    }
                                }
                                , onchange: function (obj, index) {
                                    console.log(obj);
                                    console.log(index);
                                    var urlStr = null;
                                    if (index === 0)//添加
                                    {
                                        urlStr = "<%=basePath%>addUserAuth"
                                    } else if (index === 1) {//移除
                                        urlStr = "<%=basePath%>delUserAuth"
                                    }
                                    $.ajax({
                                        type: "post",
                                        url: urlStr,
                                        data: {"data": JSON.stringify(obj), "userId": data.userId},
                                        dataType: "json",
                                        success: function (result) {
                                        }
                                    })
                                }
                            });
                        }
                    });
                    //edit(data);
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
            okLayer.open("添加用户", "/Phihong/Controlleradduser", "90%", "90%", null, function () {
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
    <a href="javascript:" title="修改权限" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
<%--    <a href="javascript:" title="停用账号" lay-event="del"><i class="layui-icon">&#xe640;</i></a>--%>
</script>
<!-- 启用|停用模板 -->
<script type="text/html" id="statusTpl">
    {{#  if(d.Workstate == "Y"){ }}
    <span class="layui-btn layui-btn-normal layui-btn-xs">启用状态</span>
    {{#  } else if(d.Workstate == "N") { }}
    <span class="layui-btn layui-btn-warm layui-btn-xs">停用状态</span>
    {{#  } }}
</script>

<script type="text/html" id="alterAuto">
    <div id="test1" class="demo-transfer"></div>
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
