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
        def test = readJSON file: 'workspaces.json'
        echo "${test}"._embedded.workspaces.size()
    } catch(Exception ex) {
        println(ex)
    }
}