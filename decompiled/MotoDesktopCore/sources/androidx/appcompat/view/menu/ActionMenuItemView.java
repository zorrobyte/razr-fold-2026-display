package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.InterfaceC0075l;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuItemView extends AppCompatTextView implements B, View.OnClickListener, InterfaceC0075l {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public q f661d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public CharSequence f662e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f663f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public n f664g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public C0058b f665h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public AbstractC0059c f666i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f667j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f668k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final int f669l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f670m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final int f671n;

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        Resources resources = context.getResources();
        this.f667j = e();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionMenuItemView, 0, 0);
        this.f669l = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.ActionMenuItemView_android_minWidth, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.f671n = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.f670m = -1;
        setSaveEnabled(false);
    }

    @Override // androidx.appcompat.widget.InterfaceC0075l
    public final boolean a() {
        return !TextUtils.isEmpty(getText());
    }

    @Override // androidx.appcompat.widget.InterfaceC0075l
    public final boolean b() {
        return (TextUtils.isEmpty(getText()) ^ true) && this.f661d.getIcon() == null;
    }

    @Override // androidx.appcompat.view.menu.B
    public final void c(q qVar) {
        this.f661d = qVar;
        setIcon(qVar.getIcon());
        setTitle(qVar.getTitleCondensed());
        setId(qVar.f811a);
        setVisibility(qVar.isVisible() ? 0 : 8);
        setEnabled(qVar.isEnabled());
        if (qVar.hasSubMenu() && this.f665h == null) {
            this.f665h = new C0058b(this);
        }
    }

    public final boolean e() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i2 = configuration.screenWidthDp;
        return i2 >= 480 || (i2 >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    public final void f() {
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(this.f662e);
        if (this.f663f != null && ((this.f661d.f834y & 4) != 4 || (!this.f667j && !this.f668k))) {
            z2 = false;
        }
        boolean z4 = z3 & z2;
        setText(z4 ? this.f662e : null);
        CharSequence charSequence = this.f661d.f827q;
        if (TextUtils.isEmpty(charSequence)) {
            setContentDescription(z4 ? null : this.f661d.f815e);
        } else {
            setContentDescription(charSequence);
        }
        CharSequence charSequence2 = this.f661d.r;
        if (TextUtils.isEmpty(charSequence2)) {
            setTooltipText(z4 ? null : this.f661d.f815e);
        } else {
            setTooltipText(charSequence2);
        }
    }

    @Override // androidx.appcompat.view.menu.B
    public q getItemData() {
        return this.f661d;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        n nVar = this.f664g;
        if (nVar != null) {
            nVar.a(this.f661d);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.f667j = e();
        f();
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public final void onMeasure(int i2, int i3) {
        int i4;
        boolean z2 = !TextUtils.isEmpty(getText());
        if (z2 && (i4 = this.f670m) >= 0) {
            super.setPadding(i4, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i2, i3);
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int measuredWidth = getMeasuredWidth();
        int i5 = this.f669l;
        int iMin = mode == Integer.MIN_VALUE ? Math.min(size, i5) : i5;
        if (mode != 1073741824 && i5 > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i3);
        }
        if (z2 || this.f663f == null) {
            return;
        }
        super.setPadding((getMeasuredWidth() - this.f663f.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        C0058b c0058b;
        if (this.f661d.hasSubMenu() && (c0058b = this.f665h) != null && c0058b.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setCheckable(boolean z2) {
    }

    public void setChecked(boolean z2) {
    }

    public void setExpandedFormat(boolean z2) {
        if (this.f668k != z2) {
            this.f668k = z2;
            q qVar = this.f661d;
            if (qVar != null) {
                o oVar = qVar.f824n;
                oVar.f791k = true;
                oVar.p(true);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.f663f = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i2 = this.f671n;
            if (intrinsicWidth > i2) {
                intrinsicHeight = (int) (intrinsicHeight * (i2 / intrinsicWidth));
                intrinsicWidth = i2;
            }
            if (intrinsicHeight > i2) {
                intrinsicWidth = (int) (intrinsicWidth * (i2 / intrinsicHeight));
            } else {
                i2 = intrinsicHeight;
            }
            drawable.setBounds(0, 0, intrinsicWidth, i2);
        }
        setCompoundDrawables(drawable, null, null, null);
        f();
    }

    public void setItemInvoker(n nVar) {
        this.f664g = nVar;
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPadding(int i2, int i3, int i4, int i5) {
        this.f670m = i2;
        super.setPadding(i2, i3, i4, i5);
    }

    public void setPopupCallback(AbstractC0059c abstractC0059c) {
        this.f666i = abstractC0059c;
    }

    public void setTitle(CharSequence charSequence) {
        this.f662e = charSequence;
        f();
    }
}
