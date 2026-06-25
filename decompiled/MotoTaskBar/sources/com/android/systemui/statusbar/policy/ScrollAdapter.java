package com.android.systemui.statusbar.policy;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface ScrollAdapter {
    View getHostView();

    boolean isScrolledToBottom();

    boolean isScrolledToTop();
}
