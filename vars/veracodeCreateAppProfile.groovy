def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the app profile creation process for Transbank's POV considering they have to populate a couple of custom
    //fields (product_name, product_id); in this case it's neccesary to use wrappers to create app profile incluidng custom fields
    def appProfileName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def strJavaWrapperLocation = new String (".")
    echo "[INFO] Veracode - Creating a New App Profile"
    echo "[INFO] REPO NAME: ${REPO_NAME}"
    echo "[INFO] APP PROFILE NAME: ${appProfileName}"
    
    //We have to download the JAVA Wrapper
    echo '[INFO] ------------------------------------------------------------------------'
    echo '[INFO] DOWNLOADING VERACODE JAVA WRAPPER'
    //response = "curl https://repo1.maven.org/maven2/com/veracode/vosp/api/wrappers/vosp-api-wrappers-java/24.7.14.0/vosp-api-wrappers-java-24.7.14.0.jar -o VeracodeJavaAPI.jar".execute().text
    //echo "response: ${response}"
    //response = "curl https://repo1.maven.org/maven2/com/veracode/vosp/api/wrappers/vosp-api-wrappers-java/24.7.14.0/vosp-api-wrappers-java-24.7.14.0.jar -o VeracodeJavaAPI.jar".execute().text
    try {
        response = "curl https://repo1.maven.org/maven2/com/veracode/vosp/api/wrappers/vosp-api-wrappers-java/24.7.14.0/vosp-api-wrappers-java-24.7.14.0.jar -o VeracodeJavaAPI.jar".execute().text
        echo "response: ${response}"
    } catch(Exception ex) {
        println(ex.text)
    }







    //def shellScript = libraryResource 'com/oseduardo/scripts/linux/veracodeCreateAppProfile.sh'
    //echo "${shellScript}"
    //sh "printf '${shellScript}' > myShellScriptFile.sh"
    //sh "printf '#/bin/bash pwd' > testFile.sh"
    //sh "ls"
    //sh "cat myShellScriptFile.sh"
    //sh "ls"

    //Using class ProcessBuilder 
    //echo "[INFO] Starting ProcessBuilder..."

    myOutput = "pwd".execute().text
    echo "myOutput: ${myOutput}"


    //def pb = new ProcessBuilder("sh", "-c", "pwd").inheritIO()
    //def pb = new ProcessBuilder("./testFile.sh").inheritIO()
    //Map<String, String> env = pb.environment()
    //env.put( "API-ID", "${VID}" )
    //env.put( "API-Key", "${VKEY}" )
    //env.put( "AppName", "${appProfileName}" )
    //env.put( "ProductName", "${PRODUCT_NAME}" )
    //env.put( "ProductID", "${PRODUCT_ID}" )
    //Process p = pb.start()
    //InputStream myInputStream = p.getInputStream()
    //InputStreamReader myIsr = new InputStreamReader(myInputStream)
    //char[] cbuf = new char[2048];  //read 1024 characters, increse to higher amount if necessary
    //myIsr.read(cbuf);
    //log.info(new String(cbuf))
    //p.waitFor()
    //echo "[INFO] ProcessBuilder Finished"




    //def script = "myShellScriptFile.sh"
    //def varAPIID = "${VID}"
    //def varAPIKey = "${VKEY}"
    //def varAppProfileName = "${appProfileName}"
    //def varProductName = "${PRODUCT_NAME}"
    //def varProductID = "${PRODUCT_ID}"
    
    // we have to create HashMap from HashMap here! 
    // // Note the result of method! 
    // // // https://docs.oracle.com/javase/7/docs/api/java/lang/System.html#getenv() 
    // // // "Returns an unmodifiable string map view of the current system environment." 
    //myenv = new HashMap(System.getenv())
    //myenv.put("API-ID", "${VID}" ) 
    //myenv.put("API-Key", "${VKey}" ) 
    //myenv.put("AppName", "${appProfileName}" ) 
    //myenv.put("ProductName", "${PRODUCT_NAME}" ) 
    //myenv.put("ProductID", "${PRODUCT_ID}" ) 
    // // we have to convert to array before calling execute 
    //String[] envarray = myenv.collect { k, v -> "$k=$v" }

    //def std_out = new StringBuilder()
    //def std_err = new StringBuilder()

    //proc = script.execute( envarray, null )

    //proc.consumeProcessOutput(std_out, std_err)

    //println std_out

}