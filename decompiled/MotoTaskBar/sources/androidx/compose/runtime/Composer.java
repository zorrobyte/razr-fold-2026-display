package androidx.compose.runtime;

import androidx.compose.runtime.tooling.CompositionData;
import kotlin.coroutines.CoroutineContext;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Composer {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: Composer.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final Object Empty = new Object() { // from class: androidx.compose.runtime.Composer$Companion$Empty$1
            public String toString() {
                return "Empty";
            }
        };

        private Companion() {
        }

        public final Object getEmpty() {
            return Empty;
        }
    }

    boolean changed(Object obj);

    boolean changedInstance(Object obj);

    void collectParameterInformation();

    Object consume(CompositionLocal compositionLocal);

    void endProvider();

    void endProviders();

    void endReplaceGroup();

    ScopeUpdateScope endRestartGroup();

    CoroutineContext getApplyCoroutineContext();

    CompositionData getCompositionData();

    RecomposeScope getRecomposeScope();

    void recordUsed(RecomposeScope recomposeScope);

    Object rememberedValue();

    boolean shouldExecute(boolean z, int i);

    void skipToGroupEnd();

    void startProvider(ProvidedValue providedValue);

    void startProviders(ProvidedValue[] providedValueArr);

    void startReplaceGroup(int i);

    Composer startRestartGroup(int i);

    void updateRememberedValue(Object obj);
}
