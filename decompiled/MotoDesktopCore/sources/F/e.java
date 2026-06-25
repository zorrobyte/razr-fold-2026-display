package F;

import X.C0013e;
import X.C0045y;
import X.InterfaceC0046z;
import X.v0;
import X.w0;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioSystem;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.w;
import androidx.appcompat.app.x;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.C0084v;
import androidx.appcompat.widget.I;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.C0110c;
import androidx.recyclerview.widget.C0112e;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.t;
import androidx.viewpager.widget.ViewPager;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import com.motorola.mobiledesktop.core.uinput.EventType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;
import x.C0165b;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f120a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f121b;

    public /* synthetic */ e(int i2, Object obj) {
        this.f120a = i2;
        this.f121b = obj;
    }

    public e(C0084v c0084v, int i2) {
        this.f120a = 12;
        this.f121b = c0084v;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2;
        switch (this.f120a) {
            case 0:
                ViewPager viewPager = (ViewPager) this.f121b;
                viewPager.setScrollState(0);
                viewPager.q();
                return;
            case 1:
                C0013e.a((C0013e) this.f121b);
                return;
            case 2:
                ArrayList arrayList = new ArrayList();
                synchronized (((MotoDesktopService) this.f121b).f2257O) {
                    arrayList.addAll(((MotoDesktopService) this.f121b).f2257O);
                    break;
                }
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    try {
                        InterfaceC0046z interfaceC0046z = (InterfaceC0046z) arrayList.get(i3);
                        MotoDesktopService motoDesktopService = (MotoDesktopService) this.f121b;
                        ((C0045y) interfaceC0046z).onImeFocusStateChanged(motoDesktopService.f2259Q, motoDesktopService.f2258P);
                    } catch (DeadObjectException unused) {
                        synchronized (((MotoDesktopService) this.f121b).f2257O) {
                            ((MotoDesktopService) this.f121b).f2257O.remove(arrayList.get(i3));
                        }
                    } catch (RemoteException unused2) {
                    }
                }
                return;
            case 3:
                int forceUse = AudioSystem.getForceUse(0);
                if (forceUse == 0) {
                    AudioSystem.setForceUse(0, 1);
                } else {
                    AudioSystem.setForceUse(0, 0);
                }
                AudioSystem.setForceUse(0, forceUse);
                ((Y.i) this.f121b).f388a.c();
                return;
            case EventType.EVENT_MSC /* 4 */:
                x xVar = (x) this.f121b;
                Menu menuS0 = xVar.s0();
                o oVar = menuS0 instanceof o ? (o) menuS0 : null;
                if (oVar != null) {
                    oVar.w();
                }
                try {
                    menuS0.clear();
                    w wVar = xVar.f650j;
                    if (!wVar.f2410a.onCreatePanelMenu(0, menuS0) || !wVar.onPreparePanel(0, null, menuS0)) {
                        menuS0.clear();
                    }
                    if (oVar != null) {
                        return;
                    } else {
                        return;
                    }
                } finally {
                    if (oVar != null) {
                        oVar.v();
                    }
                }
                break;
            case EventType.EVENT_SW /* 5 */:
                I i4 = (I) this.f121b;
                i4.f1007m = null;
                i4.drawableStateChanged();
                return;
            case 6:
                ((Toolbar) this.f121b).t();
                return;
            case 7:
                androidx.core.widget.b bVar = (androidx.core.widget.b) this.f121b;
                if (bVar.f1458o) {
                    boolean z2 = bVar.f1456m;
                    androidx.core.widget.a aVar = bVar.f1444a;
                    if (z2) {
                        bVar.f1456m = false;
                        aVar.getClass();
                        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                        aVar.f1439e = jCurrentAnimationTimeMillis;
                        aVar.f1441g = -1L;
                        aVar.f1440f = jCurrentAnimationTimeMillis;
                        aVar.f1442h = 0.5f;
                    }
                    if ((aVar.f1441g > 0 && AnimationUtils.currentAnimationTimeMillis() > aVar.f1441g + ((long) aVar.f1443i)) || !bVar.e()) {
                        bVar.f1458o = false;
                        return;
                    }
                    boolean z3 = bVar.f1457n;
                    View view = bVar.f1446c;
                    if (z3) {
                        bVar.f1457n = false;
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                        view.onTouchEvent(motionEventObtain);
                        motionEventObtain.recycle();
                    }
                    if (aVar.f1440f == 0) {
                        throw new RuntimeException("Cannot compute scroll delta before calling start()");
                    }
                    long jCurrentAnimationTimeMillis2 = AnimationUtils.currentAnimationTimeMillis();
                    float fA = aVar.a(jCurrentAnimationTimeMillis2);
                    long j2 = jCurrentAnimationTimeMillis2 - aVar.f1440f;
                    aVar.f1440f = jCurrentAnimationTimeMillis2;
                    bVar.f1460q.scrollListBy((int) (j2 * ((fA * 4.0f) + ((-4.0f) * fA * fA)) * aVar.f1438d));
                    WeakHashMap weakHashMap = v.l.f2836a;
                    view.postOnAnimation(this);
                    return;
                }
                return;
            case 8:
                C0112e c0112e = (C0112e) this.f121b;
                int i5 = c0112e.f1881v;
                ValueAnimator valueAnimator = c0112e.f1880u;
                if (i5 == 1) {
                    valueAnimator.cancel();
                } else if (i5 != 2) {
                    return;
                }
                c0112e.f1881v = 3;
                valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
                valueAnimator.setDuration(500);
                valueAnimator.start();
                return;
            case 9:
                t tVar = ((RecyclerView) this.f121b).f1769E;
                if (tVar != null) {
                    C0110c c0110c = (C0110c) tVar;
                    ArrayList arrayList2 = c0110c.f1848e;
                    boolean z4 = !arrayList2.isEmpty();
                    ArrayList arrayList3 = c0110c.f1850g;
                    boolean z5 = !arrayList3.isEmpty();
                    ArrayList arrayList4 = c0110c.f1851h;
                    boolean z6 = !arrayList4.isEmpty();
                    ArrayList arrayList5 = c0110c.f1849f;
                    boolean zIsEmpty = true ^ arrayList5.isEmpty();
                    if (z4 || z5 || zIsEmpty || z6) {
                        Iterator it = arrayList2.iterator();
                        if (it.hasNext()) {
                            w0.c(it.next());
                            throw null;
                        }
                        arrayList2.clear();
                        if (z5) {
                            ArrayList arrayList6 = new ArrayList();
                            arrayList6.addAll(arrayList3);
                            ArrayList arrayList7 = c0110c.f1853j;
                            arrayList7.add(arrayList6);
                            arrayList3.clear();
                            if (z4) {
                                w0.c(arrayList6.get(0));
                                throw null;
                            }
                            Iterator it2 = arrayList6.iterator();
                            if (it2.hasNext()) {
                                w0.c(it2.next());
                                throw null;
                            }
                            arrayList6.clear();
                            arrayList7.remove(arrayList6);
                        }
                        if (z6) {
                            ArrayList arrayList8 = new ArrayList();
                            arrayList8.addAll(arrayList4);
                            ArrayList arrayList9 = c0110c.f1854k;
                            arrayList9.add(arrayList8);
                            arrayList4.clear();
                            if (z4) {
                                w0.c(arrayList8.get(0));
                                throw null;
                            }
                            Iterator it3 = arrayList8.iterator();
                            if (it3.hasNext()) {
                                w0.c(it3.next());
                                throw null;
                            }
                            arrayList8.clear();
                            arrayList9.remove(arrayList8);
                        }
                        if (zIsEmpty) {
                            ArrayList arrayList10 = new ArrayList();
                            arrayList10.addAll(arrayList5);
                            ArrayList arrayList11 = c0110c.f1852i;
                            arrayList11.add(arrayList10);
                            arrayList5.clear();
                            if (z4 || z5 || z6) {
                                Math.max(z5 ? c0110c.f1908c : 0L, z6 ? c0110c.f1909d : 0L);
                                w0.c(arrayList10.get(0));
                                throw null;
                            }
                            Iterator it4 = arrayList10.iterator();
                            if (it4.hasNext()) {
                                w0.c(it4.next());
                                throw null;
                            }
                            arrayList10.clear();
                            arrayList11.remove(arrayList10);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            case 10:
                ((StaggeredGridLayoutManager) this.f121b).K();
                return;
            case 11:
                StringBuilder sb = new StringBuilder("bSystemUiConnected:");
                e0.g gVar = (e0.g) this.f121b;
                sb.append(gVar.f2476d);
                sb.append(" mRetryCount:");
                sb.append(gVar.f2477e);
                v0.e("FwkDragDropManager", sb.toString());
                if (gVar.f2476d || (i2 = gVar.f2477e) > 5) {
                    if (gVar.f2477e > 5) {
                        v0.b("FwkDragDropManager", "failed to bind service with max retries");
                    }
                    gVar.f2478f.removeCallbacks(gVar.f2481i);
                    gVar.f2477e = 0;
                    return;
                }
                gVar.f2477e = i2 + 1;
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.dragdrop.MotoReadyForDropService"));
                    v0.a("FwkDragDropManager", "bindService:" + gVar.f2473a.bindService(intent, gVar.f2482j, 1));
                    return;
                } catch (Exception e2) {
                    v0.g("FwkDragDropManager", "bindService failed: " + e2.toString());
                    return;
                }
            case 12:
                ((C0084v) this.f121b).getClass();
                return;
            default:
                ((C0165b) this.f121b).p(0);
                return;
        }
    }
}
