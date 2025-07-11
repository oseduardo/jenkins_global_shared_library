def call(String REPO_NAME, String PRODUCT_NAME, String PRODUCT_ID, String VID, String VKEY) {
    //This procedure is created to customize the app profile creation process for prospect's POV considering they have to populate a couple of custom
    //fields (product_name, product_id); in this case it's neccesary to use wrappers to create app profile incluidng custom fields
    def appProfileName = new String("${PRODUCT_ID}_${REPO_NAME}")
    def strJavaWrapperLocation = new String (".")
    def appID = new String ("")
    echo "[INFO] Veracode - Creating a New App Profile"
    echo "[INFO] PRODUCT NAME: ${PRODUCT_NAME}"
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

            if(parseXML.'*'.findAll { it.toString().indexOf("app_name=${appProfileName},") != -1 }.toString() != "[]"){ //There's a "," after app profile name
                appXMLRecord = parseXML.'*'.findAll { it.toString().indexOf("app_name=${appProfileName},") != -1 }.toString()
                intIndexAppName = "${appXMLRecord}".indexOf(", app_name=${appProfileName},")
            }
            else{ //There's a "}" after app profile name
                appXMLRecord = parseXML.'*'.findAll { it.toString().indexOf("app_name=${appProfileName}}") != -1 }.toString()
                intIndexAppName = "${appXMLRecord}".indexOf(", app_name=${appProfileName}}")
            }

            echo "Index App Name: ${intIndexAppName}"
            indexAppID = "${appXMLRecord}".indexOf("app_id=")
            echo "Index App ID: ${indexAppID}"

            echo "appXMLRecord: ${appXMLRecord}"
            if("${appXMLRecord}" != "[]"){
                appID = appXMLRecord.substring("${appXMLRecord}".indexOf("app_id=") + 7,intIndexAppName)
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
                    echo "parseXML2: ${parseXML2}"

                    if(parseXML2.'*'.findAll { it.toString().indexOf("app_name=${appProfileName},") != -1 }.toString() != "[]"){ //There's a "," after app profile name
                        appXMLRecord2 = parseXML2.'*'.findAll { it.toString().indexOf("app_name=${appProfileName},") != -1 }.toString()
                        intIndexAppName = "${appXMLRecord2}".indexOf(", app_name=${appProfileName},")
                        echo "Index App Name: ${intIndexAppName}"
                    }
                    else{ //There's a "}" after app profile name
                        appXMLRecord2 = parseXML2.'*'.findAll { it.toString().indexOf("app_name=${appProfileName}}") != -1 }.toString()
                        intIndexAppName = "${appXMLRecord2}".indexOf(", app_name=${appProfileName}}")
                        echo "Index App Name: ${intIndexAppName}"
                    }

                    echo "appXMLRecord2: ${appXMLRecord2}"
                    if("${appXMLRecord2}" != "[]"){
                        appID2 = appXMLRecord2.substring("${appXMLRecord2}".indexOf("app_id=") + 7,intIndexAppName)
                        echo "appID2: ${appID2}"

                        /****************************************************************************************************************/
                        //AQUI SE ACTUALIZA EL APP PROFILE CON LOS CUSTOM FIELDS product_name Y product_id
                        /****************************************************************************************************************/
                        try {
                            //Updating field "product_name"
                            echo "[INFO] Updating product_name custom field in app profile ${appProfileName}. product_name: ${PRODUCT_NAME}"
                            println(["java", "-jar", "${strJavaWrapperLocation}/VeracodeJavaAPI.jar", "-action", "updateapp", "-customfieldname", "product_name", "-customfieldvalue", "${PRODUCT_NAME}", "-appid", "${appID2}"].execute().text)
                            echo "[INFO] Updating product_id custom field in app profile ${appProfileName}. product_id: ${PRODUCT_ID}"
                            println(["java", "-jar", "${strJavaWrapperLocation}/VeracodeJavaAPI.jar", "-action", "updateapp", "-customfieldname", "product_id", "-customfieldvalue", "${PRODUCT_ID}", "-appid", "${appID2}"].execute().text)
                            echo "[INFO] Custom fields product_name and product_id have been updated succesfully"
                        } catch(Exception ex) {
                            println(ex)
                        }
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