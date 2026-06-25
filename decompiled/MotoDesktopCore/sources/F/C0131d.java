package f;

import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: f.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0131d implements Iterable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final WeakHashMap f2531a = new WeakHashMap();

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0131d)) {
            return false;
        }
        C0131d c0131d = (C0131d) obj;
        c0131d.getClass();
        iterator();
        c0131d.iterator();
        return true;
    }

    public final int hashCode() {
        iterator();
        return 0;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        C0129b c0129b = new C0129b();
        this.f2531a.put(c0129b, Boolean.FALSE);
        return c0129b;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        iterator();
        sb.append("]");
        return sb.toString();
    }
}
