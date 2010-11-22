package com.solution51.sfu

/* Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information please visit www.solution51.com
 * or email info@solution51.com
 * Author: Peter Delahunty
 * Email: peter.delahunty@solution51.com
 * Date: 26-May-2009
*/


class SuperFileUploadTagLib {

    static namespace = 'sfu'

    def fileUploadControl = {attrs, body ->

        def paramName = attrs.paramName?:"sfuFile"

        out << "<div id='swfupload_file'><input type='file' id='$paramName' name='$paramName'></div>"
        out << '<div id="swfupload_control">'
        out << '<span id="swfupload_btn"></span>'
        out << '</div>'


    }

    def fileUploadProgressBar = {attrs, body ->

        def cssClass = (attrs.cssClass) ? "class=\"$attrs.cssClass\"" : ""

        out << "<div id=\"swfupload_progressBar\" $cssClass style=\"display:none\">"
        out << '<span class="progressBar" id="swfu_progress_bar">0%</span>'
        out << '</div>'
    }

    def generateConfiguration = {attrs, body ->

        def controller = attrs.controller ?: "sfuReciever";
        def action = attrs.action ?: "handleFlashUpload";
        def form = attrs.form

        def fileSize = (attrs.fileSize) ?: "10";
        def paramName = (attrs.paramName) ?: "sfuFile";

        def buttonUrl = (attrs.buttonImageFile) ? createResourceSrc(attrs.buttonImageDir ?: "images", attrs.buttonImageFile) : createPluginResourceSrc("js/swfupload/default_button.png")
        def buttonWidth = (attrs.buttonWidth) ?: "61"
        def buttonHeight = (attrs.buttonHeight) ?: "22"

        def progressBarBoxImg = (attrs.progressBarBoxImg) ?: createPluginResourceSrc("images/swfupload/progressbar.gif")
        def progressBarImg = (attrs.progressBarImg) ?: createPluginResourceSrc("images/swfupload/progressbg_green.gif")
        def progressBarHeight = (attrs.progressBarHeight) ?: "12"
        def progressBarWidth = (attrs.progressBarWidth) ?: "120"

        def uploadUrl = g.createLink(controller: controller, action: action);
        def swfUploadFlash = createPluginResourceSrc("js/swfupload/flash/swfupload.swf")

        def useEmbeddedJquery = attrs.useEmbeddedJquery?Boolean.parseBoolean(attrs.useEmbeddedJquery).booleanValue():true

        def cssClass = (attrs.cssClass) ? "class=\"$attrs.cssClass\"" : ""

        writeJavascriptLibs(useEmbeddedJquery)

        out << "\n\n"
        out << '<script type="text/javascript">\n'
        out << '//<![CDATA[\n'


        out << 'var swfu;\n'

        out << '$(document).ready(function() {\n'

        out << 'var settings_object = {\n'

        out << "upload_url : \"$uploadUrl\",\n"
        out << "flash_url : \"$swfUploadFlash\",\n"
        out << "file_size_limit : \"$fileSize MB\",\n"
        out << "file_post_name: \"$paramName\",\n"

        out << 'button_placeholder_id : "swfupload_btn",\n'
        out << "button_image_url : \"$buttonUrl\",\n"
        out << "button_width: $buttonWidth,\n"
        out << "button_height: $buttonHeight,\n"
        out << 'swfupload_loaded_handler : swfUploadLoaded,\n'

        out << 'file_dialog_start_handler: fileDialogStart,\n'
        out << 'file_queued_handler : fileQueued,\n'
        out << 'file_queue_error_handler : fileQueueError,\n'
        out << 'file_dialog_complete_handler : fileDialogComplete,\n'

        out << 'upload_start_handler : uploadStart,\n'
        out << 'upload_progress_handler : uploadProgress,\n'
        out << 'upload_error_handler : uploadError,\n'
        out << 'upload_success_handler : uploadSuccess,\n'
        out << 'upload_complete_handler : uploadComplete,\n'

        out << 'custom_settings : {\n'
        out << "textbox_class : '$cssClass',\n"
        out << "formId : \"$form\",\n"
        out << 'upload_successful : false,\n'
        out << 'error_message_QUEUE_LIMIT_EXCEEDED : "",\n'
        out << 'error_message_FILE_EXCEEDS_SIZE_LIMIT : "",\n'
        out << 'error_message_ZERO_BYTE_FILE : "",\n'
        out << 'error_message_INVALID_FILETYPE : "",\n'
        out << 'error_message_QUEUE_UNKNOWN_ERROR : ""\n'
        out << '}\n'
        out << '};\n'
        out << 'try {\n'
        out << '    swfu = new SWFUpload(settings_object);\n'
        out << '} catch (ex) {\n'
        out << 'alert("exception: " + ex);\n'
        out << '}\n'
        out << "});\n"

        out << 'function updateProgressBar(percent) {\n'
        out << 'var progressBarImages = {\n'
        out << "height:'$progressBarHeight',\n"
        out << "width:'$progressBarWidth',\n"
        out << "boxImage:'$progressBarBoxImg',\n"
        out << "barImage:'$progressBarImg'\n"
        out << '};\n'
        out << '$("#swfu_progress_bar").progressBar(percent,progressBarImages);\n'
        out << '};\n'

        out << '//]]>\n'
        out << '</script>\n'

        def buttonWidthNum = new Integer(buttonWidth) + 2;
        out << '<style type="text/css">\n'
        out << '#swfupload_control {position: relative;}\n'
        out << "#swfupload_text {position: absolute;top: 0;left: 0;margin-left: " + buttonWidthNum + "px;}\n"
        out << "</style>\n"
    }



    private void writeJavascriptLibs(boolean useEmbeddedJquery) {

        def jsUtilSrc = createPluginResourceSrc("js/swfupload/swfupload.js")
        out << "<script type=\"text/javascript\" src=\"$jsUtilSrc\"></script>\n"

        jsUtilSrc = createPluginResourceSrc("js/swfupload/plugins/swfupload.swfobject.js")
        out << "<script type=\"text/javascript\" src=\"$jsUtilSrc\"></script>\n"

        jsUtilSrc = createPluginResourceSrc("js/swfupload/handlers.js")
        out << "<script type=\"text/javascript\" src=\"$jsUtilSrc\"></script>\n"

        if (useEmbeddedJquery) {
            jsUtilSrc = createPluginResourceSrc("js/swfupload/progressbar/jquery.js")
            out << "<script type=\"text/javascript\" src=\"$jsUtilSrc\"></script>\n"
        }

        jsUtilSrc = createPluginResourceSrc("js/swfupload/progressbar/jquery.progressbar.js")
        out << "<script type=\"text/javascript\" src=\"$jsUtilSrc\"></script>\n"


    }

    def createResourceSrc(dirName, fileName) {
        return g.createLinkTo(dir: dirName, file: fileName)
    }

    def createPluginResourceSrc(fileName) {
        return g.createLinkTo(dir: pluginContextPath, file: fileName)
    }

}
