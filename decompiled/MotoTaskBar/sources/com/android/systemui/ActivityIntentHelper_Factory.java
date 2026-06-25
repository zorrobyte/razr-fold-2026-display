package com.android.systemui;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityIntentHelper_Factory implements Factory {
    private final Provider contextProvider;

    public ActivityIntentHelper_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static ActivityIntentHelper_Factory create(Provider provider) {
        return new ActivityIntentHelper_Factory(provider);
    }

    public static ActivityIntentHelper newInstance(Context context) {
        return new ActivityIntentHelper(context);
    }

    @Override // javax.inject.Provider
    public ActivityIntentHelper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
