def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    //Authentication is made by usinh HMAC Auth, which is covered with agent running the stage that is invoking this method

    def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")

    //Validate if a Workspace with the name wkspName exists
    sh 'http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces > workspaces.json'
    sh 'cat workspaces.json'
    //strWorkspaces = "http --auth-type veracode_hmac GET https://api.veracode.com/srcclr/v3/workspaces".execute()
    //echo "${strWorkspaces}"
}