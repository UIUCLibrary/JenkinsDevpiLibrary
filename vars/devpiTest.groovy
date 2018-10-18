def call(Map args) {
    def defaultArgs = [
        certsDir: "certs\\",
        pytestArgs: "-vv",
    ]

    args = defaultArgs << args;
//    bat "${args.devpiExecutable} use ${args.devpiUrl} --clientdir ${args.certsDir}"

    bat "${args.devpiExecutable} use ${args.url} --clientdir ${args.certsDir}"

    withCredentials([usernamePassword(credentialsId: "DS_devpi", usernameVariable: 'DEVPI_USERNAME', passwordVariable: 'DEVPI_PASSWORD')]) {
        bat "${args.devpiExecutable} login DS_Jenkins --clientdir ${args.certsDir} --password ${DEVPI_PASSWORD}"
        bat "${args.devpiExecutable} use ${args.index} --clientdir ${args.certsDir}"
    }
    test_devpi(args.devpiExecutable, args.index, args.pkgName, args.pkgRegex)
}

def test_devpi(DevpiPath, DevpiIndex, packageName, PackageRegex, certsDir="certs\\"){

    echo "Testing on ${NODE_NAME}"
    withEnv(['PYTEST_ADDOPTS=-vv']) {
        bat "${DevpiPath} test --index ${DevpiIndex} --verbose ${packageName} -s ${PackageRegex} --clientdir ${certsDir}"
    }
}