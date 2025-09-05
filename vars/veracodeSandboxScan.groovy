def call(String APP_PROFILE, String SANDBOX_NAME) {
    // Launches a Sandbox Scan in Veracode platform
    // APP_PROFILE: Application profile where target sandbox is located
    // SANDBOX_NAME: Especific sandbox where scan results will be uploaded
    echo "[INFO] Veracode SAST - Sandbox Scan"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    echo "[INFO] Starting Veracode Sandbox Scan..."

    try {
        // Launch Veracode Sandbox Scan by using veracode plugin for Jenkins
        withCredentials([usernamePassword(credentialsId: 'veracode-creds', usernameVariable: 'VID', passwordVariable: 'VKEY')]){
            veracode applicationName: "${APP_PROFILE}", createProfile: false, createSandbox: true, criticality: 'VeryHigh', canFailJob: true, deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: "${SANDBOX_NAME}", scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 60, uploadIncludesPattern: "veracode-artifacts/*.*", vid: VID, vkey: VKEY, waitForScan: true, ScanAllNonFatalTopLevelModules: true, IncludeNewModules: true
        }
    } catch(hudson.AbortException hEx) {
        echo "Entro a excepción!"
        println("Stacktrace: " + hEx.getStackTrace())
        //println(hEx.getMessage())
        return hEx
    }

    return 0
}