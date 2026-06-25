package androidx.fragment.app;

import android.view.View;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class M implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1576a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1577b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1578c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1579d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1580e;

    public M(int i2, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.f1576a = i2;
        this.f1577b = arrayList;
        this.f1578c = arrayList2;
        this.f1579d = arrayList3;
        this.f1580e = arrayList4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        for (int i2 = 0; i2 < this.f1576a; i2++) {
            View view = (View) this.f1577b.get(i2);
            String str = (String) this.f1578c.get(i2);
            WeakHashMap weakHashMap = v.l.f2836a;
            view.setTransitionName(str);
            ((View) this.f1579d.get(i2)).setTransitionName((String) this.f1580e.get(i2));
        }
    }
}
