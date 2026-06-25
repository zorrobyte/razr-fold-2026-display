package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Trace;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class NotificationSettingsController implements Dumpable {
    private final Handler mBackgroundHandler;
    private final ContentObserver mContentObserver;
    private final UserTracker.Callback mCurrentUserTrackerCallback;
    private final HashMap mListeners = new HashMap();
    private final Handler mMainHandler;
    private final SecureSettings mSecureSettings;
    private final UserTracker mUserTracker;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.NotificationSettingsController$1, reason: invalid class name */
    class AnonymousClass1 extends ContentObserver {
        AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, final Uri uri) {
            Trace.traceBegin(4096L, "NotificationSettingsController.ContentObserver.onChange");
            super.onChange(z, uri);
            synchronized (NotificationSettingsController.this.mListeners) {
                try {
                    if (NotificationSettingsController.this.mListeners.containsKey(uri)) {
                        final int userId = NotificationSettingsController.this.mUserTracker.getUserId();
                        final String currentSettingValue = NotificationSettingsController.this.getCurrentSettingValue(uri, userId);
                        ArrayList arrayList = (ArrayList) NotificationSettingsController.this.mListeners.get(uri);
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            final Listener listener = (Listener) obj;
                            NotificationSettingsController.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    listener.onSettingChanged(uri, userId, currentSettingValue);
                                }
                            });
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Trace.traceEnd(4096L);
        }
    }

    public interface Listener {
        void onSettingChanged(Uri uri, int i, String str);
    }

    public NotificationSettingsController(UserTracker userTracker, Handler handler, Handler handler2, SecureSettings secureSettings, DumpManager dumpManager) {
        this.mUserTracker = userTracker;
        this.mMainHandler = handler;
        this.mBackgroundHandler = handler2;
        this.mSecureSettings = secureSettings;
        this.mContentObserver = new AnonymousClass1(handler2);
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanged(int i, Context context) {
                Trace.traceBegin(4096L, "NotificationSettingsController.UserTracker.Callback.onUserChanged");
                synchronized (NotificationSettingsController.this.mListeners) {
                    try {
                        if (NotificationSettingsController.this.mListeners.size() > 0) {
                            NotificationSettingsController.this.mSecureSettings.unregisterContentObserver(NotificationSettingsController.this.mContentObserver);
                            Iterator it = NotificationSettingsController.this.mListeners.keySet().iterator();
                            while (it.hasNext()) {
                                NotificationSettingsController.this.mSecureSettings.registerContentObserverForUser((Uri) it.next(), false, NotificationSettingsController.this.mContentObserver, i);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Trace.traceEnd(4096L);
            }
        };
        this.mCurrentUserTrackerCallback = callback;
        userTracker.addCallback(callback, new HandlerExecutor(handler2));
        dumpManager.registerNormalDumpable("NotificationSettingsController", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCurrentSettingValue(Uri uri, int i) {
        return this.mSecureSettings.getStringForUser(uri == null ? null : uri.getLastPathSegment(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$0(Uri uri) {
        this.mSecureSettings.registerContentObserverForUser(uri, false, this.mContentObserver, this.mUserTracker.getUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$2(final Uri uri, final Listener listener) {
        final int userId = this.mUserTracker.getUserId();
        final String currentSettingValue = getCurrentSettingValue(uri, userId);
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                listener.onSettingChanged(uri, userId, currentSettingValue);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeCallback$3() {
        this.mSecureSettings.unregisterContentObserver(this.mContentObserver);
    }

    public void addCallback(final Uri uri, final Listener listener) {
        if (uri == null || listener == null) {
            return;
        }
        Trace.traceBegin(4096L, "NotificationSettingsController.addCallback");
        synchronized (this.mListeners) {
            try {
                ArrayList arrayList = (ArrayList) this.mListeners.get(uri);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                if (!arrayList.contains(listener)) {
                    arrayList.add(listener);
                }
                this.mListeners.put(uri, arrayList);
                if (arrayList.size() == 1) {
                    this.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$addCallback$0(uri);
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addCallback$2(uri, listener);
            }
        });
        Trace.traceEnd(4096L);
    }

    public void removeCallback(Uri uri, Listener listener) {
        Trace.traceBegin(4096L, "NotificationSettingsController.removeCallback");
        synchronized (this.mListeners) {
            try {
                ArrayList arrayList = (ArrayList) this.mListeners.get(uri);
                if (arrayList != null) {
                    arrayList.remove(listener);
                }
                if (arrayList == null || arrayList.size() == 0) {
                    this.mListeners.remove(uri);
                }
                if (this.mListeners.size() == 0) {
                    this.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$removeCallback$3();
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.traceEnd(4096L);
    }
}
