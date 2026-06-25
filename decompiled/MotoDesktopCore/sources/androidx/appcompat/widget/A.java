package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import androidx.appcompat.view.menu.ViewTreeObserverOnGlobalLayoutListenerC0062f;

/* JADX INFO: loaded from: classes.dex */
public final class A extends Q {

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public CharSequence f857C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public ListAdapter f858D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final Rect f859E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public final /* synthetic */ AppCompatSpinner f860F;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A(AppCompatSpinner appCompatSpinner, Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2, 0);
        this.f860F = appCompatSpinner;
        this.f859E = new Rect();
        this.f1061o = appCompatSpinner;
        this.f1069x = true;
        this.f1070y.setFocusable(true);
        this.f1062p = new Y.y(1, this);
    }

    @Override // androidx.appcompat.widget.Q
    public final void c(ListAdapter listAdapter) {
        super.c(listAdapter);
        this.f858D = listAdapter;
    }

    public final void g() {
        int i2;
        C0083u c0083u = this.f1070y;
        Drawable background = c0083u.getBackground();
        AppCompatSpinner appCompatSpinner = this.f860F;
        if (background != null) {
            background.getPadding(appCompatSpinner.f958h);
            boolean zA = y0.a(appCompatSpinner);
            Rect rect = appCompatSpinner.f958h;
            i2 = zA ? rect.right : -rect.left;
        } else {
            Rect rect2 = appCompatSpinner.f958h;
            rect2.right = 0;
            rect2.left = 0;
            i2 = 0;
        }
        int paddingLeft = appCompatSpinner.getPaddingLeft();
        int paddingRight = appCompatSpinner.getPaddingRight();
        int width = appCompatSpinner.getWidth();
        int i3 = appCompatSpinner.f957g;
        if (i3 == -2) {
            int iA = appCompatSpinner.a((SpinnerAdapter) this.f858D, c0083u.getBackground());
            int i4 = appCompatSpinner.getContext().getResources().getDisplayMetrics().widthPixels;
            Rect rect3 = appCompatSpinner.f958h;
            int i5 = (i4 - rect3.left) - rect3.right;
            if (iA > i5) {
                iA = i5;
            }
            e(Math.max(iA, (width - paddingLeft) - paddingRight));
        } else if (i3 == -1) {
            e((width - paddingLeft) - paddingRight);
        } else {
            e(i3);
        }
        this.f1052f = y0.a(appCompatSpinner) ? ((width - paddingRight) - this.f1051e) + i2 : i2 + paddingLeft;
    }

    @Override // androidx.appcompat.widget.Q, androidx.appcompat.view.menu.E
    public final void show() {
        ViewTreeObserver viewTreeObserver;
        C0083u c0083u = this.f1070y;
        boolean zIsShowing = c0083u.isShowing();
        g();
        this.f1070y.setInputMethodMode(2);
        super.show();
        this.f1049c.setChoiceMode(1);
        AppCompatSpinner appCompatSpinner = this.f860F;
        int selectedItemPosition = appCompatSpinner.getSelectedItemPosition();
        I i2 = this.f1049c;
        if (c0083u.isShowing() && i2 != null) {
            i2.setListSelectionHidden(false);
            i2.setSelection(selectedItemPosition);
            if (i2.getChoiceMode() != 0) {
                i2.setItemChecked(selectedItemPosition, true);
            }
        }
        if (zIsShowing || (viewTreeObserver = appCompatSpinner.getViewTreeObserver()) == null) {
            return;
        }
        ViewTreeObserverOnGlobalLayoutListenerC0062f viewTreeObserverOnGlobalLayoutListenerC0062f = new ViewTreeObserverOnGlobalLayoutListenerC0062f(this, 2);
        viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserverOnGlobalLayoutListenerC0062f);
        this.f1070y.setOnDismissListener(new C0088z(this, viewTreeObserverOnGlobalLayoutListenerC0062f));
    }
}
