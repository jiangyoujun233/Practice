<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#btn1").click(function(){
                $.ajax({
                    "url":"send/array/one.html",    //请求目标资源地址
                    "type":"post",              //请求方式
                    "data": { "array":[5,8,12] },           //要发送的请求参数
                    "dataType":"text",          //如何对待服务器端返回的数据
                    "success":function (response) {         //服务器成功处理请求后调用的回调函数，response是响应体数据
                        alert(response)
                    },
                    "error":function (response) {           //服务器处理请求失败后后调用的回调函数，response是响应体数据
                        alert(response)
                    }
                });
            });

            $("#btn2").click(function(){
                $.ajax({
                    "url":"send/array/two.html",    //请求目标资源地址
                    "type":"post",              //请求方式
                    "data": { "array":[5,8,12] },           //要发送的请求参数
                    "dataType":"text",          //如何对待服务器端返回的数据
                    "success":function (response) {         //服务器成功处理请求后调用的回调函数，response是响应体数据
                        alert(response)
                    },
                    "error":function (response) {           //服务器处理请求失败后后调用的回调函数，response是响应体数据
                        alert(response)
                    }
                });
            });
        });
    </script>
</head>
<body>

<a href="test/ssm.html">测试SSM整合</a>

<br/>
<br/>

<button id="btn1">Send [5,8,12] one</button>

<br/>
<br/>

<button id="btn2">Send [5,8,12] two</button>
</body>
</html>
