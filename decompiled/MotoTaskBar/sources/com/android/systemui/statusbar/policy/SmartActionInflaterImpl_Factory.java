package com.android.systemui.statusbar.policy;

import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.SmartReplyController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SmartActionInflaterImpl_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider constantsProvider;
    private final Provider headsUpManagerProvider;
    private final Provider smartReplyControllerProvider;

    public SmartActionInflaterImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.constantsProvider = provider;
        this.activityStarterProvider = provider2;
        this.smartReplyControllerProvider = provider3;
        this.headsUpManagerProvider = provider4;
    }

    public static SmartActionInflaterImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new SmartActionInflaterImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static SmartActionInflaterImpl newInstance(SmartReplyConstants smartReplyConstants, ActivityStarter activityStarter, SmartReplyController smartReplyController, HeadsUpManager headsUpManager) {
        return new SmartActionInflaterImpl(smartReplyConstants, activityStarter, smartReplyController, headsUpManager);
    }

    @Override // javax.inject.Provider
    public SmartActionInflaterImpl get() {
        return newInstance((SmartReplyConstants) this.constantsProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SmartReplyController) this.smartReplyControllerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get());
    }
}
