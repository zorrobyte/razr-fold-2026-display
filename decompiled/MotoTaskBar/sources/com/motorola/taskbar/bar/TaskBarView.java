package com.motorola.taskbar.bar;

import android.R;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.panel.VolumePanelLayout;
import com.motorola.taskbar.recent.TaskBarRecentsView;
import com.motorola.taskbar.sysicons.BatteryMeterView;
import com.motorola.taskbar.sysicons.DesktopSettingsIconView;
import com.motorola.taskbar.sysicons.NotificationSysIconView;
import com.motorola.taskbar.sysicons.VolumeSysIconView;
import com.motorola.taskbar.sysicons.WifiSysIconView;
import com.motorola.taskbar.util.AppUtils;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarView extends FrameLayout implements ConfigurationController.ConfigurationListener {
    protected static final Boolean DEBUG = Boolean.valueOf(DebugConfig.DEBUG_COMMON);
    private KeyButtonView mAppTrayButton;
    private KeyButtonView mAppsScrollBack;
    private KeyButtonView mAppsScrollForward;
    private final ContentObserver mAudioContentObserver;
    protected boolean mAudioIconShow;
    protected KeyButtonView mBackButton;
    private BarBackgroundDrawable mBarBackground;
    private BatteryMeterView mBatteryMeterView;
    private View mClockDateDiv;
    protected Clock mClockView;
    private KeyButtonView mCollapseExpandAppsButton;
    private ContentObserver mContentObserver;
    private Context mCurrentUserContext;
    protected float mDarkIntensity;
    protected DateView mDateView;
    protected HashMap mDesktopStatusBarIconInfoMap;
    private DevicePolicyReceiver mDevicePolicyReceiver;
    private int mDisplayId;
    protected ViewGroup mDynamicSysiconsContainer;
    private KeyButtonView mEthernetButton;
    protected KeyButtonView mHomeButton;
    protected KeyButtonView mIMESysIconView;
    private StatusBarIcon mImeStatusBarIcon;
    private boolean mIsRecentsViewCollapseExpanding;
    private boolean mIsRecentsViewExpanded;
    protected LayoutInflater mLayoutInflater;
    protected KeyButtonView mLockButton;
    private KeyButtonView mMirrorPhoneButton;
    private MirrorPhonePanelController mMirrorPhonePanelController;
    protected KeyButtonView mMoreButton;
    private NotificationSysIconView mNotificationSysIconView;
    private TaskBarRecentsView.OnRecentsStatusListener mOnRecentsStatusListener;
    private QSNotificationPanelController mQSNotificationPanelController;
    protected KeyButtonView mRecentsButton;
    protected Set mRestartBlackPackageList;
    public KeyButtonView mRestartButton;
    protected KeyButtonView mRotationButton;
    protected KeyButtonView mScreenshotButton;
    protected ScreenshotController mScreenshotController;
    private KeyButtonView mSearchButton;
    protected DesktopSettingsIconView mSettingsButton;
    protected TaskBarButtonClickHelper mTaskBarButtonClickHelper;
    protected ViewGroup mTaskBarContentView;
    private TaskBarRecentsView mTaskBarRecentsView;
    private TaskBarServiceProxy mTaskBarServiceProxy;
    protected ViewGroup mTaskbarCenterContain;
    private int mTaskbarCenterContainOriginWidth;
    protected ViewGroup mTaskbarLeftContain;
    protected ViewGroup mTaskbarRightContain;
    private int mTaskbarRightContainOriginWidth;
    private Handler mUiHandler;
    private CurrentUserTracker mUserTracker;
    protected VolumeSysIconView mVolumeSysIconView;
    private WifiSysIconView mWifiSysIconView;

    public class BarBackgroundDrawable extends Drawable {
        private boolean mAnimating;
        private int mColor;
        private int mColorStart;
        private final int mDivColor;
        private final int mDivHeight;
        private long mEndTime;
        private Rect mFrame;
        private final int mOpaque;
        private final int mSemiTransparent;
        private long mStartTime;
        private PorterDuffColorFilter mTintFilter;
        private final int mTransparent;
        private final int mWarning;
        private int mMode = -1;
        private Paint mPaint = new Paint();

        public BarBackgroundDrawable(Context context) {
            context.getResources();
            this.mOpaque = context.getColor(R$color.system_bar_background_opaque);
            this.mSemiTransparent = context.getColor(R$color.system_bar_background_semi_transparent);
            this.mTransparent = context.getColor(R$color.system_bar_background_transparent);
            this.mWarning = Utils.getColorAttrDefaultColor(context, R.attr.colorError);
            this.mDivColor = context.getColor(R$color.system_bar_top_div);
            this.mDivHeight = context.getResources().getDimensionPixelSize(R$dimen.system_bar_top_div_height);
        }

        public void applyModeBackground(int i, boolean z) {
            if (this.mMode == i) {
                return;
            }
            this.mMode = i;
            this.mAnimating = z;
            if (z) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                this.mStartTime = jElapsedRealtime;
                this.mEndTime = jElapsedRealtime + 200;
                this.mColorStart = this.mColor;
            }
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int i = this.mMode;
            int i2 = i == 5 ? this.mWarning : (i == 2 || i == 1) ? this.mSemiTransparent : (i == 0 || i == 6) ? this.mTransparent : this.mOpaque;
            if (this.mAnimating) {
                if (SystemClock.elapsedRealtime() >= this.mEndTime) {
                    this.mAnimating = false;
                    this.mColor = i2;
                } else {
                    long j = this.mStartTime;
                    float fMax = Math.max(0.0f, Math.min(Interpolators.LINEAR.getInterpolation((r3 - j) / (r5 - j)), 1.0f));
                    float f = 1.0f - fMax;
                    this.mColor = Color.argb((int) ((Color.alpha(i2) * fMax) + (Color.alpha(this.mColorStart) * f)), (int) ((Color.red(i2) * fMax) + (Color.red(this.mColorStart) * f)), (int) ((Color.green(i2) * fMax) + (Color.green(this.mColorStart) * f)), (int) ((fMax * Color.blue(i2)) + (Color.blue(this.mColorStart) * f)));
                }
            } else {
                this.mColor = i2;
            }
            if (Color.alpha(this.mColor) > 0) {
                this.mPaint.setColor(this.mColor);
                PorterDuffColorFilter porterDuffColorFilter = this.mTintFilter;
                if (porterDuffColorFilter != null) {
                    this.mPaint.setColorFilter(porterDuffColorFilter);
                }
                Rect rect = this.mFrame;
                if (rect != null) {
                    canvas.drawRect(rect, this.mPaint);
                } else {
                    canvas.drawPaint(this.mPaint);
                }
                if (this.mDivHeight > 0) {
                    this.mPaint.setColor(this.mDivColor);
                    canvas.drawRect(new Rect(0, 0, canvas.getWidth(), this.mDivHeight), this.mPaint);
                }
            }
            if (this.mAnimating) {
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        protected void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setTint(int i) {
            PorterDuffColorFilter porterDuffColorFilter = this.mTintFilter;
            PorterDuff.Mode mode = porterDuffColorFilter == null ? PorterDuff.Mode.SRC_IN : porterDuffColorFilter.getMode();
            PorterDuffColorFilter porterDuffColorFilter2 = this.mTintFilter;
            if (porterDuffColorFilter2 == null || porterDuffColorFilter2.getColor() != i) {
                this.mTintFilter = new PorterDuffColorFilter(i, mode);
            }
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintMode(PorterDuff.Mode mode) {
            PorterDuffColorFilter porterDuffColorFilter = this.mTintFilter;
            int color = porterDuffColorFilter == null ? 0 : porterDuffColorFilter.getColor();
            PorterDuffColorFilter porterDuffColorFilter2 = this.mTintFilter;
            if (porterDuffColorFilter2 == null || porterDuffColorFilter2.getMode() != mode) {
                this.mTintFilter = new PorterDuffColorFilter(color, mode);
            }
            invalidateSelf();
        }
    }

    public class DesktopStatusBarIconInfo {
        private DynamicSysIconView mIconView;
        private final String mKey;
        private StatusBarIcon mStatusBarIcon;
        private PendingIntent mSwitchIntent;

        public DesktopStatusBarIconInfo(String str, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
            this.mKey = str;
            this.mStatusBarIcon = statusBarIcon;
            this.mSwitchIntent = pendingIntent;
            DynamicSysIconView dynamicSysIconView = (DynamicSysIconView) LayoutInflater.from(TaskBarView.this.getContext()).inflate(R$layout.dynamic_sys_icon, (ViewGroup) null);
            this.mIconView = dynamicSysIconView;
            dynamicSysIconView.setStatusBarIcon(statusBarIcon, pendingIntent);
            this.mIconView.setDarkIntensity(TaskBarView.this.mDarkIntensity);
            this.mIconView.setTag(R$id.dynamic_sys_icon_key, str);
            if ("key_ime".equals(str)) {
                this.mIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$DesktopStatusBarIconInfo$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$new$0(view);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            TaskBarView.this.mTaskBarButtonClickHelper.showIMEDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$update$1(View view) {
            TaskBarView.this.mTaskBarButtonClickHelper.showIMEDialog();
        }

        public DynamicSysIconView getDynamicIconView() {
            return this.mIconView;
        }

        public StatusBarIcon getStatusBarIcon() {
            return this.mStatusBarIcon;
        }

        public PendingIntent getSwitchIntent() {
            return this.mSwitchIntent;
        }

        public void update(StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
            this.mStatusBarIcon = statusBarIcon;
            this.mSwitchIntent = pendingIntent;
            this.mIconView.setStatusBarIcon(statusBarIcon, pendingIntent);
            this.mIconView.setDarkIntensity(TaskBarView.this.mDarkIntensity);
            if ("key_ime".equals(this.mKey)) {
                this.mIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$DesktopStatusBarIconInfo$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$update$1(view);
                    }
                });
            }
        }
    }

    class DevicePolicyReceiver extends BroadcastReceiver {
        private DevicePolicyReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction())) {
                TaskBarView.this.updateAudioIcon();
            }
        }

        public void registerReceiver(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
            context.registerReceiver(this, intentFilter, 2);
        }

        public void unregisterReceiver(Context context) {
            context.unregisterReceiver(this);
        }
    }

    public TaskBarView(Context context) {
        super(context);
        this.mDesktopStatusBarIconInfoMap = new HashMap();
        this.mDarkIntensity = 1.0f;
        this.mUiHandler = new Handler();
        this.mIsRecentsViewExpanded = false;
        this.mIsRecentsViewCollapseExpanding = false;
        this.mTaskbarCenterContainOriginWidth = 0;
        this.mTaskbarRightContainOriginWidth = 0;
        this.mAudioIconShow = true;
        this.mDevicePolicyReceiver = new DevicePolicyReceiver();
        this.mRestartBlackPackageList = new HashSet() { // from class: com.motorola.taskbar.bar.TaskBarView.1
            {
                add("com.motorola.launcher3");
                add("com.motorola.systemui.desk");
                add("com.motorola.mobiledesktop");
                add("com.android.intentresolver");
            }
        };
        this.mContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                TaskBarView.this.updateMirrorPhoneButtonVisible();
            }
        };
        this.mAudioContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC.equals(uri)) {
                    TaskBarView.this.toastAudioTip();
                }
            }
        };
        this.mOnRecentsStatusListener = new TaskBarRecentsView.OnRecentsStatusListener() { // from class: com.motorola.taskbar.bar.TaskBarView.7
            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onItemsChanged() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                } else {
                    TaskBarView.this.updateExpandCollapseAppsButton();
                }
            }

            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onScrollIdle() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                }
            }
        };
    }

    public TaskBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDesktopStatusBarIconInfoMap = new HashMap();
        this.mDarkIntensity = 1.0f;
        this.mUiHandler = new Handler();
        this.mIsRecentsViewExpanded = false;
        this.mIsRecentsViewCollapseExpanding = false;
        this.mTaskbarCenterContainOriginWidth = 0;
        this.mTaskbarRightContainOriginWidth = 0;
        this.mAudioIconShow = true;
        this.mDevicePolicyReceiver = new DevicePolicyReceiver();
        this.mRestartBlackPackageList = new HashSet() { // from class: com.motorola.taskbar.bar.TaskBarView.1
            {
                add("com.motorola.launcher3");
                add("com.motorola.systemui.desk");
                add("com.motorola.mobiledesktop");
                add("com.android.intentresolver");
            }
        };
        this.mContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                TaskBarView.this.updateMirrorPhoneButtonVisible();
            }
        };
        this.mAudioContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC.equals(uri)) {
                    TaskBarView.this.toastAudioTip();
                }
            }
        };
        this.mOnRecentsStatusListener = new TaskBarRecentsView.OnRecentsStatusListener() { // from class: com.motorola.taskbar.bar.TaskBarView.7
            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onItemsChanged() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                } else {
                    TaskBarView.this.updateExpandCollapseAppsButton();
                }
            }

            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onScrollIdle() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                }
            }
        };
    }

    public TaskBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDesktopStatusBarIconInfoMap = new HashMap();
        this.mDarkIntensity = 1.0f;
        this.mUiHandler = new Handler();
        this.mIsRecentsViewExpanded = false;
        this.mIsRecentsViewCollapseExpanding = false;
        this.mTaskbarCenterContainOriginWidth = 0;
        this.mTaskbarRightContainOriginWidth = 0;
        this.mAudioIconShow = true;
        this.mDevicePolicyReceiver = new DevicePolicyReceiver();
        this.mRestartBlackPackageList = new HashSet() { // from class: com.motorola.taskbar.bar.TaskBarView.1
            {
                add("com.motorola.launcher3");
                add("com.motorola.systemui.desk");
                add("com.motorola.mobiledesktop");
                add("com.android.intentresolver");
            }
        };
        this.mContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                TaskBarView.this.updateMirrorPhoneButtonVisible();
            }
        };
        this.mAudioContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC.equals(uri)) {
                    TaskBarView.this.toastAudioTip();
                }
            }
        };
        this.mOnRecentsStatusListener = new TaskBarRecentsView.OnRecentsStatusListener() { // from class: com.motorola.taskbar.bar.TaskBarView.7
            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onItemsChanged() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                } else {
                    TaskBarView.this.updateExpandCollapseAppsButton();
                }
            }

            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onScrollIdle() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                }
            }
        };
    }

    public TaskBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDesktopStatusBarIconInfoMap = new HashMap();
        this.mDarkIntensity = 1.0f;
        this.mUiHandler = new Handler();
        this.mIsRecentsViewExpanded = false;
        this.mIsRecentsViewCollapseExpanding = false;
        this.mTaskbarCenterContainOriginWidth = 0;
        this.mTaskbarRightContainOriginWidth = 0;
        this.mAudioIconShow = true;
        this.mDevicePolicyReceiver = new DevicePolicyReceiver();
        this.mRestartBlackPackageList = new HashSet() { // from class: com.motorola.taskbar.bar.TaskBarView.1
            {
                add("com.motorola.launcher3");
                add("com.motorola.systemui.desk");
                add("com.motorola.mobiledesktop");
                add("com.android.intentresolver");
            }
        };
        this.mContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                TaskBarView.this.updateMirrorPhoneButtonVisible();
            }
        };
        this.mAudioContentObserver = new ContentObserver(this.mUiHandler) { // from class: com.motorola.taskbar.bar.TaskBarView.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC.equals(uri)) {
                    TaskBarView.this.toastAudioTip();
                }
            }
        };
        this.mOnRecentsStatusListener = new TaskBarRecentsView.OnRecentsStatusListener() { // from class: com.motorola.taskbar.bar.TaskBarView.7
            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onItemsChanged() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                } else {
                    TaskBarView.this.updateExpandCollapseAppsButton();
                }
            }

            @Override // com.motorola.taskbar.recent.TaskBarRecentsView.OnRecentsStatusListener
            public void onScrollIdle() {
                if (TaskBarView.this.mIsRecentsViewExpanded) {
                    TaskBarView.this.updateScrollerButton();
                }
            }
        };
    }

    private int getAudioTipDisplayId() {
        Cursor cursorQuery = this.mCurrentUserContext.getContentResolver().query(VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC, null, null, null);
        if (cursorQuery == null) {
            Log.e("TaskBarView", "getAudioTipDisplayId cursor is null");
            return 0;
        }
        try {
            if (cursorQuery.moveToFirst()) {
                return cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("ToastDisplayId"));
            }
            cursorQuery.close();
            Log.e("TaskBarView", "getAudioTipDisplayId cursor is empty, request");
            return 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0;
        } finally {
            cursorQuery.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$10(View view) {
        this.mTaskBarButtonClickHelper.startCalendarActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$11(View view) {
        this.mBatteryMeterView.forceHideTooltip();
        this.mTaskBarButtonClickHelper.startBatteryActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$12(View view) {
        this.mTaskBarButtonClickHelper.startWifiSettingActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$13(View view) {
        this.mTaskBarButtonClickHelper.showIMEDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$14(View view) {
        if (this.mVolumeSysIconView.onClick()) {
            return;
        }
        this.mTaskBarButtonClickHelper.requestSwitchVolumeDialog(this.mVolumeSysIconView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$15(View view) {
        this.mTaskBarButtonClickHelper.requestSwitchNotificationPanel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$16(View view) {
        this.mTaskBarButtonClickHelper.requestSwitchMirrorPanel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$17(View view) {
        this.mTaskBarButtonClickHelper.startSearchActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$18(View view) {
        this.mTaskBarButtonClickHelper.toggleRestartDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$19(View view) {
        this.mTaskBarButtonClickHelper.startWifiSettingActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        this.mTaskBarButtonClickHelper.triggerBackButton(this.mBackButton);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$20(View view) {
        this.mTaskBarButtonClickHelper.rotateScreen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$21(View view) {
        requestUpdateAppsView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        this.mTaskBarButtonClickHelper.handleAirRemoteHome();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        this.mTaskBarButtonClickHelper.handleAirRemoteMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        this.mTaskBarButtonClickHelper.startSettingsActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(View view) {
        this.mTaskBarButtonClickHelper.lockDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7(View view) {
        this.mTaskBarButtonClickHelper.toggleRecents();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(View view) {
        this.mTaskBarButtonClickHelper.showScreenshotTools(this.mScreenshotButton);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9(View view) {
        this.mTaskBarButtonClickHelper.startClockActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLayoutByDirection$0(boolean z, View view) {
        requestScrollAppList(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLayoutByDirection$1(boolean z, View view) {
        requestScrollAppList(z);
    }

    private boolean needRequestHidePanel(int i, int i2) {
        KeyButtonView keyButtonView;
        NotificationSysIconView notificationSysIconView = this.mNotificationSysIconView;
        if (notificationSysIconView == null) {
            return false;
        }
        int[] iArr = new int[2];
        notificationSysIconView.getLocationInWindow(iArr);
        int i3 = iArr[0];
        Rect rect = new Rect(i3, iArr[1], this.mNotificationSysIconView.getWidth() + i3, iArr[1] + this.mNotificationSysIconView.getHeight());
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        int[] iArr2 = new int[2];
        DesktopStatusBarIconInfo desktopStatusBarIconInfo = (DesktopStatusBarIconInfo) this.mDesktopStatusBarIconInfoMap.get("key_ime");
        if (desktopStatusBarIconInfo != null && desktopStatusBarIconInfo.mIconView != null && desktopStatusBarIconInfo.mIconView.isAttachedToWindow()) {
            DynamicSysIconView dynamicSysIconView = desktopStatusBarIconInfo.mIconView;
            dynamicSysIconView.getLocationInWindow(iArr2);
            int i4 = iArr2[0];
            rect2.set(i4, iArr2[1], dynamicSysIconView.getWidth() + i4, iArr2[1] + dynamicSysIconView.getHeight());
        }
        if (rect2.isEmpty() && (keyButtonView = this.mIMESysIconView) != null && keyButtonView.isAttachedToWindow()) {
            this.mIMESysIconView.getLocationInWindow(iArr2);
            int i5 = iArr2[0];
            rect2.set(i5, iArr2[1], this.mIMESysIconView.getWidth() + i5, iArr2[1] + this.mIMESysIconView.getHeight());
        }
        KeyButtonView keyButtonView2 = this.mScreenshotButton;
        if (keyButtonView2 != null && keyButtonView2.isAttachedToWindow()) {
            this.mScreenshotButton.getLocationInWindow(iArr2);
            int i6 = iArr2[0];
            rect3.set(i6, iArr2[1], this.mScreenshotButton.getWidth() + i6, iArr2[1] + this.mScreenshotButton.getHeight());
        }
        return (rect.contains(i, i2) || rect2.contains(i, i2) || rect3.contains(i, i2)) ? false : true;
    }

    private void requestScrollAppList(boolean z) {
        if (this.mTaskBarRecentsView == null) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.taskbar_appitem_width);
        TaskBarRecentsView taskBarRecentsView = this.mTaskBarRecentsView;
        if (!z) {
            dimensionPixelSize = 0 - dimensionPixelSize;
        }
        taskBarRecentsView.scrollBy(dimensionPixelSize, 0);
    }

    private void requestUpdateAppsView() {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        if (this.mIsRecentsViewCollapseExpanding || (viewGroup = this.mTaskbarRightContain) == null || (viewGroup2 = this.mTaskbarCenterContain) == null) {
            return;
        }
        boolean z = this.mIsRecentsViewExpanded;
        this.mIsRecentsViewExpanded = !z;
        this.mIsRecentsViewCollapseExpanding = true;
        if (z) {
            this.mTaskbarCenterContainOriginWidth = viewGroup2.getWidth();
            Animation animation = new Animation() { // from class: com.motorola.taskbar.bar.TaskBarView.6
                @Override // android.view.animation.Animation
                protected void applyTransformation(float f, Transformation transformation) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) TaskBarView.this.mTaskbarCenterContain.getLayoutParams();
                    if (f == 1.0f) {
                        layoutParams.width = 0;
                        layoutParams.weight = 1.0f;
                        TaskBarView.this.mTaskbarRightContain.setVisibility(0);
                        TaskBarView.this.mIsRecentsViewCollapseExpanding = false;
                        TaskBarView.this.updateExpandCollapseAppsButton();
                        TaskBarView.this.updateScrollerButton();
                    } else {
                        layoutParams.width = TaskBarView.this.mTaskbarCenterContainOriginWidth - ((int) (TaskBarView.this.mTaskbarRightContainOriginWidth * f));
                        layoutParams.weight = 0.0f;
                    }
                    TaskBarView.this.mTaskbarCenterContain.setLayoutParams(layoutParams);
                    TaskBarView.this.mTaskbarCenterContain.requestLayout();
                }

                @Override // android.view.animation.Animation
                public boolean willChangeBounds() {
                    return true;
                }
            };
            animation.setDuration(200L);
            this.mTaskbarCenterContain.startAnimation(animation);
            return;
        }
        this.mTaskbarRightContainOriginWidth = viewGroup.getWidth();
        this.mTaskbarCenterContainOriginWidth = this.mTaskbarCenterContain.getWidth();
        Animation animation2 = new Animation() { // from class: com.motorola.taskbar.bar.TaskBarView.5
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 0.0f) {
                    TaskBarView.this.mTaskbarRightContain.setVisibility(8);
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) TaskBarView.this.mTaskbarCenterContain.getLayoutParams();
                layoutParams.width = ((int) (TaskBarView.this.mTaskbarRightContainOriginWidth * f)) + TaskBarView.this.mTaskbarCenterContainOriginWidth;
                layoutParams.weight = 0.0f;
                TaskBarView.this.mTaskbarCenterContain.setLayoutParams(layoutParams);
                if (f == 1.0f) {
                    TaskBarView.this.mIsRecentsViewCollapseExpanding = false;
                    TaskBarView.this.updateExpandCollapseAppsButton();
                    TaskBarView.this.updateScrollerButton();
                }
                TaskBarView.this.mTaskbarCenterContain.requestLayout();
            }

            @Override // android.view.animation.Animation
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation2.setDuration(200L);
        this.mTaskbarCenterContain.startAnimation(animation2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toastAudioTip() {
        int audioTipDisplayId = getAudioTipDisplayId();
        if (DEBUG.booleanValue()) {
            Log.d("TaskBarView", "toastAudioTip, targetDisplayId: " + audioTipDisplayId + " mCurrent DisplayId :" + ((FrameLayout) this).mContext.getDisplayId());
        }
        if (audioTipDisplayId == ((FrameLayout) this).mContext.getDisplayId()) {
            Toast.makeText(((FrameLayout) this).mContext, R$string.volume_pc_enabled_tip, 0).show();
        }
    }

    private void touchAutoHide() {
        this.mTaskBarServiceProxy.touchAutoHide(this.mDisplayId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAudioIcon() {
        Bundle userRestrictions = ((UserManager) getContext().getSystemService("user")).getUserRestrictions();
        boolean z = userRestrictions != null ? true ^ userRestrictions.getBoolean("no_adjust_volume") : true;
        if (DEBUG.booleanValue()) {
            Log.d("TaskBarView", "updateAudioIcon, allowAdjustVolume: " + z);
        }
        if (z != this.mAudioIconShow) {
            this.mAudioIconShow = z;
            onAudioStatusChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateExpandCollapseAppsButton() {
        updateExpandCollapseAppsButton(getLayoutDirection() == 1);
    }

    private void updateExpandCollapseAppsButton(boolean z) {
        KeyButtonView keyButtonView = this.mCollapseExpandAppsButton;
        if (keyButtonView == null) {
            return;
        }
        if (this.mIsRecentsViewExpanded) {
            keyButtonView.setVisibility(0);
            this.mCollapseExpandAppsButton.setImageDrawable(getDrawable(z ? R$drawable.ic_taskbar_expand : R$drawable.ic_taskbar_collapse));
            this.mCollapseExpandAppsButton.setContentDescription(getContext().getString(R$string.collapse));
        } else {
            if (!this.mTaskBarRecentsView.canScrollHorizontally()) {
                this.mCollapseExpandAppsButton.setVisibility(8);
                return;
            }
            this.mCollapseExpandAppsButton.setVisibility(0);
            this.mCollapseExpandAppsButton.setImageDrawable(getDrawable(z ? R$drawable.ic_taskbar_collapse : R$drawable.ic_taskbar_expand));
            this.mCollapseExpandAppsButton.setContentDescription(getContext().getString(R$string.expand));
        }
    }

    private void updateLayoutByDirection() {
        final boolean z = getLayoutDirection() == 0;
        KeyButtonView keyButtonView = this.mAppsScrollBack;
        if (keyButtonView != null) {
            keyButtonView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateLayoutByDirection$0(z, view);
                }
            });
        }
        KeyButtonView keyButtonView2 = this.mAppsScrollForward;
        if (keyButtonView2 != null) {
            keyButtonView2.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateLayoutByDirection$1(z, view);
                }
            });
        }
        onLayoutDirectionChanged(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScrollerButton() {
        KeyButtonView keyButtonView;
        TaskBarRecentsView taskBarRecentsView = this.mTaskBarRecentsView;
        if (taskBarRecentsView == null || (keyButtonView = this.mAppsScrollBack) == null || this.mAppsScrollForward == null) {
            return;
        }
        if (!this.mIsRecentsViewExpanded) {
            keyButtonView.setVisibility(8);
            this.mAppsScrollForward.setVisibility(8);
        } else {
            boolean zCanScrollHorizontally = taskBarRecentsView.canScrollHorizontally(false);
            boolean zCanScrollHorizontally2 = this.mTaskBarRecentsView.canScrollHorizontally(true);
            this.mAppsScrollBack.setVisibility(zCanScrollHorizontally ? 0 : 8);
            this.mAppsScrollForward.setVisibility(zCanScrollHorizontally2 ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserContext(int i) {
        this.mCurrentUserContext = getContext().createContextAsUser(UserHandle.of(i), 0);
    }

    public void addDesktopIcon(String str, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
        if (this.mDynamicSysiconsContainer == null) {
            return;
        }
        DesktopStatusBarIconInfo desktopStatusBarIconInfo = (DesktopStatusBarIconInfo) this.mDesktopStatusBarIconInfoMap.get(str);
        if (desktopStatusBarIconInfo != null) {
            desktopStatusBarIconInfo.update(statusBarIcon, pendingIntent);
            return;
        }
        DesktopStatusBarIconInfo desktopStatusBarIconInfo2 = new DesktopStatusBarIconInfo(str, statusBarIcon, pendingIntent);
        this.mDesktopStatusBarIconInfoMap.put(str, desktopStatusBarIconInfo2);
        this.mDynamicSysiconsContainer.addView(desktopStatusBarIconInfo2.mIconView, 0, new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R$dimen.sys_icon_width), -1));
    }

    protected boolean canShowMirrorPhoneButton() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7 || actionMasked == 9) {
            touchAutoHide();
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    public Rect getDesktopIconRect(String str) {
        ViewGroup viewGroup;
        DesktopStatusBarIconInfo desktopStatusBarIconInfo = (DesktopStatusBarIconInfo) this.mDesktopStatusBarIconInfoMap.get(str);
        if (desktopStatusBarIconInfo == null || desktopStatusBarIconInfo.mIconView == null || (viewGroup = this.mDynamicSysiconsContainer) == null) {
            return null;
        }
        int[] iArr = new int[2];
        if (viewGroup.indexOfChild(desktopStatusBarIconInfo.mIconView) != -1 && desktopStatusBarIconInfo.mIconView.getVisibility() == 0) {
            desktopStatusBarIconInfo.mIconView.getLocationOnScreen(iArr);
            int i = iArr[0];
            return new Rect(i, iArr[1], desktopStatusBarIconInfo.mIconView.getWidth() + i, iArr[1] + desktopStatusBarIconInfo.mIconView.getHeight());
        }
        getLocationOnScreen(iArr);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.sys_icon_width);
        int width = iArr[0] + (getWidth() / 2);
        int i2 = dimensionPixelSize / 2;
        int i3 = iArr[1];
        return new Rect(width - i2, i3, width + i2, getHeight() + i3);
    }

    protected KeyButtonDrawable getDrawable(int i) {
        return KeyButtonDrawable.create(((FrameLayout) this).mContext, i, false);
    }

    protected void initView() {
        boolean zIsMobileUiOrAppStreamDisplay = ((MotoFeature) Dependency.get(MotoFeature.class)).isMobileUiOrAppStreamDisplay(getContext());
        this.mTaskBarServiceProxy = (TaskBarServiceProxy) Dependency.get(TaskBarServiceProxy.class);
        this.mQSNotificationPanelController = (QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class);
        this.mMirrorPhonePanelController = (MirrorPhonePanelController) Dependency.get(MirrorPhonePanelController.class);
        this.mDisplayId = ((FrameLayout) this).mContext.getDisplayId();
        this.mLayoutInflater = LayoutInflater.from(((FrameLayout) this).mContext);
        KeyButtonView keyButtonView = (KeyButtonView) findViewById(R$id.back);
        this.mBackButton = keyButtonView;
        if (keyButtonView != null) {
            keyButtonView.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_back : R$drawable.ic_taskbar_back_24px));
            this.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$2(view);
                }
            });
        }
        KeyButtonView keyButtonView2 = (KeyButtonView) findViewById(R$id.home);
        this.mHomeButton = keyButtonView2;
        if (keyButtonView2 != null) {
            keyButtonView2.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_home : R$drawable.ic_home_24px));
            this.mHomeButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda13
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$3(view);
                }
            });
        }
        KeyButtonView keyButtonView3 = (KeyButtonView) findViewById(R$id.app_tray);
        this.mAppTrayButton = keyButtonView3;
        if (keyButtonView3 != null) {
            keyButtonView3.setImageDrawable(getDrawable(R$drawable.ic_apps_24px));
            this.mAppTrayButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda14
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$4(view);
                }
            });
        }
        DesktopSettingsIconView desktopSettingsIconView = (DesktopSettingsIconView) findViewById(R$id.settings);
        this.mSettingsButton = desktopSettingsIconView;
        if (desktopSettingsIconView != null) {
            desktopSettingsIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda15
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$5(view);
                }
            });
        }
        TaskBarRecentsView taskBarRecentsView = (TaskBarRecentsView) findViewById(R$id.taskbar_recent);
        this.mTaskBarRecentsView = taskBarRecentsView;
        if (taskBarRecentsView != null) {
            taskBarRecentsView.setOnRecentsStatusListener(this.mOnRecentsStatusListener);
        }
        KeyButtonView keyButtonView4 = (KeyButtonView) findViewById(R$id.lock);
        this.mLockButton = keyButtonView4;
        if (keyButtonView4 != null) {
            keyButtonView4.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_lock : R$drawable.ic_lock_24px));
            this.mLockButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda16
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$6(view);
                }
            });
        }
        KeyButtonView keyButtonView5 = (KeyButtonView) findViewById(R$id.recents);
        this.mRecentsButton = keyButtonView5;
        if (keyButtonView5 != null) {
            keyButtonView5.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_recents : R$drawable.ic_recents_24px));
            this.mRecentsButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda17
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$7(view);
                }
            });
        }
        KeyButtonView keyButtonView6 = (KeyButtonView) findViewById(R$id.screenshot);
        this.mScreenshotButton = keyButtonView6;
        if (keyButtonView6 != null) {
            keyButtonView6.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.mobileui_screenshot : R$drawable.ic_screenshot_24px));
            this.mScreenshotButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda18
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$8(view);
                }
            });
        }
        this.mBarBackground = new BarBackgroundDrawable(getContext());
        Clock clock = (Clock) findViewById(R$id.clock);
        this.mClockView = clock;
        if (clock != null) {
            clock.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda19
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$9(view);
                }
            });
        }
        this.mClockDateDiv = findViewById(R$id.clock_date_div);
        DateView dateView = (DateView) findViewById(R$id.date);
        this.mDateView = dateView;
        if (dateView != null) {
            dateView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$10(view);
                }
            });
        }
        BatteryMeterView batteryMeterView = (BatteryMeterView) findViewById(R$id.battery);
        this.mBatteryMeterView = batteryMeterView;
        if (batteryMeterView != null) {
            batteryMeterView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda21
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$11(view);
                }
            });
        }
        WifiSysIconView wifiSysIconView = (WifiSysIconView) findViewById(R$id.wifi);
        this.mWifiSysIconView = wifiSysIconView;
        if (wifiSysIconView != null) {
            wifiSysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$12(view);
                }
            });
        }
        KeyButtonView keyButtonView7 = (KeyButtonView) findViewById(R$id.ime);
        this.mIMESysIconView = keyButtonView7;
        if (keyButtonView7 != null) {
            keyButtonView7.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_keyboard : R$drawable.ic_keyboard_24px));
            this.mIMESysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$13(view);
                }
            });
        }
        VolumeSysIconView volumeSysIconView = (VolumeSysIconView) findViewById(R$id.volume);
        this.mVolumeSysIconView = volumeSysIconView;
        if (volumeSysIconView != null) {
            volumeSysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$14(view);
                }
            });
        }
        NotificationSysIconView notificationSysIconView = (NotificationSysIconView) findViewById(R$id.notification);
        this.mNotificationSysIconView = notificationSysIconView;
        if (notificationSysIconView != null) {
            notificationSysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$15(view);
                }
            });
        }
        KeyButtonView keyButtonView8 = (KeyButtonView) findViewById(R$id.mirror_phone);
        this.mMirrorPhoneButton = keyButtonView8;
        if (keyButtonView8 != null) {
            keyButtonView8.setImageDrawable(getDrawable(R$drawable.ic_mirror_phone_24px));
            this.mMirrorPhoneButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$16(view);
                }
            });
        }
        KeyButtonView keyButtonView9 = (KeyButtonView) findViewById(R$id.search);
        this.mSearchButton = keyButtonView9;
        if (keyButtonView9 != null) {
            keyButtonView9.setImageDrawable(getDrawable(R$drawable.ic_search_24px));
            this.mSearchButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$17(view);
                }
            });
        }
        KeyButtonView keyButtonView10 = (KeyButtonView) findViewById(R$id.restart);
        this.mRestartButton = keyButtonView10;
        if (keyButtonView10 != null) {
            keyButtonView10.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_restart : R$drawable.ic_restart_24px));
            this.mRestartButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$18(view);
                }
            });
        }
        KeyButtonView keyButtonView11 = (KeyButtonView) findViewById(R$id.ethernet);
        this.mEthernetButton = keyButtonView11;
        if (keyButtonView11 != null) {
            keyButtonView11.setImageDrawable(getDrawable(R$drawable.ic_ethernet));
            this.mEthernetButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$19(view);
                }
            });
        }
        KeyButtonView keyButtonView12 = (KeyButtonView) findViewById(R$id.rotate);
        this.mRotationButton = keyButtonView12;
        if (keyButtonView12 != null) {
            keyButtonView12.setImageDrawable(getDrawable(R$drawable.ic_mobileui_rotation));
            this.mRotationButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$20(view);
                }
            });
        }
        this.mTaskbarLeftContain = (ViewGroup) findViewById(R$id.taskbar_left);
        this.mTaskbarCenterContain = (ViewGroup) findViewById(R$id.taskbar_center);
        this.mTaskbarRightContain = (ViewGroup) findViewById(R$id.taskbar_right);
        KeyButtonView keyButtonView13 = (KeyButtonView) findViewById(R$id.taskbar_apps_expand_collapse);
        this.mCollapseExpandAppsButton = keyButtonView13;
        if (keyButtonView13 != null) {
            keyButtonView13.setImageDrawable(getDrawable(R$drawable.ic_taskbar_collapse));
            this.mCollapseExpandAppsButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarView$$ExternalSyntheticLambda12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initView$21(view);
                }
            });
        }
        this.mAppsScrollBack = (KeyButtonView) findViewById(R$id.taskbar_apps_scroll_back);
        this.mAppsScrollForward = (KeyButtonView) findViewById(R$id.taskbar_apps_scroll_forward);
        KeyButtonView keyButtonView14 = (KeyButtonView) findViewById(R$id.more);
        this.mMoreButton = keyButtonView14;
        if (keyButtonView14 != null) {
            keyButtonView14.setImageDrawable(getDrawable(zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_more : R$drawable.ic_mobile_more_24px));
        }
        this.mDynamicSysiconsContainer = (ViewGroup) findViewById(R$id.taskbar_dynamic_sysicons_container);
        this.mImeStatusBarIcon = new StatusBarIcon(getContext().getPackageName(), UserHandle.SYSTEM, R$drawable.ic_keyboard_24px, 0, 0, getResources().getString(R$string.ime_decription), StatusBarIcon.Type.SystemIcon);
        this.mTaskBarContentView = (ViewGroup) findViewById(R$id.taskbar_content);
        setIconsDark();
        updateDisplayChooserMode(((TaskBarController) Dependency.get(TaskBarController.class)).getDisplayChooserMode(this.mDisplayId));
    }

    protected boolean isBlackAppRunningTop() {
        String topTaskPackage = AppUtils.getTopTaskPackage(this.mDisplayId);
        if (this.mRestartBlackPackageList.contains(topTaskPackage)) {
            Log.d("TaskBarView", "isBlackAppRunningTop: " + topTaskPackage);
            return true;
        }
        if (getContext().getPackageManager().getLaunchIntentForPackage(topTaskPackage) != null) {
            return false;
        }
        Log.d("TaskBarView", "No launcher intent " + topTaskPackage);
        return true;
    }

    protected boolean isImButtonShowing() {
        return ((TaskBarController) Dependency.get(TaskBarController.class)).isImButtonShowing(((FrameLayout) this).mContext.getDisplayId());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setIconsDark();
        updateLayoutByDirection();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        this.mUserTracker.startTracking();
        updateUserContext(this.mUserTracker.getCurrentUserId());
        try {
            this.mCurrentUserContext.getContentResolver().registerContentObserver(VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC, true, this.mAudioContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateMirrorPhoneButtonVisible();
        updateAudioIcon();
        this.mDevicePolicyReceiver.registerReceiver(getContext());
        setTaskBarImeSwitchButtonVisible(isImButtonShowing());
    }

    protected void onAudioStatusChanged() {
        TaskBarButtonClickHelper taskBarButtonClickHelper;
        VolumeSysIconView volumeSysIconView = this.mVolumeSysIconView;
        if (volumeSysIconView != null) {
            volumeSysIconView.setVisibility(this.mAudioIconShow ? 0 : 8);
            if (this.mAudioIconShow || (taskBarButtonClickHelper = this.mTaskBarButtonClickHelper) == null) {
                return;
            }
            taskBarButtonClickHelper.requestHideVolumeDialog();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
        this.mUserTracker.stopTracking();
        this.mDevicePolicyReceiver.unregisterReceiver(getContext());
        this.mCurrentUserContext.getContentResolver().unregisterContentObserver(this.mAudioContentObserver);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        String string = MotorolaSettings.Global.getString(getContext().getContentResolver(), "ready_for_black_list");
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONArray jSONArray = new JSONArray(string);
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.mRestartBlackPackageList.add(jSONArray.getString(i));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        this.mScreenshotController = (ScreenshotController) Dependency.get(ScreenshotController.class);
        BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mTaskBarButtonClickHelper = new TaskBarButtonClickHelper(getContext(), getResources().getDimensionPixelSize(R$dimen.task_bar_tooltip_app_offset_y));
        this.mUserTracker = new CurrentUserTracker(broadcastDispatcher) { // from class: com.motorola.taskbar.bar.TaskBarView.2
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i2) {
                boolean zIsAttachedToWindow = TaskBarView.this.isAttachedToWindow();
                if (zIsAttachedToWindow) {
                    TaskBarView.this.mCurrentUserContext.getContentResolver().unregisterContentObserver(TaskBarView.this.mAudioContentObserver);
                }
                TaskBarView.this.updateUserContext(i2);
                if (zIsAttachedToWindow) {
                    TaskBarView.this.mCurrentUserContext.getContentResolver().registerContentObserver(VolumePanelLayout.CONTENT_URI_SHOW_AUDIO_ICON, true, TaskBarView.this.mAudioContentObserver);
                    TaskBarView.this.mCurrentUserContext.getContentResolver().registerContentObserver(VolumePanelLayout.CONTENT_URI_TOAST_USE_CLIENT_MIC, true, TaskBarView.this.mAudioContentObserver);
                    TaskBarView.this.updateMirrorPhoneButtonVisible();
                }
            }
        };
        initView();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            if (needRequestHidePanel((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.mQSNotificationPanelController.requestHideNotificationPanel(this.mDisplayId);
            }
            TaskBarButtonClickHelper taskBarButtonClickHelper = this.mTaskBarButtonClickHelper;
            if (taskBarButtonClickHelper != null) {
                taskBarButtonClickHelper.hideRestartDialog();
            }
        }
        touchAutoHide();
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onLayoutDirectionChanged(boolean z) {
        KeyButtonView keyButtonView;
        if (this.mAppsScrollForward == null || (keyButtonView = this.mAppsScrollBack) == null) {
            return;
        }
        if (z) {
            keyButtonView.setImageDrawable(getDrawable(R$drawable.ic_icon_rightward));
            this.mAppsScrollForward.setImageDrawable(getDrawable(R$drawable.ic_icon_leftward));
        } else {
            keyButtonView.setImageDrawable(getDrawable(R$drawable.ic_icon_leftward));
            this.mAppsScrollForward.setImageDrawable(getDrawable(R$drawable.ic_icon_rightward));
        }
        updateExpandCollapseAppsButton(z);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        BarBackgroundDrawable barBackgroundDrawable = new BarBackgroundDrawable(((FrameLayout) this).mContext);
        barBackgroundDrawable.mMode = this.mBarBackground.mMode;
        this.mBarBackground = barBackgroundDrawable;
        setIconsDark();
        this.mTaskBarButtonClickHelper.requestHideVolumeDialog();
    }

    public void removeAllDesktopIcon() {
        ViewGroup viewGroup = this.mDynamicSysiconsContainer;
        if (viewGroup == null) {
            return;
        }
        viewGroup.removeAllViews();
        this.mDesktopStatusBarIconInfoMap.clear();
    }

    public void removeDesktopIcon(String str) {
        DesktopStatusBarIconInfo desktopStatusBarIconInfo = (DesktopStatusBarIconInfo) this.mDesktopStatusBarIconInfoMap.remove(str);
        if (desktopStatusBarIconInfo != null) {
            this.mDynamicSysiconsContainer.removeView(desktopStatusBarIconInfo.mIconView);
        }
    }

    public void requestHideVolumeDialog() {
        this.mTaskBarButtonClickHelper.requestHideVolumeDialog();
    }

    public void setContentVisibility(int i) {
        ViewGroup viewGroup = this.mTaskBarContentView;
        if (viewGroup != null) {
            viewGroup.setVisibility(i);
            return;
        }
        Log.w("TaskBarView", "setContentVisibility with null mTaskBarContentView: " + i);
    }

    public void setIconsDark() {
        ViewGroup viewGroup = this.mTaskBarContentView;
        if (viewGroup != null) {
            viewGroup.setBackground(getContext().getDrawable(R$drawable.taskbar_bg));
        }
        float f = ((FrameLayout) this).mContext.getResources().getFloat(R$dimen.taskbar_icon_dark_intensity);
        this.mDarkIntensity = f;
        KeyButtonView keyButtonView = this.mBackButton;
        if (keyButtonView != null) {
            keyButtonView.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView2 = this.mHomeButton;
        if (keyButtonView2 != null) {
            keyButtonView2.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView3 = this.mAppTrayButton;
        if (keyButtonView3 != null) {
            keyButtonView3.setDarkIntensity(f);
        }
        DesktopSettingsIconView desktopSettingsIconView = this.mSettingsButton;
        if (desktopSettingsIconView != null) {
            desktopSettingsIconView.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView4 = this.mScreenshotButton;
        if (keyButtonView4 != null) {
            keyButtonView4.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView5 = this.mSearchButton;
        if (keyButtonView5 != null) {
            keyButtonView5.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView6 = this.mRecentsButton;
        if (keyButtonView6 != null) {
            keyButtonView6.setDarkIntensity(f);
        }
        TaskBarRecentsView taskBarRecentsView = this.mTaskBarRecentsView;
        if (taskBarRecentsView != null) {
            taskBarRecentsView.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView7 = this.mRestartButton;
        if (keyButtonView7 != null) {
            keyButtonView7.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView8 = this.mLockButton;
        if (keyButtonView8 != null) {
            keyButtonView8.setDarkIntensity(f);
        }
        Clock clock = this.mClockView;
        if (clock != null) {
            clock.setDarkIntensity(f);
        }
        View view = this.mClockDateDiv;
        if (view != null) {
            view.setBackground(getResources().getDrawable(R$drawable.dot_4dp));
        }
        DateView dateView = this.mDateView;
        if (dateView != null) {
            dateView.setDarkIntensity(f);
        }
        BatteryMeterView batteryMeterView = this.mBatteryMeterView;
        if (batteryMeterView != null) {
            batteryMeterView.setDarkIntensity(f);
        }
        WifiSysIconView wifiSysIconView = this.mWifiSysIconView;
        if (wifiSysIconView != null) {
            wifiSysIconView.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView9 = this.mIMESysIconView;
        if (keyButtonView9 != null) {
            keyButtonView9.setDarkIntensity(f);
        }
        VolumeSysIconView volumeSysIconView = this.mVolumeSysIconView;
        if (volumeSysIconView != null) {
            volumeSysIconView.setDarkIntensity(f);
        }
        NotificationSysIconView notificationSysIconView = this.mNotificationSysIconView;
        if (notificationSysIconView != null) {
            notificationSysIconView.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView10 = this.mMirrorPhoneButton;
        if (keyButtonView10 != null) {
            keyButtonView10.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView11 = this.mSearchButton;
        if (keyButtonView11 != null) {
            keyButtonView11.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView12 = this.mCollapseExpandAppsButton;
        if (keyButtonView12 != null) {
            keyButtonView12.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView13 = this.mAppsScrollBack;
        if (keyButtonView13 != null) {
            keyButtonView13.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView14 = this.mAppsScrollForward;
        if (keyButtonView14 != null) {
            keyButtonView14.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView15 = this.mMoreButton;
        if (keyButtonView15 != null) {
            keyButtonView15.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView16 = this.mEthernetButton;
        if (keyButtonView16 != null) {
            keyButtonView16.setDarkIntensity(f);
        }
        KeyButtonView keyButtonView17 = this.mRotationButton;
        if (keyButtonView17 != null) {
            keyButtonView17.setDarkIntensity(f);
        }
        Iterator it = this.mDesktopStatusBarIconInfoMap.values().iterator();
        while (it.hasNext()) {
            ((DesktopStatusBarIconInfo) it.next()).mIconView.setDarkIntensity(f);
        }
    }

    public void setTaskBarImeSwitchButtonVisible(boolean z) {
        if (z) {
            addDesktopIcon("key_ime", this.mImeStatusBarIcon, null);
        } else {
            removeDesktopIcon("key_ime");
        }
    }

    public void setTaskBarTransitionMode(int i) {
        this.mBarBackground.applyModeBackground(i, false);
    }

    public void toggleAppTray() {
        this.mTaskBarButtonClickHelper.toggleAppTray();
    }

    public void toggleShortcutHelper() {
        this.mTaskBarButtonClickHelper.toggleShortcutHelper();
    }

    public void triggerHomeButton() {
        this.mTaskBarButtonClickHelper.triggerHomeButton(this.mHomeButton);
    }

    public void updateDisplayChooserMode(String str) {
        if (this.mAppTrayButton == null || TextUtils.isEmpty(str)) {
            return;
        }
        str.hashCode();
        if (str.equals("mirror") || str.equals("normal")) {
            this.mAppTrayButton.setToolTipText(getContext().getString(R$string.app_tray_description));
        } else {
            this.mAppTrayButton.setToolTipText(getContext().getString(R$string.sidebar_description));
        }
    }

    protected void updateMirrorPhoneButtonVisible() {
        boolean zCanShowMirrorPhoneButton = canShowMirrorPhoneButton();
        KeyButtonView keyButtonView = this.mMirrorPhoneButton;
        if (keyButtonView != null) {
            keyButtonView.setVisibility(zCanShowMirrorPhoneButton ? 0 : 8);
        }
        if (zCanShowMirrorPhoneButton) {
            return;
        }
        this.mMirrorPhonePanelController.requestHidePanel(this.mDisplayId);
    }
}
