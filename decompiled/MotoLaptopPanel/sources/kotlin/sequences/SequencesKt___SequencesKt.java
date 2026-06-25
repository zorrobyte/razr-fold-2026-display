package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _Sequences.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SequencesKt___SequencesKt extends SequencesKt___SequencesJvmKt {
    public static Iterable asIterable(Sequence sequence) {
        sequence.getClass();
        return new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(sequence);
    }

    public static Sequence filter(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new FilteringSequence(sequence, true, function1);
    }

    public static Sequence map(Sequence sequence, Function1 function1) {
        sequence.getClass();
        function1.getClass();
        return new TransformingSequence(sequence, function1);
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
}
