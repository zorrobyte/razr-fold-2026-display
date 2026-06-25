package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class TransformingSequence implements Sequence {
    private final Sequence sequence;
    private final Function1 transformer;

    /* JADX INFO: renamed from: kotlin.sequences.TransformingSequence$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: Sequences.kt */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        private final Iterator iterator;

        AnonymousClass1() {
            this.iterator = TransformingSequence.this.sequence.iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public Object next() {
            return TransformingSequence.this.transformer.invoke(this.iterator.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public TransformingSequence(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        this.sequence = sequence;
        this.transformer = function1;
    }

    public final Sequence flatten$kotlin_stdlib(Function1 function1) {
        function1.getClass();
        return new FlatteningSequence(this.sequence, this.transformer, function1);
    }

    @Override // kotlin.sequences.Sequence
    public Iterator iterator() {
        return new AnonymousClass1();
    }
}
