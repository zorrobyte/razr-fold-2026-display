package com.motorola.plugin.core.components.impls;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import androidx.core.content.ContextCompat;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PackageEventMonitor;
import com.motorola.plugin.core.components.PluginManagerKt;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.core.remote.RemotePluginManager;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PackageEventMonitorImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PackageEventMonitorImpl extends BroadcastReceiver implements PackageEventMonitor {
    private final Context context;
    private Callback mCallback;
    private final DisposableContainer mDisposable;
    private boolean mListening;
    private final RemotePluginManager rpm;

    /* JADX INFO: compiled from: PackageEventMonitorImpl.kt */
    final class Callback extends LauncherApps.Callback implements PackageEventMonitor.PackageEventCallback {
        private final PackageEventMonitor.PackageEventCallback mCallback;

        public Callback(PackageEventMonitor.PackageEventCallback packageEventCallback) {
            packageEventCallback.getClass();
            this.mCallback = packageEventCallback;
        }

        @Override // com.motorola.plugin.core.components.PackageEventMonitor.PackageEventCallback
        public void onDisablePlugin(ComponentName componentName) {
            componentName.getClass();
            this.mCallback.onDisablePlugin(componentName);
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageAdded(final String str, final UserHandle userHandle) {
            str.getClass();
            userHandle.getClass();
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$Callback$onPackageAdded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Package added -> " + str + " for user " + userHandle;
                }
            }, 60, null);
            this.mCallback.onPluginPackageChanged(PluginPackage.Companion.of(ExtensionsKt.toPluginId(str), userHandle), null, MarkFlag.Companion.mark().markAdd());
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageChanged(final String str, final UserHandle userHandle) {
            str.getClass();
            userHandle.getClass();
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$Callback$onPackageChanged$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Package changed -> " + str + " for user " + userHandle;
                }
            }, 60, null);
            this.mCallback.onPluginPackageChanged(PluginPackage.Companion.of(ExtensionsKt.toPluginId(str), userHandle), null, MarkFlag.Companion.mark().markUpdate());
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageRemoved(final String str, final UserHandle userHandle) {
            str.getClass();
            userHandle.getClass();
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$Callback$onPackageRemoved$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Package removed -> " + str + " for user " + userHandle;
                }
            }, 60, null);
            this.mCallback.onPluginPackageChanged(PluginPackage.Companion.of(ExtensionsKt.toPluginId(str), userHandle), null, MarkFlag.Companion.mark().markDelete());
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesAvailable(String[] strArr, final UserHandle userHandle, final boolean z) {
            strArr.getClass();
            userHandle.getClass();
            for (final String str : strArr) {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$Callback$onPackagesAvailable$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "Package available -> " + str + " replacing? " + z + " for user " + userHandle;
                    }
                }, 60, null);
                MarkFlag markFlagMark = MarkFlag.Companion.mark();
                markFlagMark.markAdd();
                if (z) {
                    markFlagMark.reset();
                    markFlagMark.markUpdate();
                }
                this.mCallback.onPluginPackageChanged(PluginPackage.Companion.of(ExtensionsKt.toPluginId(str), userHandle), null, markFlagMark);
            }
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesUnavailable(String[] strArr, final UserHandle userHandle, final boolean z) {
            strArr.getClass();
            userHandle.getClass();
            for (final String str : strArr) {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$Callback$onPackagesUnavailable$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "Package unavailable -> " + str + " replacing? " + z + " for user " + userHandle;
                    }
                }, 60, null);
                this.mCallback.onPluginPackageChanged(PluginPackage.Companion.of(ExtensionsKt.toPluginId(str), userHandle), null, MarkFlag.Companion.mark().markDelete());
            }
        }

        @Override // com.motorola.plugin.core.components.PackageEventMonitor.PackageEventCallback
        public void onPluginPackageChanged(PluginPackage pluginPackage, ComponentName componentName, MarkFlag markFlag) {
            pluginPackage.getClass();
            markFlag.getClass();
            this.mCallback.onPluginPackageChanged(pluginPackage, componentName, markFlag);
        }

        @Override // com.motorola.plugin.core.components.PackageEventMonitor.PackageEventCallback
        public void onUserUnlocked() {
            this.mCallback.onUserUnlocked();
        }
    }

    public PackageEventMonitorImpl(@AppContext Context context, RemotePluginManager remotePluginManager, DisposableContainer disposableContainer) {
        context.getClass();
        remotePluginManager.getClass();
        disposableContainer.getClass();
        this.context = context;
        this.rpm = remotePluginManager;
        this.mDisposable = disposableContainer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: startListening$lambda-0, reason: not valid java name */
    public static final void m2210startListening$lambda0(PackageEventMonitorImpl packageEventMonitorImpl, Callback callback) {
        packageEventMonitorImpl.getClass();
        callback.getClass();
        LauncherApps launcherApps = (LauncherApps) ContextCompat.getSystemService(packageEventMonitorImpl.context, LauncherApps.class);
        if (launcherApps != null) {
            launcherApps.unregisterCallback(callback);
        }
        packageEventMonitorImpl.rpm.unregisterCallback(callback);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        if (this.mListening) {
            this.mListening = false;
            this.mDisposable.dispose();
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Uri data;
        Unit unit;
        context.getClass();
        intent.getClass();
        String action = intent.getAction();
        if (action != null) {
            int iHashCode = action.hashCode();
            if (iHashCode == -628301545) {
                if (action.equals(PluginManagerKt.INTENT_PLUGIN_CHANGED)) {
                    Uri data2 = intent.getData();
                    data2.getClass();
                    final String encodedSchemeSpecificPart = data2.getEncodedSchemeSpecificPart();
                    encodedSchemeSpecificPart.getClass();
                    final ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(encodedSchemeSpecificPart);
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl.onReceive.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Plugin changed -> ");
                            sb.append(encodedSchemeSpecificPart);
                            sb.append(" : ");
                            ComponentName componentName = componentNameUnflattenFromString;
                            sb.append((Object) (componentName == null ? null : componentName.flattenToShortString()));
                            return sb.toString();
                        }
                    }, 62, null);
                    UserHandle userHandleMyUserHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
                    if (userHandleMyUserHandle == null) {
                        userHandleMyUserHandle = Process.myUserHandle();
                    }
                    Callback callback = this.mCallback;
                    if (callback == null) {
                        return;
                    }
                    PluginPackage.Companion companion = PluginPackage.Companion;
                    PluginId pluginId = ExtensionsKt.toPluginId(encodedSchemeSpecificPart);
                    userHandleMyUserHandle.getClass();
                    callback.onPluginPackageChanged(companion.of(pluginId, userHandleMyUserHandle), componentNameUnflattenFromString, MarkFlag.Companion.mark().markAdd());
                    return;
                }
                return;
            }
            if (iHashCode == 833559602) {
                if (action.equals("android.intent.action.USER_UNLOCKED")) {
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl.onReceive.1
                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return "On user unlocked";
                        }
                    }, 60, null);
                    Callback callback2 = this.mCallback;
                    if (callback2 == null) {
                        return;
                    }
                    callback2.onUserUnlocked();
                    return;
                }
                return;
            }
            if (iHashCode == 1609092121 && action.equals(PluginManagerKt.INTENT_DISABLE_PLUGIN) && (data = intent.getData()) != null) {
                try {
                    Result.Companion companion2 = Result.Companion;
                    String string = data.toString();
                    string.getClass();
                    String strSubstring = string.substring(10);
                    strSubstring.getClass();
                    final ComponentName componentNameUnflattenFromString2 = ComponentName.unflattenFromString(strSubstring);
                    componentNameUnflattenFromString2.getClass();
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$onReceive$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return Intrinsics.stringPlus("Disable plugin -> ", componentNameUnflattenFromString2.flattenToShortString());
                        }
                    }, 62, null);
                    Callback callback3 = this.mCallback;
                    if (callback3 == null) {
                        unit = null;
                    } else {
                        callback3.onDisablePlugin(componentNameUnflattenFromString2);
                        unit = Unit.INSTANCE;
                    }
                    Result.m2707constructorimpl(unit);
                } catch (Throwable th) {
                    Result.Companion companion3 = Result.Companion;
                    Result.m2707constructorimpl(ResultKt.createFailure(th));
                }
            }
        }
    }

    @Override // com.motorola.plugin.core.components.PackageEventMonitor
    public void startListening(PackageEventMonitor.PackageEventCallback packageEventCallback, Looper looper) {
        packageEventCallback.getClass();
        looper.getClass();
        if (this.mListening) {
            return;
        }
        this.mListening = true;
        Handler handler = new Handler(looper);
        final Callback callback = new Callback(packageEventCallback);
        this.mCallback = callback;
        LauncherApps launcherApps = (LauncherApps) ContextCompat.getSystemService(this.context, LauncherApps.class);
        if (launcherApps != null) {
            launcherApps.registerCallback(callback, handler);
        }
        this.rpm.registerCallback(callback, handler);
        this.mDisposable.add(new Disposable() { // from class: com.motorola.plugin.core.components.impls.PackageEventMonitorImpl$$ExternalSyntheticLambda0
            @Override // com.motorola.plugin.core.misc.Disposable
            public final void dispose() {
                PackageEventMonitorImpl.m2210startListening$lambda0(this.f$0, callback);
            }
        });
    }

    @Override // com.motorola.plugin.core.components.PackageEventMonitor
    public void stopListening() {
        dispose();
    }
}
