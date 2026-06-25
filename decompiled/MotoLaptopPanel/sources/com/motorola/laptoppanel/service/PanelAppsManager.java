package com.motorola.laptoppanel.service;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.laptoppanel.util.LaptopSettingsObserver;
import com.motorola.laptoppanel.util.Logger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: PanelAppsManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PanelAppsManager {
    private final MutableSharedFlow _disabledAppsChanged;
    private final MutableStateFlow _isLaptopPanelEnabled;
    private final Lazy allowedApps$delegate;
    private final Context context;
    private final SharedFlow disabledAppsChanged;
    private final Set homeOrRecentsApps;
    private final StateFlow isLaptopPanelEnabled;
    private final CoroutineScope scope;
    private LaptopSettingsObserver settingsObserver;
    private final Lazy staticDisabledApps$delegate;
    private final Lazy unSupportedApps$delegate;
    private Set userDisabledApps;

    public PanelAppsManager(Context context, CoroutineScope coroutineScope) {
        context.getClass();
        coroutineScope.getClass();
        this.context = context;
        this.scope = coroutineScope;
        this.homeOrRecentsApps = SetsKt.setOf((Object[]) new String[]{"com.android.launcher3", "com.motorola.launcher3", "com.google.android.apps.nexuslauncher"});
        this.unSupportedApps$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PanelAppsManager.unSupportedApps_delegate$lambda$2(this.f$0);
            }
        });
        this.allowedApps$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PanelAppsManager.allowedApps_delegate$lambda$5(this.f$0);
            }
        });
        this.staticDisabledApps$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return PanelAppsManager.staticDisabledApps_delegate$lambda$6(this.f$0);
            }
        });
        this.userDisabledApps = SetsKt.emptySet();
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(isLaptopPanelEnabledInDb()));
        this._isLaptopPanelEnabled = MutableStateFlow;
        this.isLaptopPanelEnabled = FlowKt.asStateFlow(MutableStateFlow);
        MutableSharedFlow mutableSharedFlowMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7, null);
        this._disabledAppsChanged = mutableSharedFlowMutableSharedFlow$default;
        this.disabledAppsChanged = FlowKt.asSharedFlow(mutableSharedFlowMutableSharedFlow$default);
        this.userDisabledApps = loadUserDisabledApps();
        registerSettingsObserver();
        Logger.INSTANCE.d(this, "DisabledApps loaded unSupportedApps=" + getUnSupportedApps().size() + ", userDisabledApps=" + this.userDisabledApps.size(), new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set allowedApps_delegate$lambda$5(PanelAppsManager panelAppsManager) {
        try {
            String string = panelAppsManager.context.getResources().getString(17042350);
            string.getClass();
            List listSplit$default = StringsKt.split$default((CharSequence) string, new char[]{',', ';'}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(StringsKt.trim((String) it.next()).toString());
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (((String) obj).length() > 0) {
                    arrayList2.add(obj);
                }
            }
            return CollectionsKt.toSet(arrayList2);
        } catch (Exception unused) {
            return SetsKt.emptySet();
        }
    }

    private final Set getAllowedApps() {
        return (Set) this.allowedApps$delegate.getValue();
    }

    private final Set getStaticDisabledApps() {
        return (Set) this.staticDisabledApps$delegate.getValue();
    }

    private final Set getUnSupportedApps() {
        return (Set) this.unSupportedApps$delegate.getValue();
    }

    private final boolean isLaptopPanelEnabledInDb() {
        return 1 == MotorolaSettings.Secure.getInt(this.context.getContentResolver(), "laptop_panel_enabled", 1);
    }

    private final boolean isManifestAllowed(String str) {
        if (SystemProperties.getBoolean("persist.sys.laptoppanel.ignore_manifest", false)) {
            Logger.INSTANCE.d(this, "isManifestAllowed: " + str + " -> true (debug property override)", new Object[0]);
            return true;
        }
        if (getAllowedApps().contains(str)) {
            Logger.INSTANCE.d(this, "isManifestAllowed: " + str + " -> true (allowed list override)", new Object[0]);
            return true;
        }
        try {
            List<LauncherActivityInfo> activityList = ((LauncherApps) this.context.getSystemService(LauncherApps.class)).getActivityList(str, UserHandle.getUserHandleForUid(this.context.getApplicationInfo().uid));
            if (activityList.isEmpty()) {
                Logger.INSTANCE.w(this, "isManifestAllowed: " + str + " -> false (No launcher activity found)", new Object[0]);
                return false;
            }
            for (LauncherActivityInfo launcherActivityInfo : activityList) {
                ActivityInfo activityInfo = launcherActivityInfo.getActivityInfo();
                activityInfo.getClass();
                int i = activityInfo.resizeMode;
                if (i != 0 && (activityInfo.applicationInfo.targetSdkVersion >= 24 || i != 1)) {
                    int i2 = activityInfo.screenOrientation;
                    if (i2 == 1 || i2 == 7 || i2 == 9 || i2 == 12) {
                        Logger.INSTANCE.w(this, "Activity " + launcherActivityInfo.getComponentName() + " is Portrait only (orientation=" + i2 + ")", new Object[0]);
                        return false;
                    }
                }
                Logger.INSTANCE.w(this, "Activity " + launcherActivityInfo.getComponentName() + " is NOT resizable (mode=" + i + ")", new Object[0]);
                return false;
            }
            return true;
        } catch (Exception e) {
            Logger.INSTANCE.e(this, e, "isManifestAllowed exception", new Object[0]);
            return false;
        }
    }

    private final Set loadUserDisabledApps() {
        return parseSemicolonPkgs(MotorolaSettings.Secure.getString(this.context.getContentResolver(), "laptop_panel_disabled_apps_list"));
    }

    private final Set parseSemicolonPkgs(String str) {
        return (str == null || StringsKt.isBlank(str)) ? SetsKt.emptySet() : SequencesKt.toSet(SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence(StringsKt.split$default((CharSequence) str, new char[]{';', ','}, false, 0, 6, (Object) null)), new Function1() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PanelAppsManager.parseSemicolonPkgs$lambda$8((String) obj);
            }
        }), new Function1() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(PanelAppsManager.parseSemicolonPkgs$lambda$9((String) obj));
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String parseSemicolonPkgs$lambda$8(String str) {
        str.getClass();
        return StringsKt.trim(str).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean parseSemicolonPkgs$lambda$9(String str) {
        str.getClass();
        return str.length() > 0;
    }

    private final void registerSettingsObserver() {
        if (this.settingsObserver != null) {
            return;
        }
        Logger.INSTANCE.d(this, "registerSettingsObserver", new Object[0]);
        ContentResolver contentResolver = this.context.getContentResolver();
        contentResolver.getClass();
        LaptopSettingsObserver laptopSettingsObserver = new LaptopSettingsObserver(contentResolver, new Handler(Looper.getMainLooper()), new Function1() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PanelAppsManager.registerSettingsObserver$lambda$10(this.f$0, ((Boolean) obj).booleanValue());
            }
        }, new Function1() { // from class: com.motorola.laptoppanel.service.PanelAppsManager$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PanelAppsManager.registerSettingsObserver$lambda$11(this.f$0, (String) obj);
            }
        });
        laptopSettingsObserver.start();
        this.settingsObserver = laptopSettingsObserver;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit registerSettingsObserver$lambda$10(PanelAppsManager panelAppsManager, boolean z) {
        if (((Boolean) panelAppsManager._isLaptopPanelEnabled.getValue()).booleanValue() != z) {
            panelAppsManager._isLaptopPanelEnabled.setValue(Boolean.valueOf(z));
            Logger.INSTANCE.i(panelAppsManager, "onEnabledChanged " + z, new Object[0]);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit registerSettingsObserver$lambda$11(PanelAppsManager panelAppsManager, String str) {
        Set semicolonPkgs = panelAppsManager.parseSemicolonPkgs(str);
        if (Intrinsics.areEqual(panelAppsManager.userDisabledApps, semicolonPkgs)) {
            return Unit.INSTANCE;
        }
        Set set = CollectionsKt.toSet(SetsKt.plus(panelAppsManager.getStaticDisabledApps(), panelAppsManager.userDisabledApps));
        Set set2 = CollectionsKt.toSet(SetsKt.plus(panelAppsManager.getStaticDisabledApps(), semicolonPkgs));
        Set setPlus = SetsKt.plus(SetsKt.minus(set2, set), SetsKt.minus(set, set2));
        panelAppsManager.userDisabledApps = semicolonPkgs;
        Logger.INSTANCE.i(panelAppsManager, "onDisabledListChanged " + setPlus, new Object[0]);
        if (!setPlus.isEmpty()) {
            BuildersKt__Builders_commonKt.launch$default(panelAppsManager.scope, null, null, new PanelAppsManager$registerSettingsObserver$2$1(panelAppsManager, setPlus, null), 3, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set staticDisabledApps_delegate$lambda$6(PanelAppsManager panelAppsManager) {
        return CollectionsKt.toSet(SetsKt.plus(panelAppsManager.homeOrRecentsApps, panelAppsManager.getUnSupportedApps()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set unSupportedApps_delegate$lambda$2(PanelAppsManager panelAppsManager) {
        String string = panelAppsManager.context.getResources().getString(17042351);
        string.getClass();
        List listSplit$default = StringsKt.split$default((CharSequence) string, new char[]{',', ';'}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim((String) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        return CollectionsKt.toSet(arrayList2);
    }

    private final void unregisterSettingsObserver() {
        Logger.INSTANCE.d(this, "unregisterSettingsObserver", new Object[0]);
        LaptopSettingsObserver laptopSettingsObserver = this.settingsObserver;
        if (laptopSettingsObserver != null) {
            laptopSettingsObserver.stop();
        }
        this.settingsObserver = null;
    }

    public final void destroy() {
        unregisterSettingsObserver();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        fileDescriptor.getClass();
        printWriter.getClass();
        strArr.getClass();
        printWriter.println("PanelAppsManager State:");
        printWriter.println("--------------------------------------------------");
        printWriter.println("  isLaptopPanelEnabled: " + this._isLaptopPanelEnabled.getValue());
        printWriter.println("  homeOrRecentsApps (size=" + this.homeOrRecentsApps.size() + "): " + this.homeOrRecentsApps);
        printWriter.println("  unSupportedApps (size=" + getUnSupportedApps().size() + "): " + getUnSupportedApps());
        printWriter.println("  userDisabledApps (size=" + this.userDisabledApps.size() + "): " + this.userDisabledApps);
        printWriter.println("--------------------------------------------------");
        if (strArr.length == 0) {
            return;
        }
        String str = strArr[0];
        if (StringsKt.startsWith$default(str, "-", false, 2, null)) {
            return;
        }
        printWriter.println("  [Test] Checking isAllowed('" + str + "')...");
        printWriter.println("  Result: " + isAllowed(str));
        boolean zIsPackageInSet = isPackageInSet(str, this.homeOrRecentsApps);
        boolean zIsPackageInSet2 = isPackageInSet(str, getUnSupportedApps());
        boolean zIsPackageInSet3 = isPackageInSet(str, this.userDisabledApps);
        boolean zIsManifestAllowed = isManifestAllowed(str);
        printWriter.println("    - inHome: " + zIsPackageInSet);
        printWriter.println("    - inUnsupported: " + zIsPackageInSet2);
        printWriter.println("    - inUserDisabled: " + zIsPackageInSet3);
        printWriter.println("    - manifestAllowed: " + zIsManifestAllowed);
    }

    public final SharedFlow getDisabledAppsChanged() {
        return this.disabledAppsChanged;
    }

    public final boolean isAllowed(String str) {
        if (str == null || str.length() == 0 || Intrinsics.areEqual(str, this.context.getPackageName())) {
            return false;
        }
        boolean zIsPackageInSet = isPackageInSet(str, this.homeOrRecentsApps);
        boolean zIsPackageInSet2 = isPackageInSet(str, getUnSupportedApps());
        boolean zIsPackageInSet3 = isPackageInSet(str, this.userDisabledApps);
        boolean zIsManifestAllowed = isManifestAllowed(str);
        boolean z = (zIsPackageInSet || zIsPackageInSet2 || zIsPackageInSet3 || !zIsManifestAllowed) ? false : true;
        Logger.INSTANCE.i(this, "isAllowed(" + str + ") -> " + z + " | inHome=" + zIsPackageInSet + ", inUnsupported=" + zIsPackageInSet2 + ", inUserDisabled=" + zIsPackageInSet3 + ", manifestAllowed=" + zIsManifestAllowed, new Object[0]);
        return z;
    }

    public final boolean isHomeOrRecents(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return isPackageInSet(str, this.homeOrRecentsApps);
    }

    public final StateFlow isLaptopPanelEnabled() {
        return this.isLaptopPanelEnabled;
    }

    public final boolean isPackageInSet(String str, Set set) {
        str.getClass();
        set.getClass();
        Set<String> set2 = set;
        if ((set2 instanceof Collection) && set2.isEmpty()) {
            return false;
        }
        for (String str2 : set2) {
            if (Intrinsics.areEqual(str, str2)) {
                return true;
            }
            if (StringsKt.startsWith$default(str, str2 + ".", false, 2, null)) {
                return true;
            }
        }
        return false;
    }
}
