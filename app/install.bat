mkdir release

adb uninstall calculatorsgh.free
adb install -r release\app-release.apk
adb shell am start -n "calculatorsgh.free/calculatorsgh.activities.ActivitySplashScreen" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
exit