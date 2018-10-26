pipeline{
    agent{
        label "Windows && Python3"
    }
    stages{
        stage("get devpi in a venv"){
            steps{
                bat "${tool 'CPython-3.6'} -m venv venv"
                bat "venv\\Scripts\\python.exe -m pip install pip --upgrade --quiet"
                bat "venv\\Scripts\\python.exe -m pip install devpi-client detox"
            }
        }
        stage("Test Devpi Version"){
            steps{
                library "devpi@$BRANCH_NAME"
                devpiVersion("venv\\Scripts\\devpi.exe")
            }
        }
        stage("Test devpiTest"){
            parallel{
                stage("devpiTest simple"){
                    steps{
                        library "devpi@$BRANCH_NAME"
                        devpiTest(
                                devpiExecutable: "venv\\Scripts\\devpi.exe",
                                url: "https://devpi.library.illinois.edu",
                                index: "hborcher/dev",
                                pkgName: "pyhathiprep",
                                pkgVersion: "0.0.1",
                                pkgRegex: "zip"

                        )
                    }
                }
                stage("Test devpiTest detox"){
                    steps{
                        library "devpi@$BRANCH_NAME"
                        devpiTest(
                                devpiExecutable: "venv\\Scripts\\devpi.exe",
                                url: "https://devpi.library.illinois.edu",
                                index: "hborcher/dev",
                                pkgName: "pyhathiprep",
                                pkgVersion: "0.1.0",
                                pkgRegex: "zip",
                                detox: true

                        )
                    }
                }
            }

        }


    }

}
