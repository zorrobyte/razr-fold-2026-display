package com.motorola.plugin.sdk;

import android.content.Context;
import com.motorola.plugin.sdk.channel.IRemoteChannel;

/* JADX INFO: loaded from: classes2.dex */
public interface Plugin {
    default void onCreate(Context context, Context context2, IRemoteChannel iRemoteChannel) {
    }

    default void onDestroy() {
    }
}
