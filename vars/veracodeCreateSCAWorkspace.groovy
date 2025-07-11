def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the SCA (ABS) Workspace creation process for prospect's POV
    def wkspName = new String("${PRODUCT_ID}_${REPO_NAME}")

    //Setting up HMAC for secure authentication with SCA Agent REST APIs
    holamundo("Espero todo salga bien!")
}