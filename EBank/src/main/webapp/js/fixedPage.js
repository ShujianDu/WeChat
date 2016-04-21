window.onload = function(){
		var doughnutData = [
				{
					value: 0,
					color:"#ed6139",
				},
				{
					value: 1,
					color: "#0ca8f5",	
				},
				
			];
			
			var ctx = document.getElementById("chart-area").getContext("2d");
			window.myDoughnut = new Chart(ctx).Doughnut(doughnutData, {responsive : true});
		    };