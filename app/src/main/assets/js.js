		var suggestions = [["It's morning","Find out where is the flag raising ceremony venue","flagraising.html"],["Say Jiayous to your friends","See the competitions coming up","compupdates.html"],["Check out the announcements you have missed","All the announcements in one place","announce.html"]];
		var fullnaventry = [["Home","index.html"],["Announcements","announce.html"],["Flag-raising venue","flagraising.html"],["Staircases and gates","gates.html"],["Lend-and-borrow","lend.html"],["Competition Updates","compupdates.html"],["Canteen and Cafe","canteen.html"],["Teacher Locker finder","pigeon.html"],["Thought of The Week","totw.html"],["Chat","irc.html"],["Important Contacts","contacts.html"],["Fault report","fault.html"],["Logout","logout.html"]];
		var naventry = [["Home","index.html"],["Announcements","announce.html"],["Flag-raising venue","flagraising.html"],["Assembly Venue","assembly.html"],["Competition Updates","compupdates.html"],["Listening to you","feedback.html"],["Teacher Locker finder","locked.html"],["Blazer loan","locked.html"],["Consultation Booking","locked.html"],["Canteen and Cafe","locked.html"],["Staircases and gates","locked.html"],["Lend-and-borrow","locked.html"],["Chat","locked.html"],["Fault report","locked.html"],["Logout","logout.html"]];
		var teachersnamelist = [[["Grace","Ong"],1],[["b"],2]];
	function onloadhome(){
		var logo = document.createElement("IMG");
		logo.src="logo.png";
		logo.id="logo";
		document.getElementById('splash').appendChild(logo);
		for (var i = 0; i < suggestions.length; i++){
			var suggestionentry = document.createElement("DIV");
			suggestionentry.className = "suggestions";
			suggestionentry.id="suggestion"+i;
			suggestionentry.innerHTML = "<div id='suggestionwrapper' onclick=window.location.href="+"'"+suggestions[i][2]+"'><div class='suggestiontitles'>"+suggestions[i][0]+"</div><div class='suggestiondesc'>"+suggestions[i][1]+"</div></div>"
			$("homescreensuggestionswrapper").appendChild(suggestionentry);
		}
		for (var j = 0; j < naventry.length; j++){
			if(j == 0){
				var suggestionentry = document.createElement("DIV");
				suggestionentry.className = "naventry";
				suggestionentry.innerHTML = "<div id='navwrapper' onclick='hidenav()'>"+naventry[j][0]+"</div>"
				$("nav").appendChild(suggestionentry);
			} else {
				var suggestionentry = document.createElement("DIV");
				suggestionentry.className = "naventry";
			if(naventry[j][1] == "locked.html"){
				suggestionentry.className = "greynaventry";
			}
				suggestionentry.innerHTML = "<div id='navwrapper' onclick=window.location.href="+"'"+naventry[j][1]+"'>"+naventry[j][0]+"</div></div>"
				$("nav").appendChild(suggestionentry);
			}
		}
	}
	function onload(){
		var logo = document.createElement("IMG");
		logo.src="logo.png";
		logo.id="logo";
		document.getElementById('splash').appendChild(logo);
		for (var j = 0; j < naventry.length; j++){
			var suggestionentry = document.createElement("DIV");
			suggestionentry.className = "naventry";
			if(naventry[j][1] == "locked.html"){
				suggestionentry.className = "greynaventry";
			}
			suggestionentry.innerHTML = "<div id='navwrapper' onclick=window.location.href="+"'"+naventry[j][1]+"'>"+naventry[j][0]+"</div></div>"
			$("nav").appendChild(suggestionentry);
		}
	}
	function extendnav(){
		$("nav").style.left="0";
		$("darken").style.zIndex=1;
		setTimeout(function(){$("darken").style.opacity=0.5;},1);
	}
	function hidenav(){
		$("nav").style.left="-72vw";
		$("darken").style.opacity=0;
		setTimeout(function(){$("darken").style.zIndex=-1;},1);
	}
	function $(name){
		return document.getElementById(name);
	}

