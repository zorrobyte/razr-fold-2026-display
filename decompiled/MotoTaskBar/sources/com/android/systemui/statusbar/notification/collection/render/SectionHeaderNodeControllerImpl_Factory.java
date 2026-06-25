package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import com.android.systemui.displays.ActivityStarter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SectionHeaderNodeControllerImpl_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider clickIntentActionProvider;
    private final Provider headerTextResIdProvider;
    private final Provider layoutInflaterProvider;
    private final Provider nodeLabelProvider;

    public SectionHeaderNodeControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.nodeLabelProvider = provider;
        this.layoutInflaterProvider = provider2;
        this.headerTextResIdProvider = provider3;
        this.activityStarterProvider = provider4;
        this.clickIntentActionProvider = provider5;
    }

    public static SectionHeaderNodeControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SectionHeaderNodeControllerImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static SectionHeaderNodeControllerImpl newInstance(String str, LayoutInflater layoutInflater, int i, ActivityStarter activityStarter, String str2) {
        return new SectionHeaderNodeControllerImpl(str, layoutInflater, i, activityStarter, str2);
    }

    @Override // javax.inject.Provider
    public SectionHeaderNodeControllerImpl get() {
        return newInstance((String) this.nodeLabelProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), ((Integer) this.headerTextResIdProvider.get()).intValue(), (ActivityStarter) this.activityStarterProvider.get(), (String) this.clickIntentActionProvider.get());
    }
}
