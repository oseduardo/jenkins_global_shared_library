def call(String APP_PROFILE, String VID, String VKEY) {
    echo "[INFO] Veracode SAST - Policy Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    sh "ls -al ./app/target"

    veracode applicationName: "${APP_PROFILE}", createProfile: true, createSandbox: false, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: '', scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 20, uploadIncludesPattern: 'app/target/verademo.war', vid: "${VID}", vkey: "${VKEY}", waitForScan: true
}