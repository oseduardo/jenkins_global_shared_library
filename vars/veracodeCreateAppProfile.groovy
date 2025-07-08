def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the app profile creation process for Transbank's POV considering they have to populate a couple of custom
    //fields (product_name, product_id); in this case it's neccesary to use wrappers to create app profile incluidng custom fields
    def appProfileName = new String("${PRODUCT_ID}_${REPO_NAME}")
    echo "[INFO] Veracode - Creating a New App Profile"
    echo "[INFO] REPO NAME: ${REPO_NAME}"
    echo "[INFO] APP PROFILE NAME: ${appProfileName}"
    
    def shellScript = libraryResource 'com/oseduardo/scripts/linux/veracodeCreateAppProfile.sh'
    //echo "${shellScript} | tee myShellScriptFile.sh"
    sh "printf '${shellScript}' > myShellScriptFile.sh"
    sh "ls"
    sh "cat myShellScriptFile.sh"

    //Using class ProcessBuilder 
    //def pb = new ProcessBuilder("myShellScriptFile.sh").inheritIO()
    //Map<String, String> env = pb.environment()
    //env.put( "API-ID", "${VID}" )
    //env.put( "API-Key", "${VKEY}" )
    //env.put( "AppName", "${appProfileName}" )
    //env.put( "ProductName", "${PRODUCT_NAME}" )
    //env.put( "ProductID", "${PRODUCT_ID}" )
    //Process p = pb.start()
    //p.waitFor()
}