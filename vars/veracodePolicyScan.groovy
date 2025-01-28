def call(String VID, String VKEY) {
    echo "[INFO] Veracode SAST - Policy Scan"
    sh "ls -al ./app/target"

    veracode applicationName: 'verademo_jenkins', canFailJob: true, createSandbox: false, criticality: 'VeryHigh', deleteIncompleteScanLevel: '2', fileNamePattern: '', replacementPattern: '', sandboxName: '', scanExcludesPattern: '', scanIncludesPattern: '', scanName: '${BUILD_NUMBER}', teams: '', timeout: 20, uploadIncludesPattern: 'app/target/verademo.war', vid: "${VID}", vkey: "${VKEY}", waitForScan: true
}