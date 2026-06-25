package s;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public final class h implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AtomicReference f2814a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Callable f2815b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ReentrantLock f2816c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ AtomicBoolean f2817d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Condition f2818e;

    public h(AtomicReference atomicReference, b bVar, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
        this.f2814a = atomicReference;
        this.f2815b = bVar;
        this.f2816c = reentrantLock;
        this.f2817d = atomicBoolean;
        this.f2818e = condition;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f2814a.set(this.f2815b.call());
        } catch (Exception unused) {
        }
        ReentrantLock reentrantLock = this.f2816c;
        reentrantLock.lock();
        try {
            this.f2817d.set(false);
            this.f2818e.signal();
        } finally {
            reentrantLock.unlock();
        }
    }
}
