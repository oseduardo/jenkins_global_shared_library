def call(String APP_PROFILE, String ARTIFACTS_PATH, String VID, String VKEY) {
    // Launches a Policy Scan in Veracode platform
    // APP_PROFILE: Application profile where target sandbox is located
    // ARTIFACTS_PATH: Folder path in pipeline agent where built artifacts are available to upload for scanning
    echo "[INFO] Veracode SAST - Policy Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    echo "[INFO] Getting artifacts from ${ARTIFACTS_PATH}"
    echo "[INFO] Starting Veracode Policy Scan..."

    veracode applicationName: "${APP_PROFILE}", createProfile: false, createSandbox: false, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: '', scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 60, uploadIncludesPattern: "${ARTIFACTS_PATH}", vid: "${VID}", vkey: "${VKEY}", waitForScan: true, ScanAllNonFatalTopLevelModules: true, IncludeNewModules: true
}