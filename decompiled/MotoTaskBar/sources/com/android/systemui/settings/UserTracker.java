package com.android.systemui.settings;

import android.content.Context;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: UserTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public interface UserTracker extends UserContextProvider {

    /* JADX INFO: compiled from: UserTracker.kt */
    public interface Callback {
        default void onBeforeUserSwitching(int i) {
        }

        default void onProfilesChanged(List list) {
            list.getClass();
        }

        default void onUserChanged(int i, Context context) {
            context.getClass();
        }

        default void onUserChanging(int i, Context context) {
            context.getClass();
        }

        default void onUserChanging(int i, Context context, Runnable runnable) {
            context.getClass();
            runnable.getClass();
            onUserChanging(i, context);
            runnable.run();
        }
    }

    void addCallback(Callback callback, Executor executor);

    int getUserId();

    void removeCallback(Callback callback);
}
