def call(String SRCCLR_API_TOKEN, String SRCCLR_WORKSPACE_SLUG) {
    echo "[INFO] Veracode SCA - Agent-Based Scan"
    sh "export SRCCLR_API_TOKEN=${SRCCLR_API_TOKEN}"
    sh "export SRCCLR_WORKSPACE_SLUG=${SRCCLR_WORKSPACE_SLUG}"
    sh "ls -al"
    sh "curl -sSL https://download.sourceclear.com/ci.sh | bash -s -- scan --skip-compile --update-advisor --allow-dirty"
}