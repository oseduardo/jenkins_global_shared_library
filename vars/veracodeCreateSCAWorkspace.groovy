def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent is running the stage that is invoking this method

    //This procedure assumes an Organizational CLI Agent has been created previously in SCA (ABS), with its Access Token registered in the tool
    //that will be invoking it, in this case, Jenkins.
    //The procedure will get the workspace's site_id, and it will return this value as a String, to be used when the SCA scan takes place
    def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def siteID = ""

    try {
        //Validates if a Workspace with the name wkspName exists
        siteID = getSiteID(wkspName)

        if(siteID == "") { //A workspace with wkspName name doesn't exist! A new one is created
            println("[INFO] Creating ${wkspName} workspace...")
            sh "echo -n '{\"name\": \"${wkspName}\"}' | http --auth-type veracode_hmac POST https://api.veracode.com/srcclr/v3/workspaces"
            strPrueba = holaMundo()
            echo "${strPrueba}"
        }
    } catch(Exception ex) {
        println(ex)
    }

    return siteID //Finally it returns the site_id of the workspace
}

def String getSiteID(String strWkspName){
    def siteID = ""
        
    //Get workspaces list
    sh 'http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces > workspaces.json'
    def jsonWorkspaces = readJSON file: 'workspaces.json'
    def intWorkspaces = jsonWorkspaces._embedded.workspaces.size()
    if(intWorkspaces != 0) {
        def intIndex = 0

        //Get site_id for that specific workspace, by using workspace name (wkspName)
        while(intIndex < intWorkspaces && siteID == ""){
            if(jsonWorkspaces._embedded.workspaces[intIndex].name == wkspName) {siteID = jsonWorkspaces._embedded.workspaces[intIndex].site_id}
            intIndex = intIndex + 1
        }
    }
    
    return strSiteID
}