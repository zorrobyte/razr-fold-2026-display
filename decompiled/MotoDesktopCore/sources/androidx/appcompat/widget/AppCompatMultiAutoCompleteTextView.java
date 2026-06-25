package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.MultiAutoCompleteTextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.app.AbstractC0054a;
import b.AbstractC0122a;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatMultiAutoCompleteTextView extends MultiAutoCompleteTextView {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final int[] f944c = {R.attr.popupBackground};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0080q f945a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final B f946b;

    /* JADX WARN: Illegal instructions before constructor call */
    public AppCompatMultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.autoCompleteTextViewStyle;
        m0.a(context);
        super(context, attributeSet, i2);
        f0.b bVarM = f0.b.m(getContext(), attributeSet, f944c, i2, 0);
        if (((TypedArray) bVarM.f2538c).hasValue(0)) {
            setDropDownBackgroundDrawable(bVarM.f(0));
        }
        bVarM.n();
        C0080q c0080q = new C0080q(this);
        this.f945a = c0080q;
        c0080q.d(attributeSet, i2);
        B b2 = new B(this);
        this.f946b = b2;
        b2.d(attributeSet, i2);
        b2.b();
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            c0080q.a();
        }
        B b2 = this.f946b;
        if (b2 != null) {
            b2.b();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            return c0080q.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            return c0080q.c();
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        AbstractC0054a.r(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            c0080q.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            c0080q.f(i2);
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public void setDropDownBackgroundResource(int i2) {
        setDropDownBackgroundDrawable(AbstractC0122a.a(getContext(), i2));
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            c0080q.h(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0080q c0080q = this.f945a;
        if (c0080q != null) {
            c0080q.i(mode);
        }
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        B b2 = this.f946b;
        if (b2 != null) {
            b2.e(context, i2);
        }
    }
}
