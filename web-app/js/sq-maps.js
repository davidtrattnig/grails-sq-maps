// file: sq-maps.js
function SQM() {}

SQM.prototype.map = null;
SQM.prototype.lng = null;
SQM.prototype.lat = null;
SQM.prototype.zoom = null;
SQM.prototype.markes = null;
SQM.prototype.focus = null;
SQM.prototype.hashPosition = false;

/** set a GeoJSON valid datasource **/
SQM.prototype.setDatasource = function(ds) {
	alert("set datasource...");
	$.ajax({
	    type: "POST",
	    url: ds,
	    dataType: 'json',
	    success: function (response) {
	        geojsonLayer = L.geoJson(response, {
	            style: yourLeafletStyle
	        }).addTo(SQM.map);
	    }
	});
	alert("set datasource done!");
}
SQM.prototype.placeMarker = function(map, location, isDraggable, callback) {

	 var m = new google.maps.Marker({
      		position: location, 
      		map: map,
		draggable: isDraggable
	});	
	
	if (callback) {
		google.maps.event.addListener(m, 'click', callback);
	}
	return m;			
}

/** places a custom marker with provided image, shadow & shape **/
SQM.prototype.placeMarker = function(map, location, isDraggable, callback, image, shadow, shape) {
	

	var m = new google.maps.Marker({
	      		position: location, 
	      		map: map,
			draggable: isDraggable,
		    icon: image,
		    shadow: shadow,
		    shape: shape
		});	
		
	if (callback) {
		google.maps.event.addListener(m, 'click', callback);
	}
	return m;			
}

/** places a custom marker with provided image, shadow & shape **/
SQM.prototype.placeMarkerWithLabel = function(map, location, isDraggable, callback, label) {
	
	var m = new MarkerWithLabel({
	      		position: location, 
	      		map: map,
				draggable: isDraggable,
		    	labelContent: label,
		    	labelAnchor: new google.maps.Point(-30, -30),
		    	labelClass: "map-label", 
		    	labelInBackground: false
		});	
	
	if (callback) {
		google.maps.event.addListener(m, 'click', callback);
	}
	return m;			
}

/** geocodes a given address **/
SQM.prototype.codeAddress = function(map, geocoder, address, country, callback) {

	address = address.replace(/(\r\n|\n|\r)/gm,", ");
	address += ", " + country;
	
  	geocoder.geocode( { 'address': address}, function(results, status) {
		    if (status == google.maps.GeocoderStatus.OK) {
		    	map.setCenter(results[0].geometry.location);
		    	var marker = new google.maps.Marker({
		        		map: map,
		          		position: results[0].geometry.location,
						draggable: true
		      		});
				callback(marker);
		    } else {
		      alert("Geocode was not successful for the following reason: " + status);
		    }
  		});

}