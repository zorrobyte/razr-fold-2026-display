package kotlin.sequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _Sequences.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SequencesKt___SequencesKt extends SequencesKt___SequencesJvmKt {

    /* JADX INFO: renamed from: kotlin.sequences.SequencesKt___SequencesKt$flatMap$1, reason: invalid class name */
    /* JADX INFO: compiled from: _Sequences.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1, Iterable.class, "iterator", "iterator()Ljava/util/Iterator;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Iterator invoke(Iterable iterable) {
            iterable.getClass();
            return iterable.iterator();
        }
    }

    /* JADX INFO: renamed from: kotlin.sequences.SequencesKt___SequencesKt$flatMap$2, reason: invalid class name */
    /* JADX INFO: compiled from: _Sequences.kt */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(1, Sequence.class, "iterator", "iterator()Ljava/util/Iterator;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Iterator invoke(Sequence sequence) {
            sequence.getClass();
            return sequence.iterator();
        }
    }

    public static Iterable asIterable(Sequence sequence) {
        sequence.getClass();
        return new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(sequence);
    }

    public static Sequence distinct(Sequence sequence) {
        sequence.getClass();
        return distinctBy(sequence, new Function1() { // from class: kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SequencesKt___SequencesKt.distinct$lambda$13$SequencesKt___SequencesKt(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object distinct$lambda$13$SequencesKt___SequencesKt(Object obj) {
        return obj;
    }

    public static final Sequence distinctBy(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new DistinctSequence(sequence, function1);
    }

    public static Object elementAtOrElse(Sequence sequence, int i, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        if (i < 0) {
            return function1.invoke(Integer.valueOf(i));
        }
        int i2 = 0;
        for (Object obj : sequence) {
            int i3 = i2 + 1;
            if (i == i2) {
                return obj;
            }
            i2 = i3;
        }
        return function1.invoke(Integer.valueOf(i));
    }

    public static Sequence filter(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new FilteringSequence(sequence, true, function1);
    }

    public static Sequence filterNot(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new FilteringSequence(sequence, false, function1);
    }

    public static Sequence filterNotNull(Sequence sequence) {
        sequence.getClass();
        Sequence sequenceFilterNot = filterNot(sequence, new Function1() { // from class: kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(SequencesKt___SequencesKt.filterNotNull$lambda$5$SequencesKt___SequencesKt(obj));
            }
        });
        sequenceFilterNot.getClass();
        return sequenceFilterNot;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean filterNotNull$lambda$5$SequencesKt___SequencesKt(Object obj) {
        return obj == null;
    }

    public static Object firstOrNull(Sequence sequence) {
        sequence.getClass();
        Iterator it = sequence.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public static Sequence flatMap(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new FlatteningSequence(sequence, function1, AnonymousClass2.INSTANCE);
    }

    public static Sequence flatMapIterable(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new FlatteningSequence(sequence, function1, AnonymousClass1.INSTANCE);
    }

    public static Object last(Sequence sequence) {
        sequence.getClass();
        Iterator it = sequence.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException("Sequence is empty.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            next = it.next();
        }
        return next;
    }

    public static Sequence map(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new TransformingSequence(sequence, function1);
    }

    public static Sequence mapIndexed(Sequence sequence, Function2 function2) {
        sequence.getClass();
        function2.getClass();
        return new TransformingIndexedSequence(sequence, function2);
    }

    public static Sequence mapNotNull(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return filterNotNull(new TransformingSequence(sequence, function1));
    }

    public static Sequence plus(Sequence sequence, Sequence sequence2) {
        sequence.getClass();
        sequence2.getClass();
        return SequencesKt__SequencesKt.flatten(SequencesKt__SequencesKt.sequenceOf(sequence, sequence2));
    }

    public static Sequence sortedWith(final Sequence sequence, final Comparator comparator) {
        sequence.getClass();
        comparator.getClass();
        return new Sequence() { // from class: kotlin.sequences.SequencesKt___SequencesKt.sortedWith.1
            @Override // kotlin.sequences.Sequence
            public Iterator iterator() {
                List mutableList = SequencesKt___SequencesKt.toMutableList(sequence);
                CollectionsKt.sortWith(mutableList, comparator);
                return mutableList.iterator();
            }
        };
    }

    public static final Collection toCollection(Sequence sequence, Collection collection) {
        sequence.getClass();
        collection.getClass();
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            collection.add(it.next());
        }
        return collection;
    }

    public static List toList(Sequence sequence) {
        sequence.getClass();
        Iterator it = sequence.iterator();
        if (!it.hasNext()) {
            return CollectionsKt.emptyList();
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return CollectionsKt.listOf(next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(next);
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static final List toMutableList(Sequence sequence) {
        sequence.getClass();
        return (List) toCollection(sequence, new ArrayList());
    }

    public static Set toSet(Sequence sequence) {
        sequence.getClass();
        Iterator it = sequence.iterator();
        if (!it.hasNext()) {
            return SetsKt.emptySet();
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return SetsKt.setOf(next);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(next);
        while (it.hasNext()) {
            linkedHashSet.add(it.next());
        }
        return linkedHashSet;
    }

    public static Sequence zip(Sequence sequence, Sequence sequence2) {
        sequence.getClass();
        sequence2.getClass();
        return new MergingSequence(sequence, sequence2, new Function2() { // from class: kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return TuplesKt.to(obj, obj2);
            }
        });
    }
}
