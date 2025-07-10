def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the app profile creation process for Transbank's POV considering they have to populate a couple of custom
    //fields (product_name, product_id); in this case it's neccesary to use wrappers to create app profile incluidng custom fields
    def appProfileName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def strJavaWrapperLocation = new String (".")
    def appID = new String ("")
    echo "[INFO] Veracode - Creating a New App Profile"
    echo "[INFO] REPO NAME: ${REPO_NAME}"
    echo "[INFO] APP PROFILE NAME: ${appProfileName}"
    
    //Download Java API Wrapper
    echo '[INFO] ------------------------------------------------------------------------'
    echo '[INFO] DOWNLOADING VERACODE JAVA WRAPPER'
    try {
        response = "curl https://repo1.maven.org/maven2/com/veracode/vosp/api/wrappers/vosp-api-wrappers-java/24.7.14.0/vosp-api-wrappers-java-24.7.14.0.jar -o VeracodeJavaAPI.jar".execute().text
        wrapperVersion = "java -jar VeracodeJavaAPI.jar -wrapperversion".execute().text
        echo "Java API Wrapper Version: ${wrapperVersion}"

        //Validate if appProfileName exists; if so, the AppID is captured
        appIDResponse = "java -verbose -jar ${strJavaWrapperLocation}/VeracodeJavaAPI.jar -vid ${VID} -vkey ${VKEY} -action GetAppList".execute().text

        if("${appIDResponse}" != ""){
            int intBeginXML = "${appIDResponse}".indexOf("<?xml")
            int intEndXML = "${appIDResponse}".indexOf("</applist>")
            strXML = "${appIDResponse}".substring(intBeginXML, intEndXML + 10)
            echo "strXML: ${strXML}"
            def parseXML = new XmlParser().parseText(strXML)
            appXMLRecord = parseXML.'*'.find { it.toString().indexOf("app_name=${appProfileName},") != -1 }.toString()
            echo "appXMLRecord: ${appXMLRecord}"
            if("${appXMLRecord}" != "[]"){
                appID = appXMLRecord.substring("${appXMLRecord}".indexOf("app_id=") + 7,"${appXMLRecord}".indexOf(", app_name=${appProfileName},"))
                echo "appID: ${appID}"
            }
            else{
                echo "[INFO] CREATING APP PROFILE ${appProfileName}"
                /*appProfileCreateResponse = "java -jar ${strJavaWrapperLocation}/VeracodeJavaAPI.jar -vid ${VID} -vkey ${VKEY} -action createApp -appname ${appProfileName} -criticality VeryHigh".execute().text
                echo "[INFO] APP PROFILE ${appProfileName} HAS BEEN CREATED"

                appIDResponse = ""
                appIDResponse = "java -verbose -jar ${strJavaWrapperLocation}/VeracodeJavaAPI.jar -vid ${VID} -vkey ${VKEY} -action GetAppList".execute().text
                if("${appIDResponse}" != ""){
                    int intBeginXML2 = "${appIDResponse}".indexOf("<?xml")
                    int intEndXML2 = "${appIDResponse}".indexOf("</applist>")
                    echo "intBeginXML2: ${intBeginXML2}"
                    echo "intEndXML2: ${intEndXML2}"
                    strXML2 = "${appIDResponse}".substring(intBeginXML2, intEndXML2 + 10)
                    echo "strXML2: ${strXML2}"
                    def parseXML2 = new XmlParser().parseText(strXML2)
                    appXMLRecord2 = parseXML2.'*'.findAll { it.toString().indexOf("app_name=${appProfileName},") != -1 }.toString()
                    echo "appXMLRecord2: ${appXMLRecord2}"
                    if("${appXMLRecord2}" != "[]"){
                        appID2 = appXMLRecord2.substring("${appXMLRecord2}".indexOf("app_id=") + 7,"${appXMLRecord2}".indexOf(", app_name=${appProfileName},"))
                        echo "appID2: ${appID2}"
                    }
                }*/
            }
        }
    } catch(Exception ex) {
        println(ex)
    }
}