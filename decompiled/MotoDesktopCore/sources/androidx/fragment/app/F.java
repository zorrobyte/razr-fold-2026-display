package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import h.C0135b;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class F implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ O f1513a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0135b f1514b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f1515c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ G f1516d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1517e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ View f1518f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1519g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1520h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final /* synthetic */ boolean f1521i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1522j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final /* synthetic */ Object f1523k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final /* synthetic */ Rect f1524l;

    public F(O o2, C0135b c0135b, Object obj, G g2, ArrayList arrayList, View view, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2, boolean z2, ArrayList arrayList2, Object obj2, Rect rect) {
        this.f1513a = o2;
        this.f1514b = c0135b;
        this.f1515c = obj;
        this.f1516d = g2;
        this.f1517e = arrayList;
        this.f1518f = view;
        this.f1519g = abstractComponentCallbacksC0098j;
        this.f1520h = abstractComponentCallbacksC0098j2;
        this.f1521i = z2;
        this.f1522j = arrayList2;
        this.f1523k = obj2;
        this.f1524l = rect;
    }

    @Override // java.lang.Runnable
    public final void run() {
        O o2 = this.f1513a;
        C0135b c0135b = this.f1514b;
        Object obj = this.f1515c;
        G g2 = this.f1516d;
        C0135b c0135bC = H.c(o2, c0135b, obj, g2);
        ArrayList arrayList = this.f1517e;
        if (c0135bC != null) {
            arrayList.addAll(c0135bC.values());
            arrayList.add(this.f1518f);
        }
        boolean z2 = this.f1521i;
        if (z2) {
            this.f1520h.getEnterTransitionCallback();
        } else {
            this.f1519g.getEnterTransitionCallback();
        }
        if (obj != null) {
            o2.t(obj, this.f1522j, arrayList);
            View viewG = H.g(c0135bC, g2, this.f1523k, z2);
            if (viewG != null) {
                O.i(this.f1524l, viewG);
            }
        }
    }
}
