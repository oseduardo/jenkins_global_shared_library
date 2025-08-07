def call(String APP_PROFILE, String SANDBOX_NAME, String VID, String VKEY) {
    echo "[INFO] Veracode SAST - Sandbox Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    sh "ls -al ./app/target"

    veracode applicationName: "${APP_PROFILE}", createProfile: false, createSandbox: true, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: "${SANDBOX_NAME}", scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 60, uploadIncludesPattern: 'app/target/verademo.war', vid: "${VID}", vkey: "${VKEY}", waitForScan: true, ScanAllNonFatalTopLevelModules: true, IncludeNewModules: true
}