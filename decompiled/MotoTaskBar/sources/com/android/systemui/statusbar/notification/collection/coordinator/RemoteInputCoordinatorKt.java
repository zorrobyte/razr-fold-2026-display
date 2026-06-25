package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: RemoteInputCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RemoteInputCoordinatorKt {
    private static final Lazy DEBUG$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Object mo2224invoke() {
            return Boolean.valueOf(RemoteInputCoordinatorKt.DEBUG_delegate$lambda$0());
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean DEBUG_delegate$lambda$0() {
        return Log.isLoggable("RemoteInputCoordinator", 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getDEBUG() {
        return ((Boolean) DEBUG$delegate.getValue()).booleanValue();
    }
}
