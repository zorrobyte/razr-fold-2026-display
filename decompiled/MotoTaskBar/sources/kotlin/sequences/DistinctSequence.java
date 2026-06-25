package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DistinctSequence implements Sequence {
    private final Function1 keySelector;
    private final Sequence source;

    public DistinctSequence(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        this.source = sequence;
        this.keySelector = function1;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator iterator() {
        return new DistinctIterator(this.source.iterator(), this.keySelector);
    }
}
