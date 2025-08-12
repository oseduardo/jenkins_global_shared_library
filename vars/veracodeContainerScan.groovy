def call(String IMAGE_NAME) {
    // Executes Veracode Container Security by running Veracode CLI tool to scan a container image that has been built previously in jenkins pipeline
    // It assumes that Veracode CLI tool is already installed in jenkins agent from which this shared library is called
    // IMAGE_NAME is the name of the image to be scanned, including its tag. i. e.: jenkins/agent:alpine-jdk17
    echo "[INFO] Veracode Container Scan"

    script {
        def STATUS = sh returnStatus: true, script: "veracode scan --type image --source ${IMAGE_NAME} --format table --output cs_container_results.txt"
        sh 'cat cs_container_results.txt'
        echo "STATUS: ${STATUS}"
        sh """
            if [ $STATUS -eq 3 ]
            then
                echo '[INFO] Scan was succesfull but there are some issues. Scanned container did not pass policy!'
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