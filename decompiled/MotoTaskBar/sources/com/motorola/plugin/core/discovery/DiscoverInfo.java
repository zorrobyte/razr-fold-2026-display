package com.motorola.plugin.core.discovery;

import android.content.ComponentName;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.context.VersionInfo;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DiscoverInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DiscoverInfo {
    private final String action;
    private final ClassLoader classLoader;
    private final String implementorClazz;
    private final ComponentName pluginComponent;
    private final PluginContext pluginContext;
    private final PluginPackage pluginPackage;
    private final PluginType pluginType;
    private final String prototypePluginClazz;
    private final ComponentName serviceComponent;
    public String traceId;
    private final VersionInfo versionInfo;

    public DiscoverInfo(String str, PluginPackage pluginPackage, String str2, String str3, ClassLoader classLoader, PluginContext pluginContext, ComponentName componentName, ComponentName componentName2, VersionInfo versionInfo, PluginType pluginType) {
        str.getClass();
        pluginPackage.getClass();
        str2.getClass();
        str3.getClass();
        classLoader.getClass();
        pluginContext.getClass();
        componentName.getClass();
        componentName2.getClass();
        versionInfo.getClass();
        pluginType.getClass();
        this.action = str;
        this.pluginPackage = pluginPackage;
        this.prototypePluginClazz = str2;
        this.implementorClazz = str3;
        this.classLoader = classLoader;
        this.pluginContext = pluginContext;
        this.serviceComponent = componentName;
        this.pluginComponent = componentName2;
        this.versionInfo = versionInfo;
        this.pluginType = pluginType;
    }

    public static /* synthetic */ DiscoverInfo copy$default(DiscoverInfo discoverInfo, String str, PluginPackage pluginPackage, String str2, String str3, ClassLoader classLoader, PluginContext pluginContext, ComponentName componentName, ComponentName componentName2, VersionInfo versionInfo, PluginType pluginType, int i, Object obj) {
        if ((i & 1) != 0) {
            str = discoverInfo.action;
        }
        if ((i & 2) != 0) {
            pluginPackage = discoverInfo.pluginPackage;
        }
        if ((i & 4) != 0) {
            str2 = discoverInfo.prototypePluginClazz;
        }
        if ((i & 8) != 0) {
            str3 = discoverInfo.implementorClazz;
        }
        if ((i & 16) != 0) {
            classLoader = discoverInfo.classLoader;
        }
        if ((i & 32) != 0) {
            pluginContext = discoverInfo.pluginContext;
        }
        if ((i & 64) != 0) {
            componentName = discoverInfo.serviceComponent;
        }
        if ((i & 128) != 0) {
            componentName2 = discoverInfo.pluginComponent;
        }
        if ((i & 256) != 0) {
            versionInfo = discoverInfo.versionInfo;
        }
        if ((i & 512) != 0) {
            pluginType = discoverInfo.pluginType;
        }
        VersionInfo versionInfo2 = versionInfo;
        PluginType pluginType2 = pluginType;
        ComponentName componentName3 = componentName;
        ComponentName componentName4 = componentName2;
        ClassLoader classLoader2 = classLoader;
        PluginContext pluginContext2 = pluginContext;
        return discoverInfo.copy(str, pluginPackage, str2, str3, classLoader2, pluginContext2, componentName3, componentName4, versionInfo2, pluginType2);
    }

    public final String component1() {
        return this.action;
    }

    public final PluginType component10() {
        return this.pluginType;
    }

    public final PluginPackage component2() {
        return this.pluginPackage;
    }

    public final String component3() {
        return this.prototypePluginClazz;
    }

    public final String component4() {
        return this.implementorClazz;
    }

    public final ClassLoader component5() {
        return this.classLoader;
    }

    public final PluginContext component6() {
        return this.pluginContext;
    }

    public final ComponentName component7() {
        return this.serviceComponent;
    }

    public final ComponentName component8() {
        return this.pluginComponent;
    }

    public final VersionInfo component9() {
        return this.versionInfo;
    }

    public final DiscoverInfo copy(String str, PluginPackage pluginPackage, String str2, String str3, ClassLoader classLoader, PluginContext pluginContext, ComponentName componentName, ComponentName componentName2, VersionInfo versionInfo, PluginType pluginType) {
        str.getClass();
        pluginPackage.getClass();
        str2.getClass();
        str3.getClass();
        classLoader.getClass();
        pluginContext.getClass();
        componentName.getClass();
        componentName2.getClass();
        versionInfo.getClass();
        pluginType.getClass();
        return new DiscoverInfo(str, pluginPackage, str2, str3, classLoader, pluginContext, componentName, componentName2, versionInfo, pluginType);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DiscoverInfo)) {
            return false;
        }
        DiscoverInfo discoverInfo = (DiscoverInfo) obj;
        return Intrinsics.areEqual(this.action, discoverInfo.action) && Intrinsics.areEqual(this.pluginPackage, discoverInfo.pluginPackage) && Intrinsics.areEqual(this.prototypePluginClazz, discoverInfo.prototypePluginClazz) && Intrinsics.areEqual(this.implementorClazz, discoverInfo.implementorClazz) && Intrinsics.areEqual(this.classLoader, discoverInfo.classLoader) && Intrinsics.areEqual(this.pluginContext, discoverInfo.pluginContext) && Intrinsics.areEqual(this.serviceComponent, discoverInfo.serviceComponent) && Intrinsics.areEqual(this.pluginComponent, discoverInfo.pluginComponent) && Intrinsics.areEqual(this.versionInfo, discoverInfo.versionInfo) && this.pluginType == discoverInfo.pluginType;
    }

    public final String getAction() {
        return this.action;
    }

    public final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public final String getImplementorClazz() {
        return this.implementorClazz;
    }

    public final ComponentName getPluginComponent() {
        return this.pluginComponent;
    }

    public final PluginContext getPluginContext() {
        return this.pluginContext;
    }

    public final PluginPackage getPluginPackage() {
        return this.pluginPackage;
    }

    public final PluginType getPluginType() {
        return this.pluginType;
    }

    public final String getPrototypePluginClazz() {
        return this.prototypePluginClazz;
    }

    public final ComponentName getServiceComponent() {
        return this.serviceComponent;
    }

    public final String getTraceId() {
        String str = this.traceId;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("traceId");
        throw null;
    }

    public final VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public int hashCode() {
        return (((((((((((((((((this.action.hashCode() * 31) + this.pluginPackage.hashCode()) * 31) + this.prototypePluginClazz.hashCode()) * 31) + this.implementorClazz.hashCode()) * 31) + this.classLoader.hashCode()) * 31) + this.pluginContext.hashCode()) * 31) + this.serviceComponent.hashCode()) * 31) + this.pluginComponent.hashCode()) * 31) + this.versionInfo.hashCode()) * 31) + this.pluginType.hashCode();
    }

    public final void setTraceId(String str) {
        str.getClass();
        this.traceId = str;
    }

    public String toString() {
        return "DiscoverInfo(action=" + this.action + ", pluginPackage=" + this.pluginPackage + ", prototypePluginClazz=" + this.prototypePluginClazz + ", implementorClazz=" + this.implementorClazz + ", classLoader=" + this.classLoader + ", pluginContext=" + this.pluginContext + ", serviceComponent=" + this.serviceComponent + ", pluginComponent=" + this.pluginComponent + ", versionInfo=" + this.versionInfo + ", pluginType=" + this.pluginType + ')';
    }
}
