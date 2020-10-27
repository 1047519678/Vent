<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%
    pageContext.setAttribute("path", request.getContextPath());
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>二维码生成器</title>
    <head>
        <meta name="viewport"
              content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <script src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
        <script src="<%=basePath%>js/qrcode.js"></script>
        <script src="<%=basePath%>js/qrcode.min.js"></script>
        <style>
            .backbtn {
                width: 100%;
                height: 50px;
                font-size: 22px;
                background-color: #5efd63;
                border: none;
                border-radius: 12px;
            }

            .btndownload {
                font-size: 22px;
                background-color: #5efd63;
                border: none;
                border-radius: 12px;
            }


            input {
                width: 100%;
                height: 50px;
                font-size: 22px;
                /*background-color: #5efd63;*/
                border: none;
                border-radius: 12px;
            }

            img {
                border: 25px solid white;
            }
        </style>

    </head>
<body id="main" style="background-color: rgb(168, 223, 255);">
<div>
    <button class="btndownload" type="button" onclick="qrcodedownload()">下载二维码
    </button>
    <form id="form1">

        <div style="font-size: 34px; text-align: center; margin-top: 5px; font-weight: bold;"><span id="title"></span>
        </div>
        <br/>
        <div id="divCodeIN">
            <div style="font-size: 26px; font-weight: bold; text-align: center;color:midnightblue;">二维码生成器<span></span>
            </div>
            </br>
            <div style="text-align: center">
                <div id="qrcodeid" style="display: inline-block"></div>
            </div>
            <br/>
            <div><input id="str" placeholder="请输入内容..."/></div>
            <br/>
            <div>
                <button class="backbtn" type="button" onclick="getinfo()">生成二维码
                </button>

            </div>
        </div>

    </form>
</div>
</body>
<script>
    $(document).ready(function () {
        getinfo();
    });

    function getinfo() {
        $("#qrcodeid").empty();
        var qrcode = new QRCode(document.getElementById("qrcodeid"), {
            height: 230,
            width: 230,
        })
        var str = $("#str").val()
        qrcode.makeCode(str);
    }

    function qrcodedownload() {
        var data = $("canvas")[0].toDataURL().replace("image/png", "image/octet-stream;");//获取二维码值，并修改响应头部。　　　
        var filename = "phiqrcode.png";//保存的图片名称和格式，canvas默认使用的png格式。这个格式效果最好。
        var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
        save_link.href = data;
        save_link.download = filename;
        var event = document.createEvent('MouseEvents');
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        save_link.dispatchEvent(event)
    }
</script>
</html>
