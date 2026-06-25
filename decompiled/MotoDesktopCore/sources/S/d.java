package s;

import h.k;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class d implements i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f2801a;

    public d(String str) {
        this.f2801a = str;
    }

    @Override // s.i
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final void a(f fVar) {
        synchronized (g.f2811c) {
            try {
                k kVar = g.f2812d;
                ArrayList arrayList = (ArrayList) kVar.get(this.f2801a);
                if (arrayList == null) {
                    return;
                }
                kVar.remove(this.f2801a);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ((i) arrayList.get(i2)).a(fVar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
