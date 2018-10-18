def call(String devpiExecutable){
    script {
        bat "${devpiExecutable} --version"
    }
}