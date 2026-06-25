package androidx.appcompat.view.menu;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public final class p implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public o f805a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public androidx.appcompat.app.g f806b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public l f807c;

    @Override // androidx.appcompat.view.menu.z
    public final void a(o oVar, boolean z2) {
        androidx.appcompat.app.g gVar;
        if ((z2 || oVar == this.f805a) && (gVar = this.f806b) != null) {
            gVar.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.z
    public final boolean e(o oVar) {
        return false;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        l lVar = this.f807c;
        if (lVar.f779g == null) {
            lVar.f779g = new k(lVar);
        }
        this.f805a.q(lVar.f779g.getItem(i2), null, 0);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.f807c.a(this.f805a, true);
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        o oVar = this.f805a;
        if (i2 == 82 || i2 == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.f806b.getWindow();
                if (window2 != null && (decorView2 = window2.getDecorView()) != null && (keyDispatcherState2 = decorView2.getKeyDispatcherState()) != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.f806b.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                oVar.c(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return oVar.performShortcut(i2, keyEvent, 0);
    }
}
