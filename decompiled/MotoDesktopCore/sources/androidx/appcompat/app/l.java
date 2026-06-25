package androidx.appcompat.app;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class l extends d.k {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ q f572b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public l(q qVar, Window.Callback callback) {
        super(callback);
        this.f572b = qVar;
    }

    @Override // d.k, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.f572b.g(keyEvent) || this.f2410a.dispatchKeyEvent(keyEvent);
    }

    @Override // d.k, android.view.Window.Callback
    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (this.f2410a.dispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        q qVar = this.f572b;
        qVar.l();
        Y.r rVar = qVar.f617f;
        if (rVar != null && rVar.Y(keyCode, keyEvent)) {
            return true;
        }
        p pVar = qVar.f599D;
        if (pVar != null && qVar.r(pVar, keyEvent.getKeyCode(), keyEvent)) {
            p pVar2 = qVar.f599D;
            if (pVar2 == null) {
                return true;
            }
            pVar2.f590l = true;
            return true;
        }
        if (qVar.f599D == null) {
            p pVarK = qVar.k(0);
            qVar.s(pVarK, keyEvent);
            boolean zR = qVar.r(pVarK, keyEvent.getKeyCode(), keyEvent);
            pVarK.f589k = false;
            if (zR) {
                return true;
            }
        }
        return false;
    }

    @Override // d.k, android.view.Window.Callback
    public final void onContentChanged() {
    }

    @Override // d.k, android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i2, Menu menu) {
        if (i2 != 0 || (menu instanceof androidx.appcompat.view.menu.o)) {
            return this.f2410a.onCreatePanelMenu(i2, menu);
        }
        return false;
    }

    @Override // d.k, android.view.Window.Callback
    public final boolean onMenuOpened(int i2, Menu menu) {
        super.onMenuOpened(i2, menu);
        q qVar = this.f572b;
        if (i2 == 108) {
            qVar.l();
            Y.r rVar = qVar.f617f;
            if (rVar != null) {
                rVar.q(true);
            }
        } else {
            qVar.getClass();
        }
        return true;
    }

    @Override // d.k, android.view.Window.Callback
    public final void onPanelClosed(int i2, Menu menu) {
        super.onPanelClosed(i2, menu);
        q qVar = this.f572b;
        if (i2 == 108) {
            qVar.l();
            Y.r rVar = qVar.f617f;
            if (rVar != null) {
                rVar.q(false);
                return;
            }
            return;
        }
        if (i2 != 0) {
            qVar.getClass();
            return;
        }
        p pVarK = qVar.k(i2);
        if (pVarK.f591m) {
            qVar.e(pVarK, false);
        }
    }

    @Override // android.view.Window.Callback
    public final boolean onPreparePanel(int i2, View view, Menu menu) {
        androidx.appcompat.view.menu.o oVar = menu instanceof androidx.appcompat.view.menu.o ? (androidx.appcompat.view.menu.o) menu : null;
        if (i2 == 0 && oVar == null) {
            return false;
        }
        if (oVar != null) {
            oVar.f804y = true;
        }
        boolean zOnPreparePanel = this.f2410a.onPreparePanel(i2, view, menu);
        if (oVar != null) {
            oVar.f804y = false;
        }
        return zOnPreparePanel;
    }

    @Override // d.k, android.view.Window.Callback
    public final void onProvideKeyboardShortcuts(List list, Menu menu, int i2) {
        androidx.appcompat.view.menu.o oVar = this.f572b.k(0).f586h;
        if (oVar != null) {
            super.onProvideKeyboardShortcuts(list, oVar, i2);
        } else {
            super.onProvideKeyboardShortcuts(list, menu, i2);
        }
    }

    @Override // d.k, android.view.Window.Callback
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01a0  */
    @Override // d.k, android.view.Window.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback r8, int r9) {
        /*
            Method dump skipped, instruction units count: 443
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.l.onWindowStartingActionMode(android.view.ActionMode$Callback, int):android.view.ActionMode");
    }
}
