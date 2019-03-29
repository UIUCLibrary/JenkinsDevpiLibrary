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

    bat(
        label: "Configuring DevPi to use server ${tester.url}",
        script: "${tester.buildUseCommand()}"
    )

    powershell(
        label: "Logging into DevPi server",
        script: "cmd.exe /c \"${tester.buildLogInCommand()}\""
    )
    bat(
        label: "Selecting DevPi index, ${tester.index}",
        script: "${tester.buildSelectIndexCommand()}"
    )

    withEnv(["PYTEST_ADDOPTS=${args.pytestArgs}"]) {
        bat(
            label: "Running Tests DevPi packages ${tester.pkgName}, version ${tester.pkgVersion} with ${args.pkgRegex}",
            script: "${tester.buildTestCommandString()}"
        )
    }

}