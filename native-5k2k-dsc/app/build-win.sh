#!/bin/bash
# Windows/Git-Bash build for the 5K Display Control app.
# Needs: Android SDK build-tools + platform, JDK on PATH. Override SDK/API via env.
set -e
export MSYS_NO_PATHCONV=1 MSYS2_ARG_CONV_EXCL='*'
cd "$(dirname "$0")"

SDK="${ANDROID_SDK:-C:/Android/sdk}"
API="${ANDROID_API:-34}"
BT="$SDK/build-tools/${BUILD_TOOLS:-34.0.0}"
AJ="$SDK/platforms/android-$API/android.jar"

rm -rf out dex base.apk aligned.apk; mkdir -p out dex
[ -f debug.ks ] || keytool -genkeypair -v -keystore debug.ks -storepass android -keypass android \
  -alias android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Android Debug,O=Android,C=US" >/dev/null 2>&1

"$BT/aapt2.exe" link -I "$AJ" --manifest AndroidManifest.xml --min-sdk-version 30 --target-sdk-version "$API" -o base.apk
javac -encoding UTF-8 -source 11 -target 11 -classpath "$AJ" -d out src/com/zorrobyte/dispctl/MainActivity.java
java -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 --min-api 30 --lib "$AJ" --output dex $(find out -name '*.class')
python -c "import zipfile; z=zipfile.ZipFile('base.apk','a',zipfile.ZIP_STORED); z.write('dex/classes.dex','classes.dex'); z.close()"
"$BT/zipalign.exe" -f -p 4 base.apk aligned.apk
java -jar "$BT/lib/apksigner.jar" sign --ks debug.ks --ks-pass pass:android --key-pass pass:android --out 5K-Display-Control.apk aligned.apk
rm -rf out dex base.apk aligned.apk
echo "BUILT: 5K-Display-Control.apk ($(stat -c %s 5K-Display-Control.apk) bytes)"
