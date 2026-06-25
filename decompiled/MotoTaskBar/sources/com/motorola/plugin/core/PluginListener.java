package com.motorola.plugin.core;

import android.content.Context;
import android.os.UserHandle;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.sdk.Plugin;

/* JADX INFO: compiled from: PluginListener.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginListener {
    void onPluginConnected(String str, String str2, Plugin plugin, Context context);

    default void onPluginDisconnected(String str, String str2, Plugin plugin) {
        str.getClass();
        str2.getClass();
        plugin.getClass();
    }

    default void onPluginFailedToLoad(String str, String str2) {
        str.getClass();
        str2.getClass();
    }

    default void onPluginPackageChanged(String str, UserHandle userHandle, MarkFlag markFlag) {
        str.getClass();
        userHandle.getClass();
        markFlag.getClass();
    }
}
