package com.motorola.plugin.core.discovery;

import android.content.ComponentName;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.context.VersionInfo;
import com.motorola.plugin.core.misc.BitFlag;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginInfo {
    public static final int FLAG_REMOTE_PLUGIN = 2;
    public static final Flag Flag = new Flag(null);
    private final String action;
    private final ComponentName channelServiceComponent;
    private final ClassLoader classLoader;
    private CompatibilityInfo compatibilityInfo;
    private final ComponentName configure;
    private final boolean enabled;
    private final BitFlag flags;
    private final int icon;
    private final String implementorPluginClass;
    private final int label;
    private final String path;
    private final ComponentName pluginComponent;
    private final PluginPackage pluginPackage;
    private final PluginStyle pluginStyle;
    private final List pluginTags;
    private final int previewImage;
    private final String prototypePluginClass;
    private final List rules;
    private VersionInfo versionInfo;

    /* JADX INFO: compiled from: PluginInfo.kt */
    public final class Flag {
        private Flag() {
        }

        public /* synthetic */ Flag(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PluginInfo(PluginPackage pluginPackage, int i, int i2, int i3, ComponentName componentName, String str, String str2, String str3, ComponentName componentName2, ComponentName componentName3, ClassLoader classLoader, CompatibilityInfo compatibilityInfo, VersionInfo versionInfo, boolean z, PluginStyle pluginStyle, List list, List list2, String str4, BitFlag bitFlag) {
        pluginPackage.getClass();
        str.getClass();
        str2.getClass();
        str3.getClass();
        componentName2.getClass();
        componentName3.getClass();
        classLoader.getClass();
        compatibilityInfo.getClass();
        versionInfo.getClass();
        pluginStyle.getClass();
        list.getClass();
        list2.getClass();
        str4.getClass();
        bitFlag.getClass();
        this.pluginPackage = pluginPackage;
        this.label = i;
        this.icon = i2;
        this.previewImage = i3;
        this.configure = componentName;
        this.action = str;
        this.prototypePluginClass = str2;
        this.implementorPluginClass = str3;
        this.channelServiceComponent = componentName2;
        this.pluginComponent = componentName3;
        this.classLoader = classLoader;
        this.compatibilityInfo = compatibilityInfo;
        this.versionInfo = versionInfo;
        this.enabled = z;
        this.pluginStyle = pluginStyle;
        this.pluginTags = list;
        this.rules = list2;
        this.path = str4;
        this.flags = bitFlag;
    }

    public /* synthetic */ PluginInfo(PluginPackage pluginPackage, int i, int i2, int i3, ComponentName componentName, String str, String str2, String str3, ComponentName componentName2, ComponentName componentName3, ClassLoader classLoader, CompatibilityInfo compatibilityInfo, VersionInfo versionInfo, boolean z, PluginStyle pluginStyle, List list, List list2, String str4, BitFlag bitFlag, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(pluginPackage, i, i2, i3, componentName, str, str2, str3, componentName2, componentName3, classLoader, compatibilityInfo, versionInfo, z, pluginStyle, list, list2, str4, (i4 & 262144) != 0 ? BitFlag.Companion.empty() : bitFlag);
    }

    public static /* synthetic */ PluginInfo copy$default(PluginInfo pluginInfo, PluginPackage pluginPackage, int i, int i2, int i3, ComponentName componentName, String str, String str2, String str3, ComponentName componentName2, ComponentName componentName3, ClassLoader classLoader, CompatibilityInfo compatibilityInfo, VersionInfo versionInfo, boolean z, PluginStyle pluginStyle, List list, List list2, String str4, BitFlag bitFlag, int i4, Object obj) {
        BitFlag bitFlag2;
        String str5;
        PluginPackage pluginPackage2 = (i4 & 1) != 0 ? pluginInfo.pluginPackage : pluginPackage;
        int i5 = (i4 & 2) != 0 ? pluginInfo.label : i;
        int i6 = (i4 & 4) != 0 ? pluginInfo.icon : i2;
        int i7 = (i4 & 8) != 0 ? pluginInfo.previewImage : i3;
        ComponentName componentName4 = (i4 & 16) != 0 ? pluginInfo.configure : componentName;
        String str6 = (i4 & 32) != 0 ? pluginInfo.action : str;
        String str7 = (i4 & 64) != 0 ? pluginInfo.prototypePluginClass : str2;
        String str8 = (i4 & 128) != 0 ? pluginInfo.implementorPluginClass : str3;
        ComponentName componentName5 = (i4 & 256) != 0 ? pluginInfo.channelServiceComponent : componentName2;
        ComponentName componentName6 = (i4 & 512) != 0 ? pluginInfo.pluginComponent : componentName3;
        ClassLoader classLoader2 = (i4 & 1024) != 0 ? pluginInfo.classLoader : classLoader;
        CompatibilityInfo compatibilityInfo2 = (i4 & 2048) != 0 ? pluginInfo.compatibilityInfo : compatibilityInfo;
        VersionInfo versionInfo2 = (i4 & 4096) != 0 ? pluginInfo.versionInfo : versionInfo;
        boolean z2 = (i4 & 8192) != 0 ? pluginInfo.enabled : z;
        PluginPackage pluginPackage3 = pluginPackage2;
        PluginStyle pluginStyle2 = (i4 & 16384) != 0 ? pluginInfo.pluginStyle : pluginStyle;
        List list3 = (i4 & 32768) != 0 ? pluginInfo.pluginTags : list;
        List list4 = (i4 & 65536) != 0 ? pluginInfo.rules : list2;
        String str9 = (i4 & 131072) != 0 ? pluginInfo.path : str4;
        if ((i4 & 262144) != 0) {
            str5 = str9;
            bitFlag2 = pluginInfo.flags;
        } else {
            bitFlag2 = bitFlag;
            str5 = str9;
        }
        return pluginInfo.copy(pluginPackage3, i5, i6, i7, componentName4, str6, str7, str8, componentName5, componentName6, classLoader2, compatibilityInfo2, versionInfo2, z2, pluginStyle2, list3, list4, str5, bitFlag2);
    }

    public final PluginPackage component1() {
        return this.pluginPackage;
    }

    public final ComponentName component10() {
        return this.pluginComponent;
    }

    public final ClassLoader component11() {
        return this.classLoader;
    }

    public final CompatibilityInfo component12() {
        return this.compatibilityInfo;
    }

    public final VersionInfo component13() {
        return this.versionInfo;
    }

    public final boolean component14() {
        return this.enabled;
    }

    public final PluginStyle component15() {
        return this.pluginStyle;
    }

    public final List component16() {
        return this.pluginTags;
    }

    public final List component17() {
        return this.rules;
    }

    public final String component18() {
        return this.path;
    }

    public final BitFlag component19() {
        return this.flags;
    }

    public final int component2() {
        return this.label;
    }

    public final int component3() {
        return this.icon;
    }

    public final int component4() {
        return this.previewImage;
    }

    public final ComponentName component5() {
        return this.configure;
    }

    public final String component6() {
        return this.action;
    }

    public final String component7() {
        return this.prototypePluginClass;
    }

    public final String component8() {
        return this.implementorPluginClass;
    }

    public final ComponentName component9() {
        return this.channelServiceComponent;
    }

    public final PluginInfo copy(PluginPackage pluginPackage, int i, int i2, int i3, ComponentName componentName, String str, String str2, String str3, ComponentName componentName2, ComponentName componentName3, ClassLoader classLoader, CompatibilityInfo compatibilityInfo, VersionInfo versionInfo, boolean z, PluginStyle pluginStyle, List list, List list2, String str4, BitFlag bitFlag) {
        pluginPackage.getClass();
        str.getClass();
        str2.getClass();
        str3.getClass();
        componentName2.getClass();
        componentName3.getClass();
        classLoader.getClass();
        compatibilityInfo.getClass();
        versionInfo.getClass();
        pluginStyle.getClass();
        list.getClass();
        list2.getClass();
        str4.getClass();
        bitFlag.getClass();
        return new PluginInfo(pluginPackage, i, i2, i3, componentName, str, str2, str3, componentName2, componentName3, classLoader, compatibilityInfo, versionInfo, z, pluginStyle, list, list2, str4, bitFlag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PluginInfo)) {
            return false;
        }
        PluginInfo pluginInfo = (PluginInfo) obj;
        return Intrinsics.areEqual(this.pluginPackage, pluginInfo.pluginPackage) && this.label == pluginInfo.label && this.icon == pluginInfo.icon && this.previewImage == pluginInfo.previewImage && Intrinsics.areEqual(this.configure, pluginInfo.configure) && Intrinsics.areEqual(this.action, pluginInfo.action) && Intrinsics.areEqual(this.prototypePluginClass, pluginInfo.prototypePluginClass) && Intrinsics.areEqual(this.implementorPluginClass, pluginInfo.implementorPluginClass) && Intrinsics.areEqual(this.channelServiceComponent, pluginInfo.channelServiceComponent) && Intrinsics.areEqual(this.pluginComponent, pluginInfo.pluginComponent) && Intrinsics.areEqual(this.classLoader, pluginInfo.classLoader) && Intrinsics.areEqual(this.compatibilityInfo, pluginInfo.compatibilityInfo) && Intrinsics.areEqual(this.versionInfo, pluginInfo.versionInfo) && this.enabled == pluginInfo.enabled && this.pluginStyle == pluginInfo.pluginStyle && Intrinsics.areEqual(this.pluginTags, pluginInfo.pluginTags) && Intrinsics.areEqual(this.rules, pluginInfo.rules) && Intrinsics.areEqual(this.path, pluginInfo.path) && Intrinsics.areEqual(this.flags, pluginInfo.flags);
    }

    public final String getAction() {
        return this.action;
    }

    public final ComponentName getChannelServiceComponent() {
        return this.channelServiceComponent;
    }

    public final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public final CompatibilityInfo getCompatibilityInfo() {
        return this.compatibilityInfo;
    }

    public final ComponentName getConfigure() {
        return this.configure;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final BitFlag getFlags() {
        return this.flags;
    }

    public final int getIcon() {
        return this.icon;
    }

    public final String getImplementorPluginClass() {
        return this.implementorPluginClass;
    }

    public final int getLabel() {
        return this.label;
    }

    public final String getPath() {
        return this.path;
    }

    public final ComponentName getPluginComponent() {
        return this.pluginComponent;
    }

    public final PluginPackage getPluginPackage() {
        return this.pluginPackage;
    }

    public final PluginStyle getPluginStyle() {
        return this.pluginStyle;
    }

    public final List getPluginTags() {
        return this.pluginTags;
    }

    public final int getPreviewImage() {
        return this.previewImage;
    }

    public final String getPrototypePluginClass() {
        return this.prototypePluginClass;
    }

    public final List getRules() {
        return this.rules;
    }

    public final VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v26, types: [int] */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r1v37 */
    public int hashCode() {
        int iHashCode = ((((((this.pluginPackage.hashCode() * 31) + Integer.hashCode(this.label)) * 31) + Integer.hashCode(this.icon)) * 31) + Integer.hashCode(this.previewImage)) * 31;
        ComponentName componentName = this.configure;
        int iHashCode2 = (((((((((((((((((iHashCode + (componentName == null ? 0 : componentName.hashCode())) * 31) + this.action.hashCode()) * 31) + this.prototypePluginClass.hashCode()) * 31) + this.implementorPluginClass.hashCode()) * 31) + this.channelServiceComponent.hashCode()) * 31) + this.pluginComponent.hashCode()) * 31) + this.classLoader.hashCode()) * 31) + this.compatibilityInfo.hashCode()) * 31) + this.versionInfo.hashCode()) * 31;
        boolean z = this.enabled;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        return ((((((((((iHashCode2 + r1) * 31) + this.pluginStyle.hashCode()) * 31) + this.pluginTags.hashCode()) * 31) + this.rules.hashCode()) * 31) + this.path.hashCode()) * 31) + this.flags.hashCode();
    }

    public final void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        compatibilityInfo.getClass();
        this.compatibilityInfo = compatibilityInfo;
    }

    public final void setVersionInfo(VersionInfo versionInfo) {
        versionInfo.getClass();
        this.versionInfo = versionInfo;
    }

    public String toString() {
        return "PluginInfo(package='" + this.pluginPackage + "', label=" + this.label + ", icon=" + this.icon + ", previewImage=" + this.previewImage + ", configure=" + this.configure + ", action='" + this.action + "', channelServiceComponent=" + this.channelServiceComponent + ", pluginComponent=" + this.pluginComponent + ", compatibilityInfo=" + this.compatibilityInfo + ", version=" + this.versionInfo.getDefaultVersion() + ", enabled=" + this.enabled + ", style=" + this.pluginStyle + ", tags=" + this.pluginTags + ", flags=" + this.flags + ')';
    }
}
