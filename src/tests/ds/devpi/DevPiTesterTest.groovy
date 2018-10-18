package tests.ds.devpi
import org.ds.devpi.DevPiTester



class DevPiTesterTest extends GroovyTestCase{

    void testNormal(){
        def tester = new DevPiTester("devpi.exe")
        tester.url = "https://devpi.library.illinois.edu"
        tester.index = "hborcher/dev"
        tester.pkgName = "pyhathiprep"
        tester.pkgVersion = "0.0.1"
        tester.pkgRegex = "zip"
        assertToString(tester.buildTestCommandString(), "devpi.exe --index hborcher/dev pyhathiprep==0.0.1 -s zip --clientdir certs\\")
    }

    void testNormalsAnyVersion() {
        def tester = new DevPiTester("devpi.exe")
        tester.url = "https://devpi.library.illinois.edu"
        tester.index = "hborcher/dev"
        tester.pkgName = "pyhathiprep"

        assertToString(tester.buildTestCommandString(), "devpi.exe --index hborcher/dev pyhathiprep --clientdir certs\\")
    }
    void testNoDevpiExecutable() {
        shouldFail {
            def tester = new DevPiTester()
            tester.buildTestCommandString()
        }

    }
}
