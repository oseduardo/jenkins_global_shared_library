def call(String APP_PROFILE, String SANDBOX_NAME, String ARTIFACTS_PATH, String VID, String VKEY) {
    // Launches a Sandbox Scan in Veracode platform
    // APP_PROFILE: Application profile where target sandbox is located
    // SANDBOX_NAME: Especific sandbox where scan results will be uploaded
    // ARTIFACTS_PATH: Relative path in pipeline agent where built artifacts are available to upload for scanning
    echo "[INFO] Veracode SAST - Sandbox Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    echo "[INFO] Getting artifacts from ${ARTIFACTS_PATH}"
    echo "[INFO] Starting Veracode Sandbox Scan..."

    veracode applicationName: "${APP_PROFILE}", createProfile: false, createSandbox: true, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: "${SANDBOX_NAME}", scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 60, uploadIncludesPattern: "${ARTIFACTS_PATH}", vid: "${VID}", vkey: "${VKEY}", waitForScan: true, ScanAllNonFatalTopLevelModules: true, IncludeNewModules: true
}