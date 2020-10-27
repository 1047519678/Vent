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
<html lang="en" class="page-fill">
<head>
    <meta charset="UTF-8">
    <title>达宏电子</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="<%=basePath%>images/favicon.ico?randomId=<%=Math.random()%>" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css?randomId=<%=Math.random()%>"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/g.css?randomId=<%=Math.random()%>">
</head>
<body class="page-fill">
<div class="page-fill" id="login">
    <form class="layui-form" accept-charset="utf-8" method="post" action="<%=basePath%>loginmian">
        <div class="login_face"><img src="<%=basePath%>images/logo.png?randomId=<%=Math.random()%>"></div>
        <div class="layui-form-item input-item">
            <label for="username">请输入账号</label>
            <input type="text" lay-verify="required" name="username" readonly="readonly"
                   onfocus="document.getElementById('username').readOnly=false" placeholder="" autocomplete="off"
                   id="username"
                   value="" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-item input-item">
            <label for="password">请输入密码</label>
            <input type="password" lay-verify="required|password" name="password" placeholder="" autocomplete="off"
                   readonly="readonly" id="password" onfocus="document.getElementById('password').readOnly=false"
                   value="" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-item input-item captcha-box">
            <span style="color: red">${msg}</span>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn layui-block" lay-filter="login" lay-submit="">登录</button>
        </div>
        <div class="login-link">
            <a href="<%=basePath%>loginreg">注册</a>
            <a href="<%=basePath%>loginforget">忘记密码?</a>
        </div>
    </form>
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js?randomId=<%=Math.random()%>"></script>
<script src="<%=basePath%>js/jquery-2.1.4.min.js?randomId=<%=Math.random()%>"></script>
<script>
    $(document).ready(function () {
        //初始化内容
        while (true) {
            $('#username').val('');
            if ($('#username').val() == '') break;
        }
        while (true) {
            $('#password').val('');
            if ($('#password').val() == '') break;
        }
    });
    layui.use(["form", "okGVerify", "okUtils", "okLayer"], function () {
        let form = layui.form;
        let $ = layui.jquery;
        let okUtils = layui.okUtils;
        form.verify({
            password: [/^[\S]{6,20}$/, "密码必须6到20位，且不能出现空格"],
        });

        /**
         * 表单提交
         */
        form.on("submit(login)", function (data) {
            okUtils.ajax("/loginmian", "post", data.field, true)
            return false;
        });

        /**
         * 表单input组件单击时
         */
        $("#login .input-item .layui-input").click(function (e) {
            e.stopPropagation();
            $(this).addClass("layui-input-focus").find(".layui-input").focus();
        });

        /**
         * 表单input组件获取焦点时
         */
        $("#login .layui-form-item .layui-input").focus(function () {
            $(this).parent().addClass("layui-input-focus");
        });
        /**
         * 表单input组件失去焦点时
         */
        $("#login .layui-form-item .layui-input").blur(function () {
            $(this).parent().removeClass("layui-input-focus");
            if ($(this).val() != "") {
                $(this).parent().addClass("layui-input-active");
            } else {
                $(this).parent().removeClass("layui-input-active");
            }
        })
    });
</script>
<script>
    if (top !== this) {
        parent.location.reload();
    }
</script>
</body>
</html>
