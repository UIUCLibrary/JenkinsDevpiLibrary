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

    DevPiTester(devpiExecutable) {
        this.devpiExecutable = devpiExecutable
    }

    def buildTestCommandString(){
        def command = ""

        if(!devpiExecutable){
            throw Exception("No path to devpi Executable defined")
        }
        command += devpiExecutable

        command += " test"

        command += " --index " + index
        command += " " + pkgName

        if(pkgVersion){
            command += "==" + pkgVersion
        }

        if(pkgRegex){
            command += " -s " + pkgRegex
        }

        if(certsDir){
            command += " --clientdir " + certsDir
        }
        return  command
    }

    def buildLoginCommand(){
        def command = ""

        command += devpiExecutable

        command += " login " + userName

        command += " --password " + userPassword

        if(certsDir){
            command += " --clientdir " + certsDir
        }
        command += " -y"

        return command
    }
}
