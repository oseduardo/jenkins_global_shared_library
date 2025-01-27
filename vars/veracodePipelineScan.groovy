def call(Map config = [:]) {
    echo "[INFO] Preparing Veracode SAST Pipeline Scan Execution..."
    echo "[INFO] VID: ${config.VID}"
    echo "[INFO] VKEY: ${config.VKEY}"
}