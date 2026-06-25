package com.motorola.plugin.core.channel;

import android.os.Bundle;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.channel.IDataSetChangedRegistry;
import java.util.List;

/* JADX INFO: compiled from: IDataSetChangedRegistryExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface IDataSetChangedRegistryExtension extends IDataSetChangedRegistry, ISnapshotAware, Disposable {
    void notifyDataSetChanged(List list, Bundle bundle);

    void notifyReceivedExtraData(Bundle bundle);
}
