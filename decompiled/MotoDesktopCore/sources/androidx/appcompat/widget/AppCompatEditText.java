package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.appcompat.R$attr;
import androidx.appcompat.app.AbstractC0054a;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatEditText extends EditText {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0080q f938a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final B f939b;

    /* JADX WARN: Illegal instructions before constructor call */
    public AppCompatEditText(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.editTextStyle;
        m0.a(context);
        super(context, attributeSet, i2);
        C0080q c0080q = new C0080q(this);
        this.f938a = c0080q;
        c0080q.d(attributeSet, i2);
        B b2 = new B(this);
        this.f939b = b2;
        b2.d(attributeSet, i2);
        b2.b();
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            c0080q.a();
        }
        B b2 = this.f939b;
        if (b2 != null) {
            b2.b();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            return c0080q.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            return c0080q.c();
        }
        return null;
    }

    @Override // android.widget.EditText, android.widget.TextView
    public Editable getText() {
        return super.getText();
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        AbstractC0054a.r(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            c0080q.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            c0080q.f(i2);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            c0080q.h(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0080q c0080q = this.f938a;
        if (c0080q != null) {
            c0080q.i(mode);
        }
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        B b2 = this.f939b;
        if (b2 != null) {
            b2.e(context, i2);
        }
    }
}
