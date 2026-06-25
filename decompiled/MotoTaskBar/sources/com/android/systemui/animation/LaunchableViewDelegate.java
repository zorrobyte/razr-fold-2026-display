package com.android.systemui.animation;

import android.view.View;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: LaunchableView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaunchableViewDelegate {
    private boolean blockVisibilityChanges;
    private int lastVisibility;
    private final Function1 superSetVisibility;
    private final View view;

    public LaunchableViewDelegate(View view, Function1 function1) {
        view.getClass();
        function1.getClass();
        this.view = view;
        this.superSetVisibility = function1;
        this.lastVisibility = view.getVisibility();
    }

    public final void setVisibility(int i) {
        if (this.blockVisibilityChanges) {
            this.lastVisibility = i;
        } else {
            this.superSetVisibility.invoke(Integer.valueOf(i));
        }
    }
}
