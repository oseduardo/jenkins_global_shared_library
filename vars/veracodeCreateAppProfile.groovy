def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the app profile creation process for Transbank's POV considering they have to populate a couple of custom
    //fields (product_name, product_id); in this case it's neccesary to use wrappers to create app profile incluidng custom fields
    def appProfileName = new String("${PRODUCT_ID}_${REPO_NAME}")
    //def appProfileName = "verademo"
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
            echo "parseXML: ${parseXML}"

            /******** Prueba REGEX *************/
            def tmpText = "{https://analysiscenter.veracode.com/schema/2.0/applist}app[attributes={app_id=488174, app_name=verademo, policy_updated_date=2025-07-08T21:05:39-04:00}; value=[]]"
            def matcher = tmpText =~ /(verademo)\(,|;)?/
            echo "Prueba REGEX - matcher: ${matcher}"
            /***********************************/

            appXMLRecord = parseXML.'*'.findAll { it.toString().indexOf("app_name=${appProfileName}") != -1 }.toString()
            //appXMLRecord = parseXML.'*'[0].toString()
            echo "appXMLRecord: ${appXMLRecord}"
            if("${appXMLRecord}" != "[]"){
                appID = appXMLRecord.substring("${appXMLRecord}".indexOf("app_id=") + 7,"${appXMLRecord}".indexOf(", app_name=${appProfileName}"))
                echo "appID: ${appID}"
            }
            else{
                echo "[INFO] CREATING APP PROFILE ${appProfileName}"
                appProfileCreateResponse = "java -jar ${strJavaWrapperLocation}/VeracodeJavaAPI.jar -vid ${VID} -vkey ${VKEY} -action createApp -appname ${appProfileName} -criticality VeryHigh".execute().text
                echo "[INFO] APP PROFILE ${appProfileName} HAS BEEN CREATED"

                appIDResponse = ""
                appIDResponse = "java -verbose -jar ${strJavaWrapperLocation}/VeracodeJavaAPI.jar -vid ${VID} -vkey ${VKEY} -action GetAppList".execute().text
                if("${appIDResponse}" != ""){
                    int intBeginXML2 = "${appIDResponse}".indexOf("<?xml")
                    int intEndXML2 = "${appIDResponse}".indexOf("</applist>")
                    strXML2 = "${appIDResponse}".substring(intBeginXML2, intEndXML2 + 10)
                    echo "strXML2: ${strXML2}"
                    def parseXML2 = new XmlParser().parseText(strXML2)
                    appXMLRecord2 = parseXML2.'*'.findAll { it.toString().indexOf("app_name=${appProfileName}") != -1 }.toString()
                    echo "appXMLRecord2: ${appXMLRecord2}"
                    if("${appXMLRecord2}" != "[]"){
                        appID2 = appXMLRecord2.substring("${appXMLRecord2}".indexOf("app_id=") + 7,"${appXMLRecord2}".indexOf(", app_name=${appProfileName}"))
                        echo "appID2: ${appID2}"

                        /****************************************************************************************************************/
                        //AQUI SE DEBE CREAR LOS DOS LLAMADOS A ACTUALIZAR EL APP PROFILE CON LOS CUSTOM FIELDS product_name Y product_id
                        /****************************************************************************************************************/
                    }
                    else{
                        throw new Exception("[ERROR] There was an error recovering App ID after App Profile creation")
                    }
                }
                else{
                    throw new Exception("[ERROR] There was an error creating the App Profile")
                }
            }
        }
        else{
            throw new Exception("[ERROR] There was an error trying to get App ID")
        }
    } catch(Exception ex) {
        println(ex)
    }
}