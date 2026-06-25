package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class BigPictureLayoutInflaterFactory_Factory implements Factory {

    abstract class InstanceHolder {
        static final BigPictureLayoutInflaterFactory_Factory INSTANCE = new BigPictureLayoutInflaterFactory_Factory();
    }

    public static BigPictureLayoutInflaterFactory_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BigPictureLayoutInflaterFactory newInstance() {
        return new BigPictureLayoutInflaterFactory();
    }

    @Override // javax.inject.Provider
    public BigPictureLayoutInflaterFactory get() {
        return newInstance();
    }
}
