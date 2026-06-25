package androidx.compose.ui.node;

import java.util.Comparator;
import java.util.TreeSet;

/* JADX INFO: compiled from: JvmTreeSet.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SortedSet extends TreeSet {
    public SortedSet(Comparator comparator) {
        super(comparator);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    @Override // java.util.TreeSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ int size() {
        return getSize();
    }
}
