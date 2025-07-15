def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent running the stage that is invoking this method

    //def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def wkspName = new String("twenty-CRM")
    
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
            def wkspID = ""
            while(intIndex < intWorkspaces && wkspID == ""){
                //println(jsonWorkspaces._embedded.workspaces[intIndex].id)
                //println(jsonWorkspaces._embedded.workspaces[intIndex].name)
                if(jsonWorkspaces._embedded.workspaces[intIndex].name == wkspName) {wkspID = jsonWorkspaces._embedded.workspaces[intIndex].id}
                intIndex = intIndex + 1
            }
            println("Workspace exists. Workspace ID: ${wkspID}")
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