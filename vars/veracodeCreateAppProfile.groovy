def call(String APP_PROFILE, String VID, String VKEY) {
    echo "[INFO] Veracode - Creating a New App Profile"
    echo "[INFO] APP PROFILE NAME: ${APP_PROFILE}"
    
    def shellScript = libraryResource 'com/oseduardo/scripts/linux/hello-world.sh'
    echo "${shellScript}"
}