
	//var myArray = [{"isClass":true,"date":"07/12/2020","time":"13:30","name":"Bike \u0026 Beats","instructor":"Jenny Lawford","duration":"45 mins","bookingId":"slot1319494","locationId":13},{"isClass":true,"date":"06/12/2020","time":"14:30","name":"Escalate","instructor":"Maira Miranda","duration":"45 mins","bookingId":"slot1514780","locationId":13},{"isClass":true,"date":"07/12/2020","time":"07:15","name":"Pilates","instructor":"Sandra Miliauskaite","duration":"45 mins","bookingId":"slot1505423","locationId":13},{"isClass":true,"date":"07/12/2020","time":"07:30","name":"Reppin\u0027","instructor":"Remy Martyn","duration":"45 mins","bookingId":"slot1511246","locationId":13},{"isClass":true,"date":"07/12/2020","time":"08:30","name":"Bike \u0026 Beats","instructor":"Lisa Hoey","duration":"45 mins","bookingId":"slot1514777","locationId":13}]
	var myArray = []
	console.log(myArray)

	//buildTable(myArray)

	$.ajax({
		method: 'GET',
		url: 'http://localhost:8080/timetable?locationId=13&isClasses=true',
		success:function(response){
			myArray = response
			console.log("got response")
			buildTable(myArray)
			console.log(myArray)
		}
	})



	function buildTable(data){
		var table = document.getElementById('myTable')
		console.log("build table")

		for (var i = 0; i < data.length; i++){

			var row = `<tr>
							<td>${data[i].date}</td>
							<td>${data[i].time}</td>
							<td>${data[i].name}</td>
							<td>${data[i].instructor}</td>
							<td>${data[i].duration}</td>
					  </tr>`
			table.innerHTML += row

			console.log("done row")


		}
	}


