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
                devpiVersion("venv/Scripts/devpi.exe")
            }
        }
    }
}
