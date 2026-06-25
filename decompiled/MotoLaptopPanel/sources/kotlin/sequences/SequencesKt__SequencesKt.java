package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    public static Sequence asSequence(final Iterator it) {
        it.getClass();
        return constrainOnce(new Sequence() { // from class: kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator iterator() {
                return it;
            }
        });
    }

    public static final Sequence constrainOnce(Sequence sequence) {
        sequence.getClass();
        return sequence instanceof ConstrainedOnceSequence ? sequence : new ConstrainedOnceSequence(sequence);
    }

    public static Sequence generateSequence(final Object obj, Function1 function1) {
        function1.getClass();
        return obj == null ? EmptySequence.INSTANCE : new GeneratorSequence(new Function0() { // from class: kotlin.sequences.SequencesKt__SequencesKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SequencesKt__SequencesKt.generateSequence$lambda$5$SequencesKt__SequencesKt(obj);
            }
        }, function1);
    }

    public static Sequence generateSequence(final Function0 function0) {
        function0.getClass();
        return constrainOnce(new GeneratorSequence(function0, new Function1() { // from class: kotlin.sequences.SequencesKt__SequencesKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SequencesKt__SequencesKt.generateSequence$lambda$4$SequencesKt__SequencesKt(function0, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object generateSequence$lambda$4$SequencesKt__SequencesKt(Function0 function0, Object obj) {
        obj.getClass();
        return function0.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object generateSequence$lambda$5$SequencesKt__SequencesKt(Object obj) {
        return obj;
    }
}
