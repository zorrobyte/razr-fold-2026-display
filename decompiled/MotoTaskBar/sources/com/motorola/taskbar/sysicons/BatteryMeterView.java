package com.motorola.taskbar.sysicons;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.graph.ThemedBatteryDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.DualToneHandler;
import com.android.systemui.Interpolators;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.KeyButtonRipple;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.util.HoverWrapper;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes2.dex */
public class BatteryMeterView extends LinearLayout {
    private BatteryBroadcastReceiver mBatteryBroadcastReceiver;
    private final ImageView mBatteryIconView;
    private TextView mBatteryPercentView;
    private BroadcastDispatcher mBroadcastDispatcher;
    private boolean mCharging;
    private final ThemedBatteryDrawable mDrawable;
    private DualToneHandler mDualToneHandler;
    protected HoverWrapper mHoverWrapper;
    private int mLevel;
    private int mNonAdaptedBackgroundColor;
    private int mNonAdaptedForegroundColor;
    private int mNonAdaptedSingleToneColor;
    private final int mPercentageStyleId;
    private PowerManager mPowerManager;
    private KeyButtonRipple mRipple;
    private SettingObserver mSettingObserver;
    private boolean mShowPercentAvailable;
    private int mShowPercentMode;
    private int mTextColor;
    private boolean mUseWallpaperTextColors;
    private int mUser;
    private final CurrentUserTracker mUserTracker;

    class BatteryBroadcastReceiver extends BroadcastReceiver {
        private BatteryBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            if (!action.equals("android.intent.action.BATTERY_CHANGED")) {
                if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                    BatteryMeterView batteryMeterView = BatteryMeterView.this;
                    batteryMeterView.onPowerSaveChanged(batteryMeterView.mPowerManager.isPowerSaveMode());
                    return;
                }
                return;
            }
            int intExtra = intent.getIntExtra("level", 0);
            boolean z = intent.getIntExtra("plugged", 0) != 0;
            int intExtra2 = intent.getIntExtra("status", 1);
            char c = 5;
            if (intExtra2 != 2) {
                if (intExtra2 != 5) {
                    c = 4;
                }
            } else if (intExtra < 100) {
                c = 2;
            }
            BatteryMeterView.this.onBatteryLevelChanged(intExtra, z, c == 2);
        }

        public void registerReceiver(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            BatteryMeterView.this.mBroadcastDispatcher.registerReceiver(this, intentFilter);
        }

        public void unregisterReceiver(Context context) {
            BatteryMeterView.this.mBroadcastDispatcher.unregisterReceiver(this);
        }
    }

    final class SettingObserver extends ContentObserver {
        public SettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            BatteryMeterView.this.updateShowPercent();
            if (TextUtils.equals(uri.getLastPathSegment(), "battery_estimates_last_update_time")) {
                BatteryMeterView.this.updatePercentText();
            }
        }
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowPercentMode = 0;
        this.mBatteryBroadcastReceiver = new BatteryBroadcastReceiver();
        BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mBroadcastDispatcher = broadcastDispatcher;
        setOrientation(0);
        setGravity(8388627);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BatteryMeterView, i, 0);
        int color = typedArrayObtainStyledAttributes.getColor(R$styleable.BatteryMeterView_frameColor, context.getColor(R$color.meter_background_color));
        this.mPercentageStyleId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.BatteryMeterView_textAppearance, 0);
        ThemedBatteryDrawable themedBatteryDrawable = new ThemedBatteryDrawable(context, color);
        this.mDrawable = themedBatteryDrawable;
        typedArrayObtainStyledAttributes.recycle();
        this.mSettingObserver = new SettingObserver(new Handler(context.getMainLooper()));
        this.mShowPercentAvailable = true;
        setupLayoutTransition();
        ImageView imageView = new ImageView(context);
        this.mBatteryIconView = imageView;
        imageView.setImageDrawable(themedBatteryDrawable);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(getResources().getDimensionPixelSize(R$dimen.status_bar_battery_icon_width), getResources().getDimensionPixelSize(R$dimen.status_bar_battery_icon_height));
        marginLayoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R$dimen.battery_margin_bottom));
        addView(imageView, marginLayoutParams);
        this.mLevel = ((BatteryManager) context.getSystemService("batterymanager")).getIntProperty(4);
        updateShowPercent();
        this.mDualToneHandler = new DualToneHandler(context);
        this.mUserTracker = new CurrentUserTracker(broadcastDispatcher) { // from class: com.motorola.taskbar.sysicons.BatteryMeterView.1
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i2) {
                BatteryMeterView.this.mUser = i2;
                BatteryMeterView.this.getContext().getContentResolver().unregisterContentObserver(BatteryMeterView.this.mSettingObserver);
                BatteryMeterView.this.getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, BatteryMeterView.this.mSettingObserver, i2);
                BatteryMeterView.this.updateShowPercent();
            }
        };
        setClipChildren(false);
        setClipToPadding(false);
    }

    private TextView loadPercentView() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R$layout.battery_percentage_view, (ViewGroup) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        this.mDrawable.setCharging(z);
        this.mDrawable.setBatteryLevel(i);
        this.mCharging = z;
        this.mLevel = i;
        updatePercentText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPowerSaveChanged(boolean z) {
        this.mDrawable.setPowerSaveEnabled(z);
    }

    private void setPercentTextAtCurrentLevel() {
        this.mBatteryPercentView.setText(NumberFormat.getPercentInstance().format(this.mLevel / 100.0f));
    }

    private void setupLayoutTransition() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(200L);
        layoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f));
        layoutTransition.setInterpolator(2, Interpolators.ALPHA_IN);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
        layoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
        layoutTransition.setAnimator(3, objectAnimatorOfFloat);
        setLayoutTransition(layoutTransition);
    }

    private void updateColors(int i, int i2, int i3) {
        this.mDrawable.setColors(i, i2, i3);
        this.mTextColor = i3;
        TextView textView = this.mBatteryPercentView;
        if (textView != null) {
            textView.setTextColor(i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePercentText() {
        if (this.mBatteryPercentView != null) {
            setPercentTextAtCurrentLevel();
        }
        HoverWrapper hoverWrapper = this.mHoverWrapper;
        if (hoverWrapper != null) {
            hoverWrapper.setToolTipText(getContext().getString(this.mCharging ? R$string.accessibility_battery_level_charging : R$string.accessibility_battery_level, Integer.valueOf(this.mLevel)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShowPercent() {
        int i;
        TextView textView = this.mBatteryPercentView;
        boolean z = textView != null;
        if ((!this.mShowPercentAvailable || this.mShowPercentMode == 2) && (i = this.mShowPercentMode) != 1 && i != 3) {
            if (z) {
                removeView(textView);
                this.mBatteryPercentView = null;
                return;
            }
            return;
        }
        if (z) {
            return;
        }
        TextView textViewLoadPercentView = loadPercentView();
        this.mBatteryPercentView = textViewLoadPercentView;
        int i2 = this.mPercentageStyleId;
        if (i2 != 0) {
            textViewLoadPercentView.setTextAppearance(i2);
        }
        int i3 = this.mTextColor;
        if (i3 != 0) {
            this.mBatteryPercentView.setTextColor(i3);
        }
        updatePercentText();
        addView(this.mBatteryPercentView, new ViewGroup.LayoutParams(-2, -1));
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        HoverWrapper hoverWrapper = this.mHoverWrapper;
        if (hoverWrapper != null) {
            hoverWrapper.drawBackground(canvas);
        }
        super.draw(canvas);
    }

    public void forceHideTooltip() {
        this.mHoverWrapper.forceHideTooltip();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPowerManager = (PowerManager) ((LinearLayout) this).mContext.getSystemService(PowerManager.class);
        this.mBatteryBroadcastReceiver.registerReceiver(getContext());
        this.mUser = ActivityManager.getCurrentUser();
        getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, this.mSettingObserver, this.mUser);
        getContext().getContentResolver().registerContentObserver(Settings.Global.getUriFor("battery_estimates_last_update_time"), false, this.mSettingObserver);
        updateShowPercent();
        this.mUserTracker.startTracking();
        onPowerSaveChanged(this.mPowerManager.isPowerSaveMode());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mBatteryBroadcastReceiver.unregisterReceiver(getContext());
        this.mUserTracker.stopTracking();
        getContext().getContentResolver().unregisterContentObserver(this.mSettingObserver);
        this.mHoverWrapper.forceHideTooltip();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mHoverWrapper = HoverWrapper.register((View) this, "", true, true);
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(getContext(), this);
        this.mRipple = keyButtonRipple;
        setBackground(keyButtonRipple);
        this.mRipple.setType(KeyButtonRipple.Type.ROUNDED_RECT);
    }

    public void setDarkIntensity(float f) {
        this.mNonAdaptedSingleToneColor = this.mDualToneHandler.getSingleColor(f);
        this.mNonAdaptedForegroundColor = this.mDualToneHandler.getFillColor(f);
        int backgroundColor = this.mDualToneHandler.getBackgroundColor(f);
        this.mNonAdaptedBackgroundColor = backgroundColor;
        if (!this.mUseWallpaperTextColors) {
            updateColors(this.mNonAdaptedForegroundColor, backgroundColor, this.mNonAdaptedSingleToneColor);
        }
        this.mRipple.setDarkIntensity(f);
    }
}
