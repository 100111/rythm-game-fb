<%@page import="control.ChallengeCtrl"%>
<%@page import="model.ChallengeSumRecord"%>
<%@page import="model.Challenge"%>
<%@page import="java.util.Vector"%>
<%@page import="db.ChallengeDAO"%>
<%@page import="model.UserFB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	$( ".draggable" ).draggable({
		revert: true 
	});
</script>
<%
	UserFB user = (UserFB) session.getAttribute("user");
	//UserFB user = new UserFB("100005547128193","","");
	
	Vector<Challenge> challengeList = ChallengeDAO.getAllChallengeForUser(user.getId());
	Vector<ChallengeSumRecord> recordList = ChallengeCtrl.createResultHistoryFromChallenge(user.getId(), challengeList);
%>
	

<table width="100%" height="150px;" border="1px" cellpadding="10px" class="whiteCl">
	<tr>
		<td>Image</td>
    	<td>Win</td>
    	<td>Lose</td>
    	<td>Tinh trang</td>
	</tr>
	<% for(ChallengeSumRecord record : recordList) { %>
	<tr>
		<td><img src="https://graph.facebook.com/<%=record.getIdOppement()%>/picture?type=square"/></td>
    	<td><%=record.getWin()%></td>
    	<td><%=record.getLose()%></td>
    	<%
	    	String status = "";
	    	switch (record.getStatus()) {
	    	case 0:
	    		status ="";
	    		break;
	    	case 1:
	    		status = "Cho tra loi";
	    		break;
	    	case 2:
	    		status = "Thi dau";
	    		break;	
	    	}
    	%>
    	<td><%=status%></td>
    </tr>
    <% } %>
</table>