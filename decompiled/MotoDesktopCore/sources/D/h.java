package d;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.q;
import androidx.appcompat.view.menu.v;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import q.InterfaceMenuItemC0157a;

/* JADX INFO: loaded from: classes.dex */
public final class h {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public CharSequence f2368A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public CharSequence f2369B;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final /* synthetic */ i f2372E;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Menu f2373a;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f2380h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2381i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int f2382j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public CharSequence f2383k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public CharSequence f2384l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f2385m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public char f2386n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f2387o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public char f2388p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f2389q;
    public int r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f2390s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public boolean f2391t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public boolean f2392u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public int f2393v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public int f2394w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public String f2395x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public String f2396y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public v.c f2397z;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public ColorStateList f2370C = null;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public PorterDuff.Mode f2371D = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2374b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2375c = 0;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2376d = 0;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2377e = 0;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f2378f = true;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f2379g = true;

    public h(i iVar, Menu menu) {
        this.f2372E = iVar;
        this.f2373a = menu;
    }

    public final Object a(String str, Class[] clsArr, Object[] objArr) {
        try {
            Constructor<?> constructor = this.f2372E.f2402c.getClassLoader().loadClass(str).getConstructor(clsArr);
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (Exception e2) {
            Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e2);
            return null;
        }
    }

    public final void b(MenuItem menuItem) {
        boolean z2 = false;
        menuItem.setChecked(this.f2390s).setVisible(this.f2391t).setEnabled(this.f2392u).setCheckable(this.r >= 1).setTitleCondensed(this.f2384l).setIcon(this.f2385m);
        int i2 = this.f2393v;
        if (i2 >= 0) {
            menuItem.setShowAsAction(i2);
        }
        String str = this.f2396y;
        i iVar = this.f2372E;
        if (str != null) {
            if (iVar.f2402c.isRestricted()) {
                throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
            }
            if (iVar.f2403d == null) {
                iVar.f2403d = i.a(iVar.f2402c);
            }
            Object obj = iVar.f2403d;
            String str2 = this.f2396y;
            g gVar = new g();
            gVar.f2366a = obj;
            Class<?> cls = obj.getClass();
            try {
                gVar.f2367b = cls.getMethod(str2, g.f2365c);
                menuItem.setOnMenuItemClickListener(gVar);
            } catch (Exception e2) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str2 + " in class " + cls.getName());
                inflateException.initCause(e2);
                throw inflateException;
            }
        }
        boolean z3 = menuItem instanceof q;
        if (z3) {
        }
        if (this.r >= 2) {
            if (z3) {
                q qVar = (q) menuItem;
                qVar.f833x = (qVar.f833x & (-5)) | 4;
            } else if (menuItem instanceof v) {
                v vVar = (v) menuItem;
                try {
                    Method method = vVar.f842d;
                    Object obj2 = vVar.f733a;
                    if (method == null) {
                        vVar.f842d = ((InterfaceMenuItemC0157a) obj2).getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                    }
                    vVar.f842d.invoke(obj2, Boolean.TRUE);
                } catch (Exception e3) {
                    Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e3);
                }
            }
        }
        String str3 = this.f2395x;
        if (str3 != null) {
            menuItem.setActionView((View) a(str3, i.f2398e, iVar.f2400a));
            z2 = true;
        }
        int i3 = this.f2394w;
        if (i3 > 0) {
            if (z2) {
                Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
            } else {
                menuItem.setActionView(i3);
            }
        }
        v.c cVar = this.f2397z;
        if (cVar != null) {
            if (menuItem instanceof InterfaceMenuItemC0157a) {
                ((InterfaceMenuItemC0157a) menuItem).a(cVar);
            } else {
                Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
            }
        }
        CharSequence charSequence = this.f2368A;
        boolean z4 = menuItem instanceof InterfaceMenuItemC0157a;
        if (z4) {
            ((InterfaceMenuItemC0157a) menuItem).setContentDescription(charSequence);
        } else {
            menuItem.setContentDescription(charSequence);
        }
        CharSequence charSequence2 = this.f2369B;
        if (z4) {
            ((InterfaceMenuItemC0157a) menuItem).setTooltipText(charSequence2);
        } else {
            menuItem.setTooltipText(charSequence2);
        }
        char c2 = this.f2386n;
        int i4 = this.f2387o;
        if (z4) {
            ((InterfaceMenuItemC0157a) menuItem).setAlphabeticShortcut(c2, i4);
        } else {
            menuItem.setAlphabeticShortcut(c2, i4);
        }
        char c3 = this.f2388p;
        int i5 = this.f2389q;
        if (z4) {
            ((InterfaceMenuItemC0157a) menuItem).setNumericShortcut(c3, i5);
        } else {
            menuItem.setNumericShortcut(c3, i5);
        }
        PorterDuff.Mode mode = this.f2371D;
        if (mode != null) {
            if (z4) {
                ((InterfaceMenuItemC0157a) menuItem).setIconTintMode(mode);
            } else {
                menuItem.setIconTintMode(mode);
            }
        }
        ColorStateList colorStateList = this.f2370C;
        if (colorStateList != null) {
            if (z4) {
                ((InterfaceMenuItemC0157a) menuItem).setIconTintList(colorStateList);
            } else {
                menuItem.setIconTintList(colorStateList);
            }
        }
    }
}
