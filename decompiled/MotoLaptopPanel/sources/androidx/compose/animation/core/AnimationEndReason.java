package androidx.compose.animation.core;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: AnimationEndReason.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AnimationEndReason {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ AnimationEndReason[] $VALUES;
    public static final AnimationEndReason BoundReached = new AnimationEndReason("BoundReached", 0);
    public static final AnimationEndReason Finished = new AnimationEndReason("Finished", 1);

    private static final /* synthetic */ AnimationEndReason[] $values() {
        return new AnimationEndReason[]{BoundReached, Finished};
    }

    static {
        AnimationEndReason[] animationEndReasonArr$values = $values();
        $VALUES = animationEndReasonArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(animationEndReasonArr$values);
    }

    private AnimationEndReason(String str, int i) {
    }

    public static AnimationEndReason valueOf(String str) {
        return (AnimationEndReason) Enum.valueOf(AnimationEndReason.class, str);
    }

    public static AnimationEndReason[] values() {
        return (AnimationEndReason[]) $VALUES.clone();
    }
}
