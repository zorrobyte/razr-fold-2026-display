package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: classes2.dex */
final class GeneratorSequence implements Sequence {
    private final Function0 getInitialValue;
    private final Function1 getNextValue;

    /* JADX INFO: renamed from: kotlin.sequences.GeneratorSequence$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: Sequences.kt */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        private Object nextItem;
        private int nextState = -2;

        AnonymousClass1() {
        }

        private final void calcNext() {
            Object objInvoke;
            if (this.nextState == -2) {
                objInvoke = GeneratorSequence.this.getInitialValue.mo2224invoke();
            } else {
                Function1 function1 = GeneratorSequence.this.getNextValue;
                Object obj = this.nextItem;
                obj.getClass();
                objInvoke = function1.invoke(obj);
            }
            this.nextItem = objInvoke;
            this.nextState = objInvoke == null ? 0 : 1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextState < 0) {
                calcNext();
            }
            return this.nextState == 1;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.nextState < 0) {
                calcNext();
            }
            if (this.nextState == 0) {
                throw new NoSuchElementException();
            }
            Object obj = this.nextItem;
            obj.getClass();
            this.nextState = -1;
            return obj;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public GeneratorSequence(Function0 function0, Function1 function1) {
        function0.getClass();
        function1.getClass();
        this.getInitialValue = function0;
        this.getNextValue = function1;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator iterator() {
        return new AnonymousClass1();
    }
}
