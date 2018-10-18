import org.ds.devpi.DevPiTester

def call(Map args) {

    def defaultArgs = [
        certsDir: "certs\\",
        pytestArgs: "-vv",
    ]

    args = defaultArgs << args


    DevPiTester tester = new DevPiTester(args.devpiExecutable)

    tester.index = args.index
    tester.pkgName = args.pkgName
    tester.pkgRegex = args.pkgRegex
    tester.certsDir = args.certsDir
    tester.pkgVersion = args.pkgVersion
    tester.url = args.url

    echo "Testing on ${NODE_NAME}"

    bat "${tester.buildUseCommand()}"

    withCredentials([usernamePassword(credentialsId: "DS_devpi", usernameVariable: 'DEVPI_USERNAME', passwordVariable: 'DEVPI_PASSWORD')]) {
//        bat "${args.devpiExecutable} login DS_Jenkins --clientdir ${args.certsDir} --password ${DEVPI_PASSWORD}"
        tester.userName = "${DEVPI_USERNAME}"
        tester.userPassword = "${DEVPI_PASSWORD}"
    }
    bat "${tester.buildLogInCommand()}"
//    bat "${args.devpiExecutable} use ${args.index} --clientdir ${args.certsDir}"

    withEnv(["PYTEST_ADDOPTS=${args.pytestArgs}"]) {
        bat "${tester.buildTestCommandString()}"
    }

}