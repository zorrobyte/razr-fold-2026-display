package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import androidx.appcompat.R$attr;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatButton extends Button {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0080q f933a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final B f934b;

    /* JADX WARN: Illegal instructions before constructor call */
    public AppCompatButton(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.buttonStyle;
        m0.a(context);
        super(context, attributeSet, i2);
        C0080q c0080q = new C0080q(this);
        this.f933a = c0080q;
        c0080q.d(attributeSet, i2);
        B b2 = new B(this);
        this.f934b = b2;
        b2.d(attributeSet, i2);
        b2.b();
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            c0080q.a();
        }
        B b2 = this.f934b;
        if (b2 != null) {
            b2.b();
        }
    }

    @Override // android.widget.TextView
    public int getAutoSizeMaxTextSize() {
        return super.getAutoSizeMaxTextSize();
    }

    @Override // android.widget.TextView
    public int getAutoSizeMinTextSize() {
        return super.getAutoSizeMinTextSize();
    }

    @Override // android.widget.TextView
    public int getAutoSizeStepGranularity() {
        return super.getAutoSizeStepGranularity();
    }

    @Override // android.widget.TextView
    public int[] getAutoSizeTextAvailableSizes() {
        return super.getAutoSizeTextAvailableSizes();
    }

    @Override // android.widget.TextView
    public int getAutoSizeTextType() {
        return super.getAutoSizeTextType() == 1 ? 1 : 0;
    }

    public ColorStateList getSupportBackgroundTintList() {
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            return c0080q.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            return c0080q.c();
        }
        return null;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        B b2 = this.f934b;
        if (b2 != null) {
            b2.getClass();
        }
    }

    @Override // android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        super.onTextChanged(charSequence, i2, i3, i4);
    }

    @Override // android.widget.TextView
    public final void setAutoSizeTextTypeUniformWithConfiguration(int i2, int i3, int i4, int i5) {
        super.setAutoSizeTextTypeUniformWithConfiguration(i2, i3, i4, i5);
    }

    @Override // android.widget.TextView
    public final void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i2) {
        super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i2);
    }

    @Override // android.widget.TextView
    public void setAutoSizeTextTypeWithDefaults(int i2) {
        super.setAutoSizeTextTypeWithDefaults(i2);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            c0080q.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            c0080q.f(i2);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    public void setSupportAllCaps(boolean z2) {
        B b2 = this.f934b;
        if (b2 != null) {
            b2.f962a.setAllCaps(z2);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            c0080q.h(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0080q c0080q = this.f933a;
        if (c0080q != null) {
            c0080q.i(mode);
        }
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        B b2 = this.f934b;
        if (b2 != null) {
            b2.e(context, i2);
        }
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i2, float f2) {
        super.setTextSize(i2, f2);
    }
}
