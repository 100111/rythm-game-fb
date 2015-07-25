<%@page import="db.MusicDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="model.Challenge"%>
<%@page import="model.UserFB"%>
<%@page import="db.ChallengeDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	UserFB user = (UserFB) session.getAttribute("user");
	//UserFB user = new UserFB("100001718630473","","");

	Vector<Challenge> challengeList =  ChallengeDAO.getAllChallengeForUserSolo(user.getId());
%>
<script type="text/javascript">
	function goToPlay(idChallenge,idMusicLv) {
		location.href = "game.jsp?idLv="+idMusicLv+"&challenge="+idChallenge;
	}
</script>
<div style="width: 100%; height: 100%;overflow: scroll;">
<table class="whiteCl" style="width: 100%; height: 100%; color: white;">
	<% for(Challenge challenge : challengeList) {
		if (challenge.getResult() == 0) {%>
		<tr>
			<td><img src="https://graph.facebook.com/<%=challenge.getIdUserSent()%>/picture?type=square"/></td>
			<td><%=MusicDAO.getMusicNameByMuiscLvID(challenge.getIdMusic())%></td>
			<td><%=challenge.getSenderScore()%></td>
			<td><button onclick="goToPlay(<%=challenge.getId()%>,<%=challenge.getIdMusic()%>)">Play</button></td>
		</tr>
		<%} %>
	<% } %>
</table>
</div>
</body>
</html>