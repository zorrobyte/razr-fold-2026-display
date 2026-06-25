package androidx.fragment.app;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class D implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Object f1499a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ O f1500b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ View f1501c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1502d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1503e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1504f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1505g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ Object f1506h;

    public D(Object obj, O o2, View view, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object obj2) {
        this.f1499a = obj;
        this.f1500b = o2;
        this.f1501c = view;
        this.f1502d = abstractComponentCallbacksC0098j;
        this.f1503e = arrayList;
        this.f1504f = arrayList2;
        this.f1505g = arrayList3;
        this.f1506h = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        O o2 = this.f1500b;
        View view = this.f1501c;
        Object obj = this.f1499a;
        if (obj != null) {
            o2.m(view, obj);
            this.f1504f.addAll(H.f(o2, obj, this.f1502d, this.f1503e, view));
        }
        ArrayList arrayList = this.f1505g;
        if (arrayList != null) {
            Object obj2 = this.f1506h;
            if (obj2 != null) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(view);
                o2.n(obj2, arrayList, arrayList2);
            }
            arrayList.clear();
            arrayList.add(view);
        }
    }
}
