package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public interface SettingsProxy {
    static float parseFloat(String str, float f) {
        if (str != null) {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
        }
        return f;
    }

    ContentResolver getContentResolver();

    default float getFloat(String str, float f) {
        return parseFloat(getString(str), f);
    }

    default int getInt(String str, int i) {
        String string = getString(str);
        if (string != null) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    String getString(String str);

    Uri getUriFor(String str);

    default void registerContentObserver(Uri uri, ContentObserver contentObserver) {
        registerContentObserver(uri, false, contentObserver);
    }

    default void registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver) {
        getContentResolver().registerContentObserver(uri, z, contentObserver);
    }

    default void registerContentObserver(String str, ContentObserver contentObserver) {
        registerContentObserver(getUriFor(str), contentObserver);
    }

    default void unregisterContentObserver(ContentObserver contentObserver) {
        getContentResolver().unregisterContentObserver(contentObserver);
    }
}
