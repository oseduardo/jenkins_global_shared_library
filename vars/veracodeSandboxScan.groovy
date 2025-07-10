def call(String APP_PROFILE, String VID, String VKEY) {
    echo "[INFO] Veracode SAST - Sandbox Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    sh "ls -al ./app/target"

    veracode applicationName: "${APP_PROFILE}", createProfile: false, createSandbox: true, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: 'dev_branch_jenkins', scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 20, uploadIncludesPattern: 'app/target/verademo.war', vid: "${VID}", vkey: "${VKEY}", waitForScan: true
}