package androidx.compose.ui.node;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: TraversableNode.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraversableNode$Companion$TraverseDescendantsAction {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ TraversableNode$Companion$TraverseDescendantsAction[] $VALUES;
    public static final TraversableNode$Companion$TraverseDescendantsAction ContinueTraversal = new TraversableNode$Companion$TraverseDescendantsAction("ContinueTraversal", 0);
    public static final TraversableNode$Companion$TraverseDescendantsAction SkipSubtreeAndContinueTraversal = new TraversableNode$Companion$TraverseDescendantsAction("SkipSubtreeAndContinueTraversal", 1);
    public static final TraversableNode$Companion$TraverseDescendantsAction CancelTraversal = new TraversableNode$Companion$TraverseDescendantsAction("CancelTraversal", 2);

    private static final /* synthetic */ TraversableNode$Companion$TraverseDescendantsAction[] $values() {
        return new TraversableNode$Companion$TraverseDescendantsAction[]{ContinueTraversal, SkipSubtreeAndContinueTraversal, CancelTraversal};
    }

    static {
        TraversableNode$Companion$TraverseDescendantsAction[] traversableNode$Companion$TraverseDescendantsActionArr$values = $values();
        $VALUES = traversableNode$Companion$TraverseDescendantsActionArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(traversableNode$Companion$TraverseDescendantsActionArr$values);
    }

    private TraversableNode$Companion$TraverseDescendantsAction(String str, int i) {
    }

    public static TraversableNode$Companion$TraverseDescendantsAction valueOf(String str) {
        return (TraversableNode$Companion$TraverseDescendantsAction) Enum.valueOf(TraversableNode$Companion$TraverseDescendantsAction.class, str);
    }

    public static TraversableNode$Companion$TraverseDescendantsAction[] values() {
        return (TraversableNode$Companion$TraverseDescendantsAction[]) $VALUES.clone();
    }
}
