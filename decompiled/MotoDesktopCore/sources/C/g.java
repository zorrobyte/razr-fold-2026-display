package C;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class g extends s {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public static final C0002c f38A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public static final C0002c f39B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public static final C0002c f40C;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public static final String[] f41x = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public static final C0002c f42y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public static final C0002c f43z;

    static {
        new C0001b(PointF.class, "boundsOrigin").f27b = new Rect();
        f42y = new C0002c(PointF.class, "topLeft", 0);
        f43z = new C0002c(PointF.class, "bottomRight", 1);
        f38A = new C0002c(PointF.class, "bottomRight", 2);
        f39B = new C0002c(PointF.class, "topLeft", 3);
        f40C = new C0002c(PointF.class, "position", 4);
    }

    public static void H(y yVar) {
        View view = yVar.f103b;
        WeakHashMap weakHashMap = v.l.f2836a;
        if (!view.isLaidOut() && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        HashMap map = yVar.f102a;
        map.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        map.put("android:changeBounds:parent", yVar.f103b.getParent());
    }

    @Override // C.s
    public final void d(y yVar) {
        H(yVar);
    }

    @Override // C.s
    public final void g(y yVar) {
        H(yVar);
    }

    @Override // C.s
    public final Animator k(ViewGroup viewGroup, y yVar, y yVar2) {
        int i2;
        g gVar;
        Animator animatorOfObject;
        if (yVar == null || yVar2 == null) {
            return null;
        }
        HashMap map = yVar.f102a;
        HashMap map2 = yVar2.f102a;
        ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
        ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        View view = yVar2.f103b;
        Rect rect = (Rect) map.get("android:changeBounds:bounds");
        Rect rect2 = (Rect) map2.get("android:changeBounds:bounds");
        int i3 = rect.left;
        int i4 = rect2.left;
        int i5 = rect.top;
        int i6 = rect2.top;
        int i7 = rect.right;
        int i8 = rect2.right;
        int i9 = rect.bottom;
        int i10 = rect2.bottom;
        int i11 = i7 - i3;
        int i12 = i9 - i5;
        int i13 = i8 - i4;
        int i14 = i10 - i6;
        Rect rect3 = (Rect) map.get("android:changeBounds:clip");
        Rect rect4 = (Rect) map2.get("android:changeBounds:clip");
        if ((i11 == 0 || i12 == 0) && (i13 == 0 || i14 == 0)) {
            i2 = 0;
        } else {
            i2 = (i3 == i4 && i5 == i6) ? 0 : 1;
            if (i7 != i8 || i9 != i10) {
                i2++;
            }
        }
        if ((rect3 != null && !rect3.equals(rect4)) || (rect3 == null && rect4 != null)) {
            i2++;
        }
        if (i2 <= 0) {
            return null;
        }
        A.a(view, i3, i5, i7, i9);
        if (i2 != 2) {
            gVar = this;
            if (i3 == i4 && i5 == i6) {
                gVar.f90t.getClass();
                animatorOfObject = ObjectAnimator.ofObject(view, f38A, (TypeConverter) null, n.a(i7, i9, i8, i10));
            } else {
                gVar.f90t.getClass();
                animatorOfObject = ObjectAnimator.ofObject(view, f39B, (TypeConverter) null, n.a(i3, i5, i4, i6));
            }
        } else if (i11 == i13 && i12 == i14) {
            gVar = this;
            gVar.f90t.getClass();
            animatorOfObject = ObjectAnimator.ofObject(view, f40C, (TypeConverter) null, n.a(i3, i5, i4, i6));
        } else {
            gVar = this;
            f fVar = new f();
            fVar.f35e = view;
            gVar.f90t.getClass();
            ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(fVar, f42y, (TypeConverter) null, n.a(i3, i5, i4, i6));
            gVar.f90t.getClass();
            ObjectAnimator objectAnimatorOfObject2 = ObjectAnimator.ofObject(fVar, f43z, (TypeConverter) null, n.a(i7, i9, i8, i10));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorOfObject, objectAnimatorOfObject2);
            animatorSet.addListener(new C0003d(fVar));
            animatorOfObject = animatorSet;
        }
        if (view.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup4 = (ViewGroup) view.getParent();
            Y.r.p0(viewGroup4, true);
            gVar.a(new C0004e(0, viewGroup4));
        }
        return animatorOfObject;
    }

    @Override // C.s
    public final String[] p() {
        return f41x;
    }
}
