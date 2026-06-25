package com.android.systemui.haptics.qs;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class QSLongPressEffect_Factory implements Factory {

    abstract class InstanceHolder {
        static final QSLongPressEffect_Factory INSTANCE = new QSLongPressEffect_Factory();
    }

    public static QSLongPressEffect_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static QSLongPressEffect newInstance() {
        return new QSLongPressEffect();
    }

    @Override // javax.inject.Provider
    public QSLongPressEffect get() {
        return newInstance();
    }
}
