//Library (@Devpi) _
library identifier: 'DevPi', retriever: modernSCM([$class: 'GitSCMSource', credentialsId: '', id: '98ab3564-a34c-4645-acbf-e2fe00d3b429', remote: 'https://github.com/UIUCLibrary/JenkinsDevpiLibrary.git', traits: [[$class: 'LocalBranchTrait']]])

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
                devpiVersion("venv\\Scripts\\devpi.exe")
            }
        }
        stage("Test devpiTest"){
            steps{
                devpiTest(
                        devpiExecutable: "venv\\Scripts\\devpi.exe",
                        url: "https://devpi.library.illinois.edu",
                        index: "hborcher/dev",
                        pkgName: "pyhathiprep==0.0.1",
                        pkgRegex: "zip"

                )
            }
        }

    }
}
