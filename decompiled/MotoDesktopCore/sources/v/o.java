package v;

import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public final class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f2838a;

    public o(Object obj) {
        this.f2838a = obj;
    }

    public final int a() {
        return ((WindowInsets) this.f2838a).getSystemWindowInsetLeft();
    }

    public final int b() {
        return ((WindowInsets) this.f2838a).getSystemWindowInsetRight();
    }

    public final int c() {
        return ((WindowInsets) this.f2838a).getSystemWindowInsetTop();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || o.class != obj.getClass()) {
            return false;
        }
        Object obj2 = ((o) obj).f2838a;
        Object obj3 = this.f2838a;
        return obj3 == null ? obj2 == null : obj3.equals(obj2);
    }

    public final int hashCode() {
        Object obj = this.f2838a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
