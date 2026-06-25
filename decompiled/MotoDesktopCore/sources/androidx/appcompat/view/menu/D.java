package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import h.C0135b;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class D extends AbstractC0060d implements Menu {
    @Override // android.view.Menu
    public final MenuItem add(int i2) {
        return c(((o) this.f733a).add(i2));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i2, int i3, int i4, int i5) {
        return c(((o) this.f733a).add(i2, i3, i4, i5));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i2, int i3, int i4, CharSequence charSequence) {
        return c(((o) this.f733a).a(i2, i3, i4, charSequence));
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return c(((o) this.f733a).a(0, 0, 0, charSequence));
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i2, int i3, int i4, ComponentName componentName, Intent[] intentArr, Intent intent, int i5, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int iAddIntentOptions = ((o) this.f733a).addIntentOptions(i2, i3, i4, componentName, intentArr, intent, i5, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i6 = 0; i6 < length; i6++) {
                menuItemArr[i6] = c(menuItemArr2[i6]);
            }
        }
        return iAddIntentOptions;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i2) {
        return ((o) this.f733a).addSubMenu(i2);
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i2, int i3, int i4, int i5) {
        return ((o) this.f733a).addSubMenu(i2, i3, i4, i5);
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i2, int i3, int i4, CharSequence charSequence) {
        return ((o) this.f733a).addSubMenu(i2, i3, i4, charSequence);
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return ((o) this.f733a).addSubMenu(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public final void clear() {
        C0135b c0135b = this.f732c;
        if (c0135b != null) {
            c0135b.clear();
        }
        ((o) this.f733a).clear();
    }

    @Override // android.view.Menu
    public final void close() {
        ((o) this.f733a).c(true);
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i2) {
        return c(((o) this.f733a).findItem(i2));
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i2) {
        return c(((o) this.f733a).getItem(i2));
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        return ((o) this.f733a).hasVisibleItems();
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i2, KeyEvent keyEvent) {
        return ((o) this.f733a).isShortcutKey(i2, keyEvent);
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i2, int i3) {
        return ((o) this.f733a).performIdentifierAction(i2, i3);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i2, KeyEvent keyEvent, int i3) {
        return ((o) this.f733a).performShortcut(i2, keyEvent, i3);
    }

    @Override // android.view.Menu
    public final void removeGroup(int i2) {
        C0135b c0135b = this.f732c;
        if (c0135b != null) {
            Iterator it = ((h.h) c0135b.keySet()).iterator();
            while (true) {
                h.g gVar = (h.g) it;
                if (!gVar.hasNext()) {
                    break;
                } else if (i2 == ((MenuItem) gVar.next()).getGroupId()) {
                    gVar.remove();
                }
            }
        }
        ((o) this.f733a).removeGroup(i2);
    }

    @Override // android.view.Menu
    public final void removeItem(int i2) {
        C0135b c0135b = this.f732c;
        if (c0135b != null) {
            Iterator it = ((h.h) c0135b.keySet()).iterator();
            while (true) {
                h.g gVar = (h.g) it;
                if (!gVar.hasNext()) {
                    break;
                } else if (i2 == ((MenuItem) gVar.next()).getItemId()) {
                    gVar.remove();
                    break;
                }
            }
        }
        ((o) this.f733a).removeItem(i2);
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i2, boolean z2, boolean z3) {
        ((o) this.f733a).setGroupCheckable(i2, z2, z3);
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i2, boolean z2) {
        ((o) this.f733a).setGroupEnabled(i2, z2);
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i2, boolean z2) {
        ((o) this.f733a).setGroupVisible(i2, z2);
    }

    @Override // android.view.Menu
    public final void setQwertyMode(boolean z2) {
        ((o) this.f733a).setQwertyMode(z2);
    }

    @Override // android.view.Menu
    public final int size() {
        return ((o) this.f733a).f786f.size();
    }
}
