package com.android.systemui.media.controls.domain.resume;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListenerKt;
import com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Utils;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MediaResumeListener.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaResumeListener implements MediaDataManager.Listener, Dumpable {
    private final Executor backgroundExecutor;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private int currentUserId;
    private final Executor mainExecutor;
    private ResumeMediaBrowser mediaBrowser;
    private final MediaResumeListener$mediaBrowserCallback$1 mediaBrowserCallback;
    private final ResumeMediaBrowserFactory mediaBrowserFactory;
    private MediaDataManager mediaDataManager;
    private final MediaFlags mediaFlags;
    private final ConcurrentLinkedQueue resumeComponents;
    private final SystemClock systemClock;
    private boolean useMediaResumption;
    private final UserTracker userTracker;
    private final MediaResumeListener$userTrackerCallback$1 userTrackerCallback;
    private final BroadcastReceiver userUnlockReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.media.controls.domain.resume.MediaResumeListener$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    /* JADX WARN: Type inference failed for: r12v1, types: [com.android.systemui.media.controls.domain.resume.MediaResumeListener$mediaBrowserCallback$1] */
    public MediaResumeListener(Context context, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, Executor executor, Executor executor2, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock, MediaFlags mediaFlags) {
        context.getClass();
        broadcastDispatcher.getClass();
        userTracker.getClass();
        executor.getClass();
        executor2.getClass();
        resumeMediaBrowserFactory.getClass();
        dumpManager.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.userTracker = userTracker;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.mediaBrowserFactory = resumeMediaBrowserFactory;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.useMediaResumption = Utils.useMediaResumption(context);
        this.resumeComponents = new ConcurrentLinkedQueue();
        this.currentUserId = context.getUserId();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$userUnlockReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                context2.getClass();
                intent.getClass();
                if (Intrinsics.areEqual("android.intent.action.USER_UNLOCKED", intent.getAction()) && intent.getIntExtra("android.intent.extra.user_handle", -1) == this.this$0.currentUserId) {
                    this.this$0.loadMediaResumptionControls();
                }
            }
        };
        this.userUnlockReceiver = broadcastReceiver;
        ?? r11 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanged(int i, Context context2) {
                context2.getClass();
                this.this$0.currentUserId = i;
                this.this$0.loadSavedComponents();
            }
        };
        this.userTrackerCallback = r11;
        this.mediaBrowserCallback = new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$mediaBrowserCallback$1
            @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
            public void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
                mediaDescription.getClass();
                componentName.getClass();
                resumeMediaBrowser.getClass();
                MediaSession.Token token = resumeMediaBrowser.getToken();
                PendingIntent appIntent = resumeMediaBrowser.getAppIntent();
                PackageManager packageManager = this.this$0.context.getPackageManager();
                CharSequence packageName = componentName.getPackageName();
                packageName.getClass();
                Runnable resumeAction = this.this$0.getResumeAction(componentName);
                try {
                    packageName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(componentName.getPackageName(), 0));
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("MediaResumeListener", "Error getting package information", e);
                }
                Log.d("MediaResumeListener", "Adding resume controls for " + resumeMediaBrowser.getUserId() + ": " + mediaDescription);
                MediaDataManager mediaDataManager = this.this$0.mediaDataManager;
                if (mediaDataManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
                    mediaDataManager = null;
                }
                int userId = resumeMediaBrowser.getUserId();
                token.getClass();
                String string = packageName.toString();
                appIntent.getClass();
                String packageName2 = componentName.getPackageName();
                packageName2.getClass();
                mediaDataManager.addResumptionControls(userId, mediaDescription, resumeAction, token, string, appIntent, packageName2);
            }
        };
        if (this.useMediaResumption) {
            DumpManager.registerDumpable$default(dumpManager, "MediaResumeListener", this, null, 4, null);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            UserHandle userHandle = UserHandle.ALL;
            userHandle.getClass();
            broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, null, userHandle);
            userTracker.addCallback(r11, executor);
            loadSavedComponents();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Runnable getResumeAction(final ComponentName componentName) {
        return new Runnable() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener.getResumeAction.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                mediaResumeListener.setMediaBrowser(mediaResumeListener.mediaBrowserFactory.create(null, componentName, MediaResumeListener.this.currentUserId));
                ResumeMediaBrowser resumeMediaBrowser = MediaResumeListener.this.mediaBrowser;
                if (resumeMediaBrowser != null) {
                    resumeMediaBrowser.restart();
                }
            }
        };
    }

    public static /* synthetic */ void getUserUnlockReceiver$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadMediaResumptionControls() {
        if (this.useMediaResumption) {
            PackageManager packageManager = this.context.getPackageManager();
            long jCurrentTimeMillis = this.systemClock.currentTimeMillis();
            for (Pair pair : this.resumeComponents) {
                if (jCurrentTimeMillis - ((Number) pair.getSecond()).longValue() <= MediaTimeoutListenerKt.getRESUME_MEDIA_TIMEOUT()) {
                    Intent intent = new Intent("android.media.browse.MediaBrowserService");
                    intent.setComponent((ComponentName) pair.getFirst());
                    if (packageManager.resolveServiceAsUser(intent, 0, this.currentUserId) != null) {
                        this.mediaBrowserFactory.create(this.mediaBrowserCallback, (ComponentName) pair.getFirst(), this.currentUserId).findRecentMedia();
                    } else {
                        Log.d("MediaResumeListener", "User " + this.currentUserId + " does not have component " + pair.getFirst());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadSavedComponents() {
        long jCurrentTimeMillis;
        List listSplit;
        this.resumeComponents.clear();
        boolean z = false;
        List listEmptyList = null;
        String string = this.context.getSharedPreferences("media_control_prefs", 0).getString("browser_components_" + this.currentUserId, null);
        if (string != null && (listSplit = new Regex(":").split(string, 0)) != null) {
            if (listSplit.isEmpty()) {
                listEmptyList = CollectionsKt.emptyList();
            } else {
                ListIterator listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                listEmptyList = CollectionsKt.emptyList();
            }
        }
        if (listEmptyList != null) {
            Iterator it = listEmptyList.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                List listSplit$default = StringsKt.split$default((CharSequence) it.next(), new String[]{"/"}, false, 0, 6, (Object) null);
                ComponentName componentName = new ComponentName((String) listSplit$default.get(0), (String) listSplit$default.get(1));
                if (listSplit$default.size() == 3) {
                    try {
                        jCurrentTimeMillis = Long.parseLong((String) listSplit$default.get(2));
                    } catch (NumberFormatException unused) {
                        jCurrentTimeMillis = this.systemClock.currentTimeMillis();
                        z2 = true;
                    }
                    this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(jCurrentTimeMillis)));
                } else {
                    jCurrentTimeMillis = this.systemClock.currentTimeMillis();
                }
                z2 = true;
                this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(jCurrentTimeMillis)));
            }
            z = z2;
        }
        int i = this.currentUserId;
        String string2 = Arrays.toString(this.resumeComponents.toArray());
        string2.getClass();
        Log.d("MediaResumeListener", "loaded resume components for " + i + ": " + string2);
        if (z) {
            writeSharedPrefs();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setMediaBrowser(ResumeMediaBrowser resumeMediaBrowser) {
        ResumeMediaBrowser resumeMediaBrowser2 = this.mediaBrowser;
        if (resumeMediaBrowser2 != null) {
            resumeMediaBrowser2.disconnect();
        }
        this.mediaBrowser = resumeMediaBrowser;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tryUpdateResumptionList(final String str, final ComponentName componentName) {
        Log.d("MediaResumeListener", "Testing if we can connect to " + componentName);
        setMediaBrowser(this.mediaBrowserFactory.create(new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener.tryUpdateResumptionList.1
            @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
            public void addTrack(MediaDescription mediaDescription, ComponentName componentName2, ResumeMediaBrowser resumeMediaBrowser) {
                mediaDescription.getClass();
                componentName2.getClass();
                resumeMediaBrowser.getClass();
                Log.d("MediaResumeListener", "Can get resumable media for " + resumeMediaBrowser.getUserId() + " from " + componentName);
                MediaDataManager mediaDataManager = this.mediaDataManager;
                if (mediaDataManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
                    mediaDataManager = null;
                }
                mediaDataManager.setResumeAction(str, this.getResumeAction(componentName));
                this.updateResumptionList(componentName);
                this.setMediaBrowser(null);
            }

            @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
            public void onConnected() {
                Log.d("MediaResumeListener", "Connected to " + componentName);
            }

            @Override // com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.Callback
            public void onError() {
                Log.e("MediaResumeListener", "Cannot resume with " + componentName);
                this.setMediaBrowser(null);
            }
        }, componentName, this.currentUserId));
        ResumeMediaBrowser resumeMediaBrowser = this.mediaBrowser;
        if (resumeMediaBrowser != null) {
            resumeMediaBrowser.testConnection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateResumptionList(ComponentName componentName) {
        Object next;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.resumeComponents;
        Iterator it = concurrentLinkedQueue.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((ComponentName) ((Pair) next).getFirst()).equals(componentName)) {
                    break;
                }
            }
        }
        concurrentLinkedQueue.remove(next);
        this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(this.systemClock.currentTimeMillis())));
        if (this.resumeComponents.size() > 5) {
            this.resumeComponents.remove();
        }
        writeSharedPrefs();
    }

    private final void writeSharedPrefs() {
        StringBuilder sb = new StringBuilder();
        for (Pair pair : this.resumeComponents) {
            sb.append(((ComponentName) pair.getFirst()).flattenToString());
            sb.append("/");
            sb.append(((Number) pair.getSecond()).longValue());
            sb.append(":");
        }
        this.context.getSharedPreferences("media_control_prefs", 0).edit().putString("browser_components_" + this.currentUserId, sb.toString()).apply();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        str.getClass();
        mediaData.getClass();
        if (this.useMediaResumption) {
            if (!str.equals(str2)) {
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener.onMediaDataLoaded.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaResumeListener.this.setMediaBrowser(null);
                    }
                });
            }
            boolean z3 = mediaData.isLocalSession() || (this.mediaFlags.isRemoteResumeAllowed() && mediaData.getPlaybackLocation() != 2);
            if (mediaData.getResumeAction() == null && !mediaData.getHasCheckedForResume() && z3) {
                MediaDataManager mediaDataManager = this.mediaDataManager;
                if (mediaDataManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
                    mediaDataManager = null;
                }
                mediaDataManager.setResumeAction(str, null);
                Log.d("MediaResumeListener", "Checking for service component for " + mediaData.getPackageName());
                List listQueryIntentServicesAsUser = this.context.getPackageManager().queryIntentServicesAsUser(new Intent("android.media.browse.MediaBrowserService"), 0, this.currentUserId);
                listQueryIntentServicesAsUser.getClass();
                final ArrayList arrayList = new ArrayList();
                for (Object obj : listQueryIntentServicesAsUser) {
                    if (Intrinsics.areEqual(((ResolveInfo) obj).serviceInfo.packageName, mediaData.getPackageName())) {
                        arrayList.add(obj);
                    }
                }
                if (arrayList.size() > 0) {
                    this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener.onMediaDataLoaded.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                            String str3 = str;
                            List list = arrayList;
                            list.getClass();
                            ComponentName componentName = ((ResolveInfo) list.get(0)).getComponentInfo().getComponentName();
                            componentName.getClass();
                            mediaResumeListener.tryUpdateResumptionList(str3, componentName);
                        }
                    });
                }
            }
        }
    }

    public final void setManager(MediaDataManager mediaDataManager) {
        mediaDataManager.getClass();
        this.mediaDataManager = mediaDataManager;
    }
}
