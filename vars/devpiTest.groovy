import org.ds.devpi.DevPiTester

def call(Map args) {
    // Run Devpi client with test command

    def defaultArgs = [
        certsDir: "certs\\",
        pytestArgs: "-vv",
        credentialsId: "DS_devpi",
        detox: false
    ]

    args = defaultArgs << args


    DevPiTester tester = new DevPiTester(args.devpiExecutable)

    tester.index = args.index
    tester.pkgName = args.pkgName
    tester.pkgRegex = args.pkgRegex
    tester.certsDir = args.certsDir
    tester.pkgVersion = args.pkgVersion
    tester.url = args.url
    tester.detox = args.detox

    if(args.containsKey("toxEnvironment")){
        tester.toxEnvironment = args.toxEnvironment
    }


    withCredentials([usernamePassword(credentialsId: "${args.credentialsId}", usernameVariable: 'DEVPI_USERNAME', passwordVariable: 'DEVPI_PASSWORD')]) {
        tester.userName = "${DEVPI_USERNAME}"
        tester.userPassword = "${DEVPI_PASSWORD}"
    }
    echo "Testing on ${NODE_NAME}"
    if(isUnix()){
        def use_command = "${tester.buildUseCommand()}".replace("\\", "\\\\")
        sh(script: "${use_command}",
           label: "Configuring DevPi to use server ${tester.url}",
        )
        def logginCommand = "${tester.buildLogInCommand()}".replace("\$", "`\$").replace("^", "")
        sh(label: "Logging into DevPi server",
           script: "& ${logginCommand}"
        )
        def index_command = "${tester.buildSelectIndexCommand()}".replace("\\", "\\\\")
        sh(label: "Selecting DevPi index, ${tester.index}",
           script: "${index_command}"
        )

        def test_command = "${tester.buildTestCommandString()}".replace("\\", "\\\\")
        withEnv(["PYTEST_ADDOPTS=${args.pytestArgs}"]) {
            sh(label: "Running Tests DevPi packages ${tester.pkgName}, version ${tester.pkgVersion} with ${args.pkgRegex}",
               script: test_command
            )
        }
    } else {
        def use_command = "${tester.buildUseCommand()}".replace("\\", "\\\\")
        bat(
            script: "${use_command}",
            label: "Configuring DevPi to use server ${tester.url}",
        )

    //    For some reason I need to remove any ^ characters
    //    Escape $ characters
        def logginCommand = "${tester.buildLogInCommand()}".replace("\$", "`\$").replace("^", "")
        powershell(
            label: "Logging into DevPi server",
            script: "& ${logginCommand}"
        )

        def index_command = "${tester.buildSelectIndexCommand()}".replace("\\", "\\\\")
        bat(
            label: "Selecting DevPi index, ${tester.index}",
            script: "${index_command}"
        )

        def test_command = "${tester.buildTestCommandString()}".replace("\\", "\\\\")
        withEnv(["PYTEST_ADDOPTS=${args.pytestArgs}"]) {
            bat(
                label: "Running Tests DevPi packages ${tester.pkgName}, version ${tester.pkgVersion} with ${args.pkgRegex}",
                script: test_command
            )
        }
    }


}
