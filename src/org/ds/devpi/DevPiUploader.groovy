package org.ds.devpi

class DevPiUploader extends AbstractDevPiCommand{
    public String distPath
    public String certsDir

    DevPiUploader(devpiExecutable) {
        this.devpiExecutable = devpiExecutable
    }


    def buildUploadCommandString (){
        def command = ""
        command += '"' + devpiExecutable + '"'
        command += ' "upload"'
        if(distPath){
            command += ' "--from-dir"'
            command += ' "' + distPath + '"'
        }

        if(certsDir){
            command += ' "--clientdir" "' + certsDir + '"'
        }

        return command
    }


}
