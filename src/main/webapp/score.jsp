<%@page import="db.ChallengeDAO"%>
<%@page import="model.Challenge"%>
<%@page import="gamepaly.ctrl.SubmitScore"%>
<%@page import="db.MusicDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="control.ChallengeCtrl"%>
<%@page import="model.UserFB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="css_ui/thanh.css" rel="stylesheet"/> 
<link type="text/css" href="css_ui/cupertino/jquery-ui-1.10.3.custom.min.css" rel="stylesheet"/> 
<!--
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
-->
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="js/supportJS.js"></script>
<title>Score</title>

<style type="text/css">
	#scoreTopDiv{
		width: 100%;
		height: 120px;
	}
	#avatarDiv{
		margin: 10px 10px;
		-webkit-box-shadow:-5px -5px 30px #cccccc;	
	}
	#personalInfoDiv{
		width: 50%;
		height: 100px;
		margin: 10px;
	}
	#totalScoreDiv{
	
		width: 30%;
		border: 1px double #ff0;
		margin-top: 10px;
		margin-right: 10px;
		border-radius: 40px;
		word-wrap: break-word;
		text-wrap: break-word;
	}
	#scoreBotDiv{
		border: 1px double #909090;
		height: 400px;
	}
	#randomUser{
		width: 40%;
		height: auto;
		border: 1px double black;
		margin: 10px;
	}
	.randomUser_but{
		cursor: pointer;
		background: url("images/hinh1.png");
	}
	.randomUser_but:ACTIVE{
		border: 0px;
	}
</style>
</head>
<body>
<%
	//int idMusicLevel = 1;
	int idMusicLevel = (Integer) session.getAttribute("idMusicLv");
	
	int scoreN = 0;
	try {
		scoreN = Integer.parseInt(request.getParameter("score"));
	} catch (NumberFormatException e) {
		out.println("Format error");
	}
	
	int idChallenge = 0; Challenge challenge = null;
	if(request.getParameter("challenge") != null) {
		idChallenge = Integer.parseInt(request.getParameter("challenge"));
		challenge = ChallengeDAO.findByID(idChallenge);
	}
	
	UserFB user = (UserFB) session.getAttribute("user");
	//UserFB user = new UserFB("100002825455302","","");
	
	Vector<UserFB> randomUser = ChallengeCtrl.getRandomUser(5);
	Vector<UserFB> friendList = ChallengeCtrl.getRandomFriend(user.getId(), 5,false, session);
	//Vector<UserFB> friendList = new Vector<UserFB>();
	
	String scoreString = SubmitScore.postScoreToFBString(scoreN, user.getId(), session);
	
	String songName = MusicDAO.getMusicNameByMuiscLvID(idMusicLevel);
%>
<script type="text/javascript">
	//Tell server to save score to database
	$(document).ready(function() {

		document.getElementById("loading").style.display = "none";
		document.getElementById("score").style.display = "block";
		
		$.post("SubmitScore", { idUser: '<%=user.getId()%>', idMusicLevel : '<%=idMusicLevel%>', score : '<%=scoreN%>' })
		.done(function(data) {
		 	alert("Done: " + data);
		})
		.fail(function( jqxhr, textStatus, error ) {
			var err = textStatus + ', ' + error;
			alert("Eror " + err);
		});

		
	});

	function shareToFB(caption,messageFB){
		var obj = {
			method: 'feed',
			redirect_uri: 'index2.jsp',
			link: 'https://apps.facebook.com/rhythmtest/',
			picture: 'http://www.nhomvl.com/logo128.png',
			name: 'Rhythm Game Score',
			caption: caption,
			description: messageFB
		};

		FB.ui(obj,shareCallback);
	}

	function registerChallenge(sourceButton,reciverID,isInvite) {
		sourceButton.disabled = true;

		$.post("ChallengeCtrl", { sender: '<%=user.getId()%>', reciver: reciverID, idMusicLevel : '<%=idMusicLevel%>', invite : isInvite, score : '<%=scoreN%>' })
		.done(function(data) {
			sendRequestToUser(reciverID);
		 	alert("Done: " + data);
		})
		.fail(function( jqxhr, textStatus, error ) {
			var err = textStatus + ', ' + error;
			alert("Eror " + err);
		});
	}

	function postToFb() {
		$.post("<%= scoreString%>")
		.done(function(data) {
		 	alert("Done: " + data);
		})
		.fail(function( jqxhr, textStatus, error ) {
			var err = textStatus + ', ' + error;
			alert("Eror " + err);
		});
	}

	function backToIndex() {
		location.href = 'index2.jsp';
	}

	function sendRequestToUser(idUser) {
		FB.ui({method: 'apprequests',
          message: 'Feel the Rhythm',
          to: idUser
        }, requestCallback);
		
	}

	function inviteUser() {
		FB.ui({method: 'apprequests',
          message: 'Feel the Rhythm'
        }, requestCallback);
	}

	//Callback list
	function requestCallback() {
		
	}
	function shareCallback() {
		
	}
	
</script>
<div style="width : 800px; height: 600px; margin: auto;border:1px solid red; background: url('images/background.jpg');">
	<div id="scoreTopDiv" style="width: 100%; border: 1px double blue;">
		<div id="avatarDiv" class="left" style="border: 1px double black">
			<img src="https://graph.facebook.com/<%=user.getId()%>/picture?type=normal"/>
		</div>
		<div id="personalInfoDiv" class="left whiteCl" style="border: 1px double #f0f;">
			<% if(idChallenge == 0) { %>
				<%=user.getUsernamefb() %>
			<% } else {
					switch (ChallengeCtrl.updateChallengeResult(challenge, scoreN)) { 
					case 1:
						%><h2>Lose</h2><%
						break;
					case 2:
						%><h2>Win</</h2><%
						break;
					}
				}	
			%>
		</div>
		<div id="totalScoreDiv" class="right">
			<table border="0" style=" margin: auto; width: 80%" class="whiteCl">
				<tr><td colspan="2"><div id="songName"><%=songName%></div></td></tr>
				<tr><td colspan="2"><div id="totalScore" style="text-align: center;width: 100%;height: 100%;"><h2><%=scoreN %></h2></div></td></tr>
				<tr>
					<td>Perfect</td>
					<td>Good</td>
				</tr>
				<tr>
					<td>Max combo</td>
					<td>Miss</td>
				</tr>
			</table>
		</div>
	 </div>
	 
	<div class="clearfix"></div>
	
	<div id="scoreBotDiv" style="width: 100%;">
		<div id="randomUser" class="left">
			<% for (UserFB rUser : randomUser){ %>
				<button class="randomUser_but" style="width: 160px;" onclick="registerChallenge(this,<%=rUser.getId()%>,false);">
					<img src="https://graph.facebook.com/<%=rUser.getId()%>/picture?type=square"/>&nbsp;<%=rUser.getUsernamefb() %>
				</button><br/>
			<% } %>
		</div>
		<div id="inviteUser" class="left">
			<% for (UserFB friend : friendList){ %>
				<button class="randomUser_but" style="width: 160px;" onclick="registerChallenge(this,<%=friend.getId()%>,true);">
					<img src="https://graph.facebook.com/<%=friend.getId()%>/picture?type=square"/>&nbsp;<%=friend.getUsernamefb() %><br/>
				</button><br/>
			<% } %>
		</div>
		<div id="commandButton" class="right">
			<button onclick="postToFb('<%=songName%>','New score updated: <%=scoreN%>');">Post Score</button>
			<button onclick="shareToFB();">Share</button>
			<button onclick="inviteUser();">Invite Friends</button>
			<button onclick="backToIndex();">Finish</button> 
		</div>
	</div>			
</div>	
</body>
</html>