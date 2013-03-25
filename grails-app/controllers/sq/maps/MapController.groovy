package sq.maps

import grails.converters.*

class MapController {

    def json() { 

		render(contentType: "text/json") {[
			'type' : 'FeatureCollection',
			'features' : [
				'type':'Feature',
				'geometry' : [
					'type':'Point',
					'coordinates': [ '13.43', '47.2' ]
				],
				'properties' : [ 'fooo' : 'baaar']
			]
		]}
	}
}
