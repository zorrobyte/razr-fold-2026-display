package com.motorola.taskbar.recent;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import com.android.systemui.plugins.moto.ActivityStarter;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RecentsController.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RecentsController {
    public static final Companion Companion = new Companion(null);
    private final ActivityStarter activityStarter;
    private final Context context;
    private SparseBooleanArray isRecentsShowing;
    private SparseLongArray mLastToggleTime;
    private SparseArray mRecentsRequestCallback;

    /* JADX INFO: compiled from: RecentsController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public RecentsController(Context context, ActivityStarter activityStarter) {
        context.getClass();
        activityStarter.getClass();
        this.context = context;
        this.activityStarter = activityStarter;
        this.mLastToggleTime = new SparseLongArray();
        this.mRecentsRequestCallback = new SparseArray();
        this.isRecentsShowing = new SparseBooleanArray();
    }

    public static /* synthetic */ void hideRecents$default(RecentsController recentsController, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        recentsController.hideRecents(i, z);
    }

    private final void requestLaunchNextTask(int i, boolean z) {
        RecentsRequestCallback recentsRequestCallback = (RecentsRequestCallback) this.mRecentsRequestCallback.get(i);
        if (recentsRequestCallback != null) {
            recentsRequestCallback.requestLaunchNextTask(i, z);
        }
    }

    private final void requestRecentsHide(int i, boolean z) {
        RecentsRequestCallback recentsRequestCallback = (RecentsRequestCallback) this.mRecentsRequestCallback.get(i);
        if (recentsRequestCallback != null) {
            recentsRequestCallback.requestHideRecents(i, z);
        }
    }

    private final void startRecentsActivity(int i, boolean z) {
        Intent intentLaunchIntent = RecentsActivity.Companion.launchIntent(this.context, z);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(i);
        activityOptionsMakeBasic.setLaunchWindowingMode(1);
        this.activityStarter.startActivity(intentLaunchIntent, activityOptionsMakeBasic);
    }

    public final void addCallback(int i, RecentsRequestCallback recentsRequestCallback) {
        recentsRequestCallback.getClass();
        this.mRecentsRequestCallback.set(i, recentsRequestCallback);
    }

    public final void hideRecents(int i, boolean z) {
        requestRecentsHide(i, z);
    }

    public final boolean isRecentsVisible(int i) {
        return this.isRecentsShowing.get(i);
    }

    public final void launchNextTask(int i, boolean z) {
        requestLaunchNextTask(i, z);
    }

    public final void removeCallback(int i) {
        this.mRecentsRequestCallback.set(i, null);
    }

    public final void reportRecentsHide(int i) {
        this.isRecentsShowing.put(i, false);
    }

    public final void reportRecentsShow(int i) {
        this.isRecentsShowing.put(i, true);
    }

    public final void showRecents(int i, boolean z) {
        startRecentsActivity(i, z);
    }

    public final void toggleRecents(int i, boolean z) {
        if (i == 0) {
            return;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mLastToggleTime.get(i);
        if (!isRecentsVisible(i)) {
            Log.d("RecentsController", "toggleRecents: invisible, withAltTab? " + z);
            if (jElapsedRealtime < 350) {
                return;
            }
            Log.d("RecentsController", "toggleRecents: show recents, with tab? " + z);
            showRecents(i, z);
            ActivityManagerWrapper.getInstance().closeSystemWindows("recentapps");
            this.mLastToggleTime.put(i, SystemClock.elapsedRealtime());
            return;
        }
        Log.d("RecentsController", "toggleRecents: visible, isRecentsShowing? " + this.isRecentsShowing + ", withAltTab? " + z);
        if (!z) {
            Log.d("RecentsController", "toggleRecents: hide recents");
            hideRecents$default(this, i, false, 2, null);
        } else {
            if (jElapsedRealtime < 350) {
                return;
            }
            Log.d("RecentsController", "toggleRecents: launch next task with tab");
            launchNextTask(i, true);
            this.mLastToggleTime.put(i, SystemClock.elapsedRealtime());
        }
    }
}
