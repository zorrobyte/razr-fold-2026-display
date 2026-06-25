package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import h.C0135b;
import q.InterfaceMenuItemC0157a;

/* JADX INFO: renamed from: androidx.appcompat.view.menu.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0060d extends AbstractC0061e {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Context f731b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public C0135b f732c;

    public AbstractC0060d(Context context, Object obj) {
        super(obj);
        this.f731b = context;
    }

    public final MenuItem c(MenuItem menuItem) {
        if (!(menuItem instanceof InterfaceMenuItemC0157a)) {
            return menuItem;
        }
        InterfaceMenuItemC0157a interfaceMenuItemC0157a = (InterfaceMenuItemC0157a) menuItem;
        if (this.f732c == null) {
            this.f732c = new C0135b();
        }
        MenuItem menuItem2 = (MenuItem) this.f732c.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        v vVar = new v(this.f731b, interfaceMenuItemC0157a);
        this.f732c.put(interfaceMenuItemC0157a, vVar);
        return vVar;
    }
}
