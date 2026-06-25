package androidx.fragment.app;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class x implements w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f1660a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f1661b = 1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ z f1662c;

    public x(z zVar, int i2) {
        this.f1662c = zVar;
        this.f1660a = i2;
    }

    @Override // androidx.fragment.app.w
    public final boolean a(ArrayList arrayList, ArrayList arrayList2) {
        AbstractC0103o abstractC0103oPeekChildFragmentManager;
        z zVar = this.f1662c;
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = zVar.f1685o;
        int i2 = this.f1660a;
        if (abstractComponentCallbacksC0098j == null || i2 >= 0 || (abstractC0103oPeekChildFragmentManager = abstractComponentCallbacksC0098j.peekChildFragmentManager()) == null || !abstractC0103oPeekChildFragmentManager.a()) {
            return zVar.U(arrayList, arrayList2, i2, this.f1661b);
        }
        return false;
    }
}
