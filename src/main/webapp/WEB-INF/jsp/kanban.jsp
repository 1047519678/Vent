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
    <title>看板</title>
</head>
<body onload="jumpurl()">
<script>

    function jumpurl() {
        if ("<%=basePath%>".indexOf("192.168.65.7") > -1) {
            window.location.href = 'http://192.168.65.7/phi/html/kanban.html'
        }
        if ("<%=basePath%>".indexOf("10.1.1.253") > -1) {
            window.location.href = 'http://10.1.1.253/phi/html/kanban.html'
        }

    }

</script>
</body>
</html>
