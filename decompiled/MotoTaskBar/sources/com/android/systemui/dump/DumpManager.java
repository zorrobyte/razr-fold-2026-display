package com.android.systemui.dump;

import com.android.systemui.Dumpable;

/* JADX INFO: compiled from: DumpManager.kt */
/* JADX INFO: loaded from: classes.dex */
public class DumpManager {
    public static /* synthetic */ void registerDumpable$default(DumpManager dumpManager, String str, Dumpable dumpable, DumpPriority dumpPriority, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerDumpable");
        }
        if ((i & 4) != 0) {
            dumpPriority = DumpPriority.CRITICAL;
        }
        dumpManager.registerDumpable(str, dumpable, dumpPriority);
    }

    public final void registerCriticalDumpable(String str, Dumpable dumpable) {
        str.getClass();
        dumpable.getClass();
    }

    public final synchronized void registerDumpable(Dumpable dumpable) {
        dumpable.getClass();
    }

    public final void registerDumpable(String str, Dumpable dumpable) {
        str.getClass();
        dumpable.getClass();
        registerDumpable$default(this, str, dumpable, null, 4, null);
    }

    public final synchronized void registerDumpable(String str, Dumpable dumpable, DumpPriority dumpPriority) {
        str.getClass();
        dumpable.getClass();
        dumpPriority.getClass();
    }

    public final void registerNormalDumpable(String str, Dumpable dumpable) {
        str.getClass();
        dumpable.getClass();
    }

    public final synchronized void unregisterDumpable(String str) {
        str.getClass();
    }
}
