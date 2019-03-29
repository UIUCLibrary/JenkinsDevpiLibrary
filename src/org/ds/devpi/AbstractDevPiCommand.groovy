package org.ds.devpi

abstract class AbstractDevPiCommand implements Serializable {
    public String url
    public String index
    public String devpiExecutable
    public String certsDir = "certs\\"

    def buildLogInCommand(){
        def command = ""
        command += '"' + devpiExecutable + '"'
        command += ' "login" "' + userName + '"'
        command += ' "--password" "' + userPassword + '"'
        if(certsDir){
            command += ' "--clientdir" "' + certsDir + '"'
        }
        command += ' "-y"'

        return command
    }

    def buildUseCommand() {

        def command = ""
        command += '"' + devpiExecutable + '"'
        command += ' "use" "' + url + '"'

        if(certsDir){
            command += ' "--clientdir" "' + certsDir + '"'
        }
        return command
    }

    def buildSelectIndexCommand() {
        def command = ""
        command += '"' + devpiExecutable + '"'
        command += ' "use" "' + index + '"'

        if(certsDir){
            command += ' "--clientdir" "' + certsDir + '"'
        }
        return command
    }
}
