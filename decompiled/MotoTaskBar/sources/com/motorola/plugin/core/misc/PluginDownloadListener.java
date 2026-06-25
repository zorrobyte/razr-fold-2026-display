package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.context.PluginId;

/* JADX INFO: compiled from: PluginDownloadListener.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginDownloadListener {
    void onFailed(String str, PluginId pluginId);

    void onFinished(String str, PluginId pluginId);

    void onProgress(int i, PluginId pluginId);

    void onStart(PluginId pluginId);
}
