package com.motorola.plugin.core.channel;

import android.net.Uri;
import android.os.Bundle;
import com.motorola.plugin.sdk.channel.RemoteService;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonChannelResponseHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CommonChannelResponseHelper {
    private final ClassLoader pluginClassLoader;
    private final IRemoteChannelExtension remoteChannel;

    public CommonChannelResponseHelper(IRemoteChannelExtension iRemoteChannelExtension, ClassLoader classLoader) {
        iRemoteChannelExtension.getClass();
        classLoader.getClass();
        this.remoteChannel = iRemoteChannelExtension;
        this.pluginClassLoader = classLoader;
    }

    private final void onDataSetChanged(Bundle bundle) {
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(RemoteService.EXTRA_NOTIFY_URIS, Uri.class);
        Bundle bundle2 = bundle.getBundle(RemoteService.EXTRA_BUNDLE_DATA);
        if (bundle2 == null) {
            bundle2 = null;
        } else {
            bundle2.setClassLoader(bundle.getClassLoader());
        }
        this.remoteChannel.notifyDataSetChanged(parcelableArrayList, bundle2);
    }

    private final void onReceivedExtraData(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(RemoteService.EXTRA_BUNDLE_DATA);
        if (bundle2 == null) {
            bundle2 = null;
        } else {
            bundle2.setClassLoader(bundle.getClassLoader());
        }
        this.remoteChannel.notifyReceivedExtraData(bundle2);
    }

    public final void onChannelResponse(Bundle bundle) {
        bundle.getClass();
        bundle.setClassLoader(this.pluginClassLoader);
        Object obj = bundle.get(RemoteService.EXTRA_METHOD);
        if (Intrinsics.areEqual(obj, RemoteService.METHOD_NOTIFY_DATASET_CHANGE)) {
            onDataSetChanged(bundle);
        } else if (Intrinsics.areEqual(obj, RemoteService.METHOD_SEND_EXTRA_DATA)) {
            onReceivedExtraData(bundle);
        }
    }
}
