package androidx.appcompat.app;

import android.view.View;
import android.widget.PopupWindow;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class k extends AbstractC0054a {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ int f570d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f571e;

    public /* synthetic */ k(int i2, Object obj) {
        this.f570d = i2;
        this.f571e = obj;
    }

    @Override // androidx.appcompat.app.AbstractC0054a, v.n
    public void b() {
        Object obj = this.f571e;
        switch (this.f570d) {
            case 0:
                ((i) obj).f567b.f624m.setVisibility(0);
                break;
            case 1:
                q qVar = (q) obj;
                qVar.f624m.setVisibility(0);
                qVar.f624m.sendAccessibilityEvent(32);
                if (qVar.f624m.getParent() instanceof View) {
                    View view = (View) qVar.f624m.getParent();
                    WeakHashMap weakHashMap = v.l.f2836a;
                    view.requestApplyInsets();
                }
                break;
        }
    }

    @Override // v.n
    public final void c() {
        Object obj = this.f571e;
        switch (this.f570d) {
            case 0:
                i iVar = (i) obj;
                iVar.f567b.f624m.setAlpha(1.0f);
                q qVar = iVar.f567b;
                qVar.f627p.d(null);
                qVar.f627p = null;
                break;
            case 1:
                q qVar2 = (q) obj;
                qVar2.f624m.setAlpha(1.0f);
                qVar2.f627p.d(null);
                qVar2.f627p = null;
                break;
            default:
                F.f fVar = (F.f) obj;
                ((q) fVar.f124b).f624m.setVisibility(8);
                q qVar3 = (q) fVar.f124b;
                PopupWindow popupWindow = qVar3.f625n;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                } else if (qVar3.f624m.getParent() instanceof View) {
                    View view = (View) qVar3.f624m.getParent();
                    WeakHashMap weakHashMap = v.l.f2836a;
                    view.requestApplyInsets();
                }
                qVar3.f624m.removeAllViews();
                qVar3.f627p.d(null);
                qVar3.f627p = null;
                break;
        }
    }
}
