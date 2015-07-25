<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" href="css_ui/cupertino/jquery-ui-1.10.3.custom.min.css" rel="stylesheet"/> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="js/supportJS.js"></script>
	<link rel="stylesheet" href="css_ui/thanh.css" />
	<link rel="stylesheet" href="css_ui/loader_effect.css" />
    <title>Rhythm Game</title>
    <script>
   
     $(document).ready(function() {
          //Khoi tao thong tin cua processbar
    	  changeProcessBar("Checking user...",20);
    	  
    	  //FB code
    	  $.ajaxSetup({ cache: true });  
    	  $.getScript('//connect.facebook.net/en_UK/all.js', function(){
    	    window.fbAsyncInit = function() {
    	      FB.init({
    	        appId: '573292326025854',
    	        channelUrl : '//WWW.NHOMVL.COM/channel.html',
    	        status     : true,
    	        cookie     : true,                                 
    	        xfbml      : true 
    	      });       

    	      FB.login(function(response) {
   	    	    if (response.authResponse) {
   	    	    	$( "#dialog" ).dialog( "open" );
   	    	    } else {
   	    	    	alert("Sorry, we need some permission from you to start the game.");
   	    	    	
   	    	    }
   	    	 //publish_actions
   	    	}, {scope: 'email,publish_actions'});   
     	     
    	    };
    	  });
      });
	function sendTokenToServer(uid,accessToken){
		//Hien thi thong bao lien lac sever
		changeProcessBar("Loading server info...",30);
		
		$.post("LoginUser", { user: uid, token: accessToken })
		.done(function(data) {
			//Hien thi thong bao lien lac sever
			changeProcessBar("Loading image and music list...",70);
			preLoadImageMusic();
		});
	}

	//Function tu viet
	function changeProcessBar(message,step){
		 changeInfo(message,"info");
		 doAnim(step);
	}
	function preLoadImageMusic(){
		$(['images/Pioneer-music-wallpapers.jpg','images/Rhythm.jpg']).preload();
		$.post("MusicUrlLoader", { all: "true" })
		.done(function(data) {
			changeProcessBar("Done !",100);
			setTimeout(function(){location.href  = "index2.jsp";},1000);
		})
		.error(function(data) {
			alert("Error");
		});
	}
	function doAnim(wD){
	 $('.ui-progressbar-value').stop(true).animate({width: wD + '%'},1000, 'easeOutBounce');
	}
	</script>
    <style>
    	body{
    		background-color: black;
    	}
		#mainDiv{
			width: 850px;
			
		}
		#loadLogo{
			width: 850px;
			height: 500px;
			
			/*Full size*/
			background: url("images/Rhythm.png") no-repeat center center;
			-webkit-background-size: 100% 100%;
			background-size: 100% 100%;
			
		}
		#loadingBar{
			width: 500px;
			height: 100%;
		}
	</style>
</head>
<body>
<div id="fb-root"></div>
	<div id="dialog" title="Wellcome">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
			You have give us permission.
		</p>
		<p>
			Hope you will enjoy our game.
		</p>
	</div>
	<div id="mainDiv" class="center">
		<div class="right" style="width: 370px;margin: 25px;"><div class="fb-like" data-href="https://apps.facebook.com/rhythmtest/" data-send="true" data-width="350" data-show-faces="true"></div></div>
		<div class="clearfix"></div>
		<div id="loadLogo" class="center fullBackground">
			<table align="center" id="loadingBar">
				<tr>
					<td></td>
				</tr>
				<tr style="height: 40px;">
					<td><div id="progressbar" style="height: 22px;"></div></td>
				</tr>
				<tr style="height: 40px;">
					<td><div id="info" style="color: white;font-weight: bold;font-size: 18pt;"></div></td>
				</tr>
				<tr style="height: 40px;"><td></td></tr>
			</table>
		</div>	
	</div>
</body>
</html>