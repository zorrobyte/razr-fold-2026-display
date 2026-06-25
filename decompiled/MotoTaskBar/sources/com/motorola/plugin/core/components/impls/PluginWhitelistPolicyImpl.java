package com.motorola.plugin.core.components.impls;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.R;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.Checksum;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.MarkFlag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PluginWhitelistPolicyImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginWhitelistPolicyImpl implements PluginWhitelistPolicyExt {
    private final Context context;
    private boolean enableAllPluginsIfDebugBuild;
    private final String myPackageName;
    private final Map myWhitelistedPluginPolicyMap;

    /* JADX INFO: compiled from: PluginWhitelistPolicyImpl.kt */
    final class PluginWhitelistPolicySnapshot extends AbstractSnapshot {
        private boolean myEnableAllPlugins;
        private int myInstance;
        public Map myWhitelistedPolicyMap;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginWhitelistPolicySnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final boolean getMyEnableAllPlugins() {
            return this.myEnableAllPlugins;
        }

        public final int getMyInstance() {
            return this.myInstance;
        }

        public final Map getMyWhitelistedPolicyMap() {
            Map map = this.myWhitelistedPolicyMap;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myWhitelistedPolicyMap");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginWhitelistPolicy", getMyInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("enableAny", Boolean.valueOf(getMyEnableAllPlugins())).newLine();
            int i = 0;
            for (Map.Entry entry : getMyWhitelistedPolicyMap().entrySet()) {
                PluginId pluginId = (PluginId) entry.getKey();
                Policy policy = (Policy) entry.getValue();
                String id = pluginId.getId();
                IPrinter iPrinter2 = iPrinter;
                IPrinter.DefaultImpls.printIndex$default(iPrinter2, i, id, null, 4, null);
                iPrinter2.printPair("match", policy.getMatchState());
                iPrinter2.newLine();
                i++;
                iPrinter = iPrinter2;
            }
            iPrinter.decreaseIndent();
        }

        public final void setMyEnableAllPlugins(boolean z) {
            this.myEnableAllPlugins = z;
        }

        public final void setMyInstance(int i) {
            this.myInstance = i;
        }

        public final void setMyWhitelistedPolicyMap(Map map) {
            map.getClass();
            this.myWhitelistedPolicyMap = map;
        }
    }

    /* JADX INFO: compiled from: PluginWhitelistPolicyImpl.kt */
    final class Policy {
        private List checksums;
        private Boolean match;

        /* JADX WARN: Multi-variable type inference failed */
        public Policy() {
            this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }

        public Policy(List list, Boolean bool) {
            this.checksums = list;
            this.match = bool;
        }

        public /* synthetic */ Policy(List list, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : list, (i & 2) != 0 ? null : bool);
        }

        public static /* synthetic */ Policy copy$default(Policy policy, List list, Boolean bool, int i, Object obj) {
            if ((i & 1) != 0) {
                list = policy.checksums;
            }
            if ((i & 2) != 0) {
                bool = policy.match;
            }
            return policy.copy(list, bool);
        }

        public final List component1() {
            return this.checksums;
        }

        public final Boolean component2() {
            return this.match;
        }

        public final Policy copy(List list, Boolean bool) {
            return new Policy(list, bool);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Policy)) {
                return false;
            }
            Policy policy = (Policy) obj;
            return Intrinsics.areEqual(this.checksums, policy.checksums) && Intrinsics.areEqual(this.match, policy.match);
        }

        public final List getChecksums() {
            return this.checksums;
        }

        public final Boolean getMatch() {
            return this.match;
        }

        public final String getMatchState() {
            Boolean bool = this.match;
            return bool == null ? "--" : String.valueOf(bool);
        }

        public int hashCode() {
            List list = this.checksums;
            int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
            Boolean bool = this.match;
            return iHashCode + (bool != null ? bool.hashCode() : 0);
        }

        public final void setChecksums(List list) {
            this.checksums = list;
        }

        public final void setMatch(Boolean bool) {
            this.match = bool;
        }

        public String toString() {
            return "Policy(checksums=" + this.checksums + ", match=" + this.match + ')';
        }
    }

    public PluginWhitelistPolicyImpl(@AppContext Context context) {
        context.getClass();
        this.context = context;
        this.myWhitelistedPluginPolicyMap = new LinkedHashMap();
        this.myPackageName = context.getPackageName();
    }

    private final void generatePolicyMap() {
        boolean z;
        List checksums;
        boolean z2;
        if (getEnableAllPluginsIfDebugBuild()) {
            return;
        }
        synchronized (this) {
            try {
                this.myWhitelistedPluginPolicyMap.clear();
                String[] stringArray = this.context.getResources().getStringArray(R.array.config_predefined_pluginWhitelist);
                stringArray.getClass();
                for (String str : stringArray) {
                    str.getClass();
                    List listSplit$default = StringsKt.split$default((CharSequence) str, new char[]{':'}, false, 0, 6, (Object) null);
                    if (listSplit$default.size() < 2) {
                        throw new IllegalArgumentException("Whitelist plugin format error, please check the integration document");
                    }
                    String str2 = (String) listSplit$default.get(0);
                    String str3 = (String) listSplit$default.get(1);
                    final String str4 = (String) CollectionsKt.getOrNull(listSplit$default, 2);
                    final PluginId pluginId = ExtensionsKt.toPluginId(str2);
                    if (str4 != null && !Intrinsics.areEqual(str4, "dev") && !Intrinsics.areEqual(str4, "prod")) {
                        throw new IllegalStateException("Whitelist plugin format error, please check the integration document");
                    }
                    Map map = this.myWhitelistedPluginPolicyMap;
                    Object obj = map.get(pluginId);
                    Boolean boolValueOf = null;
                    boolean z3 = false;
                    boolean z4 = false;
                    boolean z5 = false;
                    Object obj2 = obj;
                    if (obj == null) {
                        Policy policy = new Policy(z5 ? 1 : 0, z4 ? 1 : 0, 3, z3 ? 1 : 0);
                        map.put(pluginId, policy);
                        obj2 = policy;
                    }
                    final Policy policy2 = (Policy) obj2;
                    PackageInfo packageInfoOrNull = getPackageInfoOrNull(pluginId);
                    if (StringsKt.isBlank(str3)) {
                        policy2.setMatch(Boolean.FALSE);
                    } else if (packageInfoOrNull != null && ExtensionsKt.isSystemOrUpdated(packageInfoOrNull)) {
                        policy2.setMatch(Boolean.TRUE);
                    } else {
                        List checksumsSha1 = packageInfoOrNull == null ? null : ExtensionsKt.getChecksumsSha1(packageInfoOrNull);
                        if (checksumsSha1 != null) {
                            if (checksumsSha1.isEmpty()) {
                                z = false;
                                boolValueOf = Boolean.valueOf(z);
                            } else {
                                Iterator it = checksumsSha1.iterator();
                                while (it.hasNext()) {
                                    if (StringsKt.equals((String) it.next(), str3, true)) {
                                        z = true;
                                        break;
                                    }
                                }
                                z = false;
                                boolValueOf = Boolean.valueOf(z);
                            }
                        }
                        if (policy2.getChecksums() == null) {
                            checksums = new ArrayList();
                            policy2.setChecksums(checksums);
                        } else {
                            checksums = policy2.getChecksums();
                            checksums.getClass();
                        }
                        checksums.add(new Checksum(1, str3));
                        if (policy2.getMatch() == null) {
                            policy2.setMatch(boolValueOf);
                        } else if (boolValueOf != null) {
                            if (boolValueOf.booleanValue()) {
                                z2 = true;
                                policy2.setMatch(Boolean.valueOf(z2));
                            } else {
                                Boolean match = policy2.getMatch();
                                match.getClass();
                                if (match.booleanValue()) {
                                    z2 = true;
                                    policy2.setMatch(Boolean.valueOf(z2));
                                } else {
                                    z2 = false;
                                    policy2.setMatch(Boolean.valueOf(z2));
                                }
                            }
                        }
                    }
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginWhitelistPolicyImpl$generatePolicyMap$1$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            String strStringPlus;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Install ");
                            String str5 = str4;
                            String str6 = "";
                            if (str5 != null && (strStringPlus = Intrinsics.stringPlus(str5, " ")) != null) {
                                str6 = strStringPlus;
                            }
                            sb.append(str6);
                            sb.append("policy for ");
                            sb.append(pluginId.getId());
                            sb.append(", match = ");
                            sb.append(policy2.getMatchState());
                            return sb.toString();
                        }
                    }, 62, null);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final PackageInfo getPackageInfoOrNull(PluginId pluginId) {
        Object objM2707constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(this.context.getPackageManager().getPackageInfo(pluginId.getId(), 134217728));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
            objM2707constructorimpl = null;
        }
        return (PackageInfo) objM2707constructorimpl;
    }

    private final void updatePolicy(final PluginId pluginId, final Policy policy) {
        if (policy.getMatch() != null) {
            return;
        }
        PackageInfo packageInfoOrNull = getPackageInfoOrNull(pluginId);
        if (packageInfoOrNull == null) {
            policy.setMatch(null);
        } else if (ExtensionsKt.isSystemOrUpdated(packageInfoOrNull)) {
            policy.setMatch(Boolean.TRUE);
        } else {
            List checksumsSha1 = ExtensionsKt.getChecksumsSha1(packageInfoOrNull);
            boolean z = false;
            if (checksumsSha1 == null || !checksumsSha1.isEmpty()) {
                Iterator it = checksumsSha1.iterator();
                loop0: while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String str = (String) it.next();
                    List checksums = policy.getChecksums();
                    if (checksums != null && !checksums.isEmpty()) {
                        Iterator it2 = checksums.iterator();
                        while (it2.hasNext()) {
                            if (StringsKt.equals(((Checksum) it2.next()).getHex(), str, true)) {
                                z = true;
                                break loop0;
                            }
                        }
                    }
                }
            }
            policy.setMatch(Boolean.valueOf(z));
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginWhitelistPolicyImpl.updatePolicy.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Update policy " + pluginId.getId() + ", match = " + policy.getMatchState();
            }
        }, 62, null);
    }

    @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
    public boolean getEnableAllPluginsIfDebugBuild() {
        return this.enableAllPluginsIfDebugBuild;
    }

    @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
    @Deprecated
    public void installWhitelistedPlugins(String[] strArr) {
        PluginWhitelistPolicyExt.DefaultImpls.installWhitelistedPlugins(this, strArr);
    }

    @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
    public boolean isPluginWhitelisted(ComponentName componentName) {
        componentName.getClass();
        String packageName = componentName.getPackageName();
        packageName.getClass();
        return isPluginWhitelisted(ExtensionsKt.toPluginId(packageName));
    }

    @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
    public boolean isPluginWhitelisted(PluginId pluginId) {
        boolean zAreEqual;
        pluginId.getClass();
        if (getEnableAllPluginsIfDebugBuild() || Intrinsics.areEqual(pluginId.getId(), this.myPackageName)) {
            return true;
        }
        synchronized (this) {
            Policy policy = (Policy) this.myWhitelistedPluginPolicyMap.get(pluginId);
            if (policy == null) {
                zAreEqual = false;
            } else {
                updatePolicy(pluginId, policy);
                zAreEqual = Intrinsics.areEqual(policy.getMatch(), Boolean.TRUE);
            }
        }
        return zAreEqual;
    }

    @Override // com.motorola.plugin.core.components.OnInitializedAware
    public void onInitialized() {
        generatePolicyMap();
    }

    @Override // com.motorola.plugin.core.components.PluginWhitelistPolicyExt
    public void onPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag) {
        pluginPackage.getClass();
        markFlag.getClass();
        if (getEnableAllPluginsIfDebugBuild() || !markFlag.deleted()) {
            return;
        }
        synchronized (this) {
            Policy policy = (Policy) this.myWhitelistedPluginPolicyMap.get(pluginPackage.getPluginId());
            if (policy != null) {
                policy.setMatch(null);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
    public void setEnableAllPluginsIfDebugBuild(boolean z) {
        this.enableAllPluginsIfDebugBuild = PluginConfigKt.getDEBUG() && z;
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginWhitelistPolicySnapshot pluginWhitelistPolicySnapshot = new PluginWhitelistPolicySnapshot(iSnapshot);
        pluginWhitelistPolicySnapshot.setMyInstance(hashCode());
        pluginWhitelistPolicySnapshot.setMyEnableAllPlugins(getEnableAllPluginsIfDebugBuild());
        pluginWhitelistPolicySnapshot.setMyWhitelistedPolicyMap(MapsKt.toMap(this.myWhitelistedPluginPolicyMap));
        return pluginWhitelistPolicySnapshot;
    }
}
