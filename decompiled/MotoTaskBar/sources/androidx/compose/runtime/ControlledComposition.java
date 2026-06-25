package androidx.compose.runtime;

import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Composition.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ControlledComposition extends Composition {
    void abandonChanges();

    void applyChanges();

    void applyLateChanges();

    void changesApplied();

    void composeContent(Function2 function2);

    Object delegateInvalidations(ControlledComposition controlledComposition, int i, Function0 function0);

    void disposeUnusedMovableContent(MovableContentState movableContentState);

    void insertMovableContent(List list);

    void invalidateAll();

    boolean isComposing();

    boolean observesAnyOf(Set set);

    void prepareCompose(Function0 function0);

    boolean recompose();

    void recordModificationsOf(Set set);

    void recordReadOf(Object obj);

    void recordWriteOf(Object obj);
}
