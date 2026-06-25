package com.android.systemui.plugins.clocks;

import android.graphics.drawable.Drawable;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClockPickerConfig.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AxisPresetConfig {
    public static final int $stable = 8;
    private final IndexedStyle current;
    private final List groups;

    /* JADX INFO: compiled from: ClockPickerConfig.kt */
    public final class Group {
        public static final int $stable = 8;
        private final Drawable icon;
        private final List presets;

        public Group(List list, Drawable drawable) {
            list.getClass();
            drawable.getClass();
            this.presets = list;
            this.icon = drawable;
        }

        public static /* synthetic */ Group copy$default(Group group, List list, Drawable drawable, int i, Object obj) {
            if ((i & 1) != 0) {
                list = group.presets;
            }
            if ((i & 2) != 0) {
                drawable = group.icon;
            }
            return group.copy(list, drawable);
        }

        public final List component1() {
            return this.presets;
        }

        public final Drawable component2() {
            return this.icon;
        }

        public final Group copy(List list, Drawable drawable) {
            list.getClass();
            drawable.getClass();
            return new Group(list, drawable);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Group)) {
                return false;
            }
            Group group = (Group) obj;
            return Intrinsics.areEqual(this.presets, group.presets) && Intrinsics.areEqual(this.icon, group.icon);
        }

        public final Drawable getIcon() {
            return this.icon;
        }

        public final List getPresets() {
            return this.presets;
        }

        public int hashCode() {
            return (this.presets.hashCode() * 31) + this.icon.hashCode();
        }

        public String toString() {
            return "Group(presets=" + this.presets + ", icon=" + this.icon + ")";
        }
    }

    /* JADX INFO: compiled from: ClockPickerConfig.kt */
    public final class IndexedStyle {
        public static final int $stable = 8;
        private final int groupIndex;
        private final int presetIndex;
        private final ClockAxisStyle style;

        public IndexedStyle(int i, int i2, ClockAxisStyle clockAxisStyle) {
            clockAxisStyle.getClass();
            this.groupIndex = i;
            this.presetIndex = i2;
            this.style = clockAxisStyle;
        }

        public static /* synthetic */ IndexedStyle copy$default(IndexedStyle indexedStyle, int i, int i2, ClockAxisStyle clockAxisStyle, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = indexedStyle.groupIndex;
            }
            if ((i3 & 2) != 0) {
                i2 = indexedStyle.presetIndex;
            }
            if ((i3 & 4) != 0) {
                clockAxisStyle = indexedStyle.style;
            }
            return indexedStyle.copy(i, i2, clockAxisStyle);
        }

        public final int component1() {
            return this.groupIndex;
        }

        public final int component2() {
            return this.presetIndex;
        }

        public final ClockAxisStyle component3() {
            return this.style;
        }

        public final IndexedStyle copy(int i, int i2, ClockAxisStyle clockAxisStyle) {
            clockAxisStyle.getClass();
            return new IndexedStyle(i, i2, clockAxisStyle);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IndexedStyle)) {
                return false;
            }
            IndexedStyle indexedStyle = (IndexedStyle) obj;
            return this.groupIndex == indexedStyle.groupIndex && this.presetIndex == indexedStyle.presetIndex && Intrinsics.areEqual(this.style, indexedStyle.style);
        }

        public final int getGroupIndex() {
            return this.groupIndex;
        }

        public final int getPresetIndex() {
            return this.presetIndex;
        }

        public final ClockAxisStyle getStyle() {
            return this.style;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.groupIndex) * 31) + Integer.hashCode(this.presetIndex)) * 31) + this.style.hashCode();
        }

        public String toString() {
            return "IndexedStyle(groupIndex=" + this.groupIndex + ", presetIndex=" + this.presetIndex + ", style=" + this.style + ")";
        }
    }

    public AxisPresetConfig(List list, IndexedStyle indexedStyle) {
        list.getClass();
        this.groups = list;
        this.current = indexedStyle;
    }

    public /* synthetic */ AxisPresetConfig(List list, IndexedStyle indexedStyle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? null : indexedStyle);
    }

    public static /* synthetic */ AxisPresetConfig copy$default(AxisPresetConfig axisPresetConfig, List list, IndexedStyle indexedStyle, int i, Object obj) {
        if ((i & 1) != 0) {
            list = axisPresetConfig.groups;
        }
        if ((i & 2) != 0) {
            indexedStyle = axisPresetConfig.current;
        }
        return axisPresetConfig.copy(list, indexedStyle);
    }

    public final List component1() {
        return this.groups;
    }

    public final IndexedStyle component2() {
        return this.current;
    }

    public final AxisPresetConfig copy(List list, IndexedStyle indexedStyle) {
        list.getClass();
        return new AxisPresetConfig(list, indexedStyle);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AxisPresetConfig)) {
            return false;
        }
        AxisPresetConfig axisPresetConfig = (AxisPresetConfig) obj;
        return Intrinsics.areEqual(this.groups, axisPresetConfig.groups) && Intrinsics.areEqual(this.current, axisPresetConfig.current);
    }

    public final IndexedStyle findStyle(ClockAxisStyle clockAxisStyle) {
        clockAxisStyle.getClass();
        int i = 0;
        for (Object obj : this.groups) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int i3 = 0;
            for (Object obj2 : ((Group) obj).getPresets()) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ClockAxisStyle clockAxisStyle2 = (ClockAxisStyle) obj2;
                if (Intrinsics.areEqual(clockAxisStyle2, clockAxisStyle)) {
                    return new IndexedStyle(i, i3, clockAxisStyle2);
                }
                i3 = i4;
            }
            i = i2;
        }
        return null;
    }

    public final IndexedStyle getCurrent() {
        return this.current;
    }

    public final List getGroups() {
        return this.groups;
    }

    public int hashCode() {
        int iHashCode = this.groups.hashCode() * 31;
        IndexedStyle indexedStyle = this.current;
        return iHashCode + (indexedStyle == null ? 0 : indexedStyle.hashCode());
    }

    public String toString() {
        return "AxisPresetConfig(groups=" + this.groups + ", current=" + this.current + ")";
    }
}
