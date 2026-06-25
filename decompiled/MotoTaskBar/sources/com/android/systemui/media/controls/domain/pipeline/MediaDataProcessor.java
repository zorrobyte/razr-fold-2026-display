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
import android.os.Handler;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.row.HybridGroupManager;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: MediaDataProcessor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaDataProcessor implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final int MAX_COMPACT_ACTIONS = 3;
    public static final int MAX_NOTIFICATION_ACTIONS = 5;
    private final ActivityStarter activityStarter;
    private final MediaDataProcessor$appChangeReceiver$1 appChangeReceiver;
    private final CoroutineScope applicationScope;
    private final int artworkHeight;
    private final int artworkWidth;
    private final CoroutineDispatcher backgroundDispatcher;
    private final Executor backgroundExecutor;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private final DumpManager dumpManager;
    private final DelayableExecutor foregroundExecutor;
    private final Handler handler;
    private final Set internalListeners;
    private final MediaUiEventLogger logger;
    private final MediaControllerFactory mediaControllerFactory;
    private final MediaDataRepository mediaDataRepository;
    private final MediaFlags mediaFlags;
    private final StatusBarManager statusBarManager;
    private final SystemClock systemClock;
    private final int themeText;
    private final Executor uiExecutor;
    private boolean useMediaResumption;
    private final boolean useQsMediaPlayer;

    /* JADX INFO: compiled from: MediaDataProcessor.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: MediaDataProcessor.kt */
    public interface Listener {
        static /* synthetic */ void onMediaDataLoaded$default(Listener listener, String str, String str2, MediaData mediaData, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onMediaDataLoaded");
            }
            if ((i & 8) != 0) {
                z = true;
            }
            boolean z3 = z;
            if ((i & 16) != 0) {
                z2 = false;
            }
            listener.onMediaDataLoaded(str, str2, mediaData, z3, z2);
        }

        void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2);

        void onMediaDataRemoved(String str);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MediaDataProcessor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, Handler handler, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, ActivityStarter activityStarter, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, MediaDataRepository mediaDataRepository) {
        context.getClass();
        coroutineScope.getClass();
        coroutineDispatcher.getClass();
        threadFactory.getClass();
        executor.getClass();
        delayableExecutor.getClass();
        handler.getClass();
        mediaControllerFactory.getClass();
        dumpManager.getClass();
        broadcastDispatcher.getClass();
        activityStarter.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        mediaUiEventLogger.getClass();
        mediaDataRepository.getClass();
        Executor executorBuildExecutorOnNewThread = threadFactory.buildExecutorOnNewThread("MediaDataProcessor");
        executorBuildExecutorOnNewThread.getClass();
        this(context, coroutineScope, coroutineDispatcher, executorBuildExecutorOnNewThread, executor, delayableExecutor, handler, mediaControllerFactory, broadcastDispatcher, dumpManager, activityStarter, Utils.useMediaResumption(context), Utils.useQsMediaPlayer(context), systemClock, mediaFlags, mediaUiEventLogger, mediaDataRepository);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$appChangeReceiver$1] */
    public MediaDataProcessor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, Handler handler, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, ActivityStarter activityStarter, boolean z, boolean z2, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, MediaDataRepository mediaDataRepository) {
        context.getClass();
        coroutineScope.getClass();
        coroutineDispatcher.getClass();
        executor.getClass();
        executor2.getClass();
        delayableExecutor.getClass();
        handler.getClass();
        mediaControllerFactory.getClass();
        broadcastDispatcher.getClass();
        dumpManager.getClass();
        activityStarter.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        mediaUiEventLogger.getClass();
        mediaDataRepository.getClass();
        this.context = context;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.handler = handler;
        this.mediaControllerFactory = mediaControllerFactory;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dumpManager = dumpManager;
        this.activityStarter = activityStarter;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.mediaDataRepository = mediaDataRepository;
        this.themeText = com.android.settingslib.Utils.getColorAttr(context, R.attr.textColorPrimary).getDefaultColor();
        this.internalListeners = new LinkedHashSet();
        this.artworkWidth = context.getResources().getDimensionPixelSize(R.dimen.config_prefDialogWidth);
        this.artworkHeight = context.getResources().getDimensionPixelSize(R$dimen.qs_media_session_height_expanded);
        Object systemService = context.getSystemService("statusbar");
        systemService.getClass();
        this.statusBarManager = (StatusBarManager) systemService;
        this.appChangeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$appChangeReceiver$1
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
                        MediaDataProcessor mediaDataProcessor = this.this$0;
                        for (String str : stringArrayExtra) {
                            str.getClass();
                            mediaDataProcessor.removeAllForPackage(str);
                        }
                    }
                }
            }
        };
    }

    private final void convertToResumePlayer(String str, MediaData mediaData) {
        List listEmptyList;
        Log.d("MediaDataProcessor", "Converting " + str + " to resume");
        CharSequence song = mediaData.getSong();
        if (song == null || StringsKt.isBlank(song)) {
            Log.e("MediaDataProcessor", "Description incomplete");
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
        boolean z = this.mediaDataRepository.addMediaEntry(packageName, mediaDataCopy$default) == null;
        Log.d("MediaDataProcessor", "migrating? " + z + " from " + str + " -> " + packageName);
        if (z) {
            notifyMediaDataLoaded(packageName, str, mediaDataCopy$default);
        } else {
            notifyMediaDataRemoved(str);
            notifyMediaDataLoaded(packageName, packageName, mediaDataCopy$default);
        }
        this.logger.logActiveConvertedToResume(mediaDataCopy$default.getAppUid(), packageName, mediaDataCopy$default.getInstanceId());
        Map map = (Map) this.mediaDataRepository.getMediaEntries().getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (((MediaData) entry.getValue()).getResumption()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap.size();
        if (size > 5) {
            for (Pair pair : CollectionsKt.sortedWith(MapsKt.toList(linkedHashMap), new Comparator() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$convertToResumePlayer$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((MediaData) ((Pair) obj).component2()).getLastActive()), Long.valueOf(((MediaData) ((Pair) obj2).component2()).getLastActive()));
                }
            }).subList(0, size - 5)) {
                String str2 = (String) pair.component1();
                MediaData mediaData2 = (MediaData) pair.component2();
                Log.d("MediaDataProcessor", "Removing excess control " + str2);
                this.mediaDataRepository.removeMediaEntry(str2);
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
            Log.e("MediaDataProcessor", "Too many compact actions for " + statusBarNotification.getKey() + ",limiting to first " + i);
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
                    Log.w("MediaDataProcessor", "Too many notification actions for " + statusBarNotification.getKey() + ", limiting to first " + i2);
                    break;
                }
                if (action.getIcon() == null) {
                    Log.i("MediaDataProcessor", "No icon for action " + iComponent1 + " " + ((Object) action.title));
                    arrayList.remove(Integer.valueOf(iComponent1));
                } else {
                    Runnable runnable = action.actionIntent != null ? new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$createActionsFromNotification$runnable$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (action.actionIntent.isActivity()) {
                                this.activityStarter.startPendingIntentDismissingKeyguard(action.actionIntent);
                                return;
                            }
                            if (action.isAuthenticationRequired()) {
                                ActivityStarter activityStarter = this.activityStarter;
                                final MediaDataProcessor mediaDataProcessor = this;
                                final Notification.Action action2 = action;
                                activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$createActionsFromNotification$runnable$1.1
                                    @Override // com.android.systemui.displays.ActivityStarter.OnDismissAction
                                    public final boolean onDismiss() {
                                        MediaDataProcessor mediaDataProcessor2 = mediaDataProcessor;
                                        PendingIntent pendingIntent = action2.actionIntent;
                                        pendingIntent.getClass();
                                        return mediaDataProcessor2.sendPendingIntent(pendingIntent);
                                    }
                                }, new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$createActionsFromNotification$runnable$1.2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                    }
                                }, true);
                                return;
                            }
                            MediaDataProcessor mediaDataProcessor2 = this;
                            PendingIntent pendingIntent = action.actionIntent;
                            pendingIntent.getClass();
                            mediaDataProcessor2.sendPendingIntent(pendingIntent);
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
        final MediaDataProcessor mediaDataProcessor;
        final MediaController mediaController2;
        MediaAction standardAction;
        MediaAction mediaAction;
        MediaAction mediaActionCreateActionsFromState$nextCustomAction;
        PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null || !this.mediaFlags.areMediaSessionActionsEnabled(str, userHandle)) {
            return null;
        }
        if (NotificationMediaManager.isConnectingState(playbackState.getState())) {
            Drawable drawable = this.context.getDrawable(17304686);
            drawable.getClass();
            ((Animatable) drawable).start();
            mediaDataProcessor = this;
            standardAction = new MediaAction(drawable, null, this.context.getString(R$string.controls_media_button_connecting), this.context.getDrawable(R$drawable.ic_media_connecting_container), 17304686);
            mediaController2 = mediaController;
        } else if (NotificationMediaManager.isPlayingState(playbackState.getState())) {
            mediaDataProcessor = this;
            mediaController2 = mediaController;
            standardAction = mediaDataProcessor.getStandardAction(mediaController2, playbackState.getActions(), 2L);
        } else {
            mediaDataProcessor = this;
            mediaController2 = mediaController;
            standardAction = mediaDataProcessor.getStandardAction(mediaController2, playbackState.getActions(), 4L);
        }
        MediaAction standardAction2 = mediaDataProcessor.getStandardAction(mediaController2, playbackState.getActions(), 16L);
        MediaAction standardAction3 = mediaDataProcessor.getStandardAction(mediaController2, playbackState.getActions(), 32L);
        List<PlaybackState.CustomAction> customActions = playbackState.getCustomActions();
        customActions.getClass();
        Iterator it = SequencesKt.map(SequencesKt.filterNotNull(CollectionsKt.asSequence(customActions)), new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaDataProcessor.createActionsFromState$lambda$12(this.f$0, str, mediaController2, (PlaybackState.CustomAction) obj);
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
    public static final MediaAction createActionsFromState$lambda$12(MediaDataProcessor mediaDataProcessor, String str, MediaController mediaController, PlaybackState.CustomAction customAction) {
        customAction.getClass();
        return mediaDataProcessor.getCustomAction(str, mediaController, customAction);
    }

    private static final MediaAction createActionsFromState$nextCustomAction(Iterator it) {
        if (it.hasNext()) {
            return (MediaAction) it.next();
        }
        return null;
    }

    private final String findExistingEntry(String str, String str2) {
        Map map = (Map) this.mediaDataRepository.getMediaEntries().getValue();
        if (map.containsKey(str)) {
            return str;
        }
        if (map.containsKey(str2)) {
            return str2;
        }
        return null;
    }

    private final ApplicationInfo getAppInfoFromPackage(String str) {
        try {
            return this.context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("MediaDataProcessor", "Could not get app info for " + str, e);
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

    private final MediaAction getCustomAction(String str, final MediaController mediaController, final PlaybackState.CustomAction customAction) {
        return new MediaAction(Icon.createWithResource(str, customAction.getIcon()).loadDrawable(this.context), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.getCustomAction.1
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
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_play), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.getStandardAction.1
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().play();
                }
            }, this.context.getString(R$string.controls_media_button_play), this.context.getDrawable(R$drawable.ic_media_play_container), null, 16, null);
        }
        if (j2 == 2) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.getStandardAction.2
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().pause();
                }
            }, this.context.getString(R$string.controls_media_button_pause), this.context.getDrawable(R$drawable.ic_media_pause_container), null, 16, null);
        }
        if (j2 == 16) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_prev), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.getStandardAction.3
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToPrevious();
                }
            }, this.context.getString(R$string.controls_media_button_prev), null, null, 16, null);
        }
        if (j2 == 32) {
            return new MediaAction(this.context.getDrawable(R$drawable.ic_media_next), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.getStandardAction.4
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
            Log.d("MediaDataProcessor", "Notification removed but using session actions " + str);
            this.mediaDataRepository.addMediaEntry(str, mediaData);
            notifyMediaDataLoaded(str, str, mediaData);
            return;
        }
        if (!z && mediaData.getSemanticActions() == null) {
            Log.d("MediaDataProcessor", "Session destroyed but using notification actions " + str);
            this.mediaDataRepository.addMediaEntry(str, mediaData);
            notifyMediaDataLoaded(str, str, mediaData);
            return;
        }
        if (mediaData.getActive() && !isAbleToResume(mediaData)) {
            Log.d("MediaDataProcessor", "Removing still-active player " + str);
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            return;
        }
        if (!this.mediaFlags.isRetainingPlayersEnabled() && !isAbleToResume(mediaData)) {
            Log.d("MediaDataProcessor", "Removing player " + str);
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            return;
        }
        Log.d("MediaDataProcessor", "Notification (" + z + ") and/or session (" + z2 + ") gone for inactive player " + str);
        convertToResumePlayer(str, mediaData);
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
        for (String str : MediaDataProcessorKt.ART_URIS) {
            String string = mediaMetadata.getString(str);
            if (!TextUtils.isEmpty(string)) {
                Uri uri = Uri.parse(string);
                uri.getClass();
                Bitmap bitmapLoadBitmapFromUri = loadBitmapFromUri(uri);
                if (bitmapLoadBitmapFromUri != null) {
                    Log.d("MediaDataProcessor", "loaded art from " + str);
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
            return ImageDecoder.decodeBitmap(sourceCreateSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.loadBitmapFromUri.1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    imageDecoder.getClass();
                    imageInfo.getClass();
                    source.getClass();
                    int width = imageInfo.getSize().getWidth();
                    int height = imageInfo.getSize().getHeight();
                    float scaleFactor = MediaDataUtils.getScaleFactor(new android.util.Pair(Integer.valueOf(width), Integer.valueOf(height)), new android.util.Pair(Integer.valueOf(MediaDataProcessor.this.artworkWidth), Integer.valueOf(MediaDataProcessor.this.artworkHeight)));
                    if (scaleFactor != 0.0f && scaleFactor < 1.0f) {
                        imageDecoder.setTargetSize((int) (width * scaleFactor), (int) (scaleFactor * height));
                    }
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (IOException e) {
            Log.e("MediaDataProcessor", "Unable to load bitmap", e);
            return null;
        } catch (RuntimeException e2) {
            Log.e("MediaDataProcessor", "Unable to load bitmap", e2);
            return null;
        }
    }

    private final Bitmap loadBitmapFromUriForUser(Uri uri, int i, int i2, String str) {
        try {
            UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i2, str, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, i));
            return loadBitmapFromUri(uri);
        } catch (SecurityException e) {
            Log.e("MediaDataProcessor", "Failed to get URI permission: " + e);
            return null;
        }
    }

    private final void loadMediaData(final String str, final StatusBarNotification statusBarNotification, final String str2, final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.loadMediaData.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataProcessor.this.loadMediaDataInBg(str, statusBarNotification, str2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadMediaDataInBgForResumption(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, final String str2) {
        InstanceId newInstanceId;
        CharSequence title = mediaDescription.getTitle();
        if (title == null || StringsKt.isBlank(title)) {
            Log.e("MediaDataProcessor", "Description incomplete");
            this.mediaDataRepository.removeMediaEntry(str2);
            return;
        }
        Log.d("MediaDataProcessor", "adding track for " + i + " from browser: " + mediaDescription);
        MediaData mediaData = (MediaData) ((Map) this.mediaDataRepository.getMediaEntries().getValue()).get(str2);
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
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.loadMediaDataInBgForResumption.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataProcessor mediaDataProcessor = MediaDataProcessor.this;
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
                boolean z3 = true;
                Icon icon2 = null;
                MediaDeviceData mediaDeviceData = null;
                boolean z4 = false;
                mediaDataProcessor.onMediaDataLoaded(str3, null, new MediaData(i3, z3, str4, icon2, subtitle, title2, icon, listListOf, listListOf2, mediaButton, str5, token, pendingIntent, mediaDeviceData, z4, runnable, 0, true, str5, true, null, false, jElapsedRealtime, createdTimestampMillis, instanceId, i2, z2, descriptionProgress, 3211264, null));
            }
        });
    }

    private final void logSingleVsMultipleMediaAdded(int i, String str, InstanceId instanceId) {
        if (((Map) this.mediaDataRepository.getMediaEntries().getValue()).size() == 1) {
            this.logger.logSingleMediaPlayerInCarousel(i, str, instanceId);
        } else if (((Map) this.mediaDataRepository.getMediaEntries().getValue()).size() == 2) {
            this.logger.logMultipleMediaPlayersInCarousel(i, str, instanceId);
        }
    }

    private final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            Listener.onMediaDataLoaded$default((Listener) it.next(), str, str2, mediaData, false, false, 24, null);
        }
    }

    private final void notifyMediaDataRemoved(String str) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onMediaDataRemoved(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeAllForPackage(String str) {
        Assert.isMainThread();
        Map map = (Map) this.mediaDataRepository.getMediaEntries().getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).getPackageName(), str)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            removeEntry$default(this, (String) ((Map.Entry) it.next()).getKey(), false, 2, null);
        }
    }

    private final void removeEntry(String str, boolean z) {
        MediaData mediaDataRemoveMediaEntry = this.mediaDataRepository.removeMediaEntry(str);
        if (mediaDataRemoveMediaEntry != null && z) {
            this.logger.logMediaRemoved(mediaDataRemoveMediaEntry.getAppUid(), mediaDataRemoveMediaEntry.getPackageName(), mediaDataRemoveMediaEntry.getInstanceId());
        }
        notifyMediaDataRemoved(str);
    }

    static /* synthetic */ void removeEntry$default(MediaDataProcessor mediaDataProcessor, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        mediaDataProcessor.removeEntry(str, z);
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
            Log.d("MediaDataProcessor", "Intent canceled", e);
            return false;
        }
    }

    public final void addResumptionControls(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, String str2) {
        int i2;
        final String str3 = str2;
        mediaDescription.getClass();
        runnable.getClass();
        token.getClass();
        str.getClass();
        pendingIntent.getClass();
        str3.getClass();
        if (!((Map) this.mediaDataRepository.getMediaEntries().getValue()).containsKey(str3)) {
            InstanceId newInstanceId = this.logger.getNewInstanceId();
            try {
                i2 = this.context.getPackageManager().getApplicationInfo(str3, 0).uid;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaDataProcessor", "Could not get app UID for " + str3, e);
                i2 = -1;
            }
            int i3 = i2;
            str3 = str2;
            this.mediaDataRepository.addMediaEntry(str3, MediaData.copy$default(new MediaData(0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268435455, null), 0, false, null, null, null, null, null, null, null, null, str2, null, null, null, false, runnable, 0, false, null, true, null, false, 0L, this.systemClock.currentTimeMillis(), newInstanceId, i3, false, null, 209157119, null));
            logSingleVsMultipleMediaAdded(i3, str3, newInstanceId);
            this.logger.logResumeMediaAdded(i3, str3, newInstanceId);
        }
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.addResumptionControls.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataProcessor.this.loadMediaDataInBgForResumption(i, mediaDescription, runnable, token, str, pendingIntent, str3);
            }
        });
    }

    public final boolean dismissMediaData(InstanceId instanceId, long j) {
        instanceId.getClass();
        Map map = (Map) this.mediaDataRepository.getMediaEntries().getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).getInstanceId(), instanceId)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        if (linkedHashMap.isEmpty()) {
            return false;
        }
        return dismissMediaData((String) CollectionsKt.first(linkedHashMap.keySet()), j);
    }

    public final boolean dismissMediaData(final String str, long j) {
        str.getClass();
        boolean z = ((Map) this.mediaDataRepository.getMediaEntries().getValue()).get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.dismissMediaData.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) ((Map) MediaDataProcessor.this.mediaDataRepository.getMediaEntries().getValue()).get(str);
                if (mediaData != null) {
                    MediaDataProcessor mediaDataProcessor = MediaDataProcessor.this;
                    if (!mediaData.isLocalSession() || (token = mediaData.getToken()) == null) {
                        return;
                    }
                    mediaDataProcessor.mediaControllerFactory.create(token).getTransportControls().stop();
                }
            }
        });
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.dismissMediaData.2
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataProcessor.removeEntry$default(MediaDataProcessor.this, str, false, 2, null);
            }
        }, j);
        return z;
    }

    public final void loadMediaDataInBg(final String str, final StatusBarNotification statusBarNotification, final String str2, boolean z) {
        String str3;
        Ref$ObjectRef ref$ObjectRef;
        Icon icon;
        int i;
        int i2;
        int i3;
        InstanceId newInstanceId;
        MediaData mediaData;
        str.getClass();
        statusBarNotification.getClass();
        final MediaSession.Token token = (MediaSession.Token) statusBarNotification.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class);
        if (token == null) {
            return;
        }
        MediaController mediaControllerCreate = this.mediaControllerFactory.create(token);
        MediaMetadata metadata = mediaControllerCreate.getMetadata();
        final Notification notification = statusBarNotification.getNotification();
        notification.getClass();
        ApplicationInfo appInfoFromPackage = (ApplicationInfo) notification.extras.getParcelable("android.appInfo", ApplicationInfo.class);
        if (appInfoFromPackage == null) {
            String packageName = statusBarNotification.getPackageName();
            packageName.getClass();
            appInfoFromPackage = getAppInfoFromPackage(packageName);
        }
        String appName = getAppName(statusBarNotification, appInfoFromPackage);
        Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        String string = metadata != null ? metadata.getString("android.media.metadata.DISPLAY_TITLE") : null;
        ref$ObjectRef2.element = string;
        String str4 = string;
        if (str4 == null || StringsKt.isBlank(str4)) {
            ref$ObjectRef2.element = metadata != null ? metadata.getString("android.media.metadata.TITLE") : null;
        }
        CharSequence charSequence = (CharSequence) ref$ObjectRef2.element;
        if (charSequence == null || StringsKt.isBlank(charSequence)) {
            ref$ObjectRef2.element = HybridGroupManager.resolveTitle(notification);
        }
        CharSequence charSequence2 = (CharSequence) ref$ObjectRef2.element;
        if (charSequence2 == null || StringsKt.isBlank(charSequence2)) {
            ref$ObjectRef2.element = this.context.getString(R$string.controls_media_empty_title, appName);
            try {
                this.statusBarManager.logBlankMediaTitle(statusBarNotification.getPackageName(), statusBarNotification.getUser().getIdentifier());
            } catch (RuntimeException unused) {
                Log.e("MediaDataProcessor", "Error reporting blank media title for package " + statusBarNotification.getPackageName());
            }
        }
        Bitmap bitmapLoadBitmapFromUri = metadata != null ? loadBitmapFromUri(metadata) : null;
        if (bitmapLoadBitmapFromUri == null) {
            bitmapLoadBitmapFromUri = metadata != null ? metadata.getBitmap("android.media.metadata.ART") : null;
        }
        if (bitmapLoadBitmapFromUri == null) {
            bitmapLoadBitmapFromUri = metadata != null ? metadata.getBitmap("android.media.metadata.ALBUM_ART") : null;
        }
        Icon largeIcon = bitmapLoadBitmapFromUri == null ? notification.getLargeIcon() : Icon.createWithBitmap(bitmapLoadBitmapFromUri);
        final Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
        MediaMetadataCompat mediaMetadataCompatFromMediaMetadata = MediaMetadataCompat.fromMediaMetadata(metadata);
        final boolean z2 = mediaMetadataCompatFromMediaMetadata != null && mediaMetadataCompatFromMediaMetadata.getLong("android.media.IS_EXPLICIT") == 1;
        final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        String string2 = metadata != null ? metadata.getString("android.media.metadata.ARTIST") : null;
        ref$ObjectRef3.element = string2;
        String str5 = string2;
        if (str5 == null || StringsKt.isBlank(str5)) {
            ref$ObjectRef3.element = HybridGroupManager.resolveText(notification);
        }
        final Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
        if (isRemoteCastNotification(statusBarNotification)) {
            Bundle bundle = statusBarNotification.getNotification().extras;
            str3 = appName;
            CharSequence charSequence3 = bundle.getCharSequence("android.mediaRemoteDevice", null);
            ref$ObjectRef = ref$ObjectRef2;
            int i4 = bundle.getInt("android.mediaRemoteIcon", -1);
            icon = largeIcon;
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
            Log.d("MediaDataProcessor", str + " is RCN for " + ((Object) charSequence3));
            i = -1;
            if (charSequence3 != null && i4 > -1) {
                ref$ObjectRef4.element = new MediaDeviceData(pendingIntent != null && pendingIntent.isActivity(), Icon.createWithResource(statusBarNotification.getPackageName(), i4).loadDrawable(statusBarNotification.getPackageContext(this.context)), charSequence3, pendingIntent, null, false, 16, null);
            }
        } else {
            str3 = appName;
            ref$ObjectRef = ref$ObjectRef2;
            icon = largeIcon;
            i = -1;
        }
        final Ref$ObjectRef ref$ObjectRef5 = new Ref$ObjectRef();
        ref$ObjectRef5.element = CollectionsKt.emptyList();
        final Ref$ObjectRef ref$ObjectRef6 = new Ref$ObjectRef();
        ref$ObjectRef6.element = CollectionsKt.emptyList();
        String packageName2 = statusBarNotification.getPackageName();
        packageName2.getClass();
        UserHandle user = statusBarNotification.getUser();
        user.getClass();
        final MediaButton mediaButtonCreateActionsFromState = createActionsFromState(packageName2, mediaControllerCreate, user);
        if (mediaButtonCreateActionsFromState == null) {
            Pair pairCreateActionsFromNotification = createActionsFromNotification(statusBarNotification);
            ref$ObjectRef5.element = pairCreateActionsFromNotification.getFirst();
            ref$ObjectRef6.element = pairCreateActionsFromNotification.getSecond();
        }
        if (isRemoteCastNotification(statusBarNotification)) {
            i3 = 2;
        } else {
            MediaController.PlaybackInfo playbackInfo = mediaControllerCreate.getPlaybackInfo();
            if (playbackInfo != null) {
                i2 = 1;
                if (playbackInfo.getPlaybackType() == 1) {
                    i3 = 0;
                }
            } else {
                i2 = 1;
            }
            i3 = i2;
        }
        PlaybackState playbackState = mediaControllerCreate.getPlaybackState();
        final Boolean boolValueOf = playbackState != null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())) : null;
        MediaData mediaData2 = (MediaData) ((Map) this.mediaDataRepository.getMediaEntries().getValue()).get(str);
        if (mediaData2 == null || (newInstanceId = mediaData2.getInstanceId()) == null) {
            newInstanceId = this.logger.getNewInstanceId();
        }
        int i5 = appInfoFromPackage != null ? appInfoFromPackage.uid : i;
        if (z) {
            String packageName3 = statusBarNotification.getPackageName();
            packageName3.getClass();
            logSingleVsMultipleMediaAdded(i5, packageName3, newInstanceId);
            MediaUiEventLogger mediaUiEventLogger = this.logger;
            mediaData = mediaData2;
            String packageName4 = statusBarNotification.getPackageName();
            packageName4.getClass();
            mediaUiEventLogger.logActiveMediaAdded(i5, packageName4, newInstanceId, i3);
        } else {
            mediaData = mediaData2;
            if (mediaData == null || i3 != mediaData.getPlaybackLocation()) {
                MediaUiEventLogger mediaUiEventLogger2 = this.logger;
                String packageName5 = statusBarNotification.getPackageName();
                packageName5.getClass();
                mediaUiEventLogger2.logPlaybackLocationChange(i5, packageName5, newInstanceId, i3);
            }
        }
        final long jElapsedRealtime = this.systemClock.elapsedRealtime();
        long createdTimestampMillis = mediaData != null ? mediaData.getCreatedTimestampMillis() : 0L;
        final Icon icon2 = icon;
        final InstanceId instanceId = newInstanceId;
        final int i6 = i5;
        final int i7 = i3;
        final String str6 = str3;
        final Ref$ObjectRef ref$ObjectRef7 = ref$ObjectRef;
        final long j = createdTimestampMillis;
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.loadMediaDataInBg.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaData mediaData3 = (MediaData) ((Map) MediaDataProcessor.this.mediaDataRepository.getMediaEntries().getValue()).get(str);
                Runnable resumeAction = mediaData3 != null ? mediaData3.getResumeAction() : null;
                MediaData mediaData4 = (MediaData) ((Map) MediaDataProcessor.this.mediaDataRepository.getMediaEntries().getValue()).get(str);
                boolean z3 = mediaData4 != null && mediaData4.getHasCheckedForResume();
                MediaData mediaData5 = (MediaData) ((Map) MediaDataProcessor.this.mediaDataRepository.getMediaEntries().getValue()).get(str);
                boolean active = mediaData5 != null ? mediaData5.getActive() : true;
                MediaDataProcessor mediaDataProcessor = MediaDataProcessor.this;
                String str7 = str;
                String str8 = str2;
                int normalizedUserId = statusBarNotification.getNormalizedUserId();
                String str9 = str6;
                Icon icon3 = smallIcon;
                CharSequence charSequence4 = (CharSequence) ref$ObjectRef3.element;
                CharSequence charSequence5 = (CharSequence) ref$ObjectRef7.element;
                Icon icon4 = icon2;
                List list = (List) ref$ObjectRef5.element;
                List list2 = (List) ref$ObjectRef6.element;
                MediaButton mediaButton = mediaButtonCreateActionsFromState;
                String packageName6 = statusBarNotification.getPackageName();
                packageName6.getClass();
                mediaDataProcessor.onMediaDataLoaded(str7, str8, new MediaData(normalizedUserId, true, str9, icon3, charSequence4, charSequence5, icon4, list, list2, mediaButton, packageName6, token, notification.contentIntent, (MediaDeviceData) ref$ObjectRef4.element, active, resumeAction, i7, false, str, z3, boolValueOf, !statusBarNotification.isOngoing(), jElapsedRealtime, j, instanceId, i6, z2, null, 134348800, null));
            }
        });
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        str.getClass();
        mediaData.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaDataProcessor#onMediaDataLoaded");
        }
        try {
            Assert.isMainThread();
            if (((Map) this.mediaDataRepository.getMediaEntries().getValue()).containsKey(str)) {
                this.mediaDataRepository.addMediaEntry(str, mediaData);
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

    public final void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
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
            MediaData mediaData = new MediaData(0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268435455, null);
            String packageName2 = statusBarNotification.getPackageName();
            packageName2.getClass();
            this.mediaDataRepository.addMediaEntry(str, MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, packageName2, null, null, null, false, null, 0, false, null, false, null, false, 0L, this.systemClock.currentTimeMillis(), newInstanceId, 0, false, null, 243268607, null));
        } else if (Intrinsics.areEqual(strFindExistingEntry, str)) {
            z = false;
        } else {
            MediaData mediaDataRemoveMediaEntry = this.mediaDataRepository.removeMediaEntry(strFindExistingEntry);
            mediaDataRemoveMediaEntry.getClass();
            this.mediaDataRepository.addMediaEntry(str, mediaDataRemoveMediaEntry);
        }
        loadMediaData(str, statusBarNotification, strFindExistingEntry, z);
    }

    public final void onNotificationRemoved(String str) {
        str.getClass();
        Assert.isMainThread();
        MediaData mediaDataRemoveMediaEntry = this.mediaDataRepository.removeMediaEntry(str);
        if (mediaDataRemoveMediaEntry == null) {
            return;
        }
        if (isAbleToResume(mediaDataRemoveMediaEntry)) {
            convertToResumePlayer(str, mediaDataRemoveMediaEntry);
        } else if (this.mediaFlags.isRetainingPlayersEnabled()) {
            handlePossibleRemoval(str, mediaDataRemoveMediaEntry, true);
        } else {
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(mediaDataRemoveMediaEntry.getAppUid(), mediaDataRemoveMediaEntry.getPackageName(), mediaDataRemoveMediaEntry.getInstanceId());
        }
    }

    public final void setInactive(String str, boolean z, boolean z2) {
        str.getClass();
        MediaData mediaData = (MediaData) ((Map) this.mediaDataRepository.getMediaEntries().getValue()).get(str);
        if (mediaData != null) {
            if (z && !z2) {
                this.logger.logMediaTimeout(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            }
            if (mediaData.getActive() == (!z) && !z2) {
                if (mediaData.getResumption()) {
                    Log.d("MediaDataProcessor", "timing out resume player " + str);
                    dismissMediaData(str, 0L);
                    return;
                }
                return;
            }
            if (mediaData.getActive()) {
                mediaData.setLastActive(this.systemClock.elapsedRealtime());
            }
            mediaData.setActive(!z);
            Log.d("MediaDataProcessor", "Updating " + str + " timedOut: " + z);
            onMediaDataLoaded(str, str, mediaData);
        }
    }

    public final void setResumeAction(String str, Runnable runnable) {
        str.getClass();
        MediaData mediaData = (MediaData) ((Map) this.mediaDataRepository.getMediaEntries().getValue()).get(str);
        if (mediaData != null) {
            mediaData.setResumeAction(runnable);
            mediaData.setHasCheckedForResume(true);
        }
    }
}
