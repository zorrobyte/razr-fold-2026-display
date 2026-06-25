package K;

import F.j;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.s;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import e0.k;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import k.C0146e;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public final class e extends s {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public BottomSheetBehavior f196c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f197d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f198e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f199f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public k f200g;

    public final FrameLayout c(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = (FrameLayout) View.inflate(getContext(), R$layout.design_bottom_sheet_dialog, null);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(R$id.coordinator);
        if (i2 != 0 && view == null) {
            view = getLayoutInflater().inflate(i2, (ViewGroup) coordinatorLayout, false);
        }
        FrameLayout frameLayout2 = (FrameLayout) coordinatorLayout.findViewById(R$id.design_bottom_sheet);
        ViewGroup.LayoutParams layoutParams2 = frameLayout2.getLayoutParams();
        if (!(layoutParams2 instanceof C0146e)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        AbstractC0143b abstractC0143b = ((C0146e) layoutParams2).f2746a;
        if (!(abstractC0143b instanceof BottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) abstractC0143b;
        this.f196c = bottomSheetBehavior;
        bottomSheetBehavior.f2126t = this.f200g;
        bottomSheetBehavior.f2117j = this.f197d;
        if (layoutParams == null) {
            frameLayout2.addView(view);
        } else {
            frameLayout2.addView(view, layoutParams);
        }
        coordinatorLayout.findViewById(R$id.touch_outside).setOnClickListener(new c(0, this));
        l.b(frameLayout2, new j(this, 1));
        frameLayout2.setOnTouchListener(new d());
        return frameLayout;
    }

    @Override // androidx.appcompat.app.s, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.clearFlags(67108864);
            window.addFlags(Integer.MIN_VALUE);
            window.setLayout(-1, -1);
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        int i2;
        super.onStart();
        BottomSheetBehavior bottomSheetBehavior = this.f196c;
        if (bottomSheetBehavior == null || (i2 = bottomSheetBehavior.f2119l) != 5 || 4 == i2) {
            return;
        }
        WeakReference weakReference = bottomSheetBehavior.r;
        if (weakReference == null) {
            bottomSheetBehavior.f2119l = 4;
            return;
        }
        View view = (View) weakReference.get();
        if (view == null) {
            return;
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent.isLayoutRequested()) {
            WeakHashMap weakHashMap = l.f2836a;
            if (view.isAttachedToWindow()) {
                view.post(new a(bottomSheetBehavior, view));
                return;
            }
        }
        bottomSheetBehavior.x(view, 4);
    }

    @Override // android.app.Dialog
    public final void setCancelable(boolean z2) {
        super.setCancelable(z2);
        if (this.f197d != z2) {
            this.f197d = z2;
            BottomSheetBehavior bottomSheetBehavior = this.f196c;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.f2117j = z2;
            }
        }
    }

    @Override // android.app.Dialog
    public final void setCanceledOnTouchOutside(boolean z2) {
        super.setCanceledOnTouchOutside(z2);
        if (z2 && !this.f197d) {
            this.f197d = true;
        }
        this.f198e = z2;
        this.f199f = true;
    }

    @Override // androidx.appcompat.app.s, android.app.Dialog
    public final void setContentView(int i2) {
        super.setContentView(c(null, i2, null));
    }

    @Override // androidx.appcompat.app.s, android.app.Dialog
    public final void setContentView(View view) {
        super.setContentView(c(view, 0, null));
    }

    @Override // androidx.appcompat.app.s, android.app.Dialog
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(c(view, 0, layoutParams));
    }
}
