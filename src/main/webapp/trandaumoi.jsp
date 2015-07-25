<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
#divnew{
background:url(images/Pioneer-music-wallpapers.jpg) no-repeat center center;
background-size: 98% 98%;
width:100%;
height:540px;
margin: auto;	
}
#butStart{
	background:url(images/hinh1.png) no-repeat center center;
	margin-top: 365px;
	margin-left:95px;
	width:130px;
	height:95px;
	border:0px;
	-webkit-border-radius: 50%;
	-webkit-transform: rotate(-16deg);
	font-family:ThuPhap2;
	font-size:36px;
	color:#90C;
	-webkit-transition:1s;	
}
#butStart:hover{		
		color:#F00;
		font-family:ThuPhap2;
		font-size:36px;
		-webkit-border-radius:50%;
		-webkit-transform: rotate(-16deg);
		text-shadow: 0px 0px 20px #FF0000;
		-webkit-transition:0.1s;
}
#butStart:active{
		color:#00F;
		font-family:ThuPhap2;
		font-size:36px;
		-webkit-border-radius:50%;
		-webkit-transform: rotate(-16deg);
		text-shadow: 0px 0px 20px #FF0000;
}

</style>
<script>
	$("#butStart").button({
		text:true
		});
	
</script>
<body>
<div id="divnew">
	<button onclick="baihat();" id="butStart">PLAY</button>
</div>
<body>