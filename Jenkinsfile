pipeline{
    agent{
        label "Windows && Python3"
    }
    stages{
        stage("get devpi in a venv"){
            steps{
                bat "${tool 'CPython-3.6'} -m venv venv"
                bat "venv\\Scripts\\python.exe -m pip install pip --upgrade --quiet"
                bat "venv\\Scripts\\python.exe -m pip install devpi-client"
            }
        }
        stage("Test Devpi Version"){
            steps{
                echo "env.GIT_COMMIT = ${env.GIT_COMMIT}"
                library "devpi@${env.GIT_COMMIT}"
                devpiVersion("venv\\Scripts\\devpi.exe")
            }
        }
        stage("Test devpiTest"){
            steps{
                library "devpi@${env.GIT_COMMIT}"
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

    }

}
