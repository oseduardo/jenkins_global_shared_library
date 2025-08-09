def call(String APP_PROFILE, String VID, String VKEY) {
    // Launches a Policy Scan in Veracode platform
    // APP_PROFILE: Application profile where target sandbox is located
    echo "[INFO] Veracode SAST - Policy Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    echo "[INFO] Starting Veracode Policy Scan..."

    // Launches Veracode Policy Scan by using veracode plugin for Jenkins
    veracode applicationName: "${APP_PROFILE}", createProfile: false, createSandbox: false, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: '', scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 60, uploadIncludesPattern: "veracode-artifacts/*.*", vid: "${VID}", vkey: "${VKEY}", waitForScan: true, ScanAllNonFatalTopLevelModules: true, IncludeNewModules: true
}