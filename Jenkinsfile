//@Library ("devpi") _
//library "my-shared-library@$BRANCH_NAME"
//@Library (devpiVersion) _
//library identifier: 'DevPi', retriever: modernSCM([$class: 'GitSCMSource', credentialsId: '', id: '98ab3564-a34c-4645-acbf-e2fe00d3b429', remote: 'https://github.com/UIUCLibrary/JenkinsDevpiLibrary.git', traits: [[$class: 'LocalBranchTrait']]])
//library identifier: 'DevPi', retriever: modernSCM([$class: 'GitSCMSource', credentialsId: '', id: '0db438ef-5a98-459e-ad7c-0fda6048ee2c', remote: 'https://github.com/UIUCLibrary/JenkinsDevpiLibrary.git', traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait'], [$class: 'LocalBranchTrait']]]) _

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
                library "devpi@$BRANCH_NAME"
                devpiVersion("venv\\Scripts\\devpi.exe")
            }
        }
        stage("Test devpiTest"){
            steps{
                library "devpi@$BRANCH_NAME"
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
