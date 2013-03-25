package sq.maps

class MapsTagLib {

	static namespace = "map"
	
	def api = { attrs, body ->
		def API_KEY = grailsApplication.config.google.maps.apiKey
		out << '<script type="text/javascript" ' 
		out << 'src="http://maps.googleapis.com/maps/api/js?key='+API_KEY+'&sensor=true">'
		out << '</script>'
	}
	
	
	def render = { attrs ->
	
		def src = attrs.remove("src")
		def id = attrs.remove("id")
		def lat = attrs.remove("lat")
		def lng = attrs.remove("lng")
		def width = attrs.remove("width")
		def height = attrs.remove("height")
		def zoom = attrs.remove("zoom")
		def vmargin = attrs.remove("vmargin")
		vmargin = vmargin ? vmargin : "0"
		out << g.render(plugin:"sq-maps", template:"/map", model:[
			src:src, 
			id:id, 
			lat:lat, lng:lng,
			width:width, height:height,
			zoom:zoom,
			margin:vmargin])
	}
}
