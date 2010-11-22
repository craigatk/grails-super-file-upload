var swfUploadControlActive = false;

function sfuSubmitForm(form) {
    try {
		if(swfUploadControlActive == true){
			document.getElementById("swfupload_progressBar").style.display = "block";
			swfu.startUpload();
			return false;
		}else{
			return true;
		}
    } catch (ex) {
		alert("Unable to submit the form: " + ex)
    }
    return false;
}

function uploadDone() {

}

function swfUploadLoaded() {
    var defaultFileInput = document.getElementById("swfupload_file");
    defaultFileInput.innerHTML = "";
	defaultFileInput.style.display = "none";
    
	var control = document.getElementById("swfupload_control");
	var tempParent = document.createElement("span");
	var elementsString = '<input id="swfupload_uploaded_file_id" type="hidden" name="uploadedFileId"><input id="swfupload_text" ' + this.customSettings.textbox_class + ' type="text" disabled="disabled">';
	tempParent.innerHTML = elementsString;
	control.appendChild(tempParent, control.firstChild);

	updateProgressBar(0);

	swfUploadControlActive = true;

}

function fileDialogStart() {
    var txtFileName = document.getElementById("swfupload_text");
    txtFileName.value = "";
    try{
        this.cancelUpload();
        this.setButtonDisabled(false);
    } catch (e) {
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
}

function fileQueued(file) {
    try {
        var txtFileName = document.getElementById("swfupload_text");
        txtFileName.value = file.name;
    } catch (e) {
    }
}

function fileQueueError(file, errorCode, message) {
    try {
        // Handle this error separately because we don't want to create a FileProgress element for it.
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
                return;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                alert("The file you selected is too big.");
                return;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                alert("The file you selected is empty.  Please select another file.");
                return;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                alert("The file you choose is not an allowed file type.");
                return;
            default:
                alert("An error occurred in the upload. Try again later.");
                return;
        }
    } catch (e) {
    }
}

function uploadStart(file){
    this.setButtonDisabled(true);
}

function uploadProgress(file, bytesLoaded, bytesTotal) {

    try {
        var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
        $("#swfu_progress_bar").progressBar(percent);
    } catch (e) {
    }
}

function uploadSuccess(file, serverData) {
    try {
        if (serverData == " ") {
            this.customSettings.upload_successful = false;
        } else {
            this.customSettings.upload_successful = true;
            document.getElementById("swfupload_uploaded_file_id").value = serverData;
        }

    } catch (e) {
    }
}

function uploadComplete(file) {
    try {
        if (this.customSettings.upload_successful) {
            try {
                var uploadForm = document.getElementById(this.customSettings.formId);
                uploadForm.submit();
            } catch (ex) {
                alert("Error submitting form");
            }
        } else {
            alert("There was a problem with the upload.\nThe server did not accept it.");
        }
    } catch (e) {
    }
}

function uploadError(file, errorCode, message) {
    try {

        if (errorCode === SWFUpload.UPLOAD_ERROR.FILE_CANCELLED) {
            // Don't show cancelled error boxes
            this.setButtonDisabled(false);
            return;
        }

        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
                alert("Swfupload Configuration error.  You did not specify a valid upload url.");
                return;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                alert("You may only upload 1 file.");
                return;
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                break;
            default:
                alert("SwfUpload error occurred (code: " + errorCode + "). Try again later.");
                return;
        }

        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
                alert("Upload Error");
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
                alert("Upload Failed.");
                break;
            case SWFUpload.UPLOAD_ERROR.IO_ERROR:
                alert("Server (IO) Error");
                break;
            case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
                alert("Security Error");
                break;
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
                alert("Upload Cancelled");
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                alert("Upload Stopped");
                break;
        }
    } catch (ex) {
    }
}