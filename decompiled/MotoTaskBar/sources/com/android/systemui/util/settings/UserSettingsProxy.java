package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import com.android.systemui.settings.UserTracker;

/* JADX INFO: loaded from: classes.dex */
public interface UserSettingsProxy extends SettingsProxy {
    @Override // com.android.systemui.util.settings.SettingsProxy
    default int getInt(String str, int i) {
        return getIntForUser(str, i, getUserId());
    }

    default int getIntForUser(String str, int i, int i2) {
        String stringForUser = getStringForUser(str, i2);
        if (stringForUser != null) {
            try {
                return Integer.parseInt(stringForUser);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    default int getRealUserHandle(int i) {
        return i != -2 ? i : getUserTracker().getUserId();
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default String getString(String str) {
        return getStringForUser(str, getUserId());
    }

    String getStringForUser(String str, int i);

    default int getUserId() {
        return getContentResolver().getUserId();
    }

    UserTracker getUserTracker();

    @Override // com.android.systemui.util.settings.SettingsProxy
    default void registerContentObserver(Uri uri, ContentObserver contentObserver) {
        registerContentObserverForUser(uri, contentObserver, getUserId());
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default void registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver) {
        registerContentObserverForUser(uri, z, contentObserver, getUserId());
    }

    default void registerContentObserverForUser(Uri uri, ContentObserver contentObserver, int i) {
        registerContentObserverForUser(uri, false, contentObserver, i);
    }

    default void registerContentObserverForUser(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        getContentResolver().registerContentObserver(uri, z, contentObserver, getRealUserHandle(i));
    }

    default void registerContentObserverForUser(String str, ContentObserver contentObserver, int i) {
        registerContentObserverForUser(getUriFor(str), contentObserver, i);
    }
}
