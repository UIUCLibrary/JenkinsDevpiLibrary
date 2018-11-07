pipeline{
    agent{
        label "Windows && Python3"
    }
    stages{
        stage("get devpi in a venv"){
            steps{
                bat "${tool 'CPython-3.6'} -m venv venv"
                bat "venv\\Scripts\\python.exe -m pip install pip --upgrade --quiet"
                bat "venv\\Scripts\\python.exe -m pip install devpi-client detox==0.13 tox==3.2.1"
            }
        }
        stage("Test Devpi Version"){
            steps{
                library "devpi@$BRANCH_NAME"
                devpiVersion("venv\\Scripts\\devpi.exe")
            }
        }

        stage("Tests"){
            parallel{
                stage("test devpiTest simple"){
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
                                pkgVersion: "0.0.1",
                                pkgRegex: "zip",
                                detox: true

                        )
                    }
                }
                stage("test devpiTest environemtn"){
                    steps{
                        library "devpi@$BRANCH_NAME"
                        devpiTest(
                                devpiExecutable: "venv\\Scripts\\devpi.exe",
                                url: "https://devpi.library.illinois.edu",
                                index: "hborcher/dev",
                                pkgName: "pyhathiprep",
                                pkgVersion: "0.0.1",
                                pkgRegex: "zip",
                                toxEnvironment: "py36"

                        )
                    }
                }
            }

        }


    }

}
