package com.motorola.taskbar.qsnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: loaded from: classes2.dex */
public class QsNotificationTooltipPopupManager {
    private final Context mContext;
    private final QsNotificationTooltipPopup mQsNotificationTooltipPopup;
    private long mShowingId = -1;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.qsnotification.QsNotificationTooltipPopupManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                QsNotificationTooltipPopupManager.this.mQsNotificationTooltipPopup.hide(true);
            }
        }
    };
    private AdapterView.OnItemClickListener mAdapterViewOnItemClickListener = new AdapterView.OnItemClickListener() { // from class: com.motorola.taskbar.qsnotification.QsNotificationTooltipPopupManager.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            QsNotificationTooltipPopupManager.m2494$$Nest$fgetmOnItemClickListener(QsNotificationTooltipPopupManager.this);
            throw null;
        }
    };

    public interface OnItemClickListener {
    }

    /* JADX INFO: renamed from: -$$Nest$fgetmOnItemClickListener, reason: not valid java name */
    static /* bridge */ /* synthetic */ OnItemClickListener m2494$$Nest$fgetmOnItemClickListener(QsNotificationTooltipPopupManager qsNotificationTooltipPopupManager) {
        qsNotificationTooltipPopupManager.getClass();
        return null;
    }

    public QsNotificationTooltipPopupManager(Context context) {
        this.mContext = context;
        this.mQsNotificationTooltipPopup = new QsNotificationTooltipPopup(context);
        context.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
    }

    private long beginShow() {
        long j = this.mShowingId + 1;
        this.mShowingId = j;
        return j;
    }

    public synchronized long getShowingId() {
        return this.mQsNotificationTooltipPopup.getShowingId();
    }

    public synchronized void hide(long j, boolean z) {
        if (getShowingId() == j) {
            this.mQsNotificationTooltipPopup.hide(z);
        }
    }

    public synchronized long show(int i, View view, int i2, int i3, CharSequence charSequence, View.OnClickListener onClickListener) {
        long jBeginShow;
        jBeginShow = beginShow();
        this.mQsNotificationTooltipPopup.show(jBeginShow, i, view, i2, i3, charSequence, onClickListener);
        return jBeginShow;
    }
}
