package androidx.fragment.app;

import com.motorola.mobiledesktop.core.uinput.EventType;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* JADX INFO: renamed from: androidx.fragment.app.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0090b extends C implements w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final z f1596a;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1598c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1599d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f1600e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1601f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1602g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f1603h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1604i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public String f1605j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f1606k;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1608m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public CharSequence f1609n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f1610o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public CharSequence f1611p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public ArrayList f1612q;
    public ArrayList r;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final ArrayList f1597b = new ArrayList();

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1607l = -1;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f1613s = false;

    public C0090b(z zVar) {
        this.f1596a = zVar;
    }

    public static boolean k(C0089a c0089a) {
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = c0089a.f1591b;
        return (abstractComponentCallbacksC0098j == null || !abstractComponentCallbacksC0098j.mAdded || abstractComponentCallbacksC0098j.mView == null || abstractComponentCallbacksC0098j.mDetached || abstractComponentCallbacksC0098j.mHidden || !abstractComponentCallbacksC0098j.isPostponed()) ? false : true;
    }

    @Override // androidx.fragment.app.w
    public final boolean a(ArrayList arrayList, ArrayList arrayList2) {
        Field field = z.f1666C;
        arrayList.add(this);
        arrayList2.add(Boolean.FALSE);
        if (!this.f1604i) {
            return true;
        }
        z zVar = this.f1596a;
        if (zVar.f1676f == null) {
            zVar.f1676f = new ArrayList();
        }
        zVar.f1676f.add(this);
        return true;
    }

    public final void b(C0089a c0089a) {
        this.f1597b.add(c0089a);
        c0089a.f1592c = this.f1598c;
        c0089a.f1593d = this.f1599d;
        c0089a.f1594e = this.f1600e;
        c0089a.f1595f = this.f1601f;
    }

    public final void c(int i2) {
        if (this.f1604i) {
            Field field = z.f1666C;
            ArrayList arrayList = this.f1597b;
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = ((C0089a) arrayList.get(i3)).f1591b;
                if (abstractComponentCallbacksC0098j != null) {
                    abstractComponentCallbacksC0098j.mBackStackNesting += i2;
                    Field field2 = z.f1666C;
                }
            }
        }
    }

    public final int d(boolean z2) {
        int size;
        if (this.f1606k) {
            throw new IllegalStateException("commit already called");
        }
        Field field = z.f1666C;
        this.f1606k = true;
        if (this.f1604i) {
            z zVar = this.f1596a;
            synchronized (zVar) {
                try {
                    ArrayList arrayList = zVar.f1679i;
                    if (arrayList == null || arrayList.size() <= 0) {
                        if (zVar.f1678h == null) {
                            zVar.f1678h = new ArrayList();
                        }
                        size = zVar.f1678h.size();
                        zVar.f1678h.add(this);
                    } else {
                        ArrayList arrayList2 = zVar.f1679i;
                        size = ((Integer) arrayList2.remove(arrayList2.size() - 1)).intValue();
                        zVar.f1678h.set(size, this);
                    }
                } finally {
                }
            }
            this.f1607l = size;
        } else {
            this.f1607l = -1;
        }
        this.f1596a.E(this, z2);
        return this.f1607l;
    }

    public final void e(int i2, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, String str, int i3) {
        Class<?> cls = abstractComponentCallbacksC0098j.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        abstractComponentCallbacksC0098j.mFragmentManager = this.f1596a;
        if (str != null) {
            String str2 = abstractComponentCallbacksC0098j.mTag;
            if (str2 != null && !str.equals(str2)) {
                throw new IllegalStateException("Can't change tag of fragment " + abstractComponentCallbacksC0098j + ": was " + abstractComponentCallbacksC0098j.mTag + " now " + str);
            }
            abstractComponentCallbacksC0098j.mTag = str;
        }
        if (i2 != 0) {
            if (i2 == -1) {
                throw new IllegalArgumentException("Can't add fragment " + abstractComponentCallbacksC0098j + " with tag " + str + " to container view with no id");
            }
            int i4 = abstractComponentCallbacksC0098j.mFragmentId;
            if (i4 != 0 && i4 != i2) {
                throw new IllegalStateException("Can't change container ID of fragment " + abstractComponentCallbacksC0098j + ": was " + abstractComponentCallbacksC0098j.mFragmentId + " now " + i2);
            }
            abstractComponentCallbacksC0098j.mFragmentId = i2;
            abstractComponentCallbacksC0098j.mContainerId = i2;
        }
        b(new C0089a(i3, abstractComponentCallbacksC0098j));
    }

    public final void f(String str, PrintWriter printWriter) {
        String str2;
        printWriter.print(str);
        printWriter.print("mName=");
        printWriter.print(this.f1605j);
        printWriter.print(" mIndex=");
        printWriter.print(this.f1607l);
        printWriter.print(" mCommitted=");
        printWriter.println(this.f1606k);
        if (this.f1602g != 0) {
            printWriter.print(str);
            printWriter.print("mTransition=#");
            printWriter.print(Integer.toHexString(this.f1602g));
            printWriter.print(" mTransitionStyle=#");
            printWriter.println(Integer.toHexString(this.f1603h));
        }
        if (this.f1598c != 0 || this.f1599d != 0) {
            printWriter.print(str);
            printWriter.print("mEnterAnim=#");
            printWriter.print(Integer.toHexString(this.f1598c));
            printWriter.print(" mExitAnim=#");
            printWriter.println(Integer.toHexString(this.f1599d));
        }
        if (this.f1600e != 0 || this.f1601f != 0) {
            printWriter.print(str);
            printWriter.print("mPopEnterAnim=#");
            printWriter.print(Integer.toHexString(this.f1600e));
            printWriter.print(" mPopExitAnim=#");
            printWriter.println(Integer.toHexString(this.f1601f));
        }
        if (this.f1608m != 0 || this.f1609n != null) {
            printWriter.print(str);
            printWriter.print("mBreadCrumbTitleRes=#");
            printWriter.print(Integer.toHexString(this.f1608m));
            printWriter.print(" mBreadCrumbTitleText=");
            printWriter.println(this.f1609n);
        }
        if (this.f1610o != 0 || this.f1611p != null) {
            printWriter.print(str);
            printWriter.print("mBreadCrumbShortTitleRes=#");
            printWriter.print(Integer.toHexString(this.f1610o));
            printWriter.print(" mBreadCrumbShortTitleText=");
            printWriter.println(this.f1611p);
        }
        ArrayList arrayList = this.f1597b;
        if (arrayList.isEmpty()) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Operations:");
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0089a c0089a = (C0089a) arrayList.get(i2);
            switch (c0089a.f1590a) {
                case 0:
                    str2 = "NULL";
                    break;
                case 1:
                    str2 = "ADD";
                    break;
                case 2:
                    str2 = "REPLACE";
                    break;
                case 3:
                    str2 = "REMOVE";
                    break;
                case EventType.EVENT_MSC /* 4 */:
                    str2 = "HIDE";
                    break;
                case EventType.EVENT_SW /* 5 */:
                    str2 = "SHOW";
                    break;
                case 6:
                    str2 = "DETACH";
                    break;
                case 7:
                    str2 = "ATTACH";
                    break;
                case 8:
                    str2 = "SET_PRIMARY_NAV";
                    break;
                case 9:
                    str2 = "UNSET_PRIMARY_NAV";
                    break;
                default:
                    str2 = "cmd=" + c0089a.f1590a;
                    break;
            }
            printWriter.print(str);
            printWriter.print("  Op #");
            printWriter.print(i2);
            printWriter.print(": ");
            printWriter.print(str2);
            printWriter.print(" ");
            printWriter.println(c0089a.f1591b);
            if (c0089a.f1592c != 0 || c0089a.f1593d != 0) {
                printWriter.print(str);
                printWriter.print("enterAnim=#");
                printWriter.print(Integer.toHexString(c0089a.f1592c));
                printWriter.print(" exitAnim=#");
                printWriter.println(Integer.toHexString(c0089a.f1593d));
            }
            if (c0089a.f1594e != 0 || c0089a.f1595f != 0) {
                printWriter.print(str);
                printWriter.print("popEnterAnim=#");
                printWriter.print(Integer.toHexString(c0089a.f1594e));
                printWriter.print(" popExitAnim=#");
                printWriter.println(Integer.toHexString(c0089a.f1595f));
            }
        }
    }

    public final void g() {
        ArrayList arrayList = this.f1597b;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            z zVar = this.f1596a;
            if (i2 >= size) {
                if (this.f1613s) {
                    return;
                }
                zVar.R(zVar.f1681k, true);
                return;
            }
            C0089a c0089a = (C0089a) arrayList.get(i2);
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = c0089a.f1591b;
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.setNextTransition(this.f1602g, this.f1603h);
            }
            switch (c0089a.f1590a) {
                case 1:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1592c);
                    zVar.c(abstractComponentCallbacksC0098j, false);
                    break;
                case 2:
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + c0089a.f1590a);
                case 3:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1593d);
                    zVar.V(abstractComponentCallbacksC0098j);
                    break;
                case EventType.EVENT_MSC /* 4 */:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1593d);
                    zVar.getClass();
                    if (!abstractComponentCallbacksC0098j.mHidden) {
                        abstractComponentCallbacksC0098j.mHidden = true;
                        abstractComponentCallbacksC0098j.mHiddenChanged = !abstractComponentCallbacksC0098j.mHiddenChanged;
                    }
                    break;
                case EventType.EVENT_SW /* 5 */:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1592c);
                    zVar.getClass();
                    if (abstractComponentCallbacksC0098j.mHidden) {
                        abstractComponentCallbacksC0098j.mHidden = false;
                        abstractComponentCallbacksC0098j.mHiddenChanged = !abstractComponentCallbacksC0098j.mHiddenChanged;
                    }
                    break;
                case 6:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1593d);
                    zVar.h(abstractComponentCallbacksC0098j);
                    break;
                case 7:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1592c);
                    zVar.d(abstractComponentCallbacksC0098j);
                    break;
                case 8:
                    zVar.d0(abstractComponentCallbacksC0098j);
                    break;
                case 9:
                    zVar.d0(null);
                    break;
            }
            if (!this.f1613s && c0089a.f1590a != 1 && abstractComponentCallbacksC0098j != null) {
                zVar.Q(abstractComponentCallbacksC0098j);
            }
            i2++;
        }
    }

    public final void h(boolean z2) {
        ArrayList arrayList = this.f1597b;
        int size = arrayList.size() - 1;
        while (true) {
            z zVar = this.f1596a;
            if (size < 0) {
                if (this.f1613s || !z2) {
                    return;
                }
                zVar.R(zVar.f1681k, true);
                return;
            }
            C0089a c0089a = (C0089a) arrayList.get(size);
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = c0089a.f1591b;
            if (abstractComponentCallbacksC0098j != null) {
                int i2 = this.f1602g;
                Field field = z.f1666C;
                abstractComponentCallbacksC0098j.setNextTransition(i2 != 4097 ? i2 != 4099 ? i2 != 8194 ? 0 : 4097 : 4099 : 8194, this.f1603h);
            }
            switch (c0089a.f1590a) {
                case 1:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1595f);
                    zVar.V(abstractComponentCallbacksC0098j);
                    break;
                case 2:
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + c0089a.f1590a);
                case 3:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1594e);
                    zVar.c(abstractComponentCallbacksC0098j, false);
                    break;
                case EventType.EVENT_MSC /* 4 */:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1594e);
                    zVar.getClass();
                    if (abstractComponentCallbacksC0098j.mHidden) {
                        abstractComponentCallbacksC0098j.mHidden = false;
                        abstractComponentCallbacksC0098j.mHiddenChanged = !abstractComponentCallbacksC0098j.mHiddenChanged;
                    }
                    break;
                case EventType.EVENT_SW /* 5 */:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1595f);
                    zVar.getClass();
                    if (!abstractComponentCallbacksC0098j.mHidden) {
                        abstractComponentCallbacksC0098j.mHidden = true;
                        abstractComponentCallbacksC0098j.mHiddenChanged = !abstractComponentCallbacksC0098j.mHiddenChanged;
                    }
                    break;
                case 6:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1594e);
                    zVar.d(abstractComponentCallbacksC0098j);
                    break;
                case 7:
                    abstractComponentCallbacksC0098j.setNextAnim(c0089a.f1595f);
                    zVar.h(abstractComponentCallbacksC0098j);
                    break;
                case 8:
                    zVar.d0(null);
                    break;
                case 9:
                    zVar.d0(abstractComponentCallbacksC0098j);
                    break;
            }
            if (!this.f1613s && c0089a.f1590a != 3 && abstractComponentCallbacksC0098j != null) {
                zVar.Q(abstractComponentCallbacksC0098j);
            }
            size--;
        }
    }

    public final boolean i(int i2) {
        ArrayList arrayList = this.f1597b;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = ((C0089a) arrayList.get(i3)).f1591b;
            int i4 = abstractComponentCallbacksC0098j != null ? abstractComponentCallbacksC0098j.mContainerId : 0;
            if (i4 != 0 && i4 == i2) {
                return true;
            }
        }
        return false;
    }

    public final boolean j(ArrayList arrayList, int i2, int i3) {
        if (i3 == i2) {
            return false;
        }
        ArrayList arrayList2 = this.f1597b;
        int size = arrayList2.size();
        int i4 = -1;
        for (int i5 = 0; i5 < size; i5++) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = ((C0089a) arrayList2.get(i5)).f1591b;
            int i6 = abstractComponentCallbacksC0098j != null ? abstractComponentCallbacksC0098j.mContainerId : 0;
            if (i6 != 0 && i6 != i4) {
                for (int i7 = i2; i7 < i3; i7++) {
                    C0090b c0090b = (C0090b) arrayList.get(i7);
                    int size2 = c0090b.f1597b.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = ((C0089a) c0090b.f1597b.get(i8)).f1591b;
                        if ((abstractComponentCallbacksC0098j2 != null ? abstractComponentCallbacksC0098j2.mContainerId : 0) == i6) {
                            return true;
                        }
                    }
                }
                i4 = i6;
            }
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.f1607l >= 0) {
            sb.append(" #");
            sb.append(this.f1607l);
        }
        if (this.f1605j != null) {
            sb.append(" ");
            sb.append(this.f1605j);
        }
        sb.append("}");
        return sb.toString();
    }
}
