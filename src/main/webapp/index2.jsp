<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>index</title>

	<link type="text/css" href="css_ui/cupertino/jquery-ui-1.10.3.custom.min.css" rel="stylesheet"/>
    
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
  
  	<link type="text/css" href="css_ui/bar/bar.css" rel="stylesheet">
    <link type="text/css" href="css_ui/nivo-slider.css" rel="stylesheet">
    <link type="text/css" href="css_ui/thanh.css" rel="stylesheet">
  
    <script type="text/javascript" src="js/jquery.nivo.slider.js"></script>
 
   <style type="text/css">
@font-face {
	font-family: myFirstFont;
	src: url(Font/FORTE.TTF);
	font-family:ComicTragedy;
	src:url(font/Comic%20Tragedy%20-%20BC.ttf);
} 
@font-face{
font-family:ThuPhap2;
src:url(font/hlgiomuc.ttf);	
}
@font-face{
font-family:ThuPhap;
src:url(font/VNI-Thu%20fapfan.ttf);	
}
#divFull {
	width: 900px;;
	height: auto;
	margin: auto;
}
.thea {
	text-decoration: none;
	font-family:ThuPhap;
	color: #39F;
}
.thea:hover{
	text-decoration:none;
	color:#F0F;	
}

#divHeader{
	height:120px;
	width:800px;
	margin: 20px auto auto auto;
	border-top-left-radius: 50px;
	-webkit-box-shadow:-3px -3px 15px #96C;	
	margin-bottom:20px;
}

#logo{
border-top-left-radius:50px	
}
#divBoddy{
	border:1px double #FF00FF;
	margin:auto;
	width:800px;
	height:550px;
	background-color: #000000;
	font-family: Arial
}

#menu {
	width:100%;
	height:100%;
	text-align: center;
	font-size:12px;
	border:1px groove #F00;
}
.ui-widget-content {
height:360px;	
}
#container {
	width:100%;
	height:auto;
	border: 1px groove #00F;
}
.menuItem{
	border:2px groove #9CF;
	text-height:text-size;
	color:#936;
	background:#9CF !important;
}
#div_body{
	background:url(images/music_wallpaper.jpg) no-repeat center center;
	background-size:100% 100%;
}

</style> 
<script>
$(function() {
$( "#menu" ).accordion();
$("#hienThiSP").toggle("clip","",500);
});
function reloadHieuUng(){
		//Hieu ung 1
		$( ".draggable" ).draggable({
			 revert: true 
			 });
		//Hieu ung 2
			 
	}
	///////////////////
	
	$(function() {
		loadND('');
		reloadHieuUng();
	});
	
	function loadND(idTheLoai){
		$("#container").toggle("clip","",500);
		if(idTheLoai == 'ketqua')
		{
			$("#container").load("tempChallengeList.jsp");
		}
		else if(idTheLoai=='tiso'){
			$("#container").load("tiso.jsp");	
		}
		else if(idTheLoai=='thongtin'){
			$("#container").load("thongtin.jsp");	
		}
		else if(idTheLoai=='about'){
			$("#container").load("about.jsp");	
		}
		else {
			$("#container").load("trandaumoi.jsp");
		}
		$("#container").toggle("clip","",500);
	}
	function ketqua(){
		$("#container").load("khac.jsp");	
	}
	function baihat(){
		$("#container").load("baihat.jsp", function() {
			$('#slider').nivoSlider();
		});
	}
</script>
</head>

<body id="div_body">
<div id="divFull">
	<!--------   header   --------->
    <div id="divHeader"	>
    <img id="logo" src="images/Rhythm.jpg" width="100%" height="120px">
    	
    </div>
    
    <!------- end header  --------->
    	<div id="divBoddy">
        	<table width="100%" height="100%">
            	<tr>
                	<td width="30%">
                    	<div id="menu">
            				<h3 onClick="loadND('');"><a href="#" class="thea">Trận Đấu Mới</a></h3>
        					<div class="menuItem"><p><a href="#" onClick="baihat();" class="thea">Chọn bài hát</a></p></div>	
                			<h3 onClick="loadND('ketqua');"><a href="#" class="thea">Thách đấu</a></h3>
                			<div class="menuItem"><p>Thách đấu hiện tại</p></div>
                			<h3 onClick="loadND('tiso');"><a href="#" class="thea">Tỉ Số Bàn Thắng</a></h3>
                			<div class="menuItem"><p>Tỉ Số Bàn Thắng</p></div>
                			<h3 onClick="loadND('thongtin');"><a href="#" class="thea">Thông Tin</a></h3>
                			<div class="menuItem"><p>Thông Tin</p></div>
                			<h3 onClick="loadND('about');"><a href="#" class="thea">About</a></h3>
                			<div class="menuItem"><p>Thông tin Apps</p></div>
        				</div>
                    </td>
                    <td width="70%">
                    	<div id="container">
        		
        				</div>
                    </td>
                
                </tr>
            </table>
        	      	
       	</div>
        
</div>
		
</body>
</html>
