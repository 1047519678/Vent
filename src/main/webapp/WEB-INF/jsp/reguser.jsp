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
    <title>用户注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="<%=basePath%>images/favicon.ico?randomId=<%=Math.random()%>" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=basePath%>css/oksub.css?randomId=<%=Math.random()%>"/>
    <style>
        #login form.layui-form {
            margin: 0;
            transform: translate(-50%, -50%);
        }

        .register .tit {
            padding-top: 15px;
            text-align: center;
            font-size: 18px;
        }

        .register .code-box {
            display: flex;
        }

        .register .code-box .btn-auth-code {
            margin-left: 10px;
        }
    </style>
</head>
<body class="page-fill">
<div class="page-fill register" id="login">
    <form class="layui-form ">
        <div class="layui-form-item tit">注册</div>
        <div class="layui-form-item input-item">
            <label for="username">请输入工号</label>
            <input type="text" lay-verify="required" name="username" placeholder="" autocomplete="off" id="username"
                   class="layui-input">
        </div>
        <div class="layui-form-item input-item">
            <label for="password">请输入身份证后六位</label>
            <input type="password" lay-verify="required|password" name="password" placeholder="" autocomplete="off"
                   id="password" class="layui-input">
        </div>
        <div class="layui-form-item">
            <button class="layui-btn layui-block" lay-filter="login" lay-submit="">注册</button>
        </div>
        <div class="login-link">
            <a href="<%=basePath%>login">有账号去登录</a>
        </div>
    </form>
</div>
<!--js逻辑-->
<script src="<%=basePath%>lib/layui/layui.js?randomId=<%=Math.random()%>"></script>
<script>
    layui.use(["form", "okGVerify", "okLayer", "okUtils"], function () {
        let form = layui.form;
        let $ = layui.jquery;
        let okGVerify = layui.okGVerify;
        let okLayer = layui.okLayer;
        let regPhone = /^[1][0-9]{10}$/;
        let okUtils = layui.okUtils;
        /**手机号验证**/
        let setInter = '';
        /**定时器对象*/
        let second = 60;//设置时间
        /**
         * 初始化验证码
         */
        // let verifyCode = new okGVerify("#captchaImg");

        /**
         * 数据校验
         */
        form.verify({
            password: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"]
        });

        /**
         * 表单提交
         */
        form.on("submit(login)", function (data) {
            okUtils.ajax("/reguser", "post", data.field, false).done(function (response) {
                okLayer.greenTickMsg(response.msg, function () {

                })
            }).fail(function (error) {
                console.log(error)
            });
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
        });

        /**获取验证码**/
        $('.btn-auth-code').click(function () {
            let that = $(this),
                phone = $("#phone").val();
            if ($(this).hasClass("layui-btn-disabled")) {
                return;
            }
            if (regPhone.test(phone)) {
                if (!setInter) {
                    clearInterval(setInter);
                    that.addClass("layui-btn-disabled");
                    that.text(second + "秒后获取");
                    setInter = setInterval(function () {
                        second--;
                        if (second < 1) {
                            clearInterval(setInter);
                            that.removeClass("layui-btn-disabled");
                            that.text("重新获取");
                            setInter = "";
                            second = 60;
                        } else {
                            that.text(second + "秒后获取");
                        }
                    }, 1000);
                }
            } else {
                layer.msg("输入的手机号格式不正确，请重新输入", {
                    icon: "5",
                    anim: "6",
                });
                $("#phone").focus();
            }
        });
    });
</script>
</body>
</html>
