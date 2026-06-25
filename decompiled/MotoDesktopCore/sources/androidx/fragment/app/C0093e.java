package androidx.fragment.app;

/* JADX INFO: renamed from: androidx.fragment.app.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0093e implements androidx.lifecycle.d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1616a;

    public C0093e(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        this.f1616a = abstractComponentCallbacksC0098j;
    }

    @Override // androidx.lifecycle.d
    public final androidx.lifecycle.c getLifecycle() {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1616a;
        if (abstractComponentCallbacksC0098j.mViewLifecycleRegistry == null) {
            abstractComponentCallbacksC0098j.mViewLifecycleRegistry = new androidx.lifecycle.f(abstractComponentCallbacksC0098j.mViewLifecycleOwner);
        }
        return abstractComponentCallbacksC0098j.mViewLifecycleRegistry;
    }
}
