package com.motorola.taskbar.panel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;

/* JADX INFO: loaded from: classes2.dex */
public class MirrorPhonePanel extends LinearLayout {
    private final BroadcastDispatcher mBroadcastDispatcher;
    private View mInjectKeyView;
    private BroadcastReceiver mIntentReceiver;
    private TextView mTitleTextView;

    public MirrorPhonePanel(Context context) {
        super(context);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.panel.MirrorPhonePanel.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    MirrorPhonePanel.this.refreshView();
                }
            }
        };
    }

    public MirrorPhonePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.panel.MirrorPhonePanel.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    MirrorPhonePanel.this.refreshView();
                }
            }
        };
    }

    public MirrorPhonePanel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.panel.MirrorPhonePanel.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    MirrorPhonePanel.this.refreshView();
                }
            }
        };
    }

    public MirrorPhonePanel(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.panel.MirrorPhonePanel.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    MirrorPhonePanel.this.refreshView();
                }
            }
        };
    }

    private void addKeyInjectWindow() {
        if (this.mInjectKeyView != null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(1, 1, 2041, 16, -3);
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.gravity = 51;
        layoutParams.setTitle("MotoMirrorPhoneInjectKeyWindow: " + getContext().getDisplay().getDisplayId());
        this.mInjectKeyView = LayoutInflater.from(getContext()).inflate(R$layout.mirror_panel_inject_key, (ViewGroup) null);
        ((WindowManager) getContext().getSystemService("window")).addView(this.mInjectKeyView, layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshView() {
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setText(getResources().getString(R$string.mirror_phone_setting_description));
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mBroadcastDispatcher.registerReceiver(this.mIntentReceiver, intentFilter);
    }

    private void removeKeyInjectWindow() {
        if (this.mInjectKeyView == null) {
            return;
        }
        ((WindowManager) getContext().getSystemService("window")).removeView(this.mInjectKeyView);
        this.mInjectKeyView = null;
    }

    private void unregisterReceiver() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            Log.d("MirrorPhonePanel", "ACTION_DOWN");
            addKeyInjectWindow();
        } else if (action == 4) {
            Log.d("MirrorPhonePanel", "ACTION_OUTSIDE");
            removeKeyInjectWindow();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addKeyInjectWindow();
        registerReceiver();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeKeyInjectWindow();
        unregisterReceiver();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleTextView = (TextView) findViewById(R$id.title_text);
    }

    public boolean requestRemoveKeyInjectWindow() {
        if (this.mInjectKeyView == null) {
            return false;
        }
        removeKeyInjectWindow();
        return true;
    }
}
