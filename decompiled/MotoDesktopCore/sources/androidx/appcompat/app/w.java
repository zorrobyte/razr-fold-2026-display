package androidx.appcompat.app;

import android.view.Menu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class w extends d.k {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ x f647b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public w(x xVar, l lVar) {
        super(lVar);
        this.f647b = xVar;
    }

    @Override // d.k, android.view.Window.Callback
    public final View onCreatePanelView(int i2) {
        return i2 == 0 ? new View(this.f647b.f648h.f1320a.getContext()) : this.f2410a.onCreatePanelView(i2);
    }

    @Override // android.view.Window.Callback
    public final boolean onPreparePanel(int i2, View view, Menu menu) {
        boolean zOnPreparePanel = this.f2410a.onPreparePanel(i2, view, menu);
        if (zOnPreparePanel) {
            x xVar = this.f647b;
            if (!xVar.f649i) {
                xVar.f648h.f1332m = true;
                xVar.f649i = true;
            }
        }
        return zOnPreparePanel;
    }
}
