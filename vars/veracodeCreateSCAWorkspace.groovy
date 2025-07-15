def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent running the stage that is invoking this method

    def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")
    //def jsonSlurper = new JsonSlurper()

    //Validate if a Workspace with the name wkspName exists
    try {
        //Get workspaces list
        sh 'http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces > workspaces.json'
        //sh 'cat workspaces.json'
        //jsonWorkspaces = jsonSlurper.parse(new File('workspaces.json'))
        //echo "${jsonWorkspaces}"
        def jsonWorkspaces = readJSON file: 'workspaces.json'
        def intWorkspaces = jsonWorkspaces._embedded.workspaces.size()
        if(intWorkspaces != 0) {
            def intIndex = 0
            while(intIndex < intWorkspaces){
                println(jsonWorkspaces._embedded.workspaces[intIndex].id.text)
                println(jsonWorkspaces._embedded.workspaces[intIndex].name.text)
                echo "\n"
                intIndex = intIndex++
            }
        }
        else{
            //A new workspace is created
            echo "There are no workspaces"
            echo "[INFO] CREATING WORKSPACE ${wkspName}..."            
        }


        echo "${mySize}"
    } catch(Exception ex) {
        println(ex)
    }
}