package androidx.compose.runtime;

import java.util.Set;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: CompositionContext.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositionContext {
    public abstract void composeInitial$runtime_release(ControlledComposition controlledComposition, Function2 function2);

    public void doneComposing$runtime_release() {
    }

    public abstract boolean getCollectingCallByInformation$runtime_release();

    public abstract boolean getCollectingParameterInformation$runtime_release();

    public abstract boolean getCollectingSourceInformation$runtime_release();

    public abstract Composition getComposition$runtime_release();

    public PersistentCompositionLocalMap getCompositionLocalScope$runtime_release() {
        return CompositionContextKt.EmptyPersistentCompositionLocalMap;
    }

    public abstract int getCompoundHashKey$runtime_release();

    public abstract CoroutineContext getEffectCoroutineContext();

    public CompositionObserverHolder getObserverHolder$runtime_release() {
        return null;
    }

    public abstract void insertMovableContent$runtime_release(MovableContentStateReference movableContentStateReference);

    public abstract void invalidate$runtime_release(ControlledComposition controlledComposition);

    public abstract MovableContentState movableContentStateResolve$runtime_release(MovableContentStateReference movableContentStateReference);

    public abstract void recordInspectionTable$runtime_release(Set set);

    public void registerComposer$runtime_release(Composer composer) {
    }

    public void startComposing$runtime_release() {
    }

    public void unregisterComposer$runtime_release(Composer composer) {
    }

    public abstract void unregisterComposition$runtime_release(ControlledComposition controlledComposition);
}
