<html>
    <#include "../common/header.ftl">
    <body>
    <div id="wrapper" class="toggled">
    <#--边框sidebar-->
        <#include "../common/nav.ftl" >
    <#--主要内容content -->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th><#--操作这里有详情和取消，所以样式占了两列 -->
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatusEnum().message}</td>
                                    <td>${orderDTO.getPayStatusEnum().message}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td> <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                    <td>
                                        <#if orderDTO.getOrderStatusEnum().message =="新订单">
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <#--分页 -->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right" >
                            <#--如果当前页小于等于1就不可以按上一页，否则就可以 -->
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                            </#if>

                            <#--1页到总数 -->
                            <#list 1..orderDTOPage.getTotalPages() as index>
                            <#--如果是当前页就灰掉不可按 -->
                                <#if currentPage==index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#--动态页面 -->
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                            <#-- 如果当前页大于等于总数不可按下一页-->
                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
<#--     webSocket弹窗   -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
     <div class="modal-dialog">
           <div class="modal-content">
                <div class="modal-header">
                       <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                           提醒
                        </h4>
                </div>
                <div class="modal-body">
                        你有新的订单
                </div>
                <div class="modal-footer">
                    <#--关闭的时候音乐停下来 -->
                    <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <#--查看新的订单就是刷新 -->
                    <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                </div>
           </div>

     </div>

</div>
<#--播放音乐 loop="loop":循环播放-->
<audio id="notice" loop="loop">
    <source src="/sell/mp3/song.mp3" type="audio/mpeg"/>
</audio>
<#--引入js -->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

<#--原生的HTML5不需要引入其他外部js，直接可以在这里用 -->
<script>
    var websocket=null;
    <#--先判断浏览器是否支持 ,如果在Window这个对象里有的话就是支持的,有的话就new一个，它的URL是以ws也就是WebSocket简写开头的-->
   <#--sell9.mynatapp.cc,换成服务器地址了 -->
    if('WebSocket' in window){
        <#--wlhsell.com -->
        websocket=new WebSocket('ws://wlhsell.com/sell/webSocket');//使用域名访问，但是这边不是http 协议，是ws协议
     }else{
         alert('该浏览器不支持websocket！');
     }
     <#--下面是webSocket打开事件 -->
    websocket.onopen = function(event){
        console.log('建立连接');
    }
    <#--下面是webSocket关闭事件 -->
    websocket.onclose = function(event){
        console.log('连接关闭');
    }
    <#--消息来的时候 --->
    websocket.onmessage=function (event){
        console.log('收到消息：'+event.data);
        //收到消息后就可以做弹窗提醒，播放音乐
        //喜欢啥音乐就在static/mp3放啥音乐，使用html5原生播放音乐
        $('#myModal').modal('show');
        document.getElementById('notice').play();
    }
    <#--下面是发生错误的时候 -->
    websocket.onerror=function(){
        alert('websocket通信发生错误!');
    }
    <#--窗口关闭的时候关闭webSocket -->
    window.onbeforeunload=function(){
        websocket.close();
    }
</script>

    </body>
</html>
