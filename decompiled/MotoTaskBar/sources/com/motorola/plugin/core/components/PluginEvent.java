package com.motorola.plugin.core.components;

import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;

/* JADX INFO: compiled from: PluginEvent.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginEvent extends OnInitializedAware, ISnapshotAware, Disposable {

    /* JADX INFO: compiled from: PluginEvent.kt */
    public final class DefaultImpls {
        public static /* synthetic */ void recordEvent$default(PluginEvent pluginEvent, String str, ISnapshot iSnapshot, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: recordEvent");
            }
            if ((i & 2) != 0) {
                iSnapshot = null;
            }
            pluginEvent.recordEvent(str, iSnapshot);
        }
    }

    void flush();

    void recordEvent(String str, ISnapshot iSnapshot);
}
