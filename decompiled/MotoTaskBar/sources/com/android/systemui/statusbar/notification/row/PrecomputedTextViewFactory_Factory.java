package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class PrecomputedTextViewFactory_Factory implements Factory {

    abstract class InstanceHolder {
        static final PrecomputedTextViewFactory_Factory INSTANCE = new PrecomputedTextViewFactory_Factory();
    }

    public static PrecomputedTextViewFactory_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PrecomputedTextViewFactory newInstance() {
        return new PrecomputedTextViewFactory();
    }

    @Override // javax.inject.Provider
    public PrecomputedTextViewFactory get() {
        return newInstance();
    }
}
