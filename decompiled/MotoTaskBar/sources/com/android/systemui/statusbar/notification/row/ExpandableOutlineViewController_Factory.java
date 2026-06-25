package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableOutlineViewController_Factory implements Factory {
    private final Provider expandableViewControllerProvider;
    private final Provider viewProvider;

    public ExpandableOutlineViewController_Factory(Provider provider, Provider provider2) {
        this.viewProvider = provider;
        this.expandableViewControllerProvider = provider2;
    }

    public static ExpandableOutlineViewController_Factory create(Provider provider, Provider provider2) {
        return new ExpandableOutlineViewController_Factory(provider, provider2);
    }

    public static ExpandableOutlineViewController newInstance(ExpandableOutlineView expandableOutlineView, ExpandableViewController expandableViewController) {
        return new ExpandableOutlineViewController(expandableOutlineView, expandableViewController);
    }

    @Override // javax.inject.Provider
    public ExpandableOutlineViewController get() {
        return newInstance((ExpandableOutlineView) this.viewProvider.get(), (ExpandableViewController) this.expandableViewControllerProvider.get());
    }
}
