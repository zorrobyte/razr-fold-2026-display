package androidx.compose.material3;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final class SliderComponents {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ SliderComponents[] $VALUES;
    public static final SliderComponents THUMB = new SliderComponents("THUMB", 0);
    public static final SliderComponents TRACK = new SliderComponents("TRACK", 1);

    private static final /* synthetic */ SliderComponents[] $values() {
        return new SliderComponents[]{THUMB, TRACK};
    }

    static {
        SliderComponents[] sliderComponentsArr$values = $values();
        $VALUES = sliderComponentsArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(sliderComponentsArr$values);
    }

    private SliderComponents(String str, int i) {
    }

    public static SliderComponents valueOf(String str) {
        return (SliderComponents) Enum.valueOf(SliderComponents.class, str);
    }

    public static SliderComponents[] values() {
        return (SliderComponents[]) $VALUES.clone();
    }
}
