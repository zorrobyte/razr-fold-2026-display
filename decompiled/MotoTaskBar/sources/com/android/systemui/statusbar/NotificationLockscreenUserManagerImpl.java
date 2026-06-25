package com.android.systemui.statusbar;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.database.ExecutorContentObserver;
import android.net.Uri;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class NotificationLockscreenUserManagerImpl implements Dumpable, NotificationLockscreenUserManager {
    private static final Uri SHOW_LOCKSCREEN = Settings.Secure.getUriFor("lock_screen_show_notifications");
    private static final Uri SHOW_PRIVATE_LOCKSCREEN = Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
    protected final BroadcastReceiver mAllUsersReceiver;
    private final Executor mBackgroundExecutor;
    protected final BroadcastReceiver mBaseBroadcastReceiver;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final NotificationClickNotifier mClickNotifier;
    private final Lazy mCommonNotifCollectionLazy;
    protected final Context mContext;
    protected final SparseArray mCurrentManagedProfiles;
    protected final SparseArray mCurrentProfiles;
    protected int mCurrentUserId;
    private final DevicePolicyManager mDevicePolicyManager;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final FeatureFlagsClassic mFeatureFlags;
    protected KeyguardManager mKeyguardManager;
    protected final BroadcastReceiver mKeyguardReceiver;
    private LockPatternUtils mLockPatternUtils;
    private final Collection mLockScreenUris;
    protected ContentObserver mLockscreenSettingsObserver;
    private final Executor mMainExecutor;
    protected NotificationPresenter mPresenter;
    private final SecureSettings mSecureSettings;
    protected ContentObserver mSettingsObserver;
    private boolean mShowLockscreenNotifications;
    protected final UserTracker.Callback mUserChangedCallback;
    private final UserManager mUserManager;
    private final UserTracker mUserTracker;
    private final Lazy mVisibilityProviderLazy;
    private final Object mLock = new Object();
    private final SparseBooleanArray mLockscreenPublicMode = new SparseBooleanArray();
    private final SparseBooleanArray mUsersWithSeparateWorkChallenge = new SparseBooleanArray();
    private final SparseBooleanArray mUsersAllowingPrivateNotifications = new SparseBooleanArray();
    private final SparseBooleanArray mUsersAllowingNotifications = new SparseBooleanArray();
    private final SparseBooleanArray mUsersDpcAllowingNotifications = new SparseBooleanArray();
    private final SparseBooleanArray mUsersUsersAllowingNotifications = new SparseBooleanArray();
    private boolean mKeyguardAllowingNotifications = true;
    private final SparseBooleanArray mUsersDpcAllowingPrivateNotifications = new SparseBooleanArray();
    private final SparseBooleanArray mUsersUsersAllowingPrivateNotifications = new SparseBooleanArray();
    private final SparseBooleanArray mUsersInLockdownLatestResult = new SparseBooleanArray();
    private final SparseBooleanArray mShouldHideNotifsLatestResult = new SparseBooleanArray();
    private final List mListeners = new ArrayList();
    private final ListenerSet mNotifStateChangedListeners = new ListenerSet();

    /* JADX INFO: renamed from: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$3, reason: invalid class name */
    class AnonymousClass3 extends BroadcastReceiver {
        AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(int i) {
            NotificationLockscreenUserManagerImpl.this.initValuesForUser(i);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Objects.equals(action, "android.intent.action.USER_REMOVED")) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra != -1) {
                    Iterator it = NotificationLockscreenUserManagerImpl.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onUserRemoved(intExtra);
                    }
                }
                NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                return;
            }
            if (Objects.equals(action, "android.intent.action.USER_ADDED")) {
                NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                final int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                NotificationLockscreenUserManagerImpl.this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReceive$0(intExtra2);
                    }
                });
                return;
            }
            if (NotificationLockscreenUserManagerImpl.this.profileAvailabilityActions(action)) {
                NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                return;
            }
            if (!Objects.equals(action, "android.intent.action.USER_UNLOCKED") && Objects.equals(action, "com.android.systemui.statusbar.work_challenge_unlocked_notification_action")) {
                IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT");
                String stringExtra = intent.getStringExtra("android.intent.extra.INDEX");
                if (intentSender != null) {
                    try {
                        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        NotificationLockscreenUserManagerImpl.this.mContext.startIntentSender(intentSender, null, 0, 0, 0, activityOptionsMakeBasic.toBundle());
                    } catch (IntentSender.SendIntentException unused) {
                    }
                }
                if (stringExtra != null) {
                    NotificationLockscreenUserManagerImpl.this.mClickNotifier.onNotificationClick(stringExtra, ((NotificationVisibilityProvider) NotificationLockscreenUserManagerImpl.this.mVisibilityProviderLazy.get()).obtain(stringExtra, true));
                }
            }
        }
    }

    public NotificationLockscreenUserManagerImpl(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, UserTracker userTracker, Lazy lazy, Lazy lazy2, NotificationClickNotifier notificationClickNotifier, KeyguardManager keyguardManager, Executor executor, Executor executor2, DeviceProvisionedController deviceProvisionedController, SecureSettings secureSettings, DumpManager dumpManager, LockPatternUtils lockPatternUtils, FeatureFlagsClassic featureFlagsClassic) {
        ArrayList arrayList = new ArrayList();
        this.mLockScreenUris = arrayList;
        this.mKeyguardReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED".equals(intent.getAction())) {
                    NotificationLockscreenUserManagerImpl.this.mKeyguardAllowingNotifications = intent.getBooleanExtra("android.app.extra.KM_PRIVATE_NOTIFS_ALLOWED", false);
                    if (NotificationLockscreenUserManagerImpl.this.mCurrentUserId == getSendingUserId() && NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting()) {
                        NotificationLockscreenUserManagerImpl.this.notifyNotificationStateChanged();
                    }
                }
            }
        };
        this.mAllUsersReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                boolean zUpdateDpcSettings;
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction())) {
                    int sendingUserId = getSendingUserId();
                    if (sendingUserId == -1) {
                        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                        int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
                        List users = notificationLockscreenUserManagerImpl.mUserManager.getUsers();
                        zUpdateDpcSettings = false;
                        for (int size = users.size() - 1; size >= 0; size--) {
                            zUpdateDpcSettings |= NotificationLockscreenUserManagerImpl.this.updateDpcSettings(((UserInfo) users.get(size)).id);
                        }
                        sendingUserId = i;
                    } else {
                        zUpdateDpcSettings = NotificationLockscreenUserManagerImpl.this.updateDpcSettings(sendingUserId);
                    }
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = NotificationLockscreenUserManagerImpl.this;
                    if (notificationLockscreenUserManagerImpl2.mCurrentUserId == sendingUserId) {
                        zUpdateDpcSettings |= notificationLockscreenUserManagerImpl2.updateLockscreenNotificationSetting();
                    }
                    if (zUpdateDpcSettings) {
                        NotificationLockscreenUserManagerImpl.this.notifyNotificationStateChanged();
                    }
                }
            }
        };
        this.mBaseBroadcastReceiver = new AnonymousClass3();
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.4
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanging(int i, Context context2) {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                notificationLockscreenUserManagerImpl.mCurrentUserId = i;
                notificationLockscreenUserManagerImpl.updateCurrentProfilesCache();
                Log.v("LockscreenUserManager", "userId " + NotificationLockscreenUserManagerImpl.this.mCurrentUserId + " is in the house");
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManagerImpl.this.updatePublicMode();
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = NotificationLockscreenUserManagerImpl.this;
                NotificationPresenter notificationPresenter = notificationLockscreenUserManagerImpl2.mPresenter;
                if (notificationPresenter != null) {
                    notificationPresenter.onUserSwitched(notificationLockscreenUserManagerImpl2.mCurrentUserId);
                } else {
                    Log.w("LockscreenUserManager", "user switch before setup with presenter", new Exception());
                }
                Iterator it = NotificationLockscreenUserManagerImpl.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onUserChanged(NotificationLockscreenUserManagerImpl.this.mCurrentUserId);
                }
            }
        };
        this.mCurrentProfiles = new SparseArray();
        this.mCurrentManagedProfiles = new SparseArray();
        this.mCurrentUserId = 0;
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mCurrentUserId = userTracker.getUserId();
        this.mVisibilityProviderLazy = lazy;
        this.mCommonNotifCollectionLazy = lazy2;
        this.mClickNotifier = notificationClickNotifier;
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardManager = keyguardManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mSecureSettings = secureSettings;
        this.mFeatureFlags = featureFlagsClassic;
        arrayList.add(SHOW_LOCKSCREEN);
        arrayList.add(SHOW_PRIVATE_LOCKSCREEN);
        dumpManager.registerDumpable(this);
        init();
    }

    private void init() {
        this.mLockscreenSettingsObserver = new ExecutorContentObserver(this.mBackgroundExecutor) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.5
            public void onChange(boolean z, Collection collection, int i) {
                List users = NotificationLockscreenUserManagerImpl.this.mUserManager.getUsers();
                for (int size = users.size() - 1; size >= 0; size--) {
                    onChange(z, collection, i, ((UserInfo) users.get(size)).getUserHandle());
                }
            }

            public void onChange(boolean z, Collection collection, int i, UserHandle userHandle) {
                boolean zUpdateUserShowSettings;
                Iterator it = collection.iterator();
                boolean zUpdateLockscreenNotificationSetting = false;
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    if (NotificationLockscreenUserManagerImpl.SHOW_LOCKSCREEN.equals(uri)) {
                        zUpdateUserShowSettings = NotificationLockscreenUserManagerImpl.this.updateUserShowSettings(userHandle.getIdentifier());
                    } else if (NotificationLockscreenUserManagerImpl.SHOW_PRIVATE_LOCKSCREEN.equals(uri)) {
                        zUpdateUserShowSettings = NotificationLockscreenUserManagerImpl.this.updateUserShowPrivateSettings(userHandle.getIdentifier());
                    }
                    zUpdateLockscreenNotificationSetting |= zUpdateUserShowSettings;
                }
                if (NotificationLockscreenUserManagerImpl.this.mCurrentUserId == userHandle.getIdentifier()) {
                    zUpdateLockscreenNotificationSetting |= NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                }
                if (zUpdateLockscreenNotificationSetting) {
                    NotificationLockscreenUserManagerImpl.this.notifyNotificationStateChanged();
                }
            }
        };
        this.mSettingsObserver = new ExecutorContentObserver(this.mMainExecutor) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.6
            public void onChange(boolean z) {
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManagerImpl.this.mDeviceProvisionedController.isDeviceProvisioned();
            }
        };
        this.mContext.getContentResolver().registerContentObserver(SHOW_LOCKSCREEN, false, this.mLockscreenSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(SHOW_PRIVATE_LOCKSCREEN, true, this.mLockscreenSettingsObserver, -1);
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        BroadcastReceiver broadcastReceiver = this.mAllUsersReceiver;
        IntentFilter intentFilter = new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        Executor executor = this.mBackgroundExecutor;
        UserHandle userHandle = UserHandle.ALL;
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, executor, userHandle);
        this.mBroadcastDispatcher.registerReceiver(this.mKeyguardReceiver, new IntentFilter("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED"), this.mBackgroundExecutor, userHandle);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        if (privateSpaceFlagsEnabled()) {
            intentFilter2.addAction("android.intent.action.PROFILE_AVAILABLE");
            intentFilter2.addAction("android.intent.action.PROFILE_UNAVAILABLE");
        }
        this.mBroadcastDispatcher.registerReceiver(this.mBaseBroadcastReceiver, intentFilter2, null, userHandle);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        this.mContext.registerReceiver(this.mBaseBroadcastReceiver, intentFilter3, "com.android.systemui.permission.SELF", null, 2);
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mMainExecutor);
        this.mCurrentUserId = this.mUserTracker.getUserId();
        updateCurrentProfilesCache();
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$init$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initValuesForUser(int i) {
        this.mLockscreenSettingsObserver.onChange(false, this.mLockScreenUris, 0, UserHandle.of(i));
        updateDpcSettings(i);
        updateGlobalKeyguardSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        List users = this.mUserManager.getUsers();
        for (int size = users.size() - 1; size >= 0; size--) {
            initValuesForUser(((UserInfo) users.get(size)).id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyNotificationStateChanged$3() {
        Iterator it = this.mNotifStateChangedListeners.iterator();
        while (it.hasNext()) {
            ((NotificationLockscreenUserManager.NotificationStateChangedListener) it.next()).onNotificationStateChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCurrentProfilesCache$1() {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onCurrentProfilesChanged(this.mCurrentProfiles);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$updatePublicMode$2(int i) {
        return Boolean.valueOf(this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNotificationStateChanged() {
        if (!Looper.getMainLooper().isCurrentThread()) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyNotificationStateChanged$3();
                }
            });
            return;
        }
        Iterator it = this.mNotifStateChangedListeners.iterator();
        while (it.hasNext()) {
            ((NotificationLockscreenUserManager.NotificationStateChangedListener) it.next()).onNotificationStateChanged();
        }
    }

    private boolean packageHasVisibilityOverride(String str) {
        if (this.mCommonNotifCollectionLazy.get() == null) {
            Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
            return true;
        }
        NotificationEntry entry = ((CommonNotifCollection) this.mCommonNotifCollectionLazy.get()).getEntry(str);
        return entry != null && entry.isChannelVisibilityPrivate();
    }

    private static boolean privateSpaceFlagsEnabled() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean profileAvailabilityActions(String str) {
        return privateSpaceFlagsEnabled() ? Objects.equals(str, "android.intent.action.PROFILE_AVAILABLE") || Objects.equals(str, "android.intent.action.PROFILE_UNAVAILABLE") : Objects.equals(str, "android.intent.action.MANAGED_PROFILE_AVAILABLE") || Objects.equals(str, "android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
    }

    private void setShowLockscreenNotifications(boolean z) {
        this.mShowLockscreenNotifications = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            try {
                this.mCurrentProfiles.clear();
                this.mCurrentManagedProfiles.clear();
                UserManager userManager = this.mUserManager;
                if (userManager != null) {
                    for (UserInfo userInfo : userManager.getProfiles(this.mCurrentUserId)) {
                        this.mCurrentProfiles.put(userInfo.id, userInfo);
                        if ("android.os.usertype.profile.MANAGED".equals(userInfo.userType)) {
                            this.mCurrentManagedProfiles.put(userInfo.id, userInfo);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateCurrentProfilesCache$1();
            }
        });
    }

    private boolean updateGlobalKeyguardSettings() {
        boolean z = this.mKeyguardAllowingNotifications;
        this.mKeyguardAllowingNotifications = true;
        return !z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateUserShowPrivateSettings(int i) {
        boolean z = this.mUsersUsersAllowingPrivateNotifications.get(i);
        boolean z2 = this.mSecureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, i) != 0;
        this.mUsersUsersAllowingPrivateNotifications.put(i, z2);
        return z2 != z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateUserShowSettings(int i) {
        boolean z = this.mUsersUsersAllowingNotifications.get(i);
        boolean z2 = this.mSecureSettings.getIntForUser("lock_screen_show_notifications", 1, i) != 0;
        this.mUsersUsersAllowingNotifications.put(i, z2);
        return z2 != z;
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public void addNotificationStateChangedListener(NotificationLockscreenUserManager.NotificationStateChangedListener notificationStateChangedListener) {
        this.mNotifStateChangedListeners.addIfAbsent(notificationStateChangedListener);
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public void addUserChangedListener(NotificationLockscreenUserManager.UserChangedListener userChangedListener) {
        this.mListeners.add(userChangedListener);
    }

    public SparseArray getCurrentProfiles() {
        return this.mCurrentProfiles;
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public int getCurrentUserId() {
        return this.mCurrentUserId;
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean isAnyProfilePublicMode() {
        synchronized (this.mLock) {
            try {
                for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size--) {
                    if (isLockscreenPublicMode(((UserInfo) this.mCurrentProfiles.valueAt(size)).id)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean isCurrentProfile(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    z = this.mCurrentProfiles.get(i) != null;
                } finally {
                }
            }
        }
        return z;
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean isLockscreenPublicMode(int i) {
        return i == -1 ? this.mLockscreenPublicMode.get(this.mCurrentUserId, false) : this.mLockscreenPublicMode.get(i, false);
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean isProfileAvailable(int i) {
        boolean zIsUserRunning;
        synchronized (this.mLock) {
            zIsUserRunning = this.mUserManager.isUserRunning(i);
        }
        return zIsUserRunning;
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean needsRedaction(NotificationEntry notificationEntry) {
        int userId = notificationEntry.getSbn().getUserId();
        boolean z = ((this.mCurrentManagedProfiles.contains(userId) || userAllowsPrivateNotificationsInPublic(this.mCurrentUserId)) && userAllowsPrivateNotificationsInPublic(userId)) ? false : true;
        return !this.mKeyguardAllowingNotifications || packageHasVisibilityOverride(notificationEntry.getSbn().getKey()) || (notificationEntry.isNotificationVisibilityPrivate() && z);
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean needsSeparateWorkChallenge(int i) {
        return this.mUsersWithSeparateWorkChallenge.get(i, false);
    }

    void setLockscreenPublicMode(boolean z, int i) {
        this.mLockscreenPublicMode.put(i, z);
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public void setUpWithPresenter(NotificationPresenter notificationPresenter) {
        this.mPresenter = notificationPresenter;
    }

    protected boolean updateDpcSettings(int i) {
        boolean z = this.mUsersDpcAllowingNotifications.get(i);
        boolean z2 = this.mUsersDpcAllowingPrivateNotifications.get(i);
        int keyguardDisabledFeatures = this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i);
        boolean z3 = (keyguardDisabledFeatures & 4) == 0;
        boolean z4 = (keyguardDisabledFeatures & 8) == 0;
        this.mUsersDpcAllowingNotifications.put(i, z3);
        this.mUsersDpcAllowingPrivateNotifications.put(i, z4);
        return (z == z3 && z2 == z4) ? false : true;
    }

    protected boolean updateLockscreenNotificationSetting() {
        boolean z = this.mUsersUsersAllowingNotifications.get(this.mCurrentUserId);
        boolean z2 = this.mUsersDpcAllowingNotifications.get(this.mCurrentUserId, true);
        boolean z3 = this.mShowLockscreenNotifications;
        setShowLockscreenNotifications(z && z2);
        return z3 != this.mShowLockscreenNotifications;
    }

    public void updatePublicMode() {
        SparseArray currentProfiles = getCurrentProfiles();
        SparseBooleanArray sparseBooleanArrayClone = this.mLockscreenPublicMode.clone();
        SparseBooleanArray sparseBooleanArrayClone2 = this.mUsersWithSeparateWorkChallenge.clone();
        this.mUsersWithSeparateWorkChallenge.clear();
        for (int size = currentProfiles.size() - 1; size >= 0; size--) {
            final int i = ((UserInfo) currentProfiles.valueAt(size)).id;
            boolean zBooleanValue = ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$updatePublicMode$2(i);
                }
            })).booleanValue();
            setLockscreenPublicMode((i != getCurrentUserId() && zBooleanValue && this.mLockPatternUtils.isSecure(i)) ? this.mKeyguardManager.isDeviceLocked(i) : false, i);
            this.mUsersWithSeparateWorkChallenge.put(i, zBooleanValue);
        }
        if (this.mLockscreenPublicMode.equals(sparseBooleanArrayClone) && this.mUsersWithSeparateWorkChallenge.equals(sparseBooleanArrayClone2)) {
            return;
        }
        notifyNotificationStateChanged();
    }

    @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager
    public boolean userAllowsPrivateNotificationsInPublic(int i) {
        if (i == -1) {
            i = this.mCurrentUserId;
        }
        if (this.mUsersUsersAllowingPrivateNotifications.indexOfKey(i) < 0) {
            Log.i("LockscreenUserManager", "Asking for redact notifs setting too early", new Throwable());
            return false;
        }
        if (this.mUsersDpcAllowingPrivateNotifications.indexOfKey(i) >= 0) {
            return this.mUsersUsersAllowingPrivateNotifications.get(i) && this.mUsersDpcAllowingPrivateNotifications.get(i) && this.mKeyguardAllowingNotifications;
        }
        Log.i("LockscreenUserManager", "Asking for redact notifs dpm override too early", new Throwable());
        return false;
    }
}
