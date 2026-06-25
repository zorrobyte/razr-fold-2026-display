package com.google.android.material.transformation;

import N.a;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExpandableBehavior extends AbstractC0143b {
    public ExpandableBehavior() {
    }

    public ExpandableBehavior(Context context, AttributeSet attributeSet) {
    }

    @Override // k.AbstractC0143b
    public abstract boolean b(View view, View view2);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // k.AbstractC0143b
    public final boolean d(CoordinatorLayout coordinatorLayout, View view, View view2) {
        ((FloatingActionButton) ((a) view2)).getClass();
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // k.AbstractC0143b
    public final boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
        a aVar;
        WeakHashMap weakHashMap = l.f2836a;
        if (!view.isLaidOut()) {
            ArrayList arrayListI = coordinatorLayout.i(view);
            int size = arrayListI.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    aVar = null;
                    break;
                }
                View view2 = (View) arrayListI.get(i3);
                if (b(view, view2)) {
                    aVar = (a) view2;
                    break;
                }
                i3++;
            }
            if (aVar != null) {
                throw null;
            }
        }
        return false;
    }
}
