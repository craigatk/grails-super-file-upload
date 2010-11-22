package com.solution51.sfu

class SfuUploadController {
    SuperFileUploadService superFileUploadService

    def index = { }

    def upload = {
        def uploadedFiles = superFileUploadService.getUploadedFiles(params)

        def uploadedFileNames = new StringBuffer()

        for (uploadedFile in uploadedFiles) {
            uploadedFileNames.append(uploadedFile.originalFileName + ", ")
        }

        flash.message = "Uploaded files '${uploadedFileNames.toString()}'"

        redirect action: "index"
    }
}
