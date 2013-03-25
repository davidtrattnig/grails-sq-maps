/** global vars **/
var g_currentInfoWin = null;

/** places the default marker **/
function placeMarker(map, location, isDraggable, callback) {

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
function placeMarker(map, location, isDraggable, callback, image, shadow, shape) {
	

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
function placeMarkerWithLabel(map, location, isDraggable, callback, label) {
	
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
function codeAddress(map, geocoder, address, country, callback) {

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