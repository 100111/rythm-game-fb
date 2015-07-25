<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <link rel="stylesheet" href="style.css" />
    <title>jQuery Example</title>
    <script>
      $(document).ready(function() {
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

  			//Login code
   	      	FB.Event.subscribe('auth.login', function(response) {
   	    	  if (response.status === 'connected') {
   	    	    // the user is logged in and has authenticated your
   	    	    // app, and response.authResponse supplies
   	    	    // the user's ID, a valid access token, a signed
   	    	    // request, and the time the access token 
   	    	    // and signed request each expire
   	    	    var uid = response.authResponse.userID;
   	    	    var accessToken = response.authResponse.accessToken;
   	    	    alert("uid: "+ uid+"/ntoken: "+accessToken);
   	    	  } else if (response.status === 'not_authorized') {
   	    	    // the user is logged in to Facebook, 
   	    	    // but has not authenticated your app
     	    	alert("not author");    
   	    	  } else {
   	    	    alert("not login");
   	    	  }
   	    	 });	
     	     
    	    };
    	  });
      });
    </script>
</head>
<body>
<div id="fb-root"></div>
<div class="fb-like" data-send="true" data-width="450" data-show-faces="true"></div>
</body>
</html>