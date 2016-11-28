var map;
var polygons = [];
function mapload() {
    L.mapbox.accessToken = 'pk.eyJ1IjoidG9tcmVwIiwiYSI6ImNpdmk5Y2xneTAwYzAydG1xcjV4Znd2NGcifQ.3EE6sapWOjQ3EcFy3pbsxw';
	map = L.mapbox.map('map', 'mapbox.streets').setView([48.73,19.8], 8);

}
function findParks(){
	clear();
	city = document.getElementById("SelectCity").value;
	d = document.getElementById("SelectDist").value*1000;
	dist = d.toString();
	jQuery(function ($){
		var url = 'http://localhost:8080/PDTProjekt/rest/pdt/parks/'+city+'/'+dist;
		$.getJSON(url,function(data){
		
		for(i=0;i<data[0].geometry.coordinates.length;i++){
			for(j=0;j<data[0].geometry.coordinates[i].length;j++){
				for(k=0;k<data[0].geometry.coordinates[i][j].length;k++){
					var temp = data[0].geometry.coordinates[i][j][k][0];
					data[0].geometry.coordinates[i][j][k][0] = data[0].geometry.coordinates[i][j][k][1];
					data[0].geometry.coordinates[i][j][k][1] = temp;

				}
			}
		}
		for(i=0;i<data[0].geometry.coordinates.length;i++){

			var polygon = L.polygon(data[0].geometry.coordinates[i]).addTo(map);
			polygons.push(polygon);
		}
		});
	});
}
function findLakes(){
	clear();
	l = document.getElementById("SelectLimit").value;
	limit= l.toString();
	jQuery(function ($){
		var url = 'http://localhost:8080/PDTProjekt/rest/pdt/lakes/'+limit;
		$.getJSON(url,function(data){

			console.log(data.length);
		for(i=0;i<data.length;i++){
			console.log(data[i].geometry.coordinates);
			for(j=0;j<data[i].geometry.coordinates[0].length;j++){
				var temp = data[i].geometry.coordinates[0][j][0];
					data[i].geometry.coordinates[0][j][0] = data[i].geometry.coordinates[0][j][1];
					data[i].geometry.coordinates[0][j][1] = temp;

			}
		}
		for(i=0;i<data.length;i++){

			var polygon = L.polygon(data[i].geometry.coordinates).addTo(map);
			polygons.push(polygon);
		}
		});
	});
}
function findCaves(){
	clear();
	access = document.getElementById("SelectAccess").value;
	jQuery(function ($){
		var url = 'http://localhost:8080/PDTProjekt/rest/pdt/caves/'+access;
		$.getJSON(url,function(data){

			for(i=0;i<data.length;i++){
				var marker = L.marker([data[i].geometry.coordinates[1],data[i].geometry.coordinates[0]]).addTo(map);
				polygons.push(marker);
			}
		});
	});
}
function clear(){
	for(i=0;i<polygons.length;i++){
		map.removeLayer(polygons[i]);
	}
	polygons = [];
}