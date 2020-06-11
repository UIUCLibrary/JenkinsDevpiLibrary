pipeline{
    agent none
//     agent{
//         label "Windows && Python3"
//     }

    stages{
//         stage("Creating a Python venv"){
// //             environment{
// //                 path = "${tool 'CPython-3.6'};${PATH}"
// //             }
//             steps{
//                 bat(
//                     label: "Create a new Python Virtual Environment",
//                     script: "python -m venv venv"
//                 )
//             }
//         }
        stage("Tests"){

            stages{
                stage("test devpiTest simple on linux"){
                    agent {
                        dockerfile {
                            filename 'ci/docker/linux/Dockerfile'
                            label 'linux && docker'
                        }
                    }
                    steps{
                        lock("devpi-library"){
                            library "devpi@$BRANCH_NAME"
                        }
                        script{
                            def devpi_exec = sh( returnStdout: true, script: "which devpi").trim()
                            devpiTest(
                                    devpiExecutable: devpi_exec,
                                    url: "https://devpi.library.illinois.edu",
                                    index: "hborcher/dev",
                                    pkgName: "pyhathiprep",
                                    pkgVersion: "0.0.1",
                                    pkgRegex: "zip"

                            )
                        }
                    }
                }
                stage("test devpiTest simple on Windows"){
                    agent {
                        dockerfile {
                            filename 'ci/docker/windows/Dockerfile'
                            label 'Windows && docker'
                        }
                    }
                    steps{
                        lock("devpi-library"){
                            library "devpi@$BRANCH_NAME"
                        }
                        script{
                            def devpi_exec = powershell(returnStdout: true,
                                                        script: "(Find-Command devpi).path").trim()
                            devpiTest(
                                    devpiExecutable: devpi_exec,
                                    url: "https://devpi.library.illinois.edu",
                                    index: "hborcher/dev",
                                    pkgName: "pyhathiprep",
                                    pkgVersion: "0.0.1",
                                    pkgRegex: "zip"

                            )
                        }
                    }
                }
//                 stage("Test devpiTest detox"){
//                     steps{
//                         library "devpi@$BRANCH_NAME"
//                         devpiTest(
//                                 devpiExecutable: "venv\\Scripts\\devpi.exe",
//                                 url: "https://devpi.library.illinois.edu",
//                                 index: "hborcher/dev",
//                                 pkgName: "pyhathiprep",
//                                 pkgVersion: "0.0.1",
//                                 pkgRegex: "zip",
//                                 detox: true
//
//                         )
//                     }
//                 }
                stage("test devpiTest environemtn"){
                    agent {
                        dockerfile {
                            filename 'ci/docker/linux/Dockerfile'
                            label 'linux && docker'
                        }
                    }
                    steps{
                        library "devpi@$BRANCH_NAME"
                        script{
                            def devpi_exec = sh( returnStdout: true, script: "which devpi").trim()

                            devpiTest(
                                    devpiExecutable: "${devpi_exec}",
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
    post{
        cleanup{
            deleteDir()
        }
    }

}
