package sq.commons


class CountryTagLib {

	static namespace = "sq"
	
	def country = { attrs ->
		
		def country
		def code = attrs.remove("code")
		def dflt = attrs.remove("default")
		
		if (code) {
			country = CountryLocation.byCode(code, "sname")
			out << (country ? country : dflt)
		}		
	
	}
	
	def countrylist = { attrs, body ->
	
		def name = attrs.remove("name")
		def value = attrs.remove("value")
		def noSelection = attrs.remove("noSelection")
		def states = attrs.remove("states")
		def from = null

		if (states) {
			from = CountryLocation.listStates(states)
		} else {
			from = CountryLocation.list()
		}
		
		out << g.select(name: name, value: value, noSelection: noSelection, from: from, optionKey:"code", optionValue:"sname")		
	}


}
