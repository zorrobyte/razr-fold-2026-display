package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.UriGrantsManager;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.Utils;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.time.SystemClock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: LegacyMediaDataManagerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LegacyMediaDataManagerImpl implements Dumpable, MediaDataManager {
    public static final Companion Companion = new Companion(null);
    public static final int MAX_COMPACT_ACTIONS = 3;
    public static final int MAX_NOTIFICATION_ACTIONS = MediaViewHolder.Companion.getGenericButtonIds().size();
    private final ActivityStarter activityStarter;
    private final LegacyMediaDataManagerImpl$appChangeReceiver$1 appChangeReceiver;
    private final int artworkHeight;
    private final int artworkWidth;
    private final Executor backgroundExecutor;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private final DelayableExecutor foregroundExecutor;
    private final Set internalListeners;
    private final MediaUiEventLogger logger;
    private final MediaControllerFactory mediaControllerFactory;
    private final LegacyMediaDataFilterImpl mediaDataFilter;
    private final MediaDeviceManager mediaDeviceManager;
    private final LinkedHashMap mediaEntries;
    private final MediaFlags mediaFlags;
    private final StatusBarManager statusBarManager;
    private final SystemClock systemClock;
    private final int themeText;
    private final Executor uiExecutor;
    private boolean useMediaResumption;
    private final boolean useQsMediaPlayer;

    /* JADX INFO: compiled from: LegacyMediaDataManagerImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public LegacyMediaDataManagerImpl(Context context, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, ActivityStarter activityStarter, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger) {
        context.getClass();
        threadFactory.getClass();
        executor.getClass();
        delayableExecutor.getClass();
        mediaControllerFactory.getClass();
        dumpManager.getClass();
        broadcastDispatcher.getClass();
        mediaTimeoutListener.getClass();
        mediaResumeListener.getClass();
        mediaSessionBasedFilter.getClass();
        mediaDeviceManager.getClass();
        mediaDataCombineLatest.getClass();
        legacyMediaDataFilterImpl.getClass();
        activityStarter.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        mediaUiEventLogger.getClass();
        Executor executorBuildExecutorOnNewThread = threadFactory.buildExecutorOnNewThread("MediaDataManager");
        executorBuildExecutorOnNewThread.getClass();
        this(context, executorBuildExecutorOnNewThread, executor, delayableExecutor, mediaControllerFactory, broadcastDispatcher, dumpManager, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, legacyMediaDataFilterImpl, activityStarter, Utils.useMediaResumption(context), Utils.useQsMediaPlayer(context), systemClock, mediaFlags, mediaUiEventLogger);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v19, types: [android.content.BroadcastReceiver, com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$appChangeReceiver$1] */
    public LegacyMediaDataManagerImpl(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, ActivityStarter activityStarter, boolean z, boolean z2, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger) {
        context.getClass();
        executor.getClass();
        executor2.getClass();
        delayableExecutor.getClass();
        mediaControllerFactory.getClass();
        broadcastDispatcher.getClass();
        dumpManager.getClass();
        mediaTimeoutListener.getClass();
        mediaResumeListener.getClass();
        mediaSessionBasedFilter.getClass();
        mediaDeviceManager.getClass();
        mediaDataCombineLatest.getClass();
        legacyMediaDataFilterImpl.getClass();
        activityStarter.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        mediaUiEventLogger.getClass();
        this.context = context;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.mediaControllerFactory = mediaControllerFactory;
        this.broadcastDispatcher = broadcastDispatcher;
        this.mediaDeviceManager = mediaDeviceManager;
        this.mediaDataFilter = legacyMediaDataFilterImpl;
        this.activityStarter = activityStarter;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.themeText = com.android.settingslib.Utils.getColorAttr(context, R.attr.textColorPrimary).getDefaultColor();
        this.internalListeners = new LinkedHashSet();
        this.mediaEntries = new LinkedHashMap();
        this.artworkWidth = context.getResources().getDimensionPixelSize(R.dimen.config_prefDialogWidth);
        this.artworkHeight = context.getResources().getDimensionPixelSize(R$dimen.qs_media_session_height_expanded);
        Object systemService = context.getSystemService("statusbar");
        systemService.getClass();
        this.statusBarManager = (StatusBarManager) systemService;
        ?? r11 = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$appChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra;
                String encodedSchemeSpecificPart;
                context2.getClass();
                intent.getClass();
                String action = intent.getAction();
                if (action != null) {
                    int iHashCode = action.hashCode();
                    if (iHashCode != -1001645458) {
                        if (iHashCode != -757780528) {
                            if (iHashCode != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                return;
                            }
                        } else if (!action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            return;
                        }
                        Uri data = intent.getData();
                        if (data == null || (encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart()) == null) {
                            return;
                        }
                        this.this$0.removeAllForPackage(encodedSchemeSpecificPart);
                        return;
                    }
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.this$0;
                        for (String str : stringArrayExtra) {
                            str.getClass();
                            legacyMediaDataManagerImpl.removeAllForPackage(str);
                        }
                    }
                }
            }
        };
        this.appChangeReceiver = r11;
        DumpManager.registerDumpable$default(dumpManager, "MediaDataManager", this, null, 4, null);
        addInternalListener(mediaTimeoutListener);
        addInternalListener(mediaResumeListener);
        addInternalListener(mediaSessionBasedFilter);
        mediaDeviceManager.start();
        mediaSessionBasedFilter.addListener(mediaDeviceManager);
        mediaSessionBasedFilter.addListener(mediaDataCombineLatest);
        mediaDeviceManager.addListener(mediaDataCombineLatest);
        mediaDataCombineLatest.addListener(legacyMediaDataFilterImpl);
        mediaTimeoutListener.setTimeoutCallback(new Function2() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return LegacyMediaDataManagerImpl._init_$lambda$0(this.f$0, (String) obj, ((Boolean) obj2).booleanValue());
            }
        });
        mediaTimeoutListener.setStateCallback(new Function2() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return LegacyMediaDataManagerImpl._init_$lambda$1(this.f$0, (String) obj, (PlaybackState) obj2);
            }
        });
        mediaTimeoutListener.setSessionCallback(new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LegacyMediaDataManagerImpl._init_$lambda$2(this.f$0, (String) obj);
            }
        });
        mediaResumeListener.setManager(this);
        legacyMediaDataFilterImpl.setMediaDataManager(this);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGES_SUSPENDED");
        UserHandle userHandle = UserHandle.ALL;
        userHandle.getClass();
        broadcastDispatcher.registerReceiver(r11, intentFilter, null, userHandle);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter2.addDataScheme("package");
        context.registerReceiver(r11, intentFilter2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$0(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, boolean z) {
        str.getClass();
        MediaDataManager.setInactive$default(legacyMediaDataManagerImpl, str, z, false, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$1(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, PlaybackState playbackState) {
        str.getClass();
        playbackState.getClass();
        legacyMediaDataManagerImpl.updateState(str, playbackState);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$2(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str) {
        str.getClass();
        legacyMediaDataManagerImpl.onSessionDestroyed(str);
        return Unit.INSTANCE;
    }

    private final boolean addInternalListener(MediaDataManager.Listener listener) {
        return this.internalListeners.add(listener);
    }

    private final void convertToResumePlayer(String str, MediaData mediaData) {
        List listEmptyList;
        Log.d("MediaDataManager", "Converting " + str + " to resume");
        CharSequence song = mediaData.getSong();
        if (song == null || StringsKt.isBlank(song)) {
            Log.e("MediaDataManager", "Description incomplete");
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            return;
        }
        Runnable resumeAction = mediaData.getResumeAction();
        MediaAction resumeMediaAction = resumeAction != null ? getResumeMediaAction(resumeAction) : null;
        if (resumeMediaAction == null || (listEmptyList = CollectionsKt.listOf(resumeMediaAction)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        Intent launchIntentForPackage = this.context.getPackageManager().getLaunchIntentForPackage(mediaData.getPackageName());
        MediaData mediaDataCopy$default = MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, list, CollectionsKt.listOf((Object) 0), new MediaButton(resumeMediaAction, null, null, null, null, false, false, 126, null), null, null, launchIntentForPackage != null ? PendingIntent.getActivity(this.context, 0, launchIntentForPackage, 67108864) : null, null, false, null, 0, true, null, false, Boolean.FALSE, true, mediaData.getActive() ? this.systemClock.elapsedRealtime() : mediaData.getLastActive(), 0L, null, 0, false, null, 260940927, null);
        String packageName = mediaData.getPackageName();
        boolean z = this.mediaEntries.put(packageName, mediaDataCopy$default) == null;
        Log.d("MediaDataManager", "migrating? " + z + " from " + str + " -> " + packageName);
        if (z) {
            notifyMediaDataLoaded(packageName, str, mediaDataCopy$default);
        } else {
            notifyMediaDataRemoved(str);
            notifyMediaDataLoaded(packageName, packageName, mediaDataCopy$default);
        }
        this.logger.logActiveConvertedToResume(mediaDataCopy$default.getAppUid(), packageName, mediaDataCopy$default.getInstanceId());
        LinkedHashMap linkedHashMap = this.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((MediaData) entry.getValue()).getResumption()) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap2.size();
        if (size > 5) {
            for (Pair pair : CollectionsKt.sortedWith(MapsKt.toList(linkedHashMap2), new Comparator() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$convertToResumePlayer$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    Pair pair2 = (Pair) obj;
                    Long lValueOf = Long.valueOf(((MediaData) pair2.component2()).getLastActive());
                    Pair pair3 = (Pair) obj2;
                    return ComparisonsKt.compareValues(lValueOf, Long.valueOf(((MediaData) pair3.component2()).getLastActive()));
                }
            }).subList(0, size - 5)) {
                String str2 = (String) pair.component1();
                MediaData mediaData2 = (MediaData) pair.component2();
                Log.d("MediaDataManager", "Removing excess control " + str2);
                this.mediaEntries.remove(str2);
                notifyMediaDataRemoved(str2);
                this.logger.logMediaRemoved(mediaData2.getAppUid(), mediaData2.getPackageName(), mediaData2.getInstanceId());
            }
        }
    }

    private final Pair createActionsFromNotification(StatusBarNotification statusBarNotification) {
        List arrayList;
        Icon icon;
        Notification notification = statusBarNotification.getNotification();
        ArrayList arrayList2 = new ArrayList();
        Notification.Action[] actionArr = notification.actions;
        int[] intArray = notification.extras.getIntArray("android.compactActions");
        if (intArray == null || (arrayList = ArraysKt.toMutableList(intArray)) == null) {
            arrayList = new ArrayList();
        }
        int size = arrayList.size();
        int i = MAX_COMPACT_ACTIONS;
        if (size > i) {
            Log.e("MediaDataManager", "Too many compact actions for " + statusBarNotification.getKey() + ",limiting to first " + i);
            arrayList = arrayList.subList(0, i);
        }
        if (actionArr != null) {
            Iterator it = ArraysKt.withIndex(actionArr).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                IndexedValue indexedValue = (IndexedValue) it.next();
                int iComponent1 = indexedValue.component1();
                final Notification.Action action = (Notification.Action) indexedValue.component2();
                int i2 = MAX_NOTIFICATION_ACTIONS;
                if (iComponent1 == i2) {
                    Log.w("MediaDataManager", "Too many notification actions for " + statusBarNotification.getKey() + ", limiting to first " + i2);
                    break;
                }
                if (action.getIcon() == null) {
                    Log.i("MediaDataManager", "No icon for action " + iComponent1 + " " + ((Object) action.title));
                    arrayList.remove(Integer.valueOf(iComponent1));
                } else {
                    Runnable runnable = action.actionIntent != null ? new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$createActionsFromNotification$runnable$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (action.actionIntent.isActivity()) {
                                this.activityStarter.startPendingIntentDismissingKeyguard(action.actionIntent);
                                return;
                            }
                            if (action.isAuthenticationRequired()) {
                                ActivityStarter activityStarter = this.activityStarter;
                                final LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this;
                                final Notification.Action action2 = action;
                                activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$createActionsFromNotification$runnable$1.1
                                    @Override // com.android.systemui.displays.ActivityStarter.OnDismissAction
                                    public final boolean onDismiss() {
                                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl2 = legacyMediaDataManagerImpl;
                                        PendingIntent pendingIntent = action2.actionIntent;
                                        pendingIntent.getClass();
                                        return legacyMediaDataManagerImpl2.sendPendingIntent(pendingIntent);
                                    }
                                }, new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$createActionsFromNotification$runnable$1.2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                    }
                                }, true);
                                return;
                            }
                            LegacyMediaDataManagerImpl legacyMediaDataManagerImpl2 = this;
                            PendingIntent pendingIntent = action.actionIntent;
                            pendingIntent.getClass();
                            legacyMediaDataManagerImpl2.sendPendingIntent(pendingIntent);
                        }
                    } : null;
                    Icon icon2 = action.getIcon();
                    if (icon2 == null || icon2.getType() != 2) {
                        icon = action.getIcon();
                    } else {
                        String packageName = statusBarNotification.getPackageName();
                        Icon icon3 = action.getIcon();
                        icon3.getClass();
                        icon = Icon.createWithResource(packageName, icon3.getResId());
                    }
                    arrayList2.add(new MediaAction(icon.setTint(this.themeText).loadDrawable(this.context), runnable, action.title, null, null, 16, null));
                }
            }
        }
        return new Pair(arrayList2, arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final MediaButton createActionsFromState(final String str, MediaController mediaController, UserHandle userHandle) {
        final LegacyMediaDataManagerImpl legacyMediaDataManagerImpl;
        final MediaController mediaController2;
        MediaAction standardAction;
        MediaAction mediaAction;
        MediaAction mediaActionCreateActionsFromState$nextCustomAction;
        final PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null || !this.mediaFlags.areMediaSessionActionsEnabled(str, userHandle)) {
            return null;
        }
        if (NotificationMediaManager.isConnectingState(playbackState.getState())) {
            Drawable drawable = this.context.getDrawable(17304686);
            drawable.getClass();
            ((Animatable) drawable).start();
            legacyMediaDataManagerImpl = this;
            standardAction = new MediaAction(drawable, null, this.context.getString(R$string.controls_media_button_connecting), this.context.getDrawable(R$drawable.ic_media_connecting_container), 17304686);
            mediaController2 = mediaController;
        } else if (NotificationMediaManager.isPlayingState(playbackState.getState())) {
            legacyMediaDataManagerImpl = this;
            mediaController2 = mediaController;
            standardAction = legacyMediaDataManagerImpl.getStandardAction(mediaController2, playbackState.getActions(), 2L);
        } else {
            legacyMediaDataManagerImpl = this;
            mediaController2 = mediaController;
            standardAction = legacyMediaDataManagerImpl.getStandardAction(mediaController2, playbackState.getActions(), 4L);
        }
        MediaAction standardAction2 = legacyMediaDataManagerImpl.getStandardAction(mediaController2, playbackState.getActions(), 16L);
        MediaAction standardAction3 = legacyMediaDataManagerImpl.getStandardAction(mediaController2, playbackState.getActions(), 32L);
        List<PlaybackState.CustomAction> customActions = playbackState.getCustomActions();
        customActions.getClass();
        Iterator it = SequencesKt.map(SequencesKt.filterNotNull(CollectionsKt.asSequence(customActions)), new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LegacyMediaDataManagerImpl.createActionsFromState$lambda$14(this.f$0, playbackState, str, mediaController2, (PlaybackState.CustomAction) obj);
            }
        }).iterator();
        Bundle extras = mediaController2.getExtras();
        boolean z = extras != null && extras.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS");
        Bundle extras2 = mediaController2.getExtras();
        boolean z2 = extras2 != null && extras2.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT");
        if (standardAction2 != null) {
            mediaAction = standardAction2;
        } else if (z) {
            mediaAction = null;
        } else {
            standardAction2 = createActionsFromState$nextCustomAction(it);
            mediaAction = standardAction2;
        }
        if (standardAction3 != null) {
            mediaActionCreateActionsFromState$nextCustomAction = standardAction3;
        } else {
            mediaActionCreateActionsFromState$nextCustomAction = z2 ? null : createActionsFromState$nextCustomAction(it);
        }
        return new MediaButton(standardAction, mediaActionCreateActionsFromState$nextCustomAction, mediaAction, createActionsFromState$nextCustomAction(it), createActionsFromState$nextCustomAction(it), z2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MediaAction createActionsFromState$lambda$14(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, PlaybackState playbackState, String str, MediaController mediaController, PlaybackState.CustomAction customAction) {
        customAction.getClass();
        return legacyMediaDataManagerImpl.getCustomAction(playbackState, str, mediaController, customAction);
    }

    private static final MediaAction createActionsFromState$nextCustomAction(Iterator it) {
        if (it.hasNext()) {
            return (MediaAction) it.next();
        }
        return null;
    }

    private final String findExistingEntry(String str, String str2) {
        if (this.mediaEntries.containsKey(str)) {
            return str;
        }
        if (this.mediaEntries.containsKey(str2)) {
            return str2;
        }
        return null;
    }

    private final ApplicationInfo getAppInfoFromPackage(String str) {
        try {
            return this.context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("MediaDataManager", "Could not get app info for " + str, e);
            return null;
        }
    }

    private final String getAppName(StatusBarNotification statusBarNotification, ApplicationInfo applicationInfo) {
        String string = statusBarNotification.getNotification().extras.getString("android.substName");
        if (string != null) {
            return string;
        }
        if (applicationInfo != null) {
            return this.context.getPackageManager().getApplicationLabel(applicationInfo).toString();
        }
        String packageName = statusBarNotification.getPackageName();
        packageName.getClass();
        return packageName;
    }

    private final MediaAction getCustomAction(PlaybackState playbackState, String str, final MediaController mediaController, final PlaybackState.CustomAction customAction) {
        return new MediaAction(Icon.createWithResource(str, customAction.getIcon()).loadDrawable(this.context), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.getCustomAction.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.TransportControls transportControls = mediaController.getTransportControls();
                PlaybackState.CustomAction customAction2 = customAction;
                transportControls.sendCustomAction(customAction2, customAction2.getExtras());
            }
        }, customAction.getName(), null, null, 16, null);
    }

    private final MediaAction getResumeMediaAction(Runnable runnable) {
        return new MediaAction(Icon.createWithResource(this.context, R$drawable.ic_media_play).setTint(this.themeText).loadDrawable(this.context), runnable, this.context.getString(R$string.controls_media_resume), this.context.getDrawable(R$drawable.ic_media_play_container), null, 16, null);
    }

    private final MediaAction getStandardAction(final MediaController mediaController, long j, long j2) {
        if (!includesAction(j, j2)) {
            return null;
        }
        if (j2 == 4) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_play), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.getStandardAction.1
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().play();
                }
            }, this.context.getString(R$string.controls_media_button_play), this.context.getDrawable(R$drawable.ic_media_play_container), null, 16, null);
        }
        if (j2 == 2) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.getStandardAction.2
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().pause();
                }
            }, this.context.getString(R$string.controls_media_button_pause), this.context.getDrawable(R$drawable.ic_media_pause_container), null, 16, null);
        }
        if (j2 == 16) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_prev), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.getStandardAction.3
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToPrevious();
                }
            }, this.context.getString(R$string.controls_media_button_prev), null, null, 16, null);
        }
        if (j2 == 32) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_next), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.getStandardAction.4
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToNext();
                }
            }, this.context.getString(R$string.controls_media_button_next), null, null, 16, null);
        }
        return null;
    }

    private final void handlePossibleRemoval(String str, MediaData mediaData, boolean z) {
        boolean z2 = mediaData.getToken() != null;
        if (z2 && mediaData.getSemanticActions() != null) {
            Log.d("MediaDataManager", "Notification removed but using session actions " + str);
            this.mediaEntries.put(str, mediaData);
            notifyMediaDataLoaded(str, str, mediaData);
            return;
        }
        if (!z && mediaData.getSemanticActions() == null) {
            Log.d("MediaDataManager", "Session destroyed but using notification actions " + str);
            this.mediaEntries.put(str, mediaData);
            notifyMediaDataLoaded(str, str, mediaData);
            return;
        }
        if (mediaData.getActive() && !isAbleToResume(mediaData)) {
            Log.d("MediaDataManager", "Removing still-active player " + str);
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            return;
        }
        if (!this.mediaFlags.isRetainingPlayersEnabled() && !isAbleToResume(mediaData)) {
            Log.d("MediaDataManager", "Removing player " + str);
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            return;
        }
        Log.d("MediaDataManager", "Notification (" + z + ") and/or session (" + z2 + ") gone for inactive player " + str);
        convertToResumePlayer(str, mediaData);
    }

    static /* synthetic */ void handlePossibleRemoval$default(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, MediaData mediaData, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        legacyMediaDataManagerImpl.handlePossibleRemoval(str, mediaData, z);
    }

    private final boolean includesAction(long j, long j2) {
        return ((j2 == 4 || j2 == 2) && (512 & j) > 0) || (j & j2) != 0;
    }

    private final boolean isAbleToResume(MediaData mediaData) {
        return this.useMediaResumption && mediaData.getResumeAction() != null && (mediaData.isLocalSession() || (this.mediaFlags.isRemoteResumeAllowed() && mediaData.getPlaybackLocation() != 2));
    }

    private final boolean isRemoteCastNotification(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().extras.containsKey("android.mediaRemoteDevice");
    }

    private final Bitmap loadBitmapFromUri(MediaMetadata mediaMetadata) {
        for (String str : LegacyMediaDataManagerImplKt.ART_URIS) {
            String string = mediaMetadata.getString(str);
            if (!TextUtils.isEmpty(string)) {
                Uri uri = Uri.parse(string);
                uri.getClass();
                Bitmap bitmapLoadBitmapFromUri = loadBitmapFromUri(uri);
                if (bitmapLoadBitmapFromUri != null) {
                    Log.d("MediaDataManager", "loaded art from " + str);
                    return bitmapLoadBitmapFromUri;
                }
            }
        }
        return null;
    }

    private final Bitmap loadBitmapFromUri(Uri uri) {
        if (uri.getScheme() == null) {
            return null;
        }
        if (!StringsKt.equals$default(uri.getScheme(), "content", false, 2, null) && !StringsKt.equals$default(uri.getScheme(), "android.resource", false, 2, null) && !StringsKt.equals$default(uri.getScheme(), "file", false, 2, null)) {
            return null;
        }
        ImageDecoder.Source sourceCreateSource = ImageDecoder.createSource(this.context.getContentResolver(), uri);
        sourceCreateSource.getClass();
        try {
            return ImageDecoder.decodeBitmap(sourceCreateSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.loadBitmapFromUri.1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    imageDecoder.getClass();
                    imageInfo.getClass();
                    source.getClass();
                    int width = imageInfo.getSize().getWidth();
                    int height = imageInfo.getSize().getHeight();
                    float scaleFactor = MediaDataUtils.getScaleFactor(new android.util.Pair(Integer.valueOf(width), Integer.valueOf(height)), new android.util.Pair(Integer.valueOf(LegacyMediaDataManagerImpl.this.artworkWidth), Integer.valueOf(LegacyMediaDataManagerImpl.this.artworkHeight)));
                    if (scaleFactor != 0.0f && scaleFactor < 1.0f) {
                        imageDecoder.setTargetSize((int) (width * scaleFactor), (int) (scaleFactor * height));
                    }
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (IOException e) {
            Log.e("MediaDataManager", "Unable to load bitmap", e);
            return null;
        } catch (RuntimeException e2) {
            Log.e("MediaDataManager", "Unable to load bitmap", e2);
            return null;
        }
    }

    private final Bitmap loadBitmapFromUriForUser(Uri uri, int i, int i2, String str) {
        try {
            UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i2, str, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, i));
            return loadBitmapFromUri(uri);
        } catch (SecurityException e) {
            Log.e("MediaDataManager", "Failed to get URI permission: " + e);
            return null;
        }
    }

    private final void loadMediaData(final String str, final StatusBarNotification statusBarNotification, final String str2, final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.loadMediaData.1
            @Override // java.lang.Runnable
            public final void run() {
                LegacyMediaDataManagerImpl.this.loadMediaDataInBg(str, statusBarNotification, str2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadMediaDataInBgForResumption(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, final String str2) {
        InstanceId newInstanceId;
        CharSequence title = mediaDescription.getTitle();
        if (title == null || StringsKt.isBlank(title)) {
            Log.e("MediaDataManager", "Description incomplete");
            this.mediaEntries.remove(str2);
            return;
        }
        Log.d("MediaDataManager", "adding track for " + i + " from browser: " + mediaDescription);
        MediaData mediaData = (MediaData) this.mediaEntries.get(str2);
        int appUid = mediaData != null ? mediaData.getAppUid() : -1;
        Bitmap iconBitmap = mediaDescription.getIconBitmap();
        if (iconBitmap == null && mediaDescription.getIconUri() != null) {
            Uri iconUri = mediaDescription.getIconUri();
            iconUri.getClass();
            iconBitmap = loadBitmapFromUriForUser(iconUri, i, appUid, str2);
        }
        final Icon iconCreateWithBitmap = iconBitmap != null ? Icon.createWithBitmap(iconBitmap) : null;
        if (mediaData == null || (newInstanceId = mediaData.getInstanceId()) == null) {
            newInstanceId = this.logger.getNewInstanceId();
        }
        final InstanceId instanceId = newInstanceId;
        Bundle extras = mediaDescription.getExtras();
        boolean z = false;
        if (extras != null && extras.getLong("android.media.IS_EXPLICIT") == 1) {
            z = true;
        }
        final boolean z2 = z;
        final Double descriptionProgress = this.mediaFlags.isResumeProgressEnabled() ? MediaDataUtils.getDescriptionProgress(mediaDescription.getExtras()) : null;
        final MediaAction resumeMediaAction = getResumeMediaAction(runnable);
        final long jElapsedRealtime = this.systemClock.elapsedRealtime();
        final long createdTimestampMillis = mediaData != null ? mediaData.getCreatedTimestampMillis() : 0L;
        final int i2 = appUid;
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.loadMediaDataInBgForResumption.1
            @Override // java.lang.Runnable
            public final void run() {
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                String str3 = str2;
                int i3 = i;
                String str4 = str;
                CharSequence subtitle = mediaDescription.getSubtitle();
                CharSequence title2 = mediaDescription.getTitle();
                Icon icon = iconCreateWithBitmap;
                List listListOf = CollectionsKt.listOf(resumeMediaAction);
                List listListOf2 = CollectionsKt.listOf((Object) 0);
                MediaButton mediaButton = new MediaButton(resumeMediaAction, null, null, null, null, false, false, 126, null);
                String str5 = str2;
                legacyMediaDataManagerImpl.onMediaDataLoaded(str3, null, new MediaData(i3, true, str4, null, subtitle, title2, icon, listListOf, listListOf2, mediaButton, str5, token, pendingIntent, null, false, runnable, 0, true, str5, true, null, false, jElapsedRealtime, createdTimestampMillis, instanceId, i2, z2, descriptionProgress, 3211264, null));
            }
        });
    }

    private final void logSingleVsMultipleMediaAdded(int i, String str, InstanceId instanceId) {
        if (this.mediaEntries.size() == 1) {
            this.logger.logSingleMediaPlayerInCarousel(i, str, instanceId);
        } else if (this.mediaEntries.size() == 2) {
            this.logger.logMultipleMediaPlayersInCarousel(i, str, instanceId);
        }
    }

    private final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, false, 24, null);
        }
    }

    private final void notifyMediaDataRemoved(String str) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str);
        }
    }

    private final void onSessionDestroyed(String str) {
        Log.d("MediaDataManager", "session destroyed for " + str);
        MediaData mediaData = (MediaData) this.mediaEntries.remove(str);
        if (mediaData == null) {
            return;
        }
        handlePossibleRemoval$default(this, str, MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268433407, null), false, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeAllForPackage(String str) {
        Assert.isMainThread();
        LinkedHashMap linkedHashMap = this.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).getPackageName(), str)) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (it.hasNext()) {
            removeEntry$default(this, (String) ((Map.Entry) it.next()).getKey(), false, 2, null);
        }
    }

    private final void removeEntry(String str, boolean z) {
        MediaData mediaData = (MediaData) this.mediaEntries.remove(str);
        if (mediaData != null && z) {
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
        }
        notifyMediaDataRemoved(str);
    }

    static /* synthetic */ void removeEntry$default(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        legacyMediaDataManagerImpl.removeEntry(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean sendPendingIntent(PendingIntent pendingIntent) {
        try {
            BroadcastOptions broadcastOptionsMakeBasic = BroadcastOptions.makeBasic();
            broadcastOptionsMakeBasic.getClass();
            broadcastOptionsMakeBasic.setInteractive(true);
            broadcastOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(broadcastOptionsMakeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaDataManager", "Intent canceled", e);
            return false;
        }
    }

    private final void updateState(String str, PlaybackState playbackState) {
        String str2;
        MediaData mediaDataCopy$default;
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            if (mediaData.getToken() == null) {
                Log.d("MediaDataManager", "State updated, but token was null");
                return;
            }
            String packageName = mediaData.getPackageName();
            MediaController mediaControllerCreate = this.mediaControllerFactory.create(mediaData.getToken());
            mediaControllerCreate.getClass();
            MediaButton mediaButtonCreateActionsFromState = createActionsFromState(packageName, mediaControllerCreate, new UserHandle(mediaData.getUserId()));
            if (mediaButtonCreateActionsFromState != null) {
                str2 = "MediaDataManager";
                mediaDataCopy$default = MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, mediaButtonCreateActionsFromState, null, null, null, null, false, null, 0, false, null, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, 0L, 0L, null, 0, false, null, 267386367, null);
            } else {
                str2 = "MediaDataManager";
                mediaDataCopy$default = MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, 0L, 0L, null, 0, false, null, 267386879, null);
            }
            Log.d(str2, "State updated outside of notification");
            onMediaDataLoaded(str, str, mediaDataCopy$default);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void addListener(MediaDataManager.Listener listener) {
        listener.getClass();
        this.mediaDataFilter.addListener(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void addResumptionControls(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, String str2) {
        int iIntValue;
        final String str3 = str2;
        mediaDescription.getClass();
        runnable.getClass();
        token.getClass();
        str.getClass();
        pendingIntent.getClass();
        str3.getClass();
        if (!this.mediaEntries.containsKey(str3)) {
            InstanceId newInstanceId = this.logger.getNewInstanceId();
            try {
                ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(str3, 0);
                Integer numValueOf = applicationInfo != null ? Integer.valueOf(applicationInfo.uid) : null;
                numValueOf.getClass();
                iIntValue = numValueOf.intValue();
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaDataManager", "Could not get app UID for " + str3, e);
                iIntValue = -1;
            }
            int i2 = iIntValue;
            str3 = str2;
            this.mediaEntries.put(str3, MediaData.copy$default(LegacyMediaDataManagerImplKt.LOADING, 0, false, null, null, null, null, null, null, null, null, str2, null, null, null, false, runnable, 0, false, null, true, null, false, 0L, this.systemClock.currentTimeMillis(), newInstanceId, i2, false, null, 209157119, null));
            logSingleVsMultipleMediaAdded(i2, str3, newInstanceId);
            this.logger.logResumeMediaAdded(i2, str3, newInstanceId);
        }
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.addResumptionControls.1
            @Override // java.lang.Runnable
            public final void run() {
                LegacyMediaDataManagerImpl.this.loadMediaDataInBgForResumption(i, mediaDescription, runnable, token, str, pendingIntent, str3);
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public boolean dismissMediaData(final String str, long j) {
        str.getClass();
        boolean z = this.mediaEntries.get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.dismissMediaData.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) LegacyMediaDataManagerImpl.this.mediaEntries.get(str);
                if (mediaData != null) {
                    LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                    if (!mediaData.isLocalSession() || (token = mediaData.getToken()) == null) {
                        return;
                    }
                    legacyMediaDataManagerImpl.mediaControllerFactory.create(token).getTransportControls().stop();
                }
            }
        });
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.dismissMediaData.2
            @Override // java.lang.Runnable
            public final void run() {
                LegacyMediaDataManagerImpl.removeEntry$default(LegacyMediaDataManagerImpl.this, str, false, 2, null);
            }
        }, j);
        return z;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public boolean hasActiveMediaOrRecommendation() {
        return this.mediaDataFilter.hasActiveMediaOrRecommendation();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public boolean hasAnyMediaOrRecommendation() {
        return this.mediaDataFilter.hasAnyMediaOrRecommendation();
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0254  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void loadMediaDataInBg(final java.lang.String r37, final android.service.notification.StatusBarNotification r38, final java.lang.String r39, boolean r40) {
        /*
            Method dump skipped, instruction units count: 701
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.loadMediaDataInBg(java.lang.String, android.service.notification.StatusBarNotification, java.lang.String, boolean):void");
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        str.getClass();
        mediaData.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaDataManager#onMediaDataLoaded");
        }
        try {
            Assert.isMainThread();
            if (this.mediaEntries.containsKey(str)) {
                this.mediaEntries.put(str, mediaData);
                notifyMediaDataLoaded(str, str2, mediaData);
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        str.getClass();
        statusBarNotification.getClass();
        if (!this.useQsMediaPlayer || !MediaDataManager.Companion.isMediaNotification(statusBarNotification)) {
            onNotificationRemoved(str);
            return;
        }
        Assert.isMainThread();
        String packageName = statusBarNotification.getPackageName();
        packageName.getClass();
        String strFindExistingEntry = findExistingEntry(str, packageName);
        boolean z = true;
        if (strFindExistingEntry == null) {
            InstanceId newInstanceId = this.logger.getNewInstanceId();
            MediaData mediaData = LegacyMediaDataManagerImplKt.LOADING;
            String packageName2 = statusBarNotification.getPackageName();
            packageName2.getClass();
            this.mediaEntries.put(str, MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, packageName2, null, null, null, false, null, 0, false, null, false, null, false, 0L, this.systemClock.currentTimeMillis(), newInstanceId, 0, false, null, 243268607, null));
        } else if (Intrinsics.areEqual(strFindExistingEntry, str)) {
            z = false;
        } else {
            Object objRemove = this.mediaEntries.remove(strFindExistingEntry);
            objRemove.getClass();
            this.mediaEntries.put(str, (MediaData) objRemove);
        }
        loadMediaData(str, statusBarNotification, strFindExistingEntry, z);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void onNotificationRemoved(String str) {
        str.getClass();
        Assert.isMainThread();
        MediaData mediaData = (MediaData) this.mediaEntries.remove(str);
        if (mediaData == null) {
            return;
        }
        if (isAbleToResume(mediaData)) {
            convertToResumePlayer(str, mediaData);
        } else if (this.mediaFlags.isRetainingPlayersEnabled()) {
            handlePossibleRemoval(str, mediaData, true);
        } else {
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void onSwipeToDismiss() {
        this.mediaDataFilter.onSwipeToDismiss();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void removeListener(MediaDataManager.Listener listener) {
        listener.getClass();
        this.mediaDataFilter.removeListener(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    /* JADX INFO: renamed from: setInactive */
    public void mo1360setInactive(String str, boolean z, boolean z2) {
        str.getClass();
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            if (z && !z2) {
                this.logger.logMediaTimeout(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            }
            if (mediaData.getActive() == (!z) && !z2) {
                if (mediaData.getResumption()) {
                    Log.d("MediaDataManager", "timing out resume player " + str);
                    dismissMediaData(str, 0L);
                    return;
                }
                return;
            }
            if (mediaData.getActive()) {
                mediaData.setLastActive(this.systemClock.elapsedRealtime());
            }
            mediaData.setActive(!z);
            Log.d("MediaDataManager", "Updating " + str + " timedOut: " + z);
            onMediaDataLoaded(str, str, mediaData);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void setResumeAction(String str, Runnable runnable) {
        str.getClass();
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            mediaData.setResumeAction(runnable);
            mediaData.setHasCheckedForResume(true);
        }
    }
}
