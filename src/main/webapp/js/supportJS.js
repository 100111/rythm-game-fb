/*--------------------------------- Support Function List ---------------------------------*/
// Array Remove - By John Resig (MIT Licensed)
Array.prototype.remove = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};

function addInfo(str,divID){
	var old = document.getElementById(divID).innerHTML;
	old += "<br/>"+str;
	document.getElementById(divID).innerHTML = old;
}
function changeInfo(str,divID){
	document.getElementById(divID).innerHTML = str;
}
function formatTime(secsTime){
	var sec = secsTime;
	var minute = (sec - (sec % 60)) / 60;
	return minute + ":" + (sec - minute*60);
}
/**
 * Css class support
 */
function addCssClass(elementID,className) {
	document.getElementById(elementID).className += (" "+className);
}

function changeClass(elementID,className) {
	document.getElementById(elementID).className = className;
}

function removeCssClass(elementID,className) {
	document.getElementById(elementID).className =  document.getElementById(elementID).className.replace( '/(?:^|\s)'+className+'(?!\S)/g' , '' );
}

$.fn.preload = function() {
    this.each(function(){
        $('<img/>')[0].src = this;
    });
};
