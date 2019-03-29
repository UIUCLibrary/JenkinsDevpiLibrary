package org.ds.devpi

class DevPiTester extends AbstractDevPiCommand{

    public String pkgName
    public String pkgRegex
    public String pkgVersion
    public Boolean detox
    public String toxEnvironment

    DevPiTester(devpiExecutable) {
        this.devpiExecutable = devpiExecutable
    }

    def buildTestCommandString(){
        def command = ""

        if(!devpiExecutable){
            throw Exception("No path to DevPi Executable defined")
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


}
