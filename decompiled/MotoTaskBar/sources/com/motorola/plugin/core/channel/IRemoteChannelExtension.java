package com.motorola.plugin.core.channel;

import android.os.Bundle;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.IRemoteChannel;
import java.util.List;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: IRemoteChannelExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface IRemoteChannelExtension extends IRemoteChannel, ISnapshotAware, Disposable {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int KEEP_CHANNEL_ALIVE_RESTRICTED_BY_DATASET = 4;
    public static final int KEEP_CHANNEL_ALIVE_RESTRICTED_BY_HOST = 2;
    public static final int KEEP_CHANNEL_ALIVE_RESTRICTED_BY_PLUGIN = 8;

    /* JADX INFO: compiled from: IRemoteChannelExtension.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int KEEP_CHANNEL_ALIVE_RESTRICTED_BY_DATASET = 4;
        public static final int KEEP_CHANNEL_ALIVE_RESTRICTED_BY_HOST = 2;
        public static final int KEEP_CHANNEL_ALIVE_RESTRICTED_BY_PLUGIN = 8;

        private Companion() {
        }
    }

    /* JADX INFO: compiled from: IRemoteChannelExtension.kt */
    public final class DefaultImpls {
        public static /* synthetic */ void transferNoThreadCheck$default(IRemoteChannelExtension iRemoteChannelExtension, Bundle bundle, IRemoteChannel.IOnRemoteError iOnRemoteError, IRemoteChannel.IOnRemoteResponse iOnRemoteResponse, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: transferNoThreadCheck");
            }
            if ((i & 2) != 0) {
                iOnRemoteError = null;
            }
            iRemoteChannelExtension.transferNoThreadCheck(bundle, iOnRemoteError, iOnRemoteResponse);
        }
    }

    CoroutineScope getChannelScope();

    ClientId getClientId();

    String getToken();

    void keepChannelConnectionAlive(boolean z, int i);

    void notifyDataSetChanged(List list, Bundle bundle);

    void notifyReceivedExtraData(Bundle bundle);

    void pause();

    void resume();

    void transferNoThreadCheck(Bundle bundle, IRemoteChannel.IOnRemoteError iOnRemoteError, IRemoteChannel.IOnRemoteResponse iOnRemoteResponse);
}
