package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import androidx.appcompat.R$attr;
import b.AbstractC0122a;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatCheckBox extends CheckBox {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final r f935a;

    /* JADX WARN: Illegal instructions before constructor call */
    public AppCompatCheckBox(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.checkboxStyle;
        m0.a(context);
        super(context, attributeSet, i2);
        r rVar = new r(this);
        this.f935a = rVar;
        rVar.b(attributeSet, i2);
        new B(this).d(attributeSet, i2);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        r rVar = this.f935a;
        if (rVar != null) {
            rVar.getClass();
        }
        return compoundPaddingLeft;
    }

    public ColorStateList getSupportButtonTintList() {
        r rVar = this.f935a;
        if (rVar != null) {
            return rVar.f1292b;
        }
        return null;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        r rVar = this.f935a;
        if (rVar != null) {
            return rVar.f1293c;
        }
        return null;
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(int i2) {
        setButtonDrawable(AbstractC0122a.a(getContext(), i2));
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        r rVar = this.f935a;
        if (rVar != null) {
            if (rVar.f1296f) {
                rVar.f1296f = false;
            } else {
                rVar.f1296f = true;
                rVar.a();
            }
        }
    }

    public void setSupportButtonTintList(ColorStateList colorStateList) {
        r rVar = this.f935a;
        if (rVar != null) {
            rVar.f1292b = colorStateList;
            rVar.f1294d = true;
            rVar.a();
        }
    }

    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        r rVar = this.f935a;
        if (rVar != null) {
            rVar.f1293c = mode;
            rVar.f1295e = true;
            rVar.a();
        }
    }
}
