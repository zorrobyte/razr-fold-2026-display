package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class NonOverlappingLinearLayout extends LinearLayout {
    boolean mDeferFocusableViewAvailableInLayout;
    boolean mFocusableViewAvailableFixEnabled;
    final ArrayList mSortedAvailableViews;

    public NonOverlappingLinearLayout(Context context) {
        this(context, null);
    }

    public NonOverlappingLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NonOverlappingLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFocusableViewAvailableFixEnabled = false;
        this.mSortedAvailableViews = new ArrayList();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void focusableViewAvailable(View view) {
        int iIndexOfChild;
        if (!this.mDeferFocusableViewAvailableInLayout) {
            super.focusableViewAvailable(view);
            return;
        }
        for (View view2 = view; view2 != this && view2 != null; view2 = (View) view2.getParent()) {
            if (view2.getParent() == this) {
                iIndexOfChild = indexOfChild(view2);
                break;
            }
        }
        iIndexOfChild = -1;
        if (iIndexOfChild != -1) {
            ((ArrayList) this.mSortedAvailableViews.get(iIndexOfChild)).add(view);
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c7  */
    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onLayout(boolean r9, int r10, int r11, int r12, int r13) throws java.lang.Throwable {
        /*
            r8 = this;
            r1 = 0
            boolean r0 = r8.mFocusableViewAvailableFixEnabled     // Catch: java.lang.Throwable -> La8
            r2 = 1
            if (r0 == 0) goto L19
            int r0 = r8.getOrientation()     // Catch: java.lang.Throwable -> L14
            if (r0 != 0) goto L19
            int r0 = r8.getLayoutDirection()     // Catch: java.lang.Throwable -> L14
            if (r0 != r2) goto L19
            r0 = r2
            goto L1a
        L14:
            r0 = move-exception
            r9 = r0
            r2 = r8
            goto Lab
        L19:
            r0 = r1
        L1a:
            r8.mDeferFocusableViewAvailableInLayout = r0     // Catch: java.lang.Throwable -> La8
            if (r0 == 0) goto L4c
        L1e:
            java.util.ArrayList r0 = r8.mSortedAvailableViews     // Catch: java.lang.Throwable -> L14
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L14
            int r3 = r8.getChildCount()     // Catch: java.lang.Throwable -> L14
            if (r0 <= r3) goto L35
            java.util.ArrayList r0 = r8.mSortedAvailableViews     // Catch: java.lang.Throwable -> L14
            int r3 = r0.size()     // Catch: java.lang.Throwable -> L14
            int r3 = r3 - r2
            r0.remove(r3)     // Catch: java.lang.Throwable -> L14
            goto L1e
        L35:
            java.util.ArrayList r0 = r8.mSortedAvailableViews     // Catch: java.lang.Throwable -> L14
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L14
            int r2 = r8.getChildCount()     // Catch: java.lang.Throwable -> L14
            if (r0 >= r2) goto L4c
            java.util.ArrayList r0 = r8.mSortedAvailableViews     // Catch: java.lang.Throwable -> L14
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L14
            r2.<init>()     // Catch: java.lang.Throwable -> L14
            r0.add(r2)     // Catch: java.lang.Throwable -> L14
            goto L35
        L4c:
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            super.onLayout(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L85
            boolean r8 = r2.mDeferFocusableViewAvailableInLayout     // Catch: java.lang.Throwable -> L85
            if (r8 == 0) goto L8b
            r8 = r1
        L5a:
            java.util.ArrayList r9 = r2.mSortedAvailableViews     // Catch: java.lang.Throwable -> L85
            int r9 = r9.size()     // Catch: java.lang.Throwable -> L85
            if (r8 >= r9) goto L8b
            r9 = r1
        L63:
            java.util.ArrayList r10 = r2.mSortedAvailableViews     // Catch: java.lang.Throwable -> L85
            java.lang.Object r10 = r10.get(r8)     // Catch: java.lang.Throwable -> L85
            java.util.ArrayList r10 = (java.util.ArrayList) r10     // Catch: java.lang.Throwable -> L85
            int r10 = r10.size()     // Catch: java.lang.Throwable -> L85
            if (r9 >= r10) goto L88
            java.util.ArrayList r10 = r2.mSortedAvailableViews     // Catch: java.lang.Throwable -> L85
            java.lang.Object r10 = r10.get(r8)     // Catch: java.lang.Throwable -> L85
            java.util.ArrayList r10 = (java.util.ArrayList) r10     // Catch: java.lang.Throwable -> L85
            java.lang.Object r10 = r10.get(r9)     // Catch: java.lang.Throwable -> L85
            android.view.View r10 = (android.view.View) r10     // Catch: java.lang.Throwable -> L85
            super.focusableViewAvailable(r10)     // Catch: java.lang.Throwable -> L85
            int r9 = r9 + 1
            goto L63
        L85:
            r0 = move-exception
        L86:
            r9 = r0
            goto Lab
        L88:
            int r8 = r8 + 1
            goto L5a
        L8b:
            boolean r8 = r2.mDeferFocusableViewAvailableInLayout
            if (r8 == 0) goto La7
            r2.mDeferFocusableViewAvailableInLayout = r1
        L91:
            java.util.ArrayList r8 = r2.mSortedAvailableViews
            int r8 = r8.size()
            if (r1 >= r8) goto La7
            java.util.ArrayList r8 = r2.mSortedAvailableViews
            java.lang.Object r8 = r8.get(r1)
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            r8.clear()
            int r1 = r1 + 1
            goto L91
        La7:
            return
        La8:
            r0 = move-exception
            r2 = r8
            goto L86
        Lab:
            boolean r8 = r2.mDeferFocusableViewAvailableInLayout
            if (r8 == 0) goto Lc7
            r2.mDeferFocusableViewAvailableInLayout = r1
        Lb1:
            java.util.ArrayList r8 = r2.mSortedAvailableViews
            int r8 = r8.size()
            if (r1 >= r8) goto Lc7
            java.util.ArrayList r8 = r2.mSortedAvailableViews
            java.lang.Object r8 = r8.get(r1)
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            r8.clear()
            int r1 = r1 + 1
            goto Lb1
        Lc7:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.NonOverlappingLinearLayout.onLayout(boolean, int, int, int, int):void");
    }
}
