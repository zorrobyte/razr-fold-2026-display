package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class o implements Menu {

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public static final int[] f780z = {1, 4, 5, 3, 2, 0};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f781a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Resources f782b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f783c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final boolean f784d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public m f785e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final ArrayList f786f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final ArrayList f787g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f788h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final ArrayList f789i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final ArrayList f790j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f791k;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public CharSequence f793m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public Drawable f794n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public View f795o;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public q f802w;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f804y;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f792l = 0;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f796p = false;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f797q = false;
    public boolean r = false;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f798s = false;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public boolean f799t = false;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final ArrayList f800u = new ArrayList();

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public final CopyOnWriteArrayList f801v = new CopyOnWriteArrayList();

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f803x = false;

    public o(Context context) {
        boolean z2 = false;
        this.f781a = context;
        Resources resources = context.getResources();
        this.f782b = resources;
        this.f786f = new ArrayList();
        this.f787g = new ArrayList();
        this.f788h = true;
        this.f789i = new ArrayList();
        this.f790j = new ArrayList();
        this.f791k = true;
        if (resources.getConfiguration().keyboard != 1 && ViewConfiguration.get(context).shouldShowMenuShortcutsWhenKeyboardPresent()) {
            z2 = true;
        }
        this.f784d = z2;
    }

    public final q a(int i2, int i3, int i4, CharSequence charSequence) {
        int i5;
        int i6 = ((-65536) & i4) >> 16;
        if (i6 < 0 || i6 >= 6) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }
        int i7 = (f780z[i6] << 16) | (65535 & i4);
        q qVar = new q(this, i2, i3, i4, i7, charSequence, this.f792l);
        ArrayList arrayList = this.f786f;
        int size = arrayList.size() - 1;
        while (true) {
            if (size < 0) {
                i5 = 0;
                break;
            }
            if (((q) arrayList.get(size)).f814d <= i7) {
                i5 = size + 1;
                break;
            }
            size--;
        }
        arrayList.add(i5, qVar);
        p(true);
        return qVar;
    }

    @Override // android.view.Menu
    public final MenuItem add(int i2) {
        return a(0, 0, 0, this.f782b.getString(i2));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i2, int i3, int i4, int i5) {
        return a(i2, i3, i4, this.f782b.getString(i5));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i2, int i3, int i4, CharSequence charSequence) {
        return a(i2, i3, i4, charSequence);
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i2, int i3, int i4, ComponentName componentName, Intent[] intentArr, Intent intent, int i5, MenuItem[] menuItemArr) {
        int i6;
        PackageManager packageManager = this.f781a.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i5 & 1) == 0) {
            removeGroup(i2);
        }
        for (int i7 = 0; i7 < size; i7++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i7);
            int i8 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i8 < 0 ? intent : intentArr[i8]);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            q qVarA = a(i2, i3, i4, resolveInfo.loadLabel(packageManager));
            qVarA.setIcon(resolveInfo.loadIcon(packageManager));
            qVarA.f817g = intent2;
            if (menuItemArr != null && (i6 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i6] = qVarA;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i2) {
        return addSubMenu(0, 0, 0, this.f782b.getString(i2));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i2, int i3, int i4, int i5) {
        return addSubMenu(i2, i3, i4, this.f782b.getString(i5));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i2, int i3, int i4, CharSequence charSequence) {
        q qVarA = a(i2, i3, i4, charSequence);
        G g2 = new G(this.f781a, this, qVarA);
        qVarA.f825o = g2;
        g2.setHeaderTitle(qVarA.f815e);
        return g2;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public final void b(A a2, Context context) {
        this.f801v.add(new WeakReference(a2));
        a2.e(context, this);
        this.f791k = true;
    }

    public final void c(boolean z2) {
        if (this.f799t) {
            return;
        }
        this.f799t = true;
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.f801v;
        for (WeakReference weakReference : copyOnWriteArrayList) {
            A a2 = (A) weakReference.get();
            if (a2 == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                a2.a(this, z2);
            }
        }
        this.f799t = false;
    }

    @Override // android.view.Menu
    public final void clear() {
        q qVar = this.f802w;
        if (qVar != null) {
            d(qVar);
        }
        this.f786f.clear();
        p(true);
    }

    public final void clearHeader() {
        this.f794n = null;
        this.f793m = null;
        this.f795o = null;
        p(false);
    }

    @Override // android.view.Menu
    public final void close() {
        c(true);
    }

    public boolean d(q qVar) {
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.f801v;
        boolean zD = false;
        if (!copyOnWriteArrayList.isEmpty() && this.f802w == qVar) {
            w();
            for (WeakReference weakReference : copyOnWriteArrayList) {
                A a2 = (A) weakReference.get();
                if (a2 != null) {
                    zD = a2.d(qVar);
                    if (zD) {
                        break;
                    }
                } else {
                    copyOnWriteArrayList.remove(weakReference);
                }
            }
            v();
            if (zD) {
                this.f802w = null;
            }
        }
        return zD;
    }

    public boolean e(o oVar, MenuItem menuItem) {
        m mVar = this.f785e;
        return mVar != null && mVar.c(oVar, menuItem);
    }

    public boolean f(q qVar) {
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.f801v;
        boolean zH = false;
        if (copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        w();
        for (WeakReference weakReference : copyOnWriteArrayList) {
            A a2 = (A) weakReference.get();
            if (a2 != null) {
                zH = a2.h(qVar);
                if (zH) {
                    break;
                }
            } else {
                copyOnWriteArrayList.remove(weakReference);
            }
        }
        v();
        if (zH) {
            this.f802w = qVar;
        }
        return zH;
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i2) {
        MenuItem menuItemFindItem;
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            q qVar = (q) arrayList.get(i3);
            if (qVar.f811a == i2) {
                return qVar;
            }
            if (qVar.hasSubMenu() && (menuItemFindItem = qVar.f825o.findItem(i2)) != null) {
                return menuItemFindItem;
            }
        }
        return null;
    }

    public final q g(int i2, KeyEvent keyEvent) {
        ArrayList arrayList = this.f800u;
        arrayList.clear();
        h(arrayList, i2, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return (q) arrayList.get(0);
        }
        boolean zN = n();
        for (int i3 = 0; i3 < size; i3++) {
            q qVar = (q) arrayList.get(i3);
            char c2 = zN ? qVar.f820j : qVar.f818h;
            char[] cArr = keyData.meta;
            if ((c2 == cArr[0] && (metaState & 2) == 0) || ((c2 == cArr[2] && (metaState & 2) != 0) || (zN && c2 == '\b' && i2 == 67))) {
                return qVar;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i2) {
        return (MenuItem) this.f786f.get(i2);
    }

    public final void h(ArrayList arrayList, int i2, KeyEvent keyEvent) {
        boolean zN = n();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i2 == 67) {
            ArrayList arrayList2 = this.f786f;
            int size = arrayList2.size();
            for (int i3 = 0; i3 < size; i3++) {
                q qVar = (q) arrayList2.get(i3);
                if (qVar.hasSubMenu()) {
                    qVar.f825o.h(arrayList, i2, keyEvent);
                }
                char c2 = zN ? qVar.f820j : qVar.f818h;
                if ((modifiers & 69647) == ((zN ? qVar.f821k : qVar.f819i) & 69647) && c2 != 0) {
                    char[] cArr = keyData.meta;
                    if ((c2 == cArr[0] || c2 == cArr[2] || (zN && c2 == '\b' && i2 == 67)) && qVar.isEnabled()) {
                        arrayList.add(qVar);
                    }
                }
            }
        }
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        if (this.f804y) {
            return true;
        }
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((q) arrayList.get(i2)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public final void i() {
        ArrayList arrayListL = l();
        if (this.f791k) {
            CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.f801v;
            boolean zB = false;
            for (WeakReference weakReference : copyOnWriteArrayList) {
                A a2 = (A) weakReference.get();
                if (a2 == null) {
                    copyOnWriteArrayList.remove(weakReference);
                } else {
                    zB |= a2.b();
                }
            }
            ArrayList arrayList = this.f789i;
            ArrayList arrayList2 = this.f790j;
            if (zB) {
                arrayList.clear();
                arrayList2.clear();
                int size = arrayListL.size();
                for (int i2 = 0; i2 < size; i2++) {
                    q qVar = (q) arrayListL.get(i2);
                    if (qVar.f()) {
                        arrayList.add(qVar);
                    } else {
                        arrayList2.add(qVar);
                    }
                }
            } else {
                arrayList.clear();
                arrayList2.clear();
                arrayList2.addAll(l());
            }
            this.f791k = false;
        }
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i2, KeyEvent keyEvent) {
        return g(i2, keyEvent) != null;
    }

    public String j() {
        return "android:menu:actionviewstates";
    }

    public o k() {
        return this;
    }

    public final ArrayList l() {
        boolean z2 = this.f788h;
        ArrayList arrayList = this.f787g;
        if (!z2) {
            return arrayList;
        }
        arrayList.clear();
        ArrayList arrayList2 = this.f786f;
        int size = arrayList2.size();
        for (int i2 = 0; i2 < size; i2++) {
            q qVar = (q) arrayList2.get(i2);
            if (qVar.isVisible()) {
                arrayList.add(qVar);
            }
        }
        this.f788h = false;
        this.f791k = true;
        return arrayList;
    }

    public boolean m() {
        return this.f803x;
    }

    public boolean n() {
        return this.f783c;
    }

    public boolean o() {
        return this.f784d;
    }

    public final void p(boolean z2) {
        if (this.f796p) {
            this.f797q = true;
            if (z2) {
                this.r = true;
                return;
            }
            return;
        }
        if (z2) {
            this.f788h = true;
            this.f791k = true;
        }
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.f801v;
        if (copyOnWriteArrayList.isEmpty()) {
            return;
        }
        w();
        for (WeakReference weakReference : copyOnWriteArrayList) {
            A a2 = (A) weakReference.get();
            if (a2 == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                a2.f();
            }
        }
        v();
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i2, int i3) {
        return q(findItem(i2), null, i3);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i2, KeyEvent keyEvent, int i3) {
        q qVarG = g(i2, keyEvent);
        boolean zQ = qVarG != null ? q(qVarG, null, i3) : false;
        if ((i3 & 2) != 0) {
            c(true);
        }
        return zQ;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean q(android.view.MenuItem r7, androidx.appcompat.view.menu.A r8, int r9) {
        /*
            Method dump skipped, instruction units count: 223
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.o.q(android.view.MenuItem, androidx.appcompat.view.menu.A, int):boolean");
    }

    public final void r(A a2) {
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.f801v;
        for (WeakReference weakReference : copyOnWriteArrayList) {
            A a3 = (A) weakReference.get();
            if (a3 == null || a3 == a2) {
                copyOnWriteArrayList.remove(weakReference);
            }
        }
    }

    @Override // android.view.Menu
    public final void removeGroup(int i2) {
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= size) {
                i4 = -1;
                break;
            } else if (((q) arrayList.get(i4)).f812b == i2) {
                break;
            } else {
                i4++;
            }
        }
        if (i4 >= 0) {
            int size2 = arrayList.size() - i4;
            while (true) {
                int i5 = i3 + 1;
                if (i3 >= size2 || ((q) arrayList.get(i4)).f812b != i2) {
                    break;
                }
                if (i4 >= 0) {
                    ArrayList arrayList2 = this.f786f;
                    if (i4 < arrayList2.size()) {
                        arrayList2.remove(i4);
                    }
                }
                i3 = i5;
            }
            p(true);
        }
    }

    @Override // android.view.Menu
    public final void removeItem(int i2) {
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                i3 = -1;
                break;
            } else if (((q) arrayList.get(i3)).f811a == i2) {
                break;
            } else {
                i3++;
            }
        }
        if (i3 >= 0) {
            ArrayList arrayList2 = this.f786f;
            if (i3 >= arrayList2.size()) {
                return;
            }
            arrayList2.remove(i3);
            p(true);
        }
    }

    public final void s(Bundle bundle) {
        MenuItem menuItemFindItem;
        if (bundle == null) {
            return;
        }
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(j());
        int size = this.f786f.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = getItem(i2);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                actionView.restoreHierarchyState(sparseParcelableArray);
            }
            if (item.hasSubMenu()) {
                ((G) item.getSubMenu()).s(bundle);
            }
        }
        int i3 = bundle.getInt("android:menu:expandedactionview");
        if (i3 <= 0 || (menuItemFindItem = findItem(i3)) == null) {
            return;
        }
        menuItemFindItem.expandActionView();
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i2, boolean z2, boolean z3) {
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            q qVar = (q) arrayList.get(i3);
            if (qVar.f812b == i2) {
                qVar.f833x = (qVar.f833x & (-5)) | (z3 ? 4 : 0);
                qVar.setCheckable(z2);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupDividerEnabled(boolean z2) {
        this.f803x = z2;
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i2, boolean z2) {
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            q qVar = (q) arrayList.get(i3);
            if (qVar.f812b == i2) {
                qVar.setEnabled(z2);
            }
        }
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i2, boolean z2) {
        ArrayList arrayList = this.f786f;
        int size = arrayList.size();
        boolean z3 = false;
        for (int i3 = 0; i3 < size; i3++) {
            q qVar = (q) arrayList.get(i3);
            if (qVar.f812b == i2) {
                int i4 = qVar.f833x;
                int i5 = (i4 & (-9)) | (z2 ? 0 : 8);
                qVar.f833x = i5;
                if (i4 != i5) {
                    z3 = true;
                }
            }
        }
        if (z3) {
            p(true);
        }
    }

    public final void setOptionalIconsVisible(boolean z2) {
        this.f798s = z2;
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z2) {
        this.f783c = z2;
        p(false);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.f786f.size();
    }

    public final void t(Bundle bundle) {
        int size = this.f786f.size();
        SparseArray<? extends Parcelable> sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = getItem(i2);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((G) item.getSubMenu()).t(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(j(), sparseArray);
        }
    }

    public final void u(int i2, CharSequence charSequence, int i3, Drawable drawable, View view) {
        if (view != null) {
            this.f795o = view;
            this.f793m = null;
            this.f794n = null;
        } else {
            if (i2 > 0) {
                this.f793m = this.f782b.getText(i2);
            } else if (charSequence != null) {
                this.f793m = charSequence;
            }
            if (i3 > 0) {
                this.f794n = this.f781a.getDrawable(i3);
            } else if (drawable != null) {
                this.f794n = drawable;
            }
            this.f795o = null;
        }
        p(false);
    }

    public final void v() {
        this.f796p = false;
        if (this.f797q) {
            this.f797q = false;
            p(this.r);
        }
    }

    public final void w() {
        if (this.f796p) {
            return;
        }
        this.f796p = true;
        this.f797q = false;
        this.r = false;
    }
}
