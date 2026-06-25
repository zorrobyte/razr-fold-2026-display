package com.motorola.systemui.desktop.overwrites.statusbar.notification;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.AnimationStateHandler;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.Assert;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$integer;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$style;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopHeadsUpController implements HeadsUpManager, CallbackController, ConfigurationController.ConfigurationListener {
    private static final boolean DEBUG = !Build.IS_USER;
    private final int mAutoDismissNotificationDecay;
    private final ConfigurationController mConfigurationController;
    private ContentObserver mContentObserver;
    private final Context mContext;
    private Context mCurrentUserContext;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final int mDisplayId;
    private final DisplayManager mDisplayManager;
    private int mLayoutDirection;
    private final Handler mMainHandler;
    private final int mMinimumDisplayTime;
    private final NotifBindPipeline mNotifBindPipeline;
    private final NotifInflaterImpl mNotifInflater;
    private UserTracker mUserTracker;
    protected final HashMap mPendingNotifications = new HashMap();
    private final ArrayMap mActiveNotifications = new ArrayMap();
    private final ArrayList mListeners = new ArrayList();
    private final ArrayMap mActiveHeadsupWindows = new ArrayMap();
    private boolean mAttached = false;
    private boolean mIsHeadUpDisable = false;
    private long mAttachedTime = 0;
    private Set mOldNotificationKeys = new HashSet();
    private HardDisplayMode mCurrentHardDisplayMode = new HardDisplayMode();
    private final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.2
        @Override // com.android.systemui.settings.UserTracker.Callback
        public void onUserChanged(int i, Context context) {
            if (DesktopHeadsUpController.this.mAttached) {
                DesktopHeadsUpController.this.mCurrentUserContext.getContentResolver().unregisterContentObserver(DesktopHeadsUpController.this.mContentObserver);
            }
            DesktopHeadsUpController.this.updateUserContext(context);
            if (DesktopHeadsUpController.this.mAttached) {
                DesktopHeadsUpController.this.mCurrentUserContext.getContentResolver().registerContentObserver(MotorolaSettings.System.getUriFor("settings_disable_popup_notification"), true, DesktopHeadsUpController.this.mContentObserver);
            }
        }
    };
    private final NotificationRowContentBinder.InflationCallback mInflationCallback = new NotificationRowContentBinder.InflationCallback() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.3
        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            Log.w("DesktopHeadsUpController", "inflation entry error: " + notificationEntry.getKey() + "; e:" + exc.getMessage());
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            if (DesktopHeadsUpController.this.mAttached) {
                if (DesktopHeadsUpController.DEBUG) {
                    Log.d("DesktopHeadsUpController", "onAsyncInflationFinished: " + notificationEntry.getKey());
                }
                boolean z = DesktopHeadsUpController.this.mPendingNotifications.remove(notificationEntry.getKey()) != null;
                if (notificationEntry.isRowRemoved()) {
                    return;
                }
                boolean z2 = DesktopHeadsUpController.this.getActiveNotificationUnfiltered(notificationEntry.getKey()) == null;
                if (DesktopHeadsUpController.DEBUG) {
                    Log.d("DesktopHeadsUpController", "onAsyncInflationFinished: " + notificationEntry.getKey() + "; isNew: " + z2 + "; pending: " + z);
                }
                if (z || !z2) {
                    if (!z2) {
                        DesktopHeadsUpController.this.updateHeadsUp(notificationEntry);
                        return;
                    } else {
                        DesktopHeadsUpController.this.addActiveNotification(notificationEntry);
                        DesktopHeadsUpController.this.showHeadsUp(notificationEntry);
                        return;
                    }
                }
                Log.d("DesktopHeadsUpController", "onAsyncInflationFinished: " + notificationEntry.getKey() + "; ignore removed entry");
            }
        }
    };
    private ContentObserver mHardDisplayModeObserver = new ContentObserver(0 == true ? 1 : 0) { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.4
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            DesktopHeadsUpController.this.readCurrentHardDisplayMode();
        }
    };

    class HardDisplayMode {
        public int displayId;
        public String mode;

        private HardDisplayMode() {
            this.displayId = -1;
            this.mode = null;
        }
    }

    final class HeadsupWindow {
        private boolean mChildrenUpdateRequested;
        private final WindowManager mDisplyWindowManager;
        private WindowManager.LayoutParams mLayoutParams;
        private NotificationEntry mNotificationEntry;
        private ViewGroup mPanel;
        private boolean mIsShowing = false;
        public boolean remoteInputActive = false;
        private final ViewTreeObserver.OnPreDrawListener mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.HeadsupWindow.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ExpandableNotificationRow row = HeadsupWindow.this.mNotificationEntry.getRow();
                row.setActualHeight(row.getIntrinsicHeight());
                HeadsupWindow.this.mChildrenUpdateRequested = false;
                HeadsupWindow.this.mPanel.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        private final View.OnLayoutChangeListener mPanelOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.HeadsupWindow.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                HeadsupWindow.this.requestChildrenUpdate();
            }
        };
        private final View.OnAttachStateChangeListener mPanelOnAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.HeadsupWindow.3
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                HeadsupWindow.this.mNotificationEntry.getRow().setOnHeightChangedListener(null);
                HeadsupWindow.this.mPanel.removeOnLayoutChangeListener(HeadsupWindow.this.mPanelOnLayoutChangeListener);
                HeadsupWindow.this.mPanel.removeOnAttachStateChangeListener(this);
            }
        };
        private Runnable mRemoveAlertRunnable = new Runnable() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController$HeadsupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1();
            }
        };

        public HeadsupWindow(NotificationEntry notificationEntry, Display display) {
            this.mNotificationEntry = notificationEntry;
            this.mDisplyWindowManager = (WindowManager) DesktopHeadsUpController.this.mContext.createDisplayContext(display).createWindowContext(2038, null).getSystemService("window");
        }

        private void addAutoRemovalCallbacks() {
            addAutoRemovalCallbacks(-1L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAutoRemovalCallbacks(long j) {
            removeAutoRemovalCallbacks();
            if (isSticky()) {
                return;
            }
            if (j <= 0) {
                j = Math.max(DesktopHeadsUpController.this.mAutoDismissNotificationDecay, DesktopHeadsUpController.this.mMinimumDisplayTime);
            }
            DesktopHeadsUpController.this.mMainHandler.postDelayed(this.mRemoveAlertRunnable, j);
        }

        private void addCloseBtn() {
            View expandButton;
            ImageView imageView;
            NotificationViewWrapper visibleNotificationViewWrapper = this.mNotificationEntry.getRow().getVisibleNotificationViewWrapper();
            if (visibleNotificationViewWrapper == null || (expandButton = visibleNotificationViewWrapper.getExpandButton()) == null || (imageView = (ImageView) expandButton.findViewById(R.id.expanded_menu)) == null) {
                return;
            }
            imageView.setImageDrawable(DesktopHeadsUpController.this.mContext.getDrawable(R$drawable.zz_moto_ic_heads_up_close));
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController$HeadsupWindow$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$addCloseBtn$0(view);
                }
            });
        }

        private boolean hasFullScreenIntent(NotificationEntry notificationEntry) {
            return notificationEntry.getSbn().getNotification().fullScreenIntent != null;
        }

        private void hideOtherNotificationContentView() {
            NotificationContentView showingLayout;
            ExpandableNotificationRow row = this.mNotificationEntry.getRow();
            if (row == null || (showingLayout = row.getShowingLayout()) == null) {
                return;
            }
            NotificationContentView privateLayout = row.getPrivateLayout();
            NotificationContentView publicLayout = row.getPublicLayout();
            if (privateLayout == null || publicLayout == null) {
                return;
            }
            privateLayout.setVisibility(showingLayout == privateLayout ? 0 : 4);
            publicLayout.setVisibility(showingLayout == publicLayout ? 0 : 4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isSticky() {
            return this.remoteInputActive || hasFullScreenIntent(this.mNotificationEntry);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addCloseBtn$0(View view) {
            DesktopHeadsUpController.this.removeHeadsUp(this.mNotificationEntry, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            DesktopHeadsUpController.this.removeNotification(this.mNotificationEntry.getKey(), false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeAutoRemovalCallbacks() {
            DesktopHeadsUpController.this.mMainHandler.removeCallbacks(this.mRemoveAlertRunnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestChildrenUpdate() {
            if (this.mChildrenUpdateRequested) {
                return;
            }
            this.mPanel.getViewTreeObserver().addOnPreDrawListener(this.mChildrenUpdater);
            this.mChildrenUpdateRequested = true;
            this.mPanel.invalidate();
            addCloseBtn();
            hideOtherNotificationContentView();
        }

        public void hide(boolean z) {
            if (this.mIsShowing) {
                if (z) {
                    this.mDisplyWindowManager.removeViewImmediate(this.mPanel);
                } else {
                    this.mDisplyWindowManager.removeView(this.mPanel);
                }
                removeAutoRemovalCallbacks();
            }
            this.mIsShowing = false;
        }

        public void show() {
            if (DesktopHeadsUpController.DEBUG) {
                Log.d("DesktopHeadsUpController", "HeadsupWindow show: " + this.mNotificationEntry.getKey());
            }
            if (this.mIsShowing) {
                return;
            }
            final ExpandableNotificationRow row = this.mNotificationEntry.getRow();
            this.mNotificationEntry.setHeadsUp(true);
            row.setSystemExpanded(true);
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            row.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            addCloseBtn();
            hideOtherNotificationContentView();
            int measuredHeight = row.getMeasuredHeight();
            int dimensionPixelSize = DesktopHeadsUpController.this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_heads_up_width);
            Resources resources = DesktopHeadsUpController.this.mContext.getResources();
            int i = R$dimen.notification_headsup_margin;
            this.mLayoutParams = new WindowManager.LayoutParams(dimensionPixelSize, measuredHeight + resources.getDimensionPixelSize(i), 2038, 545521768, -3);
            if (DesktopHeadsUpController.this.mLayoutDirection == 1) {
                WindowManager.LayoutParams layoutParams = this.mLayoutParams;
                layoutParams.gravity = 51;
                layoutParams.windowAnimations = R$style.Animation_TaskbarNotificationPanelRTL;
            } else {
                WindowManager.LayoutParams layoutParams2 = this.mLayoutParams;
                layoutParams2.gravity = 53;
                layoutParams2.windowAnimations = R$style.Animation_TaskbarNotificationPanel;
            }
            WindowManager.LayoutParams layoutParams3 = this.mLayoutParams;
            layoutParams3.privateFlags = 16;
            layoutParams3.setTitle("DesktopHeadsUp: " + DesktopHeadsUpController.this.mDisplayId + "; " + this.mNotificationEntry.getKey());
            this.mPanel = (ViewGroup) LayoutInflater.from(DesktopHeadsUpController.this.mContext).inflate(R$layout.notification_headsup_view, (ViewGroup) null);
            if (MotoFeature.isMobileUiMode(DesktopHeadsUpController.this.mContext)) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                DesktopHeadsUpController.this.mContext.getDisplay().getRealMetrics(displayMetrics);
                int dimensionPixelSize2 = displayMetrics.widthPixels - (DesktopHeadsUpController.this.mContext.getResources().getDimensionPixelSize(i) * 2);
                if (dimensionPixelSize > dimensionPixelSize2) {
                    this.mLayoutParams.width = dimensionPixelSize2;
                    dimensionPixelSize = dimensionPixelSize2;
                }
                this.mPanel.setPaddingRelative(0, DesktopHeadsUpController.this.mContext.getResources().getDimensionPixelSize(i), 0, 0);
                this.mLayoutParams.x = (displayMetrics.widthPixels - dimensionPixelSize) / 2;
            }
            this.mPanel.setOnTouchListener(new View.OnTouchListener() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.HeadsupWindow.4
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 4) {
                        return false;
                    }
                    HeadsupWindow headsupWindow = HeadsupWindow.this;
                    if (!headsupWindow.remoteInputActive || !headsupWindow.mNotificationEntry.rowExists()) {
                        return true;
                    }
                    HeadsupWindow.this.mNotificationEntry.closeRemoteInput();
                    return true;
                }
            });
            ViewParent parent = row.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeAllViews();
            }
            this.mPanel.addView(row, new FrameLayout.LayoutParams(-1, -1));
            row.setOnHeightChangedListener(new ExpandableView.OnHeightChangedListener() { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.HeadsupWindow.5
                @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
                public void onHeightChanged(ExpandableView expandableView, boolean z) {
                    int intrinsicHeight = row.getIntrinsicHeight() + DesktopHeadsUpController.this.mContext.getResources().getDimensionPixelSize(R$dimen.notification_headsup_margin);
                    if (intrinsicHeight == HeadsupWindow.this.mLayoutParams.height) {
                        return;
                    }
                    HeadsupWindow.this.mLayoutParams.height = intrinsicHeight;
                    HeadsupWindow.this.mDisplyWindowManager.updateViewLayout(HeadsupWindow.this.mPanel, HeadsupWindow.this.mLayoutParams);
                    HeadsupWindow.this.requestChildrenUpdate();
                }

                @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
                public void onReset(ExpandableView expandableView) {
                }
            });
            this.mPanel.addOnLayoutChangeListener(this.mPanelOnLayoutChangeListener);
            this.mPanel.addOnAttachStateChangeListener(this.mPanelOnAttachStateChangeListener);
            try {
                this.mDisplyWindowManager.addView(this.mPanel, this.mLayoutParams);
                addAutoRemovalCallbacks();
                this.mIsShowing = true;
            } catch (WindowManager.InvalidDisplayException e) {
                e.printStackTrace();
            }
        }

        public void update(NotificationEntry notificationEntry, boolean z) {
            if (z) {
                removeAutoRemovalCallbacks();
                addAutoRemovalCallbacks();
                show();
            }
        }

        public void updateFocusableFlag() {
            if (this.remoteInputActive) {
                this.mLayoutParams.flags &= -9;
            } else {
                this.mLayoutParams.flags |= 8;
            }
            if (this.mIsShowing) {
                this.mDisplyWindowManager.updateViewLayout(this.mPanel, this.mLayoutParams);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    DesktopHeadsUpController(int i, Context context, Handler handler, DeviceProvisionedController deviceProvisionedController, NotifInflater notifInflater, ConfigurationController configurationController, UserTracker userTracker, NotifBindPipeline notifBindPipeline) {
        this.mLayoutDirection = 0;
        this.mDisplayId = i;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mNotifInflater = (NotifInflaterImpl) notifInflater;
        this.mAutoDismissNotificationDecay = context.getResources().getInteger(R$integer.heads_up_notification_decay);
        this.mMinimumDisplayTime = context.getResources().getInteger(R$integer.heads_up_notification_minimum_time);
        this.mLayoutDirection = context.getResources().getConfiguration().getLayoutDirection();
        this.mConfigurationController = configurationController;
        this.mNotifBindPipeline = notifBindPipeline;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mContentObserver = new ContentObserver(handler) { // from class: com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                DesktopHeadsUpController desktopHeadsUpController = DesktopHeadsUpController.this;
                boolean disablePopupNotification = desktopHeadsUpController.getDisablePopupNotification(desktopHeadsUpController.mCurrentUserContext.getContentResolver());
                if (DesktopHeadsUpController.this.mIsHeadUpDisable != disablePopupNotification) {
                    DesktopHeadsUpController.this.mIsHeadUpDisable = disablePopupNotification;
                    if (DesktopHeadsUpController.this.mIsHeadUpDisable) {
                        DesktopHeadsUpController.this.abortAllExistingInflation();
                        DesktopHeadsUpController.this.removeAllHeadsUpWindow();
                    }
                }
            }
        };
        this.mUserTracker = userTracker;
        Log.d("DesktopHeadsUpController", "DesktopHeadsUpController: " + context.getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortAllExistingInflation() {
        Object[] array = this.mPendingNotifications.keySet().toArray();
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                abortExistingInflation((String) obj, "detach");
            }
        }
        Object[] array2 = this.mActiveNotifications.keySet().toArray();
        if (array2 == null || array2.length <= 0) {
            return;
        }
        for (Object obj2 : array2) {
            abortExistingInflation((String) obj2, "detach");
        }
    }

    private void abortExistingInflation(String str, String str2) {
        if (this.mPendingNotifications.containsKey(str)) {
            NotificationEntry notificationEntry = (NotificationEntry) this.mPendingNotifications.get(str);
            notificationEntry.abortTask();
            this.mPendingNotifications.remove(str);
            ArrayList arrayList = this.mListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((NotifCollectionListener) obj).onEntryCleanUp(notificationEntry);
            }
        }
        NotificationEntry activeNotificationUnfiltered = getActiveNotificationUnfiltered(str);
        if (activeNotificationUnfiltered != null) {
            activeNotificationUnfiltered.abortTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addActiveNotification(NotificationEntry notificationEntry) {
        Assert.isMainThread();
        this.mActiveNotifications.put(notificationEntry.getKey(), notificationEntry);
    }

    private boolean blockByWcc() {
        HardDisplayMode hardDisplayMode = this.mCurrentHardDisplayMode;
        return hardDisplayMode.displayId == this.mDisplayId && "win365".equals(hardDisplayMode.mode);
    }

    private HeadsupWindow createHeadsupWindow(NotificationEntry notificationEntry) {
        Display display = this.mDisplayManager.getDisplay(this.mDisplayId);
        if (display != null) {
            return new HeadsupWindow(notificationEntry, display);
        }
        Log.e("DesktopHeadsUpController", "create HeadsupWindow with expired display: " + this.mDisplayId);
        return null;
    }

    private void deInitCurrentHardDisplayMode() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mHardDisplayModeObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public NotificationEntry getActiveNotificationUnfiltered(String str) {
        return (NotificationEntry) this.mActiveNotifications.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getDisablePopupNotification(ContentResolver contentResolver) {
        return MotorolaSettings.System.getInt(this.mCurrentUserContext.getContentResolver(), "settings_disable_popup_notification") == 1;
    }

    private PackageManager getPackageManagerForUser(Context context, int i) {
        if (i >= 0) {
            try {
                context = context.createPackageContextAsUser("com.android.systemui", 4, new UserHandle(i));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return context.getPackageManager();
    }

    private void handleAddNotification(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        String key = statusBarNotification.getKey();
        if (DEBUG) {
            Log.d("DesktopHeadsUpController", "handleAddNotification: " + key);
        }
        NotificationEntry notificationEntry = (NotificationEntry) this.mPendingNotifications.get(key);
        if (notificationEntry != null) {
            notificationEntry.setSbn(statusBarNotification);
        } else {
            notificationEntry = new NotificationEntry(statusBarNotification, ranking, SystemClock.uptimeMillis());
            notificationEntry.setDeskHeadsUp(true);
            ArrayList arrayList = this.mListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((NotifCollectionListener) obj).onEntryInit(notificationEntry);
            }
        }
        try {
            notificationEntry.targetSdk = resolveNotificationSdk(notificationEntry.getSbn());
            this.mNotifInflater.getNotificationRowBinder().inflateViews(notificationEntry, getInflaterParams("desktop headsup add notif"), this.mInflationCallback);
            this.mPendingNotifications.put(key, notificationEntry);
        } catch (InflationException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveNotification(String str) {
        if (DEBUG) {
            Log.d("DesktopHeadsUpController", "handleRemoveNotification: " + str);
        }
        this.mOldNotificationKeys.remove(str);
        abortExistingInflation(str, "removeNotification");
        NotificationEntry activeNotificationUnfiltered = getActiveNotificationUnfiltered(str);
        if (activeNotificationUnfiltered == null) {
            Log.d("DesktopHeadsUpController", "handleRemoveNotification without entry: " + str);
            return;
        }
        this.mActiveNotifications.remove(str);
        ArrayList arrayList = this.mListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((NotifCollectionListener) obj).onEntryCleanUp(activeNotificationUnfiltered);
        }
        if (activeNotificationUnfiltered.rowExists()) {
            activeNotificationUnfiltered.removeRow();
        }
        removeHeadsUp(activeNotificationUnfiltered, false);
    }

    private void handleUpdateNotification(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        String key = statusBarNotification.getKey();
        if (DEBUG) {
            Log.d("DesktopHeadsUpController", "handleUpdateNotification: " + key);
        }
        abortExistingInflation(key, "updateNotification");
        NotificationEntry activeNotificationUnfiltered = getActiveNotificationUnfiltered(key);
        if (activeNotificationUnfiltered == null) {
            return;
        }
        activeNotificationUnfiltered.setSbn(statusBarNotification);
        activeNotificationUnfiltered.targetSdk = resolveNotificationSdk(statusBarNotification);
        activeNotificationUnfiltered.setRanking(ranking);
        try {
            this.mNotifInflater.getNotificationRowBinder().inflateViews(activeNotificationUnfiltered, getInflaterParams("desktop headsup update notif"), this.mInflationCallback);
        } catch (InflationException e) {
            e.printStackTrace();
        }
    }

    private void initCurrentHardDisplayMode() {
        readCurrentHardDisplayMode();
        this.mContext.getContentResolver().registerContentObserver(MotorolaSettings.Global.getUriFor("taskbar_display_chooser_mode"), false, this.mHardDisplayModeObserver);
    }

    private boolean isAlerting(String str) {
        HeadsupWindow headsupWindow = (HeadsupWindow) this.mActiveHeadsupWindows.get(str);
        if (headsupWindow != null && headsupWindow.mIsShowing) {
            return true;
        }
        if (!this.mPendingNotifications.containsKey(str)) {
            return false;
        }
        Log.d("DesktopHeadsUpController", "isAlerting from a Pending Notification: " + str);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readCurrentHardDisplayMode() {
        String string = MotorolaSettings.Global.getString(this.mContext.getContentResolver(), "taskbar_display_chooser_mode");
        if (TextUtils.isEmpty(string)) {
            resetCurrentHardDisplayMode();
            return;
        }
        String[] strArrSplit = string.split("_");
        if (strArrSplit == null || strArrSplit.length != 2) {
            resetCurrentHardDisplayMode();
            return;
        }
        try {
            this.mCurrentHardDisplayMode.displayId = Integer.parseInt(strArrSplit[0]);
            this.mCurrentHardDisplayMode.mode = strArrSplit[1];
        } catch (NumberFormatException unused) {
            resetCurrentHardDisplayMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAllHeadsUpWindow() {
        Iterator it = new HashSet(this.mActiveHeadsupWindows.keySet()).iterator();
        while (it.hasNext()) {
            handleRemoveNotification((String) it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeHeadsUp(NotificationEntry notificationEntry, boolean z) {
        HeadsupWindow headsupWindow = (HeadsupWindow) this.mActiveHeadsupWindows.remove(notificationEntry.getKey());
        if (headsupWindow != null) {
            headsupWindow.hide(z);
        }
    }

    private void requestHideHeadsUp(String str) {
        if (this.mAttached) {
            handleRemoveNotification(str);
        }
    }

    private void requestShowHeadsUp(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        if (this.mAttached && !this.mIsHeadUpDisable) {
            if (blockByWcc()) {
                Log.d("DesktopHeadsUpController", "requestShowHeadsUp block by wcc: " + statusBarNotification.getKey());
                return;
            }
            if (DEBUG) {
                Log.d("DesktopHeadsUpController", "requestShowHeadsUp: " + statusBarNotification.getKey());
            }
            boolean zContainsKey = this.mActiveNotifications.containsKey(statusBarNotification.getKey());
            String key = statusBarNotification.getKey();
            if (SystemClock.elapsedRealtime() - this.mAttachedTime < 3000) {
                this.mOldNotificationKeys.add(key);
                return;
            }
            this.mOldNotificationKeys.remove(key);
            if (zContainsKey) {
                handleUpdateNotification(statusBarNotification, ranking);
            } else {
                handleAddNotification(statusBarNotification, ranking);
            }
        }
    }

    private void resetCurrentHardDisplayMode() {
        HardDisplayMode hardDisplayMode = this.mCurrentHardDisplayMode;
        hardDisplayMode.displayId = -1;
        hardDisplayMode.mode = null;
    }

    private int resolveNotificationSdk(StatusBarNotification statusBarNotification) {
        try {
            return getPackageManagerForUser(this.mContext, statusBarNotification.getUser().getIdentifier()).getApplicationInfo(statusBarNotification.getPackageName(), 0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("DesktopHeadsUpController", "Failed looking up ApplicationInfo for " + statusBarNotification.getPackageName(), e);
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHeadsUp(NotificationEntry notificationEntry) {
        if (DEBUG) {
            Log.d("DesktopHeadsUpController", "showHeadsUp: " + notificationEntry.getKey());
        }
        if (this.mDeviceProvisionedController.isDeviceProvisioned() && this.mDeviceProvisionedController.isCurrentUserSetup()) {
            notificationEntry.setInterruption();
            String key = notificationEntry.getKey();
            boolean zContains = this.mOldNotificationKeys.contains(key);
            HeadsupWindow headsupWindowCreateHeadsupWindow = createHeadsupWindow(notificationEntry);
            if (headsupWindowCreateHeadsupWindow == null) {
                return;
            }
            try {
                if (zContains) {
                    Log.w("DesktopHeadsUpController", "HeadsupWindow ignore show for old: " + key);
                } else {
                    headsupWindowCreateHeadsupWindow.show();
                }
                this.mActiveHeadsupWindows.put(key, headsupWindowCreateHeadsupWindow);
            } catch (Exception e) {
                Log.w("DesktopHeadsUpController", "HeadsupWindow show fail: " + e.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHeadsUp(NotificationEntry notificationEntry) {
        HeadsupWindow headsupWindow = (HeadsupWindow) this.mActiveHeadsupWindows.get(notificationEntry.getKey());
        if (DEBUG) {
            Log.d("DesktopHeadsUpController", "updateHeadsUp: " + notificationEntry.getKey() + "; window: " + headsupWindow);
        }
        if (headsupWindow != null) {
            headsupWindow.update(notificationEntry, true);
        } else {
            showHeadsUp(notificationEntry);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserContext(Context context) {
        if (context != null) {
            this.mCurrentUserContext = context;
        } else {
            this.mCurrentUserContext = this.mContext;
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(NotifCollectionListener notifCollectionListener) {
        this.mListeners.add(notifCollectionListener);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void addListener(OnHeadsUpChangedListener onHeadsUpChangedListener) {
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void addSwipedOutNotification(String str) {
    }

    public void attach() {
        Log.d("DesktopHeadsUpController", "attach: ");
        this.mAttached = true;
        this.mAttachedTime = SystemClock.elapsedRealtime();
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        updateUserContext(this.mUserTracker.getUserContext());
        this.mCurrentUserContext.getContentResolver().registerContentObserver(MotorolaSettings.System.getUriFor("settings_disable_popup_notification"), true, this.mContentObserver);
        this.mIsHeadUpDisable = getDisablePopupNotification(this.mCurrentUserContext.getContentResolver());
        this.mConfigurationController.addCallback(this);
        this.mNotifBindPipeline.attach(this);
        initCurrentHardDisplayMode();
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean canRemoveImmediately(String str) {
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public int compare(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        if (notificationEntry == null || notificationEntry2 == null) {
            return Boolean.compare(notificationEntry == null, notificationEntry2 == null);
        }
        long postTime = notificationEntry.getSbn().getPostTime();
        long postTime2 = notificationEntry2.getSbn().getPostTime();
        if (postTime > postTime2) {
            return -1;
        }
        if (postTime == postTime2) {
            return notificationEntry.getKey().compareTo(notificationEntry2.getKey());
        }
        return 1;
    }

    public void detach() {
        Log.d("DesktopHeadsUpController", "detach: ");
        this.mAttached = false;
        this.mCurrentUserContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mUserTracker.removeCallback(this.mUserChangedCallback);
        this.mConfigurationController.removeCallback(this);
        abortAllExistingInflation();
        this.mNotifBindPipeline.detach(this);
        deInitCurrentHardDisplayMode();
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public long getEarliestRemovalTime(String str) {
        return 0L;
    }

    NotifInflater.Params getInflaterParams(String str) {
        return new NotifInflater.Params(false, str, false, false, false, false);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public NotificationEntry getTopEntry() {
        return null;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isHeadsUpEntry(String str) {
        return isAlerting(str);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isSnoozed(String str) {
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isSticky(String str) {
        HeadsupWindow headsupWindow = (HeadsupWindow) this.mActiveHeadsupWindows.get(str);
        if (headsupWindow != null) {
            return headsupWindow.isSticky();
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isTrackingHeadsUp() {
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration) {
        int layoutDirection = configuration.getLayoutDirection();
        if (layoutDirection != this.mLayoutDirection) {
            this.mLayoutDirection = layoutDirection;
        }
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void releaseAllImmediately() {
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(NotifCollectionListener notifCollectionListener) {
        this.mListeners.remove(notifCollectionListener);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean removeNotification(String str, boolean z) {
        requestHideHeadsUp(str);
        return true;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setAnimationStateHandler(AnimationStateHandler animationStateHandler) {
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setExpanded(NotificationEntry notificationEntry, boolean z) {
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setRemoteInputActive(NotificationEntry notificationEntry, boolean z) {
        HeadsupWindow headsupWindow = (HeadsupWindow) this.mActiveHeadsupWindows.get(notificationEntry.getKey());
        if (headsupWindow == null || headsupWindow.mNotificationEntry != notificationEntry || headsupWindow.remoteInputActive == z) {
            return;
        }
        headsupWindow.remoteInputActive = z;
        if (z) {
            headsupWindow.removeAutoRemovalCallbacks();
        } else {
            headsupWindow.addAutoRemovalCallbacks(this.mMinimumDisplayTime);
        }
        headsupWindow.updateFocusableFlag();
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setUser(int i) {
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setUserActionMayIndirectlyRemove(NotificationEntry notificationEntry) {
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void showNotification(NotificationEntry notificationEntry) {
        notificationEntry.setInterruption();
        requestShowHeadsUp(notificationEntry.getSbn(), notificationEntry.getRanking());
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void updateNotification(NotificationEntry notificationEntry, boolean z) {
        if (isAlerting(notificationEntry.getKey()) || z) {
            requestShowHeadsUp(notificationEntry.getSbn(), notificationEntry.getRanking());
        }
    }
}
