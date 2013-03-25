package sq.commons



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(MapsTagLib)
class MapsTagLibTests {

    void testApiTag() {
        def res = applyTemplate('<maps:api />') 
		println res
		assertOutputMatches (/.*key=.*/, '<maps:api />')
    }
}
