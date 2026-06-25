package com.google.android.setupcompat.partnerconfig;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class ResourceEntry {
    static final String KEY_FALLBACK_CONFIG = "fallbackConfig";
    static final String KEY_PACKAGE_NAME = "packageName";
    static final String KEY_RESOURCE_ID = "resourceId";
    static final String KEY_RESOURCE_NAME = "resourceName";
    private static final String TAG = "ResourceEntry";
    private final String packageName;
    private final int resourceId;
    private final String resourceName;
    private final Resources resources;

    public ResourceEntry(String str, String str2, int i, Resources resources) {
        this.packageName = str;
        this.resourceName = str2;
        this.resourceId = i;
        this.resources = resources;
    }

    public static ResourceEntry fromBundle(Context context, Bundle bundle) {
        if (bundle.containsKey(KEY_PACKAGE_NAME) && bundle.containsKey(KEY_RESOURCE_NAME) && bundle.containsKey(KEY_RESOURCE_ID)) {
            String string = bundle.getString(KEY_PACKAGE_NAME);
            String string2 = bundle.getString(KEY_RESOURCE_NAME);
            try {
                return new ResourceEntry(string, string2, bundle.getInt(KEY_RESOURCE_ID), getResourcesByPackageName(context, string));
            } catch (PackageManager.NameNotFoundException unused) {
                Bundle bundle2 = bundle.getBundle("fallbackConfig");
                if (bundle2 != null) {
                    Log.w(TAG, string + " not found, " + string2 + " fallback to default value");
                    return fromBundle(context, bundle2);
                }
            }
        }
        return null;
    }

    private static Resources getResourcesByPackageName(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getResourcesForApplication(packageManager.getApplicationInfo(str, 512));
    }

    public String getPackageName() {
        return this.packageName;
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public Resources getResources() {
        return this.resources;
    }
}
