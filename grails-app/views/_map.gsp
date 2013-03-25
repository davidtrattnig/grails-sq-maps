<div id="${id}" style="width:${width};height:${height};"></div>

<r:require modules="sqCommons, sqMaps"/>
<r:script disposition="head"> 	

function initMap(sqm, lng, lat, zoom) {

	sqm.lng = lng;
	sqm.lat = lat;
	sqm.zoom = zoom;
	if (sqm.hashPosition == true) {
		readPosition(x, y, z);
	}
	
	var center = new google.maps.LatLng(sqm.lat, sqm.lng);
	// var iwOptions = { size: new google.maps.Size(300,300) };
	// sqm.focus = new google.maps.InfoWindow(iwOptions);
  var mapOptions = {
		backgroundColor: '#ccc',
    zoom: (1*zoom),
    center: center,
    // mapTypeId: google.maps.MapTypeId.ROADMAP
		mapTypeId: google.maps.MapTypeId.TERRAIN
	};

	sqm.map = new google.maps.Map($("#${id}")[0], mapOptions);
	var omsOptions = { markersWontMove:true, markersWontHide:true, keepSpiderfied:true };
	var oms = new OverlappingMarkerSpiderfier(sqm.map, omsOptions);
	oms.addListener('click', function(marker) {
		displayInfo(marker);
		/**
		sq.updateHash( function(hash) {
			hash["e"] = marker.desc;
		}); **/
		});
			
	// var arr = new Array();
	// for (var id in g_markers) {
	// 	g_markers[id] = placeMarker(g_map, g_markers[id], false, null);
	// 	var marker = g_markers[id];
	// 	marker.desc = id;
	// 	oms.addMarker(marker);
	// 	arr.push(marker);
	// }
								
	// var mcOptions = {gridSize: 21, maxZoom: 12 };
	// var markercluster = new MarkerClusterer(sqm.map, arr, mcOptions);
	// 			
  if (sqm.hashPosition == true) {
		storePosition();
	}
			
}

function readPosition() {
	var hash = sq.hash();
	e = hash["e"] ? g_markers[hash["e"]] : null;
	x = e ? e.lng() : (hash["x"] ? hash["x"] : parseFloat(x));
	y = e ? e.lat() : (hash["y"] ? hash["y"] : parseFloat(y));
	z = hash["z"] ? parseInt(hash["z"]) : z; 
}
function storePosition() {
	google.maps.event.addListener(g_map, 'zoom_changed', function() {
			var c = g_map.getCenter();
			sq.updateHash( function(hash) {
				hash["x"] = c.lng();
				hash["y"] = c.lat();
				hash["z"] = g_map.getZoom();
			});
	  });
  google.maps.event.addListener(g_map, 'idle', function() {
   	var c = g_map.getCenter();
	sq.updateHash( function(hash) {
		hash["x"] = c.lng();
		hash["y"] = c.lat();
	});
  });	
}


	<%--
	<g:each in="${eventInstanceList}" status="i" var="event">
	g_markers['${event.id}'] = new google.maps.LatLng(${event.latitude}, ${event.longitude});	 
	datasource['${event.id}'] = '<span class="vevent"><span class="small" style="white-space:nowrap !important">${event.prettyDate()}</span><h4><span class="summary">${event.title.encodeAsHTML()}</span></h4>${event.eventType ? event.eventType?.encodeAsHTML()+" in" : ""}  <span class="location">${event.getShortAddress()}</span><br/><am:link class="pure url" controller="event" slug="${event.slug}" id="${event.id}" ><g:message code="actionmap.moreInfo" /></am:link></span>';
	
	</g:each>--%>
	
	
</r:script>		

<r:script dependsOn="application" disposition="defer">

	var sq = new SQ();
	var sqm = new SQM();
	initMap(sqm, ${lng}, ${lat}, ${zoom});
	
	var googleOptions = {
	    strokeColor: "#FFFF00",
	    strokeWeight: 7,
	    strokeOpacity: 0.75
	};

	$.getJSON('${g.createLink(controller:'map',action:'json')}', function(data) {
		alert( data.features );
		var myGoogleVector = new GeoJSON(data.features, googleOptions);

		if (myGoogleVector.error){
				alert("err:"+error);
		}else{
		   myGoogleVector.setMap(sqm.map);
		}
	});
	
	function updateHeight() {
		$( sqm.map ).height(
			$(window).height() - ${vmargin ? vmargin : 0}
		);
	}
	$(document).ready( function() {
		$(window).resize( updateHeight );
	});			

 </r:script>

