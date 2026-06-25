package v;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;

/* JADX INFO: loaded from: classes.dex */
public final class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ViewParent f2829a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ViewParent f2830b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final View f2831c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2832d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int[] f2833e;

    public g(View view) {
        this.f2831c = view;
    }

    public final boolean a(float f2, float f3, boolean z2) {
        ViewParent viewParentE;
        if (!this.f2832d || (viewParentE = e(0)) == null) {
            return false;
        }
        try {
            return viewParentE.onNestedFling(this.f2831c, f2, f3, z2);
        } catch (AbstractMethodError e2) {
            Log.e("ViewParentCompat", "ViewParent " + viewParentE + " does not implement interface method onNestedFling", e2);
            return false;
        }
    }

    public final boolean b(float f2, float f3) {
        ViewParent viewParentE;
        if (!this.f2832d || (viewParentE = e(0)) == null) {
            return false;
        }
        try {
            return viewParentE.onNestedPreFling(this.f2831c, f2, f3);
        } catch (AbstractMethodError e2) {
            Log.e("ViewParentCompat", "ViewParent " + viewParentE + " does not implement interface method onNestedPreFling", e2);
            return false;
        }
    }

    public final boolean c(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        ViewParent viewParentE;
        int i5;
        int i6;
        int[] iArr3;
        if (!this.f2832d || (viewParentE = e(i4)) == null) {
            return false;
        }
        if (i2 == 0 && i3 == 0) {
            if (iArr2 == null) {
                return false;
            }
            iArr2[0] = 0;
            iArr2[1] = 0;
            return false;
        }
        View view = this.f2831c;
        if (iArr2 != null) {
            view.getLocationInWindow(iArr2);
            int i7 = iArr2[0];
            i6 = iArr2[1];
            i5 = i7;
        } else {
            i5 = 0;
            i6 = 0;
        }
        if (iArr == null) {
            if (this.f2833e == null) {
                this.f2833e = new int[2];
            }
            iArr3 = this.f2833e;
        } else {
            iArr3 = iArr;
        }
        iArr3[0] = 0;
        iArr3[1] = 0;
        boolean z2 = viewParentE instanceof h;
        View view2 = this.f2831c;
        if (z2) {
            ((h) viewParentE).d(view2, i2, i3, iArr3, i4);
        } else if (i4 == 0) {
            try {
                viewParentE.onNestedPreScroll(view2, i2, i3, iArr3);
            } catch (AbstractMethodError e2) {
                Log.e("ViewParentCompat", "ViewParent " + viewParentE + " does not implement interface method onNestedPreScroll", e2);
            }
        }
        if (iArr2 != null) {
            view.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i5;
            iArr2[1] = iArr2[1] - i6;
        }
        return (iArr3[0] == 0 && iArr3[1] == 0) ? false : true;
    }

    public final boolean d(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        ViewParent viewParentE;
        int i7;
        int i8;
        if (!this.f2832d || (viewParentE = e(i6)) == null) {
            return false;
        }
        if (i2 == 0 && i3 == 0 && i4 == 0 && i5 == 0) {
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
            return false;
        }
        View view = this.f2831c;
        if (iArr != null) {
            view.getLocationInWindow(iArr);
            i7 = iArr[0];
            i8 = iArr[1];
        } else {
            i7 = 0;
            i8 = 0;
        }
        boolean z2 = viewParentE instanceof h;
        View view2 = this.f2831c;
        if (z2) {
            ((h) viewParentE).a(view2, i2, i3, i4, i5, i6);
        } else if (i6 == 0) {
            try {
                viewParentE.onNestedScroll(view2, i2, i3, i4, i5);
            } catch (AbstractMethodError e2) {
                Log.e("ViewParentCompat", "ViewParent " + viewParentE + " does not implement interface method onNestedScroll", e2);
            }
        }
        if (iArr != null) {
            view.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i7;
            iArr[1] = iArr[1] - i8;
        }
        return true;
    }

    public final ViewParent e(int i2) {
        if (i2 == 0) {
            return this.f2829a;
        }
        if (i2 != 1) {
            return null;
        }
        return this.f2830b;
    }

    public final boolean f(int i2) {
        return e(i2) != null;
    }

    public final boolean g(int i2, int i3) {
        boolean zOnStartNestedScroll;
        if (f(i3)) {
            return true;
        }
        if (this.f2832d) {
            View view = this.f2831c;
            View view2 = view;
            for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
                boolean z2 = parent instanceof h;
                if (z2) {
                    zOnStartNestedScroll = ((h) parent).b(view2, view, i2, i3);
                } else if (i3 == 0) {
                    try {
                        zOnStartNestedScroll = parent.onStartNestedScroll(view2, view, i2);
                    } catch (AbstractMethodError e2) {
                        Log.e("ViewParentCompat", "ViewParent " + parent + " does not implement interface method onStartNestedScroll", e2);
                        zOnStartNestedScroll = false;
                    }
                } else {
                    zOnStartNestedScroll = false;
                }
                if (zOnStartNestedScroll) {
                    if (i3 == 0) {
                        this.f2829a = parent;
                    } else if (i3 == 1) {
                        this.f2830b = parent;
                    }
                    if (z2) {
                        ((h) parent).e(view, i2, i3);
                    } else if (i3 == 0) {
                        try {
                            parent.onNestedScrollAccepted(view2, view, i2);
                        } catch (AbstractMethodError e3) {
                            Log.e("ViewParentCompat", "ViewParent " + parent + " does not implement interface method onNestedScrollAccepted", e3);
                        }
                    }
                    return true;
                }
                if (parent instanceof View) {
                    view2 = (View) parent;
                }
            }
        }
        return false;
    }

    public final void h(int i2) {
        ViewParent viewParentE = e(i2);
        if (viewParentE != null) {
            boolean z2 = viewParentE instanceof h;
            View view = this.f2831c;
            if (z2) {
                ((h) viewParentE).c(view, i2);
            } else if (i2 == 0) {
                try {
                    viewParentE.onStopNestedScroll(view);
                } catch (AbstractMethodError e2) {
                    Log.e("ViewParentCompat", "ViewParent " + viewParentE + " does not implement interface method onStopNestedScroll", e2);
                }
            }
            if (i2 == 0) {
                this.f2829a = null;
            } else {
                if (i2 != 1) {
                    return;
                }
                this.f2830b = null;
            }
        }
    }
}
