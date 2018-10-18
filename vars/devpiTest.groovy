def call(Map args) {
    def defaultArgs = [
        certsDir: "certs\\",
        pytestArgs: "-vv",
    ]

    args = defaultArgs << args;
    bat "${DevpiPath} use ${args.devpiUrl} --clientdir ${args.certsDir}"
    test_devpi(args.devpiExecutable, args.index, args.pkgName, args.pkgeRegex)
}

def test_devpi(DevpiPath, DevpiIndex, packageName, PackageRegex, certsDir="certs\\"){

    script{

        withCredentials([usernamePassword(credentialsId: "DS_devpi", usernameVariable: 'DEVPI_USERNAME', passwordVariable: 'DEVPI_PASSWORD')]) {
            bat "${DevpiPath} login DS_Jenkins --clientdir ${certsDir} --password ${DEVPI_PASSWORD}"
            bat "${DevpiPath} use ${DevpiIndex} --clientdir ${certsDir}"
        }
    }
    echo "Testing on ${NODE_NAME}"
    withEnv(['PYTEST_ADDOPTS=-vv']) {
        bat "${DevpiPath} test --index ${DevpiIndex} --verbose ${packageName} -s ${PackageRegex} --clientdir ${certsDir}"
    }
}