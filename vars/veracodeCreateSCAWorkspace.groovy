def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent is running the stage that is invoking this method

    //This procedure assumes an Organizational CLI Agent has been created previously in SCA (ABS), with its Access Token registered in the tool
    //that will be invoking it, in this case, Jenkins.
    //The procedure will get the workspace's site_id, and it will be set up in the env variable SRCCLR_WORKSPACE_SLUG, which will be used
    //when the SCA scan takes place

    /******************************************************/
    //ESTAS LINEAS DEBEN CAMBIAR PARA QUE QUEDEN FUNCIONANDO CON LOS PARAMETROS RESPECTIVOS
    //def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def wkspName = new String("verademo_jenkins")
    /******************************************************/


    //Validate if a Workspace with the name wkspName exists
    try {
        //Get workspaces list
        sh 'http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces > workspaces.json'
        def jsonWorkspaces = readJSON file: 'workspaces.json'
        def intWorkspaces = jsonWorkspaces._embedded.workspaces.size()
        if(intWorkspaces != 0) {
            def intIndex = 0
            def wkspID = ""
            def siteID = ""
            //Get site_id for that specific workspace, by using workspace ID (wkspID)
            while(intIndex < intWorkspaces && siteID == ""){
                if(jsonWorkspaces._embedded.workspaces[intIndex].name == wkspName) {siteID = jsonWorkspaces._embedded.workspaces[intIndex].site_id}
                intIndex = intIndex + 1
            }

            if(siteID != "") { //A workspace with wkspName name exists! It will set up SRCCLR_WORKSPACE_SLUG env variable with site_id value
                println("[INFO] Workspace exists.") 
                println("[INFO] Workspace name: ${wkspName}")
                println("[INFO] Workspace's Site ID: ${siteID}")
                sh "export SRCCLR_WORKSPACE_SLUG=${siteID}"
                println("[INFO] Env variable SRCCLR_WORKSPACE_SLUG has been set up!")
                sh 'echo %SRCCLR_WORKSPACE_SLUG'
                return
            }
            else {
                println("Aqui hay que crear un nuevo workspace!")
                /*********************************************/
                //To create a new workspace with name wkspName
                /*********************************************/
            }
        }
        else{
            println("Aqui hay que crear un nuevo workspace!")
            /*********************************************/
            //To create a new workspace with name wkspName
            /*********************************************/
        }

    } catch(Exception ex) {
        println(ex)
    }
}