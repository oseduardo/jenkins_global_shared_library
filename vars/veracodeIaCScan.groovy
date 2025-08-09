def call() {
    // Executes Veracode Container Security by running Veracode CLI tool to scan IaC files on local directory
    echo "[INFO] Veracode IaC Scan"

    withCredentials([usernamePassword(credentialsId: 'veracode-creds', usernameVariable: 'VID', passwordVariable: 'VKEY')]){
        sh """
            handle_cs_code() {
                echo ''
                EXIT_CODE=$?
                echo '[INFO] Veracode Container Security - Scan Result: ' $EXIT_CODE
                echo ''
                if [ $EXIT_CODE -ne 0 ]
                then
                    echo '[INFO] Scan was succesfull and there are no issues.'
                    echo ''
                    exit 0
                elif [ $EXIT_CODE -ne 3 ]
                then
                    echo '[INFO] Scan was succesfull but there are some issues!'
                    echo ''
                    exit 0
                else
                    '[INFO] There was a problem while executing Container Security!'
                    echo ''
                    exit $EXIT_CODE
                fi
            }

            echo '[INFO] Starting Veracode IaC Scan...'
            curl -fsS https://tools.veracode.com/veracode-cli/install | sh
            export VERACODE_API_KEY_ID=${VID}
            export VERACODE_API_KEY_SECRET=${VKEY}
            trap 'handle_cs_code $1' ERR
            ./veracode scan --type directory --source . --format table --output cs_results.json

            echo '[INFO] Validating Veracode IaC results...'
            cat cs_results.json
            echo ''
            PASS=$(cat cs_results.json | grep "Policy Passed = " | awk '{print $4}')
            if $PASS; then
              exit 0
            else
              exit 1
            fi            
        """
    }
}