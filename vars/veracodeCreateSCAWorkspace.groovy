def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent running the stage that is invoking this method


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
            while(intIndex < intWorkspaces && wkspID == ""){
                if(jsonWorkspaces._embedded.workspaces[intIndex].name == wkspName) {wkspID = jsonWorkspaces._embedded.workspaces[intIndex].id}
                intIndex = intIndex + 1
            }

            if(wkspID != "") { //A workspace with wkspName name exists!
                println("[INFO] Workspace exists.") 
                println("[INFO] Workspace ID: ${wkspID}")
                println("[INFO] Workspace name: ${wkspName}")

                //Get CLI agent created for that specific workspace, by using workspace ID (wkspID)
                //We are working with a naming convention for agents in this prospect: <wkspName>_CLI_Agent
                sh "http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces/${wkspID}/agents > agents.json"
                def jsonAgents = readJSON file: 'agents.json'
                def intAgents = 0
                try{
                    intAgents = jsonAgents._embedded.agents.size()
                } catch(NullPointerException nullEx) { //There are no agents for this workspace
                    intAgents = 0
                }
                if(intAgents != 0) {
                    def intAgentsIndex = 0
                    def agentID = ""
                    while(intAgentsIndex < intAgents && agentID == ""){
                        if(jsonAgents._embedded.agents[intAgentsIndex].name == wkspName+"_CLI_Agent") {agentID = jsonAgents._embedded.agents[intAgentsIndex].id}
                        intAgentsIndex = intAgentsIndex + 1
                    }

                    if(agentID != "") { //A CLI agent with <wkspName>_CLI_Agent name exists!
                        println("[INFO] Agent exists.")
                        println("[INFO] Agent ID: ${agentID}")
                        println("[INFO] Agent name: ${wkspName}_CLI_Agent")
                    }
                    else { //To create a new Agent with name <wkspName>_CLI_Agent. It sets up SRCCLR_API_TOKEN env variable
                        println("[INFO] Creating a new CLI Agent for ${wkspName} workspace...")
                        sh "http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces/${wkspID}/agents agent_type=CLI name=${wkspName}_CLI_Agent > myAgent.json"
                        def jsonMyAgent = readJSON file: 'myAgent.json'
                        println("[INFO] CLI Agent ${wkspName}_CLI_Agent has been created successfully!")
                        println("[INFO] Setting up SRCCLR_API_TOKEN env variable...")
                        def myToken = jsonMyAgent.token.access_token
                        sh "export SRCCLR_API_TOKEN=${myToken}"
                        println("[INFO] SRCCLR_API_TOKEN env variable has been set up successfully!")
                        sh 'echo $SRCCLR_API_TOKEN'
                    }
                }
                else {  //To create a new Agent with name <wkspName>_CLI_Agent. It sets up SRCCLR_API_TOKEN env variable
                    println("Aqui hay que crear un nuevo Agent")
                    /*********************************************/
                    //To create a new Agent with name <wkspName>_CLI_Agent
                    /*********************************************/
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