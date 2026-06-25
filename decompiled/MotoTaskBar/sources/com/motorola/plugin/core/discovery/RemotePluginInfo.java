package com.motorola.plugin.core.discovery;

import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.misc.Revision;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemotePluginInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RemotePluginInfo {
    private final String action;
    private final String displayIcon;
    private final String displayName;
    private final boolean enabled;
    private final Revision maxHostVersion;
    private final Revision minHostVersion;
    private final PluginId pluginId;
    private final String previewImage;
    private final String prototypePluginClass;

    public RemotePluginInfo(PluginId pluginId, String str, String str2, String str3, String str4, String str5, Revision revision, Revision revision2, boolean z) {
        pluginId.getClass();
        str4.getClass();
        str5.getClass();
        this.pluginId = pluginId;
        this.displayName = str;
        this.displayIcon = str2;
        this.previewImage = str3;
        this.action = str4;
        this.prototypePluginClass = str5;
        this.minHostVersion = revision;
        this.maxHostVersion = revision2;
        this.enabled = z;
    }

    public /* synthetic */ RemotePluginInfo(PluginId pluginId, String str, String str2, String str3, String str4, String str5, Revision revision, Revision revision2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(pluginId, str, str2, str3, str4, str5, revision, revision2, (i & 256) != 0 ? true : z);
    }

    public static /* synthetic */ RemotePluginInfo copy$default(RemotePluginInfo remotePluginInfo, PluginId pluginId, String str, String str2, String str3, String str4, String str5, Revision revision, Revision revision2, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            pluginId = remotePluginInfo.pluginId;
        }
        if ((i & 2) != 0) {
            str = remotePluginInfo.displayName;
        }
        if ((i & 4) != 0) {
            str2 = remotePluginInfo.displayIcon;
        }
        if ((i & 8) != 0) {
            str3 = remotePluginInfo.previewImage;
        }
        if ((i & 16) != 0) {
            str4 = remotePluginInfo.action;
        }
        if ((i & 32) != 0) {
            str5 = remotePluginInfo.prototypePluginClass;
        }
        if ((i & 64) != 0) {
            revision = remotePluginInfo.minHostVersion;
        }
        if ((i & 128) != 0) {
            revision2 = remotePluginInfo.maxHostVersion;
        }
        if ((i & 256) != 0) {
            z = remotePluginInfo.enabled;
        }
        Revision revision3 = revision2;
        boolean z2 = z;
        String str6 = str5;
        Revision revision4 = revision;
        String str7 = str4;
        String str8 = str2;
        return remotePluginInfo.copy(pluginId, str, str8, str3, str7, str6, revision4, revision3, z2);
    }

    public final PluginId component1() {
        return this.pluginId;
    }

    public final String component2() {
        return this.displayName;
    }

    public final String component3() {
        return this.displayIcon;
    }

    public final String component4() {
        return this.previewImage;
    }

    public final String component5() {
        return this.action;
    }

    public final String component6() {
        return this.prototypePluginClass;
    }

    public final Revision component7() {
        return this.minHostVersion;
    }

    public final Revision component8() {
        return this.maxHostVersion;
    }

    public final boolean component9() {
        return this.enabled;
    }

    public final RemotePluginInfo copy(PluginId pluginId, String str, String str2, String str3, String str4, String str5, Revision revision, Revision revision2, boolean z) {
        pluginId.getClass();
        str4.getClass();
        str5.getClass();
        return new RemotePluginInfo(pluginId, str, str2, str3, str4, str5, revision, revision2, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemotePluginInfo)) {
            return false;
        }
        RemotePluginInfo remotePluginInfo = (RemotePluginInfo) obj;
        return Intrinsics.areEqual(this.pluginId, remotePluginInfo.pluginId) && Intrinsics.areEqual(this.displayName, remotePluginInfo.displayName) && Intrinsics.areEqual(this.displayIcon, remotePluginInfo.displayIcon) && Intrinsics.areEqual(this.previewImage, remotePluginInfo.previewImage) && Intrinsics.areEqual(this.action, remotePluginInfo.action) && Intrinsics.areEqual(this.prototypePluginClass, remotePluginInfo.prototypePluginClass) && Intrinsics.areEqual(this.minHostVersion, remotePluginInfo.minHostVersion) && Intrinsics.areEqual(this.maxHostVersion, remotePluginInfo.maxHostVersion) && this.enabled == remotePluginInfo.enabled;
    }

    public final String getAction() {
        return this.action;
    }

    public final String getDisplayIcon() {
        return this.displayIcon;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final Revision getMaxHostVersion() {
        return this.maxHostVersion;
    }

    public final Revision getMinHostVersion() {
        return this.minHostVersion;
    }

    public final PluginId getPluginId() {
        return this.pluginId;
    }

    public final String getPreviewImage() {
        return this.previewImage;
    }

    public final String getPrototypePluginClass() {
        return this.prototypePluginClass;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    public int hashCode() {
        int iHashCode = this.pluginId.hashCode() * 31;
        String str = this.displayName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.displayIcon;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.previewImage;
        int iHashCode4 = (((((iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.action.hashCode()) * 31) + this.prototypePluginClass.hashCode()) * 31;
        Revision revision = this.minHostVersion;
        int iHashCode5 = (iHashCode4 + (revision == null ? 0 : revision.hashCode())) * 31;
        Revision revision2 = this.maxHostVersion;
        int iHashCode6 = (iHashCode5 + (revision2 != null ? revision2.hashCode() : 0)) * 31;
        boolean z = this.enabled;
        ?? r3 = z;
        if (z) {
            r3 = 1;
        }
        return iHashCode6 + r3;
    }

    public String toString() {
        return "RemotePluginInfo(pluginId=" + this.pluginId + ", displayName=" + ((Object) this.displayName) + ", displayIcon=" + ((Object) this.displayIcon) + ", previewImage=" + ((Object) this.previewImage) + ", action=" + this.action + ", prototypePluginClass=" + this.prototypePluginClass + ", minHostVersion=" + this.minHostVersion + ", maxHostVersion=" + this.maxHostVersion + ", enabled=" + this.enabled + ')';
    }
}
