package com.motorola.plugin.sdk.channel;

import android.net.Uri;

/* JADX INFO: loaded from: classes2.dex */
public interface IDataSetChangedRegistry {
    void register(IDataSetChangedCallback iDataSetChangedCallback, Uri... uriArr);

    void unregister(IDataSetChangedCallback iDataSetChangedCallback, Uri... uriArr);
}
