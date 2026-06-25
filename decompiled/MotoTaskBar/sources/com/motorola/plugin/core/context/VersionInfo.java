package com.motorola.plugin.core.context;

import com.motorola.plugin.sdk.annotations.Dependencies;
import com.motorola.plugin.sdk.annotations.DependsOn;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;
import com.motorola.plugin.sdk.annotations.Requirements;
import com.motorola.plugin.sdk.annotations.Requires;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VersionInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class VersionInfo {
    public static final Companion Companion = new Companion(null);
    private String mDefaultClassName;
    private final Map mVersions = new LinkedHashMap();

    /* JADX INFO: compiled from: VersionInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final VersionInfo checkVersionOrThrow(Class cls, Class cls2) {
            cls.getClass();
            cls2.getClass();
            VersionInfo versionInfoAddClass = new VersionInfo().addClass(cls);
            VersionInfo versionInfoAddClass2 = new VersionInfo().addClass(cls2);
            if (versionInfoAddClass2.hasVersionInfo()) {
                versionInfoAddClass.checkVersion(versionInfoAddClass2);
                return versionInfoAddClass2;
            }
            throwMissingRequireAttr(cls2.getName());
            return versionInfoAddClass2;
        }

        public final void throwMissingRequireAttr(String str) {
            str.getClass();
            throw new InvalidVersionException(str + " does not provide an " + ((Object) Requires.class.getName()) + " interface", false);
        }
    }

    /* JADX INFO: compiled from: VersionInfo.kt */
    public final class InvalidVersionException extends RuntimeException {
        private final boolean isTooNew;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InvalidVersionException(Class cls, boolean z, int i, int i2) {
            super(cls.getName() + " expected version " + i + " but had " + i2);
            cls.getClass();
            this.isTooNew = z;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InvalidVersionException(String str, boolean z) {
            super(str);
            str.getClass();
            this.isTooNew = z;
        }

        public final boolean isTooNew() {
            return this.isTooNew;
        }
    }

    /* JADX INFO: compiled from: VersionInfo.kt */
    final class Version {
        private final boolean required;
        private final int version;

        public Version(int i, boolean z) {
            this.version = i;
            this.required = z;
        }

        public static /* synthetic */ Version copy$default(Version version, int i, boolean z, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = version.version;
            }
            if ((i2 & 2) != 0) {
                z = version.required;
            }
            return version.copy(i, z);
        }

        public final int component1() {
            return this.version;
        }

        public final boolean component2() {
            return this.required;
        }

        public final Version copy(int i, boolean z) {
            return new Version(i, z);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Version)) {
                return false;
            }
            Version version = (Version) obj;
            return this.version == version.version && this.required == version.required;
        }

        public final boolean getRequired() {
            return this.required;
        }

        public final int getVersion() {
            return this.version;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v2, types: [int] */
        /* JADX WARN: Type inference failed for: r1v3 */
        /* JADX WARN: Type inference failed for: r1v4 */
        public int hashCode() {
            int iHashCode = Integer.hashCode(this.version) * 31;
            boolean z = this.required;
            ?? r1 = z;
            if (z) {
                r1 = 1;
            }
            return iHashCode + r1;
        }

        public String toString() {
            return "Version(version=" + this.version + ", required=" + this.required + ')';
        }
    }

    private final void addClass(Class cls, boolean z) {
        if (this.mVersions.containsKey(cls)) {
            return;
        }
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface != null) {
            this.mVersions.put(cls, new Version(providesInterface.version(), true));
        }
        Requires requires = (Requires) cls.getDeclaredAnnotation(Requires.class);
        if (requires != null) {
            this.mVersions.put(requires.target(), new Version(requires.version(), z));
        }
        Requirements requirements = (Requirements) cls.getDeclaredAnnotation(Requirements.class);
        int i = 0;
        if (requirements != null) {
            Requires[] requiresArrValue = requirements.value();
            int length = requiresArrValue.length;
            int i2 = 0;
            while (i2 < length) {
                Requires requires2 = requiresArrValue[i2];
                i2++;
                this.mVersions.put(requires2.target(), new Version(requires2.version(), z));
            }
        }
        DependsOn dependsOn = (DependsOn) cls.getDeclaredAnnotation(DependsOn.class);
        if (dependsOn != null) {
            addClass(dependsOn.target(), true);
        }
        Dependencies dependencies = (Dependencies) cls.getDeclaredAnnotation(Dependencies.class);
        if (dependencies == null) {
            return;
        }
        DependsOn[] dependsOnArrValue = dependencies.value();
        int length2 = dependsOnArrValue.length;
        while (i < length2) {
            DependsOn dependsOn2 = dependsOnArrValue[i];
            i++;
            addClass(dependsOn2.target(), true);
        }
    }

    private final Version createVersion(Class cls) {
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface == null) {
            return null;
        }
        return new Version(providesInterface.version(), false);
    }

    public final VersionInfo addClass(Class cls) {
        cls.getClass();
        if (this.mDefaultClassName == null) {
            this.mDefaultClassName = cls.getName();
        }
        addClass(cls, false);
        return this;
    }

    public final void checkVersion(VersionInfo versionInfo) throws InvalidVersionException {
        Class cls;
        Version version;
        Version versionCreateVersion;
        versionInfo.getClass();
        Map mutableMap = MapsKt.toMutableMap(this.mVersions);
        Iterator it = versionInfo.mVersions.entrySet().iterator();
        do {
            if (!it.hasNext()) {
                for (Map.Entry entry : mutableMap.entrySet()) {
                    Class cls2 = (Class) entry.getKey();
                    Version version2 = (Version) entry.getValue();
                    if (version2.getRequired()) {
                        throw new InvalidVersionException("Missing required dependency [" + ((Object) cls2.getName()) + "] with version " + version2.getVersion() + " for plugin [" + ((Object) versionInfo.mDefaultClassName) + ']', false);
                    }
                }
                return;
            }
            Map.Entry entry2 = (Map.Entry) it.next();
            cls = (Class) entry2.getKey();
            version = (Version) entry2.getValue();
            versionCreateVersion = (Version) mutableMap.remove(cls);
            if (versionCreateVersion == null) {
                versionCreateVersion = createVersion(cls);
            }
            if (versionCreateVersion == null) {
                throw new InvalidVersionException(Intrinsics.stringPlus(cls.getSimpleName(), " does not provide an interface"), false);
            }
        } while (versionCreateVersion.getVersion() == version.getVersion());
        throw new InvalidVersionException(cls, versionCreateVersion.getVersion() < version.getVersion(), versionCreateVersion.getVersion(), version.getVersion());
    }

    public final int getDefaultVersion() {
        Version version = (Version) CollectionsKt.firstOrNull(this.mVersions.values());
        if (version == null) {
            return -1;
        }
        return version.getVersion();
    }

    public final boolean hasClass(Class cls) {
        cls.getClass();
        return this.mVersions.containsKey(cls);
    }

    public final boolean hasVersionInfo() {
        return !this.mVersions.isEmpty();
    }
}
