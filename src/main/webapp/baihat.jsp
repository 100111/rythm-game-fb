<%@page import="model.Music"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
   
    
<style>
#divbaihat{
	width:100%;
	height:540px;
	background:url(images/baihat.png) no-repeat center center;
	background-size:100% 100%;
}
#butbh1{
	background:url(images/back.png) no-repeat center center;
	width:90px;
	height:50px;
	margin-top:260px;
}
#butbh2{
	margin-left:auto;
	background:url(images/next.png) no-repeat center center;
	width:90px;
	height:50px;
	margin-top:260px;
	margin-left:360px;
}
</style>
<script type="text/javascript">
    $(window).load(function() {
        $('#slider').nivoSlider();
    });
    function redirectLin(link) {
		location.href = link;
	}
</script>
<%
	Vector<Music> musicList = (Vector<Music>) session.getAttribute("musicList");
%>
<div id="divbaihat">
	<div id="wrapper">
 		<div class="slider-wrapper theme-bar">
            <div id="slider" class="nivoSlider">
          		<% for(Music m : musicList) { %>
          			<a href="#" onclick="redirectLin('game.jsp?id=<%=m.getId()%>&lv=1');"><img src="<%= m.getImageurl() %>" data-thumb="<%= m.getImageurl() %>" alt=""  title="<%=m.getName()%>" /> </a>
          		<% } %>
            </div>
           
        </div>
	</div>
</div>
