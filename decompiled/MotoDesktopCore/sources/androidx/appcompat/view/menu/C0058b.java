package androidx.appcompat.view.menu;

import android.view.View;
import androidx.appcompat.widget.C0069f;
import androidx.appcompat.widget.C0070g;
import androidx.appcompat.widget.C0072i;
import androidx.appcompat.widget.C0074k;
import androidx.appcompat.widget.L;

/* JADX INFO: renamed from: androidx.appcompat.view.menu.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0058b extends L {

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final /* synthetic */ int f729j = 0;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final /* synthetic */ View f730k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0058b(ActionMenuItemView actionMenuItemView) {
        super(actionMenuItemView);
        this.f730k = actionMenuItemView;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0058b(C0072i c0072i, View view) {
        super(view);
        this.f730k = c0072i;
    }

    @Override // androidx.appcompat.widget.L
    public final E b() {
        C0069f c0069f;
        switch (this.f729j) {
            case 0:
                AbstractC0059c abstractC0059c = ((ActionMenuItemView) this.f730k).f666i;
                if (abstractC0059c == null || (c0069f = ((C0070g) abstractC0059c).f1209a.f1244u) == null) {
                    return null;
                }
                return c0069f.a();
            default:
                C0069f c0069f2 = ((C0072i) this.f730k).f1213c.f1243t;
                if (c0069f2 == null) {
                    return null;
                }
                return c0069f2.a();
        }
    }

    @Override // androidx.appcompat.widget.L
    public final boolean c() {
        E eB;
        switch (this.f729j) {
            case 0:
                ActionMenuItemView actionMenuItemView = (ActionMenuItemView) this.f730k;
                n nVar = actionMenuItemView.f664g;
                return nVar != null && nVar.a(actionMenuItemView.f661d) && (eB = b()) != null && eB.isShowing();
            default:
                ((C0072i) this.f730k).f1213c.l();
                return true;
        }
    }

    @Override // androidx.appcompat.widget.L
    public boolean d() {
        switch (this.f729j) {
            case 1:
                C0074k c0074k = ((C0072i) this.f730k).f1213c;
                if (c0074k.f1245v != null) {
                    return false;
                }
                c0074k.j();
                return true;
            default:
                return super.d();
        }
    }
}
