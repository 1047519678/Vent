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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>达宏电子</title>
    <link rel="shortcut icon" href="<%=basePath%>favicon.ico">
    <link href="<%=basePath%>css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="<%=basePath%>css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath%>css/style.min862f.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">

        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>组管理</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="typography.html#">编辑</a>
                            </li>
                            <li><a href="typography.html#">删除</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            组管理
                        </h3> 新增组，变更组，组管理，删除组，组权限
                    </div>
                    <div class="alert alert-success">
                        <h3>
                            用户管理
                        </h3>新增加用户，用户权限管理，用户组基本资料管理
                    </div>
                </div>

            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>軟件管控</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="typography.html#">编辑</a>
                            </li>
                            <li><a href="typography.html#">删除</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            軟件下載
                        </h3> 從服務器上下載已經程序；方便版本管控 和資源查找方便
                    </div>
                    <div class="alert alert-success">
                        <h3>
                            軟件上傳
                        </h3>開發的軟件上傳至服務器存儲（主要存放 工具類軟件 方便直接使用）
                    </div>
                </div>

            </div>
        </div>
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>看板系统</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="typography.html#">编辑</a>
                            </li>
                            <li><a href="typography.html#">删除</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="alert alert-info">
                        <h3>
                            节拍明细
                        </h3> 监控已设定的时时生产的节拍达成状况
                    </div>
                    <div class="alert alert-danger">
                        <h3>
                            零缺陷样板
                        </h3> 按节拍运作的零缺陷产线，就是把在制造过程中隐藏的或者已经显露出来的缺陷，全部发掘出来，通过改善把产线变成为没有缺陷的产线，减少浪费，大幅度的提升品质和效率
                    </div>
                    <div class="well well-lg">
                        <h3>
                            24H维修报表
                        </h3>从发生不良起 超过24H的pcb 将会在报表中呈现
                    </div>

                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            异常分析表
                        </h3> 每天的不良现象呈现表
                    </div>

                    <div class="well well-lg">
                        <h3>
                            CPK_趋势预警
                        </h3> 制程能力指数的数据呈现
                    </div>

                    <div class="alert alert-success">
                        <h3>
                            SPC_偏移预警
                        </h3> 根据反馈信息及时发现系统性因素出现的征兆，并采取措施消除其影响，使过程维持在仅受随机性因素影响的受控状态，以达到控制质量的目的。
                    </div>

                    <div class="well well-lg">
                        <h3>
                            SPC_连续上升下降预警
                        </h3>
                    </div>

                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            不良率预警
                        </h3> 生成过程中产品的不良数呈现
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>数据维护</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="typography.html#">编辑</a>
                            </li>
                            <li><a href="typography.html#">删除</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="alert alert-success">
                        <h3>
                            IP 记录表
                        </h3> 对产线使用的IP进行记录；方便网络跳线是方面处理；有问题的电脑能立马查找到
                    </div>
                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            节拍上下限
                        </h3> 设置节拍明细的上下限范围
                    </div>
                    <div class="alert alert-success">
                        <h3>
                            EP上班时间
                        </h3> 零缺陷样板的当天上班时常维护入口
                    </div>

                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            设置DT异常
                        </h3> 计划节拍的生产异常原因记录
                    </div>

                    <div class="alert alert-success">
                        <h3>
                            非设置DT异常
                        </h3> 非计划节拍的生产异常原因记录
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>数据监控</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="typography.html#">编辑</a>
                            </li>
                            <li><a href="typography.html#">删除</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="alert alert-success">
                        <h3>
                            生产预警
                        </h3> 在产线中异常指数时常达到设定值时 发起微信，E-mail通知预警（通知班组长）
                    </div>
                    <div class="well well-lg">
                        <h3>
                            生产异常预警
                        </h3> 在产线中异常指数时常达到设定值时 发起微信，E-mail通知预警（通知上级）
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>工具</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="typography.html#">编辑</a>
                            </li>
                            <li><a href="typography.html#">删除</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            IP查找器
                        </h3> 对维护在表里的ip查询 ；查看已经使用的IP
                    </div>
                    <div class="alert alert-success">
                        <h3>
                            二维码生成器
                        </h3> 输入需要生成二维码的字符，生成二维码图形（可下载）
                    </div>

                    <div class="alert alert-warning alert-dismissable">
                        <h3>
                            微信二维码解析器
                        </h3> 识别图形二维码；调用微信API 解码
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="<%=basePath%>js/jquery.min.js?v=2.1.4"></script>
<script src="<%=basePath%>js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=basePath%>js/content.min.js?v=1.0.0"></script>
<!-- <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script> -->
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/typography.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:52 GMT -->
</html>
