package androidx.appcompat.view.menu;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.widget.AppCompatSpinner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: androidx.appcompat.view.menu.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewTreeObserverOnGlobalLayoutListenerC0062f implements ViewTreeObserver.OnGlobalLayoutListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f734a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ E f735b;

    public /* synthetic */ ViewTreeObserverOnGlobalLayoutListenerC0062f(E e2, int i2) {
        this.f734a = i2;
        this.f735b = e2;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        E e2 = this.f735b;
        switch (this.f734a) {
            case 0:
                j jVar = (j) e2;
                if (jVar.isShowing()) {
                    ArrayList arrayList = jVar.f754i;
                    if (arrayList.size() > 0 && !((i) arrayList.get(0)).f742a.f1069x) {
                        View view = jVar.f761p;
                        if (view != null && view.isShown()) {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                ((i) it.next()).f742a.show();
                            }
                        } else {
                            jVar.dismiss();
                        }
                        break;
                    }
                }
                break;
            case 1:
                F f2 = (F) e2;
                if (f2.isShowing() && !f2.f682i.f1069x) {
                    View view2 = f2.f687n;
                    if (view2 != null && view2.isShown()) {
                        f2.f682i.show();
                    } else {
                        f2.dismiss();
                    }
                    break;
                }
                break;
            default:
                androidx.appcompat.widget.A a2 = (androidx.appcompat.widget.A) e2;
                AppCompatSpinner appCompatSpinner = a2.f860F;
                a2.getClass();
                WeakHashMap weakHashMap = v.l.f2836a;
                if (appCompatSpinner.isAttachedToWindow() && appCompatSpinner.getGlobalVisibleRect(a2.f859E)) {
                    a2.g();
                    super/*androidx.appcompat.widget.Q*/.show();
                } else {
                    a2.dismiss();
                }
                break;
        }
    }
}
