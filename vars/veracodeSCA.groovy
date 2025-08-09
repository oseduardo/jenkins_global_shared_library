def call(String SRCCLR_WORKSPACE_SLUG) {
    echo "[INFO] Veracode SCA - Agent-Based Scan"

    withCredentials([string(credentialsId: 'SRCCLR_API_TOKEN', variable: 'SRCCLR_API_TOKEN')]){
        sh 'export SRCCLR_API_TOKEN=' + SRCCLR_API_TOKEN
        sh "curl -sSL https://download.sourceclear.com/ci.sh | sh -s -- scan --skip-compile --update-advisor --allow-dirty --recursive --ws=${SRCCLR_WORKSPACE_SLUG}"
    }
}