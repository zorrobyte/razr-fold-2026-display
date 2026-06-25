package I;

import android.view.View;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public final class i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f179a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f180b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f181c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f182d;

    public i(View view) {
        this.f179a = view;
    }

    public final boolean a(int i2) {
        if (this.f182d == i2) {
            return false;
        }
        this.f182d = i2;
        b();
        return true;
    }

    public final void b() {
        int i2 = this.f182d;
        View view = this.f179a;
        int top = i2 - (view.getTop() - this.f180b);
        WeakHashMap weakHashMap = l.f2836a;
        view.offsetTopAndBottom(top);
        view.offsetLeftAndRight(0 - (view.getLeft() - this.f181c));
    }
}
