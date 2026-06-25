package com.android.systemui.statusbar.phone;

import com.android.systemui.displays.ActivityStarter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class KeyguardDismissUtil_Factory implements Factory {
    private final Provider activityStarterProvider;

    public KeyguardDismissUtil_Factory(Provider provider) {
        this.activityStarterProvider = provider;
    }

    public static KeyguardDismissUtil_Factory create(Provider provider) {
        return new KeyguardDismissUtil_Factory(provider);
    }

    public static KeyguardDismissUtil newInstance(ActivityStarter activityStarter) {
        return new KeyguardDismissUtil(activityStarter);
    }

    @Override // javax.inject.Provider
    public KeyguardDismissUtil get() {
        return newInstance((ActivityStarter) this.activityStarterProvider.get());
    }
}
