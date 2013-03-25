package sq.maps



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(MapsTagLib)
class MapsTagLibTests {

    void testApiTag() {
        def res = applyTemplate('<map:api />') 
		println res
		assertOutputMatches (/.*key=.*/, '<map:api />')
    }
	
    void testInitMapTag() {
        def res = applyTemplate('<map:render />') 
		println res
		assertOutputMatches (/.*initMap=.*/, '<map:render />')
    }
}
