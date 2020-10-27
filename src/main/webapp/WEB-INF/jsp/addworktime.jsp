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
    <title>EP上班时间维护</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css">
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">

            <div>
                <fieldset class="layui-elem-field layui-field-title">
                    <legend>EP上班时间资料维护</legend>
                </fieldset>

                <div class="layui-form-item">
                    <label class="layui-form-label">选择机种</label>
                    <div class="layui-input-inline">
                        <select name="productmodel">
                            <option value="">请选择一个机种</option>
                            <c:forEach items="${model}" var="model">
                                <option value="${model.ProductModel}">${model.ProductModel}</option>
                            </c:forEach>

                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">开始时间</label>
                    <div class="layui-input-inline">
                        <select name="modelworktime">
                            <option value="">请选择上班开始时间</option>
                            <option value="00:00">00:00</option>
                            <option value="07:30">07:30</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">工作時長</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="work_time" name="work_time"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">计划时长</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="dt_worktime" name="dt_worktime"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">计划UPM</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="upm" name="upm"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label">出勤人力</label>
                    <div class="layui-input-inline">
                        <input type="tel" id="attendance_manpower" name="attendance_manpower"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item" style="text-align: center">
                    <button type="button" class="layui-btn" style="width: 86%;background: cornflowerblue" lay-submit="" lay-filter="search">提交</button>
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
    });
</script>

</body>
</html>
