package F;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.recyclerview.widget.B;
import androidx.recyclerview.widget.H;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.NavigationMenuItemView;
import com.motorola.mobiledesktop.core.uinput.EventType;
import w.C0163b;

/* JADX INFO: loaded from: classes.dex */
public final class j extends v.b {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f134c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Object f135d;

    public /* synthetic */ j(KeyEvent.Callback callback, int i2) {
        this.f134c = i2;
        this.f135d = callback;
    }

    public j(H h2) {
        this.f134c = 4;
        this.f135d = h2;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    @Override // v.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(android.view.View r2, android.view.accessibility.AccessibilityEvent r3) {
        /*
            r1 = this;
            int r0 = r1.f134c
            switch(r0) {
                case 0: goto L16;
                case 1: goto L5;
                case 2: goto L9;
                default: goto L5;
            }
        L5:
            super.a(r2, r3)
            return
        L9:
            super.a(r2, r3)
            java.lang.Object r1 = r1.f135d
            com.google.android.material.internal.CheckableImageButton r1 = (com.google.android.material.internal.CheckableImageButton) r1
            boolean r1 = r1.f2164c
            r3.setChecked(r1)
            return
        L16:
            super.a(r2, r3)
            java.lang.Class<androidx.viewpager.widget.ViewPager> r2 = androidx.viewpager.widget.ViewPager.class
            java.lang.String r2 = r2.getName()
            r3.setClassName(r2)
            java.lang.Object r1 = r1.f135d
            androidx.viewpager.widget.ViewPager r1 = (androidx.viewpager.widget.ViewPager) r1
            F.a r2 = r1.f1986e
            if (r2 == 0) goto L32
            int r2 = r2.d()
            r0 = 1
            if (r2 <= r0) goto L32
            goto L33
        L32:
            r0 = 0
        L33:
            r3.setScrollable(r0)
            int r2 = r3.getEventType()
            r0 = 4096(0x1000, float:5.74E-42)
            if (r2 != r0) goto L53
            F.a r2 = r1.f1986e
            if (r2 == 0) goto L53
            int r2 = r2.d()
            r3.setItemCount(r2)
            int r2 = r1.f1987f
            r3.setFromIndex(r2)
            int r1 = r1.f1987f
            r3.setToIndex(r1)
        L53:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: F.j.a(android.view.View, android.view.accessibility.AccessibilityEvent):void");
    }

    @Override // v.b
    public final void b(View view, C0163b c0163b) {
        switch (this.f134c) {
            case 0:
                View.AccessibilityDelegate accessibilityDelegate = v.b.f2827b;
                AccessibilityNodeInfo accessibilityNodeInfo = c0163b.f2840a;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName("androidx.viewpager.widget.ViewPager");
                ViewPager viewPager = (ViewPager) this.f135d;
                a aVar = viewPager.f1986e;
                accessibilityNodeInfo.setScrollable(aVar != null && aVar.d() > 1);
                if (viewPager.canScrollHorizontally(1)) {
                    c0163b.a(4096);
                }
                if (viewPager.canScrollHorizontally(-1)) {
                    c0163b.a(8192);
                }
                break;
            case 1:
                View.AccessibilityDelegate accessibilityDelegate2 = v.b.f2827b;
                AccessibilityNodeInfo accessibilityNodeInfo2 = c0163b.f2840a;
                accessibilityDelegate2.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                if (!((K.e) this.f135d).f197d) {
                    accessibilityNodeInfo2.setDismissable(false);
                } else {
                    c0163b.a(1048576);
                    accessibilityNodeInfo2.setDismissable(true);
                }
                break;
            case 2:
                View.AccessibilityDelegate accessibilityDelegate3 = v.b.f2827b;
                AccessibilityNodeInfo accessibilityNodeInfo3 = c0163b.f2840a;
                accessibilityDelegate3.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo3);
                accessibilityNodeInfo3.setCheckable(true);
                accessibilityNodeInfo3.setChecked(((CheckableImageButton) this.f135d).f2164c);
                break;
            case 3:
                View.AccessibilityDelegate accessibilityDelegate4 = v.b.f2827b;
                AccessibilityNodeInfo accessibilityNodeInfo4 = c0163b.f2840a;
                accessibilityDelegate4.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo4);
                accessibilityNodeInfo4.setCheckable(((NavigationMenuItemView) this.f135d).f2181x);
                break;
            default:
                v.b.f2827b.onInitializeAccessibilityNodeInfo(view, c0163b.f2840a);
                H h2 = (H) this.f135d;
                if (!h2.f1739c.n()) {
                    RecyclerView recyclerView = h2.f1739c;
                    if (recyclerView.getLayoutManager() != null) {
                        recyclerView.getLayoutManager().getClass();
                        RecyclerView.l(view);
                    }
                }
                break;
        }
    }

    @Override // v.b
    public boolean d(View view, int i2, Bundle bundle) {
        switch (this.f134c) {
            case 0:
                if (v.b.f2827b.performAccessibilityAction(view, i2, bundle)) {
                    return true;
                }
                ViewPager viewPager = (ViewPager) this.f135d;
                if (i2 != 4096) {
                    if (i2 == 8192 && viewPager.canScrollHorizontally(-1)) {
                        viewPager.setCurrentItem(viewPager.f1987f - 1);
                        return true;
                    }
                } else if (viewPager.canScrollHorizontally(1)) {
                    viewPager.setCurrentItem(viewPager.f1987f + 1);
                    return true;
                }
                return false;
            case 1:
                if (i2 == 1048576) {
                    K.e eVar = (K.e) this.f135d;
                    if (eVar.f197d) {
                        eVar.cancel();
                        return true;
                    }
                }
                return v.b.f2827b.performAccessibilityAction(view, i2, bundle);
            case 2:
            case 3:
            default:
                return super.d(view, i2, bundle);
            case EventType.EVENT_MSC /* 4 */:
                if (v.b.f2827b.performAccessibilityAction(view, i2, bundle)) {
                    return true;
                }
                H h2 = (H) this.f135d;
                if (!h2.f1739c.n()) {
                    RecyclerView recyclerView = h2.f1739c;
                    if (recyclerView.getLayoutManager() != null) {
                        B b2 = recyclerView.getLayoutManager().f1913b.f1787a;
                    }
                }
                return false;
        }
    }
}
