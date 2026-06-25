package h;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class g implements Iterator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f2593a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2594b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2595c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2596d = false;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ C0134a f2597e;

    public g(C0134a c0134a, int i2) {
        this.f2597e = c0134a;
        this.f2593a = i2;
        this.f2594b = c0134a.d();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f2595c < this.f2594b;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object objB = this.f2597e.b(this.f2595c, this.f2593a);
        this.f2595c++;
        this.f2596d = true;
        return objB;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.f2596d) {
            throw new IllegalStateException();
        }
        int i2 = this.f2595c - 1;
        this.f2595c = i2;
        this.f2594b--;
        this.f2596d = false;
        this.f2597e.h(i2);
    }
}
