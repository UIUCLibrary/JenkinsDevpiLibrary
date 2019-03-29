package tests.ds.devpi
import org.ds.devpi.DevPiUploader

class DevPiUploaderTest extends GroovyTestCase{
    void testNormalUploadCommand(){
        def uploader = new DevPiUploader("devpi.exe")
        uploader.url = "https://devpi.library.illinois.edu"
        uploader.index = "hborcher/dev"
        uploader.distPath = "dist"
        assertToString(uploader.buildUploadCommandString(), '"devpi.exe" "upload" "--from-dir" "dist"')
    }
}
