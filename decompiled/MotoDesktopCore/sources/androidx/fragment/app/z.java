package androidx.fragment.app;

import X.w0;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import h.C0136c;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import u.C0160a;

/* JADX INFO: loaded from: classes.dex */
public final class z extends AbstractC0103o implements LayoutInflater.Factory2 {

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public static Field f1666C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public static final DecelerateInterpolator f1667D = new DecelerateInterpolator(2.5f);

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public static final DecelerateInterpolator f1668E = new DecelerateInterpolator(1.5f);

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public A f1669A;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ArrayList f1671a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f1672b;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public SparseArray f1675e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public ArrayList f1676f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ArrayList f1677g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public ArrayList f1678h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public ArrayList f1679i;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public AbstractC0102n f1682l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public v f1683m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public AbstractComponentCallbacksC0098j f1684n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public AbstractComponentCallbacksC0098j f1685o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f1686p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f1687q;
    public boolean r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f1688s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public boolean f1689t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public ArrayList f1690u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public ArrayList f1691v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public ArrayList f1692w;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public ArrayList f1695z;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1673c = 0;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final ArrayList f1674d = new ArrayList();

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final CopyOnWriteArrayList f1680j = new CopyOnWriteArrayList();

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f1681k = 0;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public Bundle f1693x = null;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public SparseArray f1694y = null;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public final P f1670B = new P(2, this);

    static {
        new AccelerateInterpolator(2.5f);
        new AccelerateInterpolator(1.5f);
    }

    public static Animation.AnimationListener L(Animation animation) {
        try {
            if (f1666C == null) {
                Field declaredField = Animation.class.getDeclaredField("mListener");
                f1666C = declaredField;
                declaredField.setAccessible(true);
            }
            return (Animation.AnimationListener) f1666C.get(animation);
        } catch (IllegalAccessException e2) {
            Log.e("FragmentManager", "Cannot access Animation's mListener field", e2);
            return null;
        } catch (NoSuchFieldException e3) {
            Log.e("FragmentManager", "No field with the name mListener is found in Animation class", e3);
            return null;
        }
    }

    public static F.f O(float f2, float f3, float f4, float f5) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(f1667D);
        scaleAnimation.setDuration(220L);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f4, f5);
        alphaAnimation.setInterpolator(f1668E);
        alphaAnimation.setDuration(220L);
        animationSet.addAnimation(alphaAnimation);
        return new F.f(animationSet);
    }

    public static boolean P(Animator animator) {
        if (animator == null) {
            return false;
        }
        if (animator instanceof ValueAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
        } else if (animator instanceof AnimatorSet) {
            ArrayList<Animator> childAnimations = ((AnimatorSet) animator).getChildAnimations();
            for (int i2 = 0; i2 < childAnimations.size(); i2++) {
                if (P(childAnimations.get(i2))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void c0(android.view.View r6, F.f r7) {
        /*
            r0 = 1
            if (r6 == 0) goto L69
            int r1 = r6.getLayerType()
            java.lang.Object r2 = r7.f124b
            android.animation.Animator r2 = (android.animation.Animator) r2
            java.lang.Object r7 = r7.f123a
            android.view.animation.Animation r7 = (android.view.animation.Animation) r7
            r3 = 0
            if (r1 != 0) goto L46
            java.util.WeakHashMap r1 = v.l.f2836a
            boolean r1 = r6.hasOverlappingRendering()
            if (r1 == 0) goto L46
            boolean r1 = r7 instanceof android.view.animation.AlphaAnimation
            if (r1 == 0) goto L20
        L1e:
            r1 = r0
            goto L43
        L20:
            boolean r1 = r7 instanceof android.view.animation.AnimationSet
            if (r1 == 0) goto L3f
            r1 = r7
            android.view.animation.AnimationSet r1 = (android.view.animation.AnimationSet) r1
            java.util.List r1 = r1.getAnimations()
            r4 = r3
        L2c:
            int r5 = r1.size()
            if (r4 >= r5) goto L3d
            java.lang.Object r5 = r1.get(r4)
            boolean r5 = r5 instanceof android.view.animation.AlphaAnimation
            if (r5 == 0) goto L3b
            goto L1e
        L3b:
            int r4 = r4 + r0
            goto L2c
        L3d:
            r1 = r3
            goto L43
        L3f:
            boolean r1 = P(r2)
        L43:
            if (r1 == 0) goto L46
            goto L47
        L46:
            r0 = r3
        L47:
            if (r0 == 0) goto L69
            if (r2 == 0) goto L56
            C.p r7 = new C.p
            r7.<init>()
            r7.f63b = r6
            r2.addListener(r7)
            goto L69
        L56:
            android.view.animation.Animation$AnimationListener r0 = L(r7)
            r1 = 2
            r2 = 0
            r6.setLayerType(r1, r2)
            androidx.fragment.app.s r1 = new androidx.fragment.app.s
            r1.<init>(r0)
            r1.f1652b = r6
            r7.setAnimationListener(r1)
        L69:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.z.c0(android.view.View, F.f):void");
    }

    public static void e0(A a2) {
        if (a2 == null) {
            return;
        }
        List list = a2.f1484a;
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((AbstractComponentCallbacksC0098j) it.next()).mRetaining = true;
            }
        }
        List list2 = a2.f1485b;
        if (list2 != null) {
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                e0((A) it2.next());
            }
        }
    }

    public final void A(Menu menu) {
        if (this.f1681k < 1) {
            return;
        }
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.f1674d;
            if (i2 >= arrayList.size()) {
                return;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performOptionsMenuClosed(menu);
            }
            i2++;
        }
    }

    public final boolean B(Menu menu) {
        int i2 = 0;
        if (this.f1681k < 1) {
            return false;
        }
        boolean z2 = false;
        while (true) {
            ArrayList arrayList = this.f1674d;
            if (i2 >= arrayList.size()) {
                return z2;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.performPrepareOptionsMenu(menu)) {
                z2 = true;
            }
            i2++;
        }
    }

    public final void C(int i2) {
        try {
            this.f1672b = true;
            R(i2, false);
            this.f1672b = false;
            G();
        } catch (Throwable th) {
            this.f1672b = false;
            throw th;
        }
    }

    public final void D(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        String str2 = str + "    ";
        SparseArray sparseArray = this.f1675e;
        if (sparseArray != null && (size5 = sparseArray.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i2 = 0; i2 < size5; i2++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(abstractComponentCallbacksC0098j);
                if (abstractComponentCallbacksC0098j != null) {
                    abstractComponentCallbacksC0098j.dump(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        int size6 = this.f1674d.size();
        if (size6 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i3 = 0; i3 < size6; i3++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = (AbstractComponentCallbacksC0098j) this.f1674d.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(abstractComponentCallbacksC0098j2.toString());
            }
        }
        ArrayList arrayList = this.f1677g;
        if (arrayList != null && (size4 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i4 = 0; i4 < size4; i4++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j3 = (AbstractComponentCallbacksC0098j) this.f1677g.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(abstractComponentCallbacksC0098j3.toString());
            }
        }
        ArrayList arrayList2 = this.f1676f;
        if (arrayList2 != null && (size3 = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i5 = 0; i5 < size3; i5++) {
                C0090b c0090b = (C0090b) this.f1676f.get(i5);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i5);
                printWriter.print(": ");
                printWriter.println(c0090b.toString());
                c0090b.f(str2, printWriter);
            }
        }
        synchronized (this) {
            try {
                ArrayList arrayList3 = this.f1678h;
                if (arrayList3 != null && (size2 = arrayList3.size()) > 0) {
                    printWriter.print(str);
                    printWriter.println("Back Stack Indices:");
                    for (int i6 = 0; i6 < size2; i6++) {
                        Object obj = (C0090b) this.f1678h.get(i6);
                        printWriter.print(str);
                        printWriter.print("  #");
                        printWriter.print(i6);
                        printWriter.print(": ");
                        printWriter.println(obj);
                    }
                }
                ArrayList arrayList4 = this.f1679i;
                if (arrayList4 != null && arrayList4.size() > 0) {
                    printWriter.print(str);
                    printWriter.print("mAvailBackStackIndices: ");
                    printWriter.println(Arrays.toString(this.f1679i.toArray()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ArrayList arrayList5 = this.f1671a;
        if (arrayList5 != null && (size = arrayList5.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i7 = 0; i7 < size; i7++) {
                Object obj2 = (w) this.f1671a.get(i7);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i7);
                printWriter.print(": ");
                printWriter.println(obj2);
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.f1682l);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.f1683m);
        if (this.f1684n != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.f1684n);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.f1681k);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.f1687q);
        printWriter.print(" mStopped=");
        printWriter.print(this.r);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.f1688s);
        if (this.f1686p) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.f1686p);
        }
    }

    public final void E(w wVar, boolean z2) {
        if (!z2 && (this.f1687q || this.r)) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        synchronized (this) {
            try {
                if (!this.f1688s && this.f1682l != null) {
                    if (this.f1671a == null) {
                        this.f1671a = new ArrayList();
                    }
                    this.f1671a.add(wVar);
                    b0();
                    return;
                }
                if (!z2) {
                    throw new IllegalStateException("Activity has been destroyed");
                }
            } finally {
            }
        }
    }

    public final void F(boolean z2) {
        if (this.f1672b) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.f1682l == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        }
        if (Looper.myLooper() != this.f1682l.f1640d.getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z2 && (this.f1687q || this.r)) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.f1690u == null) {
            this.f1690u = new ArrayList();
            this.f1691v = new ArrayList();
        }
        this.f1672b = true;
        try {
            I(null, null);
        } finally {
            this.f1672b = false;
        }
    }

    public final boolean G() {
        boolean zA;
        F(true);
        boolean z2 = false;
        while (true) {
            ArrayList arrayList = this.f1690u;
            ArrayList arrayList2 = this.f1691v;
            synchronized (this) {
                try {
                    ArrayList arrayList3 = this.f1671a;
                    if (arrayList3 == null || arrayList3.size() == 0) {
                        zA = false;
                    } else {
                        int size = this.f1671a.size();
                        zA = false;
                        for (int i2 = 0; i2 < size; i2++) {
                            zA |= ((w) this.f1671a.get(i2)).a(arrayList, arrayList2);
                        }
                        this.f1671a.clear();
                        this.f1682l.f1640d.removeCallbacks(this.f1670B);
                    }
                } finally {
                }
            }
            if (!zA) {
                break;
            }
            this.f1672b = true;
            try {
                W(this.f1690u, this.f1691v);
                f();
                z2 = true;
            } catch (Throwable th) {
                f();
                throw th;
            }
        }
        if (this.f1689t) {
            this.f1689t = false;
            f0();
        }
        e();
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0131  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void H(java.util.ArrayList r20, java.util.ArrayList r21, int r22, int r23) {
        /*
            Method dump skipped, instruction units count: 714
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.z.H(java.util.ArrayList, java.util.ArrayList, int, int):void");
    }

    public final void I(ArrayList arrayList, ArrayList arrayList2) {
        boolean z2;
        int iIndexOf;
        C0090b c0090b;
        int iIndexOf2;
        ArrayList arrayList3 = this.f1695z;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i2 = 0;
        while (i2 < size) {
            y yVar = (y) this.f1695z.get(i2);
            if (arrayList == null || yVar.f1663a || (iIndexOf2 = arrayList.indexOf((c0090b = yVar.f1664b))) == -1 || !((Boolean) arrayList2.get(iIndexOf2)).booleanValue()) {
                int i3 = yVar.f1665c;
                C0090b c0090b2 = yVar.f1664b;
                if (i3 == 0 || (arrayList != null && c0090b2.j(arrayList, 0, arrayList.size()))) {
                    this.f1695z.remove(i2);
                    i2--;
                    size--;
                    if (arrayList == null || (z2 = yVar.f1663a) || (iIndexOf = arrayList.indexOf(c0090b2)) == -1 || !((Boolean) arrayList2.get(iIndexOf)).booleanValue()) {
                        yVar.a();
                    } else {
                        c0090b2.f1596a.g(c0090b2, z2, false, false);
                    }
                }
            } else {
                c0090b.f1596a.g(c0090b, yVar.f1663a, false, false);
            }
            i2++;
        }
    }

    public final AbstractComponentCallbacksC0098j J(int i2) {
        ArrayList arrayList = this.f1674d;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(size);
            if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.mFragmentId == i2) {
                return abstractComponentCallbacksC0098j;
            }
        }
        SparseArray sparseArray = this.f1675e;
        if (sparseArray == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(size2);
            if (abstractComponentCallbacksC0098j2 != null && abstractComponentCallbacksC0098j2.mFragmentId == i2) {
                return abstractComponentCallbacksC0098j2;
            }
        }
        return null;
    }

    public final AbstractComponentCallbacksC0098j K(String str) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098jFindFragmentByWho;
        SparseArray sparseArray = this.f1675e;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(size);
            if (abstractComponentCallbacksC0098j != null && (abstractComponentCallbacksC0098jFindFragmentByWho = abstractComponentCallbacksC0098j.findFragmentByWho(str)) != null) {
                return abstractComponentCallbacksC0098jFindFragmentByWho;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004e A[Catch: RuntimeException -> 0x0054, TRY_LEAVE, TryCatch #0 {RuntimeException -> 0x0054, blocks: (B:19:0x0044, B:21:0x004e), top: B:74:0x0044 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final F.f M(androidx.fragment.app.AbstractComponentCallbacksC0098j r6, int r7, boolean r8, int r9) {
        /*
            Method dump skipped, instruction units count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.z.M(androidx.fragment.app.j, int, boolean, int):F.f");
    }

    public final void N(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        if (abstractComponentCallbacksC0098j.mIndex >= 0) {
            return;
        }
        int i2 = this.f1673c;
        this.f1673c = i2 + 1;
        abstractComponentCallbacksC0098j.setIndex(i2, this.f1684n);
        if (this.f1675e == null) {
            this.f1675e = new SparseArray();
        }
        this.f1675e.put(abstractComponentCallbacksC0098j.mIndex, abstractComponentCallbacksC0098j);
    }

    public final void Q(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        Animator animator;
        if (abstractComponentCallbacksC0098j == null) {
            return;
        }
        int iMin = this.f1681k;
        if (abstractComponentCallbacksC0098j.mRemoving) {
            iMin = abstractComponentCallbacksC0098j.isInBackStack() ? Math.min(iMin, 1) : Math.min(iMin, 0);
        }
        S(abstractComponentCallbacksC0098j, iMin, abstractComponentCallbacksC0098j.getNextTransition(), abstractComponentCallbacksC0098j.getNextTransitionStyle(), false);
        if (abstractComponentCallbacksC0098j.mView != null) {
            ViewGroup viewGroup = abstractComponentCallbacksC0098j.mContainer;
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = null;
            if (viewGroup != null) {
                ArrayList arrayList = this.f1674d;
                int iIndexOf = arrayList.indexOf(abstractComponentCallbacksC0098j) - 1;
                while (true) {
                    if (iIndexOf < 0) {
                        break;
                    }
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j3 = (AbstractComponentCallbacksC0098j) arrayList.get(iIndexOf);
                    if (abstractComponentCallbacksC0098j3.mContainer == viewGroup && abstractComponentCallbacksC0098j3.mView != null) {
                        abstractComponentCallbacksC0098j2 = abstractComponentCallbacksC0098j3;
                        break;
                    }
                    iIndexOf--;
                }
            }
            if (abstractComponentCallbacksC0098j2 != null) {
                View view = abstractComponentCallbacksC0098j2.mView;
                ViewGroup viewGroup2 = abstractComponentCallbacksC0098j.mContainer;
                int iIndexOfChild = viewGroup2.indexOfChild(view);
                int iIndexOfChild2 = viewGroup2.indexOfChild(abstractComponentCallbacksC0098j.mView);
                if (iIndexOfChild2 < iIndexOfChild) {
                    viewGroup2.removeViewAt(iIndexOfChild2);
                    viewGroup2.addView(abstractComponentCallbacksC0098j.mView, iIndexOfChild);
                }
            }
            if (abstractComponentCallbacksC0098j.mIsNewlyAdded && abstractComponentCallbacksC0098j.mContainer != null) {
                float f2 = abstractComponentCallbacksC0098j.mPostponedAlpha;
                if (f2 > 0.0f) {
                    abstractComponentCallbacksC0098j.mView.setAlpha(f2);
                }
                abstractComponentCallbacksC0098j.mPostponedAlpha = 0.0f;
                abstractComponentCallbacksC0098j.mIsNewlyAdded = false;
                F.f fVarM = M(abstractComponentCallbacksC0098j, abstractComponentCallbacksC0098j.getNextTransition(), true, abstractComponentCallbacksC0098j.getNextTransitionStyle());
                if (fVarM != null) {
                    c0(abstractComponentCallbacksC0098j.mView, fVarM);
                    Animation animation = (Animation) fVarM.f123a;
                    if (animation != null) {
                        abstractComponentCallbacksC0098j.mView.startAnimation(animation);
                    } else {
                        View view2 = abstractComponentCallbacksC0098j.mView;
                        Animator animator2 = (Animator) fVarM.f124b;
                        animator2.setTarget(view2);
                        animator2.start();
                    }
                }
            }
        }
        if (abstractComponentCallbacksC0098j.mHiddenChanged) {
            if (abstractComponentCallbacksC0098j.mView != null) {
                F.f fVarM2 = M(abstractComponentCallbacksC0098j, abstractComponentCallbacksC0098j.getNextTransition(), !abstractComponentCallbacksC0098j.mHidden, abstractComponentCallbacksC0098j.getNextTransitionStyle());
                if (fVarM2 == null || (animator = (Animator) fVarM2.f124b) == null) {
                    if (fVarM2 != null) {
                        c0(abstractComponentCallbacksC0098j.mView, fVarM2);
                        View view3 = abstractComponentCallbacksC0098j.mView;
                        Animation animation2 = (Animation) fVarM2.f123a;
                        view3.startAnimation(animation2);
                        animation2.start();
                    }
                    abstractComponentCallbacksC0098j.mView.setVisibility((!abstractComponentCallbacksC0098j.mHidden || abstractComponentCallbacksC0098j.isHideReplaced()) ? 0 : 8);
                    if (abstractComponentCallbacksC0098j.isHideReplaced()) {
                        abstractComponentCallbacksC0098j.setHideReplaced(false);
                    }
                } else {
                    animator.setTarget(abstractComponentCallbacksC0098j.mView);
                    if (!abstractComponentCallbacksC0098j.mHidden) {
                        abstractComponentCallbacksC0098j.mView.setVisibility(0);
                    } else if (abstractComponentCallbacksC0098j.isHideReplaced()) {
                        abstractComponentCallbacksC0098j.setHideReplaced(false);
                    } else {
                        ViewGroup viewGroup3 = abstractComponentCallbacksC0098j.mContainer;
                        View view4 = abstractComponentCallbacksC0098j.mView;
                        viewGroup3.startViewTransition(view4);
                        animator.addListener(new r(viewGroup3, view4, abstractComponentCallbacksC0098j));
                    }
                    c0(abstractComponentCallbacksC0098j.mView, fVarM2);
                    animator.start();
                }
            }
            if (abstractComponentCallbacksC0098j.mAdded && abstractComponentCallbacksC0098j.mHasMenu && abstractComponentCallbacksC0098j.mMenuVisible) {
                this.f1686p = true;
            }
            abstractComponentCallbacksC0098j.mHiddenChanged = false;
            abstractComponentCallbacksC0098j.onHiddenChanged(abstractComponentCallbacksC0098j.mHidden);
        }
    }

    public final void R(int i2, boolean z2) {
        AbstractC0102n abstractC0102n;
        if (this.f1682l == null && i2 != 0) {
            throw new IllegalStateException("No activity");
        }
        if (z2 || i2 != this.f1681k) {
            this.f1681k = i2;
            if (this.f1675e != null) {
                ArrayList arrayList = this.f1674d;
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    Q((AbstractComponentCallbacksC0098j) arrayList.get(i3));
                }
                int size2 = this.f1675e.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i4);
                    if (abstractComponentCallbacksC0098j != null && ((abstractComponentCallbacksC0098j.mRemoving || abstractComponentCallbacksC0098j.mDetached) && !abstractComponentCallbacksC0098j.mIsNewlyAdded)) {
                        Q(abstractComponentCallbacksC0098j);
                    }
                }
                f0();
                if (this.f1686p && (abstractC0102n = this.f1682l) != null && this.f1681k == 4) {
                    ((C0099k) abstractC0102n).f1634f.b();
                    this.f1686p = false;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:222:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void S(androidx.fragment.app.AbstractComponentCallbacksC0098j r17, int r18, int r19, int r20, boolean r21) {
        /*
            Method dump skipped, instruction units count: 1021
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.z.S(androidx.fragment.app.j, int, int, int, boolean):void");
    }

    public final void T() {
        this.f1669A = null;
        this.f1687q = false;
        this.r = false;
        ArrayList arrayList = this.f1674d;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.noteStateNotSaved();
            }
        }
    }

    public final boolean U(ArrayList arrayList, ArrayList arrayList2, int i2, int i3) {
        int size;
        C0090b c0090b;
        ArrayList arrayList3 = this.f1676f;
        if (arrayList3 == null) {
            return false;
        }
        if (i2 >= 0 || (i3 & 1) != 0) {
            if (i2 >= 0) {
                size = arrayList3.size() - 1;
                while (size >= 0) {
                    C0090b c0090b2 = (C0090b) this.f1676f.get(size);
                    if (i2 >= 0 && i2 == c0090b2.f1607l) {
                        break;
                    }
                    size--;
                }
                if (size < 0) {
                    return false;
                }
                if ((i3 & 1) != 0) {
                    do {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        c0090b = (C0090b) this.f1676f.get(size);
                        if (i2 < 0) {
                            break;
                        }
                    } while (i2 == c0090b.f1607l);
                }
            } else {
                size = -1;
            }
            if (size == this.f1676f.size() - 1) {
                return false;
            }
            for (int size2 = this.f1676f.size() - 1; size2 > size; size2--) {
                arrayList.add(this.f1676f.remove(size2));
                arrayList2.add(Boolean.TRUE);
            }
        } else {
            int size3 = arrayList3.size() - 1;
            if (size3 < 0) {
                return false;
            }
            arrayList.add(this.f1676f.remove(size3));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    public final void V(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        boolean z2 = !abstractComponentCallbacksC0098j.isInBackStack();
        if (!abstractComponentCallbacksC0098j.mDetached || z2) {
            synchronized (this.f1674d) {
                this.f1674d.remove(abstractComponentCallbacksC0098j);
            }
            if (abstractComponentCallbacksC0098j.mHasMenu && abstractComponentCallbacksC0098j.mMenuVisible) {
                this.f1686p = true;
            }
            abstractComponentCallbacksC0098j.mAdded = false;
            abstractComponentCallbacksC0098j.mRemoving = true;
        }
    }

    public final void W(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        I(arrayList, arrayList2);
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            if (!((C0090b) arrayList.get(i2)).f1613s) {
                if (i3 != i2) {
                    H(arrayList, arrayList2, i3, i2);
                }
                i3 = i2 + 1;
                if (((Boolean) arrayList2.get(i2)).booleanValue()) {
                    while (i3 < size && ((Boolean) arrayList2.get(i3)).booleanValue() && !((C0090b) arrayList.get(i3)).f1613s) {
                        i3++;
                    }
                }
                H(arrayList, arrayList2, i2, i3);
                i2 = i3 - 1;
            }
            i2++;
        }
        if (i3 != size) {
            H(arrayList, arrayList2, i3, size);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void X(Parcelable parcelable, A a2) {
        List list;
        List list2;
        FragmentState[] fragmentStateArr;
        if (parcelable == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
        if (fragmentManagerState.f1537a == null) {
            return;
        }
        A a3 = null;
        if (a2 != null) {
            List list3 = a2.f1484a;
            list = a2.f1485b;
            list2 = a2.f1486c;
            int size = list3 != null ? list3.size() : 0;
            for (int i2 = 0; i2 < size; i2++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) list3.get(i2);
                int i3 = 0;
                while (true) {
                    fragmentStateArr = fragmentManagerState.f1537a;
                    if (i3 >= fragmentStateArr.length || fragmentStateArr[i3].f1543b == abstractComponentCallbacksC0098j.mIndex) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i3 == fragmentStateArr.length) {
                    g0(new IllegalStateException("Could not find active fragment with index " + abstractComponentCallbacksC0098j.mIndex));
                    throw null;
                }
                FragmentState fragmentState = fragmentStateArr[i3];
                fragmentState.f1553l = abstractComponentCallbacksC0098j;
                abstractComponentCallbacksC0098j.mSavedViewState = null;
                abstractComponentCallbacksC0098j.mBackStackNesting = 0;
                abstractComponentCallbacksC0098j.mInLayout = false;
                abstractComponentCallbacksC0098j.mAdded = false;
                abstractComponentCallbacksC0098j.mTarget = null;
                Bundle bundle = fragmentState.f1552k;
                if (bundle != null) {
                    bundle.setClassLoader(this.f1682l.f1639c.getClassLoader());
                    abstractComponentCallbacksC0098j.mSavedViewState = fragmentState.f1552k.getSparseParcelableArray("android:view_state");
                    abstractComponentCallbacksC0098j.mSavedFragmentState = fragmentState.f1552k;
                }
            }
        } else {
            list = null;
            list2 = null;
        }
        this.f1675e = new SparseArray(fragmentManagerState.f1537a.length);
        int i4 = 0;
        while (true) {
            FragmentState[] fragmentStateArr2 = fragmentManagerState.f1537a;
            if (i4 >= fragmentStateArr2.length) {
                break;
            }
            FragmentState fragmentState2 = fragmentStateArr2[i4];
            if (fragmentState2 != null) {
                A a4 = (list == null || i4 >= list.size()) ? a3 : (A) list.get(i4);
                androidx.lifecycle.j jVar = (list2 == null || i4 >= list2.size()) ? a3 : (androidx.lifecycle.j) list2.get(i4);
                AbstractC0102n abstractC0102n = this.f1682l;
                v vVar = this.f1683m;
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = this.f1684n;
                if (fragmentState2.f1553l == null) {
                    Context context = abstractC0102n.f1639c;
                    Bundle bundle2 = fragmentState2.f1550i;
                    if (bundle2 != null) {
                        bundle2.setClassLoader(context.getClassLoader());
                    }
                    String str = fragmentState2.f1542a;
                    if (vVar != null) {
                        fragmentState2.f1553l = vVar.a(context, str, bundle2);
                    } else {
                        fragmentState2.f1553l = AbstractComponentCallbacksC0098j.instantiate(context, str, bundle2);
                    }
                    Bundle bundle3 = fragmentState2.f1552k;
                    if (bundle3 != null) {
                        bundle3.setClassLoader(context.getClassLoader());
                        fragmentState2.f1553l.mSavedFragmentState = fragmentState2.f1552k;
                    }
                    fragmentState2.f1553l.setIndex(fragmentState2.f1543b, abstractComponentCallbacksC0098j2);
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j3 = fragmentState2.f1553l;
                    abstractComponentCallbacksC0098j3.mFromLayout = fragmentState2.f1544c;
                    abstractComponentCallbacksC0098j3.mRestored = true;
                    abstractComponentCallbacksC0098j3.mFragmentId = fragmentState2.f1545d;
                    abstractComponentCallbacksC0098j3.mContainerId = fragmentState2.f1546e;
                    abstractComponentCallbacksC0098j3.mTag = fragmentState2.f1547f;
                    abstractComponentCallbacksC0098j3.mRetainInstance = fragmentState2.f1548g;
                    abstractComponentCallbacksC0098j3.mDetached = fragmentState2.f1549h;
                    abstractComponentCallbacksC0098j3.mHidden = fragmentState2.f1551j;
                    abstractComponentCallbacksC0098j3.mFragmentManager = abstractC0102n.f1641e;
                }
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j4 = fragmentState2.f1553l;
                abstractComponentCallbacksC0098j4.mChildNonConfig = a4;
                abstractComponentCallbacksC0098j4.mViewModelStore = jVar;
                this.f1675e.put(abstractComponentCallbacksC0098j4.mIndex, abstractComponentCallbacksC0098j4);
                fragmentState2.f1553l = null;
            }
            i4++;
            a3 = null;
        }
        if (a2 != null) {
            List list4 = a2.f1484a;
            int size2 = list4 != null ? list4.size() : 0;
            for (int i5 = 0; i5 < size2; i5++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j5 = (AbstractComponentCallbacksC0098j) list4.get(i5);
                int i6 = abstractComponentCallbacksC0098j5.mTargetIndex;
                if (i6 >= 0) {
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j6 = (AbstractComponentCallbacksC0098j) this.f1675e.get(i6);
                    abstractComponentCallbacksC0098j5.mTarget = abstractComponentCallbacksC0098j6;
                    if (abstractComponentCallbacksC0098j6 == null) {
                        Log.w("FragmentManager", "Re-attaching retained fragment " + abstractComponentCallbacksC0098j5 + " target no longer exists: " + abstractComponentCallbacksC0098j5.mTargetIndex);
                    }
                }
            }
        }
        this.f1674d.clear();
        if (fragmentManagerState.f1538b != null) {
            int i7 = 0;
            while (true) {
                int[] iArr = fragmentManagerState.f1538b;
                if (i7 >= iArr.length) {
                    break;
                }
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j7 = (AbstractComponentCallbacksC0098j) this.f1675e.get(iArr[i7]);
                if (abstractComponentCallbacksC0098j7 == null) {
                    g0(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.f1538b[i7]));
                    throw null;
                }
                abstractComponentCallbacksC0098j7.mAdded = true;
                if (this.f1674d.contains(abstractComponentCallbacksC0098j7)) {
                    throw new IllegalStateException("Already added!");
                }
                synchronized (this.f1674d) {
                    this.f1674d.add(abstractComponentCallbacksC0098j7);
                }
                i7++;
            }
        }
        if (fragmentManagerState.f1539c != null) {
            this.f1676f = new ArrayList(fragmentManagerState.f1539c.length);
            int i8 = 0;
            while (true) {
                BackStackState[] backStackStateArr = fragmentManagerState.f1539c;
                if (i8 >= backStackStateArr.length) {
                    break;
                }
                BackStackState backStackState = backStackStateArr[i8];
                backStackState.getClass();
                C0090b c0090b = new C0090b(this);
                int i9 = 0;
                while (true) {
                    int[] iArr2 = backStackState.f1487a;
                    if (i9 >= iArr2.length) {
                        break;
                    }
                    C0089a c0089a = new C0089a();
                    c0089a.f1590a = iArr2[i9];
                    int i10 = i9 + 2;
                    int i11 = iArr2[i9 + 1];
                    if (i11 >= 0) {
                        c0089a.f1591b = (AbstractComponentCallbacksC0098j) this.f1675e.get(i11);
                    } else {
                        c0089a.f1591b = null;
                    }
                    int i12 = iArr2[i10];
                    c0089a.f1592c = i12;
                    int i13 = iArr2[i9 + 3];
                    c0089a.f1593d = i13;
                    int i14 = i9 + 5;
                    int i15 = iArr2[i9 + 4];
                    c0089a.f1594e = i15;
                    i9 += 6;
                    int i16 = iArr2[i14];
                    c0089a.f1595f = i16;
                    c0090b.f1598c = i12;
                    c0090b.f1599d = i13;
                    c0090b.f1600e = i15;
                    c0090b.f1601f = i16;
                    c0090b.b(c0089a);
                }
                c0090b.f1602g = backStackState.f1488b;
                c0090b.f1603h = backStackState.f1489c;
                c0090b.f1605j = backStackState.f1490d;
                c0090b.f1607l = backStackState.f1491e;
                c0090b.f1604i = true;
                c0090b.f1608m = backStackState.f1492f;
                c0090b.f1609n = backStackState.f1493g;
                c0090b.f1610o = backStackState.f1494h;
                c0090b.f1611p = backStackState.f1495i;
                c0090b.f1612q = backStackState.f1496j;
                c0090b.r = backStackState.f1497k;
                c0090b.f1613s = backStackState.f1498l;
                c0090b.c(1);
                this.f1676f.add(c0090b);
                int i17 = c0090b.f1607l;
                if (i17 >= 0) {
                    synchronized (this) {
                        try {
                            if (this.f1678h == null) {
                                this.f1678h = new ArrayList();
                            }
                            int size3 = this.f1678h.size();
                            if (i17 < size3) {
                                this.f1678h.set(i17, c0090b);
                            } else {
                                while (size3 < i17) {
                                    this.f1678h.add(null);
                                    if (this.f1679i == null) {
                                        this.f1679i = new ArrayList();
                                    }
                                    this.f1679i.add(Integer.valueOf(size3));
                                    size3++;
                                }
                                this.f1678h.add(c0090b);
                            }
                        } finally {
                        }
                    }
                }
                i8++;
            }
        } else {
            this.f1676f = null;
        }
        int i18 = fragmentManagerState.f1540d;
        if (i18 >= 0) {
            this.f1685o = (AbstractComponentCallbacksC0098j) this.f1675e.get(i18);
        }
        this.f1673c = fragmentManagerState.f1541e;
    }

    public final Parcelable Y() {
        BackStackState[] backStackStateArr;
        int[] iArr;
        int size;
        Bundle bundle;
        if (this.f1695z != null) {
            while (!this.f1695z.isEmpty()) {
                ((y) this.f1695z.remove(0)).a();
            }
        }
        SparseArray sparseArray = this.f1675e;
        int size2 = sparseArray == null ? 0 : sparseArray.size();
        int i2 = 0;
        while (true) {
            backStackStateArr = null;
            if (i2 >= size2) {
                break;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i2);
            if (abstractComponentCallbacksC0098j != null) {
                if (abstractComponentCallbacksC0098j.getAnimatingAway() != null) {
                    int stateAfterAnimating = abstractComponentCallbacksC0098j.getStateAfterAnimating();
                    View animatingAway = abstractComponentCallbacksC0098j.getAnimatingAway();
                    Animation animation = animatingAway.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        animatingAway.clearAnimation();
                    }
                    abstractComponentCallbacksC0098j.setAnimatingAway(null);
                    S(abstractComponentCallbacksC0098j, stateAfterAnimating, 0, 0, false);
                } else if (abstractComponentCallbacksC0098j.getAnimator() != null) {
                    abstractComponentCallbacksC0098j.getAnimator().end();
                }
            }
            i2++;
        }
        G();
        this.f1687q = true;
        this.f1669A = null;
        SparseArray sparseArray2 = this.f1675e;
        if (sparseArray2 == null || sparseArray2.size() <= 0) {
            return null;
        }
        int size3 = this.f1675e.size();
        FragmentState[] fragmentStateArr = new FragmentState[size3];
        boolean z2 = false;
        for (int i3 = 0; i3 < size3; i3++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i3);
            if (abstractComponentCallbacksC0098j2 != null) {
                if (abstractComponentCallbacksC0098j2.mIndex < 0) {
                    g0(new IllegalStateException("Failure saving state: active " + abstractComponentCallbacksC0098j2 + " has cleared index: " + abstractComponentCallbacksC0098j2.mIndex));
                    throw null;
                }
                FragmentState fragmentState = new FragmentState(abstractComponentCallbacksC0098j2);
                fragmentStateArr[i3] = fragmentState;
                if (abstractComponentCallbacksC0098j2.mState <= 0 || fragmentState.f1552k != null) {
                    fragmentState.f1552k = abstractComponentCallbacksC0098j2.mSavedFragmentState;
                } else {
                    if (this.f1693x == null) {
                        this.f1693x = new Bundle();
                    }
                    abstractComponentCallbacksC0098j2.performSaveInstanceState(this.f1693x);
                    u(false);
                    if (this.f1693x.isEmpty()) {
                        bundle = null;
                    } else {
                        bundle = this.f1693x;
                        this.f1693x = null;
                    }
                    if (abstractComponentCallbacksC0098j2.mView != null) {
                        Z(abstractComponentCallbacksC0098j2);
                    }
                    if (abstractComponentCallbacksC0098j2.mSavedViewState != null) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putSparseParcelableArray("android:view_state", abstractComponentCallbacksC0098j2.mSavedViewState);
                    }
                    if (!abstractComponentCallbacksC0098j2.mUserVisibleHint) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putBoolean("android:user_visible_hint", abstractComponentCallbacksC0098j2.mUserVisibleHint);
                    }
                    fragmentState.f1552k = bundle;
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j3 = abstractComponentCallbacksC0098j2.mTarget;
                    if (abstractComponentCallbacksC0098j3 != null) {
                        if (abstractComponentCallbacksC0098j3.mIndex < 0) {
                            g0(new IllegalStateException("Failure saving state: " + abstractComponentCallbacksC0098j2 + " has target not in fragment manager: " + abstractComponentCallbacksC0098j2.mTarget));
                            throw null;
                        }
                        if (bundle == null) {
                            fragmentState.f1552k = new Bundle();
                        }
                        Bundle bundle2 = fragmentState.f1552k;
                        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j4 = abstractComponentCallbacksC0098j2.mTarget;
                        int i4 = abstractComponentCallbacksC0098j4.mIndex;
                        if (i4 < 0) {
                            g0(new IllegalStateException(w0.a("Fragment ", abstractComponentCallbacksC0098j4, " is not currently in the FragmentManager")));
                            throw null;
                        }
                        bundle2.putInt("android:target_state", i4);
                        int i5 = abstractComponentCallbacksC0098j2.mTargetRequestCode;
                        if (i5 != 0) {
                            fragmentState.f1552k.putInt("android:target_req_state", i5);
                        }
                    }
                }
                z2 = true;
            }
        }
        if (!z2) {
            return null;
        }
        ArrayList arrayList = this.f1674d;
        int size4 = arrayList.size();
        if (size4 > 0) {
            iArr = new int[size4];
            for (int i6 = 0; i6 < size4; i6++) {
                int i7 = ((AbstractComponentCallbacksC0098j) arrayList.get(i6)).mIndex;
                iArr[i6] = i7;
                if (i7 < 0) {
                    g0(new IllegalStateException("Failure saving state: active " + arrayList.get(i6) + " has cleared index: " + iArr[i6]));
                    throw null;
                }
            }
        } else {
            iArr = null;
        }
        ArrayList arrayList2 = this.f1676f;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i8 = 0; i8 < size; i8++) {
                backStackStateArr[i8] = new BackStackState((C0090b) this.f1676f.get(i8));
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.f1540d = -1;
        fragmentManagerState.f1537a = fragmentStateArr;
        fragmentManagerState.f1538b = iArr;
        fragmentManagerState.f1539c = backStackStateArr;
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j5 = this.f1685o;
        if (abstractComponentCallbacksC0098j5 != null) {
            fragmentManagerState.f1540d = abstractComponentCallbacksC0098j5.mIndex;
        }
        fragmentManagerState.f1541e = this.f1673c;
        a0();
        return fragmentManagerState;
    }

    public final void Z(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        if (abstractComponentCallbacksC0098j.mInnerView == null) {
            return;
        }
        SparseArray sparseArray = this.f1694y;
        if (sparseArray == null) {
            this.f1694y = new SparseArray();
        } else {
            sparseArray.clear();
        }
        abstractComponentCallbacksC0098j.mInnerView.saveHierarchyState(this.f1694y);
        if (this.f1694y.size() > 0) {
            abstractComponentCallbacksC0098j.mSavedViewState = this.f1694y;
            this.f1694y = null;
        }
    }

    @Override // androidx.fragment.app.AbstractC0103o
    public final boolean a() {
        AbstractC0103o abstractC0103oPeekChildFragmentManager;
        if (this.f1687q || this.r) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        G();
        F(true);
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1685o;
        if (abstractComponentCallbacksC0098j != null && (abstractC0103oPeekChildFragmentManager = abstractComponentCallbacksC0098j.peekChildFragmentManager()) != null && abstractC0103oPeekChildFragmentManager.a()) {
            return true;
        }
        boolean zU = U(this.f1690u, this.f1691v, -1, 0);
        if (zU) {
            this.f1672b = true;
            try {
                W(this.f1690u, this.f1691v);
            } finally {
                f();
            }
        }
        if (this.f1689t) {
            this.f1689t = false;
            f0();
        }
        e();
        return zU;
    }

    public final void a0() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        A a2;
        if (this.f1675e != null) {
            arrayList = null;
            arrayList2 = null;
            arrayList3 = null;
            for (int i2 = 0; i2 < this.f1675e.size(); i2++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i2);
                if (abstractComponentCallbacksC0098j != null) {
                    if (abstractComponentCallbacksC0098j.mRetainInstance) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(abstractComponentCallbacksC0098j);
                        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = abstractComponentCallbacksC0098j.mTarget;
                        abstractComponentCallbacksC0098j.mTargetIndex = abstractComponentCallbacksC0098j2 != null ? abstractComponentCallbacksC0098j2.mIndex : -1;
                    }
                    z zVar = abstractComponentCallbacksC0098j.mChildFragmentManager;
                    if (zVar != null) {
                        zVar.a0();
                        a2 = abstractComponentCallbacksC0098j.mChildFragmentManager.f1669A;
                    } else {
                        a2 = abstractComponentCallbacksC0098j.mChildNonConfig;
                    }
                    if (arrayList2 == null && a2 != null) {
                        arrayList2 = new ArrayList(this.f1675e.size());
                        for (int i3 = 0; i3 < i2; i3++) {
                            arrayList2.add(null);
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(a2);
                    }
                    if (arrayList3 == null && abstractComponentCallbacksC0098j.mViewModelStore != null) {
                        arrayList3 = new ArrayList(this.f1675e.size());
                        for (int i4 = 0; i4 < i2; i4++) {
                            arrayList3.add(null);
                        }
                    }
                    if (arrayList3 != null) {
                        arrayList3.add(abstractComponentCallbacksC0098j.mViewModelStore);
                    }
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
            arrayList3 = null;
        }
        if (arrayList == null && arrayList2 == null && arrayList3 == null) {
            this.f1669A = null;
        } else {
            this.f1669A = new A(arrayList, arrayList2, arrayList3);
        }
    }

    public final void b(C0136c c0136c) {
        int i2 = this.f1681k;
        if (i2 < 1) {
            return;
        }
        int iMin = Math.min(i2, 3);
        ArrayList arrayList = this.f1674d;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i3);
            if (abstractComponentCallbacksC0098j.mState < iMin) {
                S(abstractComponentCallbacksC0098j, iMin, abstractComponentCallbacksC0098j.getNextAnim(), abstractComponentCallbacksC0098j.getNextTransition(), false);
                if (abstractComponentCallbacksC0098j.mView != null && !abstractComponentCallbacksC0098j.mHidden && abstractComponentCallbacksC0098j.mIsNewlyAdded) {
                    c0136c.add(abstractComponentCallbacksC0098j);
                }
            }
        }
    }

    public final void b0() {
        synchronized (this) {
            try {
                ArrayList arrayList = this.f1695z;
                boolean z2 = false;
                boolean z3 = (arrayList == null || arrayList.isEmpty()) ? false : true;
                ArrayList arrayList2 = this.f1671a;
                if (arrayList2 != null && arrayList2.size() == 1) {
                    z2 = true;
                }
                if (z3 || z2) {
                    this.f1682l.f1640d.removeCallbacks(this.f1670B);
                    this.f1682l.f1640d.post(this.f1670B);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void c(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, boolean z2) {
        N(abstractComponentCallbacksC0098j);
        if (abstractComponentCallbacksC0098j.mDetached) {
            return;
        }
        if (this.f1674d.contains(abstractComponentCallbacksC0098j)) {
            throw new IllegalStateException("Fragment already added: " + abstractComponentCallbacksC0098j);
        }
        synchronized (this.f1674d) {
            this.f1674d.add(abstractComponentCallbacksC0098j);
        }
        abstractComponentCallbacksC0098j.mAdded = true;
        abstractComponentCallbacksC0098j.mRemoving = false;
        if (abstractComponentCallbacksC0098j.mView == null) {
            abstractComponentCallbacksC0098j.mHiddenChanged = false;
        }
        if (abstractComponentCallbacksC0098j.mHasMenu && abstractComponentCallbacksC0098j.mMenuVisible) {
            this.f1686p = true;
        }
        if (z2) {
            S(abstractComponentCallbacksC0098j, this.f1681k, 0, 0, false);
        }
    }

    public final void d(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        if (abstractComponentCallbacksC0098j.mDetached) {
            abstractComponentCallbacksC0098j.mDetached = false;
            if (abstractComponentCallbacksC0098j.mAdded) {
                return;
            }
            if (this.f1674d.contains(abstractComponentCallbacksC0098j)) {
                throw new IllegalStateException("Fragment already added: " + abstractComponentCallbacksC0098j);
            }
            synchronized (this.f1674d) {
                this.f1674d.add(abstractComponentCallbacksC0098j);
            }
            abstractComponentCallbacksC0098j.mAdded = true;
            if (abstractComponentCallbacksC0098j.mHasMenu && abstractComponentCallbacksC0098j.mMenuVisible) {
                this.f1686p = true;
            }
        }
    }

    public final void d0(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        if (abstractComponentCallbacksC0098j == null || (this.f1675e.get(abstractComponentCallbacksC0098j.mIndex) == abstractComponentCallbacksC0098j && (abstractComponentCallbacksC0098j.mHost == null || abstractComponentCallbacksC0098j.getFragmentManager() == this))) {
            this.f1685o = abstractComponentCallbacksC0098j;
            return;
        }
        throw new IllegalArgumentException("Fragment " + abstractComponentCallbacksC0098j + " is not an active fragment of FragmentManager " + this);
    }

    public final void e() {
        SparseArray sparseArray = this.f1675e;
        if (sparseArray != null) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                if (this.f1675e.valueAt(size) == null) {
                    SparseArray sparseArray2 = this.f1675e;
                    sparseArray2.delete(sparseArray2.keyAt(size));
                }
            }
        }
    }

    public final void f() {
        this.f1672b = false;
        this.f1691v.clear();
        this.f1690u.clear();
    }

    public final void f0() {
        if (this.f1675e == null) {
            return;
        }
        for (int i2 = 0; i2 < this.f1675e.size(); i2++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i2);
            if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.mDeferStart) {
                if (this.f1672b) {
                    this.f1689t = true;
                } else {
                    abstractComponentCallbacksC0098j.mDeferStart = false;
                    S(abstractComponentCallbacksC0098j, this.f1681k, 0, 0, false);
                }
            }
        }
    }

    public final void g(C0090b c0090b, boolean z2, boolean z3, boolean z4) {
        if (z2) {
            c0090b.h(z4);
        } else {
            c0090b.g();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(c0090b);
        arrayList2.add(Boolean.valueOf(z2));
        if (z3) {
            H.k(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z4) {
            R(this.f1681k, true);
        }
        SparseArray sparseArray = this.f1675e;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(i2);
                if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.mView != null && abstractComponentCallbacksC0098j.mIsNewlyAdded && c0090b.i(abstractComponentCallbacksC0098j.mContainerId)) {
                    float f2 = abstractComponentCallbacksC0098j.mPostponedAlpha;
                    if (f2 > 0.0f) {
                        abstractComponentCallbacksC0098j.mView.setAlpha(f2);
                    }
                    if (z4) {
                        abstractComponentCallbacksC0098j.mPostponedAlpha = 0.0f;
                    } else {
                        abstractComponentCallbacksC0098j.mPostponedAlpha = -1.0f;
                        abstractComponentCallbacksC0098j.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    public final void g0(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new C0160a());
        AbstractC0102n abstractC0102n = this.f1682l;
        if (abstractC0102n == null) {
            try {
                D("  ", null, printWriter, new String[0]);
                throw runtimeException;
            } catch (Exception e2) {
                Log.e("FragmentManager", "Failed dumping state", e2);
                throw runtimeException;
            }
        }
        try {
            ((C0099k) abstractC0102n).f1634f.dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        } catch (Exception e3) {
            Log.e("FragmentManager", "Failed dumping state", e3);
            throw runtimeException;
        }
    }

    public final void h(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        if (abstractComponentCallbacksC0098j.mDetached) {
            return;
        }
        abstractComponentCallbacksC0098j.mDetached = true;
        if (abstractComponentCallbacksC0098j.mAdded) {
            synchronized (this.f1674d) {
                this.f1674d.remove(abstractComponentCallbacksC0098j);
            }
            if (abstractComponentCallbacksC0098j.mHasMenu && abstractComponentCallbacksC0098j.mMenuVisible) {
                this.f1686p = true;
            }
            abstractComponentCallbacksC0098j.mAdded = false;
        }
    }

    public final boolean i(MenuItem menuItem) {
        if (this.f1681k < 1) {
            return false;
        }
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.f1674d;
            if (i2 >= arrayList.size()) {
                return false;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.performContextItemSelected(menuItem)) {
                return true;
            }
            i2++;
        }
    }

    public final boolean j(Menu menu, MenuInflater menuInflater) {
        if (this.f1681k < 1) {
            return false;
        }
        ArrayList arrayList = null;
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            ArrayList arrayList2 = this.f1674d;
            if (i2 >= arrayList2.size()) {
                break;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList2.get(i2);
            if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(abstractComponentCallbacksC0098j);
                z2 = true;
            }
            i2++;
        }
        if (this.f1677g != null) {
            for (int i3 = 0; i3 < this.f1677g.size(); i3++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = (AbstractComponentCallbacksC0098j) this.f1677g.get(i3);
                if (arrayList == null || !arrayList.contains(abstractComponentCallbacksC0098j2)) {
                    abstractComponentCallbacksC0098j2.onDestroyOptionsMenu();
                }
            }
        }
        this.f1677g = arrayList;
        return z2;
    }

    public final void k() {
        this.f1688s = true;
        G();
        C(0);
        this.f1682l = null;
        this.f1683m = null;
        this.f1684n = null;
    }

    public final void l(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).l(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void m(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).m(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void n(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).n(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void o(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).o(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, v.f1659a);
        if (attributeValue == null) {
            attributeValue = typedArrayObtainStyledAttributes.getString(0);
        }
        String str2 = attributeValue;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        String string = typedArrayObtainStyledAttributes.getString(2);
        typedArrayObtainStyledAttributes.recycle();
        if (!AbstractComponentCallbacksC0098j.isSupportFragmentClass(this.f1682l.f1639c, str2)) {
            return null;
        }
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + str2);
        }
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098jJ = resourceId != -1 ? J(resourceId) : null;
        if (abstractComponentCallbacksC0098jJ == null && string != null) {
            ArrayList arrayList = this.f1674d;
            int size = arrayList.size() - 1;
            while (true) {
                if (size >= 0) {
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(size);
                    if (abstractComponentCallbacksC0098j != null && string.equals(abstractComponentCallbacksC0098j.mTag)) {
                        abstractComponentCallbacksC0098jJ = abstractComponentCallbacksC0098j;
                        break;
                    }
                    size--;
                } else {
                    SparseArray sparseArray = this.f1675e;
                    if (sparseArray != null) {
                        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = (AbstractComponentCallbacksC0098j) this.f1675e.valueAt(size2);
                            if (abstractComponentCallbacksC0098j2 != null && string.equals(abstractComponentCallbacksC0098j2.mTag)) {
                                abstractComponentCallbacksC0098jJ = abstractComponentCallbacksC0098j2;
                                break;
                            }
                        }
                        abstractComponentCallbacksC0098jJ = null;
                    } else {
                        abstractComponentCallbacksC0098jJ = null;
                    }
                }
            }
        }
        if (abstractComponentCallbacksC0098jJ == null && id != -1) {
            abstractComponentCallbacksC0098jJ = J(id);
        }
        if (abstractComponentCallbacksC0098jJ == null) {
            abstractComponentCallbacksC0098jJ = this.f1683m.a(context, str2, null);
            abstractComponentCallbacksC0098jJ.mFromLayout = true;
            abstractComponentCallbacksC0098jJ.mFragmentId = resourceId != 0 ? resourceId : id;
            abstractComponentCallbacksC0098jJ.mContainerId = id;
            abstractComponentCallbacksC0098jJ.mTag = string;
            abstractComponentCallbacksC0098jJ.mInLayout = true;
            abstractComponentCallbacksC0098jJ.mFragmentManager = this;
            AbstractC0102n abstractC0102n = this.f1682l;
            abstractComponentCallbacksC0098jJ.mHost = abstractC0102n;
            abstractComponentCallbacksC0098jJ.onInflate(abstractC0102n.f1639c, attributeSet, abstractComponentCallbacksC0098jJ.mSavedFragmentState);
            c(abstractComponentCallbacksC0098jJ, true);
        } else {
            if (abstractComponentCallbacksC0098jJ.mInLayout) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + str2);
            }
            abstractComponentCallbacksC0098jJ.mInLayout = true;
            AbstractC0102n abstractC0102n2 = this.f1682l;
            abstractComponentCallbacksC0098jJ.mHost = abstractC0102n2;
            if (!abstractComponentCallbacksC0098jJ.mRetaining) {
                abstractComponentCallbacksC0098jJ.onInflate(abstractC0102n2.f1639c, attributeSet, abstractComponentCallbacksC0098jJ.mSavedFragmentState);
            }
        }
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j3 = abstractComponentCallbacksC0098jJ;
        int i2 = this.f1681k;
        if (i2 >= 1 || !abstractComponentCallbacksC0098j3.mFromLayout) {
            S(abstractComponentCallbacksC0098j3, i2, 0, 0, false);
        } else {
            S(abstractComponentCallbacksC0098j3, 1, 0, 0, false);
        }
        View view2 = abstractComponentCallbacksC0098j3.mView;
        if (view2 == null) {
            throw new IllegalStateException("Fragment " + str2 + " did not create a view.");
        }
        if (resourceId != 0) {
            view2.setId(resourceId);
        }
        if (abstractComponentCallbacksC0098j3.mView.getTag() == null) {
            abstractComponentCallbacksC0098j3.mView.setTag(string);
        }
        return abstractComponentCallbacksC0098j3.mView;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    public final void p(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).p(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void q(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).q(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void r(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).r(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void s(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).s(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void t(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).t(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            Y.r.c(abstractComponentCallbacksC0098j, sb);
        } else {
            Y.r.c(this.f1682l, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    public final void u(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).u(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void v(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).v(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void w(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).w(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void x(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).x(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final void y(boolean z2) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1684n;
        if (abstractComponentCallbacksC0098j != null) {
            AbstractC0103o fragmentManager = abstractComponentCallbacksC0098j.getFragmentManager();
            if (fragmentManager instanceof z) {
                ((z) fragmentManager).y(true);
            }
        }
        Iterator it = this.f1680j.iterator();
        if (it.hasNext()) {
            w0.c(it.next());
            if (!z2) {
                throw null;
            }
            throw null;
        }
    }

    public final boolean z(MenuItem menuItem) {
        if (this.f1681k < 1) {
            return false;
        }
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.f1674d;
            if (i2 >= arrayList.size()) {
                return false;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null && abstractComponentCallbacksC0098j.performOptionsItemSelected(menuItem)) {
                return true;
            }
            i2++;
        }
    }
}
