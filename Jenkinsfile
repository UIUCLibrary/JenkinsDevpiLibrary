pipeline{
    agent{
        label "Windows && Python3"
    }
    stages{
        stage("Creating a Python venv"){
            environment{
                path = "${tool 'CPython-3.6'};${PATH}"
            }
            steps{
                bat(
                    label: "Create a new Python Virtual Environment",
                    script: "python -m venv venv"
                )
            }
        }
        stage("Install DevPi to a venv"){
            environment{
                path = "${WORKSPACE}\\venv\\Scripts\\;${PATH}"
            }
            steps{
                bat(
                    script: "pip install pip --upgrade --quiet",
                    label: "Upgrading pip"
                    )
                bat(
                    script: 'pip install devpi-client "detox==0.13" "tox==3.2.1"',
                    label: "Installing latest devpi client, Tox version 3.2.1, and Detox version 0.13"
                )
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
