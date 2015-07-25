<%@page import="db.MusicLevelDAO"%>
<%@page import="model.MusicLevel"%>
<%@page import="model.Music"%>
<%@page import="db.MusicDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="chrome=1" />
  <script src="js/pixi.dev.js"></script>
  <script src="js/buffer-loader.js" type="text/javascript"/></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
  <script src="js/supportJS.js"></script>
  
  <!-- BEGIN: Other js and css code, not very important -->
  <link rel="stylesheet" href="css_ui/jquery.percentageloader-0.1.css" />
  <script src="js/jquery.percentageloader-0.1.js"></script>
  <link rel="stylesheet" href="css_ui/loader_effect.css" />
  <link rel="stylesheet" href="css_ui/thanh.css" />
  <!-- END -->
  
  <title>Rhythm game</title>
</head>
<body>

<div id="mainDiv" style="margin:auto; width:1000px;">
    <audio id="mainAudio" style="display:none" controls>
	    <source id="mp3Source" type="audio/mp3"></source>
    </audio>
	<table>
    	<tr>
        	<td></td>
            <td><h1>Rhythm game</h1></td>
            <td><div class="fb-like" data-href="https://apps.facebook.com/rhythmtest/" data-send="true" data-width="450" data-show-faces="true"></div></td>
            <td rowspan="2">
            	<div id="topLoader" style="margin-left: 10px;"></div>
            	<div id="scoreDiv" style="text-align: center; height: 210px;"></div>
			</td>
        </tr>
		<tr>
			<td colspan="3" align="center">	
				<div id="loading" style="width: 800px; height: 600px; border: 1px solid black;background: black;" class="animatedDiv">
					<table style="width: 100%;height: 100%;text-align: center;">
						<tr><td>
							<div class="circle_loading"></div>
							<div class="circle1_loading"></div>
						</td></tr>
					</table>
				</div>
				<div id="score" style="width: 800px; height: 600px; border: 1px solid black; display: none;" class="animatedDiv"></div>
				<div id="mainGraphic" style="width: 800px; height: 600px; border: 1px solid black; display: none;"></div>
			</td>
		</tr>
		<tr>
		    <td><div id="info"></div></td>
		</tr>
	 </table>
</div>
<script>
	
	/*--------------------------------- Audio var ---------------------------------*/
	var supportAudio = false; //Support Web Audio lib ?
	
	var context = null;
	var source = null;
	var bufferL = null;
	
	var audioElement = document.getElementById('mainAudio');
	var audioDuration = 0;
	var audioLoaded = false;
	
	var startTime =  0;
	var doneLoadingTime = 0;
	var startToPlayTime = 0;
	var playing = false;
	
	//List of all notes (load from server)
	var noteMap = {};
	var noteKeyArr = new Array();

	/*--------------------------------- Pixi var (use for GL) ---------------------------------*/
	var speed = 0.0025*145;
	var width = 800; var height = 600;
	var playWidth = 120;
	// create an new instance of a pixi stage
	var stage = new PIXI.Stage(0x00FFFF,true);
	// create a renderer instance
	var renderer = PIXI.autoDetectRenderer(width, height);
	//Tao danh sach cac doi tuong note, landing, bar
	var notes = {};var landings = {}; var bars = {}; var keySuggesst = {}
	
	//Create all texture
	var barTex = PIXI.Texture.fromImage("images/bar.png");
	var landingTex = PIXI.Texture.fromImage("images/landing.png");
	var landingLightTex = PIXI.Texture.fromImage("images/landing_light.png");
	var landingRedTex = PIXI.Texture.fromImage("images/landing_red.png");
	var landingGreenTex = PIXI.Texture.fromImage("images/landing_green.png");
	var noteTex = PIXI.Texture.fromImage("images/note.png");

	//Create control mapping, useage are somewhat like the getKeyI() function
	var keyMaping = {"0":"a","1":"s","2":"k","3":"l"};

	//Info bar
	var $topLoader;
	var intervalLoaderID;

	var score = 0;
	var combo = 0;
	var hit = 0;
	var miss = 0;
	
	/*--------------------------------- Initciating code ---------------------------------*/
	$(document).ready(function() {
		//Check Web Audio support, if true, create nessesary element
		initAudioAPI();
		
		//Load music data into audio tag
		initLoader();
		
		//Load other data when Audio API is supported
		initBufferSound();
		
		//Load note data from sever
		getNoteData();
		
		//Create pixi components
		// *but now it's being called in getNoteData()
		//initPixiComp();
		
		//Create Info components
		initInfoLoader();
		
		//Event handling
		bidingEventListener();

		
	});
	
	/*--------------------------------- Audio Function List ---------------------------------*/
	function initLoader() {
		//addInfo("Begin","info");
		startTime = new Date().getTime();
		
		//Dua nhac vao, sau nay the lai bang link tu server
		//Load music link from sever to audio tag
		addMusicInfoUsingAudioTag('https://rhythmjava-100111.rhcloud.com/sound/electric.mp3');
		
	}
	function initAudioAPI(){
		try {
			// Fix up for prefixing
			window.AudioContext = window.AudioContext||window.webkitAudioContext;
			context = new AudioContext();
			supportAudio = true;
		}
		catch(e) {  
			//If not supported, but it's fine.
			supportAudio = false;
			alert('Web Audio API is not supported in this browser, but don"t worry');
		}
	}

	//aka init sound effect components
	function initBufferSound(){
		if(supportAudio){
			bufferLoader = new BufferLoader(
				context,
				[
				  'sound/bass.mp3',
				],
				finishedLoadingBuffer
			);
			bufferLoader.load();
		}
	}

	function finishedLoadingBuffer(bufferList) {
		//Luu cac am thanh da dc load vao bien
		//Save loaded sound to bufferL
		bufferL = bufferList;

		//Start the game, (1st of 2 calls)
		checkBeforePlay();
	}

	//Playing the effect when a button is hit
	function playButtonSound() {
		var sourceButton = context.createBufferSource();		
		var gainNode = context.createGain();

		gainNode.gain.value = 0.6;

		sourceButton.buffer = bufferL[0];	
		sourceButton.connect(gainNode);
		gainNode.connect(context.destination);
		sourceButton.start(0);
	}
	
	
	function addMusicInfoUsingAudioTag(url) {
		//addInfo("Loadind music...","info");
		
		//Add music
		$('#mp3Source')
		.attr('src',url)
		.detach().appendTo($("#mainAudio"));
		
		//Add su kien load nhac xong (canplaythrough)
		//Add done-loading event
		audioElement.addEventListener("canplaythrough", doneLoadingMainAudio,false);
		audioElement.addEventListener("ended", endMusic,false);
		
		
		if(supportAudio){
			//Set up Audio API: create source node + connect to context
			var source = context.createMediaElementSource(audioElement);
			source.connect(context.destination);
		}
	}

	//Return the current time of the audio(tag), in ms
	function getCurrentTime(){
		return Math.round(audioElement.currentTime * 1000);
	}
	function playSound() {
		startToPlayTime = new Date().getTime();
		playing = true;
		audioElement.play();
	}
	function pauseSound() {
		audioElement.pause();
		playing = false;
	}
	
	/*------------------------------ Server Interaction Function List ------------------------------*/
	function getNoteData() {
		//addInfo("Begin getting note data...","info");
		
		//Lay du lieu 
		//Get data from sever
		$.getJSON( "MusicLoader", { id : 1, lv :  1} )
		.done(function( data ) {
			//addInfo("Note data loaded","info");
			var i = 0;
			$.each( data, function( key, value ) {
				//addInfo(key + " : "+ value,"keyInfo");
				noteMap[key] = value;
				
				noteKeyArr[i] = key;
				i++;
			});
			/////////////////////////////////////////////////
			initPixiComp();
		})
		.fail(function( jqxhr, textStatus, error ) {
		  var err = textStatus + ', ' + error;
		  addInfo("Error note data: "+err,"info");
		});

		
	}
		
	
	/*--------------------------------- Pixi Function List ---------------------------------*/
	function initPixiComp(){
		// add the renderer view element to the DOM
		document.getElementById("mainGraphic").appendChild(renderer.view);
		requestAnimFrame( animate );

		createBG();
		createBars();
		createLandings();
		createKeySuggest();
		
		createNotes();

		/*
		var goodText = new PIXI.Text("Good", {font: "bold 60px Arvo", fill: "#006900", align: "center", stroke: "#0abc0a", strokeThickness: 7});
		var missText =new PIXI.Text("Miss", {font: "bold italic 60px Arvo", fill: "#6e0000", align: "center", stroke: "#d02626", strokeThickness: 7});

		goodText.anchor.x = 0.5;
		goodText.anchor.y = 0.5;

		missText.anchor.x = 0.5;
		missText.anchor.y = 0.5;

		missText.position.y = height/2 + 200;
		goodText.position.y = height/2 + 200;

		stage.addChild(goodText);
		stage.addChild(missText);
		*/
	}

	function createBG(){		
			bg = new PIXI.Sprite(PIXI.Texture.fromImage("images/bg.png"));
			bg.anchor.x = 0.5;
			bg.anchor.y = 0.5;
			bg.position.x = width/2;
			bg.position.y = height/2;
			stage.addChild(bg);
	}

	function createBars(){
		//Tao 4 cai bar
		for(var i = 0; i< 4; i++){
			bars[i] = new PIXI.Sprite(barTex);
			bars[i].anchor.x = 0.5;
			bars[i].anchor.y = 0.5;
			bars[i].position.x = width/2 + (playWidth * (i - 1.5));
			bars[i].position.y = height/2;
			stage.addChild(bars[i]);
		}
	}

	function createLandings(){
		//Tao 4 nut landing
		for(var i = 0; i< 4; i++){
			landings[i] = new PIXI.Sprite(landingTex);
			landings[i].buttonMode = true;
			
			landings[i].anchor.x = 0.5;
			landings[i].anchor.y = 0.5;
			landings[i].position.x = width/2 + (playWidth * (i - 1.5));
			landings[i].position.y = height/2 + 200;	

			//Interact
			landings[i].setInteractive(true);
								
			// set the mousedown and touchstart callback..
			landings[i].mousedown = landings[i].touchstart = function(data){
				this.isdown = true;
				this.setTexture(landingLightTex);
				this.alpha = 1;

				//Code dung de bat su kien, tuong ung voi ham keyPressed
				//Process the data every time a button is hit.
				processLandingCall(data.target);
			}

			// set the mouseup and touchend callback..
			landings[i].mouseup = landings[i].touchend = landings[i].mouseupoutside = landings[i].touchendoutside = function(data){
				this.isdown = false;
				if(!this.isOver)
				{
					this.setTexture(landingTex);
				}
			}
			
			stage.addChild(landings[i]);
		}
	}
	
	//Get the currnet pressed key, currentKey: String/Char, landings: Array[Object]
	function processLandingCall(targetSprite){
		for (var i = 0; i< 4; i++) {
			if(landings[i] == targetSprite){
				currentKey = keyMaping[i];
			}
		}
		
		//currentKeyTime = getCurrentTime();
		//addInfo(currentKeyTime+" - "+currentKey,"info");
	}

	//Create falling notes from sever data
	function createNotes(){
		$.each( noteMap, function( key, value ) {
			notes[key] = new PIXI.Sprite(noteTex);
			
			notes[key].anchor.x = 0.5;
			notes[key].anchor.y = 0.5;
			
			switch (value) {
			case "a" :
				notes[key].position.x = width/2 + (playWidth * (0 - 1.5));
				break;
			case "s" :
				notes[key].position.x = width/2 + (playWidth * (1 - 1.5));
				break;
			case "k" :
				notes[key].position.x = width/2 + (playWidth * (2 - 1.5));
				break;
			case "l" :
				notes[key].position.x = width/2 + (playWidth * (3- 1.5));
				break;	
			}
			
			//Stay out of screen, 'y' will be change later
			notes[key].position.y = -1000;

			stage.addChild(notes[key]);
		});
	}

	//On-screen help
	function createKeySuggest(){
		for(var i = 0; i< 4; i++){
			var tempKey;
			switch (i) {
			case 0 :
				tempKey = "A";
				break;
			case 1 :
				tempKey = "S";
				break;
			case 2 :
				tempKey = "K";
				break;
			case 3 :
				tempKey = "L";
				break;	
			}
			
			keySuggesst[i] = new PIXI.Text(tempKey, {font: "bold 24px Arvo", fill: "#FFFFFF", align: "center"});
			keySuggesst[i].anchor.x = 0.5;
			keySuggesst[i].anchor.y = 0.5;
			keySuggesst[i].position.x = width/2 + (playWidth * (i - 1.5));
			keySuggesst[i].position.y = height/2 + 200;	
			stage.addChild(keySuggesst[i]);
		}
	}

	function hideKeySuggest(){
		for(var i = 0; i< 4; i++){
			stage.removeChild(keySuggesst[i]);
		}
	}
	
	////////////////////////////////////////////////////
	function animate() {
	    requestAnimFrame( animate );

		if (playing) {
		    //Code animate falling notes
			
			var time = getCurrentTime();
			var delta; //Variable that show difference between the current time and thetime that note SHOULD be pressed
			var key;

			//Tao bien chua not sap dc xu ly
			//Create a variable to store the next note ID
			var lanedID = -1;
				
			/*
				Go through all of notes in the list,
				This is indeed not good, but let's just leave it at this for now.
				A later code will try to remove the passed notes out of the list -> reduce load
			*/
			for (var i = 0; i< noteKeyArr.length; i++) {
				key = noteKeyArr[i];
				delta = time - Number(key);

				//Change y => animated
				/*
					delta > -2000 meaning that only the notes that will appear in the next 2sec is processed
					this will reduce a little load on the systtem ('cause all the note still need to be called in this loop)
				*/
				if (delta > -2000) {
					notes[key].position.y = (height/2) + 200 + (delta * speed);
				}
				
				/*
					delta > 40 meaning the note is LANDED and sotred out of the loop, assuming that there can only be ONE note LANDED in a short ammount of time
					this is not 0, consider the case that user press lit too late and the note is out of the LANDED zone (+- 40ms)
				*/
				if(delta > 40) {
					lanedID = i;
				}	
			}
			
			//Process score, combo, ...
			if(lanedID > -1) {
				checkHit(lanedID);	
			}
		}
	    // render the stage   
	    renderer.render(stage);
	}

	//The clock on the right
	function initInfoLoader(){
		$topLoader = $("#topLoader").percentageLoader(
				{ width: 200,
				 height: 200,
				 progress : 0 });
		$topLoader.setValue("0:0");			 
	}

	function changeInfoLoaderVal(time){
		$topLoader.setValue(formatTime(time));
		$topLoader.setProgress(1- (time/audioDuration));
	}

	/*--------------------------------- Gameplay Function List ---------------------------------*/
	var count = 0; //Using to cont combo
	function checkHit(lanedID) {
		count++;
		//addInfo("Count: "+count + ", time: "+noteKey+", key: "+ noteMap[noteKey],"dataInfo");
		
		/*
			Use landedID (which is variable 'i' in the loop in the animted() function)
			to get the noteKey (which is the time the note SHOULD be pressed)
		*/
		var noteKey = noteKeyArr[lanedID];
		
		
		//Remove the passed note from the array -> reduce load
		noteKeyArr.remove(lanedID);
		notes[noteKey].visible = false; //Remove object
		

		//Checking
		var delta2 = Math.abs(currentKeyTime - getCurrentTime()); //Dif betwwen val, the time the key SHOULD be pressed & the time the key is PRESSED 
		var noteVal = noteMap[noteKey]; // which is the KEY the should be pressed (a, s, k, l)
		var mappedNoteVal = getKeyI(noteVal); // which is 0, 1, 2, 3
		
		/*
			Hit zone range 400ms -> from -200 -> +200
		*/
		if(delta2 < 200) {
			var lowerCurKey = currentKey.toLowerCase();
			
			if(lowerCurKey == noteVal){
				/*
					Random(bad) scoring system:
					(1000/((70-delta2)/2 + delta2)): averaging the score, ex: delta2=0 => 28.5, delta2=190 => 7.7
					(combo/40 + 1): combo counting, 0c => 1x, 40c => 2x, 80c => 3x
					
				*/
				score += Math.round((1000/((70-delta2)/2 + delta2)) * (combo/40 + 1)) ;
				noteHit();
				landings[mappedNoteVal].setTexture(landingGreenTex);
				//addInfo("Hit: "+delta2+", Score: "+score,"keyInfo");
			} else {
				noteMiss();
				landings[mappedNoteVal].setTexture(landingRedTex);
				//addInfo("Wrong key: "+delta2,"keyInfo");
			}
			
		} else {
			noteMiss();
			landings[mappedNoteVal].setTexture(landingRedTex);
			//addInfo("Miss: "+delta2,"keyInfo");
		}
		updateInfoBar();
	}

	function noteHit(){
		combo++;
		hit++;
	}

	function noteMiss(){
		miss++;
		combo = 0;
	}

	function updateInfoBar(){
		var newConntent = 
			"<h3>Combo "+combo+"</h3><br/>" +
			"<h2>"+score+"</h2><br/>" +
			"<h4>Hit: "+hit+". Miss: "+miss+"<h4>";
		changeInfo(newConntent,"scoreDiv");
	}
	
	/*--------------------------------- Event Handeling Function List ---------------------------------*/
	function bidingEventListener(){
		document.addEventListener("keypress", keyPressed,false);
		document.addEventListener("keyup", keyUp,false);
	}
	
	function doneLoadingMainAudio(){
		doneLoadingTime = new Date().getTime();
		audioDuration = Math.round(audioElement.duration);
		audioLoaded = true;
		//addInfo("Music loaded in "+ (doneLoadingTime - startTime)+" ms","info");
		//addInfo("Play speed : "+ audioElement.playbackRate+" ","info");

		document.getElementById("loading").style.display = "none";
		document.getElementById("mainGraphic").style.display = "block";

		changeInfoLoaderVal(audioDuration);
		
		//2nd of 2 call, which ever happen last can run the game, NO ERROR HANGLING
		checkBeforePlay(); 
	}

	var countdown = 3;
	var timeCount = {};
	function checkBeforePlay(){

	//Check if notes data buffer is done loading
		if(bufferL == null) return;
		//Check if audio is done loading
		if(!audioLoaded) return;
		
		//TO-DO: do sth here
		if (supportAudio) {
		} else {
		}
		
		//Begin countdown	
		var intervalCountReady = self.setInterval(function(){
			
			//If countdonw is still counting, remove the last number (x = -1000) at begining of each count
			if(countdown < 3){
				timeCount[countdown+1].position.x = -1000;
			}
			
			//When it reach 0, begin to play
			if(countdown == 0) {
				self.clearInterval(intervalCountReady);
				hideKeySuggest();

				//Set up info-round-bar (the clock on the right)
				intervalLoaderID = self.setInterval(function(){
					changeInfoLoaderVal(audioDuration - Math.round(audioElement.currentTime));
				},1000);
				
				//Play audio tag
				playSound();
			} else {
				//If not, create a number onscreen.
			
				timeCount[countdown] = new PIXI.Text(countdown+"", {font:(30 + countdown*5)+"px Arial", fill:"black"});
				timeCount[countdown].anchor.x = 0.5;
				timeCount[countdown].anchor.y = 0.5;
				timeCount[countdown].position.x = width/2;
				timeCount[countdown].position.y = height/2;	
				stage.addChild(timeCount[countdown]);
				countdown--;
			}
		},1000);
		
	}

	//Game finished -> sumbmitting score
	function endMusic(){
		//addInfo("Score: "+score,"info");
		document.getElementById("mainGraphic").style.display = "none";
		document.getElementById("loading").style.display = "block";
		
		//idChallenge == 0 meaning there's no challenge being sent (no challenger)
		self.clearInterval(intervalLoaderID);
		//document.getElementById("score").innerHTML = score;
	}

	/*
		Begin playing sound.
		Because 
	*/

	var currentKeyTime = 0;var currentKey;
	function keyPressed(e) {
		currentKeyTime = getCurrentTime();
		currentKey = String.fromCharCode(e.which);
		
		var code = getKeyI(currentKey);
		if(code != -1) landings[code].setTexture(landingLightTex);

		//Sound effect
		if(supportAudio){
			playButtonSound();
		}
		
	}
	
	//Reset texture
	function keyUp(e) {
		currentKeyTime = getCurrentTime();
		currentKey = String.fromCharCode(e.which);
		
		var code = getKeyI(currentKey);
		if(code != -1) landings[code].setTexture(landingTex);
	}
	
	//Mapping
	function getKeyI(keyCode){
		var i = -1;
		switch (keyCode.toLowerCase()) {
			case "a" :
				i = 0;
				break;
			case "s" :
				i = 1;
				break;
			case "k" :
				i = 2;
				break;
			case "l" :
				i = 3;
				break;	
		}
		return i;
	}

	</script>
</body>
</html>