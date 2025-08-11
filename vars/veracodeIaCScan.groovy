def call() {
    // Executes Veracode Container Security by running Veracode CLI tool to scan IaC files on local directory (work directory) of jenkins pipeline
    // It assumes that Veracode CLI tool is already installed in jenkins agent from which this shared library is called
    echo "[INFO] Veracode IaC Scan"

    script {
        // Download Veracode CLI tool to run Veracode Container Security
        //echo "[INFO] Downloding and installing Veracode CLI..."
        //sh "curl -fsS https://tools.veracode.com/veracode-cli/install | sh"

        // Setting up Veracode API Credentials
        //sh 'export VERACODE_API_KEY_ID=' + ${VID}
        //sh 'export VERACODE_API_KEY_SECRET=' + ${VKEY}
            
        def STATUS = sh returnStatus: true, script: "veracode scan --type directory --source . --format table --output cs_iac_results.txt"
        echo "STATUS: ${STATUS}"

        // Reading results file
        /*File myFile = new File("cs_iac_results.txt")
        List<String> lstIaCResults = myFile.readLines()
        String strPolicyEvaluation = ""
        strPolicyEvaluation = lstIaCResults.findAll { it.substring(0, 12) == "Policy Passed" }
        echo "Prueba: ${strPolicyEvaluation}"*/
    }
}