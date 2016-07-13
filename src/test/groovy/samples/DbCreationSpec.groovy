package samples

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest
import eu.tjago.util.DbUtil
import spock.lang.Specification

/**
 * Created by tjago on 13.07.2016.
 */
class DbCreationSpec extends Specification {

    @ObjectUnderTest
    DbUtil dbUtil = new DbUtil();

    def setup() {
    }

    def "create Db Schema for test Database"() {
        setup:
            dbUtil.configureDbUtil("localhost", "testuser", "password123", "test", "3306")
            dbUtil.createTables();
        expect:
            true
    }
}
