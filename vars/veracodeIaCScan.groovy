def call() {
    // Executes Veracode Container Security by running Veracode CLI tool to scan IaC files on local directory (work directory) of jenkins pipeline
    // It assumes that Veracode CLI tool is already installed in jenkins agent from which this shared library is called
    echo "[INFO] Veracode IaC Scan"

    script {
        def STATUS = sh returnStatus: true, script: "veracode scan --type directory --source . --format table --output cs_iac_results.txt"
        sh 'cat cs_iac_results.txt'
        echo "STATUS: ${STATUS}"
        sh """
            if [ $STATUS -eq 3 ]
            then
                echo '[INFO] Scan was succesfull but there are some issues. Scanned application did not pass policy!'
            elif [ $STATUS -eq 0 ]
            then
                echo '[INFO] Scan was succesfull and passed policy!'
            else
                '[INFO] There was a problem while executing Veracode Container Security!'
            fi

            exit $STATUS
        """
    }
}