<%@page import="util.RestFBUtil"%>
<%@page import="com.restfb.types.User"%>
<%@page import="com.restfb.FacebookClient"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	#table_info{
		width: 100%;
		height:545px;
		box-shadow:#069;
		background:url(images/Rhythm%202.jpg) no-repeat center center;
		background-size:100% 100%;
		text-wrap:none;
	}
	#thongtin_div{
		margin:30px auto 10px auto;
		width:80%;
		color:#C60;
	
	}
	#thongtin_a{
		text-shadow:4px 4px 6px #F0F;
	}
	#images_info{
		margin:5px 5px 5px 5px;
	}
</style>
<%
	FacebookClient client = RestFBUtil.getRestFBClient(session);
	User userRs = client.fetchObject("me", User.class);
%>
<body>
	<table id="table_info">
    	<tr>	
        	<td width="30%" height="150px" style="box-shadow:#F63">
            <img id="images_info" src="https://graph.facebook.com/<%=userRs.getId()%>/picture?type=large">
            </td>
            <td>
            	<div id="thongtin_div">
            		<h3 align="center"><a class="thongtin_a">Thông Tin</a></h3>
                	<h4><a class="thongtin_a">Tên:<%=userRs.getName() %></a></h4>
                	<p>Sinh nhật:<%=userRs.getBirthday()%></p>
                	<p>Làm việc tại:</p>
                	<p>Từng học tại:</p>
                </div>
            </td>
        </tr>
        <tr>
        	<td align="center" style="color:#C60">Bạn bè đang thi đấu cùng bạn</td>
            <td></td>
            <td></td>
        </tr>
        
    </table>
</body>

