package sq.maps


import groovy.json.JsonSlurper

/**
 * GeoCoderService
 * <p/>
 * Service to query latitude/longitude data for an given address.
 * <p/>
 * For more interface details refer to
 * {@url http://code.google.com/apis/maps/documentation/geocoding/}
 *
 * @author david.trattnig
 * @since 0.1
 */
class GeoCoderService {

	private locationCache = [ : ]
	
	//TODO provide way using alternative service such as geonames:
	//def serviceUrl = "http://ws.geonames.org/search?name_equals=${query}&style=full"
	def serviceUrl = { query ->
		def url = "http://maps.google.com/maps/api/geocode/json?address=${query}&sensor=false"
		log.debug "REQUEST: $url"
		url
	}
	
	
	/**
	 * Calculates the distances between two points in km using the Haversine formula (rounded).
	 *
	 * @param posLat
	 * @param posLng
	 * @param otherLat
	 * @param otherLng
	 * @return map holding following elements:
	 */
	public Integer calculateDistance(double posLat, double posLng, double otherLat, double otherLng) {

		def final AVG_EARTH_RADIUS = 6378.145
	    def latDistance = Math.toRadians(posLat - otherLat)
	    def lngDistance = Math.toRadians(posLng - otherLng)

	    def a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
	                Math.cos( Math.toRadians(posLat)) * Math.cos( Math.toRadians(otherLat)) *
	                Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2)
					
	    def c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1 - a))
	    return (Integer) (Math.round( c * AVG_EARTH_RADIUS))
	}
	
	/**
	 * Retrieves latitude/longitude for the given address.
	 *
	 * @param address address delimited by commas
	 * @return map holding following elements:
	 * <ul>
     *   <li>address ... formatted address from GeoCoder service</li>
     *   <li>longitude</li>
     *   <li>latitude</li>
	 *	 <li>countryCode ... ISO 3166-1 alpha-2 country code</li>
     * </ul>
	 */
    public Map findLocation(String address, encode=false) {
	
		def result = [ : ]
		address = encode ? address.trim().encodeAsURL() : address.trim().replaceAll(" ", "+")
		
		//try to used cached location
		if (locationCache.get(address)) {
			result = locationCache.get(address)
			log.debug "Found cached location $result"
		} else {
		
			def requestUrl = serviceUrl(address)
			def data = new URL(requestUrl).text
			def jsonParser = new JsonSlurper().parseText(data)
			
			if (data && jsonParser) {
				if (jsonParser.results) {
					result.countryCode = 
					result.address = jsonParser.results[0]."formatted_address".toString()
					result.latitude = jsonParser.results[0].geometry.location.lat.toString()
					result.longitude = jsonParser.results[0].geometry.location.lng.toString()
					result.countryCode=""
					jsonParser.results[0]."address_components".each {
						if (it.types.contains("country")) {
							result.countryCode = it."short_name"
						}
					}
					locationCache.put(address, result)
				} else {
					if (jsonParser.status == "OVER_QUERY_LIMIT") {
						sleep(2000)
						result = findLocation(address, encode)
						if ( !result) {
							log.error("Daily query limit exceeded!")
						} else {
							log.debug("Found result after waiting 2 seconds...")
						}
					}
					log.error "invalid response '${jsonParser.status}' from ${requestUrl}"
				}
			} else {
				log.error "no response from ${requestUrl}"
			}
		}
		return result
    }
}
