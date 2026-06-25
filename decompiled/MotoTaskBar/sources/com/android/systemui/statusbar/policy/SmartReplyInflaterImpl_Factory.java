package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SmartReplyInflaterImpl_Factory implements Factory {
    private final Provider constantsProvider;
    private final Provider contextProvider;
    private final Provider keyguardDismissUtilProvider;
    private final Provider remoteInputManagerProvider;
    private final Provider smartReplyControllerProvider;

    public SmartReplyInflaterImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.constantsProvider = provider;
        this.keyguardDismissUtilProvider = provider2;
        this.remoteInputManagerProvider = provider3;
        this.smartReplyControllerProvider = provider4;
        this.contextProvider = provider5;
    }

    public static SmartReplyInflaterImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SmartReplyInflaterImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static SmartReplyInflaterImpl newInstance(SmartReplyConstants smartReplyConstants, KeyguardDismissUtil keyguardDismissUtil, NotificationRemoteInputManager notificationRemoteInputManager, SmartReplyController smartReplyController, Context context) {
        return new SmartReplyInflaterImpl(smartReplyConstants, keyguardDismissUtil, notificationRemoteInputManager, smartReplyController, context);
    }

    @Override // javax.inject.Provider
    public SmartReplyInflaterImpl get() {
        return newInstance((SmartReplyConstants) this.constantsProvider.get(), (KeyguardDismissUtil) this.keyguardDismissUtilProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (SmartReplyController) this.smartReplyControllerProvider.get(), (Context) this.contextProvider.get());
    }
}
