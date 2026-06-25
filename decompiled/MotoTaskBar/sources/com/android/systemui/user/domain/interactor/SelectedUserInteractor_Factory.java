package com.android.systemui.user.domain.interactor;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SelectedUserInteractor_Factory implements Factory {

    abstract class InstanceHolder {
        static final SelectedUserInteractor_Factory INSTANCE = new SelectedUserInteractor_Factory();
    }

    public static SelectedUserInteractor_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SelectedUserInteractor newInstance() {
        return new SelectedUserInteractor();
    }

    @Override // javax.inject.Provider
    public SelectedUserInteractor get() {
        return newInstance();
    }
}
