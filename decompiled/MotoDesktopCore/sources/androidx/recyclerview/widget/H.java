package androidx.recyclerview.widget;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.internal.NavigationMenuView;
import w.C0163b;

/* JADX INFO: loaded from: classes.dex */
public final class H extends v.b {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final RecyclerView f1739c;

    public H(NavigationMenuView navigationMenuView) {
        this.f1739c = navigationMenuView;
        new F.j(this);
    }

    @Override // v.b
    public final void a(View view, AccessibilityEvent accessibilityEvent) {
        super.a(view, accessibilityEvent);
        accessibilityEvent.setClassName(RecyclerView.class.getName());
        if (!(view instanceof RecyclerView) || this.f1739c.n()) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) view;
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().B(accessibilityEvent);
        }
    }

    @Override // v.b
    public final void b(View view, C0163b c0163b) {
        View.AccessibilityDelegate accessibilityDelegate = v.b.f2827b;
        AccessibilityNodeInfo accessibilityNodeInfo = c0163b.f2840a;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.recyclerview.widget.RecyclerView");
        RecyclerView recyclerView = this.f1739c;
        if (recyclerView.n() || recyclerView.getLayoutManager() == null) {
            return;
        }
        v layoutManager = recyclerView.getLayoutManager();
        RecyclerView recyclerView2 = layoutManager.f1913b;
        B b2 = recyclerView2.f1787a;
        if (recyclerView2.canScrollVertically(-1) || layoutManager.f1913b.canScrollHorizontally(-1)) {
            c0163b.a(8192);
            accessibilityNodeInfo.setScrollable(true);
        }
        if (layoutManager.f1913b.canScrollVertically(1) || layoutManager.f1913b.canScrollHorizontally(1)) {
            c0163b.a(4096);
            accessibilityNodeInfo.setScrollable(true);
        }
        E e2 = recyclerView2.V;
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(layoutManager.y(b2, e2), layoutManager.q(b2, e2), false, 0));
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0058 A[PHI: r3
      0x0058: PHI (r3v11 int) = (r3v7 int), (r3v15 int) binds: [B:27:0x0074, B:19:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // v.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean d(android.view.View r3, int r4, android.os.Bundle r5) {
        /*
            r2 = this;
            android.view.View$AccessibilityDelegate r0 = v.b.f2827b
            boolean r3 = r0.performAccessibilityAction(r3, r4, r5)
            r5 = 1
            if (r3 == 0) goto La
            return r5
        La:
            androidx.recyclerview.widget.RecyclerView r2 = r2.f1739c
            boolean r3 = r2.n()
            r0 = 0
            if (r3 != 0) goto L8e
            androidx.recyclerview.widget.v r3 = r2.getLayoutManager()
            if (r3 == 0) goto L8e
            androidx.recyclerview.widget.v r2 = r2.getLayoutManager()
            androidx.recyclerview.widget.RecyclerView r3 = r2.f1913b
            androidx.recyclerview.widget.B r1 = r3.f1787a
            r1 = 4096(0x1000, float:5.74E-42)
            if (r4 == r1) goto L5a
            r1 = 8192(0x2000, float:1.14794E-41)
            if (r4 == r1) goto L2c
            r3 = r0
            r4 = r3
            goto L82
        L2c:
            r4 = -1
            boolean r3 = r3.canScrollVertically(r4)
            if (r3 == 0) goto L41
            int r3 = r2.f1918g
            int r1 = r2.v()
            int r3 = r3 - r1
            int r1 = r2.s()
            int r3 = r3 - r1
            int r3 = -r3
            goto L42
        L41:
            r3 = r0
        L42:
            androidx.recyclerview.widget.RecyclerView r1 = r2.f1913b
            boolean r4 = r1.canScrollHorizontally(r4)
            if (r4 == 0) goto L58
            int r4 = r2.f1917f
            int r1 = r2.t()
            int r4 = r4 - r1
            int r1 = r2.u()
            int r4 = r4 - r1
            int r4 = -r4
            goto L82
        L58:
            r4 = r0
            goto L82
        L5a:
            boolean r3 = r3.canScrollVertically(r5)
            if (r3 == 0) goto L6d
            int r3 = r2.f1918g
            int r4 = r2.v()
            int r3 = r3 - r4
            int r4 = r2.s()
            int r3 = r3 - r4
            goto L6e
        L6d:
            r3 = r0
        L6e:
            androidx.recyclerview.widget.RecyclerView r4 = r2.f1913b
            boolean r4 = r4.canScrollHorizontally(r5)
            if (r4 == 0) goto L58
            int r4 = r2.f1917f
            int r1 = r2.t()
            int r4 = r4 - r1
            int r1 = r2.u()
            int r4 = r4 - r1
        L82:
            if (r3 != 0) goto L88
            if (r4 != 0) goto L88
            r5 = r0
            goto L8d
        L88:
            androidx.recyclerview.widget.RecyclerView r2 = r2.f1913b
            r2.v(r4, r3)
        L8d:
            return r5
        L8e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.H.d(android.view.View, int, android.os.Bundle):boolean");
    }
}
