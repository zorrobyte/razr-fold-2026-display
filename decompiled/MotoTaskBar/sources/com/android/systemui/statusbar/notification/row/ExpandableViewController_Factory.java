package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableViewController_Factory implements Factory {
    private final Provider viewProvider;

    public ExpandableViewController_Factory(Provider provider) {
        this.viewProvider = provider;
    }

    public static ExpandableViewController_Factory create(Provider provider) {
        return new ExpandableViewController_Factory(provider);
    }

    public static ExpandableViewController newInstance(ExpandableView expandableView) {
        return new ExpandableViewController(expandableView);
    }

    @Override // javax.inject.Provider
    public ExpandableViewController get() {
        return newInstance((ExpandableView) this.viewProvider.get());
    }
}
