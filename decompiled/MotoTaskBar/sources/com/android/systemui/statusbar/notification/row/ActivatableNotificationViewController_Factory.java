package com.android.systemui.statusbar.notification.row;

import android.view.accessibility.AccessibilityManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ActivatableNotificationViewController_Factory implements Factory {
    private final Provider accessibilityManagerProvider;
    private final Provider expandableOutlineViewControllerProvider;
    private final Provider viewProvider;

    public ActivatableNotificationViewController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.viewProvider = provider;
        this.expandableOutlineViewControllerProvider = provider2;
        this.accessibilityManagerProvider = provider3;
    }

    public static ActivatableNotificationViewController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ActivatableNotificationViewController_Factory(provider, provider2, provider3);
    }

    public static ActivatableNotificationViewController newInstance(ActivatableNotificationView activatableNotificationView, ExpandableOutlineViewController expandableOutlineViewController, AccessibilityManager accessibilityManager) {
        return new ActivatableNotificationViewController(activatableNotificationView, expandableOutlineViewController, accessibilityManager);
    }

    @Override // javax.inject.Provider
    public ActivatableNotificationViewController get() {
        return newInstance((ActivatableNotificationView) this.viewProvider.get(), (ExpandableOutlineViewController) this.expandableOutlineViewControllerProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get());
    }
}
