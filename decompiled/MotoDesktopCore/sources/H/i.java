package h;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class i implements Iterator, Map.Entry {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2600a;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ C0134a f2603d;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f2602c = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2601b = -1;

    public i(C0134a c0134a) {
        this.f2603d = c0134a;
        this.f2600a = c0134a.d() - 1;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!this.f2602c) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        int i2 = this.f2601b;
        C0134a c0134a = this.f2603d;
        Object objB = c0134a.b(i2, 0);
        if (key != objB && (key == null || !key.equals(objB))) {
            return false;
        }
        Object value = entry.getValue();
        Object objB2 = c0134a.b(this.f2601b, 1);
        return value == objB2 || (value != null && value.equals(objB2));
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        if (!this.f2602c) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return this.f2603d.b(this.f2601b, 0);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (!this.f2602c) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return this.f2603d.b(this.f2601b, 1);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f2601b < this.f2600a;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        if (!this.f2602c) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        int i2 = this.f2601b;
        C0134a c0134a = this.f2603d;
        Object objB = c0134a.b(i2, 0);
        Object objB2 = c0134a.b(this.f2601b, 1);
        return (objB == null ? 0 : objB.hashCode()) ^ (objB2 != null ? objB2.hashCode() : 0);
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.f2601b++;
        this.f2602c = true;
        return this;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.f2602c) {
            throw new IllegalStateException();
        }
        this.f2603d.h(this.f2601b);
        this.f2601b--;
        this.f2600a--;
        this.f2602c = false;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (this.f2602c) {
            return this.f2603d.i(this.f2601b, obj);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
