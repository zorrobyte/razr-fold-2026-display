package kotlin.sequences;

import java.util.HashSet;
import java.util.Iterator;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: classes2.dex */
final class DistinctIterator extends AbstractIterator {
    private final Function1 keySelector;
    private final HashSet observed;
    private final Iterator source;

    public DistinctIterator(Iterator it, Function1 function1) {
        it.getClass();
        function1.getClass();
        this.source = it;
        this.keySelector = function1;
        this.observed = new HashSet();
    }

    @Override // kotlin.collections.AbstractIterator
    protected void computeNext() {
        while (this.source.hasNext()) {
            Object next = this.source.next();
            if (this.observed.add(this.keySelector.invoke(next))) {
                setNext(next);
                return;
            }
        }
        done();
    }
}
