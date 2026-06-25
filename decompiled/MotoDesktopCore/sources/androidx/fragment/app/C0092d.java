package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/* JADX INFO: renamed from: androidx.fragment.app.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0092d extends v {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1615b;

    public C0092d(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        this.f1615b = abstractComponentCallbacksC0098j;
    }

    @Override // androidx.fragment.app.v
    public final AbstractComponentCallbacksC0098j a(Context context, String str, Bundle bundle) {
        this.f1615b.mHost.getClass();
        return AbstractComponentCallbacksC0098j.instantiate(context, str, bundle);
    }

    @Override // androidx.fragment.app.v
    public final View b(int i2) {
        View view = this.f1615b.mView;
        if (view != null) {
            return view.findViewById(i2);
        }
        throw new IllegalStateException("Fragment does not have a view");
    }

    @Override // androidx.fragment.app.v
    public final boolean c() {
        return this.f1615b.mView != null;
    }
}
