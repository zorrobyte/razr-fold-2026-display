#!/bin/bash
set -e
cd "$(dirname "$0")"
SDK=$HOME/Library/Android/sdk
BT=$SDK/build-tools/37.0.0
AJ=$SDK/platforms/android-36/android.jar
D8=$SDK/cmdline-tools/latest/bin/d8

rm -rf out dex base.apk aligned.apk
mkdir -p out dex

# 1. link resources + manifest -> base.apk (no res dir; manifest only)
"$BT/aapt2" link -I "$AJ" --manifest AndroidManifest.xml \
    --min-sdk-version 30 --target-sdk-version 34 \
    -o base.apk

# 2. compile java
javac -classpath "$AJ" -d out src/com/zorrobyte/dispctl/MainActivity.java

# 3. dex
"$D8" --min-api 30 --output dex $(find out -name '*.class')

# 4. add classes.dex into the apk
(cd dex && zip -q ../base.apk classes.dex)

# 5. align
"$BT/zipalign" -f -p 4 base.apk aligned.apk

# 6. sign (debug keystore: android/android)
"$BT/apksigner" sign --ks debug.ks --ks-pass pass:android \
    --key-pass pass:android --out aligned.apk aligned.apk

echo "BUILT: $(ls -l aligned.apk | awk '{print $5}') bytes"
"$BT/apksigner" verify aligned.apk && echo "signature OK"
