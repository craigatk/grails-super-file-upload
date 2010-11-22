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


import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SfuRecieverController {

    SuperFileUploadService superFileUploadService

    String parameterName

    def handleFlashUpload = {

        String fileParam = getParameterName()

        def fileStream = request.getFile(fileParam);

        if (!fileStream?.empty) {
            try {
                def filename = new UUID(System.currentTimeMillis(), System.currentTimeMillis() * System.currentTimeMillis()).toString()
                File file = new File(filename, superFileUploadService.getTempUploadDirectory());
                fileStream.transferTo(file);
                render filename
                return;
            } catch (Exception e) {
                log.error("Failed to upload file with SFU",e)
                render "FAILED"
            }
        }
        render "FAILED"
    }


    private String getParameterName() {

        if (!parameterName) {

            def sfuConfig = ConfigurationHolder.config?.sfu

            def parameterNameConfig = sfuConfig.paramName

            if (parameterNameConfig) {
                parameterName = parameterNameConfig
            } else {
                parameterName = "sfuFile"
            }
        }
        return parameterName
    }


}
