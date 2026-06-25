package F;

import X.Q0;
import X.v0;
import X.w0;
import android.animation.Animator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.companion.virtual.VirtualDeviceManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Animation;
import androidx.appcompat.app.q;
import androidx.appcompat.view.menu.o;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.M;
import androidx.recyclerview.widget.u;
import androidx.viewpager.widget.ViewPager;
import com.motorola.android.provider.MotorolaSettings;
import d.InterfaceC0124a;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import v.m;

/* JADX INFO: loaded from: classes.dex */
public final class f implements v.j, InterfaceC0124a {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static f f122c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Object f123a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f124b;

    public f(Animator animator) {
        this.f123a = null;
        this.f124b = animator;
    }

    public f(Context context, int i2) {
        switch (i2) {
            case 6:
                this.f124b = new ConcurrentHashMap();
                this.f123a = (VirtualDeviceManager) context.getApplicationContext().getSystemService("virtualdevice");
                break;
            default:
                this.f123a = context;
                this.f124b = (AlarmManager) context.getSystemService("alarm");
                break;
        }
    }

    public f(Animation animation) {
        this.f123a = animation;
        this.f124b = null;
    }

    public f(CardView cardView) {
        this.f124b = cardView;
    }

    public f(u uVar) {
        this.f123a = uVar;
        M m2 = new M();
        m2.f1757a = 0;
        this.f124b = m2;
    }

    public f(ViewPager viewPager) {
        this.f124b = viewPager;
        this.f123a = new Rect();
    }

    public /* synthetic */ f(Object obj, Object obj2) {
        this.f124b = obj;
        this.f123a = obj2;
    }

    @Override // d.InterfaceC0124a
    public void a(d.b bVar) {
        ((InterfaceC0124a) this.f123a).a(bVar);
        q qVar = (q) this.f124b;
        if (qVar.f625n != null) {
            qVar.f613b.getDecorView().removeCallbacks(qVar.f626o);
        }
        if (qVar.f624m != null) {
            m mVar = qVar.f627p;
            if (mVar != null) {
                mVar.b();
            }
            m mVarA = v.l.a(qVar.f624m);
            mVarA.a(0.0f);
            qVar.f627p = mVarA;
            mVarA.d(new androidx.appcompat.app.k(2, this));
        }
        qVar.f623l = null;
    }

    @Override // d.InterfaceC0124a
    public boolean b(d.b bVar, MenuItem menuItem) {
        return ((InterfaceC0124a) this.f123a).b(bVar, menuItem);
    }

    @Override // d.InterfaceC0124a
    public boolean c(d.b bVar, o oVar) {
        return ((InterfaceC0124a) this.f123a).c(bVar, oVar);
    }

    @Override // d.InterfaceC0124a
    public boolean d(d.b bVar, o oVar) {
        return ((InterfaceC0124a) this.f123a).d(bVar, oVar);
    }

    public View e(int i2, int i3, int i4, int i5) {
        u uVar = (u) this.f123a;
        int iE = uVar.e();
        int iD = uVar.d();
        int i6 = i3 > i2 ? 1 : -1;
        View view = null;
        while (i2 != i3) {
            View viewA = uVar.a(i2);
            int iC = uVar.c(viewA);
            int iB = uVar.b(viewA);
            M m2 = (M) this.f124b;
            m2.f1758b = iE;
            m2.f1759c = iD;
            m2.f1760d = iC;
            m2.f1761e = iB;
            if (i4 != 0) {
                m2.f1757a = i4;
                if (m2.a()) {
                    return viewA;
                }
            }
            if (i5 != 0) {
                m2.f1757a = i5;
                if (m2.a()) {
                    view = viewA;
                }
            }
            i2 += i6;
        }
        return view;
    }

    public g0.e f(int i2, int i3) {
        g0.f fVar = new g0.f(i2, i3);
        g0.e eVar = (g0.e) ((ConcurrentHashMap) this.f124b).get(fVar);
        if (eVar == null) {
            v0.b("VirtualDeviceManager", "failed to get virtual device for " + fVar);
        }
        return eVar;
    }

    @Override // v.j
    public v.o g(View view, v.o oVar) {
        WeakHashMap weakHashMap = v.l.f2836a;
        WindowInsets windowInsets = (WindowInsets) (oVar == null ? null : oVar.f2838a);
        WindowInsets windowInsetsOnApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        if (windowInsetsOnApplyWindowInsets != windowInsets) {
            windowInsets = new WindowInsets(windowInsetsOnApplyWindowInsets);
        }
        v.o oVar2 = windowInsets == null ? null : new v.o(windowInsets);
        if (((WindowInsets) oVar2.f2838a).isConsumed()) {
            return oVar2;
        }
        int iA = oVar2.a();
        Rect rect = (Rect) this.f123a;
        rect.left = iA;
        rect.top = oVar2.c();
        rect.right = oVar2.b();
        Object obj = oVar2.f2838a;
        rect.bottom = ((WindowInsets) obj).getSystemWindowInsetBottom();
        ViewPager viewPager = (ViewPager) this.f124b;
        int childCount = viewPager.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            WindowInsets windowInsets2 = (WindowInsets) obj;
            WindowInsets windowInsetsDispatchApplyWindowInsets = viewPager.getChildAt(i2).dispatchApplyWindowInsets(windowInsets2);
            if (windowInsetsDispatchApplyWindowInsets != windowInsets2) {
                windowInsets2 = new WindowInsets(windowInsetsDispatchApplyWindowInsets);
            }
            v.o oVar3 = windowInsets2 == null ? null : new v.o(windowInsets2);
            rect.left = Math.min(oVar3.a(), rect.left);
            rect.top = Math.min(oVar3.c(), rect.top);
            rect.right = Math.min(oVar3.b(), rect.right);
            rect.bottom = Math.min(((WindowInsets) oVar3.f2838a).getSystemWindowInsetBottom(), rect.bottom);
        }
        return new v.o(((WindowInsets) obj).replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom));
    }

    public g0.e h(int i2) {
        for (g0.e eVar : ((ConcurrentHashMap) this.f124b).values()) {
            if (eVar.f2558b.containsKey(Integer.valueOf(i2))) {
                return eVar;
            }
        }
        return null;
    }

    public boolean i(int i2) {
        Iterator it = ((ConcurrentHashMap) this.f124b).values().iterator();
        while (it.hasNext()) {
            if (((g0.e) it.next()).f2558b.containsKey(Integer.valueOf(i2))) {
                return true;
            }
        }
        return false;
    }

    public void j(int i2, int i3, int i4, int i5) {
        CardView cardView = (CardView) this.f124b;
        cardView.f1355d.set(i2, i3, i4, i5);
        Rect rect = cardView.f1354c;
        super/*android.view.View*/.setPadding(i2 + rect.left, i3 + rect.top, i4 + rect.right, i5 + rect.bottom);
    }

    public void k() {
        int i2;
        boolean z2 = Q0.f288a;
        Context context = (Context) this.f123a;
        int i3 = MotorolaSettings.Global.getInt(context.getContentResolver(), "ready_for_power_save_sec_max", 0) * 1000;
        w0.b(i3, "startMiracastDisconnectAlarm - maxPowerSaveTime:", "DisconnectAlarmManager");
        if (i3 == 0) {
            i3 = MotorolaSettings.System.getInt(context.getContentResolver(), "power_save_sec_miracast", 900) * 1000;
        } else if (i3 > 0 && i3 > (i2 = MotorolaSettings.System.getInt(context.getContentResolver(), "power_save_sec_miracast", 900) * 1000)) {
            i3 = i2;
        }
        w0.b(i3, "startMiracastDisconnectAlarm - final timeoutMillis:", "DisconnectAlarmManager");
        if (i3 > 0) {
            long jCurrentTimeMillis = System.currentTimeMillis() + ((long) i3);
            Intent intent = new Intent("com.motorola.mobiledesktop.core.action_disconnect");
            Context context2 = (Context) this.f123a;
            ((AlarmManager) this.f124b).setExactAndAllowWhileIdle(0, jCurrentTimeMillis, PendingIntent.getBroadcast(context2, 0, intent.setPackage(context2.getPackageName()), 201326592));
        }
    }
}
