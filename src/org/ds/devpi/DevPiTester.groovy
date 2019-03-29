package org.ds.devpi

class DevPiTester implements Serializable{
    public String url
    public String index
    public String pkgName
    public String pkgRegex
    public String certsDir = "certs\\"
    public String devpiExecutable
    public String pkgVersion
    public String userName
    public String userPassword
    public Boolean detox
    public String toxEnvironment

    DevPiTester(devpiExecutable) {
        this.devpiExecutable = devpiExecutable
    }

    def buildTestCommandString(){
        def command = ""

        if(!devpiExecutable){
            throw Exception("No path to devpi Executable defined")
        }
        command += '"' + devpiExecutable + '"'
        command += ' "test"'
        command += ' "--index" "' + index + '"'
        command += ' "' + pkgName

        if(pkgVersion){
            command += "==" + pkgVersion
        }
        command += '"'
        if(pkgRegex){
            command += ' "-s" "' + pkgRegex + '"'
        }

        if(certsDir){
            command += ' "--clientdir" "' + certsDir + '"'
        }

        if(detox){
            command += ' "--detox"'
        }

        if(toxEnvironment){
            command += ' "-e" "' + toxEnvironment + '"'

        }
        return  command
    }

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
