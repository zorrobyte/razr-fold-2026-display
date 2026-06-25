package com.bumptech.glide.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.bumptech.glide.load.Key;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class ApplicationVersionSignature {
    private static final ConcurrentMap PACKAGE_NAME_TO_KEY = new ConcurrentHashMap();

    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AppVersionSignature", "Cannot resolve info for" + context.getPackageName(), e);
            return null;
        }
    }

    private static String getVersionCode(PackageInfo packageInfo) {
        return packageInfo != null ? String.valueOf(packageInfo.versionCode) : UUID.randomUUID().toString();
    }

    public static Key obtain(Context context) {
        String packageName = context.getPackageName();
        ConcurrentMap concurrentMap = PACKAGE_NAME_TO_KEY;
        Key key = (Key) concurrentMap.get(packageName);
        if (key != null) {
            return key;
        }
        Key keyObtainVersionSignature = obtainVersionSignature(context);
        Key key2 = (Key) concurrentMap.putIfAbsent(packageName, keyObtainVersionSignature);
        return key2 == null ? keyObtainVersionSignature : key2;
    }

    private static Key obtainVersionSignature(Context context) {
        return new ObjectKey(getVersionCode(getPackageInfo(context)));
    }
}
