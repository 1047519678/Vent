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
    <title>地址列表管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">
            <div style="">
                <fieldset class="layui-elem-field layui-field-title">
                    <legend>非DT异常维护</legend>
                </fieldset>

                <div class="layui-form-item">
                    <label class="layui-form-label">选择机种</label>
                    <div class="layui-input-inline">
                        <select name="ProductModel">
                            <option value="">请选择一个机种</option>
                            <c:forEach items="${model}" var="model">
                                <option value="${model.ProductModel}">${model.ProductModel}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">异常项目</label>
                    <div class="layui-input-inline">
                        <select name="project">
                            <option value="">请选择一个异常项目</option>
                            <option value="网络异常">网络异常</option>
                            <option value="电气异常">电气异常</option>
                            <option value="其他异常">其他异常</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">异常时长</label>
                    <div class="layui-input-block">
                        <input type="text" name="Flg" lay-verify="title" autocomplete="off" class="layui-input">
                    </div>
                </div>

                    <div class="layui-form-item" style="text-align: center">
                        <button id="btn1" type="button" class="layui-btn layui-btn-normal" lay-submit=""
                                lay-filter="search" style="width: 86%;">提 交
                        </button>
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
            okUtils.ajax("/api_addnotdtexception", "post", data.field, false)
            return false;
        });
    });

</script>

</body>
</html>
