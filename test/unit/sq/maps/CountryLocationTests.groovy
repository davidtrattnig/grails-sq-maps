package sq.commons



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(CountryLocation)
class CountryLocationTests {

	void testCountryByCode() {

		def country = CountryLocation.byCode("at")
		assert country
		assert country.sname == "Austria"
	}
	
	void testCountryStateByCode() {
	
		def state = CountryLocation.byCode("de").states."de5"
		assert state.sname == "Bremen"
		assert state.latitude == "8.801422"
		assert state.longitude == "53.079178" 
	}

	void testCountryByCodeAndColumn() {

		def country = CountryLocation.byCode("at", "sname")
		assert country
		assert country == "Austria"
	}	

	void testListCountries() {

		def countries = CountryLocation.list()
		assert countries
		assert countries instanceof List
		assert countries.size() == 54
	}
		
	void testListCountriesForCodes() {

		def countries = CountryLocation.list(["al", "at", "xxx", "si"])
		assert countries
		assert countries instanceof List
		assert countries.size() == 3
		println "country0 = "+countries[0]
		assert countries[0].sname == "Albania"
		assert countries[2].sname == "Slovenia"
		assert countries[0].code == "al"
	}
	
}
