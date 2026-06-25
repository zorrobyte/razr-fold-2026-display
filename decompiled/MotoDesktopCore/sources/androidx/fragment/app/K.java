package androidx.fragment.app;

import android.transition.Transition;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class K implements Transition.TransitionListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Object f1569a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1570b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f1571c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1572d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f1573e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1574f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ L f1575g;

    public K(L l2, Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
        this.f1575g = l2;
        this.f1569a = obj;
        this.f1570b = arrayList;
        this.f1571c = obj2;
        this.f1572d = arrayList2;
        this.f1573e = obj3;
        this.f1574f = arrayList3;
    }

    @Override // android.transition.Transition.TransitionListener
    public final void onTransitionCancel(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public final void onTransitionEnd(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public final void onTransitionPause(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public final void onTransitionResume(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public final void onTransitionStart(Transition transition) {
        L l2 = this.f1575g;
        Object obj = this.f1569a;
        if (obj != null) {
            l2.n(obj, this.f1570b, null);
        }
        Object obj2 = this.f1571c;
        if (obj2 != null) {
            l2.n(obj2, this.f1572d, null);
        }
        Object obj3 = this.f1573e;
        if (obj3 != null) {
            l2.n(obj3, this.f1574f, null);
        }
    }
}
