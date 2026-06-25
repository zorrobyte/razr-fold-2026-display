package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import androidx.appcompat.R$layout;
import androidx.appcompat.app.C0056c;

/* JADX INFO: loaded from: classes.dex */
public final class l implements A, AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f773a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public LayoutInflater f774b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public o f775c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public ExpandedMenuView f776d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f777e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public z f778f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public k f779g;

    public l(Context context, int i2) {
        this.f777e = i2;
        this.f773a = context;
        this.f774b = LayoutInflater.from(context);
    }

    @Override // androidx.appcompat.view.menu.A
    public final void a(o oVar, boolean z2) {
        z zVar = this.f778f;
        if (zVar != null) {
            zVar.a(oVar, z2);
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean b() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void c(z zVar) {
        this.f778f = zVar;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean d(q qVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void e(Context context, o oVar) {
        if (this.f773a != null) {
            this.f773a = context;
            if (this.f774b == null) {
                this.f774b = LayoutInflater.from(context);
            }
        }
        this.f775c = oVar;
        k kVar = this.f779g;
        if (kVar != null) {
            kVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final void f() {
        k kVar = this.f779g;
        if (kVar != null) {
            kVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean g(G g2) {
        if (!g2.hasVisibleItems()) {
            return false;
        }
        p pVar = new p();
        pVar.f805a = g2;
        Context context = g2.f781a;
        androidx.appcompat.app.f fVar = new androidx.appcompat.app.f(context, androidx.appcompat.app.g.c(context, 0));
        C0056c c0056c = (C0056c) fVar.f564c;
        l lVar = new l(c0056c.f515a, R$layout.abc_list_menu_item_layout);
        pVar.f807c = lVar;
        lVar.f778f = pVar;
        g2.b(lVar, context);
        l lVar2 = pVar.f807c;
        if (lVar2.f779g == null) {
            lVar2.f779g = new k(lVar2);
        }
        c0056c.f527m = lVar2.f779g;
        c0056c.f528n = pVar;
        View view = g2.f795o;
        if (view != null) {
            c0056c.f519e = view;
        } else {
            c0056c.f517c = g2.f794n;
            c0056c.f518d = g2.f793m;
        }
        c0056c.f526l = pVar;
        androidx.appcompat.app.g gVarB = fVar.b();
        pVar.f806b = gVarB;
        gVarB.setOnDismissListener(pVar);
        WindowManager.LayoutParams attributes = pVar.f806b.getWindow().getAttributes();
        attributes.type = 1003;
        attributes.flags |= 131072;
        pVar.f806b.show();
        z zVar = this.f778f;
        if (zVar == null) {
            return true;
        }
        zVar.e(g2);
        return true;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean h(q qVar) {
        return false;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        this.f775c.q(this.f779g.getItem(i2), this, 0);
    }
}
