package tests.ds.devpi

import org.ds.devpi.DevPiTester

class AbstractDevPiCommandTest extends GroovyTestCase{
    void testLogInCommand(){
        def tester = new DevPiTester("devpi.exe")
        tester.userName = "myusername"
        tester.userPassword = "mypassword"

        assertToString(tester.buildLogInCommand(), '"devpi.exe" "login" "myusername" "--password" "mypassword" "--clientdir" "certs\\" "-y"' )
    }
    void testUseCommand(){
        def tester = new DevPiTester("devpi.exe")
        tester.url = "https://devpi.library.illinois.edu"
        assertToString(tester.buildUseCommand(), '"devpi.exe" "use" "https://devpi.library.illinois.edu" "--clientdir" "certs\\"' )
    }
    void testBuildSelectIndexCommand(){
        def tester = new DevPiTester("devpi.exe")
        tester.index = "hborcher/dev"
        assertToString(tester.buildSelectIndexCommand(), '"devpi.exe" "use" "hborcher/dev" "--clientdir" "certs\\"')
    }
}
