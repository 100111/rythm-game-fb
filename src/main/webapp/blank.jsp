<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css_ui/loader_effect.css" />
<link rel="stylesheet" href="css_ui/jquery.percentageloader-0.1.css" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="js/jquery.percentageloader-0.1.js"></script>
<title>Insert title here</title>
</head>
<script>
$(function() {
	$( "#progressbar" ).progressbar({
	      value: 37
	});
	$pVal = $('.ui-progressbar-value').addClass('ui-corner-right');
    var pGress = setInterval(function() { //generate our endless loop
        var pCnt = $pVal.width(); // get width as int
        // generate a random number between our max 100 and it's half 50, 
        // this is optional, and make the bar move back and forth before
        // we reach the end.
        var rDom = Math.floor(Math.random() * (100 - 50 + 1) + 50);
        var step = rDom >= 100 ? 100: rDom; // reached our max ? reset step.
        doAnim(step);
    },1000);
    var doAnim = function(wD) {
        // complete easing list http://jqueryui.com/demos/effect/easing.html
        $pVal.stop(true).animate({width: wD + '%'},1000, 'easeOutBounce');
        if (wD >= 100) clearInterval(pGress) /* run callbacks here */
    };

    var $topLoader = $("#topLoader").percentageLoader({width: 256, height: 256, controllable : true, progress : 0.5, onProgressUpdate : function(val) {
        $topLoader.setValue(Math.round(val * 100.0));
      }});
});
</script>
<body bgcolor="black">
<div id="topLoader"></div>
<div class="circle_loading"></div>
<div class="circle1_loading"></div>
<div id="progressbar"></div>
<div id='loading'>
	<div id='Gen' class='marginLeft'>
		<div class='block' id='rotate_01'></div>
		<div class='block' id='rotate_02'></div>
		<div class='block' id='rotate_03'></div>
		<div class='block' id='rotate_04'></div>
		<div class='block' id='rotate_05'></div>
		<div class='block' id='rotate_06'></div>
		<div class='block' id='rotate_07'></div>
		<div class='block' id='rotate_08'></div>
		<div class='clearfix'></div>
	</div>
	<div id='facebook' class='marginLeft'>
		<div id='block_1' class='facebook_block'></div>
		<div id='block_2' class='facebook_block'></div>
		<div id='block_3' class='facebook_block'></div>
		<div class='clearfix'></div>
	</div>
	<div id='circle' class='marginLeft'>
		<div id='circle_1' class='circle'></div>
		<div id='circle_2' class='circle'></div>
		<div id='circle_3' class='circle'></div>
		<div class='clearfix'></div>
	</div>
	<div id='outer-bar' class='marginLeft'>
		<div id='front-bar' class='bar-animation'>
			<div id='bar_1' class='bar-line'></div>
			<div id='bar_2' class='bar-line'></div>
			<div id='bar_3' class='bar-line'></div>
			<div class='clearfix'></div>
		</div>
	</div>
	<div id='circular' class='marginLeft'>
		<div id='circular_1' class='circular'></div>
		<div id='circular_2' class='circular'></div>
		<div id='circular_3' class='circular'></div>
		<div id='circular_4' class='circular'></div>
		<div id='circular_5' class='circular'></div>
		<div id='circular_6' class='circular'></div>
		<div id='circular_7' class='circular'></div>
		<div id='circular_8' class='circular'></div>
		<div class='clearfix'></div>
	</div>
</div>

</body>
</html>