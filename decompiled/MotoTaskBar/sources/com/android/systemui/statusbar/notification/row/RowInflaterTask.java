package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutFactory;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.time.SystemClock;
import com.motorola.taskbar.R$layout;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class RowInflaterTask implements InflationTask, AsyncLayoutInflater.OnInflateFinishedListener {
    private boolean mCancelled;
    private NotificationEntry mEntry;
    private Throwable mInflateOrigin;
    private long mInflateStartTimeMs;
    private RowInflationFinishedListener mListener;
    private final RowInflaterTaskLogger mLogger;
    private final SystemClock mSystemClock;

    public class RowAsyncLayoutInflater implements AsyncLayoutFactory {
        private final NotificationEntry mEntry;
        private final RowInflaterTaskLogger mLogger;
        private final SystemClock mSystemClock;

        public RowAsyncLayoutInflater(NotificationEntry notificationEntry, SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger) {
            this.mEntry = notificationEntry;
            this.mSystemClock = systemClock;
            this.mLogger = rowInflaterTaskLogger;
        }

        @Override // android.view.LayoutInflater.Factory2
        public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            if (!str.equals(ExpandableNotificationRow.class.getName())) {
                return null;
            }
            long jElapsedRealtime = this.mSystemClock.elapsedRealtime();
            ExpandableNotificationRow expandableNotificationRow = new ExpandableNotificationRow(context, attributeSet, this.mEntry);
            this.mLogger.logCreatedRow(this.mEntry, this.mSystemClock.elapsedRealtime() - jElapsedRealtime);
            return expandableNotificationRow;
        }

        @Override // android.view.LayoutInflater.Factory
        public View onCreateView(String str, Context context, AttributeSet attributeSet) {
            return null;
        }
    }

    public interface RowInflationFinishedListener {
        void onInflationFinished(ExpandableNotificationRow expandableNotificationRow);
    }

    public RowInflaterTask(SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger) {
        this.mSystemClock = systemClock;
        this.mLogger = rowInflaterTaskLogger;
    }

    private RowAsyncLayoutInflater makeRowInflater(NotificationEntry notificationEntry) {
        return new RowAsyncLayoutInflater(notificationEntry, this.mSystemClock, this.mLogger);
    }

    @Override // com.android.systemui.statusbar.InflationTask
    public void abort() {
        this.mCancelled = true;
    }

    public void inflate(Context context, ViewGroup viewGroup, NotificationEntry notificationEntry, RowInflationFinishedListener rowInflationFinishedListener) {
        inflate(context, viewGroup, notificationEntry, null, rowInflationFinishedListener);
    }

    public void inflate(Context context, ViewGroup viewGroup, NotificationEntry notificationEntry, Executor executor, RowInflationFinishedListener rowInflationFinishedListener) {
        this.mInflateOrigin = new Throwable("inflate requested here");
        this.mListener = rowInflationFinishedListener;
        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context, makeRowInflater(notificationEntry));
        this.mEntry = notificationEntry;
        notificationEntry.setInflationTask(this);
        this.mLogger.logInflateStart(notificationEntry);
        this.mInflateStartTimeMs = this.mSystemClock.elapsedRealtime();
        asyncLayoutInflater.inflate(R$layout.status_bar_notification_row, viewGroup, executor, this);
    }

    @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
    public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
        this.mLogger.logInflateFinish(this.mEntry, this.mSystemClock.elapsedRealtime() - this.mInflateStartTimeMs, this.mCancelled);
        if (this.mCancelled) {
            return;
        }
        try {
            this.mEntry.onInflationTaskFinished();
            this.mListener.onInflationFinished((ExpandableNotificationRow) view);
        } catch (Throwable th) {
            if (this.mInflateOrigin != null) {
                Log.e("RowInflaterTask", "Error in inflation finished listener: " + th, this.mInflateOrigin);
                th.addSuppressed(this.mInflateOrigin);
            }
            throw th;
        }
    }
}
