package C;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import d.InterfaceC0124a;
import h.C0135b;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import q.InterfaceMenuItemC0157a;

/* JADX INFO: loaded from: classes.dex */
public final class z implements InterfaceC0124a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f105a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f106b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f107c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Object f108d;

    public z(int i2) {
        switch (i2) {
            case 2:
                this.f105a = new androidx.appcompat.app.f(10);
                this.f106b = new h.k();
                this.f107c = new ArrayList();
                this.f108d = new HashSet();
                break;
            case 3:
                this.f105a = new Object();
                this.f108d = new U.c(1, this);
                break;
            default:
                this.f105a = new C0135b();
                this.f107c = new SparseArray();
                this.f108d = new h.e();
                this.f106b = new C0135b();
                break;
        }
    }

    public z(Context context, ActionMode.Callback callback) {
        this.f106b = context;
        this.f105a = callback;
        this.f107c = new ArrayList();
        this.f108d = new h.k();
    }

    @Override // d.InterfaceC0124a
    public void a(d.b bVar) {
        ((ActionMode.Callback) this.f105a).onDestroyActionMode(f(bVar));
    }

    @Override // d.InterfaceC0124a
    public boolean b(d.b bVar, MenuItem menuItem) {
        return ((ActionMode.Callback) this.f105a).onActionItemClicked(f(bVar), new androidx.appcompat.view.menu.v((Context) this.f106b, (InterfaceMenuItemC0157a) menuItem));
    }

    @Override // d.InterfaceC0124a
    public boolean c(d.b bVar, androidx.appcompat.view.menu.o oVar) {
        d.f fVarF = f(bVar);
        h.k kVar = (h.k) this.f108d;
        Menu d2 = (Menu) kVar.get(oVar);
        if (d2 == null) {
            d2 = new androidx.appcompat.view.menu.D((Context) this.f106b, oVar);
            kVar.put(oVar, d2);
        }
        return ((ActionMode.Callback) this.f105a).onPrepareActionMode(fVarF, d2);
    }

    @Override // d.InterfaceC0124a
    public boolean d(d.b bVar, androidx.appcompat.view.menu.o oVar) {
        d.f fVarF = f(bVar);
        h.k kVar = (h.k) this.f108d;
        Menu d2 = (Menu) kVar.get(oVar);
        if (d2 == null) {
            d2 = new androidx.appcompat.view.menu.D((Context) this.f106b, oVar);
            kVar.put(oVar, d2);
        }
        return ((ActionMode.Callback) this.f105a).onCreateActionMode(fVarF, d2);
    }

    public void e(Object obj, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(obj)) {
            return;
        }
        if (hashSet.contains(obj)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(obj);
        ArrayList arrayList2 = (ArrayList) ((h.k) this.f106b).get(obj);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            for (int i2 = 0; i2 < size; i2++) {
                e(arrayList2.get(i2), arrayList, hashSet);
            }
        }
        hashSet.remove(obj);
        arrayList.add(obj);
    }

    public d.f f(d.b bVar) {
        ArrayList arrayList = (ArrayList) this.f107c;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            d.f fVar = (d.f) arrayList.get(i2);
            if (fVar != null && fVar.f2364b == bVar) {
                return fVar;
            }
        }
        d.f fVar2 = new d.f((Context) this.f106b, bVar);
        arrayList.add(fVar2);
        return fVar2;
    }

    public void g(Runnable runnable) {
        synchronized (this.f105a) {
            try {
                if (((HandlerThread) this.f106b) == null) {
                    HandlerThread handlerThread = new HandlerThread("fonts", 10);
                    this.f106b = handlerThread;
                    handlerThread.start();
                    this.f107c = new Handler(((HandlerThread) this.f106b).getLooper(), (U.c) this.f108d);
                }
                ((Handler) this.f107c).removeMessages(0);
                Handler handler = (Handler) this.f107c;
                handler.sendMessage(handler.obtainMessage(1, runnable));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Object h(s.b bVar, int i2) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditionNewCondition = reentrantLock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        g(new s.h(atomicReference, bVar, reentrantLock, atomicBoolean, conditionNewCondition));
        reentrantLock.lock();
        try {
            if (!atomicBoolean.get()) {
                return atomicReference.get();
            }
            long nanos = TimeUnit.MILLISECONDS.toNanos(i2);
            do {
                try {
                    nanos = conditionNewCondition.awaitNanos(nanos);
                } catch (InterruptedException unused) {
                }
                if (!atomicBoolean.get()) {
                    return atomicReference.get();
                }
            } while (nanos > 0);
            throw new InterruptedException("timeout");
        } finally {
            reentrantLock.unlock();
        }
    }
}
