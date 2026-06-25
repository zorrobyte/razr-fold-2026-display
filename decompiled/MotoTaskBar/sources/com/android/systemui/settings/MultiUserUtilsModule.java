package com.android.systemui.settings;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.dump.DumpManager;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public abstract class MultiUserUtilsModule {
    static UserTracker provideUserTracker(Context context, UserManager userManager, IActivityManager iActivityManager, DumpManager dumpManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        int currentUser = ActivityManager.getCurrentUser();
        UserTrackerImpl userTrackerImpl = new UserTrackerImpl(context, userManager, iActivityManager, dumpManager, coroutineScope, coroutineDispatcher, handler);
        userTrackerImpl.initialize(currentUser);
        return userTrackerImpl;
    }
}
