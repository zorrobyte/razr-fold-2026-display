package s;

import android.content.Context;
import android.graphics.Typeface;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Callable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Context f2795a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0158a f2796b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f2797c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ String f2798d;

    public b(Context context, C0158a c0158a, int i2, String str) {
        this.f2795a = context;
        this.f2796b = c0158a;
        this.f2797c = i2;
        this.f2798d = str;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        f fVarB = g.b(this.f2795a, this.f2796b, this.f2797c);
        Typeface typeface = fVarB.f2807a;
        if (typeface != null) {
            g.f2809a.b(this.f2798d, typeface);
        }
        return fVarB;
    }
}
