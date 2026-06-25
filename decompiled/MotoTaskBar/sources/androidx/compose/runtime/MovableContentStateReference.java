package androidx.compose.runtime;

import java.util.List;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MovableContentStateReference {
    private final Anchor anchor;
    private final ControlledComposition composition;
    private List invalidations;
    private final PersistentCompositionLocalMap locals;
    private final List nestedReferences;
    private final Object parameter;
    private final SlotTable slotTable;

    public MovableContentStateReference(MovableContent movableContent, Object obj, ControlledComposition controlledComposition, SlotTable slotTable, Anchor anchor, List list, PersistentCompositionLocalMap persistentCompositionLocalMap, List list2) {
        this.parameter = obj;
        this.composition = controlledComposition;
        this.slotTable = slotTable;
        this.anchor = anchor;
        this.invalidations = list;
        this.locals = persistentCompositionLocalMap;
        this.nestedReferences = list2;
    }

    public final Anchor getAnchor$runtime_release() {
        return this.anchor;
    }

    public final ControlledComposition getComposition$runtime_release() {
        return this.composition;
    }

    public final MovableContent getContent$runtime_release() {
        return null;
    }

    public final List getInvalidations$runtime_release() {
        return this.invalidations;
    }

    public final PersistentCompositionLocalMap getLocals$runtime_release() {
        return this.locals;
    }

    public final Object getParameter$runtime_release() {
        return this.parameter;
    }

    public final SlotTable getSlotTable$runtime_release() {
        return this.slotTable;
    }
}
