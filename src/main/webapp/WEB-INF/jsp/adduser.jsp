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
    <title>添加用户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form" action="">

            <div>
                <fieldset class="layui-elem-field layui-field-title">
                    <legend>新增用户</legend>
                </fieldset>

                <div class="layui-form-item">
                    <label class="layui-form-label">角色类型</label>
                    <div class="layui-input-inline">
                        <select name="productmodel">
                            <option value="">请选择角色类型</option>
                            <c:forEach items="${usergroup}" var="type">
                                <option value="${type.groupid}">${type.groupname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">账号</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="work_time" name="work_time"  autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="dt_worktime" name="dt_worktime"  readonly="readonly" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="upm" name="upm"  autocomplete="off"
                               class="layui-icon-password">
                    </div>
                </div>


                <div class="layui-form-item">
                    <%--        <button class="layui-btn layui-block" lay-filter="login" lay-submit="">提交</button>--%>
                    <button type="button" class="layui-btn"  id="btnsubmit">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(["element", "table", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let form = layui.form;
        let $ = layui.jquery;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let okMock = layui.okMock;

        form.on("submit(search)", function (data) {
            okUtils.ajax("/api_addworktime", "post", data.field, false)
            return false;
        });

        $("#btnsubmit").click(function (data) {
         alert("");

        });

    });

</script>

</body>
</html>
