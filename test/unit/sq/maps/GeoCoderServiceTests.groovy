package sq.commons



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GeoCoderService)
class GeoCoderServiceTests {

	@Before
	void setUp() {
		mockCodec(org.codehaus.groovy.grails.plugins.codecs.URLCodec)
	}
	
    void testFindLocation() {

		def service = new GeoCoderService()
		def res = service.findLocation("kärtnerstrasse 3, wien")
		println "geoCode response: $res"
		assert res
		assert res.longitude.getAt(0..5) ==  "16.371" 
		assert res.latitude.getAt(0..5) == "48.207"
		assert res.address
    }
	
	void testFindCountryCode() {
	
		def service = new GeoCoderService()
		def res = service.findLocation("opernring 3, wien")
		println "GEOCODE response: $res"
		assert res
		assert res.address
		assert res.countryCode == "AT"
	//CountryNameCode
	}
}
