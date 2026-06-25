package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class VisualInterruptionDecisionLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public VisualInterruptionDecisionLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static VisualInterruptionDecisionLogger_Factory create(Provider provider) {
        return new VisualInterruptionDecisionLogger_Factory(provider);
    }

    public static VisualInterruptionDecisionLogger newInstance(LogBuffer logBuffer) {
        return new VisualInterruptionDecisionLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public VisualInterruptionDecisionLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
