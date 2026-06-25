package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import j.C0141b;
import j.c;
import j.d;
import j.e;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class Constraints extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public d f1364a;

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        e eVar = new e();
        eVar.f2728Y = 1.0f;
        eVar.Z = 0.0f;
        eVar.f2729a0 = 0.0f;
        eVar.f2730b0 = 0.0f;
        eVar.f2731c0 = 0.0f;
        eVar.f2732d0 = 1.0f;
        eVar.f2733e0 = 1.0f;
        eVar.f2734f0 = 0.0f;
        eVar.f2735g0 = 0.0f;
        eVar.h0 = 0.0f;
        eVar.f2736i0 = 0.0f;
        return eVar;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        e eVar = new e(context, attributeSet);
        eVar.f2728Y = 1.0f;
        eVar.Z = 0.0f;
        eVar.f2729a0 = 0.0f;
        eVar.f2730b0 = 0.0f;
        eVar.f2731c0 = 0.0f;
        eVar.f2732d0 = 1.0f;
        eVar.f2733e0 = 1.0f;
        eVar.f2734f0 = 0.0f;
        eVar.f2735g0 = 0.0f;
        eVar.h0 = 0.0f;
        eVar.f2736i0 = 0.0f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintSet);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R$styleable.ConstraintSet_android_alpha) {
                eVar.f2728Y = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2728Y);
            } else if (index == R$styleable.ConstraintSet_android_elevation) {
                eVar.Z = typedArrayObtainStyledAttributes.getFloat(index, eVar.Z);
            } else if (index == R$styleable.ConstraintSet_android_rotationX) {
                eVar.f2730b0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2730b0);
            } else if (index == R$styleable.ConstraintSet_android_rotationY) {
                eVar.f2731c0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2731c0);
            } else if (index == R$styleable.ConstraintSet_android_rotation) {
                eVar.f2729a0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2729a0);
            } else if (index == R$styleable.ConstraintSet_android_scaleX) {
                eVar.f2732d0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2732d0);
            } else if (index == R$styleable.ConstraintSet_android_scaleY) {
                eVar.f2733e0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2733e0);
            } else if (index == R$styleable.ConstraintSet_android_transformPivotX) {
                eVar.f2734f0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2734f0);
            } else if (index == R$styleable.ConstraintSet_android_transformPivotY) {
                eVar.f2735g0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2735g0);
            } else if (index == R$styleable.ConstraintSet_android_translationX) {
                eVar.h0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.h0);
            } else if (index == R$styleable.ConstraintSet_android_translationY) {
                eVar.f2736i0 = typedArrayObtainStyledAttributes.getFloat(index, eVar.f2736i0);
            } else if (index == R$styleable.ConstraintSet_android_translationZ) {
                eVar.h0 = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
            }
        }
        return eVar;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new C0141b(layoutParams);
    }

    public d getConstraintSet() {
        if (this.f1364a == null) {
            d dVar = new d();
            dVar.f2727a = new HashMap();
            this.f1364a = dVar;
        }
        d dVar2 = this.f1364a;
        dVar2.getClass();
        int childCount = getChildCount();
        HashMap map = dVar2.f2727a;
        map.clear();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            e eVar = (e) childAt.getLayoutParams();
            int id = childAt.getId();
            if (id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!map.containsKey(Integer.valueOf(id))) {
                map.put(Integer.valueOf(id), new c());
            }
            c cVar = (c) map.get(Integer.valueOf(id));
            if (childAt instanceof ConstraintHelper) {
                ConstraintHelper constraintHelper = (ConstraintHelper) childAt;
                cVar.a(eVar);
                if (constraintHelper instanceof Barrier) {
                    Barrier barrier = (Barrier) constraintHelper;
                    barrier.getType();
                    cVar.f2701X = barrier.getReferencedIds();
                }
            }
            cVar.a(eVar);
        }
        return this.f1364a;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
    }
}
