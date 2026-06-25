package androidx.core.util;

/* JADX INFO: compiled from: Pools.kt */
/* JADX INFO: loaded from: classes.dex */
public class Pools$SynchronizedPool extends Pools$SimplePool {
    private final Object lock;

    public Pools$SynchronizedPool(int i) {
        super(i);
        this.lock = new Object();
    }

    @Override // androidx.core.util.Pools$SimplePool, androidx.core.util.Pools$Pool
    public Object acquire() {
        Object objAcquire;
        synchronized (this.lock) {
            objAcquire = super.acquire();
        }
        return objAcquire;
    }

    @Override // androidx.core.util.Pools$SimplePool, androidx.core.util.Pools$Pool
    public boolean release(Object obj) {
        boolean zRelease;
        obj.getClass();
        synchronized (this.lock) {
            zRelease = super.release(obj);
        }
        return zRelease;
    }
}
