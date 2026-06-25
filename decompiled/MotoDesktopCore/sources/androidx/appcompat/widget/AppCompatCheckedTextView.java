package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;
import androidx.appcompat.app.AbstractC0054a;
import b.AbstractC0122a;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatCheckedTextView extends CheckedTextView {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int[] f936b = {R.attr.checkMark};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final B f937a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.checkedTextViewStyle);
        m0.a(context);
        B b2 = new B(this);
        this.f937a = b2;
        b2.d(attributeSet, R.attr.checkedTextViewStyle);
        b2.b();
        f0.b bVarM = f0.b.m(getContext(), attributeSet, f936b, R.attr.checkedTextViewStyle, 0);
        setCheckMarkDrawable(bVarM.f(0));
        bVarM.n();
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        B b2 = this.f937a;
        if (b2 != null) {
            b2.b();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        AbstractC0054a.r(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.widget.CheckedTextView
    public void setCheckMarkDrawable(int i2) {
        setCheckMarkDrawable(AbstractC0122a.a(getContext(), i2));
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        B b2 = this.f937a;
        if (b2 != null) {
            b2.e(context, i2);
        }
    }
}
