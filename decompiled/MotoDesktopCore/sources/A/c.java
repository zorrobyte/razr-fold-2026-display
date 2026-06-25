package A;

import Y.r;
import androidx.lifecycle.d;
import androidx.lifecycle.j;

/* JADX INFO: loaded from: classes.dex */
public final class c extends a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final d f1a;

    public c(d dVar, j jVar) {
        this.f1a = dVar;
        String canonicalName = b.class.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        String strConcat = "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName);
        if (b.class.isInstance((b) jVar.f1716a.get(strConcat))) {
            return;
        }
        b bVar = (b) jVar.f1716a.put(strConcat, new b());
        if (bVar != null) {
            bVar.a();
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        r.c(this.f1a, sb);
        sb.append("}}");
        return sb.toString();
    }
}
