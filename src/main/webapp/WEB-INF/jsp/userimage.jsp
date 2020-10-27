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
    <title>更新头像</title>
    <link rel="stylesheet" href="<%=basePath%>lib/layui/css/layui.css">
    <style type="text/css">
        .main {
            text-align: center;
            /*让div内部文字居中*/
            background-color: #fff;
            border-radius: 10px;
            width: 600px;
            height: 600px;
            position: absolute;
            margin: auto;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            border: 1px solid green
        }

        #son {
            width: 0;
            border-radius: 10px;
            background-color: #e94829;
            text-align: center;
            line-height: 20px;
            font-size: 15px;
            color: white;
            font-weight: bold;
        }

        .inputhead {
            outline-style: none;
            border: 1px solid #ccc;
            border-radius: 3px;
            height: 38px;
            /* padding: 14px 14px; */
            width: 60%;
            font-size: 12px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<%--<div class="main">
    <input class="inputhead" style="text-align: center;background-color: blanchedalmond;border-radius: 10px;width: 98%;"
           type="text" value="${username}"
           disabled="value" placeholder="未知用户">
    <div>
        <img id="preview" width="300px" height="300px" style="margin-top:100px;">
    </div>
    <br/>
    <div>
        <!-- onchange="uploadFile()" -->
        &lt;%&ndash;<input type="file" id="pic" style="border-radius: 10px" name="pic" accept="image/*"></td>
        <input type="button" style="background: #0062CC;color: white;width: 120px;border-radius: 10px" value="上传"
               onclick="uploadFile()">
        <div id="son"></div>&ndash;%&gt;
    </div>
</div>--%>
<div class="layui-upload" style=" margin-top:  20px;margin-left:5px" >
<button type="button" class="layui-btn" name="file" id="file">选择图片</button>
<button type="button" class="layui-btn" name="sub" id="sub">上传图片</button>
</div>
<div class="layui-upload"  style="display:inline;float: left;">

    <div class="layui-upload-list" style="display:inline;width:200px;height: 220px;float: left; margin-left:5px">
        <img class="layui-upload-img" style="width:200px;height: 220px;" lay-verify="required" id="demo1">
        <div style="text-align: center"><span id="demoText" style="color: #0c199c;text-align: center">新头像</span></div>
    </div>

    <div class="layui-upload-list"  style="display:inline;width:200px;height: 220px;float: left;margin-left:10px" >
        <img  src="<%=basePath%>apigetimg" style="width:200px;height: 220px;">
        <div style="text-align: center"><span style="color: #0c199c;text-align: center"> 原头像</span></div>
    </div>
</div>
<input type="hidden" id="EmpyNo" value="${EmpyNo}"/>

<script type="text/javascript" src="<%=basePath%>js/jquery-2.1.4.min.js"></script>
<script src="<%=basePath%>lib/layui/layui.js"></script>
<script>
    layui.use(['layer', 'upload'], function () {
        var upload = layui.upload;
        var layer = layui.layer;
        var $ = layui.jquery;
        //layui文件上传提交
        var uploadInst = upload.render({
            elem: '#file',
            url: '<%=basePath%>apiupdateimg',
            auto: false,
            bindAction: '#sub',
            accept: 'images',
            acceptMime: 'image/*',
            data: {"userId": $('#EmpyNo').val()},
            exts: 'jpg|png|gif|bmp|jpeg',
            size: 500,
            choose: function (obj) {
                //this.data={"path": $("#path").val(),"fileName": $("#fileName").val()}//携带额外的数据
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }, done: function (res) {
                console.info(res);
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                } else if (res.code === 0) {
                    return layer.msg('上传成功');
                }
                //上传成功
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    })
</script>
</body>
<%--<script type="text/javascript">
    var fileDom = document.getElementById("pic");
    var previewDom = document.getElementById("preview");
    fileDom.addEventListener("change", e => {
        var file = fileDom.files[0];

        //var ext=file.name.substr(file.name.lastIndexOf(".")).toLowerCase()
        // if(ext!=".JPEG"||ext!=".BMP"||ext!=".GIF"||ext!=".PNG"||ext!=".JPG"){
        // 	alertinfo("请选择图片文件",2)
        // 	return;
        // }
        //只能上传小与1M的的文件 读出来的为B
        var uploadfilesize = 1024 * 1024;
        if (file.size > uploadfilesize) {
            alert("请上传小于1M的图片");
            return;
        }
        // check if input contains a valid image file
        if (!file || file.type.indexOf("image/") < 0) {
            fileDom.value = "";
            previewDom.src = "";
            return;
        }
        // use FileReader to load image and show preview of the image
        var fileReader = new FileReader();
        fileReader.onload = e => {

            previewDom.src = e.target.result;
        };

        fileReader.readAsDataURL(file);


    });

    function uploadFile() {
        var pic = $("#pic").get(0).files[0];
        if (pic === "undefined" || pic == null || pic === "") {
            alertinfo("请选择新的图像文件", 2)
            return;
        }
        document.getElementById("preview").scr = pic.name;
        var formData = new FormData();
        formData.append("Fileimg", pic);
        $.ajax({
            type: "POST",
            url: "/Phihong/apiupdateimg",
            data: formData,
            processData: false, //必须false才会自动加上正确的Content-Type
            // dataType: 'json',
            timeout: 3000,
            contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 
            xhr: function () {
                var xhr = $.ajaxSettings.xhr();
                if (onprogress && xhr.upload) {
                    xhr.upload.addEventListener("progress", onprogress, false);
                    return xhr;
                }
            },
            success: function (data) {
                if (data.indexOf("succeed") > -1) {
                   alert("头像上传成功");
                } else {
                    alert("头像上传失败");
                }
            },
            //失败回调
            error: function (XMLHttpRequest, textStatus, errorThrown, data) {
                 alert(data)
            }
        });
    }

    /**   *  侦查附件上传情况  ,这个方法大概0.05-0.1秒执行一次   */
    function onprogress(evt) {
        var loaded = evt.loaded; //已经上传大小情况
        var tot = evt.total; //附件总大小
        var per = Math.floor(100 * loaded / tot); //已经上传的百分比
        $("#son").html(per + "%");
        $("#son").css("width", per + "%");
    }


</script>--%>
</html>
