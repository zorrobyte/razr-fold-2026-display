package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import android.os.Trace;

/* JADX INFO: loaded from: classes.dex */
public abstract class Pluggable {
    private PluggableListener mListener;
    private final String mName;

    public interface PluggableListener {
        void onPluggableInvalidated(Object obj, String str);
    }

    Pluggable(String str) {
        this.mName = str;
    }

    public final String getName() {
        return this.mName;
    }

    public final void invalidateList(String str) {
        if (this.mListener != null) {
            if (Trace.isEnabled()) {
                Trace.traceBegin(4096L, "Pluggable<" + this.mName + ">.invalidateList");
            }
            this.mListener.onPluggableInvalidated(this, str);
            Trace.endSection();
        }
    }

    public void onCleanup() {
    }

    public final void setInvalidationListener(PluggableListener pluggableListener) {
        this.mListener = pluggableListener;
    }
}
