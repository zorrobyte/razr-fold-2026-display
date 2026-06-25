package com.google.android.material.textfield;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import w.C0163b;

/* JADX INFO: loaded from: classes.dex */
public final class b extends v.b {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final TextInputLayout f2242c;

    public b(TextInputLayout textInputLayout) {
        this.f2242c = textInputLayout;
    }

    @Override // v.b
    public final void b(View view, C0163b c0163b) {
        View.AccessibilityDelegate accessibilityDelegate = v.b.f2827b;
        AccessibilityNodeInfo accessibilityNodeInfo = c0163b.f2840a;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        TextInputLayout textInputLayout = this.f2242c;
        EditText editText = textInputLayout.getEditText();
        Editable text = editText != null ? editText.getText() : null;
        CharSequence hint = textInputLayout.getHint();
        CharSequence error = textInputLayout.getError();
        CharSequence counterOverflowDescription = textInputLayout.getCounterOverflowDescription();
        boolean z2 = !TextUtils.isEmpty(text);
        boolean z3 = !TextUtils.isEmpty(hint);
        boolean z4 = !TextUtils.isEmpty(error);
        boolean z5 = false;
        boolean z6 = z4 || !TextUtils.isEmpty(counterOverflowDescription);
        if (z2) {
            accessibilityNodeInfo.setText(text);
        } else if (z3) {
            accessibilityNodeInfo.setText(hint);
        }
        if (z3) {
            accessibilityNodeInfo.setHintText(hint);
            if (!z2 && z3) {
                z5 = true;
            }
            accessibilityNodeInfo.setShowingHintText(z5);
        }
        if (z6) {
            if (!z4) {
                error = counterOverflowDescription;
            }
            accessibilityNodeInfo.setError(error);
            accessibilityNodeInfo.setContentInvalid(true);
        }
    }

    @Override // v.b
    public final void c(View view, AccessibilityEvent accessibilityEvent) {
        super.c(view, accessibilityEvent);
        TextInputLayout textInputLayout = this.f2242c;
        EditText editText = textInputLayout.getEditText();
        CharSequence text = editText != null ? editText.getText() : null;
        if (TextUtils.isEmpty(text)) {
            text = textInputLayout.getHint();
        }
        if (TextUtils.isEmpty(text)) {
            return;
        }
        accessibilityEvent.getText().add(text);
    }
}
