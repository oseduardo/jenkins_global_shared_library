def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the app profile creation process for Transbank's POV considering they have to populate a couple of custom
    //fields (product_name, product_id); in this case it's neccesary to use wrappers to create app profile incluidng custom fields
    def appProfileName = new String("${PRODUCT_ID}_${REPO_NAME}")
    echo "[INFO] Veracode - Creating a New App Profile"
    echo "[INFO] REPO NAME: ${REPO_NAME}"
    echo "[INFO] APP PROFILE NAME: ${appProfileName}"
    
    def shellScript = libraryResource 'com/oseduardo/scripts/linux/veracodeCreateAppProfile.sh'

    //Using class ProcessBuilder 
    //def pb = new ProcessBuilder(shellScript).inheritIO()
    //Map<String, String> env = pb.environment()
    //env.put( "API-ID", ${VID} )
    //env.put( "API-Key", ${VKEY} )
    //env.put( "AppName", ${appProfileName} )
    //env.put( "ProductName", ${PRODUCT_NAME} )
    //env.put( "ProductID", ${PRODUCT_ID} )
    //Process p = pb.start()
    //p.waitFor()


    //def script = "./file.sh"
    // we have to create HashMap from HashMap here! 
    // // Note the result of method! 
    // // // https://docs.oracle.com/javase/7/docs/api/java/lang/System.html#getenv() 
    // // // "Returns an unmodifiable string map view of the current system environment." 
    // // myenv = new HashMap(System.getenv()) myenv.put("variable", variable ) 
    // // we have to convert to array before calling execute String[] envarray = myenv.collect { k, v -> "$k=$v" }

    def myenv = new HashMap(System.getenv())
    myenv.put("API-ID",${VID})
    myenv.put("API-Key",${VKEY})
    myenv.put("AppName",${appProfileName})
    myenv.put("ProductName",${PRODUCT_NAME})
    myenv.put("ProductID",${PRODUCT_ID})
    String[] envarray = myenv.collect()

    def std_out = new StringBuilder()
    def std_err = new StringBuilder()

    proc = shellScript.execute( envarray, null )

    proc.consumeProcessOutput(std_out, std_err)

    println std_out
}