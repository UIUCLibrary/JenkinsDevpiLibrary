package org.ds.devpi

class DevPiUploader extends AbstractDevPiCommand{
    public String distPath

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
        return command
    }


}
