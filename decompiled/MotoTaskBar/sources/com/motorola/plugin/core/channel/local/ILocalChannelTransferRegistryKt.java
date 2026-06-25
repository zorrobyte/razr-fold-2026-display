package com.motorola.plugin.core.channel.local;

import com.motorola.plugin.sdk.Plugin;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ILocalChannelTransferRegistry.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ILocalChannelTransferRegistryKt {
    public static final /* synthetic */ void register(ILocalChannelTransferRegistry iLocalChannelTransferRegistry, ILocalChannelTransfer iLocalChannelTransfer) {
        iLocalChannelTransferRegistry.getClass();
        iLocalChannelTransfer.getClass();
        Intrinsics.reifiedOperationMarker(4, "T");
        iLocalChannelTransferRegistry.register(Plugin.class, iLocalChannelTransfer);
    }

    public static final /* synthetic */ void unregister(ILocalChannelTransferRegistry iLocalChannelTransferRegistry) {
        iLocalChannelTransferRegistry.getClass();
        Intrinsics.reifiedOperationMarker(4, "T");
        iLocalChannelTransferRegistry.unregister(Plugin.class);
    }
}
