	var classesIp = '192.168.1.109'
	var classesPort = '8080'
	var bookingIp = '192.168.1.109'
	var bookingPort = '8090'

	var locationIdList = [5,14,3,15,11,16,4,8,13,10,6,9]
	var locationList = ['Bank','Cannon Street','Covent Garden','Ealing','Farringdon','Finsbury Park','Holborn','Old Street','Elephant & Castle','Victoria','Westfield Stratford','Westfield London']
	var isClass = ""
	var locId = ""
	var myArray = []
	var emailField = document.getElementById("emailField")
	var passwordField = document.getElementById("passwordField")
	
	//Horizontal location table
	buildisClassList()
	buildLocationList()
	

	//Show table headers
	buildTable()

	function buildTable(data){
		var table = document.getElementById('myTable')
		var classDate="";

		//set error bar
		var errorRow = `<tr class="showhide">
			<th  colspan="3" id="errorHeader"> </th>
		</tr>`

		table.innerHTML = errorRow

		//set header
		var headerRow = `<tr>
			<th id="tableheaders">TIME</th>
			<th colspan="2" id="tableheaders">CLASS</th>
		</tr>`

		table.innerHTML += headerRow

		if (data!== undefined) {
			for (var i = 0; i < data.length; i++){

			// If a new date add a new line
			if(classDate!==data[i].date){
				var dateRow = `
					<tr>
						<th colspan="3" id="tabledate">${data[i].date}</th>
					<tr/>`
				table.innerHTML += dateRow
				classDate=data[i].date
			}

			bookinId = data[i].bookingId

		

			var row = `<tr>
							<td>${data[i].time}</td>
							<td>${data[i].name}</td>
							<td><button type="button" class="btn btn-secondary btnpointer" onclick="makeBooking('${data[i].date}','${data[i].isClass}','${data[i].locationId}','${data[i].bookingId}')" >Book</button></td>
					  </tr>`
			table.innerHTML += row


		}
		}

		
	}

	function buildAlertTable(){

		var table = document.getElementById('myTable')

		//set header
		var credRow = `<tr>
			<th colspan="3">Unable to get clases</th>
		</tr>`

		table.innerHTML = credRow

	}



	function buildLocationList(){

	var element = document.getElementById('locationlist')

console.log(locationIdList)
	var listHtml = ""
	for(var i=0; i<locationList.length;i++){

		var buttonId = "button" + locationIdList[i]
		console.log(locationIdList)
		listHtml += `<button type="button" id="${buttonId}" class="btn btn-secondary locbtn" onclick="setButtonAndRequestClassesForLocation(${locationIdList[i]})">${locationList[i]}</button>`
	}

	element.innerHTML = listHtml

	}

	function setButtonAndRequestClassesForLocation(locIdIn){

		setButtonColor("button" + locIdIn, locationIdList)

		locId = locIdIn

	

		doAjaxRequestToGetClassesOrGymTimes()
	}

	function tableLoading(){
		var table = document.getElementById('myTable')

		//set header
		var credRow = `<tr>
			<th colspan="3">Classes loading...</th>
		</tr>`

		table.innerHTML = credRow
	}

	function setButtonColor(buttonId, buttonIdList){
		console.log(buttonIdList)

		//set all buttons to normal
		for(var i=0; i<buttonIdList.length;i++){
			console.log("here")
			console.log(buttonIdList[i])
			var button = document.getElementById("button" + buttonIdList[i])
			button.style.backgroundColor = "#222222"
			button.style.color = "#7F7F7F"
	
		}

		//set selected to special
		console.log(buttonId)
		var button = document.getElementById(buttonId)
		button.style.backgroundColor = "#d6d834"
		button.style.color = "#000000"
	}

	function buildisClassList(){

	var element = document.getElementById('isClassList')
	listHtml = ""
	//var class = ["true","false"]
	listHtml += `<button type="button" id="classButton" class="btn btn-secondary locbtn" onclick="setIsClass('true')">Classes</button>`
	listHtml += `<button type="button" id="entryButton" class="btn btn-secondary locbtn" onclick="setIsClass('false')">Gym Entry Times</button>`

	element.innerHTML = listHtml

	
	}

	function setIsClass(value){

		if(value==="true"){
			var button = document.getElementById("classButton")
			button.style.backgroundColor = "#29AD5F"
			button.style.color = "#000000"
			var button = document.getElementById("entryButton")
			button.style.backgroundColor = "#222222"
			button.style.color = "#7F7F7F"
			isClass = "true"
		}else{
			var button = document.getElementById("entryButton")
			button.style.backgroundColor = "#29AD5F"
			button.style.color = "#000000"
			var button = document.getElementById("classButton")
			button.style.backgroundColor = "#222222"
			button.style.color = "#7F7F7F"
			isClass = "false"
		}

		doAjaxRequestToGetClassesOrGymTimes()

	}

	function doAjaxRequestToGetClassesOrGymTimes(){
		console.log("isClass:" + isClass)
		console.log("locId:" + locId)
		if(isClass!=="" && locId!==""){
			console.log("doing request")
			tableLoading()
			var urlString = 'http://' + classesIp + ":" + classesPort + '/timetable?locationId=' + locId + '&isClasses=' + isClass
		$.ajax({
		method: 'GET',
		url: urlString,
		success:function(response){
			myArray = response
			buildTable(myArray)
		},
		error:function(xhr, status, error) {
  			var err = eval("(" + xhr.responseText + ")");
  			console.log(err)
  			buildAlertTable()
		}
	    })
		}else{
			console.log("NOT doing request")
		}
		
	}

	function makeBooking(date, isClass, locationId, bookingId){
		console.log("WE GOT HERE")
		var errorBar = document.getElementById("errorHeader")
		//check email and password
		if(emailField.value==null || emailField.value=="" || passwordField.value==null || passwordField.value==""){
			errorBar.innerHTML = "Enter GymBox credentials";
		}else{

			sendBookingRequest(emailField.value, passwordField.value, date, isClass, locationId, bookingId)
		}
		console.log("Makebooking")
		console.log(emailField.value)

		console.log(passwordField.value)
	}

	function resetError(){
		var errorBar = document.getElementById("errorHeader")
		errorBar.innerHTML = " ";
	}

	function sendBookingRequest(emailpassed, passwordpassed, datepassed, isClasspassed, locationIdpassed, bookingIdpassed){
		//console.log(urlString)
		var urlString = 'http://' + bookingIp + ":" + bookingPort + '/booking?' + 'email=' + emailpassed + '&password=' + passwordpassed + '&date=' + datepassed + '&isClass=' + isClasspassed + "&locationId=" + locationIdpassed + '&bookingId=' + bookingIdpassed
		
		console.log(urlString)

		var errorBar = document.getElementById("errorHeader")
		errorBar.innerHTML = "Requesting...";

		$.ajax({
		method: 'POST',
		url: urlString,
		success:function(response){
			var errorBar = document.getElementById("errorHeader")
			errorBar.innerHTML = "Successful.";
		},
		error:function(xhr, status, error) {
  			var err = eval("(" + xhr.responseText + ")");
  			console.log(err)
  			errorBar.innerHTML = "Unable to make booking.";
		}
	    })

	}




