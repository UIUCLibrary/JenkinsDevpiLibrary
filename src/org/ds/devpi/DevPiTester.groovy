package org.ds.devpi

class DevPiTester implements Serializable{
    public url
    public index
    public pkgName
    public pkgRegex
    public certsDir = "certs\\"
    public devpiExecutable
    public pkgVersion

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
}
