<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
	#divKetQua{
		background:url(images/hinhnem.jpg) no-repeat center center;
		background-size:100% 100%;
		width:100%;
		height:540px;
		margin: auto;	
	}
	#butKetQua{
	background:url(images/hinh1.png) no-repeat center center fixed !important;
	width:120px;
	height:150px;
	font-family:ThuPhap2;
	-webkit-border-radius:50%;
	text-shadow: 0px 0px 10px #000000;
	margin-top:196px;
	margin-left:220px;
	border:0px;
	-webkit-transition:1s;
	}
	#butKetQua:hover{
		color:#F00;
		font-family:ThuPhap2;
		-webkit-border-radius:50%;
		text-shadow: 0px 0px 10px #0000FF;
		-webkit-box-shadow:0px 0px 20px #CCCCCC;
		-webkit-transition:0.5s;
	}
	#butKetQua:active{
		color:#00F;
		font-family:ThuPhap2;
		-webkit-border-radius:50%;
		text-shadow: 0px 0px 10px #FF0000;
		-webkit-box-shadow:0px 0px 20px #000000;
	}
</style>
<script>
	$("#butKetQua").button({
		text:true
	});
</script>

<div id="divKetQua">
	<button id="butKetQua" onClick="ketqua()">
    	RESULT
    </button>
</div>
