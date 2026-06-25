package com.motorola.taskbar.sysicons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.settings.utils.SettingsUtils;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopSettingsIconView extends FrameLayout {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_SYSTEM_ICON;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final BroadcastReceiver mDongleReceiver;
    private ImageView mIndicate;
    private int mLayoutId;
    private KeyButtonView mMainIconView;

    public DesktopSettingsIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DesktopSettingsIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mDongleReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.sysicons.DesktopSettingsIconView.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.motorola.mobiledesktop.action_dongle_upgrade_info".equals(intent.getAction())) {
                    DesktopSettingsIconView.this.updateView();
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CustomKeyButtonView, 0, 0);
        if (typedArrayObtainStyledAttributes != null) {
            this.mLayoutId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.CustomKeyButtonView_layoutId, R$layout.desktop_settings_icon);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private KeyButtonDrawable getKeyButtonDrawable(int i) {
        return KeyButtonDrawable.create(((FrameLayout) this).mContext, i, false);
    }

    private void registerDongleReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.motorola.mobiledesktop.action_dongle_upgrade_info");
        this.mBroadcastDispatcher.registerReceiver(this.mDongleReceiver, intentFilter);
    }

    private void unRegisterDongleReceiver() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mDongleReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateView() {
        if (this.mMainIconView == null) {
            return;
        }
        if (SettingsUtils.isDongleConnected() && SettingsUtils.isDongleUpgradeAvailable()) {
            this.mMainIconView.setImageDrawable(getKeyButtonDrawable(R$drawable.ic_display_settings_hollow));
            this.mIndicate.setVisibility(0);
        } else {
            this.mMainIconView.setImageDrawable(getKeyButtonDrawable(((MotoFeature) Dependency.get(MotoFeature.class)).isMobileUiOrAppStreamDisplay(getContext()) ? R$drawable.ic_mobileui_display_settings : R$drawable.ic_display_settings_24px));
            this.mIndicate.setVisibility(8);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerDongleReceiver();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unRegisterDongleReceiver();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(this.mLayoutId, (ViewGroup) null);
        addView(viewGroup, -1, -1);
        this.mMainIconView = (KeyButtonView) viewGroup.findViewById(R$id.main_icon);
        this.mIndicate = (ImageView) viewGroup.findViewById(R$id.indicate);
        updateView();
    }

    public void setDarkIntensity(float f) {
        KeyButtonView keyButtonView = this.mMainIconView;
        if (keyButtonView != null) {
            keyButtonView.setDarkIntensity(f);
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        KeyButtonView keyButtonView = this.mMainIconView;
        if (keyButtonView != null) {
            keyButtonView.setOnClickListener(onClickListener);
        }
    }
}
