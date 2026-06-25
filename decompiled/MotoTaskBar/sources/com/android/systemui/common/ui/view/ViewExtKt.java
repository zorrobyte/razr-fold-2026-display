package com.android.systemui.common.ui.view;

import android.view.View;

/* JADX INFO: compiled from: ViewExt.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewExtKt {
    public static final void setImportantForAccessibilityYesNo(View view, boolean z) {
        view.getClass();
        view.setImportantForAccessibility(z ? 1 : 2);
    }
}
