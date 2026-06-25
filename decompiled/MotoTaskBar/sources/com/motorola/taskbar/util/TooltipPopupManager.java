package com.motorola.taskbar.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: loaded from: classes2.dex */
public class TooltipPopupManager {
    private AdapterView.OnItemClickListener mAdapterViewOnItemClickListener;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private BroadcastReceiver mBroadcastReceiver;
    private final Context mContext;
    private long mShowingId = -1;
    private TaskBarServiceCallBack mTaskBarServiceCallBack;
    private TaskBarServiceProxy mTaskBarServiceProxy;
    private boolean mTaskBarShowing;
    private final TooltipPopup mTooltipPopup;

    public interface OnItemClickListener {
    }

    /* JADX INFO: renamed from: -$$Nest$fgetmOnItemClickListener, reason: not valid java name */
    static /* bridge */ /* synthetic */ OnItemClickListener m2669$$Nest$fgetmOnItemClickListener(TooltipPopupManager tooltipPopupManager) {
        tooltipPopupManager.getClass();
        return null;
    }

    public TooltipPopupManager(Context context) {
        BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mTaskBarShowing = true;
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.util.TooltipPopupManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    TooltipPopupManager.this.mTooltipPopup.hide(true);
                }
            }
        };
        this.mAdapterViewOnItemClickListener = new AdapterView.OnItemClickListener() { // from class: com.motorola.taskbar.util.TooltipPopupManager.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView adapterView, View view, int i, long j) {
                TooltipPopupManager.m2669$$Nest$fgetmOnItemClickListener(TooltipPopupManager.this);
                throw null;
            }
        };
        this.mTaskBarServiceCallBack = new TaskBarServiceCallBack() { // from class: com.motorola.taskbar.util.TooltipPopupManager.3
            @Override // com.motorola.taskbar.TaskBarServiceCallBack
            public void onTaskbarWindowStateChanged(int i, int i2) {
                if (TooltipPopupManager.this.mContext == null || TooltipPopupManager.this.mContext.getDisplayId() != i) {
                    return;
                }
                TooltipPopupManager.this.mTaskBarShowing = i2 == 0;
                if (!TooltipPopupManager.this.isShowing() || TooltipPopupManager.this.mTaskBarShowing) {
                    return;
                }
                TooltipPopupManager.this.mTooltipPopup.hide(true);
            }
        };
        this.mContext = context;
        this.mTooltipPopup = new TooltipPopup(context);
        broadcastDispatcher.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
        TaskBarServiceProxy taskBarServiceProxy = (TaskBarServiceProxy) Dependency.get(TaskBarServiceProxy.class);
        this.mTaskBarServiceProxy = taskBarServiceProxy;
        taskBarServiceProxy.addCallback(this.mTaskBarServiceCallBack);
    }

    private long beginShow() {
        long j = this.mShowingId + 1;
        this.mShowingId = j;
        return j;
    }

    public void destroy() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mBroadcastReceiver);
        this.mTaskBarServiceProxy.removeCallback(this.mTaskBarServiceCallBack);
        this.mTooltipPopup.hide(true);
        this.mTooltipPopup.destroy();
    }

    public synchronized long getShowingId() {
        return this.mTooltipPopup.getShowingId();
    }

    public synchronized void hide(long j, boolean z) {
        if (getShowingId() == j) {
            this.mTooltipPopup.hide(z);
        }
    }

    public synchronized boolean isShowing() {
        return this.mTooltipPopup.isShowing();
    }

    public void rePostTimeOut(long j) {
        if (getShowingId() == j) {
            this.mTooltipPopup.rePostTimeOut();
        }
    }

    public synchronized long show(int i, View view, int i2, CharSequence charSequence, View.OnClickListener onClickListener) {
        if (!this.mTaskBarShowing) {
            return -1L;
        }
        long jBeginShow = beginShow();
        this.mTooltipPopup.show(jBeginShow, i, view, 0, i2, charSequence, onClickListener);
        return jBeginShow;
    }

    public synchronized long show(int i, View view, int i2, boolean z, View view2, ViewGroup.LayoutParams layoutParams) {
        if (!this.mTaskBarShowing) {
            return -1L;
        }
        long jBeginShow = beginShow();
        this.mTooltipPopup.show(jBeginShow, i, view, i2, z, view2, layoutParams);
        return jBeginShow;
    }

    public synchronized long show(int i, View view, int i2, boolean z, CharSequence charSequence) {
        if (!this.mTaskBarShowing) {
            return -1L;
        }
        long jBeginShow = beginShow();
        this.mTooltipPopup.show(jBeginShow, i, view, i2, z, charSequence);
        return jBeginShow;
    }
}
