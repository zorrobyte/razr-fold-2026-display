package com.android.systemui.statusbar.policy;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SmartReplyStateInflaterImpl_Factory implements Factory {
    private final Provider activityManagerWrapperProvider;
    private final Provider constantsProvider;
    private final Provider devicePolicyManagerWrapperProvider;
    private final Provider packageManagerWrapperProvider;
    private final Provider smartActionsInflaterProvider;
    private final Provider smartRepliesInflaterProvider;

    public SmartReplyStateInflaterImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.constantsProvider = provider;
        this.activityManagerWrapperProvider = provider2;
        this.packageManagerWrapperProvider = provider3;
        this.devicePolicyManagerWrapperProvider = provider4;
        this.smartRepliesInflaterProvider = provider5;
        this.smartActionsInflaterProvider = provider6;
    }

    public static SmartReplyStateInflaterImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new SmartReplyStateInflaterImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static SmartReplyStateInflaterImpl newInstance(SmartReplyConstants smartReplyConstants, ActivityManagerWrapper activityManagerWrapper, PackageManagerWrapper packageManagerWrapper, DevicePolicyManagerWrapper devicePolicyManagerWrapper, SmartReplyInflater smartReplyInflater, SmartActionInflater smartActionInflater) {
        return new SmartReplyStateInflaterImpl(smartReplyConstants, activityManagerWrapper, packageManagerWrapper, devicePolicyManagerWrapper, smartReplyInflater, smartActionInflater);
    }

    @Override // javax.inject.Provider
    public SmartReplyStateInflaterImpl get() {
        return newInstance((SmartReplyConstants) this.constantsProvider.get(), (ActivityManagerWrapper) this.activityManagerWrapperProvider.get(), (PackageManagerWrapper) this.packageManagerWrapperProvider.get(), (DevicePolicyManagerWrapper) this.devicePolicyManagerWrapperProvider.get(), (SmartReplyInflater) this.smartRepliesInflaterProvider.get(), (SmartActionInflater) this.smartActionsInflaterProvider.get());
    }
}
