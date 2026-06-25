package com.motorola.plugin.core.channel.local;

import android.net.Uri;
import android.os.Bundle;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.channel.ClientId;
import java.util.Arrays;

/* JADX INFO: compiled from: ILocalChannelTransferRegistry.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ILocalChannelTransferRegistry extends Disposable, ISnapshotAware {

    /* JADX INFO: compiled from: ILocalChannelTransferRegistry.kt */
    public final class DefaultImpls {
        public static void notifyDataChanged(ILocalChannelTransferRegistry iLocalChannelTransferRegistry, Uri... uriArr) {
            iLocalChannelTransferRegistry.getClass();
            uriArr.getClass();
            iLocalChannelTransferRegistry.notifyDataChanged((Uri[]) Arrays.copyOf(uriArr, uriArr.length), null);
        }
    }

    ILocalChannelTransfer getTransfer(String str);

    String[] listTransfers();

    void notifyDataChanged(Uri... uriArr);

    void notifyDataChanged(Uri[] uriArr, Bundle bundle);

    void register(Class cls, ILocalChannelTransfer iLocalChannelTransfer);

    void sendExtraDataToClient(ClientId clientId, Bundle bundle);

    void unregister(ILocalChannelTransfer iLocalChannelTransfer);

    void unregister(Class cls);
}
