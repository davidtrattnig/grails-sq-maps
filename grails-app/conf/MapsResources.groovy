modules = {
  
	sqMaps {
		dependsOn 'oms'
		dependsOn 'gmc'
		dependsOn 'leaflet'
		dependsOn 'geoJSON'
		//resource url: '/js/gmaps/markerwithlabel_packed.js', disposition: 'head', bundle: 'mwl'
		resource url: '/js/sq-maps.js', disposition: 'head' 
	}	
	leaflet {
		resource url: '/js/leaflet/leaflet.css', disposition: 'head'
		resource url: '/js/leaflet/leaflet.ie.css', disposition: 'head'
		resource url: '/js/leaflet/leaflet.js', disposition: 'head'
	}
	/** MarkerClusterer **/
	geoJSON {
		resource url: '/js/geojson/GeoJSON.js', disposition: 'head'
	}
	/** MarkerClusterer **/
	gmc {
		resource url: '/js/gmaps/markerclusterer_compiled.js', disposition: 'head', bundle: 'gmc'
	}
	/** OverlappingMarkerSpiderfier **/
	oms {
		resource url: '/js/gmaps/oms.min.js', disposition: 'head', bundle: 'oms'
	}

}