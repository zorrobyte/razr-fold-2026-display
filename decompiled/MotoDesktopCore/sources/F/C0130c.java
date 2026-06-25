package f;

import java.util.Iterator;

/* JADX INFO: renamed from: f.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0130c implements Iterator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f2529a = true;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0131d f2530b;

    public C0130c(C0131d c0131d) {
        this.f2530b = c0131d;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f2529a) {
            this.f2530b.getClass();
        }
        return false;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!this.f2529a) {
            return null;
        }
        this.f2529a = false;
        this.f2530b.getClass();
        return null;
    }
}
