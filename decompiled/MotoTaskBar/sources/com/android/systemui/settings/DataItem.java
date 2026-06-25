package com.android.systemui.settings;

import com.android.systemui.settings.UserTracker;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserTrackerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
final class DataItem {
    private final WeakReference callback;
    private final Executor executor;

    public DataItem(WeakReference weakReference, Executor executor) {
        weakReference.getClass();
        executor.getClass();
        this.callback = weakReference;
        this.executor = executor;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataItem)) {
            return false;
        }
        DataItem dataItem = (DataItem) obj;
        return Intrinsics.areEqual(this.callback, dataItem.callback) && Intrinsics.areEqual(this.executor, dataItem.executor);
    }

    public final WeakReference getCallback() {
        return this.callback;
    }

    public final Executor getExecutor() {
        return this.executor;
    }

    public int hashCode() {
        return (this.callback.hashCode() * 31) + this.executor.hashCode();
    }

    public final boolean sameOrEmpty(UserTracker.Callback callback) {
        callback.getClass();
        UserTracker.Callback callback2 = (UserTracker.Callback) this.callback.get();
        if (callback2 != null) {
            return callback2.equals(callback);
        }
        return true;
    }

    public String toString() {
        return "DataItem(callback=" + this.callback + ", executor=" + this.executor + ")";
    }
}
