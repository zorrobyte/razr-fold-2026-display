package com.android.systemui.plugins.clocks;

import android.graphics.drawable.Drawable;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClockPickerConfig.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ClockPickerConfig {
    public static final int $stable = 8;
    private final List axes;
    private final String description;
    private final String id;
    private final boolean isReactiveToTone;
    private final String name;
    private final AxisPresetConfig presetConfig;
    private final Drawable thumbnail;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable) {
        this(str, str2, str3, drawable, false, null, null, 112, null);
        str.getClass();
        str2.getClass();
        str3.getClass();
        drawable.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z) {
        this(str, str2, str3, drawable, z, null, null, 96, null);
        str.getClass();
        str2.getClass();
        str3.getClass();
        drawable.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z, List list) {
        this(str, str2, str3, drawable, z, list, null, 64, null);
        str.getClass();
        str2.getClass();
        str3.getClass();
        drawable.getClass();
        list.getClass();
    }

    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z, List list, AxisPresetConfig axisPresetConfig) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        drawable.getClass();
        list.getClass();
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.thumbnail = drawable;
        this.isReactiveToTone = z;
        this.axes = list;
        this.presetConfig = axisPresetConfig;
    }

    public /* synthetic */ ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z, List list, AxisPresetConfig axisPresetConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, drawable, (i & 16) != 0 ? true : z, (i & 32) != 0 ? CollectionsKt.emptyList() : list, (i & 64) != 0 ? null : axisPresetConfig);
    }

    public static /* synthetic */ ClockPickerConfig copy$default(ClockPickerConfig clockPickerConfig, String str, String str2, String str3, Drawable drawable, boolean z, List list, AxisPresetConfig axisPresetConfig, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockPickerConfig.id;
        }
        if ((i & 2) != 0) {
            str2 = clockPickerConfig.name;
        }
        if ((i & 4) != 0) {
            str3 = clockPickerConfig.description;
        }
        if ((i & 8) != 0) {
            drawable = clockPickerConfig.thumbnail;
        }
        if ((i & 16) != 0) {
            z = clockPickerConfig.isReactiveToTone;
        }
        if ((i & 32) != 0) {
            list = clockPickerConfig.axes;
        }
        if ((i & 64) != 0) {
            axisPresetConfig = clockPickerConfig.presetConfig;
        }
        List list2 = list;
        AxisPresetConfig axisPresetConfig2 = axisPresetConfig;
        boolean z2 = z;
        String str4 = str3;
        return clockPickerConfig.copy(str, str2, str4, drawable, z2, list2, axisPresetConfig2);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.name;
    }

    public final String component3() {
        return this.description;
    }

    public final Drawable component4() {
        return this.thumbnail;
    }

    public final boolean component5() {
        return this.isReactiveToTone;
    }

    public final List component6() {
        return this.axes;
    }

    public final AxisPresetConfig component7() {
        return this.presetConfig;
    }

    public final ClockPickerConfig copy(String str, String str2, String str3, Drawable drawable, boolean z, List list, AxisPresetConfig axisPresetConfig) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        drawable.getClass();
        list.getClass();
        return new ClockPickerConfig(str, str2, str3, drawable, z, list, axisPresetConfig);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockPickerConfig)) {
            return false;
        }
        ClockPickerConfig clockPickerConfig = (ClockPickerConfig) obj;
        return Intrinsics.areEqual(this.id, clockPickerConfig.id) && Intrinsics.areEqual(this.name, clockPickerConfig.name) && Intrinsics.areEqual(this.description, clockPickerConfig.description) && Intrinsics.areEqual(this.thumbnail, clockPickerConfig.thumbnail) && this.isReactiveToTone == clockPickerConfig.isReactiveToTone && Intrinsics.areEqual(this.axes, clockPickerConfig.axes) && Intrinsics.areEqual(this.presetConfig, clockPickerConfig.presetConfig);
    }

    public final List getAxes() {
        return this.axes;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final AxisPresetConfig getPresetConfig() {
        return this.presetConfig;
    }

    public final Drawable getThumbnail() {
        return this.thumbnail;
    }

    public int hashCode() {
        int iHashCode = ((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.description.hashCode()) * 31) + this.thumbnail.hashCode()) * 31) + Boolean.hashCode(this.isReactiveToTone)) * 31) + this.axes.hashCode()) * 31;
        AxisPresetConfig axisPresetConfig = this.presetConfig;
        return iHashCode + (axisPresetConfig == null ? 0 : axisPresetConfig.hashCode());
    }

    public final boolean isReactiveToTone() {
        return this.isReactiveToTone;
    }

    public String toString() {
        return "ClockPickerConfig(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", thumbnail=" + this.thumbnail + ", isReactiveToTone=" + this.isReactiveToTone + ", axes=" + this.axes + ", presetConfig=" + this.presetConfig + ")";
    }
}
