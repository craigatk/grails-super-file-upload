<html>
  <head>
    <title>Multi-File Upload</title>
    <meta name="layout" content="main" />

    <sfu:generateConfiguration fileSize="30" form="uploadForm" buttonImageFile="/ButtonNoText_61x22.png" buttonWidth="61" buttonHeight="22" buttonText="Browse" buttonTextLeftPadding="8" buttonTextTopPadding="1" fileUploadLimit="30" fileTypes="*.htm;*.html" fileTypesDescription="HTML Files" />
  </head>
  <body>
    <form id="uploadForm" name="uploadForm" action="upload" onsubmit="return sfuSubmitForm(this);">
      Choose file: <sfu:fileUploadControl/>

      Progress: <sfu:fileUploadProgressBar/> <br/>

      <input type="submit" value="Save" />

    </form>

    <div>
      <g:if test="${flash.message}">
        <div class="message" name="flashMessage">${flash.message}</div>
      </g:if>
    </div>
  </body>
</html>