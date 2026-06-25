package com.motorola.plugin.sdk.channel;

import android.os.Bundle;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface IDataSetChangedCallback {
    void onDataSetChanged(List list, Bundle bundle);

    default void onReceivedExtraData(Bundle bundle) {
    }
}
