def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent running the stage that is invoking this method


    /******************************************************/
    //ESTAS LINEAS DEBEN CAMBIAR PARA QUE QUEDEN FUNCIONANDO CON LOS PARAMETROS RESPECTIVOS
    //def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def wkspName = new String("twenty-CRM")
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
            while(intIndex < intWorkspaces && wkspID == ""){
                if(jsonWorkspaces._embedded.workspaces[intIndex].name == wkspName) {wkspID = jsonWorkspaces._embedded.workspaces[intIndex].id}
                intIndex = intIndex + 1
            }

            if(wkspID != "") { //A workspace with wkspName name exists!
                println("Workspace exists. Workspace ID: ${wkspID}")

                //Get CLI agent created for that specific workspace, by using workspace ID (wkspID)
                //We are working with a naming convention for agents in this prospect: <wkspName>_CLI_Agent
                sh "http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces/${wkspID}/agents > agents.json"
                def jsonAgents = readJSON file: 'agents.json'
                def intAgents = jsonAgents._embedded.agents.size()
                if(intAgents != 0) {
                    println("There are agents - COMO VAMOS VAMOS BIEN")
                    println("Agents number: ${intAgents}")
                }
            }
            else {
                println("Aqui hay que crear un nuevo workspace!")
                /*********************************************/
                //To create a new workspace with name wkspName
                /*********************************************/
            }
        }
        else{
            //A new workspace is created
            echo "There are no workspaces"
            echo "[INFO] CREATING WORKSPACE ${wkspName}..."            
        }

    } catch(Exception ex) {
        println(ex)
    }
}