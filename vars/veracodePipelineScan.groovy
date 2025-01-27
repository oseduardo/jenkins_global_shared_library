def call(String vID, String vKEY) {
    echo "[INFO] Veracode SAST - Pipeline Scan"
    echo "[INFO] Downloading Pipeline Scan (Latest Version)..."
    script {
        curl -sSO https://downloads.veracode.com/securityscan/pipeline-scan-LATEST.zip
        unzip pipeline-scan-LATEST.zip
        echo '[INFO] --- Starting Pipeline Scan execution...'
        def STATUS = sh(java -jar pipeline-scan.jar -vid $vID -vkey $vKEY --file verademo.war -so true)
        if [ $STATUS -gt 0 ];
        then
            echo '[INFO] --- Pipeline Scan has finished.'
            echo '[INFO] --- ' $STATUS ' flaws were found.'
            exit 1 #This exit code makes the pipeline breaks. Remove this line if you need the pipeline continues its execution
        elif [ $STATUS -lt 0 ];
        then
            echo '[INFO] --- Pipeline Scan could not be executed...There are some errors...'
            echo '[INFO] --- ' $STATUS
            exit 1 #This exit code makes the pipeline breaks. Remove this line if you need the pipeline continues its execution
        fi  
    }
}