package C;

import android.view.View;
import android.view.WindowId;

/* JADX INFO: loaded from: classes.dex */
public final class E {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final WindowId f25a;

    public E(View view) {
        this.f25a = view.getWindowId();
    }

    public final boolean equals(Object obj) {
        return (obj instanceof E) && ((E) obj).f25a.equals(this.f25a);
    }

    public final int hashCode() {
        return this.f25a.hashCode();
    }
}
