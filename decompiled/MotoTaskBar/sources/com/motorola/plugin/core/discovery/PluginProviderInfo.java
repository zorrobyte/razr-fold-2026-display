package com.motorola.plugin.core.discovery;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginProviderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginProviderInfo implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    private final String action;
    private final CompatibilityInfo compatibilityInfo;
    private final ComponentName configure;
    private final Icon icon;
    private final int label;
    private final CharSequence labelCharSequence;
    private Resources mResources;
    private final String packageName;
    private final Icon previewImage;
    private final String prototypePluginClass;
    private final PluginStyle style;
    private final ArrayList tags;

    /* JADX INFO: compiled from: PluginProviderInfo.kt */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public PluginProviderInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new PluginProviderInfo(parcel, null);
        }

        @Override // android.os.Parcelable.Creator
        public PluginProviderInfo[] newArray(int i) {
            return new PluginProviderInfo[i];
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private PluginProviderInfo(Parcel parcel) {
        String string = parcel.readString();
        string.getClass();
        String string2 = parcel.readString();
        string2.getClass();
        String string3 = parcel.readString();
        string3.getClass();
        int i = parcel.readInt();
        CharSequence charSequence = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        Icon icon = (Icon) parcel.readParcelable(Icon.class.getClassLoader(), Icon.class);
        Icon icon2 = (Icon) parcel.readParcelable(Icon.class.getClassLoader(), Icon.class);
        ComponentName componentName = (ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader(), ComponentName.class);
        Object parcelable = parcel.readParcelable(CompatibilityInfo.class.getClassLoader(), CompatibilityInfo.class);
        parcelable.getClass();
        CompatibilityInfo compatibilityInfo = (CompatibilityInfo) parcelable;
        String string4 = parcel.readString();
        string4.getClass();
        PluginStyle pluginStyleValueOf = PluginStyle.valueOf(string4);
        ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
        arrayListCreateStringArrayList.getClass();
        this(string, string2, string3, i, charSequence, icon, icon2, componentName, compatibilityInfo, pluginStyleValueOf, arrayListCreateStringArrayList);
    }

    public /* synthetic */ PluginProviderInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public PluginProviderInfo(String str, String str2, String str3, int i, CharSequence charSequence, Icon icon, Icon icon2, ComponentName componentName, CompatibilityInfo compatibilityInfo, PluginStyle pluginStyle, ArrayList arrayList) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        compatibilityInfo.getClass();
        pluginStyle.getClass();
        arrayList.getClass();
        this.packageName = str;
        this.action = str2;
        this.prototypePluginClass = str3;
        this.label = i;
        this.labelCharSequence = charSequence;
        this.icon = icon;
        this.previewImage = icon2;
        this.configure = componentName;
        this.compatibilityInfo = compatibilityInfo;
        this.style = pluginStyle;
        this.tags = arrayList;
    }

    private final void cacheResourceIfNeed(Context context) {
        Object objM2707constructorimpl;
        if (this.mResources != null) {
            return;
        }
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(context.getPackageManager().getResourcesForApplication(getPackageName()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
            objM2707constructorimpl = null;
        }
        this.mResources = (Resources) objM2707constructorimpl;
    }

    public static /* synthetic */ PluginProviderInfo copy$default(PluginProviderInfo pluginProviderInfo, String str, String str2, String str3, int i, CharSequence charSequence, Icon icon, Icon icon2, ComponentName componentName, CompatibilityInfo compatibilityInfo, PluginStyle pluginStyle, ArrayList arrayList, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = pluginProviderInfo.packageName;
        }
        if ((i2 & 2) != 0) {
            str2 = pluginProviderInfo.action;
        }
        if ((i2 & 4) != 0) {
            str3 = pluginProviderInfo.prototypePluginClass;
        }
        if ((i2 & 8) != 0) {
            i = pluginProviderInfo.label;
        }
        if ((i2 & 16) != 0) {
            charSequence = pluginProviderInfo.labelCharSequence;
        }
        if ((i2 & 32) != 0) {
            icon = pluginProviderInfo.icon;
        }
        if ((i2 & 64) != 0) {
            icon2 = pluginProviderInfo.previewImage;
        }
        if ((i2 & 128) != 0) {
            componentName = pluginProviderInfo.configure;
        }
        if ((i2 & 256) != 0) {
            compatibilityInfo = pluginProviderInfo.compatibilityInfo;
        }
        if ((i2 & 512) != 0) {
            pluginStyle = pluginProviderInfo.style;
        }
        if ((i2 & 1024) != 0) {
            arrayList = pluginProviderInfo.tags;
        }
        PluginStyle pluginStyle2 = pluginStyle;
        ArrayList arrayList2 = arrayList;
        ComponentName componentName2 = componentName;
        CompatibilityInfo compatibilityInfo2 = compatibilityInfo;
        Icon icon3 = icon;
        Icon icon4 = icon2;
        CharSequence charSequence2 = charSequence;
        String str4 = str3;
        return pluginProviderInfo.copy(str, str2, str4, i, charSequence2, icon3, icon4, componentName2, compatibilityInfo2, pluginStyle2, arrayList2);
    }

    public final String component1() {
        return this.packageName;
    }

    public final PluginStyle component10() {
        return this.style;
    }

    public final ArrayList component11() {
        return this.tags;
    }

    public final String component2() {
        return this.action;
    }

    public final String component3() {
        return this.prototypePluginClass;
    }

    public final int component4() {
        return this.label;
    }

    public final CharSequence component5() {
        return this.labelCharSequence;
    }

    public final Icon component6() {
        return this.icon;
    }

    public final Icon component7() {
        return this.previewImage;
    }

    public final ComponentName component8() {
        return this.configure;
    }

    public final CompatibilityInfo component9() {
        return this.compatibilityInfo;
    }

    public final PluginProviderInfo copy(String str, String str2, String str3, int i, CharSequence charSequence, Icon icon, Icon icon2, ComponentName componentName, CompatibilityInfo compatibilityInfo, PluginStyle pluginStyle, ArrayList arrayList) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        compatibilityInfo.getClass();
        pluginStyle.getClass();
        arrayList.getClass();
        return new PluginProviderInfo(str, str2, str3, i, charSequence, icon, icon2, componentName, compatibilityInfo, pluginStyle, arrayList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PluginProviderInfo)) {
            return false;
        }
        PluginProviderInfo pluginProviderInfo = (PluginProviderInfo) obj;
        return Intrinsics.areEqual(this.packageName, pluginProviderInfo.packageName) && Intrinsics.areEqual(this.action, pluginProviderInfo.action) && Intrinsics.areEqual(this.prototypePluginClass, pluginProviderInfo.prototypePluginClass) && this.label == pluginProviderInfo.label && Intrinsics.areEqual(this.labelCharSequence, pluginProviderInfo.labelCharSequence) && Intrinsics.areEqual(this.icon, pluginProviderInfo.icon) && Intrinsics.areEqual(this.previewImage, pluginProviderInfo.previewImage) && Intrinsics.areEqual(this.configure, pluginProviderInfo.configure) && Intrinsics.areEqual(this.compatibilityInfo, pluginProviderInfo.compatibilityInfo) && this.style == pluginProviderInfo.style && Intrinsics.areEqual(this.tags, pluginProviderInfo.tags);
    }

    public final String getAction() {
        return this.action;
    }

    public final CompatibilityInfo getCompatibilityInfo() {
        return this.compatibilityInfo;
    }

    public final ComponentName getConfigure() {
        return this.configure;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final int getLabel() {
        return this.label;
    }

    public final CharSequence getLabelCharSequence() {
        return this.labelCharSequence;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final Icon getPreviewImage() {
        return this.previewImage;
    }

    public final String getPrototypePluginClass() {
        return this.prototypePluginClass;
    }

    public final PluginStyle getStyle() {
        return this.style;
    }

    public final ArrayList getTags() {
        return this.tags;
    }

    public int hashCode() {
        int iHashCode = ((((((this.packageName.hashCode() * 31) + this.action.hashCode()) * 31) + this.prototypePluginClass.hashCode()) * 31) + Integer.hashCode(this.label)) * 31;
        CharSequence charSequence = this.labelCharSequence;
        int iHashCode2 = (iHashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        Icon icon = this.icon;
        int iHashCode3 = (iHashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        Icon icon2 = this.previewImage;
        int iHashCode4 = (iHashCode3 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        ComponentName componentName = this.configure;
        return ((((((iHashCode4 + (componentName != null ? componentName.hashCode() : 0)) * 31) + this.compatibilityInfo.hashCode()) * 31) + this.style.hashCode()) * 31) + this.tags.hashCode();
    }

    public final Drawable loadIcon(Context context) {
        context.getClass();
        Icon icon = this.icon;
        if (icon == null) {
            return null;
        }
        return icon.loadDrawable(context);
    }

    public final CharSequence loadLabel(Context context) {
        context.getClass();
        cacheResourceIfNeed(context);
        Resources resources = this.mResources;
        String string = resources == null ? null : resources.getString(this.label);
        return string == null ? this.labelCharSequence : string;
    }

    public final Drawable loadPreviewImage(Context context) {
        context.getClass();
        Icon icon = this.previewImage;
        if (icon == null) {
            return null;
        }
        return icon.loadDrawable(context);
    }

    public String toString() {
        return "PluginProviderInfo(packageName=" + this.packageName + ", action=" + this.action + ", prototypePluginClass=" + this.prototypePluginClass + ", label=" + this.label + ", labelCharSequence=" + ((Object) this.labelCharSequence) + ", icon=" + this.icon + ", previewImage=" + this.previewImage + ", configure=" + this.configure + ", compatibilityInfo=" + this.compatibilityInfo + ", style=" + this.style + ", tags=" + this.tags + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeString(this.packageName);
        parcel.writeString(this.action);
        parcel.writeString(this.prototypePluginClass);
        parcel.writeInt(this.label);
        TextUtils.writeToParcel(this.labelCharSequence, parcel, i);
        parcel.writeParcelable(this.icon, i);
        parcel.writeParcelable(this.previewImage, i);
        parcel.writeParcelable(this.configure, i);
        parcel.writeParcelable(this.compatibilityInfo, i);
        parcel.writeString(this.style.name());
        parcel.writeStringList(this.tags);
    }
}
