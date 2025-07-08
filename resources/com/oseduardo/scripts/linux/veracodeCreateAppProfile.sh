#/bin/bash

#$1 API-ID
#$2 API-Key
#$3 AppName
#$4 ProductName
#$5 ProductID

JAVA_WRAPPER_LOCATION="."

echo '[INFO] Sheell Script Parameter [AppName]: ' $3
echo '[INFO] Sheell Script Parameter [ProductName]: ' $4
echo '[INFO] Sheell Script Parameter [ProductID]: ' $5
exit 0
echo '[INFO] ------------------------------------------------------------------------'
echo '[INFO] DOWNLOADING VERACODE JAVA WRAPPER'
if `wget --no-check-certificate https://repo1.maven.org/maven2/com/veracode/vosp/api/wrappers/vosp-api-wrappers-java/24.7.14.0/vosp-api-wrappers-java-24.7.14.0.jar -O VeracodeJavaAPI.jar`; then
    chmod 755 VeracodeJavaAPI.jar
    echo '[INFO] SUCCESSFULLY DOWNLOADED WRAPPER'
else
    echo '[ERROR] DOWNLOAD FAILED'
    exit 1
fi

echo '[INFO] ------------------------------------------------------------------------'
echo '[INFO] VERACODE UPLOAD AND SCAN'

app_ID=$(java -verbose -jar $JAVA_WRAPPER_LOCATION/VeracodeJavaAPI.jar -vid $1 -vkey $2 -action GetAppList | grep -w \"$3\" | sed -n 's/.* app_id=\"\([0-9]*\)\" .*/\1/p')

if [ -z "$app_ID" ];
then
    echo '[INFO] App does not exist'
    echo '[INFO] create app: ' $3
    creat_addp=$(java -jar $JAVA_WRAPPER_LOCATION/VeracodeJavaAPI.jar -vid $1 -vkey $2 -action createApp -appname "$3" -criticality high)
    echo '[INFO]app created'
    app_ID=$(java -jar $JAVA_WRAPPER_LOCATION/VeracodeJavaAPI.jar -vid $1 -vkey $2 -action GetAppList | grep -w \"$3\" | sed -n 's/.* app_id=\"\([0-9]*\)\" .*/\1/p')
    echo '[INFO] new App-ID: ' $app_ID
    echo ""
else
    echo '[INFO] App-IP: ' $app_ID
    echo ""
fi
