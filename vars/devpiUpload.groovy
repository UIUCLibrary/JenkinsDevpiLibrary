import org.ds.devpi.DevPiUploader

def call(Map args) {
    // Run Devpi client with upload command
    def defaultArgs = [
            certsDir: "certs\\",
            credentialsId: "DS_devpi",
            distPath: "dist"
    ]

    args = defaultArgs << args
    DevPiUploader uploader = new DevPiUploader(args.devpiExecutable)
    uploader.index = args.index
    uploader.certsDir = args.certsDir
    uploader.url = args.url
    uploader.distPath = args.distPath

    withCredentials([usernamePassword(credentialsId: "${args.credentialsId}", usernameVariable: 'DEVPI_USERNAME', passwordVariable: 'DEVPI_PASSWORD')]) {
        uploader.userName = "${DEVPI_USERNAME}"
        uploader.userPassword = "${DEVPI_PASSWORD}"
    }
    echo "Uploading from ${NODE_NAME}"

    def use_command = "${uploader.buildUseCommand()}".replace("\\", "\\\\")
    bat(
            script: "${use_command}",
            label: "Configuring DevPi to use server ${tester.url}",
    )

//    For some reason I need to remove any ^ characters
//    Escape $ characters
    def logginCommand = "${uploader.buildLogInCommand()}".replace("\$", "`\$").replace("^", "")
    powershell(
            label: "Logging into DevPi server",
            script: "& ${logginCommand}"
    )

    def index_command = "${uploader.buildSelectIndexCommand()}".replace("\\", "\\\\")
    bat(
        label: "Selecting DevPi index, ${tester.index}",
        script: "${index_command}"
    )

    def upload_command = "${uploader.buildUploadCommandString()}".replace("\\", "\\\\")
    bat(
            label: "Uploading Python packages in ${uploader.distPath} to DevPi index, ${uploader.index}",
            script: "${upload_command}"
    )
}
