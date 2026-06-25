package androidx.constraintlayout.core;

/* JADX INFO: loaded from: classes.dex */
interface Pools$Pool {
    Object acquire();

    boolean release(Object obj);

    void releaseAll(Object[] objArr, int i);
}
