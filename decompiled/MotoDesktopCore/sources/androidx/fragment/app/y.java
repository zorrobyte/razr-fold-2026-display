package androidx.fragment.app;

/* JADX INFO: loaded from: classes.dex */
public final class y implements InterfaceC0096h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final boolean f1663a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0090b f1664b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1665c;

    public y(C0090b c0090b, boolean z2) {
        this.f1663a = z2;
        this.f1664b = c0090b;
    }

    public final void a() {
        boolean z2 = this.f1665c > 0;
        C0090b c0090b = this.f1664b;
        z zVar = c0090b.f1596a;
        int size = zVar.f1674d.size();
        for (int i2 = 0; i2 < size; i2++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) zVar.f1674d.get(i2);
            abstractComponentCallbacksC0098j.setOnStartEnterTransitionListener(null);
            if (z2 && abstractComponentCallbacksC0098j.isPostponed()) {
                abstractComponentCallbacksC0098j.startPostponedEnterTransition();
            }
        }
        c0090b.f1596a.g(c0090b, this.f1663a, !z2, true);
    }
}
