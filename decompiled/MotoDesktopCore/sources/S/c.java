package s;

import android.os.Handler;
import androidx.appcompat.widget.C0084v;

/* JADX INFO: loaded from: classes.dex */
public final class c implements i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0084v f2799a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Handler f2800b = null;

    public c(C0084v c0084v) {
        this.f2799a = c0084v;
    }

    @Override // s.i
    public final void a(Object obj) {
        f fVar = (f) obj;
        Handler handler = this.f2800b;
        C0084v c0084v = this.f2799a;
        if (fVar == null) {
            c0084v.b(1, handler);
            return;
        }
        int i2 = fVar.f2808b;
        if (i2 == 0) {
            c0084v.c(fVar.f2807a, handler);
        } else {
            c0084v.b(i2, handler);
        }
    }
}
