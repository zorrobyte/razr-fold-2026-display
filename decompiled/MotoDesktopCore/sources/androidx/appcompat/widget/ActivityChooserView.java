package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.appcompat.R$attr;
import b.AbstractC0122a;

/* JADX INFO: loaded from: classes.dex */
public class ActivityChooserView extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Q f928a;

    public static class InnerLayout extends LinearLayout {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static final int[] f929a = {R.attr.background};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            int resourceId;
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f929a);
            setBackgroundDrawable((!typedArrayObtainStyledAttributes.hasValue(0) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)) == 0) ? typedArrayObtainStyledAttributes.getDrawable(0) : AbstractC0122a.a(context, resourceId));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public AbstractC0079p getDataModel() {
        throw null;
    }

    public Q getListPopupWindow() {
        if (this.f928a == null) {
            Q q2 = new Q(getContext(), null, R$attr.listPopupWindowStyle, 0);
            this.f928a = q2;
            q2.c(null);
            Q q3 = this.f928a;
            q3.f1061o = this;
            q3.f1069x = true;
            q3.f1070y.setFocusable(true);
            Q q4 = this.f928a;
            q4.f1062p = null;
            q4.f1070y.setOnDismissListener(null);
        }
        return this.f928a;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        throw null;
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        throw null;
    }

    public void setActivityChooserModel(AbstractC0079p abstractC0079p) {
        throw null;
    }

    public void setDefaultActionButtonContentDescription(int i2) {
    }

    public void setExpandActivityOverflowButtonContentDescription(int i2) {
        getContext().getString(i2);
        throw null;
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        throw null;
    }

    public void setInitialActivityCount(int i2) {
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
    }

    public void setProvider(v.c cVar) {
    }
}
