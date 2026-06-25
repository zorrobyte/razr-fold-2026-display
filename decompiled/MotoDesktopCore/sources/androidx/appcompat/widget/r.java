package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.appcompat.R$styleable;
import b.AbstractC0122a;

/* JADX INFO: loaded from: classes.dex */
public final class r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final CompoundButton f1291a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ColorStateList f1292b = null;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public PorterDuff.Mode f1293c = null;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1294d = false;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1295e = false;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1296f;

    public r(CompoundButton compoundButton) {
        this.f1291a = compoundButton;
    }

    public final void a() {
        CompoundButton compoundButton = this.f1291a;
        Drawable buttonDrawable = compoundButton.getButtonDrawable();
        if (buttonDrawable != null) {
            if (this.f1294d || this.f1295e) {
                Drawable drawableMutate = buttonDrawable.mutate();
                if (this.f1294d) {
                    drawableMutate.setTintList(this.f1292b);
                }
                if (this.f1295e) {
                    drawableMutate.setTintMode(this.f1293c);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(compoundButton.getDrawableState());
                }
                compoundButton.setButtonDrawable(drawableMutate);
            }
        }
    }

    public final void b(AttributeSet attributeSet, int i2) {
        int resourceId;
        CompoundButton compoundButton = this.f1291a;
        TypedArray typedArrayObtainStyledAttributes = compoundButton.getContext().obtainStyledAttributes(attributeSet, R$styleable.CompoundButton, i2, 0);
        try {
            if (typedArrayObtainStyledAttributes.hasValue(R$styleable.CompoundButton_android_button) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.CompoundButton_android_button, 0)) != 0) {
                compoundButton.setButtonDrawable(AbstractC0122a.a(compoundButton.getContext(), resourceId));
            }
            if (typedArrayObtainStyledAttributes.hasValue(R$styleable.CompoundButton_buttonTint)) {
                compoundButton.setButtonTintList(typedArrayObtainStyledAttributes.getColorStateList(R$styleable.CompoundButton_buttonTint));
            }
            if (typedArrayObtainStyledAttributes.hasValue(R$styleable.CompoundButton_buttonTintMode)) {
                compoundButton.setButtonTintMode(G.c(typedArrayObtainStyledAttributes.getInt(R$styleable.CompoundButton_buttonTintMode, -1), null));
            }
            typedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
