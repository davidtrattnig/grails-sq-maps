package at.subsquare.commons

class MapsTagLib {

	static namespace = "maps"
	
	def api = { attrs, body ->
		def API_KEY = grailsApplication.config.google.maps.apiKey
		out << '<script type="text/javascript" ' 
		out << 'src="http://maps.googleapis.com/maps/api/js?key='+API_KEY+'&sensor=true">'
		out << '</script>'
	}
	
}
