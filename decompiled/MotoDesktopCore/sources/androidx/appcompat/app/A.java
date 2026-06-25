package androidx.appcompat.app;

import android.view.View;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import d.InterfaceC0124a;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class A extends AbstractC0054a {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ int f466d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ C f467e;

    public /* synthetic */ A(C c2, int i2) {
        this.f466d = i2;
        this.f467e = c2;
    }

    @Override // v.n
    public final void c() {
        View view;
        C c2 = this.f467e;
        switch (this.f466d) {
            case 0:
                if (c2.f505v && (view = c2.f498n) != null) {
                    view.setTranslationY(0.0f);
                    c2.f495k.setTranslationY(0.0f);
                }
                c2.f495k.setVisibility(8);
                c2.f495k.setTransitioning(false);
                c2.f509z = null;
                InterfaceC0124a interfaceC0124a = c2.r;
                if (interfaceC0124a != null) {
                    interfaceC0124a.a(c2.f501q);
                    c2.f501q = null;
                    c2.r = null;
                }
                ActionBarOverlayLayout actionBarOverlayLayout = c2.f494j;
                if (actionBarOverlayLayout != null) {
                    WeakHashMap weakHashMap = v.l.f2836a;
                    actionBarOverlayLayout.requestApplyInsets();
                }
                break;
            default:
                c2.f509z = null;
                c2.f495k.requestLayout();
                break;
        }
    }
}
